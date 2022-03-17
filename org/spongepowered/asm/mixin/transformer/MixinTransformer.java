package org.spongepowered.asm.mixin.transformer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.MixinEnvironment$Option;
import org.spongepowered.asm.mixin.MixinEnvironment$Phase;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler;
import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler$ErrorAction;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
import org.spongepowered.asm.mixin.throwables.ClassAlreadyLoadedException;
import org.spongepowered.asm.mixin.throwables.MixinApplyError;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.throwables.MixinPrepareError;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.IHotSwap;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckClass;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckClass$ValidationFailedException;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckInterfaces;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionClassExporter;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.ITransformer;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.transformers.TreeTransformer;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.ReEntranceLock;
import org.spongepowered.asm.util.perf.Profiler;
import org.spongepowered.asm.util.perf.Profiler$Section;

public class MixinTransformer extends TreeTransformer {
   private static final String MIXIN_AGENT_CLASS = "org.spongepowered.tools.agent.MixinAgent";
   private static final String METRONOME_AGENT_CLASS = "org.spongepowered.metronome.Agent";
   static final Logger logger = LogManager.getLogger("mixin");
   private final IMixinService service = MixinService.getService();
   private final List<MixinConfig> configs = new ArrayList();
   private final List<MixinConfig> pendingConfigs = new ArrayList();
   private final ReEntranceLock lock;
   private final String sessionId = UUID.randomUUID().toString();
   private final Extensions extensions;
   private final IHotSwap hotSwapper;
   private final MixinPostProcessor postProcessor;
   private final Profiler profiler;
   private MixinEnvironment currentEnvironment;
   private Level verboseLoggingLevel;
   private boolean errorState;
   private int transformedCount;

   MixinTransformer() {
      this.verboseLoggingLevel = Level.DEBUG;
      this.errorState = false;
      this.transformedCount = 0;
      MixinEnvironment var1 = MixinEnvironment.getCurrentEnvironment();
      Object var2 = var1.getActiveTransformer();

      try {
         if (var2 instanceof ITransformer) {
            throw new MixinException("Terminating MixinTransformer instance " + this);
         }
      } catch (ExtensionCheckClass$ValidationFailedException var3) {
         throw b(var3);
      }

      var1.setActiveTransformer(this);
      this.lock = this.service.getReEntranceLock();
      this.extensions = new Extensions(this);
      this.hotSwapper = this.initHotSwapper(var1);
      this.postProcessor = new MixinPostProcessor();
      this.extensions.add((IClassGenerator)(new ArgsClassGenerator()));
      this.extensions.add((IClassGenerator)(new InnerClassGenerator()));
      this.extensions.add((IExtension)(new ExtensionClassExporter(var1)));
      this.extensions.add((IExtension)(new ExtensionCheckClass()));
      this.extensions.add((IExtension)(new ExtensionCheckInterfaces()));
      this.profiler = MixinEnvironment.getProfiler();
   }

   private IHotSwap initHotSwapper(MixinEnvironment var1) {
      try {
         if (!var1.getOption(MixinEnvironment$Option.HOT_SWAP)) {
            return null;
         }
      } catch (Throwable var5) {
         throw b(var5);
      }

      try {
         logger.info("Attempting to load Hot-Swap agent");
         Class var2 = Class.forName("org.spongepowered.tools.agent.MixinAgent");
         Constructor var3 = var2.getDeclaredConstructor(MixinTransformer.class);
         return (IHotSwap)var3.newInstance(this);
      } catch (Throwable var4) {
         logger.info("Hot-swap agent could not be loaded, hot swapping of mixins won't work. {}: {}", new Object[]{var4.getClass().getSimpleName(), var4.getMessage()});
         return null;
      }
   }

   public void audit(MixinEnvironment var1) {
      HashSet var2 = new HashSet();
      Iterator var3 = this.configs.iterator();

      while(var3.hasNext()) {
         MixinConfig var4 = (MixinConfig)var3.next();
         var2.addAll(var4.getUnhandledTargets());
      }

      Logger var11 = LogManager.getLogger("mixin/audit");
      Iterator var12 = var2.iterator();

      while(var12.hasNext()) {
         String var5 = (String)var12.next();

         try {
            var11.info("Force-loading class {}", new Object[]{var5});
            this.service.getClassProvider().findClass(var5, true);
         } catch (ClassNotFoundException var10) {
            var11.error("Could not force-load " + var5, var10);
         }
      }

      var12 = this.configs.iterator();

      while(var12.hasNext()) {
         MixinConfig var13 = (MixinConfig)var12.next();
         Iterator var6 = var13.getUnhandledTargets().iterator();

         while(var6.hasNext()) {
            String var7 = (String)var6.next();
            ClassAlreadyLoadedException var8 = new ClassAlreadyLoadedException(var7 + " was already classloaded");
            var11.error("Could not force-load " + var7, var8);
         }
      }

      try {
         if (var1.getOption(MixinEnvironment$Option.DEBUG_PROFILER)) {
            this.printProfilerSummary();
         }

      } catch (ClassNotFoundException var9) {
         throw b(var9);
      }
   }

   private void printProfilerSummary() {
      DecimalFormat var1 = new DecimalFormat("(###0.000");
      DecimalFormat var2 = new DecimalFormat("(###0.0");
      PrettyPrinter var3 = this.profiler.printer(false, false);
      long var4 = this.profiler.get("mixin.prepare").getTotalTime();
      long var6 = this.profiler.get("mixin.read").getTotalTime();
      long var8 = this.profiler.get("mixin.apply").getTotalTime();
      long var10 = this.profiler.get("mixin.write").getTotalTime();
      long var12 = this.profiler.get("mixin").getTotalTime();
      long var14 = this.profiler.get("class.load").getTotalTime();
      long var16 = this.profiler.get("class.transform").getTotalTime();
      long var18 = this.profiler.get("mixin.debug.export").getTotalTime();
      long var20 = var12 - var14 - var16 - var18;
      double var22 = (double)var20 / (double)var12 * 100.0D;
      double var24 = (double)var14 / (double)var12 * 100.0D;
      double var26 = (double)var16 / (double)var12 * 100.0D;
      double var28 = (double)var18 / (double)var12 * 100.0D;
      long var30 = 0L;
      Profiler$Section var32 = null;
      Iterator var33 = this.profiler.getSections().iterator();

      while(var33.hasNext()) {
         Profiler$Section var34 = (Profiler$Section)var33.next();

         long var10000;
         label95: {
            try {
               if (var34.getName().startsWith("class.transform.")) {
                  var10000 = var34.getTotalTime();
                  break label95;
               }
            } catch (Throwable var49) {
               throw b(var49);
            }

            var10000 = 0L;
         }

         long var35 = var10000;
         if (var35 > var30) {
            var30 = var35;
            var32 = var34;
         }
      }

      var3.hr().add("Summary").hr().add();
      String var50 = "%9d ms %12s seconds)";

      try {
         var3.kv("Total mixin time", var50, var12, var1.format((double)var12 * 0.001D)).add();
         var3.kv("Preparing mixins", var50, var4, var1.format((double)var4 * 0.001D));
         var3.kv("Reading input", var50, var6, var1.format((double)var6 * 0.001D));
         var3.kv("Applying mixins", var50, var8, var1.format((double)var8 * 0.001D));
         var3.kv("Writing output", var50, var10, var1.format((double)var10 * 0.001D)).add();
         var3.kv("of which", "");
         var3.kv("Time spent loading from disk", var50, var14, var1.format((double)var14 * 0.001D));
         var3.kv("Time spent transforming classes", var50, var16, var1.format((double)var16 * 0.001D)).add();
         if (var32 != null) {
            var3.kv("Worst transformer", var32.getName());
            var3.kv("Class", var32.getInfo());
            var3.kv("Time spent", "%s seconds", var32.getTotalSeconds());
            var3.kv("called", "%d times", var32.getTotalCount()).add();
         }
      } catch (Throwable var45) {
         throw b(var45);
      }

      try {
         var3.kv("   Time allocation:     Processing mixins", "%9d ms %10s%% of total)", var20, var2.format(var22));
         var3.kv("Loading classes", "%9d ms %10s%% of total)", var14, var2.format(var24));
         var3.kv("Running transformers", "%9d ms %10s%% of total)", var16, var2.format(var26));
         if (var18 > 0L) {
            var3.kv("Exporting classes (debug)", "%9d ms %10s%% of total)", var18, var2.format(var28));
         }
      } catch (Throwable var48) {
         throw b(var48);
      }

      var3.add();

      try {
         Class var51 = this.service.getClassProvider().findAgentClass("org.spongepowered.metronome.Agent", false);
         Method var52 = var51.getDeclaredMethod("getTimes");
         Map var36 = (Map)var52.invoke((Object)null);
         var3.hr().add("Transformer Times").hr().add();
         int var37 = 10;

         Iterator var38;
         Entry var39;
         for(var38 = var36.entrySet().iterator(); var38.hasNext(); var37 = Math.max(var37, ((String)var39.getKey()).length())) {
            var39 = (Entry)var38.next();
         }

         var38 = var36.entrySet().iterator();

         label69:
         while(true) {
            String var40;
            while(true) {
               if (!var38.hasNext()) {
                  var3.add();
                  break label69;
               }

               var39 = (Entry)var38.next();
               var40 = (String)var39.getKey();
               long var41 = 0L;
               Iterator var43 = this.profiler.getSections().iterator();

               while(var43.hasNext()) {
                  Profiler$Section var44 = (Profiler$Section)var43.next();
                  if (var40.equals(var44.getInfo())) {
                     var41 = var44.getTotalTime();
                     break;
                  }
               }

               try {
                  if (var41 <= 0L) {
                     break;
                  }

                  var3.add("%-" + var37 + "s %8s ms %8s ms in mixin)", var40, (Long)var39.getValue() + var41, "(" + var41);
               } catch (Throwable var46) {
                  throw b(var46);
               }
            }

            var3.add("%-" + var37 + "s %8s ms", var40, var39.getValue());
         }
      } catch (Throwable var47) {
      }

      var3.print();
   }

   public String getName() {
      return this.getClass().getName();
   }

   public boolean isDelegationExcluded() {
      return true;
   }

   public synchronized byte[] transformClassBytes(String param1, String param2, byte[] param3) {
      // $FF: Couldn't be decompiled
   }

   public List<String> reload(String var1, byte[] var2) {
      try {
         if (this.lock.getDepth() > 0) {
            throw new MixinApplyError("Cannot reload mixin if re-entrant lock entered");
         }
      } catch (ExtensionCheckClass$ValidationFailedException var6) {
         throw b(var6);
      }

      ArrayList var3 = new ArrayList();
      Iterator var4 = this.configs.iterator();

      while(var4.hasNext()) {
         MixinConfig var5 = (MixinConfig)var4.next();
         var3.addAll(var5.reloadMixin(var1, var2));
      }

      return var3;
   }

   private void checkSelect(MixinEnvironment param1) {
      // $FF: Couldn't be decompiled
   }

   private void select(MixinEnvironment var1) {
      MixinTransformer var10000;
      Level var10001;
      label40: {
         try {
            var10000 = this;
            if (var1.getOption(MixinEnvironment$Option.DEBUG_VERBOSE)) {
               var10001 = Level.INFO;
               break label40;
            }
         } catch (ExtensionCheckClass$ValidationFailedException var19) {
            throw b(var19);
         }

         var10001 = Level.DEBUG;
      }

      try {
         var10000.verboseLoggingLevel = var10001;
         if (this.transformedCount > 0) {
            logger.log(this.verboseLoggingLevel, "Ending {}, applied {} mixins", new Object[]{this.currentEnvironment, this.transformedCount});
         }
      } catch (ExtensionCheckClass$ValidationFailedException var17) {
         throw b(var17);
      }

      String var20;
      label32: {
         try {
            if (this.currentEnvironment == var1) {
               var20 = "Checking for additional";
               break label32;
            }
         } catch (ExtensionCheckClass$ValidationFailedException var18) {
            throw b(var18);
         }

         var20 = "Preparing";
      }

      String var2 = var20;
      logger.log(this.verboseLoggingLevel, "{} mixins for {}", new Object[]{var2, var1});
      this.profiler.setActive(true);
      this.profiler.mark(var1.getPhase().toString() + ":prepare");
      Profiler$Section var3 = this.profiler.begin("prepare");
      this.selectConfigs(var1);
      this.extensions.select(var1);
      int var4 = this.prepareConfigs(var1);
      this.currentEnvironment = var1;
      this.transformedCount = 0;
      var3.end();
      long var5 = var3.getTime();
      double var7 = var3.getSeconds();
      if (var7 > 0.25D) {
         long var9 = this.profiler.get("class.load").getTime();
         long var11 = this.profiler.get("class.transform").getTime();
         long var13 = this.profiler.get("mixin.plugin").getTime();
         String var15 = (new DecimalFormat("###0.000")).format(var7);
         String var16 = (new DecimalFormat("###0.0")).format((double)var5 / (double)var4);
         logger.log(this.verboseLoggingLevel, "Prepared {} mixins in {} sec ({}ms avg) ({}ms load, {}ms transform, {}ms plugin)", new Object[]{var4, var15, var16, var9, var11, var13});
      }

      this.profiler.mark(var1.getPhase().toString() + ":apply");
      this.profiler.setActive(var1.getOption(MixinEnvironment$Option.DEBUG_PROFILER));
   }

   private void selectConfigs(MixinEnvironment var1) {
      Iterator var2 = Mixins.getConfigs().iterator();

      while(var2.hasNext()) {
         Config var3 = (Config)var2.next();

         try {
            MixinConfig var4 = var3.get();

            try {
               if (var4.select(var1)) {
                  var2.remove();
                  logger.log(this.verboseLoggingLevel, "Selecting config {}", new Object[]{var4});
                  var4.onSelect();
                  this.pendingConfigs.add(var4);
               }
            } catch (Exception var5) {
               throw b(var5);
            }
         } catch (Exception var6) {
            logger.warn(String.format("Failed to select mixin config: %s", var3), var6);
         }
      }

      Collections.sort(this.pendingConfigs);
   }

   private int prepareConfigs(MixinEnvironment var1) {
      int var2 = 0;
      IHotSwap var3 = this.hotSwapper;
      Iterator var4 = this.pendingConfigs.iterator();

      MixinConfig var5;
      while(var4.hasNext()) {
         var5 = (MixinConfig)var4.next();

         try {
            var5.addListener(this.postProcessor);
            if (var3 != null) {
               var5.addListener(new MixinTransformer$1(this, var3));
            }
         } catch (InvalidMixinException var15) {
            throw b(var15);
         }
      }

      var4 = this.pendingConfigs.iterator();

      String var7;
      while(var4.hasNext()) {
         var5 = (MixinConfig)var4.next();

         try {
            logger.log(this.verboseLoggingLevel, "Preparing {} ({})", new Object[]{var5, var5.getDeclaredMixinCount()});
            var5.prepare();
            var2 += var5.getMixinCount();
         } catch (InvalidMixinException var13) {
            this.handleMixinPrepareError(var5, var13, var1);
         } catch (Exception var14) {
            var7 = var14.getMessage();
            logger.error("Error encountered whilst initialising mixin config '" + var5.getName() + "': " + var7, var14);
         }
      }

      var4 = this.pendingConfigs.iterator();

      while(var4.hasNext()) {
         var5 = (MixinConfig)var4.next();
         IMixinConfigPlugin var6 = var5.getPlugin();

         try {
            if (var6 == null) {
               continue;
            }
         } catch (InvalidMixinException var16) {
            throw b(var16);
         }

         HashSet var17 = new HashSet();
         Iterator var8 = this.pendingConfigs.iterator();

         while(var8.hasNext()) {
            MixinConfig var9 = (MixinConfig)var8.next();

            try {
               if (!var9.equals(var5)) {
                  var17.addAll(var9.getTargets());
               }
            } catch (InvalidMixinException var12) {
               throw b(var12);
            }
         }

         var6.acceptTargets(var5.getTargets(), Collections.unmodifiableSet(var17));
      }

      var4 = this.pendingConfigs.iterator();

      while(var4.hasNext()) {
         var5 = (MixinConfig)var4.next();

         try {
            var5.postInitialise();
         } catch (InvalidMixinException var10) {
            this.handleMixinPrepareError(var5, var10, var1);
         } catch (Exception var11) {
            var7 = var11.getMessage();
            logger.error("Error encountered during mixin config postInit step'" + var5.getName() + "': " + var7, var11);
         }
      }

      this.configs.addAll(this.pendingConfigs);
      Collections.sort(this.configs);
      this.pendingConfigs.clear();
      return var2;
   }

   private byte[] applyMixins(MixinEnvironment param1, TargetClassContext param2) {
      // $FF: Couldn't be decompiled
   }

   private void apply(TargetClassContext var1) {
      var1.applyMixins();
   }

   private void handleMixinPrepareError(MixinConfig var1, InvalidMixinException var2, MixinEnvironment var3) throws MixinPrepareError {
      this.handleMixinError(var1.getName(), var2, var3, MixinTransformer$ErrorPhase.PREPARE);
   }

   private void handleMixinApplyError(String var1, InvalidMixinException var2, MixinEnvironment var3) throws MixinApplyError {
      this.handleMixinError(var1, var2, var3, MixinTransformer$ErrorPhase.APPLY);
   }

   private void handleMixinError(String var1, InvalidMixinException var2, MixinEnvironment var3, MixinTransformer$ErrorPhase var4) throws Error {
      this.errorState = true;
      IMixinInfo var5 = var2.getMixin();

      try {
         if (var5 == null) {
            logger.error("InvalidMixinException has no mixin!", var2);
            throw var2;
         }
      } catch (Error var15) {
         throw b(var15);
      }

      IMixinConfig var6 = var5.getConfig();
      MixinEnvironment$Phase var7 = var5.getPhase();

      IMixinErrorHandler$ErrorAction var10000;
      label51: {
         try {
            if (var6.isRequired()) {
               var10000 = IMixinErrorHandler$ErrorAction.ERROR;
               break label51;
            }
         } catch (Error var14) {
            throw b(var14);
         }

         var10000 = IMixinErrorHandler$ErrorAction.WARN;
      }

      IMixinErrorHandler$ErrorAction var8 = var10000;

      try {
         if (var3.getOption(MixinEnvironment$Option.DEBUG_VERBOSE)) {
            (new PrettyPrinter()).add("Invalid Mixin").centre().hr('-').kvWidth(10).kv("Action", var4.name()).kv("Mixin", var5.getClassName()).kv("Config", var6.getName()).kv("Phase", var7).hr('-').add("    %s", var2.getClass().getName()).hr('-').addWrapped("    %s", var2.getMessage()).hr('-').add((Throwable)var2, 8).trace(var8.logLevel);
         }
      } catch (Error var13) {
         throw b(var13);
      }

      Iterator var9 = this.getErrorHandlers(var5.getPhase()).iterator();

      while(var9.hasNext()) {
         IMixinErrorHandler var10 = (IMixinErrorHandler)var9.next();
         IMixinErrorHandler$ErrorAction var11 = var4.onError(var10, var1, var2, var5, var8);
         if (var11 != null) {
            var8 = var11;
         }
      }

      try {
         logger.log(var8.logLevel, var4.getLogMessage(var1, var2, var5), var2);
         this.errorState = false;
         if (var8 == IMixinErrorHandler$ErrorAction.ERROR) {
            throw new MixinApplyError(var4.getErrorMessage(var5, var6, var7), var2);
         }
      } catch (Error var12) {
         throw b(var12);
      }
   }

   private List<IMixinErrorHandler> getErrorHandlers(MixinEnvironment$Phase var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = Mixins.getErrorHandlerClasses().iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();

         try {
            logger.info("Instancing error handler class {}", new Object[]{var4});
            Class var5 = this.service.getClassProvider().findClass(var4, true);
            IMixinErrorHandler var6 = (IMixinErrorHandler)var5.newInstance();

            try {
               if (var6 != null) {
                  var2.add(var6);
               }
            } catch (Throwable var7) {
               throw b(var7);
            }
         } catch (Throwable var8) {
         }
      }

      return var2;
   }

   private byte[] writeClass(TargetClassContext var1) {
      return this.writeClass(var1.getClassName(), var1.getClassNode(), var1.isExportForced());
   }

   private byte[] writeClass(String var1, ClassNode var2, boolean var3) {
      Profiler$Section var4 = this.profiler.begin("write");
      byte[] var5 = this.writeClass(var2);
      var4.end();
      this.extensions.export(this.currentEnvironment, var1, var3, var5);
      return var5;
   }

   private void dumpClassOnFailure(String var1, byte[] var2, MixinEnvironment var3) {
      if (var3.getOption(MixinEnvironment$Option.DUMP_TARGET_ON_FAILURE)) {
         ExtensionClassExporter var4 = (ExtensionClassExporter)this.extensions.getExtension(ExtensionClassExporter.class);
         var4.dumpClass(var1.replace('.', '/') + ".target", var2);
      }

   }

   private static Throwable b(Throwable var0) {
      return var0;
   }
}

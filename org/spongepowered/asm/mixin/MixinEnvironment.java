package org.spongepowered.asm.mixin;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.mixin.extensibility.IEnvironmentTokenProvider;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.MixinTransformer;
import org.spongepowered.asm.obfuscation.RemapperChain;
import org.spongepowered.asm.service.ILegacyClassTransformer;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.ITransformer;
import org.spongepowered.asm.util.ITokenProvider;
import org.spongepowered.asm.util.perf.Profiler;

public final class MixinEnvironment implements ITokenProvider {
   private static final Set<String> excludeTransformers = Sets.newHashSet(new String[]{"net.minecraftforge.fml.common.asm.transformers.EventSubscriptionTransformer", "cpw.mods.fml.common.asm.transformers.EventSubscriptionTransformer", "net.minecraftforge.fml.common.asm.transformers.TerminalTransformer", "cpw.mods.fml.common.asm.transformers.TerminalTransformer"});
   private static MixinEnvironment currentEnvironment;
   private static MixinEnvironment$Phase currentPhase;
   private static MixinEnvironment$CompatibilityLevel compatibility;
   private static boolean showHeader;
   private static final Logger logger;
   private static final Profiler profiler;
   private final IMixinService service;
   private final MixinEnvironment$Phase phase;
   private final String configsKey;
   private final boolean[] options;
   private final Set<String> tokenProviderClasses;
   private final List<MixinEnvironment$TokenProviderWrapper> tokenProviders;
   private final Map<String, Integer> internalTokens;
   private final RemapperChain remappers;
   private MixinEnvironment$Side side;
   private List<ILegacyClassTransformer> transformers;
   private String obfuscationContext;

   MixinEnvironment(MixinEnvironment$Phase param1) {
      // $FF: Couldn't be decompiled
   }

   private void printHeader(Object param1) {
      // $FF: Couldn't be decompiled
   }

   private String getCodeSource() {
      try {
         return this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
      } catch (Throwable var2) {
         return "Unknown";
      }
   }

   public MixinEnvironment$Phase getPhase() {
      return this.phase;
   }

   /** @deprecated */
   @Deprecated
   public List<String> getMixinConfigs() {
      Object var1 = (List)GlobalProperties.get(this.configsKey);
      if (var1 == null) {
         var1 = new ArrayList();
         GlobalProperties.put(this.configsKey, var1);
      }

      return (List)var1;
   }

   /** @deprecated */
   @Deprecated
   public MixinEnvironment addConfiguration(String var1) {
      logger.warn("MixinEnvironment::addConfiguration is deprecated and will be removed. Use Mixins::addConfiguration instead!");
      Mixins.addConfiguration(var1, this);
      return this;
   }

   void registerConfig(String var1) {
      List var2 = this.getMixinConfigs();

      try {
         if (!var2.contains(var1)) {
            var2.add(var1);
         }

      } catch (MixinException var3) {
         throw b(var3);
      }
   }

   /** @deprecated */
   @Deprecated
   public MixinEnvironment registerErrorHandlerClass(String var1) {
      Mixins.registerErrorHandlerClass(var1);
      return this;
   }

   public MixinEnvironment registerTokenProviderClass(String var1) {
      if (!this.tokenProviderClasses.contains(var1)) {
         try {
            Class var2 = this.service.getClassProvider().findClass(var1, true);
            IEnvironmentTokenProvider var3 = (IEnvironmentTokenProvider)var2.newInstance();
            this.registerTokenProvider(var3);
         } catch (Throwable var4) {
            logger.error("Error instantiating " + var1, var4);
         }
      }

      return this;
   }

   public MixinEnvironment registerTokenProvider(IEnvironmentTokenProvider var1) {
      try {
         if (var1 == null || this.tokenProviderClasses.contains(var1.getClass().getName())) {
            return this;
         }
      } catch (MixinException var4) {
         throw b(var4);
      }

      String var2 = var1.getClass().getName();
      MixinEnvironment$TokenProviderWrapper var3 = new MixinEnvironment$TokenProviderWrapper(var1, this);
      logger.info("Adding new token provider {} to {}", new Object[]{var2, this});
      this.tokenProviders.add(var3);
      this.tokenProviderClasses.add(var2);
      Collections.sort(this.tokenProviders);
      return this;
   }

   public Integer getToken(String var1) {
      var1 = var1.toUpperCase();
      Iterator var2 = this.tokenProviders.iterator();

      while(var2.hasNext()) {
         MixinEnvironment$TokenProviderWrapper var3 = (MixinEnvironment$TokenProviderWrapper)var2.next();
         Integer var4 = var3.getToken(var1);

         try {
            if (var4 != null) {
               return var4;
            }
         } catch (MixinException var5) {
            throw b(var5);
         }
      }

      return (Integer)this.internalTokens.get(var1);
   }

   /** @deprecated */
   @Deprecated
   public Set<String> getErrorHandlerClasses() {
      return Mixins.getErrorHandlerClasses();
   }

   public Object getActiveTransformer() {
      return GlobalProperties.get("mixin.transformer");
   }

   public void setActiveTransformer(ITransformer var1) {
      try {
         if (var1 != null) {
            GlobalProperties.put("mixin.transformer", var1);
         }

      } catch (MixinException var2) {
         throw b(var2);
      }
   }

   public MixinEnvironment setSide(MixinEnvironment$Side param1) {
      // $FF: Couldn't be decompiled
   }

   public MixinEnvironment$Side getSide() {
      if (this.side == null) {
         MixinEnvironment$Side[] var1 = MixinEnvironment$Side.values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            MixinEnvironment$Side var4 = var1[var3];

            try {
               if (var4.detect()) {
                  this.side = var4;
                  break;
               }
            } catch (MixinException var6) {
               throw b(var6);
            }
         }
      }

      MixinEnvironment$Side var10000;
      try {
         if (this.side != null) {
            var10000 = this.side;
            return var10000;
         }
      } catch (MixinException var5) {
         throw b(var5);
      }

      var10000 = MixinEnvironment$Side.UNKNOWN;
      return var10000;
   }

   public String getVersion() {
      return (String)GlobalProperties.get("mixin.initialised");
   }

   public boolean getOption(MixinEnvironment$Option var1) {
      return this.options[var1.ordinal()];
   }

   public void setOption(MixinEnvironment$Option var1, boolean var2) {
      this.options[var1.ordinal()] = var2;
   }

   public String getOptionValue(MixinEnvironment$Option var1) {
      return var1.getStringValue();
   }

   public <E extends Enum<E>> E getOption(MixinEnvironment$Option var1, E var2) {
      return var1.getEnumValue(var2);
   }

   public void setObfuscationContext(String var1) {
      this.obfuscationContext = var1;
   }

   public String getObfuscationContext() {
      return this.obfuscationContext;
   }

   public String getRefmapObfuscationContext() {
      String var1 = MixinEnvironment$Option.OBFUSCATION_TYPE.getStringValue();

      try {
         if (var1 != null) {
            return var1;
         }
      } catch (MixinException var2) {
         throw b(var2);
      }

      return this.obfuscationContext;
   }

   public RemapperChain getRemappers() {
      return this.remappers;
   }

   public void audit() {
      Object var1 = this.getActiveTransformer();
      if (var1 instanceof MixinTransformer) {
         MixinTransformer var2 = (MixinTransformer)var1;
         var2.audit(this);
      }

   }

   public List<ILegacyClassTransformer> getTransformers() {
      try {
         if (this.transformers == null) {
            this.buildTransformerDelegationList();
         }
      } catch (MixinException var1) {
         throw b(var1);
      }

      return Collections.unmodifiableList(this.transformers);
   }

   public void addTransformerExclusion(String var1) {
      excludeTransformers.add(var1);
      this.transformers = null;
   }

   private void buildTransformerDelegationList() {
      // $FF: Couldn't be decompiled
   }

   public String toString() {
      return String.format("%s[%s]", this.getClass().getSimpleName(), this.phase);
   }

   private static MixinEnvironment$Phase getCurrentPhase() {
      try {
         if (currentPhase == MixinEnvironment$Phase.NOT_INITIALISED) {
            init(MixinEnvironment$Phase.PREINIT);
         }
      } catch (MixinException var0) {
         throw b(var0);
      }

      return currentPhase;
   }

   public static void init(MixinEnvironment$Phase var0) {
      if (currentPhase == MixinEnvironment$Phase.NOT_INITIALISED) {
         currentPhase = var0;
         MixinEnvironment var1 = getEnvironment(var0);
         getProfiler().setActive(var1.getOption(MixinEnvironment$Option.DEBUG_PROFILER));
         MixinEnvironment$MixinLogWatcher.begin();
      }

   }

   public static MixinEnvironment getEnvironment(MixinEnvironment$Phase var0) {
      try {
         if (var0 == null) {
            return MixinEnvironment$Phase.DEFAULT.getEnvironment();
         }
      } catch (MixinException var1) {
         throw b(var1);
      }

      return var0.getEnvironment();
   }

   public static MixinEnvironment getDefaultEnvironment() {
      return getEnvironment(MixinEnvironment$Phase.DEFAULT);
   }

   public static MixinEnvironment getCurrentEnvironment() {
      try {
         if (currentEnvironment == null) {
            currentEnvironment = getEnvironment(getCurrentPhase());
         }
      } catch (MixinException var0) {
         throw b(var0);
      }

      return currentEnvironment;
   }

   public static MixinEnvironment$CompatibilityLevel getCompatibilityLevel() {
      return compatibility;
   }

   /** @deprecated */
   @Deprecated
   public static void setCompatibilityLevel(MixinEnvironment$CompatibilityLevel param0) throws IllegalArgumentException {
      // $FF: Couldn't be decompiled
   }

   public static Profiler getProfiler() {
      return profiler;
   }

   static void gotoPhase(MixinEnvironment$Phase param0) {
      // $FF: Couldn't be decompiled
   }

   static {
      currentPhase = MixinEnvironment$Phase.NOT_INITIALISED;
      compatibility = (MixinEnvironment$CompatibilityLevel)MixinEnvironment$Option.DEFAULT_COMPATIBILITY_LEVEL.getEnumValue(MixinEnvironment$CompatibilityLevel.JAVA_6);
      showHeader = true;
      logger = LogManager.getLogger("mixin");
      profiler = new Profiler();
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

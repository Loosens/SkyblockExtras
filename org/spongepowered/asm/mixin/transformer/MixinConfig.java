package org.spongepowered.asm.mixin.transformer;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinInitialisationError;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.MixinEnvironment$Option;
import org.spongepowered.asm.mixin.MixinEnvironment$Phase;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.MixinService;

final class MixinConfig implements Comparable<MixinConfig>, IMixinConfig {
   private static int configOrder = 0;
   private static final Set<String> globalMixinList = new HashSet();
   private final Logger logger = LogManager.getLogger("mixin");
   private final transient Map<String, List<MixinInfo>> mixinMapping = new HashMap();
   private final transient Set<String> unhandledTargets = new HashSet();
   private final transient List<MixinInfo> mixins = new ArrayList();
   private transient Config handle;
   @SerializedName("target")
   private String selector;
   @SerializedName("minVersion")
   private String version;
   @SerializedName("compatibilityLevel")
   private String compatibility;
   @SerializedName("required")
   private boolean required;
   @SerializedName("priority")
   private int priority = 1000;
   @SerializedName("mixinPriority")
   private int mixinPriority = 1000;
   @SerializedName("package")
   private String mixinPackage;
   @SerializedName("mixins")
   private List<String> mixinClasses;
   @SerializedName("client")
   private List<String> mixinClassesClient;
   @SerializedName("server")
   private List<String> mixinClassesServer;
   @SerializedName("setSourceFile")
   private boolean setSourceFile = false;
   @SerializedName("refmap")
   private String refMapperConfig;
   @SerializedName("verbose")
   private boolean verboseLogging;
   private final transient int order;
   private final transient List<MixinConfig$IListener> listeners;
   private transient IMixinService service;
   private transient MixinEnvironment env;
   private transient String name;
   @SerializedName("plugin")
   private String pluginClassName;
   @SerializedName("injectors")
   private MixinConfig$InjectorOptions injectorOptions;
   @SerializedName("overwrites")
   private MixinConfig$OverwriteOptions overwriteOptions;
   private transient IMixinConfigPlugin plugin;
   private transient IReferenceMapper refMapper;
   private transient boolean prepared;
   private transient boolean visited;

   private MixinConfig() {
      this.order = configOrder++;
      this.listeners = new ArrayList();
      this.injectorOptions = new MixinConfig$InjectorOptions();
      this.overwriteOptions = new MixinConfig$OverwriteOptions();
      this.prepared = false;
      this.visited = false;
   }

   private boolean onLoad(IMixinService var1, String var2, MixinEnvironment var3) {
      MixinConfig var10000;
      boolean var10001;
      boolean var10002;
      label16: {
         try {
            this.service = var1;
            this.name = var2;
            this.env = this.parseSelector(this.selector, var3);
            var10000 = this;
            var10001 = this.required;
            if (!this.env.getOption(MixinEnvironment$Option.IGNORE_REQUIRED)) {
               var10002 = true;
               break label16;
            }
         } catch (InvalidMixinException var4) {
            throw b(var4);
         }

         var10002 = false;
      }

      var10000.required = var10001 & var10002;
      this.initCompatibilityLevel();
      this.initInjectionPoints();
      return this.checkVersion();
   }

   private void initCompatibilityLevel() {
      // $FF: Couldn't be decompiled
   }

   private MixinEnvironment parseSelector(String var1, MixinEnvironment var2) {
      if (var1 != null) {
         String[] var3 = var1.split("[&\\| ]");
         String[] var4 = var3;
         int var5 = var3.length;
         int var6 = 0;

         while(true) {
            if (var6 >= var5) {
               MixinEnvironment$Phase var12 = MixinEnvironment$Phase.forName(var1);

               try {
                  if (var12 != null) {
                     return MixinEnvironment.getEnvironment(var12);
                  }
                  break;
               } catch (InvalidMixinException var10) {
                  throw b(var10);
               }
            }

            String var7 = var4[var6];
            var7 = var7.trim();
            Pattern var8 = Pattern.compile("^@env(?:ironment)?\\(([A-Z]+)\\)$");
            Matcher var9 = var8.matcher(var7);

            try {
               if (var9.matches()) {
                  return MixinEnvironment.getEnvironment(MixinEnvironment$Phase.forName(var9.group(1)));
               }
            } catch (InvalidMixinException var11) {
               throw b(var11);
            }

            ++var6;
         }
      }

      return var2;
   }

   private void initInjectionPoints() {
      try {
         if (this.injectorOptions.injectionPoints == null) {
            return;
         }
      } catch (Throwable var6) {
         throw b(var6);
      }

      Iterator var1 = this.injectorOptions.injectionPoints.iterator();

      while(var1.hasNext()) {
         String var2 = (String)var1.next();

         try {
            Class var3 = this.service.getClassProvider().findClass(var2, true);

            try {
               if (InjectionPoint.class.isAssignableFrom(var3)) {
                  InjectionPoint.register(var3);
                  continue;
               }
            } catch (Throwable var4) {
               throw b(var4);
            }

            this.logger.error("Unable to register injection point {} for {}, class must extend InjectionPoint", new Object[]{var3, this});
         } catch (Throwable var5) {
            this.logger.catching(var5);
         }
      }

   }

   private boolean checkVersion() throws MixinInitialisationError {
      // $FF: Couldn't be decompiled
   }

   void addListener(MixinConfig$IListener var1) {
      this.listeners.add(var1);
   }

   void onSelect() {
      // $FF: Couldn't be decompiled
   }

   void prepare() {
      // $FF: Couldn't be decompiled
   }

   void postInitialise() {
      if (this.plugin != null) {
         List var1 = this.plugin.getMixins();
         this.prepareMixins(var1, true);
      }

      Iterator var7 = this.mixins.iterator();

      while(var7.hasNext()) {
         MixinInfo var2 = (MixinInfo)var7.next();

         try {
            var2.validate();
            Iterator var3 = this.listeners.iterator();

            while(var3.hasNext()) {
               MixinConfig$IListener var4 = (MixinConfig$IListener)var3.next();
               var4.onInit(var2);
            }
         } catch (InvalidMixinException var5) {
            this.logger.error(var5.getMixin() + ": " + var5.getMessage(), var5);
            this.removeMixin(var2);
            var7.remove();
         } catch (Exception var6) {
            this.logger.error(var6.getMessage(), var6);
            this.removeMixin(var2);
            var7.remove();
         }
      }

   }

   private void removeMixin(MixinInfo var1) {
      Iterator var2 = this.mixinMapping.values().iterator();

      label28:
      while(var2.hasNext()) {
         List var3 = (List)var2.next();
         Iterator var4 = var3.iterator();

         while(true) {
            try {
               do {
                  if (!var4.hasNext()) {
                     continue label28;
                  }
               } while(var1 != var4.next());
            } catch (InvalidMixinException var5) {
               throw b(var5);
            }

            var4.remove();
         }
      }

   }

   private void prepareMixins(List<String> var1, boolean var2) {
      try {
         if (var1 == null) {
            return;
         }
      } catch (InvalidMixinException var15) {
         throw b(var15);
      }

      Iterator var3 = var1.iterator();

      while(true) {
         String var4;
         String var5;
         while(true) {
            do {
               if (!var3.hasNext()) {
                  return;
               }

               var4 = (String)var3.next();
               var5 = this.mixinPackage + var4;
            } while(var4 == null);

            try {
               if (globalMixinList.contains(var5)) {
                  continue;
               }
               break;
            } catch (InvalidMixinException var10) {
               throw b(var10);
            }
         }

         MixinInfo var6 = null;

         try {
            var6 = new MixinInfo(this.service, this, var4, true, this.plugin, var2);
            if (var6.getTargetClasses().size() > 0) {
               globalMixinList.add(var5);
               Iterator var17 = var6.getTargetClasses().iterator();

               while(var17.hasNext()) {
                  String var8 = (String)var17.next();
                  String var9 = var8.replace('/', '.');
                  this.mixinsFor(var9).add(var6);
                  this.unhandledTargets.add(var9);
               }

               var17 = this.listeners.iterator();

               while(var17.hasNext()) {
                  MixinConfig$IListener var18 = (MixinConfig$IListener)var17.next();
                  var18.onPrepare(var6);
               }

               this.mixins.add(var6);
            }
         } catch (InvalidMixinException var13) {
            InvalidMixinException var16 = var13;

            try {
               if (this.required) {
                  throw var16;
               }
            } catch (InvalidMixinException var12) {
               throw b(var12);
            }

            this.logger.error(var13.getMessage(), var13);
         } catch (Exception var14) {
            Exception var7 = var14;

            try {
               if (this.required) {
                  throw new InvalidMixinException(var6, "Error initialising mixin " + var6 + " - " + var7.getClass() + ": " + var7.getMessage(), var7);
               }
            } catch (InvalidMixinException var11) {
               throw b(var11);
            }

            this.logger.error(var14.getMessage(), var14);
         }
      }
   }

   void postApply(String var1, ClassNode var2) {
      this.unhandledTargets.remove(var1);
   }

   public Config getHandle() {
      try {
         if (this.handle == null) {
            this.handle = new Config(this);
         }
      } catch (InvalidMixinException var1) {
         throw b(var1);
      }

      return this.handle;
   }

   public boolean isRequired() {
      return this.required;
   }

   public MixinEnvironment getEnvironment() {
      return this.env;
   }

   public String getName() {
      return this.name;
   }

   public String getMixinPackage() {
      return this.mixinPackage;
   }

   public int getPriority() {
      return this.priority;
   }

   public int getDefaultMixinPriority() {
      return this.mixinPriority;
   }

   public int getDefaultRequiredInjections() {
      return this.injectorOptions.defaultRequireValue;
   }

   public String getDefaultInjectorGroup() {
      // $FF: Couldn't be decompiled
   }

   public boolean conformOverwriteVisibility() {
      return this.overwriteOptions.conformAccessModifiers;
   }

   public boolean requireOverwriteAnnotations() {
      return this.overwriteOptions.requireOverwriteAnnotations;
   }

   public int getMaxShiftByValue() {
      return Math.min(Math.max(this.injectorOptions.maxShiftBy, 0), 5);
   }

   public boolean select(MixinEnvironment var1) {
      boolean var10000;
      try {
         this.visited = true;
         if (this.env == var1) {
            var10000 = true;
            return var10000;
         }
      } catch (InvalidMixinException var2) {
         throw b(var2);
      }

      var10000 = false;
      return var10000;
   }

   boolean isVisited() {
      return this.visited;
   }

   int getDeclaredMixinCount() {
      return getCollectionSize(this.mixinClasses, this.mixinClassesClient, this.mixinClassesServer);
   }

   int getMixinCount() {
      return this.mixins.size();
   }

   public List<String> getClasses() {
      return Collections.unmodifiableList(this.mixinClasses);
   }

   public boolean shouldSetSourceFile() {
      return this.setSourceFile;
   }

   public IReferenceMapper getReferenceMapper() {
      try {
         if (this.env.getOption(MixinEnvironment$Option.DISABLE_REFMAP)) {
            return ReferenceMapper.DEFAULT_MAPPER;
         }
      } catch (InvalidMixinException var1) {
         throw b(var1);
      }

      this.refMapper.setContext(this.env.getRefmapObfuscationContext());
      return this.refMapper;
   }

   String remapClassName(String var1, String var2) {
      return this.getReferenceMapper().remap(var1, var2);
   }

   public IMixinConfigPlugin getPlugin() {
      return this.plugin;
   }

   public Set<String> getTargets() {
      return Collections.unmodifiableSet(this.mixinMapping.keySet());
   }

   public Set<String> getUnhandledTargets() {
      return Collections.unmodifiableSet(this.unhandledTargets);
   }

   public Level getLoggingLevel() {
      Level var10000;
      try {
         if (this.verboseLogging) {
            var10000 = Level.INFO;
            return var10000;
         }
      } catch (InvalidMixinException var1) {
         throw b(var1);
      }

      var10000 = Level.DEBUG;
      return var10000;
   }

   public boolean packageMatch(String var1) {
      return var1.startsWith(this.mixinPackage);
   }

   public boolean hasMixinsFor(String var1) {
      return this.mixinMapping.containsKey(var1);
   }

   public List<MixinInfo> getMixinsFor(String var1) {
      return this.mixinsFor(var1);
   }

   private List<MixinInfo> mixinsFor(String var1) {
      Object var2 = (List)this.mixinMapping.get(var1);
      if (var2 == null) {
         var2 = new ArrayList();
         this.mixinMapping.put(var1, var2);
      }

      return (List)var2;
   }

   public List<String> reloadMixin(String var1, byte[] var2) {
      Iterator var3 = this.mixins.iterator();

      while(var3.hasNext()) {
         MixinInfo var4 = (MixinInfo)var3.next();

         try {
            if (var4.getClassName().equals(var1)) {
               var4.reloadMixin(var2);
               return var4.getTargetClasses();
            }
         } catch (InvalidMixinException var5) {
            throw b(var5);
         }
      }

      return Collections.emptyList();
   }

   public String toString() {
      return this.name;
   }

   public int compareTo(MixinConfig var1) {
      try {
         if (var1 == null) {
            return 0;
         }
      } catch (InvalidMixinException var2) {
         throw b(var2);
      }

      try {
         if (var1.priority == this.priority) {
            return this.order - var1.order;
         }
      } catch (InvalidMixinException var3) {
         throw b(var3);
      }

      return this.priority - var1.priority;
   }

   static Config create(String var0, MixinEnvironment var1) {
      try {
         IMixinService var2 = MixinService.getService();
         MixinConfig var3 = (MixinConfig)(new Gson()).fromJson(new InputStreamReader(var2.getResourceAsStream(var0)), MixinConfig.class);
         return var3.onLoad(var2, var0, var1) ? var3.getHandle() : null;
      } catch (Exception var4) {
         var4.printStackTrace();
         throw new IllegalArgumentException(String.format("The specified resource '%s' was invalid or could not be read", var0), var4);
      }
   }

   private static int getCollectionSize(Collection<?>... var0) {
      int var1 = 0;
      Collection[] var2 = var0;
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Collection var5 = var2[var4];
         if (var5 != null) {
            var1 += var5.size();
         }
      }

      return var1;
   }

   private static Throwable b(Throwable var0) {
      return var0;
   }
}

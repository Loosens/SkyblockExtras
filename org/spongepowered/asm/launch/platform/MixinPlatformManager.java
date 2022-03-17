package org.spongepowered.asm.launch.platform;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.MixinEnvironment$CompatibilityLevel;
import org.spongepowered.asm.mixin.MixinEnvironment$Phase;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.service.MixinService;

public class MixinPlatformManager {
   private static final String DEFAULT_MAIN_CLASS = "net.minecraft.client.main.Main";
   private static final String MIXIN_TWEAKER_CLASS = "org.spongepowered.asm.launch.MixinTweaker";
   private static final Logger logger = LogManager.getLogger("mixin");
   private final Map<URI, MixinContainer> containers = new LinkedHashMap();
   private MixinContainer primaryContainer;
   private boolean prepared = false;
   private boolean injected;

   public void init() {
      logger.debug("Initialising Mixin Platform Manager");
      URI var1 = null;

      try {
         var1 = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI();

         try {
            if (var1 != null) {
               logger.debug("Mixin platform: primary container is {}", new Object[]{var1});
               this.primaryContainer = this.addContainer(var1);
            }
         } catch (URISyntaxException var3) {
            throw b(var3);
         }
      } catch (URISyntaxException var4) {
         var4.printStackTrace();
      }

      this.scanClasspath();
   }

   public Collection<String> getPhaseProviderClasses() {
      Collection var1 = this.primaryContainer.getPhaseProviders();

      try {
         if (var1 != null) {
            return Collections.unmodifiableCollection(var1);
         }
      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }

      return Collections.emptyList();
   }

   public final MixinContainer addContainer(URI var1) {
      MixinContainer var2 = (MixinContainer)this.containers.get(var1);

      try {
         if (var2 != null) {
            return var2;
         }
      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }

      logger.debug("Adding mixin platform agents for container {}", new Object[]{var1});
      MixinContainer var3 = new MixinContainer(this, var1);

      try {
         this.containers.put(var1, var3);
         if (this.prepared) {
            var3.prepare();
         }

         return var3;
      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }
   }

   public final void prepare(List<String> var1) {
      this.prepared = true;
      Iterator var2 = this.containers.values().iterator();

      while(var2.hasNext()) {
         MixinContainer var3 = (MixinContainer)var2.next();
         var3.prepare();
      }

      try {
         if (var1 != null) {
            this.parseArgs(var1);
            return;
         }
      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }

      String var6 = System.getProperty("sun.java.command");

      try {
         if (var6 != null) {
            this.parseArgs(Arrays.asList(var6.split(" ")));
         }
      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }

   }

   private void parseArgs(List<String> var1) {
      boolean var2 = false;

      String var4;
      for(Iterator var3 = var1.iterator(); var3.hasNext(); var2 = "--mixin".equals(var4)) {
         var4 = (String)var3.next();

         try {
            if (var2) {
               this.addConfig(var4);
            }
         } catch (IllegalArgumentException var5) {
            throw b(var5);
         }
      }

   }

   public final void inject() {
      try {
         if (this.injected) {
            return;
         }
      } catch (Exception var5) {
         throw b(var5);
      }

      try {
         this.injected = true;
         if (this.primaryContainer != null) {
            this.primaryContainer.initPrimaryContainer();
         }
      } catch (Exception var6) {
         throw b(var6);
      }

      this.scanClasspath();
      logger.debug("inject() running with {} agents", new Object[]{this.containers.size()});
      Iterator var1 = this.containers.values().iterator();

      while(var1.hasNext()) {
         MixinContainer var2 = (MixinContainer)var1.next();

         try {
            var2.inject();
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

   }

   private void scanClasspath() {
      URL[] var1 = MixinService.getService().getClassProvider().getClassPath();
      URL[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         URL var5 = var2[var4];

         try {
            URI var6 = var5.toURI();
            if (!this.containers.containsKey(var6)) {
               logger.debug("Scanning {} for mixin tweaker", new Object[]{var6});
               if ("file".equals(var6.getScheme()) && (new File(var6)).exists()) {
                  MainAttributes var7 = MainAttributes.of(var6);
                  String var8 = var7.get("TweakClass");

                  try {
                     if ("org.spongepowered.asm.launch.MixinTweaker".equals(var8)) {
                        logger.debug("{} contains a mixin tweaker, adding agents", new Object[]{var6});
                        this.addContainer(var6);
                     }
                  } catch (Exception var9) {
                     throw b(var9);
                  }
               }
            }
         } catch (Exception var10) {
            var10.printStackTrace();
         }
      }

   }

   public String getLaunchTarget() {
      Iterator var1 = this.containers.values().iterator();

      while(var1.hasNext()) {
         MixinContainer var2 = (MixinContainer)var1.next();
         String var3 = var2.getLaunchTarget();

         try {
            if (var3 != null) {
               return var3;
            }
         } catch (IllegalArgumentException var4) {
            throw b(var4);
         }
      }

      return "net.minecraft.client.main.Main";
   }

   final void setCompatibilityLevel(String var1) {
      try {
         MixinEnvironment$CompatibilityLevel var2 = MixinEnvironment$CompatibilityLevel.valueOf(var1.toUpperCase());
         logger.debug("Setting mixin compatibility level: {}", new Object[]{var2});
         MixinEnvironment.setCompatibilityLevel(var2);
      } catch (IllegalArgumentException var3) {
         logger.warn("Invalid compatibility level specified: {}", new Object[]{var1});
      }

   }

   final void addConfig(String var1) {
      try {
         if (var1.endsWith(".json")) {
            logger.debug("Registering mixin config: {}", new Object[]{var1});
            Mixins.addConfiguration(var1);
            return;
         }
      } catch (IllegalArgumentException var6) {
         throw b(var6);
      }

      if (var1.contains(".json@")) {
         int var2 = var1.indexOf(".json@");
         String var3 = var1.substring(var2 + 6);
         var1 = var1.substring(0, var2 + 5);
         MixinEnvironment$Phase var4 = MixinEnvironment$Phase.forName(var3);

         try {
            if (var4 != null) {
               logger.warn("Setting config phase via manifest is deprecated: {}. Specify target in config instead", new Object[]{var1});
               logger.debug("Registering mixin config: {}", new Object[]{var1});
               MixinEnvironment.getEnvironment(var4).addConfiguration(var1);
            }
         } catch (IllegalArgumentException var5) {
            throw b(var5);
         }
      }

   }

   final void addTokenProvider(String var1) {
      if (var1.contains("@")) {
         String[] var2 = var1.split("@", 2);
         MixinEnvironment$Phase var3 = MixinEnvironment$Phase.forName(var2[1]);

         try {
            if (var3 != null) {
               logger.debug("Registering token provider class: {}", new Object[]{var2[0]});
               MixinEnvironment.getEnvironment(var3).registerTokenProviderClass(var2[0]);
            }

         } catch (IllegalArgumentException var4) {
            throw b(var4);
         }
      } else {
         MixinEnvironment.getDefaultEnvironment().registerTokenProviderClass(var1);
      }
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

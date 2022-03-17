package org.spongepowered.asm.launch;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.platform.MixinPlatformManager;
import org.spongepowered.asm.service.MixinService;

public abstract class MixinBootstrap {
   public static final String VERSION = "0.7.11";
   private static final Logger logger = LogManager.getLogger("mixin");
   private static boolean initialised = false;
   private static boolean initState = true;
   private static MixinPlatformManager platform;

   private MixinBootstrap() {
   }

   /** @deprecated */
   @Deprecated
   public static void addProxy() {
      MixinService.getService().beginPhase();
   }

   public static MixinPlatformManager getPlatform() {
      if (platform == null) {
         Object var0 = GlobalProperties.get("mixin.platform");

         try {
            if (var0 instanceof MixinPlatformManager) {
               platform = (MixinPlatformManager)var0;
               return platform;
            }
         } catch (IllegalStateException var1) {
            throw b(var1);
         }

         platform = new MixinPlatformManager();
         GlobalProperties.put("mixin.platform", platform);
         platform.init();
      }

      return platform;
   }

   public static void init() {
      try {
         if (!start()) {
            return;
         }
      } catch (IllegalStateException var0) {
         throw b(var0);
      }

      doInit((List)null);
   }

   static boolean start() {
      // $FF: Couldn't be decompiled
   }

   static void doInit(List<String> param0) {
      // $FF: Couldn't be decompiled
   }

   static void inject() {
      getPlatform().inject();
   }

   private static boolean isSubsystemRegistered() {
      boolean var10000;
      try {
         if (GlobalProperties.get("mixin.initialised") != null) {
            var10000 = true;
            return var10000;
         }
      } catch (IllegalStateException var0) {
         throw b(var0);
      }

      var10000 = false;
      return var10000;
   }

   private static boolean checkSubsystemVersion() {
      return "0.7.11".equals(getActiveSubsystemVersion());
   }

   private static Object getActiveSubsystemVersion() {
      Object var0 = GlobalProperties.get("mixin.initialised");

      Object var10000;
      try {
         if (var0 != null) {
            var10000 = var0;
            return var10000;
         }
      } catch (IllegalStateException var1) {
         throw b(var1);
      }

      var10000 = "";
      return var10000;
   }

   private static void registerSubsystem(String var0) {
      GlobalProperties.put("mixin.initialised", var0);
   }

   static {
      MixinService.boot();
      MixinService.getService().prepare();
   }

   private static IllegalStateException b(IllegalStateException var0) {
      return var0;
   }
}

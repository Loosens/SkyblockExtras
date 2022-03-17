package org.spongepowered.asm.mixin;

import org.spongepowered.asm.util.JavaVersion;

enum MixinEnvironment$CompatibilityLevel$2 {
   MixinEnvironment$CompatibilityLevel$2(int var3, int var4, boolean var5) {
   }

   boolean isSupported() {
      boolean var10000;
      try {
         if (JavaVersion.current() >= 1.8D) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw c(var1);
      }

      var10000 = false;
      return var10000;
   }

   private static RuntimeException c(RuntimeException var0) {
      return var0;
   }
}

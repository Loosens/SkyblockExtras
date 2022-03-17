package org.spongepowered.asm.launch.platform;

import java.net.URI;

public class MixinPlatformAgentDefault extends MixinPlatformAgentAbstract {
   public MixinPlatformAgentDefault(MixinPlatformManager var1, URI var2) {
      super(var1, var2);
   }

   public void prepare() {
      String var1 = this.attributes.get("MixinCompatibilityLevel");

      try {
         if (var1 != null) {
            this.manager.setCompatibilityLevel(var1);
         }
      } catch (RuntimeException var8) {
         throw b(var8);
      }

      String var2 = this.attributes.get("MixinConfigs");
      int var5;
      if (var2 != null) {
         String[] var3 = var2.split(",");
         int var4 = var3.length;

         for(var5 = 0; var5 < var4; ++var5) {
            String var6 = var3[var5];
            this.manager.addConfig(var6.trim());
         }
      }

      String var9 = this.attributes.get("MixinTokenProviders");
      if (var9 != null) {
         String[] var10 = var9.split(",");
         var5 = var10.length;

         for(int var11 = 0; var11 < var5; ++var11) {
            String var7 = var10[var11];
            this.manager.addTokenProvider(var7.trim());
         }
      }

   }

   public void initPrimaryContainer() {
   }

   public void inject() {
   }

   public String getLaunchTarget() {
      return this.attributes.get("Main-Class");
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

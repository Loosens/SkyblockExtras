package org.spongepowered.asm.mixin;

import org.spongepowered.asm.mixin.extensibility.IEnvironmentTokenProvider;

class MixinEnvironment$TokenProviderWrapper implements Comparable<MixinEnvironment$TokenProviderWrapper> {
   private static int nextOrder = 0;
   private final int priority;
   private final int order;
   private final IEnvironmentTokenProvider provider;
   private final MixinEnvironment environment;

   public MixinEnvironment$TokenProviderWrapper(IEnvironmentTokenProvider var1, MixinEnvironment var2) {
      this.provider = var1;
      this.environment = var2;
      this.order = nextOrder++;
      this.priority = var1.getPriority();
   }

   public int compareTo(MixinEnvironment$TokenProviderWrapper var1) {
      try {
         if (var1 == null) {
            return 0;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      try {
         if (var1.priority == this.priority) {
            return var1.order - this.order;
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      return var1.priority - this.priority;
   }

   public IEnvironmentTokenProvider getProvider() {
      return this.provider;
   }

   Integer getToken(String var1) {
      return this.provider.getToken(var1, this.environment);
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

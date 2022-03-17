package org.spongepowered.tools.obfuscation;

import org.spongepowered.asm.util.ObfuscationUtil$IClassRemapper;

final class ObfuscationEnvironment$RemapperProxy implements ObfuscationUtil$IClassRemapper {
   final ObfuscationEnvironment this$0;

   ObfuscationEnvironment$RemapperProxy(ObfuscationEnvironment var1) {
      this.this$0 = var1;
   }

   public String map(String var1) {
      try {
         if (this.this$0.mappingProvider == null) {
            return null;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      return this.this$0.mappingProvider.getClassMapping(var1);
   }

   public String unmap(String var1) {
      try {
         if (this.this$0.mappingProvider == null) {
            return null;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      return this.this$0.mappingProvider.getClassMapping(var1);
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

package org.spongepowered.asm.util;

class ClassSignature$Lazy extends ClassSignature {
   private final String sig;
   private ClassSignature generated;

   ClassSignature$Lazy(String var1) {
      this.sig = var1;
   }

   public ClassSignature wake() {
      try {
         if (this.generated == null) {
            this.generated = ClassSignature.of(this.sig);
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      return this.generated;
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

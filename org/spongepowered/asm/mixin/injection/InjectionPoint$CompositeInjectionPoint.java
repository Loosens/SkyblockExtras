package org.spongepowered.asm.mixin.injection;

import com.google.common.base.Joiner;

abstract class InjectionPoint$CompositeInjectionPoint extends InjectionPoint {
   protected final InjectionPoint[] components;

   protected InjectionPoint$CompositeInjectionPoint(InjectionPoint... var1) {
      label26: {
         super();
         IllegalArgumentException var10000;
         boolean var10001;
         if (var1 != null) {
            try {
               if (var1.length >= 2) {
                  break label26;
               }
            } catch (IllegalArgumentException var3) {
               var10000 = var3;
               var10001 = false;
               throw b(var10000);
            }
         }

         try {
            throw new IllegalArgumentException("Must supply two or more component injection points for composite point!");
         } catch (IllegalArgumentException var2) {
            var10000 = var2;
            var10001 = false;
            throw b(var10000);
         }
      }

      this.components = var1;
   }

   public String toString() {
      return "CompositeInjectionPoint(" + this.getClass().getSimpleName() + ")[" + Joiner.on(',').join(this.components) + "]";
   }

   private static IllegalArgumentException b(IllegalArgumentException var0) {
      return var0;
   }
}

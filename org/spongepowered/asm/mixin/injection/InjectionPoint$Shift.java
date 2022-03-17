package org.spongepowered.asm.mixin.injection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;

final class InjectionPoint$Shift extends InjectionPoint {
   private final InjectionPoint input;
   private final int shift;

   public InjectionPoint$Shift(InjectionPoint var1, int var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("Must supply an input injection point for SHIFT");
      } else {
         this.input = var1;
         this.shift = var2;
      }
   }

   public String toString() {
      return "InjectionPoint(" + this.getClass().getSimpleName() + ")[" + this.input + "]";
   }

   public boolean find(String var1, InsnList var2, Collection<AbstractInsnNode> var3) {
      Object var10000;
      label48: {
         try {
            if (var3 instanceof List) {
               var10000 = (List)var3;
               break label48;
            }
         } catch (IllegalArgumentException var9) {
            throw b(var9);
         }

         var10000 = new ArrayList(var3);
      }

      Object var4 = var10000;
      this.input.find(var1, var2, var3);
      int var5 = 0;

      try {
         while(var5 < ((List)var4).size()) {
            ((List)var4).set(var5, var2.get(var2.indexOf((AbstractInsnNode)((List)var4).get(var5)) + this.shift));
            ++var5;
         }
      } catch (IllegalArgumentException var8) {
         throw b(var8);
      }

      try {
         if (var3 != var4) {
            var3.clear();
            var3.addAll((Collection)var4);
         }
      } catch (IllegalArgumentException var6) {
         throw b(var6);
      }

      boolean var10;
      try {
         if (var3.size() > 0) {
            var10 = true;
            return var10;
         }
      } catch (IllegalArgumentException var7) {
         throw b(var7);
      }

      var10 = false;
      return var10;
   }

   private static IllegalArgumentException b(IllegalArgumentException var0) {
      return var0;
   }
}

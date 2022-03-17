package org.spongepowered.asm.mixin.injection;

import java.util.Collection;
import java.util.LinkedHashSet;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;

final class InjectionPoint$Union extends InjectionPoint$CompositeInjectionPoint {
   public InjectionPoint$Union(InjectionPoint... var1) {
      super(var1);
   }

   public boolean find(String var1, InsnList var2, Collection<AbstractInsnNode> var3) {
      LinkedHashSet var4 = new LinkedHashSet();
      int var5 = 0;

      try {
         while(var5 < this.components.length) {
            this.components[var5].find(var1, var2, var4);
            ++var5;
         }
      } catch (RuntimeException var7) {
         throw b(var7);
      }

      boolean var10000;
      try {
         var3.addAll(var4);
         if (var4.size() > 0) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var6) {
         throw b(var6);
      }

      var10000 = false;
      return var10000;
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

package org.spongepowered.asm.mixin.injection.struct;

import java.util.ArrayList;
import java.util.Iterator;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;

public class InjectionNodes extends ArrayList<InjectionNodes$InjectionNode> {
   private static final long serialVersionUID = 1L;

   public InjectionNodes$InjectionNode add(AbstractInsnNode var1) {
      InjectionNodes$InjectionNode var2 = this.get(var1);
      if (var2 == null) {
         var2 = new InjectionNodes$InjectionNode(var1);
         this.add(var2);
      }

      return var2;
   }

   public InjectionNodes$InjectionNode get(AbstractInsnNode var1) {
      Iterator var2 = this.iterator();

      while(var2.hasNext()) {
         InjectionNodes$InjectionNode var3 = (InjectionNodes$InjectionNode)var2.next();

         try {
            if (var3.matches(var1)) {
               return var3;
            }
         } catch (RuntimeException var4) {
            throw b(var4);
         }
      }

      return null;
   }

   public boolean contains(AbstractInsnNode var1) {
      boolean var10000;
      try {
         if (this.get(var1) != null) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      var10000 = false;
      return var10000;
   }

   public void replace(AbstractInsnNode var1, AbstractInsnNode var2) {
      InjectionNodes$InjectionNode var3 = this.get(var1);

      try {
         if (var3 != null) {
            var3.replace(var2);
         }

      } catch (RuntimeException var4) {
         throw b(var4);
      }
   }

   public void remove(AbstractInsnNode var1) {
      InjectionNodes$InjectionNode var2 = this.get(var1);

      try {
         if (var2 != null) {
            var2.remove();
         }

      } catch (RuntimeException var3) {
         throw b(var3);
      }
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

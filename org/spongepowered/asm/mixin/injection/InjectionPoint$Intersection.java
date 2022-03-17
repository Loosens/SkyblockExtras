package org.spongepowered.asm.mixin.injection;

import java.util.Collection;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;

final class InjectionPoint$Intersection extends InjectionPoint$CompositeInjectionPoint {
   public InjectionPoint$Intersection(InjectionPoint... var1) {
      super(var1);
   }

   public boolean find(String param1, InsnList param2, Collection<AbstractInsnNode> param3) {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

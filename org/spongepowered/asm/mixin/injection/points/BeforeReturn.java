package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.InjectionPoint$AtCode;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

@InjectionPoint$AtCode("RETURN")
public class BeforeReturn extends InjectionPoint {
   private final int ordinal;

   public BeforeReturn(InjectionPointData var1) {
      super(var1);
      this.ordinal = var1.getOrdinal();
   }

   public boolean checkPriority(int var1, int var2) {
      return true;
   }

   public boolean find(String param1, InsnList param2, Collection<AbstractInsnNode> param3) {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

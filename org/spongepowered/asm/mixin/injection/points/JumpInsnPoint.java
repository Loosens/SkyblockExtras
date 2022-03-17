package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.InjectionPoint$AtCode;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

@InjectionPoint$AtCode("JUMP")
public class JumpInsnPoint extends InjectionPoint {
   private final int opCode;
   private final int ordinal;

   public JumpInsnPoint(InjectionPointData var1) {
      this.opCode = var1.getOpcode(-1, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 198, 199, -1);
      this.ordinal = var1.getOrdinal();
   }

   public boolean find(String param1, InsnList param2, Collection<AbstractInsnNode> param3) {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

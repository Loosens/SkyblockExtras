package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collection;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint$AtCode;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.Target;

@InjectionPoint$AtCode("LOAD")
public class BeforeLoadLocal extends ModifyVariableInjector$ContextualInjectionPoint {
   private final Type returnType;
   private final LocalVariableDiscriminator discriminator;
   private final int opcode;
   private final int ordinal;
   private boolean opcodeAfter;

   protected BeforeLoadLocal(InjectionPointData var1) {
      this(var1, 21, false);
   }

   protected BeforeLoadLocal(InjectionPointData var1, int var2, boolean var3) {
      super(var1.getContext());
      this.returnType = var1.getMethodReturnType();
      this.discriminator = var1.getLocalVariableDiscriminator();
      this.opcode = var1.getOpcode(this.returnType.getOpcode(var2));
      this.ordinal = var1.getOrdinal();
      this.opcodeAfter = var3;
   }

   boolean find(Target param1, Collection<AbstractInsnNode> param2) {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.mixin.injection.struct.Target;

class ModifyVariableInjector$Context extends LocalVariableDiscriminator$Context {
   final InsnList insns = new InsnList();

   public ModifyVariableInjector$Context(Type var1, boolean var2, Target var3, AbstractInsnNode var4) {
      super(var1, var2, var3, var4);
   }
}

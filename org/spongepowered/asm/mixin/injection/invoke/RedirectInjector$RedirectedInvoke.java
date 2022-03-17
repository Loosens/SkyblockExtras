package org.spongepowered.asm.mixin.injection.invoke;

import com.google.common.collect.ObjectArrays;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.injection.struct.Target;

class RedirectInjector$RedirectedInvoke {
   final Target target;
   final MethodInsnNode node;
   final Type returnType;
   final Type[] args;
   final Type[] locals;
   boolean captureTargetArgs = false;

   RedirectInjector$RedirectedInvoke(Target var1, MethodInsnNode var2) {
      this.target = var1;
      this.node = var2;
      this.returnType = Type.getReturnType(var2.desc);
      this.args = Type.getArgumentTypes(var2.desc);
      this.locals = var2.getOpcode() == 184 ? this.args : (Type[])ObjectArrays.concat(Type.getType("L" + var2.owner + ";"), this.args);
   }
}

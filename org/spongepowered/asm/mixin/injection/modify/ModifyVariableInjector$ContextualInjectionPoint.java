package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collection;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

abstract class ModifyVariableInjector$ContextualInjectionPoint extends InjectionPoint {
   protected final IMixinContext context;

   ModifyVariableInjector$ContextualInjectionPoint(IMixinContext var1) {
      this.context = var1;
   }

   public boolean find(String var1, InsnList var2, Collection<AbstractInsnNode> var3) {
      throw new InvalidInjectionException(this.context, this.getAtCode() + " injection point must be used in conjunction with @ModifyVariable");
   }

   abstract boolean find(Target var1, Collection<AbstractInsnNode> var2);
}

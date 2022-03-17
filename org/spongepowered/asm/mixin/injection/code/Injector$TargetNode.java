package org.spongepowered.asm.mixin.injection.code;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;

public final class Injector$TargetNode {
   final AbstractInsnNode insn;
   final Set<InjectionPoint> nominators = new HashSet();

   Injector$TargetNode(AbstractInsnNode var1) {
      this.insn = var1;
   }

   public AbstractInsnNode getNode() {
      return this.insn;
   }

   public Set<InjectionPoint> getNominators() {
      return Collections.unmodifiableSet(this.nominators);
   }

   public boolean equals(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public int hashCode() {
      return this.insn.hashCode();
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

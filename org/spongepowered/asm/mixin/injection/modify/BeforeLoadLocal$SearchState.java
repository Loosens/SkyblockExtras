package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collection;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;

class BeforeLoadLocal$SearchState {
   private final boolean print;
   private final int targetOrdinal;
   private int ordinal = 0;
   private boolean pendingCheck = false;
   private boolean found = false;
   private VarInsnNode varNode;

   BeforeLoadLocal$SearchState(int var1, boolean var2) {
      this.targetOrdinal = var1;
      this.print = var2;
   }

   boolean success() {
      return this.found;
   }

   boolean isPendingCheck() {
      return this.pendingCheck;
   }

   void setPendingCheck() {
      this.pendingCheck = true;
   }

   void register(VarInsnNode var1) {
      this.varNode = var1;
   }

   void check(Collection<AbstractInsnNode> param1, AbstractInsnNode param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

package org.spongepowered.asm.mixin.injection.invoke.util;

import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.analysis.AnalyzerException;
import org.spongepowered.asm.lib.tree.analysis.BasicValue;
import org.spongepowered.asm.lib.tree.analysis.Frame;
import org.spongepowered.asm.lib.tree.analysis.Interpreter;

class InsnFinder$PopAnalyzer$PopFrame extends Frame<BasicValue> {
   private AbstractInsnNode current;
   private InsnFinder$AnalyzerState state;
   private int depth;
   final InsnFinder$PopAnalyzer this$0;

   public InsnFinder$PopAnalyzer$PopFrame(InsnFinder$PopAnalyzer var1, int var2, int var3) {
      super(var2, var3);
      this.this$0 = var1;
      this.state = InsnFinder$AnalyzerState.SEARCH;
      this.depth = 0;
   }

   public void execute(AbstractInsnNode var1, Interpreter<BasicValue> var2) throws AnalyzerException {
      this.current = var1;
      super.execute(var1, var2);
   }

   public void push(BasicValue param1) throws IndexOutOfBoundsException {
      // $FF: Couldn't be decompiled
   }

   public BasicValue pop() throws IndexOutOfBoundsException {
      // $FF: Couldn't be decompiled
   }

   private static IndexOutOfBoundsException b(IndexOutOfBoundsException var0) {
      return var0;
   }
}

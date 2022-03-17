package org.spongepowered.asm.mixin.injection.invoke.util;

import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.analysis.Analyzer;
import org.spongepowered.asm.lib.tree.analysis.BasicInterpreter;
import org.spongepowered.asm.lib.tree.analysis.BasicValue;
import org.spongepowered.asm.lib.tree.analysis.Frame;

class InsnFinder$PopAnalyzer extends Analyzer<BasicValue> {
   protected final AbstractInsnNode node;

   public InsnFinder$PopAnalyzer(AbstractInsnNode var1) {
      super(new BasicInterpreter());
      this.node = var1;
   }

   protected Frame<BasicValue> newFrame(int var1, int var2) {
      return new InsnFinder$PopAnalyzer$PopFrame(this, var1, var2);
   }
}

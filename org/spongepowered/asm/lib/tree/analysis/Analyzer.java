package org.spongepowered.asm.lib.tree.analysis;

import java.util.List;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TryCatchBlockNode;

public class Analyzer<V extends Value> implements Opcodes {
   private final Interpreter<V> interpreter;
   private int n;
   private InsnList insns;
   private List<TryCatchBlockNode>[] handlers;
   private Frame<V>[] frames;
   private Subroutine[] subroutines;
   private boolean[] queued;
   private int[] queue;
   private int top;

   public Analyzer(Interpreter<V> var1) {
      this.interpreter = var1;
   }

   public Frame<V>[] analyze(String param1, MethodNode param2) throws AnalyzerException {
      // $FF: Couldn't be decompiled
   }

   private void findSubroutine(int param1, Subroutine param2, List<AbstractInsnNode> param3) throws AnalyzerException {
      // $FF: Couldn't be decompiled
   }

   public Frame<V>[] getFrames() {
      return this.frames;
   }

   public List<TryCatchBlockNode> getHandlers(int var1) {
      return this.handlers[var1];
   }

   protected void init(String var1, MethodNode var2) throws AnalyzerException {
   }

   protected Frame<V> newFrame(int var1, int var2) {
      return new Frame(var1, var2);
   }

   protected Frame<V> newFrame(Frame<? extends V> var1) {
      return new Frame(var1);
   }

   protected void newControlFlowEdge(int var1, int var2) {
   }

   protected boolean newControlFlowExceptionEdge(int var1, int var2) {
      return true;
   }

   protected boolean newControlFlowExceptionEdge(int var1, TryCatchBlockNode var2) {
      return this.newControlFlowExceptionEdge(var1, this.insns.indexOf(var2.handler));
   }

   private void merge(int param1, Frame<V> param2, Subroutine param3) throws AnalyzerException {
      // $FF: Couldn't be decompiled
   }

   private void merge(int param1, Frame<V> param2, Frame<V> param3, Subroutine param4, boolean[] param5) throws AnalyzerException {
      // $FF: Couldn't be decompiled
   }

   private static AnalyzerException b(AnalyzerException var0) {
      return var0;
   }
}

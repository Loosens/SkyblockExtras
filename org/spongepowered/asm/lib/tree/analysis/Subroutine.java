package org.spongepowered.asm.lib.tree.analysis;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;

class Subroutine {
   LabelNode start;
   boolean[] access;
   List<JumpInsnNode> callers;

   private Subroutine() {
   }

   Subroutine(LabelNode var1, int var2, JumpInsnNode var3) {
      this.start = var1;
      this.access = new boolean[var2];
      this.callers = new ArrayList();
      this.callers.add(var3);
   }

   public Subroutine copy() {
      Subroutine var1 = new Subroutine();
      var1.start = this.start;
      var1.access = new boolean[this.access.length];
      System.arraycopy(this.access, 0, var1.access, 0, this.access.length);
      var1.callers = new ArrayList(this.callers);
      return var1;
   }

   public boolean merge(Subroutine param1) throws AnalyzerException {
      // $FF: Couldn't be decompiled
   }

   private static AnalyzerException b(AnalyzerException var0) {
      return var0;
   }
}

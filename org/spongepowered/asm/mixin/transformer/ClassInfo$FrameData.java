package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.tree.FrameNode;

public class ClassInfo$FrameData {
   private static final String[] FRAMETYPES = new String[]{"NEW", "FULL", "APPEND", "CHOP", "SAME", "SAME1"};
   public final int index;
   public final int type;
   public final int locals;

   ClassInfo$FrameData(int var1, int var2, int var3) {
      this.index = var1;
      this.type = var2;
      this.locals = var3;
   }

   ClassInfo$FrameData(int var1, FrameNode var2) {
      this.index = var1;
      this.type = var2.type;
      this.locals = var2.local != null ? var2.local.size() : 0;
   }

   public String toString() {
      return String.format("FrameData[index=%d, type=%s, locals=%d]", this.index, FRAMETYPES[this.type + 1], this.locals);
   }
}

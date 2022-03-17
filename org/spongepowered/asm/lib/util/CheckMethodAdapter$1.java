package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.tree.MethodNode;

class CheckMethodAdapter$1 extends MethodNode {
   final MethodVisitor val$cmv;

   CheckMethodAdapter$1(int var1, int var2, String var3, String var4, String var5, String[] var6, MethodVisitor var7) {
      super(var1, var2, var3, var4, var5, var6);
      this.val$cmv = var7;
   }

   public void visitEnd() {
      // $FF: Couldn't be decompiled
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

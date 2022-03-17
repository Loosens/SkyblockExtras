package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.MethodVisitor;

class MixinPostProcessor$1 extends ClassVisitor {
   final MixinPostProcessor this$0;

   MixinPostProcessor$1(MixinPostProcessor var1, int var2, ClassVisitor var3) {
      super(var2, var3);
      this.this$0 = var1;
   }

   public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      super.visit(var1, var2 | 1, var3, var4, var5, var6);
   }

   public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      if ((var1 & 6) == 0) {
         var1 |= 1;
      }

      return super.visitField(var1, var2, var3, var4, var5);
   }

   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      if ((var1 & 6) == 0) {
         var1 |= 1;
      }

      return super.visitMethod(var1, var2, var3, var4, var5);
   }
}

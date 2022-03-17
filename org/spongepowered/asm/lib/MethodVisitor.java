package org.spongepowered.asm.lib;

public abstract class MethodVisitor {
   protected final int api;
   protected MethodVisitor mv;

   public MethodVisitor(int var1) {
      this(var1, (MethodVisitor)null);
   }

   public MethodVisitor(int var1, MethodVisitor var2) {
      if (var1 != 262144) {
         try {
            if (var1 != 327680) {
               throw new IllegalArgumentException();
            }
         } catch (IllegalArgumentException var3) {
            throw b(var3);
         }
      }

      this.api = var1;
      this.mv = var2;
   }

   public void visitParameter(String var1, int var2) {
      try {
         if (this.api < 327680) {
            throw new RuntimeException();
         }
      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }

      try {
         if (this.mv != null) {
            this.mv.visitParameter(var1, var2);
         }

      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }
   }

   public AnnotationVisitor visitAnnotationDefault() {
      try {
         return this.mv != null ? this.mv.visitAnnotationDefault() : null;
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      try {
         return this.mv != null ? this.mv.visitAnnotation(var1, var2) : null;
      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      try {
         if (this.api < 327680) {
            throw new RuntimeException();
         }
      } catch (IllegalArgumentException var6) {
         throw b(var6);
      }

      try {
         return this.mv != null ? this.mv.visitTypeAnnotation(var1, var2, var3, var4) : null;
      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }
   }

   public AnnotationVisitor visitParameterAnnotation(int var1, String var2, boolean var3) {
      try {
         return this.mv != null ? this.mv.visitParameterAnnotation(var1, var2, var3) : null;
      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }
   }

   public void visitAttribute(Attribute var1) {
      try {
         if (this.mv != null) {
            this.mv.visitAttribute(var1);
         }

      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }
   }

   public void visitCode() {
      try {
         if (this.mv != null) {
            this.mv.visitCode();
         }

      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }
   }

   public void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5) {
      try {
         if (this.mv != null) {
            this.mv.visitFrame(var1, var2, var3, var4, var5);
         }

      } catch (IllegalArgumentException var6) {
         throw b(var6);
      }
   }

   public void visitInsn(int var1) {
      try {
         if (this.mv != null) {
            this.mv.visitInsn(var1);
         }

      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }
   }

   public void visitIntInsn(int var1, int var2) {
      try {
         if (this.mv != null) {
            this.mv.visitIntInsn(var1, var2);
         }

      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }
   }

   public void visitVarInsn(int var1, int var2) {
      try {
         if (this.mv != null) {
            this.mv.visitVarInsn(var1, var2);
         }

      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }
   }

   public void visitTypeInsn(int var1, String var2) {
      try {
         if (this.mv != null) {
            this.mv.visitTypeInsn(var1, var2);
         }

      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }
   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      try {
         if (this.mv != null) {
            this.mv.visitFieldInsn(var1, var2, var3, var4);
         }

      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }
   }

   /** @deprecated */
   @Deprecated
   public void visitMethodInsn(int param1, String param2, String param3, String param4) {
      // $FF: Couldn't be decompiled
   }

   public void visitMethodInsn(int param1, String param2, String param3, String param4, boolean param5) {
      // $FF: Couldn't be decompiled
   }

   public void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4) {
      try {
         if (this.mv != null) {
            this.mv.visitInvokeDynamicInsn(var1, var2, var3, var4);
         }

      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }
   }

   public void visitJumpInsn(int var1, Label var2) {
      try {
         if (this.mv != null) {
            this.mv.visitJumpInsn(var1, var2);
         }

      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }
   }

   public void visitLabel(Label var1) {
      try {
         if (this.mv != null) {
            this.mv.visitLabel(var1);
         }

      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }
   }

   public void visitLdcInsn(Object var1) {
      try {
         if (this.mv != null) {
            this.mv.visitLdcInsn(var1);
         }

      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }
   }

   public void visitIincInsn(int var1, int var2) {
      try {
         if (this.mv != null) {
            this.mv.visitIincInsn(var1, var2);
         }

      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }
   }

   public void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4) {
      try {
         if (this.mv != null) {
            this.mv.visitTableSwitchInsn(var1, var2, var3, var4);
         }

      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }
   }

   public void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3) {
      try {
         if (this.mv != null) {
            this.mv.visitLookupSwitchInsn(var1, var2, var3);
         }

      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }
   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      try {
         if (this.mv != null) {
            this.mv.visitMultiANewArrayInsn(var1, var2);
         }

      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }
   }

   public AnnotationVisitor visitInsnAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      try {
         if (this.api < 327680) {
            throw new RuntimeException();
         }
      } catch (IllegalArgumentException var6) {
         throw b(var6);
      }

      try {
         return this.mv != null ? this.mv.visitInsnAnnotation(var1, var2, var3, var4) : null;
      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }
   }

   public void visitTryCatchBlock(Label var1, Label var2, Label var3, String var4) {
      try {
         if (this.mv != null) {
            this.mv.visitTryCatchBlock(var1, var2, var3, var4);
         }

      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }
   }

   public AnnotationVisitor visitTryCatchAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      try {
         if (this.api < 327680) {
            throw new RuntimeException();
         }
      } catch (IllegalArgumentException var6) {
         throw b(var6);
      }

      try {
         return this.mv != null ? this.mv.visitTryCatchAnnotation(var1, var2, var3, var4) : null;
      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }
   }

   public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
      try {
         if (this.mv != null) {
            this.mv.visitLocalVariable(var1, var2, var3, var4, var5, var6);
         }

      } catch (IllegalArgumentException var7) {
         throw b(var7);
      }
   }

   public AnnotationVisitor visitLocalVariableAnnotation(int var1, TypePath var2, Label[] var3, Label[] var4, int[] var5, String var6, boolean var7) {
      try {
         if (this.api < 327680) {
            throw new RuntimeException();
         }
      } catch (IllegalArgumentException var9) {
         throw b(var9);
      }

      try {
         return this.mv != null ? this.mv.visitLocalVariableAnnotation(var1, var2, var3, var4, var5, var6, var7) : null;
      } catch (IllegalArgumentException var8) {
         throw b(var8);
      }
   }

   public void visitLineNumber(int var1, Label var2) {
      try {
         if (this.mv != null) {
            this.mv.visitLineNumber(var1, var2);
         }

      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }
   }

   public void visitMaxs(int var1, int var2) {
      try {
         if (this.mv != null) {
            this.mv.visitMaxs(var1, var2);
         }

      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }
   }

   public void visitEnd() {
      try {
         if (this.mv != null) {
            this.mv.visitEnd();
         }

      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }
   }

   private static IllegalArgumentException b(IllegalArgumentException var0) {
      return var0;
   }
}

package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.TypePath;

public class MethodRemapper extends MethodVisitor {
   protected final Remapper remapper;

   public MethodRemapper(MethodVisitor var1, Remapper var2) {
      this(327680, var1, var2);
   }

   protected MethodRemapper(int var1, MethodVisitor var2, Remapper var3) {
      super(var1, var2);
      this.remapper = var3;
   }

   public AnnotationVisitor visitAnnotationDefault() {
      AnnotationVisitor var1 = super.visitAnnotationDefault();

      Object var10000;
      try {
         if (var1 == null) {
            var10000 = var1;
            return (AnnotationVisitor)var10000;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      var10000 = new AnnotationRemapper(var1, this.remapper);
      return (AnnotationVisitor)var10000;
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      AnnotationVisitor var3 = super.visitAnnotation(this.remapper.mapDesc(var1), var2);

      Object var10000;
      try {
         if (var3 == null) {
            var10000 = var3;
            return (AnnotationVisitor)var10000;
         }
      } catch (RuntimeException var4) {
         throw b(var4);
      }

      var10000 = new AnnotationRemapper(var3, this.remapper);
      return (AnnotationVisitor)var10000;
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      AnnotationVisitor var5 = super.visitTypeAnnotation(var1, var2, this.remapper.mapDesc(var3), var4);

      Object var10000;
      try {
         if (var5 == null) {
            var10000 = var5;
            return (AnnotationVisitor)var10000;
         }
      } catch (RuntimeException var6) {
         throw b(var6);
      }

      var10000 = new AnnotationRemapper(var5, this.remapper);
      return (AnnotationVisitor)var10000;
   }

   public AnnotationVisitor visitParameterAnnotation(int var1, String var2, boolean var3) {
      AnnotationVisitor var4 = super.visitParameterAnnotation(var1, this.remapper.mapDesc(var2), var3);

      Object var10000;
      try {
         if (var4 == null) {
            var10000 = var4;
            return (AnnotationVisitor)var10000;
         }
      } catch (RuntimeException var5) {
         throw b(var5);
      }

      var10000 = new AnnotationRemapper(var4, this.remapper);
      return (AnnotationVisitor)var10000;
   }

   public void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5) {
      super.visitFrame(var1, var2, this.remapEntries(var2, var3), var4, this.remapEntries(var4, var5));
   }

   private Object[] remapEntries(int var1, Object[] var2) {
      int var3 = 0;

      while(true) {
         try {
            if (var3 >= var1) {
               return var2;
            }

            if (var2[var3] instanceof String) {
               break;
            }
         } catch (RuntimeException var7) {
            throw b(var7);
         }

         ++var3;
      }

      Object[] var4 = new Object[var1];

      try {
         if (var3 > 0) {
            System.arraycopy(var2, 0, var4, 0, var3);
         }
      } catch (RuntimeException var6) {
         throw b(var6);
      }

      do {
         Object var5 = var2[var3];
         var4[var3++] = var5 instanceof String ? this.remapper.mapType((String)var5) : var5;
      } while(var3 < var1);

      return var4;
   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      super.visitFieldInsn(var1, this.remapper.mapType(var2), this.remapper.mapFieldName(var2, var3, var4), this.remapper.mapDesc(var4));
   }

   /** @deprecated */
   @Deprecated
   public void visitMethodInsn(int var1, String var2, String var3, String var4) {
      try {
         if (this.api >= 327680) {
            super.visitMethodInsn(var1, var2, var3, var4);
            return;
         }
      } catch (RuntimeException var5) {
         throw b(var5);
      }

      MethodRemapper var10000;
      int var10001;
      String var10002;
      String var10003;
      String var10004;
      boolean var10005;
      label22: {
         try {
            var10000 = this;
            var10001 = var1;
            var10002 = var2;
            var10003 = var3;
            var10004 = var4;
            if (var1 == 185) {
               var10005 = true;
               break label22;
            }
         } catch (RuntimeException var6) {
            throw b(var6);
         }

         var10005 = false;
      }

      var10000.doVisitMethodInsn(var10001, var10002, var10003, var10004, var10005);
   }

   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      try {
         if (this.api < 327680) {
            super.visitMethodInsn(var1, var2, var3, var4, var5);
            return;
         }
      } catch (RuntimeException var6) {
         throw b(var6);
      }

      this.doVisitMethodInsn(var1, var2, var3, var4, var5);
   }

   private void doVisitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      try {
         if (this.mv != null) {
            this.mv.visitMethodInsn(var1, this.remapper.mapType(var2), this.remapper.mapMethodName(var2, var3, var4), this.remapper.mapMethodDesc(var4), var5);
         }

      } catch (RuntimeException var6) {
         throw b(var6);
      }
   }

   public void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4) {
      int var5 = 0;

      try {
         while(var5 < var4.length) {
            var4[var5] = this.remapper.mapValue(var4[var5]);
            ++var5;
         }
      } catch (RuntimeException var6) {
         throw b(var6);
      }

      super.visitInvokeDynamicInsn(this.remapper.mapInvokeDynamicMethodName(var1, var2), this.remapper.mapMethodDesc(var2), (Handle)this.remapper.mapValue(var3), var4);
   }

   public void visitTypeInsn(int var1, String var2) {
      super.visitTypeInsn(var1, this.remapper.mapType(var2));
   }

   public void visitLdcInsn(Object var1) {
      super.visitLdcInsn(this.remapper.mapValue(var1));
   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      super.visitMultiANewArrayInsn(this.remapper.mapDesc(var1), var2);
   }

   public AnnotationVisitor visitInsnAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      AnnotationVisitor var5 = super.visitInsnAnnotation(var1, var2, this.remapper.mapDesc(var3), var4);

      Object var10000;
      try {
         if (var5 == null) {
            var10000 = var5;
            return (AnnotationVisitor)var10000;
         }
      } catch (RuntimeException var6) {
         throw b(var6);
      }

      var10000 = new AnnotationRemapper(var5, this.remapper);
      return (AnnotationVisitor)var10000;
   }

   public void visitTryCatchBlock(Label var1, Label var2, Label var3, String var4) {
      MethodRemapper var10000;
      Label var10001;
      Label var10002;
      Label var10003;
      String var10004;
      label16: {
         try {
            var10000 = this;
            var10001 = var1;
            var10002 = var2;
            var10003 = var3;
            if (var4 == null) {
               var10004 = null;
               break label16;
            }
         } catch (RuntimeException var5) {
            throw b(var5);
         }

         var10004 = this.remapper.mapType(var4);
      }

      var10000.visitTryCatchBlock(var10001, var10002, var10003, var10004);
   }

   public AnnotationVisitor visitTryCatchAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      AnnotationVisitor var5 = super.visitTryCatchAnnotation(var1, var2, this.remapper.mapDesc(var3), var4);

      Object var10000;
      try {
         if (var5 == null) {
            var10000 = var5;
            return (AnnotationVisitor)var10000;
         }
      } catch (RuntimeException var6) {
         throw b(var6);
      }

      var10000 = new AnnotationRemapper(var5, this.remapper);
      return (AnnotationVisitor)var10000;
   }

   public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
      super.visitLocalVariable(var1, this.remapper.mapDesc(var2), this.remapper.mapSignature(var3, true), var4, var5, var6);
   }

   public AnnotationVisitor visitLocalVariableAnnotation(int var1, TypePath var2, Label[] var3, Label[] var4, int[] var5, String var6, boolean var7) {
      AnnotationVisitor var8 = super.visitLocalVariableAnnotation(var1, var2, var3, var4, var5, this.remapper.mapDesc(var6), var7);

      Object var10000;
      try {
         if (var8 == null) {
            var10000 = var8;
            return (AnnotationVisitor)var10000;
         }
      } catch (RuntimeException var9) {
         throw b(var9);
      }

      var10000 = new AnnotationRemapper(var8, this.remapper);
      return (AnnotationVisitor)var10000;
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

package org.spongepowered.asm.lib;

public abstract class ClassVisitor {
   protected final int api;
   protected ClassVisitor cv;

   public ClassVisitor(int var1) {
      this(var1, (ClassVisitor)null);
   }

   public ClassVisitor(int var1, ClassVisitor var2) {
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
      this.cv = var2;
   }

   public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      try {
         if (this.cv != null) {
            this.cv.visit(var1, var2, var3, var4, var5, var6);
         }

      } catch (IllegalArgumentException var7) {
         throw b(var7);
      }
   }

   public void visitSource(String var1, String var2) {
      try {
         if (this.cv != null) {
            this.cv.visitSource(var1, var2);
         }

      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }
   }

   public void visitOuterClass(String var1, String var2, String var3) {
      try {
         if (this.cv != null) {
            this.cv.visitOuterClass(var1, var2, var3);
         }

      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      try {
         return this.cv != null ? this.cv.visitAnnotation(var1, var2) : null;
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
         return this.cv != null ? this.cv.visitTypeAnnotation(var1, var2, var3, var4) : null;
      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }
   }

   public void visitAttribute(Attribute var1) {
      try {
         if (this.cv != null) {
            this.cv.visitAttribute(var1);
         }

      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }
   }

   public void visitInnerClass(String var1, String var2, String var3, int var4) {
      try {
         if (this.cv != null) {
            this.cv.visitInnerClass(var1, var2, var3, var4);
         }

      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }
   }

   public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      try {
         return this.cv != null ? this.cv.visitField(var1, var2, var3, var4, var5) : null;
      } catch (IllegalArgumentException var6) {
         throw b(var6);
      }
   }

   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      try {
         return this.cv != null ? this.cv.visitMethod(var1, var2, var3, var4, var5) : null;
      } catch (IllegalArgumentException var6) {
         throw b(var6);
      }
   }

   public void visitEnd() {
      try {
         if (this.cv != null) {
            this.cv.visitEnd();
         }

      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }
   }

   private static IllegalArgumentException b(IllegalArgumentException var0) {
      return var0;
   }
}

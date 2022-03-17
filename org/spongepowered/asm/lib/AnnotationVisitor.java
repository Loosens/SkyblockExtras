package org.spongepowered.asm.lib;

public abstract class AnnotationVisitor {
   protected final int api;
   protected AnnotationVisitor av;

   public AnnotationVisitor(int var1) {
      this(var1, (AnnotationVisitor)null);
   }

   public AnnotationVisitor(int var1, AnnotationVisitor var2) {
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
      this.av = var2;
   }

   public void visit(String var1, Object var2) {
      try {
         if (this.av != null) {
            this.av.visit(var1, var2);
         }

      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }
   }

   public void visitEnum(String var1, String var2, String var3) {
      try {
         if (this.av != null) {
            this.av.visitEnum(var1, var2, var3);
         }

      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }
   }

   public AnnotationVisitor visitAnnotation(String var1, String var2) {
      try {
         return this.av != null ? this.av.visitAnnotation(var1, var2) : null;
      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }
   }

   public AnnotationVisitor visitArray(String var1) {
      try {
         return this.av != null ? this.av.visitArray(var1) : null;
      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }
   }

   public void visitEnd() {
      try {
         if (this.av != null) {
            this.av.visitEnd();
         }

      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }
   }

   private static IllegalArgumentException b(IllegalArgumentException var0) {
      return var0;
   }
}

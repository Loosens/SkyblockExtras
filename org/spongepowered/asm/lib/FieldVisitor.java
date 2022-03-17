package org.spongepowered.asm.lib;

public abstract class FieldVisitor {
   protected final int api;
   protected FieldVisitor fv;

   public FieldVisitor(int var1) {
      this(var1, (FieldVisitor)null);
   }

   public FieldVisitor(int var1, FieldVisitor var2) {
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
      this.fv = var2;
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      try {
         return this.fv != null ? this.fv.visitAnnotation(var1, var2) : null;
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
         return this.fv != null ? this.fv.visitTypeAnnotation(var1, var2, var3, var4) : null;
      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }
   }

   public void visitAttribute(Attribute var1) {
      try {
         if (this.fv != null) {
            this.fv.visitAttribute(var1);
         }

      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }
   }

   public void visitEnd() {
      try {
         if (this.fv != null) {
            this.fv.visitEnd();
         }

      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }
   }

   private static IllegalArgumentException b(IllegalArgumentException var0) {
      return var0;
   }
}

package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.AnnotationVisitor;

public final class TraceAnnotationVisitor extends AnnotationVisitor {
   private final Printer p;

   public TraceAnnotationVisitor(Printer var1) {
      this((AnnotationVisitor)null, var1);
   }

   public TraceAnnotationVisitor(AnnotationVisitor var1, Printer var2) {
      super(327680, var1);
      this.p = var2;
   }

   public void visit(String var1, Object var2) {
      this.p.visit(var1, var2);
      super.visit(var1, var2);
   }

   public void visitEnum(String var1, String var2, String var3) {
      this.p.visitEnum(var1, var2, var3);
      super.visitEnum(var1, var2, var3);
   }

   public AnnotationVisitor visitAnnotation(String var1, String var2) {
      Printer var3 = this.p.visitAnnotation(var1, var2);

      AnnotationVisitor var10000;
      label17: {
         try {
            if (this.av == null) {
               var10000 = null;
               break label17;
            }
         } catch (RuntimeException var5) {
            throw b(var5);
         }

         var10000 = this.av.visitAnnotation(var1, var2);
      }

      AnnotationVisitor var4 = var10000;
      return new TraceAnnotationVisitor(var4, var3);
   }

   public AnnotationVisitor visitArray(String var1) {
      Printer var2 = this.p.visitArray(var1);

      AnnotationVisitor var10000;
      label17: {
         try {
            if (this.av == null) {
               var10000 = null;
               break label17;
            }
         } catch (RuntimeException var4) {
            throw b(var4);
         }

         var10000 = this.av.visitArray(var1);
      }

      AnnotationVisitor var3 = var10000;
      return new TraceAnnotationVisitor(var3, var2);
   }

   public void visitEnd() {
      this.p.visitAnnotationEnd();
      super.visitEnd();
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

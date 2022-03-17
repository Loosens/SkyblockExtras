package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.AnnotationVisitor;

public class AnnotationRemapper extends AnnotationVisitor {
   protected final Remapper remapper;

   public AnnotationRemapper(AnnotationVisitor var1, Remapper var2) {
      this(327680, var1, var2);
   }

   protected AnnotationRemapper(int var1, AnnotationVisitor var2, Remapper var3) {
      super(var1, var2);
      this.remapper = var3;
   }

   public void visit(String var1, Object var2) {
      this.av.visit(var1, this.remapper.mapValue(var2));
   }

   public void visitEnum(String var1, String var2, String var3) {
      this.av.visitEnum(var1, this.remapper.mapDesc(var2), var3);
   }

   public AnnotationVisitor visitAnnotation(String var1, String var2) {
      AnnotationVisitor var3 = this.av.visitAnnotation(var1, this.remapper.mapDesc(var2));

      AnnotationRemapper var10000;
      try {
         if (var3 == null) {
            var10000 = null;
            return var10000;
         }
      } catch (RuntimeException var5) {
         throw b(var5);
      }

      try {
         if (var3 == this.av) {
            var10000 = this;
            return var10000;
         }
      } catch (RuntimeException var4) {
         throw b(var4);
      }

      var10000 = new AnnotationRemapper(var3, this.remapper);
      return var10000;
   }

   public AnnotationVisitor visitArray(String var1) {
      AnnotationVisitor var2 = this.av.visitArray(var1);

      AnnotationRemapper var10000;
      try {
         if (var2 == null) {
            var10000 = null;
            return var10000;
         }
      } catch (RuntimeException var4) {
         throw b(var4);
      }

      try {
         if (var2 == this.av) {
            var10000 = this;
            return var10000;
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      var10000 = new AnnotationRemapper(var2, this.remapper);
      return var10000;
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.TypePath;

public class FieldRemapper extends FieldVisitor {
   private final Remapper remapper;

   public FieldRemapper(FieldVisitor var1, Remapper var2) {
      this(327680, var1, var2);
   }

   protected FieldRemapper(int var1, FieldVisitor var2, Remapper var3) {
      super(var1, var2);
      this.remapper = var3;
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      AnnotationVisitor var3 = this.fv.visitAnnotation(this.remapper.mapDesc(var1), var2);

      AnnotationRemapper var10000;
      try {
         if (var3 == null) {
            var10000 = null;
            return var10000;
         }
      } catch (RuntimeException var4) {
         throw b(var4);
      }

      var10000 = new AnnotationRemapper(var3, this.remapper);
      return var10000;
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      AnnotationVisitor var5 = super.visitTypeAnnotation(var1, var2, this.remapper.mapDesc(var3), var4);

      AnnotationRemapper var10000;
      try {
         if (var5 == null) {
            var10000 = null;
            return var10000;
         }
      } catch (RuntimeException var6) {
         throw b(var6);
      }

      var10000 = new AnnotationRemapper(var5, this.remapper);
      return var10000;
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

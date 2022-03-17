package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.TypePath;

public class ClassRemapper extends ClassVisitor {
   protected final Remapper remapper;
   protected String className;

   public ClassRemapper(ClassVisitor var1, Remapper var2) {
      this(327680, var1, var2);
   }

   protected ClassRemapper(int var1, ClassVisitor var2, Remapper var3) {
      super(var1, var2);
      this.remapper = var3;
   }

   public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      ClassRemapper var10000;
      int var10001;
      int var10002;
      String var10003;
      String var10004;
      String var10005;
      String[] var10006;
      label16: {
         try {
            this.className = var3;
            var10000 = this;
            var10001 = var1;
            var10002 = var2;
            var10003 = this.remapper.mapType(var3);
            var10004 = this.remapper.mapSignature(var4, false);
            var10005 = this.remapper.mapType(var5);
            if (var6 == null) {
               var10006 = null;
               break label16;
            }
         } catch (RuntimeException var7) {
            throw b(var7);
         }

         var10006 = this.remapper.mapTypes(var6);
      }

      var10000.visit(var10001, var10002, var10003, var10004, var10005, var10006);
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      AnnotationVisitor var3 = super.visitAnnotation(this.remapper.mapDesc(var1), var2);

      AnnotationVisitor var10000;
      try {
         if (var3 == null) {
            var10000 = null;
            return var10000;
         }
      } catch (RuntimeException var4) {
         throw b(var4);
      }

      var10000 = this.createAnnotationRemapper(var3);
      return var10000;
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      AnnotationVisitor var5 = super.visitTypeAnnotation(var1, var2, this.remapper.mapDesc(var3), var4);

      AnnotationVisitor var10000;
      try {
         if (var5 == null) {
            var10000 = null;
            return var10000;
         }
      } catch (RuntimeException var6) {
         throw b(var6);
      }

      var10000 = this.createAnnotationRemapper(var5);
      return var10000;
   }

   public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      FieldVisitor var6 = super.visitField(var1, this.remapper.mapFieldName(this.className, var2, var3), this.remapper.mapDesc(var3), this.remapper.mapSignature(var4, true), this.remapper.mapValue(var5));

      FieldVisitor var10000;
      try {
         if (var6 == null) {
            var10000 = null;
            return var10000;
         }
      } catch (RuntimeException var7) {
         throw b(var7);
      }

      var10000 = this.createFieldRemapper(var6);
      return var10000;
   }

   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      String var6 = this.remapper.mapMethodDesc(var3);

      ClassRemapper var10000;
      int var10001;
      String var10002;
      String var10003;
      String var10004;
      String[] var10005;
      label31: {
         try {
            var10000 = this;
            var10001 = var1;
            var10002 = this.remapper.mapMethodName(this.className, var2, var3);
            var10003 = var6;
            var10004 = this.remapper.mapSignature(var4, false);
            if (var5 == null) {
               var10005 = null;
               break label31;
            }
         } catch (RuntimeException var9) {
            throw b(var9);
         }

         var10005 = this.remapper.mapTypes(var5);
      }

      MethodVisitor var7 = var10000.visitMethod(var10001, var10002, var10003, var10004, var10005);

      MethodVisitor var10;
      try {
         if (var7 == null) {
            var10 = null;
            return var10;
         }
      } catch (RuntimeException var8) {
         throw b(var8);
      }

      var10 = this.createMethodRemapper(var7);
      return var10;
   }

   public void visitInnerClass(String var1, String var2, String var3, int var4) {
      ClassRemapper var10000;
      String var10001;
      String var10002;
      label16: {
         try {
            var10000 = this;
            var10001 = this.remapper.mapType(var1);
            if (var2 == null) {
               var10002 = null;
               break label16;
            }
         } catch (RuntimeException var5) {
            throw b(var5);
         }

         var10002 = this.remapper.mapType(var2);
      }

      var10000.visitInnerClass(var10001, var10002, var3, var4);
   }

   public void visitOuterClass(String var1, String var2, String var3) {
      ClassRemapper var10000;
      String var10001;
      String var10002;
      label28: {
         try {
            var10000 = this;
            var10001 = this.remapper.mapType(var1);
            if (var2 == null) {
               var10002 = null;
               break label28;
            }
         } catch (RuntimeException var5) {
            throw b(var5);
         }

         var10002 = this.remapper.mapMethodName(var1, var2, var3);
      }

      String var10003;
      label21: {
         try {
            if (var3 == null) {
               var10003 = null;
               break label21;
            }
         } catch (RuntimeException var4) {
            throw b(var4);
         }

         var10003 = this.remapper.mapMethodDesc(var3);
      }

      var10000.visitOuterClass(var10001, var10002, var10003);
   }

   protected FieldVisitor createFieldRemapper(FieldVisitor var1) {
      return new FieldRemapper(var1, this.remapper);
   }

   protected MethodVisitor createMethodRemapper(MethodVisitor var1) {
      return new MethodRemapper(var1, this.remapper);
   }

   protected AnnotationVisitor createAnnotationRemapper(AnnotationVisitor var1) {
      return new AnnotationRemapper(var1, this.remapper);
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

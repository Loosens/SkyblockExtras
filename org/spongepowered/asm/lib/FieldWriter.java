package org.spongepowered.asm.lib;

final class FieldWriter extends FieldVisitor {
   private final ClassWriter cw;
   private final int access;
   private final int name;
   private final int desc;
   private int signature;
   private int value;
   private AnnotationWriter anns;
   private AnnotationWriter ianns;
   private AnnotationWriter tanns;
   private AnnotationWriter itanns;
   private Attribute attrs;

   FieldWriter(ClassWriter var1, int var2, String var3, String var4, String var5, Object var6) {
      super(327680);
      if (var1.firstField == null) {
         var1.firstField = this;
      } else {
         var1.lastField.fv = this;
      }

      try {
         var1.lastField = this;
         this.cw = var1;
         this.access = var2;
         this.name = var1.newUTF8(var3);
         this.desc = var1.newUTF8(var4);
         if (var5 != null) {
            this.signature = var1.newUTF8(var5);
         }
      } catch (RuntimeException var8) {
         throw b(var8);
      }

      try {
         if (var6 != null) {
            this.value = var1.newConstItem(var6).index;
         }

      } catch (RuntimeException var7) {
         throw b(var7);
      }
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      ByteVector var3 = new ByteVector();
      var3.putShort(this.cw.newUTF8(var1)).putShort(0);
      AnnotationWriter var4 = new AnnotationWriter(this.cw, true, var3, var3, 2);

      try {
         if (var2) {
            var4.next = this.anns;
            this.anns = var4;
            return var4;
         }
      } catch (RuntimeException var5) {
         throw b(var5);
      }

      var4.next = this.ianns;
      this.ianns = var4;
      return var4;
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      ByteVector var5 = new ByteVector();
      AnnotationWriter.putTarget(var1, var2, var5);
      var5.putShort(this.cw.newUTF8(var3)).putShort(0);
      AnnotationWriter var6 = new AnnotationWriter(this.cw, true, var5, var5, var5.length - 2);

      try {
         if (var4) {
            var6.next = this.tanns;
            this.tanns = var6;
            return var6;
         }
      } catch (RuntimeException var7) {
         throw b(var7);
      }

      var6.next = this.itanns;
      this.itanns = var6;
      return var6;
   }

   public void visitAttribute(Attribute var1) {
      var1.next = this.attrs;
      this.attrs = var1;
   }

   public void visitEnd() {
   }

   int getSize() {
      // $FF: Couldn't be decompiled
   }

   void put(ByteVector param1) {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

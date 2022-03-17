package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.commons.ClassRemapper;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;

class InnerClassGenerator$InnerClassAdapter extends ClassRemapper {
   private final InnerClassGenerator$InnerClassInfo info;

   public InnerClassGenerator$InnerClassAdapter(ClassVisitor var1, InnerClassGenerator$InnerClassInfo var2) {
      super(327680, var1, var2);
      this.info = var2;
   }

   public void visitSource(String var1, String var2) {
      super.visitSource(var1, var2);
      AnnotationVisitor var3 = this.cv.visitAnnotation("Lorg/spongepowered/asm/mixin/transformer/meta/MixinInner;", false);
      var3.visit("mixin", this.info.getOwner().toString());
      var3.visit("name", this.info.getOriginalName().substring(this.info.getOriginalName().lastIndexOf(47) + 1));
      var3.visitEnd();
   }

   public void visitInnerClass(String var1, String var2, String var3, int var4) {
      try {
         if (var1.startsWith(this.info.getOriginalName() + "$")) {
            throw new InvalidMixinException(this.info.getOwner(), "Found unsupported nested inner class " + var1 + " in " + this.info.getOriginalName());
         }
      } catch (InvalidMixinException var5) {
         throw b(var5);
      }

      super.visitInnerClass(var1, var2, var3, var4);
   }

   private static InvalidMixinException b(InvalidMixinException var0) {
      return var0;
   }
}

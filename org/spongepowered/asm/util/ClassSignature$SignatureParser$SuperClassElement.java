package org.spongepowered.asm.util;

class ClassSignature$SignatureParser$SuperClassElement extends ClassSignature$SignatureParser$TokenElement {
   final ClassSignature$SignatureParser this$1;

   ClassSignature$SignatureParser$SuperClassElement(ClassSignature$SignatureParser var1) {
      super(var1);
      this.this$1 = var1;
   }

   public void visitEnd() {
      this.this$1.this$0.setSuperClass(this.token);
   }
}

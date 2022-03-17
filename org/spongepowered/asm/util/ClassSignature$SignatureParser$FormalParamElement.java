package org.spongepowered.asm.util;

class ClassSignature$SignatureParser$FormalParamElement extends ClassSignature$SignatureParser$TokenElement {
   private final ClassSignature$TokenHandle handle;
   final ClassSignature$SignatureParser this$1;

   ClassSignature$SignatureParser$FormalParamElement(ClassSignature$SignatureParser var1, String var2) {
      super(var1);
      this.this$1 = var1;
      this.handle = var1.this$0.getType(var2);
      this.token = this.handle.asToken();
   }
}

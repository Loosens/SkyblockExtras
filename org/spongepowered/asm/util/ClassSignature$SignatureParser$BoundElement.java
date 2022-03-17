package org.spongepowered.asm.util;

import org.spongepowered.asm.lib.signature.SignatureVisitor;

class ClassSignature$SignatureParser$BoundElement extends ClassSignature$SignatureParser$TokenElement {
   private final ClassSignature$SignatureParser$TokenElement type;
   private final boolean classBound;
   final ClassSignature$SignatureParser this$1;

   ClassSignature$SignatureParser$BoundElement(ClassSignature$SignatureParser var1, ClassSignature$SignatureParser$TokenElement var2, boolean var3) {
      super(var1);
      this.this$1 = var1;
      this.type = var2;
      this.classBound = var3;
   }

   public void visitClassType(String var1) {
      this.token = this.type.token.addBound(var1, this.classBound);
   }

   public void visitTypeArgument() {
      this.token.addTypeArgument('*');
   }

   public SignatureVisitor visitTypeArgument(char var1) {
      return new ClassSignature$SignatureParser$TypeArgElement(this.this$1, this, var1);
   }
}

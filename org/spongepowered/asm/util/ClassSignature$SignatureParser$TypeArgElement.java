package org.spongepowered.asm.util;

import org.spongepowered.asm.lib.signature.SignatureVisitor;

class ClassSignature$SignatureParser$TypeArgElement extends ClassSignature$SignatureParser$TokenElement {
   private final ClassSignature$SignatureParser$TokenElement type;
   private final char wildcard;
   final ClassSignature$SignatureParser this$1;

   ClassSignature$SignatureParser$TypeArgElement(ClassSignature$SignatureParser var1, ClassSignature$SignatureParser$TokenElement var2, char var3) {
      super(var1);
      this.this$1 = var1;
      this.type = var2;
      this.wildcard = var3;
   }

   public SignatureVisitor visitArrayType() {
      this.type.setArray();
      return this;
   }

   public void visitBaseType(char var1) {
      this.token = this.type.addTypeArgument(var1).asToken();
   }

   public void visitTypeVariable(String var1) {
      ClassSignature$TokenHandle var2 = this.this$1.this$0.getType(var1);
      this.token = this.type.addTypeArgument(var2).setWildcard(this.wildcard).asToken();
   }

   public void visitClassType(String var1) {
      this.token = this.type.addTypeArgument(var1).setWildcard(this.wildcard).asToken();
   }

   public void visitTypeArgument() {
      this.token.addTypeArgument('*');
   }

   public SignatureVisitor visitTypeArgument(char var1) {
      return new ClassSignature$SignatureParser$TypeArgElement(this.this$1, this, var1);
   }

   public void visitEnd() {
   }
}

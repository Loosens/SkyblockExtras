package org.spongepowered.asm.util;

import org.spongepowered.asm.lib.signature.SignatureVisitor;

class ClassSignature$SignatureParser extends SignatureVisitor {
   private ClassSignature$SignatureParser$FormalParamElement param;
   final ClassSignature this$0;

   ClassSignature$SignatureParser(ClassSignature var1) {
      super(327680);
      this.this$0 = var1;
   }

   public void visitFormalTypeParameter(String var1) {
      this.param = new ClassSignature$SignatureParser$FormalParamElement(this, var1);
   }

   public SignatureVisitor visitClassBound() {
      return this.param.visitClassBound();
   }

   public SignatureVisitor visitInterfaceBound() {
      return this.param.visitInterfaceBound();
   }

   public SignatureVisitor visitSuperclass() {
      return new ClassSignature$SignatureParser$SuperClassElement(this);
   }

   public SignatureVisitor visitInterface() {
      return new ClassSignature$SignatureParser$InterfaceElement(this);
   }
}

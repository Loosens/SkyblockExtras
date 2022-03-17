package org.spongepowered.asm.util;

import org.spongepowered.asm.lib.signature.SignatureVisitor;

abstract class ClassSignature$SignatureParser$TokenElement extends ClassSignature$SignatureParser$SignatureElement {
   protected ClassSignature$Token token;
   private boolean array;
   final ClassSignature$SignatureParser this$1;

   ClassSignature$SignatureParser$TokenElement(ClassSignature$SignatureParser var1) {
      super(var1);
      this.this$1 = var1;
   }

   public ClassSignature$Token getToken() {
      try {
         if (this.token == null) {
            this.token = new ClassSignature$Token();
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      return this.token;
   }

   protected void setArray() {
      this.array = true;
   }

   private boolean getArray() {
      boolean var1 = this.array;
      this.array = false;
      return var1;
   }

   public void visitClassType(String var1) {
      this.getToken().setType(var1);
   }

   public SignatureVisitor visitClassBound() {
      this.getToken();
      return new ClassSignature$SignatureParser$BoundElement(this.this$1, this, true);
   }

   public SignatureVisitor visitInterfaceBound() {
      this.getToken();
      return new ClassSignature$SignatureParser$BoundElement(this.this$1, this, false);
   }

   public void visitInnerClassType(String var1) {
      this.token.addInnerClass(var1);
   }

   public SignatureVisitor visitArrayType() {
      this.setArray();
      return this;
   }

   public SignatureVisitor visitTypeArgument(char var1) {
      return new ClassSignature$SignatureParser$TypeArgElement(this.this$1, this, var1);
   }

   ClassSignature$Token addTypeArgument() {
      return this.token.addTypeArgument('*').asToken();
   }

   ClassSignature$IToken addTypeArgument(char var1) {
      return this.token.addTypeArgument(var1).setArray(this.getArray());
   }

   ClassSignature$IToken addTypeArgument(String var1) {
      return this.token.addTypeArgument(var1).setArray(this.getArray());
   }

   ClassSignature$IToken addTypeArgument(ClassSignature$Token var1) {
      return this.token.addTypeArgument(var1).setArray(this.getArray());
   }

   ClassSignature$IToken addTypeArgument(ClassSignature$TokenHandle var1) {
      return this.token.addTypeArgument(var1).setArray(this.getArray());
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

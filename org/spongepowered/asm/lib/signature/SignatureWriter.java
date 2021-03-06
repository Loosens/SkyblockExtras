package org.spongepowered.asm.lib.signature;

public class SignatureWriter extends SignatureVisitor {
   private final StringBuilder buf = new StringBuilder();
   private boolean hasFormals;
   private boolean hasParameters;
   private int argumentStack;

   public SignatureWriter() {
      super(327680);
   }

   public void visitFormalTypeParameter(String var1) {
      try {
         if (!this.hasFormals) {
            this.hasFormals = true;
            this.buf.append('<');
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      this.buf.append(var1);
      this.buf.append(':');
   }

   public SignatureVisitor visitClassBound() {
      return this;
   }

   public SignatureVisitor visitInterfaceBound() {
      this.buf.append(':');
      return this;
   }

   public SignatureVisitor visitSuperclass() {
      this.endFormals();
      return this;
   }

   public SignatureVisitor visitInterface() {
      return this;
   }

   public SignatureVisitor visitParameterType() {
      try {
         this.endFormals();
         if (!this.hasParameters) {
            this.hasParameters = true;
            this.buf.append('(');
         }

         return this;
      } catch (RuntimeException var1) {
         throw b(var1);
      }
   }

   public SignatureVisitor visitReturnType() {
      try {
         this.endFormals();
         if (!this.hasParameters) {
            this.buf.append('(');
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      this.buf.append(')');
      return this;
   }

   public SignatureVisitor visitExceptionType() {
      this.buf.append('^');
      return this;
   }

   public void visitBaseType(char var1) {
      this.buf.append(var1);
   }

   public void visitTypeVariable(String var1) {
      this.buf.append('T');
      this.buf.append(var1);
      this.buf.append(';');
   }

   public SignatureVisitor visitArrayType() {
      this.buf.append('[');
      return this;
   }

   public void visitClassType(String var1) {
      this.buf.append('L');
      this.buf.append(var1);
      this.argumentStack *= 2;
   }

   public void visitInnerClassType(String var1) {
      this.endArguments();
      this.buf.append('.');
      this.buf.append(var1);
      this.argumentStack *= 2;
   }

   public void visitTypeArgument() {
      try {
         if (this.argumentStack % 2 == 0) {
            ++this.argumentStack;
            this.buf.append('<');
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      this.buf.append('*');
   }

   public SignatureVisitor visitTypeArgument(char var1) {
      try {
         if (this.argumentStack % 2 == 0) {
            ++this.argumentStack;
            this.buf.append('<');
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      try {
         if (var1 != '=') {
            this.buf.append(var1);
         }

         return this;
      } catch (RuntimeException var2) {
         throw b(var2);
      }
   }

   public void visitEnd() {
      this.endArguments();
      this.buf.append(';');
   }

   public String toString() {
      return this.buf.toString();
   }

   private void endFormals() {
      try {
         if (this.hasFormals) {
            this.hasFormals = false;
            this.buf.append('>');
         }

      } catch (RuntimeException var1) {
         throw b(var1);
      }
   }

   private void endArguments() {
      try {
         if (this.argumentStack % 2 != 0) {
            this.buf.append('>');
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      this.argumentStack /= 2;
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

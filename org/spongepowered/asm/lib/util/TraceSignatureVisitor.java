package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.signature.SignatureVisitor;

public final class TraceSignatureVisitor extends SignatureVisitor {
   private final StringBuilder declaration;
   private boolean isInterface;
   private boolean seenFormalParameter;
   private boolean seenInterfaceBound;
   private boolean seenParameter;
   private boolean seenInterface;
   private StringBuilder returnType;
   private StringBuilder exceptions;
   private int argumentStack;
   private int arrayStack;
   private String separator = "";

   public TraceSignatureVisitor(int var1) {
      super(327680);
      this.isInterface = (var1 & 512) != 0;
      this.declaration = new StringBuilder();
   }

   private TraceSignatureVisitor(StringBuilder var1) {
      super(327680);
      this.declaration = var1;
   }

   public void visitFormalTypeParameter(String var1) {
      StringBuilder var10000;
      String var10001;
      label16: {
         try {
            var10000 = this.declaration;
            if (this.seenFormalParameter) {
               var10001 = ", ";
               break label16;
            }
         } catch (RuntimeException var2) {
            throw b(var2);
         }

         var10001 = "<";
      }

      var10000.append(var10001).append(var1);
      this.seenFormalParameter = true;
      this.seenInterfaceBound = false;
   }

   public SignatureVisitor visitClassBound() {
      this.separator = " extends ";
      this.startType();
      return this;
   }

   public SignatureVisitor visitInterfaceBound() {
      TraceSignatureVisitor var10000;
      String var10001;
      label16: {
         try {
            var10000 = this;
            if (this.seenInterfaceBound) {
               var10001 = ", ";
               break label16;
            }
         } catch (RuntimeException var1) {
            throw b(var1);
         }

         var10001 = " extends ";
      }

      var10000.separator = var10001;
      this.seenInterfaceBound = true;
      this.startType();
      return this;
   }

   public SignatureVisitor visitSuperclass() {
      this.endFormals();
      this.separator = " extends ";
      this.startType();
      return this;
   }

   public SignatureVisitor visitInterface() {
      TraceSignatureVisitor var10000;
      String var10001;
      label28: {
         try {
            var10000 = this;
            if (this.seenInterface) {
               var10001 = ", ";
               break label28;
            }
         } catch (RuntimeException var2) {
            throw b(var2);
         }

         try {
            if (this.isInterface) {
               var10001 = " extends ";
               break label28;
            }
         } catch (RuntimeException var1) {
            throw b(var1);
         }

         var10001 = " implements ";
      }

      var10000.separator = var10001;
      this.seenInterface = true;
      this.startType();
      return this;
   }

   public SignatureVisitor visitParameterType() {
      label16: {
         try {
            this.endFormals();
            if (this.seenParameter) {
               this.declaration.append(", ");
               break label16;
            }
         } catch (RuntimeException var1) {
            throw b(var1);
         }

         this.seenParameter = true;
         this.declaration.append('(');
      }

      this.startType();
      return this;
   }

   public SignatureVisitor visitReturnType() {
      label16: {
         try {
            this.endFormals();
            if (this.seenParameter) {
               this.seenParameter = false;
               break label16;
            }
         } catch (RuntimeException var1) {
            throw b(var1);
         }

         this.declaration.append('(');
      }

      this.declaration.append(')');
      this.returnType = new StringBuilder();
      return new TraceSignatureVisitor(this.returnType);
   }

   public SignatureVisitor visitExceptionType() {
      try {
         if (this.exceptions == null) {
            this.exceptions = new StringBuilder();
            return new TraceSignatureVisitor(this.exceptions);
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      this.exceptions.append(", ");
      return new TraceSignatureVisitor(this.exceptions);
   }

   public void visitBaseType(char var1) {
      label52: {
         label51: {
            label50: {
               label49: {
                  label48: {
                     label47: {
                        label46: {
                           label45: {
                              try {
                                 switch(var1) {
                                 case 'B':
                                    break label51;
                                 case 'C':
                                    break label46;
                                 case 'D':
                                 case 'E':
                                 case 'G':
                                 case 'H':
                                 case 'K':
                                 case 'L':
                                 case 'M':
                                 case 'N':
                                 case 'O':
                                 case 'P':
                                 case 'Q':
                                 case 'R':
                                 case 'T':
                                 case 'U':
                                 case 'W':
                                 case 'X':
                                 case 'Y':
                                 default:
                                    break;
                                 case 'F':
                                    break label45;
                                 case 'I':
                                    break label48;
                                 case 'J':
                                    break label50;
                                 case 'S':
                                    break label47;
                                 case 'V':
                                    this.declaration.append("void");
                                    break label52;
                                 case 'Z':
                                    break label49;
                                 }
                              } catch (RuntimeException var2) {
                                 throw b(var2);
                              }

                              this.declaration.append("double");
                              break label52;
                           }

                           this.declaration.append("float");
                           break label52;
                        }

                        this.declaration.append("char");
                        break label52;
                     }

                     this.declaration.append("short");
                     break label52;
                  }

                  this.declaration.append("int");
                  break label52;
               }

               this.declaration.append("boolean");
               break label52;
            }

            this.declaration.append("long");
            break label52;
         }

         this.declaration.append("byte");
      }

      this.endType();
   }

   public void visitTypeVariable(String var1) {
      this.declaration.append(var1);
      this.endType();
   }

   public SignatureVisitor visitArrayType() {
      this.startType();
      this.arrayStack |= 1;
      return this;
   }

   public void visitClassType(String param1) {
      // $FF: Couldn't be decompiled
   }

   public void visitInnerClassType(String var1) {
      try {
         if (this.argumentStack % 2 != 0) {
            this.declaration.append('>');
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      this.argumentStack /= 2;
      this.declaration.append('.');
      this.declaration.append(this.separator).append(var1.replace('/', '.'));
      this.separator = "";
      this.argumentStack *= 2;
   }

   public void visitTypeArgument() {
      label16: {
         try {
            if (this.argumentStack % 2 == 0) {
               ++this.argumentStack;
               this.declaration.append('<');
               break label16;
            }
         } catch (RuntimeException var1) {
            throw b(var1);
         }

         this.declaration.append(", ");
      }

      this.declaration.append('?');
   }

   public SignatureVisitor visitTypeArgument(char var1) {
      label34: {
         try {
            if (this.argumentStack % 2 == 0) {
               ++this.argumentStack;
               this.declaration.append('<');
               break label34;
            }
         } catch (RuntimeException var4) {
            throw b(var4);
         }

         this.declaration.append(", ");
      }

      label27: {
         try {
            if (var1 == '+') {
               this.declaration.append("? extends ");
               break label27;
            }
         } catch (RuntimeException var3) {
            throw b(var3);
         }

         try {
            if (var1 == '-') {
               this.declaration.append("? super ");
            }
         } catch (RuntimeException var2) {
            throw b(var2);
         }
      }

      this.startType();
      return this;
   }

   public void visitEnd() {
      try {
         if (this.argumentStack % 2 != 0) {
            this.declaration.append('>');
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      this.argumentStack /= 2;
      this.endType();
   }

   public String getDeclaration() {
      return this.declaration.toString();
   }

   public String getReturnType() {
      String var10000;
      try {
         if (this.returnType == null) {
            var10000 = null;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = this.returnType.toString();
      return var10000;
   }

   public String getExceptions() {
      String var10000;
      try {
         if (this.exceptions == null) {
            var10000 = null;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = this.exceptions.toString();
      return var10000;
   }

   private void endFormals() {
      try {
         if (this.seenFormalParameter) {
            this.declaration.append('>');
            this.seenFormalParameter = false;
         }

      } catch (RuntimeException var1) {
         throw b(var1);
      }
   }

   private void startType() {
      this.arrayStack *= 2;
   }

   private void endType() {
      try {
         if (this.arrayStack % 2 == 0) {
            this.arrayStack /= 2;
            return;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      try {
         while(this.arrayStack % 2 != 0) {
            this.arrayStack /= 2;
            this.declaration.append("[]");
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

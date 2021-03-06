package org.spongepowered.asm.lib.signature;

public class SignatureReader {
   private final String signature;

   public SignatureReader(String var1) {
      this.signature = var1;
   }

   public void accept(SignatureVisitor var1) {
      String var2 = this.signature;
      int var3 = var2.length();
      int var4;
      if (var2.charAt(0) == '<') {
         var4 = 2;

         char var6;
         do {
            int var5 = var2.indexOf(58, var4);
            var1.visitFormalTypeParameter(var2.substring(var4 - 1, var5));
            var4 = var5 + 1;
            var6 = var2.charAt(var4);
            if (var6 != 'L') {
               try {
                  if (var6 != '[' && var6 != 'T') {
                  }
               } catch (RuntimeException var8) {
                  throw b(var8);
               }
            }

            for(var4 = parseType(var2, var4, var1.visitClassBound()); (var6 = var2.charAt(var4++)) == ':'; var4 = parseType(var2, var4, var1.visitInterfaceBound())) {
            }
         } while(var6 != '>');
      } else {
         var4 = 0;
      }

      label88: {
         try {
            if (var2.charAt(var4) == '(') {
               ++var4;
               break label88;
            }
         } catch (RuntimeException var7) {
            throw b(var7);
         }

         for(var4 = parseType(var2, var4, var1.visitSuperclass()); var4 < var3; var4 = parseType(var2, var4, var1.visitInterface())) {
         }

         return;
      }

      while(var2.charAt(var4) != ')') {
         var4 = parseType(var2, var4, var1.visitParameterType());
      }

      for(var4 = parseType(var2, var4 + 1, var1.visitReturnType()); var4 < var3; var4 = parseType(var2, var4 + 1, var1.visitExceptionType())) {
      }

   }

   public void acceptType(SignatureVisitor var1) {
      parseType(this.signature, 0, var1);
   }

   private static int parseType(String var0, int var1, SignatureVisitor var2) {
      char var3;
      char var10000 = var3 = var0.charAt(var1++);

      label112: {
         try {
            switch(var10000) {
            case 'B':
            case 'C':
            case 'D':
            case 'F':
            case 'I':
            case 'J':
            case 'S':
            case 'V':
            case 'Z':
               var2.visitBaseType(var3);
               return var1;
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
            case 'U':
            case 'W':
            case 'X':
            case 'Y':
            default:
               break label112;
            case 'T':
               break;
            case '[':
               return parseType(var0, var1, var2.visitArrayType());
            }
         } catch (RuntimeException var12) {
            throw b(var12);
         }

         int var4 = var0.indexOf(59, var1);
         var2.visitTypeVariable(var0.substring(var1, var4));
         return var4 + 1;
      }

      int var5 = var1;
      boolean var6 = false;
      boolean var7 = false;

      while(true) {
         label63:
         while(true) {
            String var8;
            switch(var3 = var0.charAt(var1++)) {
            case '.':
            case ';':
               if (!var6) {
                  label105: {
                     var8 = var0.substring(var5, var1 - 1);

                     try {
                        if (var7) {
                           var2.visitInnerClassType(var8);
                           break label105;
                        }
                     } catch (RuntimeException var10) {
                        throw b(var10);
                     }

                     var2.visitClassType(var8);
                  }
               }

               try {
                  if (var3 == ';') {
                     var2.visitEnd();
                     return var1;
                  }
               } catch (RuntimeException var11) {
                  throw b(var11);
               }

               var5 = var1;
               var6 = false;
               var7 = true;
               break;
            case '<':
               var8 = var0.substring(var5, var1 - 1);

               label55: {
                  try {
                     if (var7) {
                        var2.visitInnerClassType(var8);
                        break label55;
                     }
                  } catch (RuntimeException var9) {
                     throw b(var9);
                  }

                  var2.visitClassType(var8);
               }

               var6 = true;

               while(true) {
                  while(true) {
                     switch(var3 = var0.charAt(var1)) {
                     case '*':
                        ++var1;
                        var2.visitTypeArgument();
                        break;
                     case '+':
                     case '-':
                        var1 = parseType(var0, var1 + 1, var2.visitTypeArgument(var3));
                        break;
                     case '>':
                        continue label63;
                     default:
                        var1 = parseType(var0, var1, var2.visitTypeArgument('='));
                     }
                  }
               }
            }
         }
      }
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

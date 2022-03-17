package org.spongepowered.asm.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class ClassSignature$Token implements ClassSignature$IToken {
   static final String SYMBOLS = "+-*";
   private final boolean inner;
   private boolean array;
   private char symbol;
   private String type;
   private List<ClassSignature$Token> classBound;
   private List<ClassSignature$Token> ifaceBound;
   private List<ClassSignature$IToken> signature;
   private List<ClassSignature$IToken> suffix;
   private ClassSignature$Token tail;

   ClassSignature$Token() {
      this(false);
   }

   ClassSignature$Token(String var1) {
      this(var1, false);
   }

   ClassSignature$Token(char var1) {
      this();
      this.symbol = var1;
   }

   ClassSignature$Token(boolean var1) {
      this((String)null, var1);
   }

   ClassSignature$Token(String var1, boolean var2) {
      this.symbol = 0;
      this.inner = var2;
      this.type = var1;
   }

   ClassSignature$Token setSymbol(char param1) {
      // $FF: Couldn't be decompiled
   }

   ClassSignature$Token setType(String var1) {
      try {
         if (this.type == null) {
            this.type = var1;
         }

         return this;
      } catch (RuntimeException var2) {
         throw b(var2);
      }
   }

   boolean hasClassBound() {
      boolean var10000;
      try {
         if (this.classBound != null) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   boolean hasInterfaceBound() {
      boolean var10000;
      try {
         if (this.ifaceBound != null) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public ClassSignature$IToken setArray(boolean var1) {
      this.array |= var1;
      return this;
   }

   public ClassSignature$IToken setWildcard(char var1) {
      try {
         if ("+-".indexOf(var1) == -1) {
            return this;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      return this.setSymbol(var1);
   }

   private List<ClassSignature$Token> getClassBound() {
      try {
         if (this.classBound == null) {
            this.classBound = new ArrayList();
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      return this.classBound;
   }

   private List<ClassSignature$Token> getIfaceBound() {
      try {
         if (this.ifaceBound == null) {
            this.ifaceBound = new ArrayList();
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      return this.ifaceBound;
   }

   private List<ClassSignature$IToken> getSignature() {
      try {
         if (this.signature == null) {
            this.signature = new ArrayList();
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      return this.signature;
   }

   private List<ClassSignature$IToken> getSuffix() {
      try {
         if (this.suffix == null) {
            this.suffix = new ArrayList();
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      return this.suffix;
   }

   ClassSignature$IToken addTypeArgument(char var1) {
      try {
         if (this.tail != null) {
            return this.tail.addTypeArgument(var1);
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      ClassSignature$Token var2 = new ClassSignature$Token(var1);
      this.getSignature().add(var2);
      return var2;
   }

   ClassSignature$IToken addTypeArgument(String var1) {
      try {
         if (this.tail != null) {
            return this.tail.addTypeArgument(var1);
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      ClassSignature$Token var2 = new ClassSignature$Token(var1);
      this.getSignature().add(var2);
      return var2;
   }

   ClassSignature$IToken addTypeArgument(ClassSignature$Token var1) {
      try {
         if (this.tail != null) {
            return this.tail.addTypeArgument(var1);
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      this.getSignature().add(var1);
      return var1;
   }

   ClassSignature$IToken addTypeArgument(ClassSignature$TokenHandle var1) {
      try {
         if (this.tail != null) {
            return this.tail.addTypeArgument(var1);
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      ClassSignature$TokenHandle var2 = var1.clone();
      this.getSignature().add(var2);
      return var2;
   }

   ClassSignature$Token addBound(String var1, boolean var2) {
      try {
         if (var2) {
            return this.addClassBound(var1);
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      return this.addInterfaceBound(var1);
   }

   ClassSignature$Token addClassBound(String var1) {
      ClassSignature$Token var2 = new ClassSignature$Token(var1);
      this.getClassBound().add(var2);
      return var2;
   }

   ClassSignature$Token addInterfaceBound(String var1) {
      ClassSignature$Token var2 = new ClassSignature$Token(var1);
      this.getIfaceBound().add(var2);
      return var2;
   }

   ClassSignature$Token addInnerClass(String var1) {
      this.tail = new ClassSignature$Token(var1, true);
      this.getSuffix().add(this.tail);
      return this.tail;
   }

   public String toString() {
      return this.asType();
   }

   public String asBound() {
      StringBuilder var1 = new StringBuilder();

      try {
         if (this.type != null) {
            var1.append(this.type);
         }
      } catch (RuntimeException var4) {
         throw b(var4);
      }

      Iterator var2;
      ClassSignature$Token var3;
      if (this.classBound != null) {
         var2 = this.classBound.iterator();

         while(var2.hasNext()) {
            var3 = (ClassSignature$Token)var2.next();
            var1.append(var3.asType());
         }
      }

      if (this.ifaceBound != null) {
         var2 = this.ifaceBound.iterator();

         while(var2.hasNext()) {
            var3 = (ClassSignature$Token)var2.next();
            var1.append(':').append(var3.asType());
         }
      }

      return var1.toString();
   }

   public String asType() {
      return this.asType(false);
   }

   public String asType(boolean var1) {
      StringBuilder var2 = new StringBuilder();

      try {
         if (this.array) {
            var2.append('[');
         }
      } catch (RuntimeException var10) {
         throw b(var10);
      }

      try {
         if (this.symbol != 0) {
            var2.append(this.symbol);
         }
      } catch (RuntimeException var6) {
         throw b(var6);
      }

      try {
         if (this.type == null) {
            return var2.toString();
         }
      } catch (RuntimeException var9) {
         throw b(var9);
      }

      try {
         if (!this.inner) {
            var2.append('L');
         }
      } catch (RuntimeException var5) {
         throw b(var5);
      }

      label72: {
         Iterator var3;
         ClassSignature$IToken var4;
         label89: {
            try {
               var2.append(this.type);
               if (var1) {
                  break label72;
               }

               if (this.signature == null) {
                  break label89;
               }
            } catch (RuntimeException var8) {
               throw b(var8);
            }

            var2.append('<');
            var3 = this.signature.iterator();

            while(var3.hasNext()) {
               var4 = (ClassSignature$IToken)var3.next();
               var2.append(var4.asType());
            }

            var2.append('>');
         }

         if (this.suffix != null) {
            var3 = this.suffix.iterator();

            while(var3.hasNext()) {
               var4 = (ClassSignature$IToken)var3.next();
               var2.append('.').append(var4.asType());
            }
         }
      }

      try {
         if (!this.inner) {
            var2.append(';');
         }
      } catch (RuntimeException var7) {
         throw b(var7);
      }

      return var2.toString();
   }

   boolean isRaw() {
      boolean var10000;
      try {
         if (this.signature == null) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   String getClassType() {
      String var10000;
      try {
         if (this.type != null) {
            var10000 = this.type;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = "java/lang/Object";
      return var10000;
   }

   public ClassSignature$Token asToken() {
      return this;
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

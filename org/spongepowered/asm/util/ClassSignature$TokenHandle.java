package org.spongepowered.asm.util;

class ClassSignature$TokenHandle implements ClassSignature$IToken {
   final ClassSignature$Token token;
   boolean array;
   char wildcard;
   final ClassSignature this$0;

   ClassSignature$TokenHandle(ClassSignature var1) {
      this(var1, new ClassSignature$Token());
   }

   ClassSignature$TokenHandle(ClassSignature var1, ClassSignature$Token var2) {
      this.this$0 = var1;
      this.token = var2;
   }

   public ClassSignature$IToken setArray(boolean var1) {
      this.array |= var1;
      return this;
   }

   public ClassSignature$IToken setWildcard(char var1) {
      try {
         if ("+-".indexOf(var1) > -1) {
            this.wildcard = var1;
         }

         return this;
      } catch (RuntimeException var2) {
         throw b(var2);
      }
   }

   public String asBound() {
      return this.token.asBound();
   }

   public String asType() {
      StringBuilder var1 = new StringBuilder();

      try {
         if (this.wildcard > 0) {
            var1.append(this.wildcard);
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      try {
         if (this.array) {
            var1.append('[');
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      return var1.append(this.this$0.getTypeVar(this)).toString();
   }

   public ClassSignature$Token asToken() {
      return this.token;
   }

   public String toString() {
      return this.token.toString();
   }

   public ClassSignature$TokenHandle clone() {
      return new ClassSignature$TokenHandle(this.this$0, this.token);
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

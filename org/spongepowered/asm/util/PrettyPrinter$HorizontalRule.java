package org.spongepowered.asm.util;

import com.google.common.base.Strings;

class PrettyPrinter$HorizontalRule implements PrettyPrinter$ISpecialEntry {
   private final char[] hrChars;
   final PrettyPrinter this$0;

   public PrettyPrinter$HorizontalRule(PrettyPrinter var1, char... var2) {
      this.this$0 = var1;
      this.hrChars = var2;
   }

   public String toString() {
      return Strings.repeat(new String(this.hrChars), this.this$0.width + 2);
   }
}

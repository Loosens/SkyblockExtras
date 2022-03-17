package org.spongepowered.asm.util;

class PrettyPrinter$CentredText {
   private final Object centred;
   final PrettyPrinter this$0;

   public PrettyPrinter$CentredText(PrettyPrinter var1, Object var2) {
      this.this$0 = var1;
      this.centred = var2;
   }

   public String toString() {
      String var1 = this.centred.toString();
      return String.format("%" + ((this.this$0.width - var1.length()) / 2 + var1.length()) + "s", var1);
   }
}

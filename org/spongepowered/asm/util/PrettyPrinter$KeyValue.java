package org.spongepowered.asm.util;

class PrettyPrinter$KeyValue implements PrettyPrinter$IVariableWidthEntry {
   private final String key;
   private final Object value;
   final PrettyPrinter this$0;

   public PrettyPrinter$KeyValue(PrettyPrinter var1, String var2, Object var3) {
      this.this$0 = var1;
      this.key = var2;
      this.value = var3;
   }

   public String toString() {
      return String.format(this.this$0.kvFormat, this.key, this.value);
   }

   public int getWidth() {
      return this.toString().length();
   }
}

package org.spongepowered.asm.util;

class PrettyPrinter$Row implements PrettyPrinter$IVariableWidthEntry {
   final PrettyPrinter$Table table;
   final String[] args;

   public PrettyPrinter$Row(PrettyPrinter$Table var1, Object... var2) {
      this.table = var1.grow(var2.length);
      this.args = new String[var2.length];
      int var3 = 0;

      try {
         while(var3 < var2.length) {
            this.args[var3] = var2[var3].toString();
            ((PrettyPrinter$Column)this.table.columns.get(var3)).setMinWidth(this.args[var3].length());
            ++var3;
         }

      } catch (RuntimeException var4) {
         throw b(var4);
      }
   }

   public String toString() {
      Object[] var1 = new Object[this.table.columns.size()];

      for(int var2 = 0; var2 < var1.length; ++var2) {
         PrettyPrinter$Column var3 = (PrettyPrinter$Column)this.table.columns.get(var2);

         try {
            if (var2 >= this.args.length) {
               var1[var2] = "";
               continue;
            }
         } catch (RuntimeException var5) {
            throw b(var5);
         }

         Object[] var10000;
         int var10001;
         String var10002;
         label28: {
            try {
               var10000 = var1;
               var10001 = var2;
               if (this.args[var2].length() > var3.getMaxWidth()) {
                  var10002 = this.args[var2].substring(0, var3.getMaxWidth());
                  break label28;
               }
            } catch (RuntimeException var4) {
               throw b(var4);
            }

            var10002 = this.args[var2];
         }

         var10000[var10001] = var10002;
      }

      return String.format(this.table.format, var1);
   }

   public int getWidth() {
      return this.toString().length();
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

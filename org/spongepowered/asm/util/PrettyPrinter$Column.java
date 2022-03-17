package org.spongepowered.asm.util;

class PrettyPrinter$Column {
   private final PrettyPrinter$Table table;
   private PrettyPrinter$Alignment align;
   private int minWidth;
   private int maxWidth;
   private int size;
   private String title;
   private String format;

   PrettyPrinter$Column(PrettyPrinter$Table var1) {
      this.align = PrettyPrinter$Alignment.LEFT;
      this.minWidth = 1;
      this.maxWidth = Integer.MAX_VALUE;
      this.size = 0;
      this.title = "";
      this.format = "%s";
      this.table = var1;
   }

   PrettyPrinter$Column(PrettyPrinter$Table var1, String var2) {
      this(var1);
      this.title = var2;
      this.minWidth = var2.length();
      this.updateFormat();
   }

   PrettyPrinter$Column(PrettyPrinter$Table var1, PrettyPrinter$Alignment var2, int var3, String var4) {
      this(var1, var4);
      this.align = var2;
      this.size = var3;
   }

   void setAlignment(PrettyPrinter$Alignment var1) {
      this.align = var1;
      this.updateFormat();
   }

   void setWidth(int var1) {
      try {
         if (var1 > this.size) {
            this.size = var1;
            this.updateFormat();
         }

      } catch (RuntimeException var2) {
         throw b(var2);
      }
   }

   void setMinWidth(int var1) {
      try {
         if (var1 > this.minWidth) {
            this.minWidth = var1;
            this.updateFormat();
         }

      } catch (RuntimeException var2) {
         throw b(var2);
      }
   }

   void setMaxWidth(int var1) {
      this.size = Math.min(this.size, this.maxWidth);
      this.maxWidth = Math.max(1, var1);
      this.updateFormat();
   }

   void setTitle(String var1) {
      this.title = var1;
      this.setWidth(var1.length());
   }

   private void updateFormat() {
      int var10000;
      int var10001;
      label30: {
         try {
            var10000 = this.maxWidth;
            if (this.size == 0) {
               var10001 = this.minWidth;
               break label30;
            }
         } catch (RuntimeException var3) {
            throw b(var3);
         }

         var10001 = this.size;
      }

      int var1 = Math.min(var10000, var10001);

      String var10002;
      PrettyPrinter$Column var4;
      StringBuilder var5;
      label22: {
         try {
            var4 = this;
            var5 = (new StringBuilder()).append("%");
            if (this.align == PrettyPrinter$Alignment.RIGHT) {
               var10002 = "";
               break label22;
            }
         } catch (RuntimeException var2) {
            throw b(var2);
         }

         var10002 = "-";
      }

      var4.format = var5.append(var10002).append(var1).append("s").toString();
      this.table.updateFormat();
   }

   int getMaxWidth() {
      return this.maxWidth;
   }

   String getTitle() {
      return this.title;
   }

   String getFormat() {
      return this.format;
   }

   public String toString() {
      try {
         if (this.title.length() > this.maxWidth) {
            return this.title.substring(0, this.maxWidth);
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      return this.title;
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

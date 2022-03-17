package org.spongepowered.asm.util;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class PrettyPrinter$Table implements PrettyPrinter$IVariableWidthEntry {
   final List<PrettyPrinter$Column> columns = new ArrayList();
   final List<PrettyPrinter$Row> rows = new ArrayList();
   String format = "%s";
   int colSpacing = 2;
   boolean addHeader = true;

   void headerAdded() {
      this.addHeader = false;
   }

   void setColSpacing(int var1) {
      this.colSpacing = Math.max(0, var1);
      this.updateFormat();
   }

   PrettyPrinter$Table grow(int var1) {
      try {
         while(this.columns.size() < var1) {
            this.columns.add(new PrettyPrinter$Column(this));
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      this.updateFormat();
      return this;
   }

   PrettyPrinter$Column add(PrettyPrinter$Column var1) {
      this.columns.add(var1);
      return var1;
   }

   PrettyPrinter$Row add(PrettyPrinter$Row var1) {
      this.rows.add(var1);
      return var1;
   }

   PrettyPrinter$Column addColumn(String var1) {
      return this.add(new PrettyPrinter$Column(this, var1));
   }

   PrettyPrinter$Column addColumn(PrettyPrinter$Alignment var1, int var2, String var3) {
      return this.add(new PrettyPrinter$Column(this, var1, var2, var3));
   }

   PrettyPrinter$Row addRow(Object... var1) {
      return this.add(new PrettyPrinter$Row(this, var1));
   }

   void updateFormat() {
      String var1 = Strings.repeat(" ", this.colSpacing);
      StringBuilder var2 = new StringBuilder();
      boolean var3 = false;
      Iterator var4 = this.columns.iterator();

      while(var4.hasNext()) {
         PrettyPrinter$Column var5 = (PrettyPrinter$Column)var4.next();

         try {
            if (var3) {
               var2.append(var1);
            }
         } catch (RuntimeException var6) {
            throw b(var6);
         }

         var3 = true;
         var2.append(var5.getFormat());
      }

      this.format = var2.toString();
   }

   String getFormat() {
      return this.format;
   }

   Object[] getTitles() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.columns.iterator();

      while(var2.hasNext()) {
         PrettyPrinter$Column var3 = (PrettyPrinter$Column)var2.next();
         var1.add(var3.getTitle());
      }

      return var1.toArray();
   }

   public String toString() {
      // $FF: Couldn't be decompiled
   }

   public int getWidth() {
      String var1 = this.toString();

      int var10000;
      try {
         if (var1 != null) {
            var10000 = var1.length();
            return var10000;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      var10000 = 0;
      return var10000;
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

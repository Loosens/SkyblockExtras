package org.spongepowered.asm.lib;

final class Item {
   int index;
   int type;
   int intVal;
   long longVal;
   String strVal1;
   String strVal2;
   String strVal3;
   int hashCode;
   Item next;

   Item() {
   }

   Item(int var1) {
      this.index = var1;
   }

   Item(int var1, Item var2) {
      this.index = var1;
      this.type = var2.type;
      this.intVal = var2.intVal;
      this.longVal = var2.longVal;
      this.strVal1 = var2.strVal1;
      this.strVal2 = var2.strVal2;
      this.strVal3 = var2.strVal3;
      this.hashCode = var2.hashCode;
   }

   void set(int var1) {
      this.type = 3;
      this.intVal = var1;
      this.hashCode = Integer.MAX_VALUE & this.type + var1;
   }

   void set(long var1) {
      this.type = 5;
      this.longVal = var1;
      this.hashCode = Integer.MAX_VALUE & this.type + (int)var1;
   }

   void set(float var1) {
      this.type = 4;
      this.intVal = Float.floatToRawIntBits(var1);
      this.hashCode = Integer.MAX_VALUE & this.type + (int)var1;
   }

   void set(double var1) {
      this.type = 6;
      this.longVal = Double.doubleToRawLongBits(var1);
      this.hashCode = Integer.MAX_VALUE & this.type + (int)var1;
   }

   void set(int var1, String var2, String var3, String var4) {
      label25: {
         label24: {
            try {
               this.type = var1;
               this.strVal1 = var2;
               this.strVal2 = var3;
               this.strVal3 = var4;
               switch(var1) {
               case 1:
               case 8:
               case 16:
               case 30:
                  break label25;
               case 7:
                  this.intVal = 0;
                  break label25;
               case 12:
                  break label24;
               }
            } catch (RuntimeException var5) {
               throw b(var5);
            }

            this.hashCode = Integer.MAX_VALUE & var1 + var2.hashCode() * var3.hashCode() * var4.hashCode();
            return;
         }

         this.hashCode = Integer.MAX_VALUE & var1 + var2.hashCode() * var3.hashCode();
         return;
      }

      this.hashCode = Integer.MAX_VALUE & var1 + var2.hashCode();
   }

   void set(String var1, String var2, int var3) {
      this.type = 18;
      this.longVal = (long)var3;
      this.strVal1 = var1;
      this.strVal2 = var2;
      this.hashCode = Integer.MAX_VALUE & 18 + var3 * this.strVal1.hashCode() * this.strVal2.hashCode();
   }

   void set(int var1, int var2) {
      this.type = 33;
      this.intVal = var1;
      this.hashCode = var2;
   }

   boolean isEqualTo(Item param1) {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

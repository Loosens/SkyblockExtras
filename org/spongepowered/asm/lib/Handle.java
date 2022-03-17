package org.spongepowered.asm.lib;

public final class Handle {
   final int tag;
   final String owner;
   final String name;
   final String desc;
   final boolean itf;

   /** @deprecated */
   @Deprecated
   public Handle(int var1, String var2, String var3, String var4) {
      this(var1, var2, var3, var4, var1 == 9);
   }

   public Handle(int var1, String var2, String var3, String var4, boolean var5) {
      this.tag = var1;
      this.owner = var2;
      this.name = var3;
      this.desc = var4;
      this.itf = var5;
   }

   public int getTag() {
      return this.tag;
   }

   public String getOwner() {
      return this.owner;
   }

   public String getName() {
      return this.name;
   }

   public String getDesc() {
      return this.desc;
   }

   public boolean isInterface() {
      return this.itf;
   }

   public boolean equals(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public int hashCode() {
      int var10000;
      byte var10001;
      try {
         var10000 = this.tag;
         if (this.itf) {
            var10001 = 64;
            return var10000 + var10001 + this.owner.hashCode() * this.name.hashCode() * this.desc.hashCode();
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10001 = 0;
      return var10000 + var10001 + this.owner.hashCode() * this.name.hashCode() * this.desc.hashCode();
   }

   public String toString() {
      StringBuilder var10000;
      String var10001;
      try {
         var10000 = (new StringBuilder()).append(this.owner).append('.').append(this.name).append(this.desc).append(" (").append(this.tag);
         if (this.itf) {
            var10001 = " itf";
            return var10000.append(var10001).append(')').toString();
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10001 = "";
      return var10000.append(var10001).append(')').toString();
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

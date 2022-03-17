package org.spongepowered.asm.lib;

public class ByteVector {
   byte[] data;
   int length;

   public ByteVector() {
      this.data = new byte[64];
   }

   public ByteVector(int var1) {
      this.data = new byte[var1];
   }

   public ByteVector putByte(int var1) {
      int var2 = this.length;

      try {
         if (var2 + 1 > this.data.length) {
            this.enlarge(1);
         }
      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }

      this.data[var2++] = (byte)var1;
      this.length = var2;
      return this;
   }

   ByteVector put11(int var1, int var2) {
      int var3 = this.length;

      try {
         if (var3 + 2 > this.data.length) {
            this.enlarge(2);
         }
      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }

      byte[] var4 = this.data;
      var4[var3++] = (byte)var1;
      var4[var3++] = (byte)var2;
      this.length = var3;
      return this;
   }

   public ByteVector putShort(int var1) {
      int var2 = this.length;

      try {
         if (var2 + 2 > this.data.length) {
            this.enlarge(2);
         }
      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }

      byte[] var3 = this.data;
      var3[var2++] = (byte)(var1 >>> 8);
      var3[var2++] = (byte)var1;
      this.length = var2;
      return this;
   }

   ByteVector put12(int var1, int var2) {
      int var3 = this.length;

      try {
         if (var3 + 3 > this.data.length) {
            this.enlarge(3);
         }
      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }

      byte[] var4 = this.data;
      var4[var3++] = (byte)var1;
      var4[var3++] = (byte)(var2 >>> 8);
      var4[var3++] = (byte)var2;
      this.length = var3;
      return this;
   }

   public ByteVector putInt(int var1) {
      int var2 = this.length;

      try {
         if (var2 + 4 > this.data.length) {
            this.enlarge(4);
         }
      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }

      byte[] var3 = this.data;
      var3[var2++] = (byte)(var1 >>> 24);
      var3[var2++] = (byte)(var1 >>> 16);
      var3[var2++] = (byte)(var1 >>> 8);
      var3[var2++] = (byte)var1;
      this.length = var2;
      return this;
   }

   public ByteVector putLong(long var1) {
      int var3 = this.length;

      try {
         if (var3 + 8 > this.data.length) {
            this.enlarge(8);
         }
      } catch (IllegalArgumentException var6) {
         throw b(var6);
      }

      byte[] var4 = this.data;
      int var5 = (int)(var1 >>> 32);
      var4[var3++] = (byte)(var5 >>> 24);
      var4[var3++] = (byte)(var5 >>> 16);
      var4[var3++] = (byte)(var5 >>> 8);
      var4[var3++] = (byte)var5;
      var5 = (int)var1;
      var4[var3++] = (byte)(var5 >>> 24);
      var4[var3++] = (byte)(var5 >>> 16);
      var4[var3++] = (byte)(var5 >>> 8);
      var4[var3++] = (byte)var5;
      this.length = var3;
      return this;
   }

   public ByteVector putUTF8(String param1) {
      // $FF: Couldn't be decompiled
   }

   ByteVector encodeUTF8(String param1, int param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   public ByteVector putByteArray(byte[] var1, int var2, int var3) {
      try {
         if (this.length + var3 > this.data.length) {
            this.enlarge(var3);
         }
      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }

      try {
         if (var1 != null) {
            System.arraycopy(var1, var2, this.data, this.length, var3);
         }
      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }

      this.length += var3;
      return this;
   }

   private void enlarge(int var1) {
      int var2 = 2 * this.data.length;
      int var3 = this.length + var1;

      int var10000;
      label17: {
         try {
            if (var2 > var3) {
               var10000 = var2;
               break label17;
            }
         } catch (IllegalArgumentException var5) {
            throw b(var5);
         }

         var10000 = var3;
      }

      byte[] var4 = new byte[var10000];
      System.arraycopy(this.data, 0, var4, 0, this.length);
      this.data = var4;
   }

   private static IllegalArgumentException b(IllegalArgumentException var0) {
      return var0;
   }
}

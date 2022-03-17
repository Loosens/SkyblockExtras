package org.spongepowered.asm.mixin.injection.callback;

import org.spongepowered.asm.lib.Type;

public class CallbackInfoReturnable<R> extends CallbackInfo {
   private R returnValue;

   public CallbackInfoReturnable(String var1, boolean var2) {
      super(var1, var2);
      this.returnValue = null;
   }

   public CallbackInfoReturnable(String var1, boolean var2, R var3) {
      super(var1, var2);
      this.returnValue = var3;
   }

   public CallbackInfoReturnable(String var1, boolean var2, byte var3) {
      super(var1, var2);
      this.returnValue = var3;
   }

   public CallbackInfoReturnable(String var1, boolean var2, char var3) {
      super(var1, var2);
      this.returnValue = var3;
   }

   public CallbackInfoReturnable(String var1, boolean var2, double var3) {
      super(var1, var2);
      this.returnValue = var3;
   }

   public CallbackInfoReturnable(String var1, boolean var2, float var3) {
      super(var1, var2);
      this.returnValue = var3;
   }

   public CallbackInfoReturnable(String var1, boolean var2, int var3) {
      super(var1, var2);
      this.returnValue = var3;
   }

   public CallbackInfoReturnable(String var1, boolean var2, long var3) {
      super(var1, var2);
      this.returnValue = var3;
   }

   public CallbackInfoReturnable(String var1, boolean var2, short var3) {
      super(var1, var2);
      this.returnValue = var3;
   }

   public CallbackInfoReturnable(String var1, boolean var2, boolean var3) {
      super(var1, var2);
      this.returnValue = var3;
   }

   public void setReturnValue(R var1) throws CancellationException {
      super.cancel();
      this.returnValue = var1;
   }

   public R getReturnValue() {
      return this.returnValue;
   }

   public byte getReturnValueB() {
      try {
         if (this.returnValue == null) {
            return 0;
         }
      } catch (CancellationException var1) {
         throw c(var1);
      }

      return (Byte)this.returnValue;
   }

   public char getReturnValueC() {
      try {
         if (this.returnValue == null) {
            return '\u0000';
         }
      } catch (CancellationException var1) {
         throw c(var1);
      }

      return (Character)this.returnValue;
   }

   public double getReturnValueD() {
      try {
         if (this.returnValue == null) {
            return 0.0D;
         }
      } catch (CancellationException var1) {
         throw c(var1);
      }

      return (Double)this.returnValue;
   }

   public float getReturnValueF() {
      try {
         if (this.returnValue == null) {
            return 0.0F;
         }
      } catch (CancellationException var1) {
         throw c(var1);
      }

      return (Float)this.returnValue;
   }

   public int getReturnValueI() {
      try {
         if (this.returnValue == null) {
            return 0;
         }
      } catch (CancellationException var1) {
         throw c(var1);
      }

      return (Integer)this.returnValue;
   }

   public long getReturnValueJ() {
      try {
         if (this.returnValue == null) {
            return 0L;
         }
      } catch (CancellationException var1) {
         throw c(var1);
      }

      return (Long)this.returnValue;
   }

   public short getReturnValueS() {
      try {
         if (this.returnValue == null) {
            return 0;
         }
      } catch (CancellationException var1) {
         throw c(var1);
      }

      return (Short)this.returnValue;
   }

   public boolean getReturnValueZ() {
      try {
         if (this.returnValue == null) {
            return false;
         }
      } catch (CancellationException var1) {
         throw c(var1);
      }

      return (Boolean)this.returnValue;
   }

   static String getReturnAccessor(Type param0) {
      // $FF: Couldn't be decompiled
   }

   static String getReturnDescriptor(Type param0) {
      // $FF: Couldn't be decompiled
   }

   private static CancellationException c(CancellationException var0) {
      return var0;
   }
}

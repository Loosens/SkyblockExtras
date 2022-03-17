package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.Type;

public class BasicValue implements Value {
   public static final BasicValue UNINITIALIZED_VALUE = new BasicValue((Type)null);
   public static final BasicValue INT_VALUE;
   public static final BasicValue FLOAT_VALUE;
   public static final BasicValue LONG_VALUE;
   public static final BasicValue DOUBLE_VALUE;
   public static final BasicValue REFERENCE_VALUE;
   public static final BasicValue RETURNADDRESS_VALUE;
   private final Type type;

   public BasicValue(Type var1) {
      this.type = var1;
   }

   public Type getType() {
      return this.type;
   }

   public int getSize() {
      // $FF: Couldn't be decompiled
   }

   public boolean isReference() {
      // $FF: Couldn't be decompiled
   }

   public boolean equals(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public int hashCode() {
      int var10000;
      try {
         if (this.type == null) {
            var10000 = 0;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = this.type.hashCode();
      return var10000;
   }

   public String toString() {
      try {
         if (this == UNINITIALIZED_VALUE) {
            return ".";
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      try {
         if (this == RETURNADDRESS_VALUE) {
            return "A";
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      try {
         if (this == REFERENCE_VALUE) {
            return "R";
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      return this.type.getDescriptor();
   }

   static {
      INT_VALUE = new BasicValue(Type.INT_TYPE);
      FLOAT_VALUE = new BasicValue(Type.FLOAT_TYPE);
      LONG_VALUE = new BasicValue(Type.LONG_TYPE);
      DOUBLE_VALUE = new BasicValue(Type.DOUBLE_TYPE);
      REFERENCE_VALUE = new BasicValue(Type.getObjectType("java/lang/Object"));
      RETURNADDRESS_VALUE = new BasicValue(Type.VOID_TYPE);
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

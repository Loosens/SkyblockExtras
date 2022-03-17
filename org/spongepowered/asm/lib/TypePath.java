package org.spongepowered.asm.lib;

public class TypePath {
   public static final int ARRAY_ELEMENT = 0;
   public static final int INNER_TYPE = 1;
   public static final int WILDCARD_BOUND = 2;
   public static final int TYPE_ARGUMENT = 3;
   byte[] b;
   int offset;

   TypePath(byte[] var1, int var2) {
      this.b = var1;
      this.offset = var2;
   }

   public int getLength() {
      return this.b[this.offset];
   }

   public int getStep(int var1) {
      return this.b[this.offset + 2 * var1 + 1];
   }

   public int getStepArgument(int var1) {
      return this.b[this.offset + 2 * var1 + 2];
   }

   public static TypePath fromString(String param0) {
      // $FF: Couldn't be decompiled
   }

   public String toString() {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

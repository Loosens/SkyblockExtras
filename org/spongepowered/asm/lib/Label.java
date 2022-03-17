package org.spongepowered.asm.lib;

public class Label {
   static final int DEBUG = 1;
   static final int RESOLVED = 2;
   static final int RESIZED = 4;
   static final int PUSHED = 8;
   static final int TARGET = 16;
   static final int STORE = 32;
   static final int REACHABLE = 64;
   static final int JSR = 128;
   static final int RET = 256;
   static final int SUBROUTINE = 512;
   static final int VISITED = 1024;
   static final int VISITED2 = 2048;
   public Object info;
   int status;
   int line;
   int position;
   private int referenceCount;
   private int[] srcAndRefPositions;
   int inputStackTop;
   int outputStackMax;
   Frame frame;
   Label successor;
   Edge successors;
   Label next;

   public int getOffset() {
      try {
         if ((this.status & 2) == 0) {
            throw new IllegalStateException("Label offset position has not been resolved yet");
         }
      } catch (IllegalStateException var1) {
         throw b(var1);
      }

      return this.position;
   }

   void put(MethodWriter param1, ByteVector param2, int param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   private void addReference(int var1, int var2) {
      try {
         if (this.srcAndRefPositions == null) {
            this.srcAndRefPositions = new int[6];
         }
      } catch (IllegalStateException var4) {
         throw b(var4);
      }

      if (this.referenceCount >= this.srcAndRefPositions.length) {
         int[] var3 = new int[this.srcAndRefPositions.length + 6];
         System.arraycopy(this.srcAndRefPositions, 0, var3, 0, this.srcAndRefPositions.length);
         this.srcAndRefPositions = var3;
      }

      this.srcAndRefPositions[this.referenceCount++] = var1;
      this.srcAndRefPositions[this.referenceCount++] = var2;
   }

   boolean resolve(MethodWriter var1, int var2, byte[] var3) {
      boolean var4 = false;
      this.status |= 2;
      this.position = var2;
      int var5 = 0;

      while(true) {
         while(var5 < this.referenceCount) {
            int var6 = this.srcAndRefPositions[var5++];
            int var7 = this.srcAndRefPositions[var5++];
            int var8;
            if (var6 >= 0) {
               var8 = var2 - var6;

               label53: {
                  try {
                     if (var8 >= -32768 && var8 <= 32767) {
                        break label53;
                     }
                  } catch (IllegalStateException var11) {
                     throw b(var11);
                  }

                  int var9 = var3[var7 - 1] & 255;

                  label39: {
                     try {
                        if (var9 <= 168) {
                           var3[var7 - 1] = (byte)(var9 + 49);
                           break label39;
                        }
                     } catch (IllegalStateException var10) {
                        throw b(var10);
                     }

                     var3[var7 - 1] = (byte)(var9 + 20);
                  }

                  var4 = true;
               }

               var3[var7++] = (byte)(var8 >>> 8);
               var3[var7] = (byte)var8;
            } else {
               var8 = var2 + var6 + 1;
               var3[var7++] = (byte)(var8 >>> 24);
               var3[var7++] = (byte)(var8 >>> 16);
               var3[var7++] = (byte)(var8 >>> 8);
               var3[var7] = (byte)var8;
            }
         }

         return var4;
      }
   }

   Label getFirst() {
      Label var10000;
      try {
         if (this.frame == null) {
            var10000 = this;
            return var10000;
         }
      } catch (IllegalStateException var1) {
         throw b(var1);
      }

      var10000 = this.frame.owner;
      return var10000;
   }

   boolean inSubroutine(long param1) {
      // $FF: Couldn't be decompiled
   }

   boolean inSameSubroutine(Label param1) {
      // $FF: Couldn't be decompiled
   }

   void addToSubroutine(long var1, int var3) {
      try {
         if ((this.status & 1024) == 0) {
            this.status |= 1024;
            this.srcAndRefPositions = new int[var3 / 32 + 1];
         }
      } catch (IllegalStateException var4) {
         throw b(var4);
      }

      int[] var10000 = this.srcAndRefPositions;
      var10000[(int)(var1 >>> 32)] |= (int)var1;
   }

   void visitSubroutine(Label param1, long param2, int param4) {
      // $FF: Couldn't be decompiled
   }

   public String toString() {
      return "L" + System.identityHashCode(this);
   }

   private static IllegalStateException b(IllegalStateException var0) {
      return var0;
   }
}

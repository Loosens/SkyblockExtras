package org.spongepowered.asm.lib;

final class AnnotationWriter extends AnnotationVisitor {
   private final ClassWriter cw;
   private int size;
   private final boolean named;
   private final ByteVector bv;
   private final ByteVector parent;
   private final int offset;
   AnnotationWriter next;
   AnnotationWriter prev;

   AnnotationWriter(ClassWriter var1, boolean var2, ByteVector var3, ByteVector var4, int var5) {
      super(327680);
      this.cw = var1;
      this.named = var2;
      this.bv = var3;
      this.parent = var4;
      this.offset = var5;
   }

   public void visit(String param1, Object param2) {
      // $FF: Couldn't be decompiled
   }

   public void visitEnum(String var1, String var2, String var3) {
      try {
         ++this.size;
         if (this.named) {
            this.bv.putShort(this.cw.newUTF8(var1));
         }
      } catch (RuntimeException var4) {
         throw b(var4);
      }

      this.bv.put12(101, this.cw.newUTF8(var2)).putShort(this.cw.newUTF8(var3));
   }

   public AnnotationVisitor visitAnnotation(String var1, String var2) {
      try {
         ++this.size;
         if (this.named) {
            this.bv.putShort(this.cw.newUTF8(var1));
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      this.bv.put12(64, this.cw.newUTF8(var2)).putShort(0);
      return new AnnotationWriter(this.cw, true, this.bv, this.bv, this.bv.length - 2);
   }

   public AnnotationVisitor visitArray(String var1) {
      try {
         ++this.size;
         if (this.named) {
            this.bv.putShort(this.cw.newUTF8(var1));
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      this.bv.put12(91, 0);
      return new AnnotationWriter(this.cw, false, this.bv, this.bv, this.bv.length - 2);
   }

   public void visitEnd() {
      if (this.parent != null) {
         byte[] var1 = this.parent.data;
         var1[this.offset] = (byte)(this.size >>> 8);
         var1[this.offset + 1] = (byte)this.size;
      }

   }

   int getSize() {
      int var1 = 0;

      for(AnnotationWriter var2 = this; var2 != null; var2 = var2.next) {
         var1 += var2.bv.length;
      }

      return var1;
   }

   void put(ByteVector var1) {
      int var2 = 0;
      int var3 = 2;
      AnnotationWriter var4 = this;

      AnnotationWriter var5;
      for(var5 = null; var4 != null; var4 = var4.next) {
         ++var2;
         var3 += var4.bv.length;
         var4.visitEnd();
         var4.prev = var5;
         var5 = var4;
      }

      var1.putInt(var3);
      var1.putShort(var2);

      for(var4 = var5; var4 != null; var4 = var4.prev) {
         var1.putByteArray(var4.bv.data, 0, var4.bv.length);
      }

   }

   static void put(AnnotationWriter[] param0, int param1, ByteVector param2) {
      // $FF: Couldn't be decompiled
   }

   static void putTarget(int var0, TypePath var1, ByteVector var2) {
      label39: {
         label38: {
            label37: {
               try {
                  switch(var0 >>> 24) {
                  case 0:
                  case 1:
                  case 22:
                     var2.putShort(var0 >>> 16);
                     break label39;
                  case 19:
                  case 20:
                  case 21:
                     break label38;
                  case 71:
                  case 72:
                  case 73:
                  case 74:
                  case 75:
                     break label37;
                  }
               } catch (RuntimeException var5) {
                  throw b(var5);
               }

               var2.put12(var0 >>> 24, (var0 & 16776960) >> 8);
               break label39;
            }

            var2.putInt(var0);
            break label39;
         }

         var2.putByte(var0 >>> 24);
      }

      try {
         if (var1 == null) {
            var2.putByte(0);
            return;
         }
      } catch (RuntimeException var4) {
         throw b(var4);
      }

      int var3 = var1.b[var1.offset] * 2 + 1;
      var2.putByteArray(var1.b, var1.offset, var3);
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

package org.spongepowered.asm.lib;

import java.io.IOException;
import java.io.InputStream;

public class ClassReader {
   static final boolean SIGNATURES = true;
   static final boolean ANNOTATIONS = true;
   static final boolean FRAMES = true;
   static final boolean WRITER = true;
   static final boolean RESIZE = true;
   public static final int SKIP_CODE = 1;
   public static final int SKIP_DEBUG = 2;
   public static final int SKIP_FRAMES = 4;
   public static final int EXPAND_FRAMES = 8;
   static final int EXPAND_ASM_INSNS = 256;
   public final byte[] b;
   private final int[] items;
   private final String[] strings;
   private final int maxStringLength;
   public final int header;

   public ClassReader(byte[] var1) {
      this(var1, 0, var1.length);
   }

   public ClassReader(byte[] var1, int var2, int var3) {
      this.b = var1;
      if (this.readShort(var2 + 6) > 52) {
         throw new IllegalArgumentException();
      } else {
         this.items = new int[this.readUnsignedShort(var2 + 8)];
         int var4 = this.items.length;
         this.strings = new String[var4];
         int var5 = 0;
         int var6 = var2 + 10;
         int var7 = 1;

         while(true) {
            int var8;
            label48: {
               label47: {
                  label46: {
                     label45: {
                        label44: {
                           try {
                              if (var7 >= var4) {
                                 break;
                              }

                              this.items[var7] = var6 + 1;
                              switch(var1[var6]) {
                              case 1:
                                 break label47;
                              case 2:
                              case 7:
                              case 8:
                              case 13:
                              case 14:
                              case 16:
                              case 17:
                              default:
                                 break;
                              case 3:
                              case 4:
                              case 9:
                              case 10:
                              case 11:
                              case 12:
                              case 18:
                                 break label46;
                              case 5:
                              case 6:
                                 break label45;
                              case 15:
                                 break label44;
                              }
                           } catch (IllegalArgumentException var9) {
                              throw b(var9);
                           }

                           var8 = 3;
                           break label48;
                        }

                        var8 = 4;
                        break label48;
                     }

                     var8 = 9;
                     ++var7;
                     break label48;
                  }

                  var8 = 5;
                  break label48;
               }

               var8 = 3 + this.readUnsignedShort(var6 + 1);
               if (var8 > var5) {
                  var5 = var8;
               }
            }

            var6 += var8;
            ++var7;
         }

         this.maxStringLength = var5;
         this.header = var6;
      }
   }

   public int getAccess() {
      return this.readUnsignedShort(this.header);
   }

   public String getClassName() {
      return this.readClass(this.header + 2, new char[this.maxStringLength]);
   }

   public String getSuperName() {
      return this.readClass(this.header + 4, new char[this.maxStringLength]);
   }

   public String[] getInterfaces() {
      int var1 = this.header + 6;
      int var2 = this.readUnsignedShort(var1);
      String[] var3 = new String[var2];
      if (var2 > 0) {
         char[] var4 = new char[this.maxStringLength];
         int var5 = 0;

         try {
            while(var5 < var2) {
               var1 += 2;
               var3[var5] = this.readClass(var1, var4);
               ++var5;
            }
         } catch (IllegalArgumentException var6) {
            throw b(var6);
         }
      }

      return var3;
   }

   void copyPool(ClassWriter var1) {
      char[] var2 = new char[this.maxStringLength];
      int var3 = this.items.length;
      Item[] var4 = new Item[var3];

      int var5;
      for(var5 = 1; var5 < var3; ++var5) {
         Item var8;
         int var10;
         label44: {
            int var6 = this.items[var5];
            byte var7 = this.b[var6 - 1];
            var8 = new Item(var5);
            int var9;
            switch(var7) {
            case 1:
               String var12 = this.strings[var5];
               if (var12 == null) {
                  var6 = this.items[var5];
                  var12 = this.strings[var5] = this.readUTF(var6 + 2, this.readUnsignedShort(var6), var2);
               }

               var8.set(var7, var12, (String)null, (String)null);
               break label44;
            case 2:
            case 7:
            case 8:
            case 13:
            case 14:
            case 16:
            case 17:
            default:
               var8.set(var7, this.readUTF8(var6, var2), (String)null, (String)null);
               break label44;
            case 3:
               var8.set(this.readInt(var6));
               break label44;
            case 4:
               var8.set(Float.intBitsToFloat(this.readInt(var6)));
               break label44;
            case 5:
               var8.set(this.readLong(var6));
               ++var5;
               break label44;
            case 6:
               var8.set(Double.longBitsToDouble(this.readLong(var6)));
               ++var5;
               break label44;
            case 9:
            case 10:
            case 11:
               var9 = this.items[this.readUnsignedShort(var6 + 2)];
               var8.set(var7, this.readClass(var6, var2), this.readUTF8(var9, var2), this.readUTF8(var9 + 2, var2));
               break label44;
            case 12:
               var8.set(var7, this.readUTF8(var6, var2), this.readUTF8(var6 + 2, var2), (String)null);
               break label44;
            case 15:
               var10 = this.items[this.readUnsignedShort(var6 + 1)];
               var9 = this.items[this.readUnsignedShort(var10 + 2)];
               var8.set(20 + this.readByte(var6), this.readClass(var10, var2), this.readUTF8(var9, var2), this.readUTF8(var9 + 2, var2));
               break label44;
            case 18:
            }

            try {
               if (var1.bootstrapMethods == null) {
                  this.copyBootstrapMethods(var1, var4, var2);
               }
            } catch (IllegalArgumentException var11) {
               throw b(var11);
            }

            var9 = this.items[this.readUnsignedShort(var6 + 2)];
            var8.set(this.readUTF8(var9, var2), this.readUTF8(var9 + 2, var2), this.readUnsignedShort(var6));
         }

         var10 = var8.hashCode % var4.length;
         var8.next = var4[var10];
         var4[var10] = var8;
      }

      var5 = this.items[1] - 1;
      var1.pool.putByteArray(this.b, var5, this.header - var5);
      var1.items = var4;
      var1.threshold = (int)(0.75D * (double)var3);
      var1.index = var3;
   }

   private void copyBootstrapMethods(ClassWriter var1, Item[] var2, char[] var3) {
      int var4 = this.getAttributes();
      boolean var5 = false;

      int var6;
      for(var6 = this.readUnsignedShort(var4); var6 > 0; --var6) {
         String var7 = this.readUTF8(var4 + 2, var3);
         if ("BootstrapMethods".equals(var7)) {
            var5 = true;
            break;
         }

         var4 += 6 + this.readInt(var4 + 4);
      }

      try {
         if (!var5) {
            return;
         }
      } catch (IllegalArgumentException var13) {
         throw b(var13);
      }

      var6 = this.readUnsignedShort(var4 + 8);
      int var14 = 0;

      for(int var8 = var4 + 10; var14 < var6; ++var14) {
         int var9 = var8 - var4 - 10;
         int var10 = this.readConst(this.readUnsignedShort(var8), var3).hashCode();

         for(int var11 = this.readUnsignedShort(var8 + 2); var11 > 0; --var11) {
            var10 ^= this.readConst(this.readUnsignedShort(var8 + 4), var3).hashCode();
            var8 += 2;
         }

         var8 += 4;
         Item var16 = new Item(var14);
         var16.set(var9, var10 & Integer.MAX_VALUE);
         int var12 = var16.hashCode % var2.length;
         var16.next = var2[var12];
         var2[var12] = var16;
      }

      var14 = this.readInt(var4 + 4);
      ByteVector var15 = new ByteVector(var14 + 62);
      var15.putByteArray(this.b, var4 + 10, var14 - 2);
      var1.bootstrapMethodsCount = var6;
      var1.bootstrapMethods = var15;
   }

   public ClassReader(InputStream var1) throws IOException {
      this(readClass(var1, false));
   }

   public ClassReader(String var1) throws IOException {
      this(readClass(ClassLoader.getSystemResourceAsStream(var1.replace('.', '/') + ".class"), true));
   }

   private static byte[] readClass(InputStream var0, boolean var1) throws IOException {
      try {
         if (var0 == null) {
            throw new IOException("Class not found");
         }
      } catch (IOException var17) {
         throw b(var17);
      }

      byte[] var18;
      try {
         byte[] var2 = new byte[var0.available()];
         int var3 = 0;

         while(true) {
            int var4 = var0.read(var2, var3, var2.length - var3);

            label133: {
               try {
                  if (var4 == -1) {
                     if (var3 >= var2.length) {
                        break;
                     }
                     break label133;
                  }
               } catch (IOException var15) {
                  throw b(var15);
               }

               var3 += var4;
               if (var3 == var2.length) {
                  int var5 = var0.read();
                  byte[] var6;
                  if (var5 < 0) {
                     var6 = var2;
                     return var6;
                  }

                  var6 = new byte[var2.length + 1000];
                  System.arraycopy(var2, 0, var6, 0, var3);
                  var6[var3++] = (byte)var5;
                  var2 = var6;
               }
               continue;
            }

            var18 = new byte[var3];
            System.arraycopy(var2, 0, var18, 0, var3);
            var2 = var18;
            break;
         }

         var18 = var2;
      } finally {
         try {
            if (var1) {
               var0.close();
            }
         } catch (IOException var14) {
            throw b(var14);
         }

      }

      return var18;
   }

   public void accept(ClassVisitor var1, int var2) {
      this.accept(var1, new Attribute[0], var2);
   }

   public void accept(ClassVisitor param1, Attribute[] param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   private int readField(ClassVisitor var1, Context var2, int var3) {
      char[] var4 = var2.buffer;
      int var5 = this.readUnsignedShort(var3);
      String var6 = this.readUTF8(var3 + 2, var4);
      String var7 = this.readUTF8(var3 + 4, var4);
      var3 += 6;
      String var8 = null;
      int var9 = 0;
      int var10 = 0;
      int var11 = 0;
      int var12 = 0;
      Object var13 = null;
      Attribute var14 = null;

      int var22;
      for(int var15 = this.readUnsignedShort(var3); var15 > 0; --var15) {
         String var16 = this.readUTF8(var3 + 2, var4);
         if ("ConstantValue".equals(var16)) {
            var22 = this.readUnsignedShort(var3 + 8);

            Object var10000;
            label108: {
               try {
                  if (var22 == 0) {
                     var10000 = null;
                     break label108;
                  }
               } catch (IllegalArgumentException var19) {
                  throw b(var19);
               }

               var10000 = this.readConst(var22, var4);
            }

            var13 = var10000;
         } else if ("Signature".equals(var16)) {
            var8 = this.readUTF8(var3 + 8, var4);
         } else if ("Deprecated".equals(var16)) {
            var5 |= 131072;
         } else if ("Synthetic".equals(var16)) {
            var5 |= 266240;
         } else if ("RuntimeVisibleAnnotations".equals(var16)) {
            var9 = var3 + 8;
         } else if ("RuntimeVisibleTypeAnnotations".equals(var16)) {
            var11 = var3 + 8;
         } else if ("RuntimeInvisibleAnnotations".equals(var16)) {
            var10 = var3 + 8;
         } else if ("RuntimeInvisibleTypeAnnotations".equals(var16)) {
            var12 = var3 + 8;
         } else {
            Attribute var17 = this.readAttribute(var2.attrs, var16, var3 + 8, this.readInt(var3 + 4), var4, -1, (Label[])null);
            if (var17 != null) {
               var17.next = var14;
               var14 = var17;
            }
         }

         var3 += 6 + this.readInt(var3 + 4);
      }

      var3 += 2;
      FieldVisitor var20 = var1.visitField(var5, var6, var7, var8, var13);

      try {
         if (var20 == null) {
            return var3;
         }
      } catch (IllegalArgumentException var18) {
         throw b(var18);
      }

      int var21;
      if (var9 != 0) {
         var21 = this.readUnsignedShort(var9);

         for(var22 = var9 + 2; var21 > 0; --var21) {
            var22 = this.readAnnotationValues(var22 + 2, var4, true, var20.visitAnnotation(this.readUTF8(var22, var4), true));
         }
      }

      if (var10 != 0) {
         var21 = this.readUnsignedShort(var10);

         for(var22 = var10 + 2; var21 > 0; --var21) {
            var22 = this.readAnnotationValues(var22 + 2, var4, true, var20.visitAnnotation(this.readUTF8(var22, var4), false));
         }
      }

      if (var11 != 0) {
         var21 = this.readUnsignedShort(var11);

         for(var22 = var11 + 2; var21 > 0; --var21) {
            var22 = this.readAnnotationTarget(var2, var22);
            var22 = this.readAnnotationValues(var22 + 2, var4, true, var20.visitTypeAnnotation(var2.typeRef, var2.typePath, this.readUTF8(var22, var4), true));
         }
      }

      if (var12 != 0) {
         var21 = this.readUnsignedShort(var12);

         for(var22 = var12 + 2; var21 > 0; --var21) {
            var22 = this.readAnnotationTarget(var2, var22);
            var22 = this.readAnnotationValues(var22 + 2, var4, true, var20.visitTypeAnnotation(var2.typeRef, var2.typePath, this.readUTF8(var22, var4), false));
         }
      }

      while(var14 != null) {
         Attribute var23 = var14.next;
         var14.next = null;
         var20.visitAttribute(var14);
         var14 = var23;
      }

      var20.visitEnd();
      return var3;
   }

   private int readMethod(ClassVisitor param1, Context param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   private void readCode(MethodVisitor param1, Context param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   private int[] readTypeAnnotations(MethodVisitor param1, Context param2, int param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   private int readAnnotationTarget(Context var1, int var2) {
      int var3;
      int var4;
      var3 = this.readInt(var2);
      label46:
      switch(var3 >>> 24) {
      case 0:
      case 1:
      case 22:
         var3 &= -65536;
         var2 += 2;
         break;
      case 19:
      case 20:
      case 21:
         var3 &= -16777216;
         ++var2;
         break;
      case 64:
      case 65:
         var3 &= -16777216;
         var4 = this.readUnsignedShort(var2 + 1);
         var1.start = new Label[var4];
         var1.end = new Label[var4];
         var1.index = new int[var4];
         var2 += 3;
         int var5 = 0;

         while(true) {
            if (var5 >= var4) {
               break label46;
            }

            int var6 = this.readUnsignedShort(var2);
            int var7 = this.readUnsignedShort(var2 + 2);
            var1.start[var5] = this.readLabel(var6, var1.labels);
            var1.end[var5] = this.readLabel(var6 + var7, var1.labels);
            var1.index[var5] = this.readUnsignedShort(var2 + 4);
            var2 += 6;
            ++var5;
         }
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
         var3 &= -16776961;
         var2 += 4;
         break;
      default:
         int var10000;
         int var10001;
         label39: {
            try {
               var10000 = var3;
               if (var3 >>> 24 < 67) {
                  var10001 = -256;
                  break label39;
               }
            } catch (IllegalArgumentException var9) {
               throw b(var9);
            }

            var10001 = -16777216;
         }

         var3 = var10000 & var10001;
         var2 += 3;
      }

      var4 = this.readByte(var2);

      Context var10;
      TypePath var11;
      label31: {
         try {
            var1.typeRef = var3;
            var10 = var1;
            if (var4 == 0) {
               var11 = null;
               break label31;
            }
         } catch (IllegalArgumentException var8) {
            throw b(var8);
         }

         var11 = new TypePath(this.b, var2);
      }

      var10.typePath = var11;
      return var2 + 1 + 2 * var4;
   }

   private void readParameterAnnotations(MethodVisitor var1, Context var2, int var3, boolean var4) {
      int var5 = this.b[var3++] & 255;
      int var6 = Type.getArgumentTypes(var2.desc).length - var5;

      int var7;
      AnnotationVisitor var8;
      for(var7 = 0; var7 < var6; ++var7) {
         var8 = var1.visitParameterAnnotation(var7, "Ljava/lang/Synthetic;", false);

         try {
            if (var8 != null) {
               var8.visitEnd();
            }
         } catch (IllegalArgumentException var11) {
            throw b(var11);
         }
      }

      for(char[] var9 = var2.buffer; var7 < var5 + var6; ++var7) {
         int var10 = this.readUnsignedShort(var3);

         for(var3 += 2; var10 > 0; --var10) {
            var8 = var1.visitParameterAnnotation(var7, this.readUTF8(var3, var9), var4);
            var3 = this.readAnnotationValues(var3 + 2, var9, true, var8);
         }
      }

   }

   private int readAnnotationValues(int var1, char[] var2, boolean var3, AnnotationVisitor var4) {
      int var5 = this.readUnsignedShort(var1);

      label40: {
         label50: {
            IllegalArgumentException var10000;
            boolean var10001;
            try {
               var1 += 2;
               if (!var3) {
                  break label50;
               }
            } catch (IllegalArgumentException var8) {
               var10000 = var8;
               var10001 = false;
               throw b(var10000);
            }

            while(true) {
               try {
                  if (var5 <= 0) {
                     break label40;
                  }
               } catch (IllegalArgumentException var7) {
                  var10000 = var7;
                  var10001 = false;
                  throw b(var10000);
               }

               var1 = this.readAnnotationValue(var1 + 2, var2, this.readUTF8(var1, var2), var4);
               --var5;
            }
         }

         while(var5 > 0) {
            var1 = this.readAnnotationValue(var1, var2, (String)null, var4);
            --var5;
         }
      }

      try {
         if (var4 != null) {
            var4.visitEnd();
         }

         return var1;
      } catch (IllegalArgumentException var6) {
         throw b(var6);
      }
   }

   private int readAnnotationValue(int param1, char[] param2, String param3, AnnotationVisitor param4) {
      // $FF: Couldn't be decompiled
   }

   private void getImplicitFrame(Context param1) {
      // $FF: Couldn't be decompiled
   }

   private int readFrame(int param1, boolean param2, boolean param3, Context param4) {
      // $FF: Couldn't be decompiled
   }

   private int readFrameType(Object[] var1, int var2, int var3, char[] var4, Label[] var5) {
      int var6 = this.b[var3++] & 255;

      label82: {
         label83: {
            label84: {
               label85: {
                  label86: {
                     label87: {
                        label88: {
                           try {
                              switch(var6) {
                              case 0:
                                 var1[var2] = Opcodes.TOP;
                                 return var3;
                              case 1:
                                 break label82;
                              case 2:
                                 break label83;
                              case 3:
                                 break label84;
                              case 4:
                                 break label85;
                              case 5:
                                 break label86;
                              case 6:
                                 break label87;
                              case 7:
                                 break label88;
                              }
                           } catch (IllegalArgumentException var7) {
                              throw b(var7);
                           }

                           var1[var2] = this.readLabel(this.readUnsignedShort(var3), var5);
                           var3 += 2;
                           return var3;
                        }

                        var1[var2] = this.readClass(var3, var4);
                        var3 += 2;
                        return var3;
                     }

                     var1[var2] = Opcodes.UNINITIALIZED_THIS;
                     return var3;
                  }

                  var1[var2] = Opcodes.NULL;
                  return var3;
               }

               var1[var2] = Opcodes.LONG;
               return var3;
            }

            var1[var2] = Opcodes.DOUBLE;
            return var3;
         }

         var1[var2] = Opcodes.FLOAT;
         return var3;
      }

      var1[var2] = Opcodes.INTEGER;
      return var3;
   }

   protected Label readLabel(int var1, Label[] var2) {
      try {
         if (var2[var1] == null) {
            var2[var1] = new Label();
         }
      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }

      return var2[var1];
   }

   private int getAttributes() {
      int var1 = this.header + 8 + this.readUnsignedShort(this.header + 6) * 2;

      int var2;
      int var3;
      for(var2 = this.readUnsignedShort(var1); var2 > 0; --var2) {
         for(var3 = this.readUnsignedShort(var1 + 8); var3 > 0; --var3) {
            var1 += 6 + this.readInt(var1 + 12);
         }

         var1 += 8;
      }

      var1 += 2;

      for(var2 = this.readUnsignedShort(var1); var2 > 0; --var2) {
         for(var3 = this.readUnsignedShort(var1 + 8); var3 > 0; --var3) {
            var1 += 6 + this.readInt(var1 + 12);
         }

         var1 += 8;
      }

      return var1 + 2;
   }

   private Attribute readAttribute(Attribute[] param1, String param2, int param3, int param4, char[] param5, int param6, Label[] param7) {
      // $FF: Couldn't be decompiled
   }

   public int getItemCount() {
      return this.items.length;
   }

   public int getItem(int var1) {
      return this.items[var1];
   }

   public int getMaxStringLength() {
      return this.maxStringLength;
   }

   public int readByte(int var1) {
      return this.b[var1] & 255;
   }

   public int readUnsignedShort(int var1) {
      byte[] var2 = this.b;
      return (var2[var1] & 255) << 8 | var2[var1 + 1] & 255;
   }

   public short readShort(int var1) {
      byte[] var2 = this.b;
      return (short)((var2[var1] & 255) << 8 | var2[var1 + 1] & 255);
   }

   public int readInt(int var1) {
      byte[] var2 = this.b;
      return (var2[var1] & 255) << 24 | (var2[var1 + 1] & 255) << 16 | (var2[var1 + 2] & 255) << 8 | var2[var1 + 3] & 255;
   }

   public long readLong(int var1) {
      long var2 = (long)this.readInt(var1);
      long var4 = (long)this.readInt(var1 + 4) & 4294967295L;
      return var2 << 32 | var4;
   }

   public String readUTF8(int param1, char[] param2) {
      // $FF: Couldn't be decompiled
   }

   private String readUTF(int var1, int var2, char[] var3) {
      int var4 = var1 + var2;
      byte[] var5 = this.b;
      int var6 = 0;
      byte var7 = 0;
      char var8 = 0;

      while(true) {
         while(var1 < var4) {
            byte var9 = var5[var1++];
            switch(var7) {
            case 0:
               int var12 = var9 & 255;

               try {
                  if (var12 < 128) {
                     var3[var6++] = (char)var12;
                     break;
                  }
               } catch (IllegalArgumentException var10) {
                  throw b(var10);
               }

               label36: {
                  try {
                     if (var12 < 224 && var12 > 191) {
                        break label36;
                     }
                  } catch (IllegalArgumentException var11) {
                     throw b(var11);
                  }

                  var8 = (char)(var12 & 15);
                  var7 = 2;
                  break;
               }

               var8 = (char)(var12 & 31);
               var7 = 1;
               break;
            case 1:
               var3[var6++] = (char)(var8 << 6 | var9 & 63);
               var7 = 0;
               break;
            case 2:
               var8 = (char)(var8 << 6 | var9 & 63);
               var7 = 1;
            }
         }

         return new String(var3, 0, var6);
      }
   }

   public String readClass(int var1, char[] var2) {
      return this.readUTF8(this.items[this.readUnsignedShort(var1)], var2);
   }

   public Object readConst(int var1, char[] var2) {
      int var3 = this.items[var1];

      try {
         switch(this.b[var3 - 1]) {
         case 3:
            return this.readInt(var3);
         case 4:
            return Float.intBitsToFloat(this.readInt(var3));
         case 5:
            return this.readLong(var3);
         case 6:
            return Double.longBitsToDouble(this.readLong(var3));
         case 7:
            return Type.getObjectType(this.readUTF8(var3, var2));
         case 8:
            return this.readUTF8(var3, var2);
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         default:
            break;
         case 16:
            return Type.getMethodType(this.readUTF8(var3, var2));
         }
      } catch (IllegalArgumentException var12) {
         throw b(var12);
      }

      int var4 = this.readByte(var3);
      int[] var5 = this.items;
      int var6 = var5[this.readUnsignedShort(var3 + 1)];

      boolean var10000;
      label28: {
         try {
            if (this.b[var6 - 1] == 11) {
               var10000 = true;
               break label28;
            }
         } catch (IllegalArgumentException var11) {
            throw b(var11);
         }

         var10000 = false;
      }

      boolean var7 = var10000;
      String var8 = this.readClass(var6, var2);
      var6 = var5[this.readUnsignedShort(var6 + 2)];
      String var9 = this.readUTF8(var6, var2);
      String var10 = this.readUTF8(var6 + 2, var2);
      return new Handle(var4, var8, var9, var10, var7);
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

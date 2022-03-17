package org.spongepowered.asm.lib;

class MethodWriter extends MethodVisitor {
   static final int ACC_CONSTRUCTOR = 524288;
   static final int SAME_FRAME = 0;
   static final int SAME_LOCALS_1_STACK_ITEM_FRAME = 64;
   static final int RESERVED = 128;
   static final int SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED = 247;
   static final int CHOP_FRAME = 248;
   static final int SAME_FRAME_EXTENDED = 251;
   static final int APPEND_FRAME = 252;
   static final int FULL_FRAME = 255;
   static final int FRAMES = 0;
   static final int INSERTED_FRAMES = 1;
   static final int MAXS = 2;
   static final int NOTHING = 3;
   final ClassWriter cw;
   private int access;
   private final int name;
   private final int desc;
   private final String descriptor;
   String signature;
   int classReaderOffset;
   int classReaderLength;
   int exceptionCount;
   int[] exceptions;
   private ByteVector annd;
   private AnnotationWriter anns;
   private AnnotationWriter ianns;
   private AnnotationWriter tanns;
   private AnnotationWriter itanns;
   private AnnotationWriter[] panns;
   private AnnotationWriter[] ipanns;
   private int synthetics;
   private Attribute attrs;
   private ByteVector code = new ByteVector();
   private int maxStack;
   private int maxLocals;
   private int currentLocals;
   private int frameCount;
   private ByteVector stackMap;
   private int previousFrameOffset;
   private int[] previousFrame;
   private int[] frame;
   private int handlerCount;
   private Handler firstHandler;
   private Handler lastHandler;
   private int methodParametersCount;
   private ByteVector methodParameters;
   private int localVarCount;
   private ByteVector localVar;
   private int localVarTypeCount;
   private ByteVector localVarType;
   private int lineNumberCount;
   private ByteVector lineNumber;
   private int lastCodeOffset;
   private AnnotationWriter ctanns;
   private AnnotationWriter ictanns;
   private Attribute cattrs;
   private int subroutines;
   private final int compute;
   private Label labels;
   private Label previousBlock;
   private Label currentBlock;
   private int stackSize;
   private int maxStackSize;

   MethodWriter(ClassWriter var1, int var2, String var3, String var4, String var5, String[] var6, int var7) {
      super(327680);
      if (var1.firstMethod == null) {
         var1.firstMethod = this;
      } else {
         var1.lastMethod.mv = this;
      }

      try {
         var1.lastMethod = this;
         this.cw = var1;
         this.access = var2;
         if ("<init>".equals(var3)) {
            this.access |= 524288;
         }
      } catch (IllegalStateException var9) {
         throw b(var9);
      }

      int var8;
      label59: {
         try {
            this.name = var1.newUTF8(var3);
            this.desc = var1.newUTF8(var4);
            this.descriptor = var4;
            this.signature = var5;
            if (var6 == null || var6.length <= 0) {
               break label59;
            }
         } catch (IllegalStateException var12) {
            throw b(var12);
         }

         this.exceptionCount = var6.length;
         this.exceptions = new int[this.exceptionCount];
         var8 = 0;

         try {
            while(var8 < this.exceptionCount) {
               this.exceptions[var8] = var1.newClass(var6[var8]);
               ++var8;
            }
         } catch (IllegalStateException var11) {
            throw b(var11);
         }
      }

      this.compute = var7;
      if (var7 != 3) {
         var8 = Type.getArgumentsAndReturnSizes(this.descriptor) >> 2;

         try {
            if ((var2 & 8) != 0) {
               --var8;
            }
         } catch (IllegalStateException var10) {
            throw b(var10);
         }

         this.maxLocals = var8;
         this.currentLocals = var8;
         this.labels = new Label();
         Label var10000 = this.labels;
         var10000.status |= 8;
         this.visitLabel(this.labels);
      }

   }

   public void visitParameter(String var1, int var2) {
      try {
         if (this.methodParameters == null) {
            this.methodParameters = new ByteVector();
         }
      } catch (IllegalStateException var3) {
         throw b(var3);
      }

      ByteVector var10000;
      int var10001;
      label23: {
         try {
            ++this.methodParametersCount;
            var10000 = this.methodParameters;
            if (var1 == null) {
               var10001 = 0;
               break label23;
            }
         } catch (IllegalStateException var4) {
            throw b(var4);
         }

         var10001 = this.cw.newUTF8(var1);
      }

      var10000.putShort(var10001).putShort(var2);
   }

   public AnnotationVisitor visitAnnotationDefault() {
      this.annd = new ByteVector();
      return new AnnotationWriter(this.cw, false, this.annd, (ByteVector)null, 0);
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      ByteVector var3 = new ByteVector();
      var3.putShort(this.cw.newUTF8(var1)).putShort(0);
      AnnotationWriter var4 = new AnnotationWriter(this.cw, true, var3, var3, 2);

      try {
         if (var2) {
            var4.next = this.anns;
            this.anns = var4;
            return var4;
         }
      } catch (IllegalStateException var5) {
         throw b(var5);
      }

      var4.next = this.ianns;
      this.ianns = var4;
      return var4;
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      ByteVector var5 = new ByteVector();
      AnnotationWriter.putTarget(var1, var2, var5);
      var5.putShort(this.cw.newUTF8(var3)).putShort(0);
      AnnotationWriter var6 = new AnnotationWriter(this.cw, true, var5, var5, var5.length - 2);

      try {
         if (var4) {
            var6.next = this.tanns;
            this.tanns = var6;
            return var6;
         }
      } catch (IllegalStateException var7) {
         throw b(var7);
      }

      var6.next = this.itanns;
      this.itanns = var6;
      return var6;
   }

   public AnnotationVisitor visitParameterAnnotation(int param1, String param2, boolean param3) {
      // $FF: Couldn't be decompiled
   }

   public void visitAttribute(Attribute var1) {
      try {
         if (var1.isCodeAttribute()) {
            var1.next = this.cattrs;
            this.cattrs = var1;
            return;
         }
      } catch (IllegalStateException var2) {
         throw b(var2);
      }

      var1.next = this.attrs;
      this.attrs = var1;
   }

   public void visitCode() {
   }

   public void visitFrame(int param1, int param2, Object[] param3, int param4, Object[] param5) {
      // $FF: Couldn't be decompiled
   }

   public void visitInsn(int param1) {
      // $FF: Couldn't be decompiled
   }

   public void visitIntInsn(int param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public void visitVarInsn(int param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public void visitTypeInsn(int param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public void visitFieldInsn(int param1, String param2, String param3, String param4) {
      // $FF: Couldn't be decompiled
   }

   public void visitMethodInsn(int param1, String param2, String param3, String param4, boolean param5) {
      // $FF: Couldn't be decompiled
   }

   public void visitInvokeDynamicInsn(String param1, String param2, Handle param3, Object... param4) {
      // $FF: Couldn't be decompiled
   }

   public void visitJumpInsn(int param1, Label param2) {
      // $FF: Couldn't be decompiled
   }

   public void visitLabel(Label param1) {
      // $FF: Couldn't be decompiled
   }

   public void visitLdcInsn(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public void visitIincInsn(int param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4) {
      this.lastCodeOffset = this.code.length;
      int var5 = this.code.length;
      this.code.putByte(170);
      this.code.putByteArray((byte[])null, 0, (4 - this.code.length % 4) % 4);
      var3.put(this, this.code, var5, true);
      this.code.putInt(var1).putInt(var2);
      int var6 = 0;

      try {
         while(var6 < var4.length) {
            var4[var6].put(this, this.code, var5, true);
            ++var6;
         }
      } catch (IllegalStateException var7) {
         throw b(var7);
      }

      this.visitSwitchInsn(var3, var4);
   }

   public void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3) {
      this.lastCodeOffset = this.code.length;
      int var4 = this.code.length;
      this.code.putByte(171);
      this.code.putByteArray((byte[])null, 0, (4 - this.code.length % 4) % 4);
      var1.put(this, this.code, var4, true);
      this.code.putInt(var3.length);
      int var5 = 0;

      try {
         while(var5 < var3.length) {
            this.code.putInt(var2[var5]);
            var3[var5].put(this, this.code, var4, true);
            ++var5;
         }
      } catch (IllegalStateException var6) {
         throw b(var6);
      }

      this.visitSwitchInsn(var1, var3);
   }

   private void visitSwitchInsn(Label var1, Label[] var2) {
      label46: {
         int var3;
         label47: {
            try {
               if (this.currentBlock == null) {
                  return;
               }

               if (this.compute == 0) {
                  break label47;
               }
            } catch (IllegalStateException var6) {
               throw b(var6);
            }

            --this.stackSize;
            this.addSuccessor(this.stackSize, var1);
            var3 = 0;

            try {
               while(true) {
                  if (var3 >= var2.length) {
                     break label46;
                  }

                  this.addSuccessor(this.stackSize, var2[var3]);
                  ++var3;
               }
            } catch (IllegalStateException var4) {
               throw b(var4);
            }
         }

         this.currentBlock.frame.execute(171, 0, (ClassWriter)null, (Item)null);
         this.addSuccessor(0, var1);
         Label var10000 = var1.getFirst();
         var10000.status |= 16;
         var3 = 0;

         try {
            while(var3 < var2.length) {
               this.addSuccessor(0, var2[var3]);
               var10000 = var2[var3].getFirst();
               var10000.status |= 16;
               ++var3;
            }
         } catch (IllegalStateException var5) {
            throw b(var5);
         }
      }

      this.noSuccessor();
   }

   public void visitMultiANewArrayInsn(String param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public AnnotationVisitor visitInsnAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      ByteVector var5 = new ByteVector();
      var1 = var1 & -16776961 | this.lastCodeOffset << 8;
      AnnotationWriter.putTarget(var1, var2, var5);
      var5.putShort(this.cw.newUTF8(var3)).putShort(0);
      AnnotationWriter var6 = new AnnotationWriter(this.cw, true, var5, var5, var5.length - 2);

      try {
         if (var4) {
            var6.next = this.ctanns;
            this.ctanns = var6;
            return var6;
         }
      } catch (IllegalStateException var7) {
         throw b(var7);
      }

      var6.next = this.ictanns;
      this.ictanns = var6;
      return var6;
   }

   public void visitTryCatchBlock(Label var1, Label var2, Label var3, String var4) {
      ++this.handlerCount;
      Handler var5 = new Handler();

      Handler var10000;
      int var10001;
      label29: {
         try {
            var5.start = var1;
            var5.end = var2;
            var5.handler = var3;
            var5.desc = var4;
            var10000 = var5;
            if (var4 != null) {
               var10001 = this.cw.newClass(var4);
               break label29;
            }
         } catch (IllegalStateException var7) {
            throw b(var7);
         }

         var10001 = 0;
      }

      label22: {
         try {
            var10000.type = var10001;
            if (this.lastHandler == null) {
               this.firstHandler = var5;
               break label22;
            }
         } catch (IllegalStateException var6) {
            throw b(var6);
         }

         this.lastHandler.next = var5;
      }

      this.lastHandler = var5;
   }

   public AnnotationVisitor visitTryCatchAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      ByteVector var5 = new ByteVector();
      AnnotationWriter.putTarget(var1, var2, var5);
      var5.putShort(this.cw.newUTF8(var3)).putShort(0);
      AnnotationWriter var6 = new AnnotationWriter(this.cw, true, var5, var5, var5.length - 2);

      try {
         if (var4) {
            var6.next = this.ctanns;
            this.ctanns = var6;
            return var6;
         }
      } catch (IllegalStateException var7) {
         throw b(var7);
      }

      var6.next = this.ictanns;
      this.ictanns = var6;
      return var6;
   }

   public void visitLocalVariable(String param1, String param2, String param3, Label param4, Label param5, int param6) {
      // $FF: Couldn't be decompiled
   }

   public AnnotationVisitor visitLocalVariableAnnotation(int var1, TypePath var2, Label[] var3, Label[] var4, int[] var5, String var6, boolean var7) {
      ByteVector var8 = new ByteVector();
      var8.putByte(var1 >>> 24).putShort(var3.length);
      int var9 = 0;

      try {
         while(var9 < var3.length) {
            var8.putShort(var3[var9].position).putShort(var4[var9].position - var3[var9].position).putShort(var5[var9]);
            ++var9;
         }
      } catch (IllegalStateException var12) {
         throw b(var12);
      }

      label35: {
         try {
            if (var2 == null) {
               var8.putByte(0);
               break label35;
            }
         } catch (IllegalStateException var11) {
            throw b(var11);
         }

         var9 = var2.b[var2.offset] * 2 + 1;
         var8.putByteArray(var2.b, var2.offset, var9);
      }

      var8.putShort(this.cw.newUTF8(var6)).putShort(0);
      AnnotationWriter var13 = new AnnotationWriter(this.cw, true, var8, var8, var8.length - 2);

      try {
         if (var7) {
            var13.next = this.ctanns;
            this.ctanns = var13;
            return var13;
         }
      } catch (IllegalStateException var10) {
         throw b(var10);
      }

      var13.next = this.ictanns;
      this.ictanns = var13;
      return var13;
   }

   public void visitLineNumber(int var1, Label var2) {
      try {
         if (this.lineNumber == null) {
            this.lineNumber = new ByteVector();
         }
      } catch (IllegalStateException var3) {
         throw b(var3);
      }

      ++this.lineNumberCount;
      this.lineNumber.putShort(var2.position);
      this.lineNumber.putShort(var1);
   }

   public void visitMaxs(int param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public void visitEnd() {
   }

   private void addSuccessor(int var1, Label var2) {
      Edge var3 = new Edge();
      var3.info = var1;
      var3.successor = var2;
      var3.next = this.currentBlock.successors;
      this.currentBlock.successors = var3;
   }

   private void noSuccessor() {
      if (this.compute == 0) {
         Label var1 = new Label();
         var1.frame = new Frame();
         var1.frame.owner = var1;
         var1.resolve(this, this.code.length, this.code.data);
         this.previousBlock.successor = var1;
         this.previousBlock = var1;
      } else {
         this.currentBlock.outputStackMax = this.maxStackSize;
      }

      try {
         if (this.compute != 1) {
            this.currentBlock = null;
         }

      } catch (IllegalStateException var2) {
         throw b(var2);
      }
   }

   private void visitFrame(Frame param1) {
      // $FF: Couldn't be decompiled
   }

   private void visitImplicitFirstFrame() {
      // $FF: Couldn't be decompiled
   }

   private int startFrame(int param1, int param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   private void endFrame() {
      // $FF: Couldn't be decompiled
   }

   private void writeFrame() {
      // $FF: Couldn't be decompiled
   }

   private void writeFrameTypes(int var1, int var2) {
      for(int var3 = var1; var3 < var2; ++var3) {
         int var4 = this.frame[var3];
         int var5 = var4 & -268435456;
         if (var5 == 0) {
            int var11 = var4 & 1048575;

            label50: {
               try {
                  switch(var4 & 267386880) {
                  case 24117248:
                     this.stackMap.putByte(7).putShort(this.cw.newClass(this.cw.typeTable[var11].strVal1));
                     continue;
                  case 25165824:
                     break label50;
                  }
               } catch (IllegalStateException var7) {
                  throw b(var7);
               }

               this.stackMap.putByte(var11);
               continue;
            }

            this.stackMap.putByte(8).putShort(this.cw.typeTable[var11].intVal);
         } else {
            StringBuilder var6 = new StringBuilder();
            var5 >>= 28;

            try {
               while(var5-- > 0) {
                  var6.append('[');
               }
            } catch (IllegalStateException var10) {
               throw b(var10);
            }

            label106: {
               try {
                  if ((var4 & 267386880) == 24117248) {
                     var6.append('L');
                     var6.append(this.cw.typeTable[var4 & 1048575].strVal1);
                     var6.append(';');
                     break label106;
                  }
               } catch (IllegalStateException var9) {
                  throw b(var9);
               }

               label85: {
                  label84: {
                     label83: {
                        label82: {
                           label81: {
                              label80: {
                                 try {
                                    switch(var4 & 15) {
                                    case 1:
                                       var6.append('I');
                                       break label106;
                                    case 2:
                                       break label85;
                                    case 3:
                                       break label84;
                                    case 4:
                                    case 5:
                                    case 6:
                                    case 7:
                                    case 8:
                                    default:
                                       break;
                                    case 9:
                                       break label83;
                                    case 10:
                                       break label82;
                                    case 11:
                                       break label81;
                                    case 12:
                                       break label80;
                                    }
                                 } catch (IllegalStateException var8) {
                                    throw b(var8);
                                 }

                                 var6.append('J');
                                 break label106;
                              }

                              var6.append('S');
                              break label106;
                           }

                           var6.append('C');
                           break label106;
                        }

                        var6.append('B');
                        break label106;
                     }

                     var6.append('Z');
                     break label106;
                  }

                  var6.append('D');
                  break label106;
               }

               var6.append('F');
            }

            this.stackMap.putByte(7).putShort(this.cw.newClass(var6.toString()));
         }
      }

   }

   private void writeFrameType(Object var1) {
      try {
         if (var1 instanceof String) {
            this.stackMap.putByte(7).putShort(this.cw.newClass((String)var1));
            return;
         }
      } catch (IllegalStateException var3) {
         throw b(var3);
      }

      try {
         if (var1 instanceof Integer) {
            this.stackMap.putByte((Integer)var1);
            return;
         }
      } catch (IllegalStateException var2) {
         throw b(var2);
      }

      this.stackMap.putByte(8).putShort(((Label)var1).position);
   }

   final int getSize() {
      // $FF: Couldn't be decompiled
   }

   final void put(ByteVector param1) {
      // $FF: Couldn't be decompiled
   }

   private static IllegalStateException b(IllegalStateException var0) {
      return var0;
   }
}

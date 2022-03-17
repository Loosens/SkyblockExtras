package org.spongepowered.asm.lib.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.TypeReference;
import org.spongepowered.asm.lib.signature.SignatureReader;

public class Textifier extends Printer {
   public static final int INTERNAL_NAME = 0;
   public static final int FIELD_DESCRIPTOR = 1;
   public static final int FIELD_SIGNATURE = 2;
   public static final int METHOD_DESCRIPTOR = 3;
   public static final int METHOD_SIGNATURE = 4;
   public static final int CLASS_SIGNATURE = 5;
   public static final int TYPE_DECLARATION = 6;
   public static final int CLASS_DECLARATION = 7;
   public static final int PARAMETERS_DECLARATION = 8;
   public static final int HANDLE_DESCRIPTOR = 9;
   protected String tab;
   protected String tab2;
   protected String tab3;
   protected String ltab;
   protected Map<Label, String> labelNames;
   private int access;
   private int valueNumber;

   public Textifier() {
      this(327680);
      if (this.getClass() != Textifier.class) {
         throw new IllegalStateException();
      }
   }

   protected Textifier(int var1) {
      super(var1);
      this.tab = "  ";
      this.tab2 = "    ";
      this.tab3 = "      ";
      this.ltab = "   ";
      this.valueNumber = 0;
   }

   public static void main(String[] param0) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public void visit(int param1, int param2, String param3, String param4, String param5, String[] param6) {
      // $FF: Couldn't be decompiled
   }

   public void visitSource(String var1, String var2) {
      try {
         this.buf.setLength(0);
         if (var1 != null) {
            this.buf.append(this.tab).append("// compiled from: ").append(var1).append('\n');
         }
      } catch (IllegalStateException var4) {
         throw b(var4);
      }

      try {
         if (var2 != null) {
            this.buf.append(this.tab).append("// debug info: ").append(var2).append('\n');
         }
      } catch (IllegalStateException var5) {
         throw b(var5);
      }

      try {
         if (this.buf.length() > 0) {
            this.text.add(this.buf.toString());
         }

      } catch (IllegalStateException var3) {
         throw b(var3);
      }
   }

   public void visitOuterClass(String var1, String var2, String var3) {
      try {
         this.buf.setLength(0);
         this.buf.append(this.tab).append("OUTERCLASS ");
         this.appendDescriptor(0, var1);
         this.buf.append(' ');
         if (var2 != null) {
            this.buf.append(var2).append(' ');
         }
      } catch (IllegalStateException var4) {
         throw b(var4);
      }

      this.appendDescriptor(3, var3);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public Textifier visitClassAnnotation(String var1, boolean var2) {
      this.text.add("\n");
      return this.visitAnnotation(var1, var2);
   }

   public Printer visitClassTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      this.text.add("\n");
      return this.visitTypeAnnotation(var1, var2, var3, var4);
   }

   public void visitClassAttribute(Attribute var1) {
      this.text.add("\n");
      this.visitAttribute(var1);
   }

   public void visitInnerClass(String var1, String var2, String var3, int var4) {
      this.buf.setLength(0);
      this.buf.append(this.tab).append("// access flags 0x");
      this.buf.append(Integer.toHexString(var4 & -33).toUpperCase()).append('\n');
      this.buf.append(this.tab);
      this.appendAccess(var4);
      this.buf.append("INNERCLASS ");
      this.appendDescriptor(0, var1);
      this.buf.append(' ');
      this.appendDescriptor(0, var2);
      this.buf.append(' ');
      this.appendDescriptor(0, var3);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public Textifier visitField(int param1, String param2, String param3, String param4, Object param5) {
      // $FF: Couldn't be decompiled
   }

   public Textifier visitMethod(int param1, String param2, String param3, String param4, String[] param5) {
      // $FF: Couldn't be decompiled
   }

   public void visitClassEnd() {
      this.text.add("}\n");
   }

   public void visit(String var1, Object var2) {
      try {
         this.buf.setLength(0);
         this.appendComa(this.valueNumber++);
         if (var1 != null) {
            this.buf.append(var1).append('=');
         }
      } catch (IllegalStateException var5) {
         throw b(var5);
      }

      label241: {
         try {
            if (var2 instanceof String) {
               this.visitString((String)var2);
               break label241;
            }
         } catch (IllegalStateException var24) {
            throw b(var24);
         }

         try {
            if (var2 instanceof Type) {
               this.visitType((Type)var2);
               break label241;
            }
         } catch (IllegalStateException var23) {
            throw b(var23);
         }

         try {
            if (var2 instanceof Byte) {
               this.visitByte((Byte)var2);
               break label241;
            }
         } catch (IllegalStateException var22) {
            throw b(var22);
         }

         try {
            if (var2 instanceof Boolean) {
               this.visitBoolean((Boolean)var2);
               break label241;
            }
         } catch (IllegalStateException var21) {
            throw b(var21);
         }

         try {
            if (var2 instanceof Short) {
               this.visitShort((Short)var2);
               break label241;
            }
         } catch (IllegalStateException var20) {
            throw b(var20);
         }

         try {
            if (var2 instanceof Character) {
               this.visitChar((Character)var2);
               break label241;
            }
         } catch (IllegalStateException var19) {
            throw b(var19);
         }

         try {
            if (var2 instanceof Integer) {
               this.visitInt((Integer)var2);
               break label241;
            }
         } catch (IllegalStateException var18) {
            throw b(var18);
         }

         try {
            if (var2 instanceof Float) {
               this.visitFloat((Float)var2);
               break label241;
            }
         } catch (IllegalStateException var17) {
            throw b(var17);
         }

         try {
            if (var2 instanceof Long) {
               this.visitLong((Long)var2);
               break label241;
            }
         } catch (IllegalStateException var16) {
            throw b(var16);
         }

         try {
            if (var2 instanceof Double) {
               this.visitDouble((Double)var2);
               break label241;
            }
         } catch (IllegalStateException var15) {
            throw b(var15);
         }

         label176: {
            int var4;
            label242: {
               try {
                  if (!var2.getClass().isArray()) {
                     break label241;
                  }

                  this.buf.append('{');
                  if (!(var2 instanceof byte[])) {
                     break label242;
                  }
               } catch (IllegalStateException var14) {
                  throw b(var14);
               }

               byte[] var3 = (byte[])((byte[])var2);
               var4 = 0;

               try {
                  while(true) {
                     if (var4 >= var3.length) {
                        break label176;
                     }

                     this.appendComa(var4);
                     this.visitByte(var3[var4]);
                     ++var4;
                  }
               } catch (IllegalStateException var13) {
                  throw b(var13);
               }
            }

            if (var2 instanceof boolean[]) {
               boolean[] var25 = (boolean[])((boolean[])var2);
               var4 = 0;

               try {
                  while(var4 < var25.length) {
                     this.appendComa(var4);
                     this.visitBoolean(var25[var4]);
                     ++var4;
                  }
               } catch (IllegalStateException var12) {
                  throw b(var12);
               }
            } else if (var2 instanceof short[]) {
               short[] var26 = (short[])((short[])var2);
               var4 = 0;

               try {
                  while(var4 < var26.length) {
                     this.appendComa(var4);
                     this.visitShort(var26[var4]);
                     ++var4;
                  }
               } catch (IllegalStateException var11) {
                  throw b(var11);
               }
            } else if (var2 instanceof char[]) {
               char[] var27 = (char[])((char[])var2);
               var4 = 0;

               try {
                  while(var4 < var27.length) {
                     this.appendComa(var4);
                     this.visitChar(var27[var4]);
                     ++var4;
                  }
               } catch (IllegalStateException var10) {
                  throw b(var10);
               }
            } else if (var2 instanceof int[]) {
               int[] var28 = (int[])((int[])var2);
               var4 = 0;

               try {
                  while(var4 < var28.length) {
                     this.appendComa(var4);
                     this.visitInt(var28[var4]);
                     ++var4;
                  }
               } catch (IllegalStateException var9) {
                  throw b(var9);
               }
            } else if (var2 instanceof long[]) {
               long[] var29 = (long[])((long[])var2);
               var4 = 0;

               try {
                  while(var4 < var29.length) {
                     this.appendComa(var4);
                     this.visitLong(var29[var4]);
                     ++var4;
                  }
               } catch (IllegalStateException var8) {
                  throw b(var8);
               }
            } else if (var2 instanceof float[]) {
               float[] var30 = (float[])((float[])var2);
               var4 = 0;

               try {
                  while(var4 < var30.length) {
                     this.appendComa(var4);
                     this.visitFloat(var30[var4]);
                     ++var4;
                  }
               } catch (IllegalStateException var7) {
                  throw b(var7);
               }
            } else if (var2 instanceof double[]) {
               double[] var31 = (double[])((double[])var2);
               var4 = 0;

               try {
                  while(var4 < var31.length) {
                     this.appendComa(var4);
                     this.visitDouble(var31[var4]);
                     ++var4;
                  }
               } catch (IllegalStateException var6) {
                  throw b(var6);
               }
            }
         }

         this.buf.append('}');
      }

      this.text.add(this.buf.toString());
   }

   private void visitInt(int var1) {
      this.buf.append(var1);
   }

   private void visitLong(long var1) {
      this.buf.append(var1).append('L');
   }

   private void visitFloat(float var1) {
      this.buf.append(var1).append('F');
   }

   private void visitDouble(double var1) {
      this.buf.append(var1).append('D');
   }

   private void visitChar(char var1) {
      this.buf.append("(char)").append(var1);
   }

   private void visitShort(short var1) {
      this.buf.append("(short)").append(var1);
   }

   private void visitByte(byte var1) {
      this.buf.append("(byte)").append(var1);
   }

   private void visitBoolean(boolean var1) {
      this.buf.append(var1);
   }

   private void visitString(String var1) {
      appendString(this.buf, var1);
   }

   private void visitType(Type var1) {
      this.buf.append(var1.getClassName()).append(".class");
   }

   public void visitEnum(String var1, String var2, String var3) {
      try {
         this.buf.setLength(0);
         this.appendComa(this.valueNumber++);
         if (var1 != null) {
            this.buf.append(var1).append('=');
         }
      } catch (IllegalStateException var4) {
         throw b(var4);
      }

      this.appendDescriptor(1, var2);
      this.buf.append('.').append(var3);
      this.text.add(this.buf.toString());
   }

   public Textifier visitAnnotation(String var1, String var2) {
      try {
         this.buf.setLength(0);
         this.appendComa(this.valueNumber++);
         if (var1 != null) {
            this.buf.append(var1).append('=');
         }
      } catch (IllegalStateException var4) {
         throw b(var4);
      }

      this.buf.append('@');
      this.appendDescriptor(1, var2);
      this.buf.append('(');
      this.text.add(this.buf.toString());
      Textifier var3 = this.createTextifier();
      this.text.add(var3.getText());
      this.text.add(")");
      return var3;
   }

   public Textifier visitArray(String var1) {
      try {
         this.buf.setLength(0);
         this.appendComa(this.valueNumber++);
         if (var1 != null) {
            this.buf.append(var1).append('=');
         }
      } catch (IllegalStateException var3) {
         throw b(var3);
      }

      this.buf.append('{');
      this.text.add(this.buf.toString());
      Textifier var2 = this.createTextifier();
      this.text.add(var2.getText());
      this.text.add("}");
      return var2;
   }

   public void visitAnnotationEnd() {
   }

   public Textifier visitFieldAnnotation(String var1, boolean var2) {
      return this.visitAnnotation(var1, var2);
   }

   public Printer visitFieldTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitTypeAnnotation(var1, var2, var3, var4);
   }

   public void visitFieldAttribute(Attribute var1) {
      this.visitAttribute(var1);
   }

   public void visitFieldEnd() {
   }

   public void visitParameter(String var1, int var2) {
      StringBuffer var10000;
      String var10001;
      label16: {
         try {
            this.buf.setLength(0);
            this.buf.append(this.tab2).append("// parameter ");
            this.appendAccess(var2);
            var10000 = this.buf.append(' ');
            if (var1 == null) {
               var10001 = "<no name>";
               break label16;
            }
         } catch (IllegalStateException var3) {
            throw b(var3);
         }

         var10001 = var1;
      }

      var10000.append(var10001).append('\n');
      this.text.add(this.buf.toString());
   }

   public Textifier visitAnnotationDefault() {
      this.text.add(this.tab2 + "default=");
      Textifier var1 = this.createTextifier();
      this.text.add(var1.getText());
      this.text.add("\n");
      return var1;
   }

   public Textifier visitMethodAnnotation(String var1, boolean var2) {
      return this.visitAnnotation(var1, var2);
   }

   public Printer visitMethodTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitTypeAnnotation(var1, var2, var3, var4);
   }

   public Textifier visitParameterAnnotation(int var1, String var2, boolean var3) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append('@');
      this.appendDescriptor(1, var2);
      this.buf.append('(');
      this.text.add(this.buf.toString());
      Textifier var4 = this.createTextifier();

      List var10000;
      String var10001;
      label17: {
         try {
            this.text.add(var4.getText());
            var10000 = this.text;
            if (var3) {
               var10001 = ") // parameter ";
               break label17;
            }
         } catch (IllegalStateException var5) {
            throw b(var5);
         }

         var10001 = ") // invisible, parameter ";
      }

      var10000.add(var10001);
      this.text.add(var1);
      this.text.add("\n");
      return var4;
   }

   public void visitMethodAttribute(Attribute var1) {
      label16: {
         try {
            this.buf.setLength(0);
            this.buf.append(this.tab).append("ATTRIBUTE ");
            this.appendDescriptor(-1, var1.type);
            if (var1 instanceof Textifiable) {
               ((Textifiable)var1).textify(this.buf, this.labelNames);
               break label16;
            }
         } catch (IllegalStateException var2) {
            throw b(var2);
         }

         this.buf.append(" : unknown\n");
      }

      this.text.add(this.buf.toString());
   }

   public void visitCode() {
   }

   public void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5) {
      label34: {
         label33: {
            label32: {
               label31: {
                  try {
                     this.buf.setLength(0);
                     this.buf.append(this.ltab);
                     this.buf.append("FRAME ");
                     switch(var1) {
                     case -1:
                     case 0:
                        this.buf.append("FULL [");
                        this.appendFrameTypes(var2, var3);
                        this.buf.append("] [");
                        this.appendFrameTypes(var4, var5);
                        this.buf.append(']');
                        break label34;
                     case 1:
                        break label33;
                     case 2:
                        break label32;
                     case 3:
                        break label31;
                     case 4:
                        break;
                     default:
                        break label34;
                     }
                  } catch (IllegalStateException var6) {
                     throw b(var6);
                  }

                  this.buf.append("SAME1 ");
                  this.appendFrameTypes(1, var5);
                  break label34;
               }

               this.buf.append("SAME");
               break label34;
            }

            this.buf.append("CHOP ").append(var2);
            break label34;
         }

         this.buf.append("APPEND [");
         this.appendFrameTypes(var2, var3);
         this.buf.append(']');
      }

      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitInsn(int var1) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append(OPCODES[var1]).append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitIntInsn(int var1, int var2) {
      StringBuffer var10000;
      String var10001;
      label16: {
         try {
            this.buf.setLength(0);
            var10000 = this.buf.append(this.tab2).append(OPCODES[var1]).append(' ');
            if (var1 == 188) {
               var10001 = TYPES[var2];
               break label16;
            }
         } catch (IllegalStateException var3) {
            throw b(var3);
         }

         var10001 = Integer.toString(var2);
      }

      var10000.append(var10001).append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitVarInsn(int var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append(OPCODES[var1]).append(' ').append(var2).append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitTypeInsn(int var1, String var2) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append(OPCODES[var1]).append(' ');
      this.appendDescriptor(0, var2);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append(OPCODES[var1]).append(' ');
      this.appendDescriptor(0, var2);
      this.buf.append('.').append(var3).append(" : ");
      this.appendDescriptor(1, var4);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   /** @deprecated */
   @Deprecated
   public void visitMethodInsn(int var1, String var2, String var3, String var4) {
      try {
         if (this.api >= 327680) {
            super.visitMethodInsn(var1, var2, var3, var4);
            return;
         }
      } catch (IllegalStateException var5) {
         throw b(var5);
      }

      Textifier var10000;
      int var10001;
      String var10002;
      String var10003;
      String var10004;
      boolean var10005;
      label22: {
         try {
            var10000 = this;
            var10001 = var1;
            var10002 = var2;
            var10003 = var3;
            var10004 = var4;
            if (var1 == 185) {
               var10005 = true;
               break label22;
            }
         } catch (IllegalStateException var6) {
            throw b(var6);
         }

         var10005 = false;
      }

      var10000.doVisitMethodInsn(var10001, var10002, var10003, var10004, var10005);
   }

   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      try {
         if (this.api < 327680) {
            super.visitMethodInsn(var1, var2, var3, var4, var5);
            return;
         }
      } catch (IllegalStateException var6) {
         throw b(var6);
      }

      this.doVisitMethodInsn(var1, var2, var3, var4, var5);
   }

   private void doVisitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append(OPCODES[var1]).append(' ');
      this.appendDescriptor(0, var2);
      this.buf.append('.').append(var3).append(' ');
      this.appendDescriptor(3, var4);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4) {
      label66: {
         try {
            this.buf.setLength(0);
            this.buf.append(this.tab2).append("INVOKEDYNAMIC").append(' ');
            this.buf.append(var1);
            this.appendDescriptor(3, var2);
            this.buf.append(" [");
            this.buf.append('\n');
            this.buf.append(this.tab3);
            this.appendHandle(var3);
            this.buf.append('\n');
            this.buf.append(this.tab3).append("// arguments:");
            if (var4.length == 0) {
               this.buf.append(" none");
               break label66;
            }
         } catch (IllegalStateException var11) {
            throw b(var11);
         }

         this.buf.append('\n');

         for(int var5 = 0; var5 < var4.length; ++var5) {
            label67: {
               this.buf.append(this.tab3);
               Object var6 = var4[var5];

               try {
                  if (var6 instanceof String) {
                     Printer.appendString(this.buf, (String)var6);
                     break label67;
                  }
               } catch (IllegalStateException var8) {
                  throw b(var8);
               }

               if (var6 instanceof Type) {
                  label68: {
                     Type var7 = (Type)var6;

                     try {
                        if (var7.getSort() == 11) {
                           this.appendDescriptor(3, var7.getDescriptor());
                           break label68;
                        }
                     } catch (IllegalStateException var9) {
                        throw b(var9);
                     }

                     this.buf.append(var7.getDescriptor()).append(".class");
                  }
               } else {
                  label69: {
                     try {
                        if (var6 instanceof Handle) {
                           this.appendHandle((Handle)var6);
                           break label69;
                        }
                     } catch (IllegalStateException var10) {
                        throw b(var10);
                     }

                     this.buf.append(var6);
                  }
               }
            }

            this.buf.append(", \n");
         }

         this.buf.setLength(this.buf.length() - 3);
      }

      this.buf.append('\n');
      this.buf.append(this.tab2).append("]\n");
      this.text.add(this.buf.toString());
   }

   public void visitJumpInsn(int var1, Label var2) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append(OPCODES[var1]).append(' ');
      this.appendLabel(var2);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitLabel(Label var1) {
      this.buf.setLength(0);
      this.buf.append(this.ltab);
      this.appendLabel(var1);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitLdcInsn(Object var1) {
      label28: {
         try {
            this.buf.setLength(0);
            this.buf.append(this.tab2).append("LDC ");
            if (var1 instanceof String) {
               Printer.appendString(this.buf, (String)var1);
               break label28;
            }
         } catch (IllegalStateException var3) {
            throw b(var3);
         }

         try {
            if (var1 instanceof Type) {
               this.buf.append(((Type)var1).getDescriptor()).append(".class");
               break label28;
            }
         } catch (IllegalStateException var2) {
            throw b(var2);
         }

         this.buf.append(var1);
      }

      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitIincInsn(int var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("IINC ").append(var1).append(' ').append(var2).append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("TABLESWITCH\n");
      int var5 = 0;

      try {
         while(var5 < var4.length) {
            this.buf.append(this.tab3).append(var1 + var5).append(": ");
            this.appendLabel(var4[var5]);
            this.buf.append('\n');
            ++var5;
         }
      } catch (IllegalStateException var6) {
         throw b(var6);
      }

      this.buf.append(this.tab3).append("default: ");
      this.appendLabel(var3);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("LOOKUPSWITCH\n");
      int var4 = 0;

      try {
         while(var4 < var3.length) {
            this.buf.append(this.tab3).append(var2[var4]).append(": ");
            this.appendLabel(var3[var4]);
            this.buf.append('\n');
            ++var4;
         }
      } catch (IllegalStateException var5) {
         throw b(var5);
      }

      this.buf.append(this.tab3).append("default: ");
      this.appendLabel(var1);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("MULTIANEWARRAY ");
      this.appendDescriptor(1, var1);
      this.buf.append(' ').append(var2).append('\n');
      this.text.add(this.buf.toString());
   }

   public Printer visitInsnAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitTypeAnnotation(var1, var2, var3, var4);
   }

   public void visitTryCatchBlock(Label var1, Label var2, Label var3, String var4) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("TRYCATCHBLOCK ");
      this.appendLabel(var1);
      this.buf.append(' ');
      this.appendLabel(var2);
      this.buf.append(' ');
      this.appendLabel(var3);
      this.buf.append(' ');
      this.appendDescriptor(0, var4);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public Printer visitTryCatchAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("TRYCATCHBLOCK @");
      this.appendDescriptor(1, var3);
      this.buf.append('(');
      this.text.add(this.buf.toString());
      Textifier var5 = this.createTextifier();

      StringBuffer var10000;
      String var10001;
      label17: {
         try {
            this.text.add(var5.getText());
            this.buf.setLength(0);
            this.buf.append(") : ");
            this.appendTypeReference(var1);
            this.buf.append(", ").append(var2);
            var10000 = this.buf;
            if (var4) {
               var10001 = "\n";
               break label17;
            }
         } catch (IllegalStateException var6) {
            throw b(var6);
         }

         var10001 = " // invisible\n";
      }

      var10000.append(var10001);
      this.text.add(this.buf.toString());
      return var5;
   }

   public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("LOCALVARIABLE ").append(var1).append(' ');
      this.appendDescriptor(1, var2);
      this.buf.append(' ');
      this.appendLabel(var4);
      this.buf.append(' ');
      this.appendLabel(var5);
      this.buf.append(' ').append(var6).append('\n');
      if (var3 != null) {
         this.buf.append(this.tab2);
         this.appendDescriptor(2, var3);
         TraceSignatureVisitor var7 = new TraceSignatureVisitor(0);
         SignatureReader var8 = new SignatureReader(var3);
         var8.acceptType(var7);
         this.buf.append(this.tab2).append("// declaration: ").append(var7.getDeclaration()).append('\n');
      }

      this.text.add(this.buf.toString());
   }

   public Printer visitLocalVariableAnnotation(int var1, TypePath var2, Label[] var3, Label[] var4, int[] var5, String var6, boolean var7) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("LOCALVARIABLE @");
      this.appendDescriptor(1, var6);
      this.buf.append('(');
      this.text.add(this.buf.toString());
      Textifier var8 = this.createTextifier();
      this.text.add(var8.getText());
      this.buf.setLength(0);
      this.buf.append(") : ");
      this.appendTypeReference(var1);
      this.buf.append(", ").append(var2);
      int var9 = 0;

      try {
         while(var9 < var3.length) {
            this.buf.append(" [ ");
            this.appendLabel(var3[var9]);
            this.buf.append(" - ");
            this.appendLabel(var4[var9]);
            this.buf.append(" - ").append(var5[var9]).append(" ]");
            ++var9;
         }
      } catch (IllegalStateException var11) {
         throw b(var11);
      }

      StringBuffer var10000;
      String var10001;
      label21: {
         try {
            var10000 = this.buf;
            if (var7) {
               var10001 = "\n";
               break label21;
            }
         } catch (IllegalStateException var10) {
            throw b(var10);
         }

         var10001 = " // invisible\n";
      }

      var10000.append(var10001);
      this.text.add(this.buf.toString());
      return var8;
   }

   public void visitLineNumber(int var1, Label var2) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("LINENUMBER ").append(var1).append(' ');
      this.appendLabel(var2);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitMaxs(int var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("MAXSTACK = ").append(var1).append('\n');
      this.text.add(this.buf.toString());
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("MAXLOCALS = ").append(var2).append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitMethodEnd() {
   }

   public Textifier visitAnnotation(String var1, boolean var2) {
      this.buf.setLength(0);
      this.buf.append(this.tab).append('@');
      this.appendDescriptor(1, var1);
      this.buf.append('(');
      this.text.add(this.buf.toString());
      Textifier var3 = this.createTextifier();

      List var10000;
      String var10001;
      label17: {
         try {
            this.text.add(var3.getText());
            var10000 = this.text;
            if (var2) {
               var10001 = ")\n";
               break label17;
            }
         } catch (IllegalStateException var4) {
            throw b(var4);
         }

         var10001 = ") // invisible\n";
      }

      var10000.add(var10001);
      return var3;
   }

   public Textifier visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      this.buf.setLength(0);
      this.buf.append(this.tab).append('@');
      this.appendDescriptor(1, var3);
      this.buf.append('(');
      this.text.add(this.buf.toString());
      Textifier var5 = this.createTextifier();

      StringBuffer var10000;
      String var10001;
      label17: {
         try {
            this.text.add(var5.getText());
            this.buf.setLength(0);
            this.buf.append(") : ");
            this.appendTypeReference(var1);
            this.buf.append(", ").append(var2);
            var10000 = this.buf;
            if (var4) {
               var10001 = "\n";
               break label17;
            }
         } catch (IllegalStateException var6) {
            throw b(var6);
         }

         var10001 = " // invisible\n";
      }

      var10000.append(var10001);
      this.text.add(this.buf.toString());
      return var5;
   }

   public void visitAttribute(Attribute var1) {
      label16: {
         try {
            this.buf.setLength(0);
            this.buf.append(this.tab).append("ATTRIBUTE ");
            this.appendDescriptor(-1, var1.type);
            if (var1 instanceof Textifiable) {
               ((Textifiable)var1).textify(this.buf, (Map)null);
               break label16;
            }
         } catch (IllegalStateException var2) {
            throw b(var2);
         }

         this.buf.append(" : unknown\n");
      }

      this.text.add(this.buf.toString());
   }

   protected Textifier createTextifier() {
      return new Textifier();
   }

   protected void appendDescriptor(int param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   protected void appendLabel(Label var1) {
      try {
         if (this.labelNames == null) {
            this.labelNames = new HashMap();
         }
      } catch (IllegalStateException var3) {
         throw b(var3);
      }

      String var2 = (String)this.labelNames.get(var1);
      if (var2 == null) {
         var2 = "L" + this.labelNames.size();
         this.labelNames.put(var1, var2);
      }

      this.buf.append(var2);
   }

   protected void appendHandle(Handle var1) {
      int var2 = var1.getTag();
      this.buf.append("// handle kind 0x").append(Integer.toHexString(var2)).append(" : ");
      boolean var3 = false;

      label73: {
         label72: {
            label71: {
               label70: {
                  label69: {
                     label68: {
                        label67: {
                           label66: {
                              try {
                                 switch(var2) {
                                 case 1:
                                    this.buf.append("GETFIELD");
                                    break label73;
                                 case 2:
                                    break label72;
                                 case 3:
                                    break label71;
                                 case 4:
                                    break label70;
                                 case 5:
                                    break label66;
                                 case 6:
                                    break label67;
                                 case 7:
                                    break label68;
                                 case 8:
                                    break;
                                 case 9:
                                    break label69;
                                 default:
                                    break label73;
                                 }
                              } catch (IllegalStateException var6) {
                                 throw b(var6);
                              }

                              this.buf.append("NEWINVOKESPECIAL");
                              var3 = true;
                              break label73;
                           }

                           this.buf.append("INVOKEVIRTUAL");
                           var3 = true;
                           break label73;
                        }

                        this.buf.append("INVOKESTATIC");
                        var3 = true;
                        break label73;
                     }

                     this.buf.append("INVOKESPECIAL");
                     var3 = true;
                     break label73;
                  }

                  this.buf.append("INVOKEINTERFACE");
                  var3 = true;
                  break label73;
               }

               this.buf.append("PUTSTATIC");
               break label73;
            }

            this.buf.append("PUTFIELD");
            break label73;
         }

         this.buf.append("GETSTATIC");
      }

      try {
         this.buf.append('\n');
         this.buf.append(this.tab3);
         this.appendDescriptor(0, var1.getOwner());
         this.buf.append('.');
         this.buf.append(var1.getName());
         if (!var3) {
            this.buf.append('(');
         }
      } catch (IllegalStateException var5) {
         throw b(var5);
      }

      try {
         this.appendDescriptor(9, var1.getDesc());
         if (!var3) {
            this.buf.append(')');
         }

      } catch (IllegalStateException var4) {
         throw b(var4);
      }
   }

   private void appendAccess(int var1) {
      try {
         if ((var1 & 1) != 0) {
            this.buf.append("public ");
         }
      } catch (IllegalStateException var8) {
         throw b(var8);
      }

      try {
         if ((var1 & 2) != 0) {
            this.buf.append("private ");
         }
      } catch (IllegalStateException var14) {
         throw b(var14);
      }

      try {
         if ((var1 & 4) != 0) {
            this.buf.append("protected ");
         }
      } catch (IllegalStateException var7) {
         throw b(var7);
      }

      try {
         if ((var1 & 16) != 0) {
            this.buf.append("final ");
         }
      } catch (IllegalStateException var13) {
         throw b(var13);
      }

      try {
         if ((var1 & 8) != 0) {
            this.buf.append("static ");
         }
      } catch (IllegalStateException var6) {
         throw b(var6);
      }

      try {
         if ((var1 & 32) != 0) {
            this.buf.append("synchronized ");
         }
      } catch (IllegalStateException var12) {
         throw b(var12);
      }

      try {
         if ((var1 & 64) != 0) {
            this.buf.append("volatile ");
         }
      } catch (IllegalStateException var5) {
         throw b(var5);
      }

      try {
         if ((var1 & 128) != 0) {
            this.buf.append("transient ");
         }
      } catch (IllegalStateException var11) {
         throw b(var11);
      }

      try {
         if ((var1 & 1024) != 0) {
            this.buf.append("abstract ");
         }
      } catch (IllegalStateException var4) {
         throw b(var4);
      }

      try {
         if ((var1 & 2048) != 0) {
            this.buf.append("strictfp ");
         }
      } catch (IllegalStateException var10) {
         throw b(var10);
      }

      try {
         if ((var1 & 4096) != 0) {
            this.buf.append("synthetic ");
         }
      } catch (IllegalStateException var3) {
         throw b(var3);
      }

      try {
         if ((var1 & '耀') != 0) {
            this.buf.append("mandated ");
         }
      } catch (IllegalStateException var9) {
         throw b(var9);
      }

      try {
         if ((var1 & 16384) != 0) {
            this.buf.append("enum ");
         }

      } catch (IllegalStateException var2) {
         throw b(var2);
      }
   }

   private void appendComa(int var1) {
      try {
         if (var1 != 0) {
            this.buf.append(", ");
         }

      } catch (IllegalStateException var2) {
         throw b(var2);
      }
   }

   private void appendTypeReference(int var1) {
      TypeReference var2 = new TypeReference(var1);

      label191: {
         label192: {
            label193: {
               label194: {
                  label195: {
                     label196: {
                        label197: {
                           label198: {
                              label199: {
                                 label200: {
                                    label201: {
                                       label202: {
                                          label203: {
                                             label204: {
                                                label205: {
                                                   label206: {
                                                      label207: {
                                                         label208: {
                                                            label209: {
                                                               label210: {
                                                                  try {
                                                                     switch(var2.getSort()) {
                                                                     case 0:
                                                                        this.buf.append("CLASS_TYPE_PARAMETER ").append(var2.getTypeParameterIndex());
                                                                        return;
                                                                     case 1:
                                                                        break label191;
                                                                     case 2:
                                                                     case 3:
                                                                     case 4:
                                                                     case 5:
                                                                     case 6:
                                                                     case 7:
                                                                     case 8:
                                                                     case 9:
                                                                     case 10:
                                                                     case 11:
                                                                     case 12:
                                                                     case 13:
                                                                     case 14:
                                                                     case 15:
                                                                     case 24:
                                                                     case 25:
                                                                     case 26:
                                                                     case 27:
                                                                     case 28:
                                                                     case 29:
                                                                     case 30:
                                                                     case 31:
                                                                     case 32:
                                                                     case 33:
                                                                     case 34:
                                                                     case 35:
                                                                     case 36:
                                                                     case 37:
                                                                     case 38:
                                                                     case 39:
                                                                     case 40:
                                                                     case 41:
                                                                     case 42:
                                                                     case 43:
                                                                     case 44:
                                                                     case 45:
                                                                     case 46:
                                                                     case 47:
                                                                     case 48:
                                                                     case 49:
                                                                     case 50:
                                                                     case 51:
                                                                     case 52:
                                                                     case 53:
                                                                     case 54:
                                                                     case 55:
                                                                     case 56:
                                                                     case 57:
                                                                     case 58:
                                                                     case 59:
                                                                     case 60:
                                                                     case 61:
                                                                     case 62:
                                                                     case 63:
                                                                     default:
                                                                        return;
                                                                     case 16:
                                                                        break label192;
                                                                     case 17:
                                                                        break label193;
                                                                     case 18:
                                                                        break label194;
                                                                     case 19:
                                                                        break label195;
                                                                     case 20:
                                                                        break label196;
                                                                     case 21:
                                                                        break label197;
                                                                     case 22:
                                                                        break label198;
                                                                     case 23:
                                                                        break label199;
                                                                     case 64:
                                                                        break label200;
                                                                     case 65:
                                                                        break label201;
                                                                     case 66:
                                                                        break label202;
                                                                     case 67:
                                                                        break label203;
                                                                     case 68:
                                                                        break label204;
                                                                     case 69:
                                                                        break label205;
                                                                     case 70:
                                                                        break label206;
                                                                     case 71:
                                                                        break label207;
                                                                     case 72:
                                                                        break label208;
                                                                     case 73:
                                                                        break label209;
                                                                     case 74:
                                                                        break label210;
                                                                     case 75:
                                                                     }
                                                                  } catch (IllegalStateException var3) {
                                                                     throw b(var3);
                                                                  }

                                                                  this.buf.append("METHOD_REFERENCE_TYPE_ARGUMENT ").append(var2.getTypeArgumentIndex());
                                                                  return;
                                                               }

                                                               this.buf.append("CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT ").append(var2.getTypeArgumentIndex());
                                                               return;
                                                            }

                                                            this.buf.append("METHOD_INVOCATION_TYPE_ARGUMENT ").append(var2.getTypeArgumentIndex());
                                                            return;
                                                         }

                                                         this.buf.append("CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT ").append(var2.getTypeArgumentIndex());
                                                         return;
                                                      }

                                                      this.buf.append("CAST ").append(var2.getTypeArgumentIndex());
                                                      return;
                                                   }

                                                   this.buf.append("METHOD_REFERENCE");
                                                   return;
                                                }

                                                this.buf.append("CONSTRUCTOR_REFERENCE");
                                                return;
                                             }

                                             this.buf.append("NEW");
                                             return;
                                          }

                                          this.buf.append("INSTANCEOF");
                                          return;
                                       }

                                       this.buf.append("EXCEPTION_PARAMETER ").append(var2.getTryCatchBlockIndex());
                                       return;
                                    }

                                    this.buf.append("RESOURCE_VARIABLE");
                                    return;
                                 }

                                 this.buf.append("LOCAL_VARIABLE");
                                 return;
                              }

                              this.buf.append("THROWS ").append(var2.getExceptionIndex());
                              return;
                           }

                           this.buf.append("METHOD_FORMAL_PARAMETER ").append(var2.getFormalParameterIndex());
                           return;
                        }

                        this.buf.append("METHOD_RECEIVER");
                        return;
                     }

                     this.buf.append("METHOD_RETURN");
                     return;
                  }

                  this.buf.append("FIELD");
                  return;
               }

               this.buf.append("METHOD_TYPE_PARAMETER_BOUND ").append(var2.getTypeParameterIndex()).append(", ").append(var2.getTypeParameterBoundIndex());
               return;
            }

            this.buf.append("CLASS_TYPE_PARAMETER_BOUND ").append(var2.getTypeParameterIndex()).append(", ").append(var2.getTypeParameterBoundIndex());
            return;
         }

         this.buf.append("CLASS_EXTENDS ").append(var2.getSuperTypeIndex());
         return;
      }

      this.buf.append("METHOD_TYPE_PARAMETER ").append(var2.getTypeParameterIndex());
   }

   private void appendFrameTypes(int param1, Object[] param2) {
      // $FF: Couldn't be decompiled
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

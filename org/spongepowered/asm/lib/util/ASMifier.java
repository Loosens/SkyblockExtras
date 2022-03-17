package org.spongepowered.asm.lib.util;

import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.TypePath;

public class ASMifier extends Printer {
   protected final String name;
   protected final int id;
   protected Map<Label, String> labelNames;
   private static final int ACCESS_CLASS = 262144;
   private static final int ACCESS_FIELD = 524288;
   private static final int ACCESS_INNER = 1048576;

   public ASMifier() {
      this(327680, "cw", 0);
      if (this.getClass() != ASMifier.class) {
         throw new IllegalStateException();
      }
   }

   protected ASMifier(int var1, String var2, int var3) {
      super(var1);
      this.name = var2;
      this.id = var3;
   }

   public static void main(String[] param0) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public void visit(int param1, int param2, String param3, String param4, String param5, String[] param6) {
      // $FF: Couldn't be decompiled
   }

   public void visitSource(String var1, String var2) {
      this.buf.setLength(0);
      this.buf.append("cw.visitSource(");
      this.appendConstant(var1);
      this.buf.append(", ");
      this.appendConstant(var2);
      this.buf.append(");\n\n");
      this.text.add(this.buf.toString());
   }

   public void visitOuterClass(String var1, String var2, String var3) {
      this.buf.setLength(0);
      this.buf.append("cw.visitOuterClass(");
      this.appendConstant(var1);
      this.buf.append(", ");
      this.appendConstant(var2);
      this.buf.append(", ");
      this.appendConstant(var3);
      this.buf.append(");\n\n");
      this.text.add(this.buf.toString());
   }

   public ASMifier visitClassAnnotation(String var1, boolean var2) {
      return this.visitAnnotation(var1, var2);
   }

   public ASMifier visitClassTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitTypeAnnotation(var1, var2, var3, var4);
   }

   public void visitClassAttribute(Attribute var1) {
      this.visitAttribute(var1);
   }

   public void visitInnerClass(String var1, String var2, String var3, int var4) {
      this.buf.setLength(0);
      this.buf.append("cw.visitInnerClass(");
      this.appendConstant(var1);
      this.buf.append(", ");
      this.appendConstant(var2);
      this.buf.append(", ");
      this.appendConstant(var3);
      this.buf.append(", ");
      this.appendAccess(var4 | 1048576);
      this.buf.append(");\n\n");
      this.text.add(this.buf.toString());
   }

   public ASMifier visitField(int var1, String var2, String var3, String var4, Object var5) {
      this.buf.setLength(0);
      this.buf.append("{\n");
      this.buf.append("fv = cw.visitField(");
      this.appendAccess(var1 | 524288);
      this.buf.append(", ");
      this.appendConstant(var2);
      this.buf.append(", ");
      this.appendConstant(var3);
      this.buf.append(", ");
      this.appendConstant(var4);
      this.buf.append(", ");
      this.appendConstant(var5);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
      ASMifier var6 = this.createASMifier("fv", 0);
      this.text.add(var6.getText());
      this.text.add("}\n");
      return var6;
   }

   public ASMifier visitMethod(int param1, String param2, String param3, String param4, String[] param5) {
      // $FF: Couldn't be decompiled
   }

   public void visitClassEnd() {
      this.text.add("cw.visitEnd();\n\n");
      this.text.add("return cw.toByteArray();\n");
      this.text.add("}\n");
      this.text.add("}\n");
   }

   public void visit(String var1, Object var2) {
      this.buf.setLength(0);
      this.buf.append("av").append(this.id).append(".visit(");
      appendConstant(this.buf, var1);
      this.buf.append(", ");
      appendConstant(this.buf, var2);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitEnum(String var1, String var2, String var3) {
      this.buf.setLength(0);
      this.buf.append("av").append(this.id).append(".visitEnum(");
      appendConstant(this.buf, var1);
      this.buf.append(", ");
      appendConstant(this.buf, var2);
      this.buf.append(", ");
      appendConstant(this.buf, var3);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
   }

   public ASMifier visitAnnotation(String var1, String var2) {
      this.buf.setLength(0);
      this.buf.append("{\n");
      this.buf.append("AnnotationVisitor av").append(this.id + 1).append(" = av");
      this.buf.append(this.id).append(".visitAnnotation(");
      appendConstant(this.buf, var1);
      this.buf.append(", ");
      appendConstant(this.buf, var2);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
      ASMifier var3 = this.createASMifier("av", this.id + 1);
      this.text.add(var3.getText());
      this.text.add("}\n");
      return var3;
   }

   public ASMifier visitArray(String var1) {
      this.buf.setLength(0);
      this.buf.append("{\n");
      this.buf.append("AnnotationVisitor av").append(this.id + 1).append(" = av");
      this.buf.append(this.id).append(".visitArray(");
      appendConstant(this.buf, var1);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
      ASMifier var2 = this.createASMifier("av", this.id + 1);
      this.text.add(var2.getText());
      this.text.add("}\n");
      return var2;
   }

   public void visitAnnotationEnd() {
      this.buf.setLength(0);
      this.buf.append("av").append(this.id).append(".visitEnd();\n");
      this.text.add(this.buf.toString());
   }

   public ASMifier visitFieldAnnotation(String var1, boolean var2) {
      return this.visitAnnotation(var1, var2);
   }

   public ASMifier visitFieldTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitTypeAnnotation(var1, var2, var3, var4);
   }

   public void visitFieldAttribute(Attribute var1) {
      this.visitAttribute(var1);
   }

   public void visitFieldEnd() {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitEnd();\n");
      this.text.add(this.buf.toString());
   }

   public void visitParameter(String var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitParameter(");
      appendString(this.buf, var1);
      this.buf.append(", ");
      this.appendAccess(var2);
      this.text.add(this.buf.append(");\n").toString());
   }

   public ASMifier visitAnnotationDefault() {
      this.buf.setLength(0);
      this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitAnnotationDefault();\n");
      this.text.add(this.buf.toString());
      ASMifier var1 = this.createASMifier("av", 0);
      this.text.add(var1.getText());
      this.text.add("}\n");
      return var1;
   }

   public ASMifier visitMethodAnnotation(String var1, boolean var2) {
      return this.visitAnnotation(var1, var2);
   }

   public ASMifier visitMethodTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitTypeAnnotation(var1, var2, var3, var4);
   }

   public ASMifier visitParameterAnnotation(int var1, String var2, boolean var3) {
      this.buf.setLength(0);
      this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitParameterAnnotation(").append(var1).append(", ");
      this.appendConstant(var2);
      this.buf.append(", ").append(var3).append(");\n");
      this.text.add(this.buf.toString());
      ASMifier var4 = this.createASMifier("av", 0);
      this.text.add(var4.getText());
      this.text.add("}\n");
      return var4;
   }

   public void visitMethodAttribute(Attribute var1) {
      this.visitAttribute(var1);
   }

   public void visitCode() {
      this.text.add(this.name + ".visitCode();\n");
   }

   public void visitFrame(int param1, int param2, Object[] param3, int param4, Object[] param5) {
      // $FF: Couldn't be decompiled
   }

   public void visitInsn(int var1) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitInsn(").append(OPCODES[var1]).append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitIntInsn(int var1, int var2) {
      StringBuffer var10000;
      String var10001;
      label16: {
         try {
            this.buf.setLength(0);
            var10000 = this.buf.append(this.name).append(".visitIntInsn(").append(OPCODES[var1]).append(", ");
            if (var1 == 188) {
               var10001 = TYPES[var2];
               break label16;
            }
         } catch (IllegalStateException var3) {
            throw b(var3);
         }

         var10001 = Integer.toString(var2);
      }

      var10000.append(var10001).append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitVarInsn(int var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitVarInsn(").append(OPCODES[var1]).append(", ").append(var2).append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitTypeInsn(int var1, String var2) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitTypeInsn(").append(OPCODES[var1]).append(", ");
      this.appendConstant(var2);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitFieldInsn(").append(OPCODES[var1]).append(", ");
      this.appendConstant(var2);
      this.buf.append(", ");
      this.appendConstant(var3);
      this.buf.append(", ");
      this.appendConstant(var4);
      this.buf.append(");\n");
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

      ASMifier var10000;
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
      StringBuffer var10000;
      String var10001;
      label16: {
         try {
            this.buf.setLength(0);
            this.buf.append(this.name).append(".visitMethodInsn(").append(OPCODES[var1]).append(", ");
            this.appendConstant(var2);
            this.buf.append(", ");
            this.appendConstant(var3);
            this.buf.append(", ");
            this.appendConstant(var4);
            this.buf.append(", ");
            var10000 = this.buf;
            if (var5) {
               var10001 = "true";
               break label16;
            }
         } catch (IllegalStateException var6) {
            throw b(var6);
         }

         var10001 = "false";
      }

      var10000.append(var10001);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitInvokeDynamicInsn(String param1, String param2, Handle param3, Object... param4) {
      // $FF: Couldn't be decompiled
   }

   public void visitJumpInsn(int var1, Label var2) {
      this.buf.setLength(0);
      this.declareLabel(var2);
      this.buf.append(this.name).append(".visitJumpInsn(").append(OPCODES[var1]).append(", ");
      this.appendLabel(var2);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitLabel(Label var1) {
      this.buf.setLength(0);
      this.declareLabel(var1);
      this.buf.append(this.name).append(".visitLabel(");
      this.appendLabel(var1);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitLdcInsn(Object var1) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitLdcInsn(");
      this.appendConstant(var1);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitIincInsn(int var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitIincInsn(").append(var1).append(", ").append(var2).append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitTableSwitchInsn(int param1, int param2, Label param3, Label... param4) {
      // $FF: Couldn't be decompiled
   }

   public void visitLookupSwitchInsn(Label param1, int[] param2, Label[] param3) {
      // $FF: Couldn't be decompiled
   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitMultiANewArrayInsn(");
      this.appendConstant(var1);
      this.buf.append(", ").append(var2).append(");\n");
      this.text.add(this.buf.toString());
   }

   public ASMifier visitInsnAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitTypeAnnotation("visitInsnAnnotation", var1, var2, var3, var4);
   }

   public void visitTryCatchBlock(Label var1, Label var2, Label var3, String var4) {
      this.buf.setLength(0);
      this.declareLabel(var1);
      this.declareLabel(var2);
      this.declareLabel(var3);
      this.buf.append(this.name).append(".visitTryCatchBlock(");
      this.appendLabel(var1);
      this.buf.append(", ");
      this.appendLabel(var2);
      this.buf.append(", ");
      this.appendLabel(var3);
      this.buf.append(", ");
      this.appendConstant(var4);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
   }

   public ASMifier visitTryCatchAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitTypeAnnotation("visitTryCatchAnnotation", var1, var2, var3, var4);
   }

   public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitLocalVariable(");
      this.appendConstant(var1);
      this.buf.append(", ");
      this.appendConstant(var2);
      this.buf.append(", ");
      this.appendConstant(var3);
      this.buf.append(", ");
      this.appendLabel(var4);
      this.buf.append(", ");
      this.appendLabel(var5);
      this.buf.append(", ").append(var6).append(");\n");
      this.text.add(this.buf.toString());
   }

   public Printer visitLocalVariableAnnotation(int param1, TypePath param2, Label[] param3, Label[] param4, int[] param5, String param6, boolean param7) {
      // $FF: Couldn't be decompiled
   }

   public void visitLineNumber(int var1, Label var2) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitLineNumber(").append(var1).append(", ");
      this.appendLabel(var2);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitMaxs(int var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitMaxs(").append(var1).append(", ").append(var2).append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitMethodEnd() {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitEnd();\n");
      this.text.add(this.buf.toString());
   }

   public ASMifier visitAnnotation(String var1, boolean var2) {
      this.buf.setLength(0);
      this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitAnnotation(");
      this.appendConstant(var1);
      this.buf.append(", ").append(var2).append(");\n");
      this.text.add(this.buf.toString());
      ASMifier var3 = this.createASMifier("av", 0);
      this.text.add(var3.getText());
      this.text.add("}\n");
      return var3;
   }

   public ASMifier visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitTypeAnnotation("visitTypeAnnotation", var1, var2, var3, var4);
   }

   public ASMifier visitTypeAnnotation(String var1, int var2, TypePath var3, String var4, boolean var5) {
      label16: {
         try {
            this.buf.setLength(0);
            this.buf.append("{\n").append("av0 = ").append(this.name).append(".").append(var1).append("(");
            this.buf.append(var2);
            if (var3 == null) {
               this.buf.append(", null, ");
               break label16;
            }
         } catch (IllegalStateException var7) {
            throw b(var7);
         }

         this.buf.append(", TypePath.fromString(\"").append(var3).append("\"), ");
      }

      this.appendConstant(var4);
      this.buf.append(", ").append(var5).append(");\n");
      this.text.add(this.buf.toString());
      ASMifier var6 = this.createASMifier("av", 0);
      this.text.add(var6.getText());
      this.text.add("}\n");
      return var6;
   }

   public void visitAttribute(Attribute param1) {
      // $FF: Couldn't be decompiled
   }

   protected ASMifier createASMifier(String var1, int var2) {
      return new ASMifier(327680, var1, var2);
   }

   void appendAccess(int param1) {
      // $FF: Couldn't be decompiled
   }

   protected void appendConstant(Object var1) {
      appendConstant(this.buf, var1);
   }

   static void appendConstant(StringBuffer param0, Object param1) {
      // $FF: Couldn't be decompiled
   }

   private void declareFrameTypes(int param1, Object[] param2) {
      // $FF: Couldn't be decompiled
   }

   private void appendFrameTypes(int param1, Object[] param2) {
      // $FF: Couldn't be decompiled
   }

   protected void declareLabel(Label var1) {
      try {
         if (this.labelNames == null) {
            this.labelNames = new HashMap();
         }
      } catch (IllegalStateException var3) {
         throw b(var3);
      }

      String var2 = (String)this.labelNames.get(var1);
      if (var2 == null) {
         var2 = "l" + this.labelNames.size();
         this.labelNames.put(var1, var2);
         this.buf.append("Label ").append(var2).append(" = new Label();\n");
      }

   }

   protected void appendLabel(Label var1) {
      this.buf.append((String)this.labelNames.get(var1));
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

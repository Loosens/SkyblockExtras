package org.spongepowered.asm.lib.util;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.TypePath;

public abstract class Printer {
   public static final String[] OPCODES;
   public static final String[] TYPES;
   public static final String[] HANDLE_TAG;
   protected final int api;
   protected final StringBuffer buf;
   public final List<Object> text;

   protected Printer(int var1) {
      this.api = var1;
      this.buf = new StringBuffer();
      this.text = new ArrayList();
   }

   public abstract void visit(int var1, int var2, String var3, String var4, String var5, String[] var6);

   public abstract void visitSource(String var1, String var2);

   public abstract void visitOuterClass(String var1, String var2, String var3);

   public abstract Printer visitClassAnnotation(String var1, boolean var2);

   public Printer visitClassTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract void visitClassAttribute(Attribute var1);

   public abstract void visitInnerClass(String var1, String var2, String var3, int var4);

   public abstract Printer visitField(int var1, String var2, String var3, String var4, Object var5);

   public abstract Printer visitMethod(int var1, String var2, String var3, String var4, String[] var5);

   public abstract void visitClassEnd();

   public abstract void visit(String var1, Object var2);

   public abstract void visitEnum(String var1, String var2, String var3);

   public abstract Printer visitAnnotation(String var1, String var2);

   public abstract Printer visitArray(String var1);

   public abstract void visitAnnotationEnd();

   public abstract Printer visitFieldAnnotation(String var1, boolean var2);

   public Printer visitFieldTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract void visitFieldAttribute(Attribute var1);

   public abstract void visitFieldEnd();

   public void visitParameter(String var1, int var2) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract Printer visitAnnotationDefault();

   public abstract Printer visitMethodAnnotation(String var1, boolean var2);

   public Printer visitMethodTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract Printer visitParameterAnnotation(int var1, String var2, boolean var3);

   public abstract void visitMethodAttribute(Attribute var1);

   public abstract void visitCode();

   public abstract void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5);

   public abstract void visitInsn(int var1);

   public abstract void visitIntInsn(int var1, int var2);

   public abstract void visitVarInsn(int var1, int var2);

   public abstract void visitTypeInsn(int var1, String var2);

   public abstract void visitFieldInsn(int var1, String var2, String var3, String var4);

   /** @deprecated */
   @Deprecated
   public void visitMethodInsn(int param1, String param2, String param3, String param4) {
      // $FF: Couldn't be decompiled
   }

   public void visitMethodInsn(int param1, String param2, String param3, String param4, boolean param5) {
      // $FF: Couldn't be decompiled
   }

   public abstract void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4);

   public abstract void visitJumpInsn(int var1, Label var2);

   public abstract void visitLabel(Label var1);

   public abstract void visitLdcInsn(Object var1);

   public abstract void visitIincInsn(int var1, int var2);

   public abstract void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4);

   public abstract void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3);

   public abstract void visitMultiANewArrayInsn(String var1, int var2);

   public Printer visitInsnAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract void visitTryCatchBlock(Label var1, Label var2, Label var3, String var4);

   public Printer visitTryCatchAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6);

   public Printer visitLocalVariableAnnotation(int var1, TypePath var2, Label[] var3, Label[] var4, int[] var5, String var6, boolean var7) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract void visitLineNumber(int var1, Label var2);

   public abstract void visitMaxs(int var1, int var2);

   public abstract void visitMethodEnd();

   public List<Object> getText() {
      return this.text;
   }

   public void print(PrintWriter var1) {
      printList(var1, this.text);
   }

   public static void appendString(StringBuffer param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   static void printList(PrintWriter var0, List<?> var1) {
      for(int var2 = 0; var2 < var1.size(); ++var2) {
         Object var3 = var1.get(var2);

         try {
            if (var3 instanceof List) {
               printList(var0, (List)var3);
               continue;
            }
         } catch (RuntimeException var4) {
            throw b(var4);
         }

         var0.print(var3.toString());
      }

   }

   static {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

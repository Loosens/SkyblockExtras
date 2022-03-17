package org.spongepowered.asm.lib.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.TypePath;

public class CheckMethodAdapter extends MethodVisitor {
   public int version;
   private int access;
   private boolean startCode;
   private boolean endCode;
   private boolean endMethod;
   private int insnCount;
   private final Map<Label, Integer> labels;
   private Set<Label> usedLabels;
   private int expandedFrames;
   private int compressedFrames;
   private int lastFrame;
   private List<Label> handlers;
   private static final int[] TYPE;
   private static Field labelStatusField;

   public CheckMethodAdapter(MethodVisitor var1) {
      this(var1, new HashMap());
   }

   public CheckMethodAdapter(MethodVisitor var1, Map<Label, Integer> var2) {
      this(327680, var1, var2);
      if (this.getClass() != CheckMethodAdapter.class) {
         throw new IllegalStateException();
      }
   }

   protected CheckMethodAdapter(int var1, MethodVisitor var2, Map<Label, Integer> var3) {
      super(var1, var2);
      this.lastFrame = -1;
      this.labels = var3;
      this.usedLabels = new HashSet();
      this.handlers = new ArrayList();
   }

   public CheckMethodAdapter(int var1, String var2, String var3, MethodVisitor var4, Map<Label, Integer> var5) {
      this(new CheckMethodAdapter$1(327680, var1, var2, var3, (String)null, (String[])null, var4), var5);
      this.access = var1;
   }

   public void visitParameter(String var1, int var2) {
      try {
         if (var1 != null) {
            checkUnqualifiedName(this.version, var1, "name");
         }
      } catch (IllegalStateException var3) {
         throw b(var3);
      }

      CheckClassAdapter.checkAccess(var2, 36880);
      super.visitParameter(var1, var2);
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      this.checkEndMethod();
      checkDesc(var1, false);
      return new CheckAnnotationAdapter(super.visitAnnotation(var1, var2));
   }

   public AnnotationVisitor visitTypeAnnotation(int param1, TypePath param2, String param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   public AnnotationVisitor visitAnnotationDefault() {
      this.checkEndMethod();
      return new CheckAnnotationAdapter(super.visitAnnotationDefault(), false);
   }

   public AnnotationVisitor visitParameterAnnotation(int var1, String var2, boolean var3) {
      this.checkEndMethod();
      checkDesc(var2, false);
      return new CheckAnnotationAdapter(super.visitParameterAnnotation(var1, var2, var3));
   }

   public void visitAttribute(Attribute var1) {
      try {
         this.checkEndMethod();
         if (var1 == null) {
            throw new IllegalArgumentException("Invalid attribute (must not be null)");
         }
      } catch (IllegalStateException var2) {
         throw b(var2);
      }

      super.visitAttribute(var1);
   }

   public void visitCode() {
      try {
         if ((this.access & 1024) != 0) {
            throw new RuntimeException("Abstract methods cannot have code");
         }
      } catch (IllegalStateException var1) {
         throw b(var1);
      }

      this.startCode = true;
      super.visitCode();
   }

   public void visitFrame(int param1, int param2, Object[] param3, int param4, Object[] param5) {
      // $FF: Couldn't be decompiled
   }

   public void visitInsn(int var1) {
      this.checkStartCode();
      this.checkEndCode();
      checkOpcode(var1, 0);
      super.visitInsn(var1);
      ++this.insnCount;
   }

   public void visitIntInsn(int param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public void visitVarInsn(int var1, int var2) {
      this.checkStartCode();
      this.checkEndCode();
      checkOpcode(var1, 2);
      checkUnsignedShort(var2, "Invalid variable index");
      super.visitVarInsn(var1, var2);
      ++this.insnCount;
   }

   public void visitTypeInsn(int param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      this.checkStartCode();
      this.checkEndCode();
      checkOpcode(var1, 4);
      checkInternalName(var2, "owner");
      checkUnqualifiedName(this.version, var3, "name");
      checkDesc(var4, false);
      super.visitFieldInsn(var1, var2, var3, var4);
      ++this.insnCount;
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

      CheckMethodAdapter var10000;
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

   private void doVisitMethodInsn(int param1, String param2, String param3, String param4, boolean param5) {
      // $FF: Couldn't be decompiled
   }

   public void visitInvokeDynamicInsn(String param1, String param2, Handle param3, Object... param4) {
      // $FF: Couldn't be decompiled
   }

   public void visitJumpInsn(int var1, Label var2) {
      this.checkStartCode();
      this.checkEndCode();
      checkOpcode(var1, 6);
      this.checkLabel(var2, false, "label");
      checkNonDebugLabel(var2);
      super.visitJumpInsn(var1, var2);
      this.usedLabels.add(var2);
      ++this.insnCount;
   }

   public void visitLabel(Label var1) {
      try {
         this.checkStartCode();
         this.checkEndCode();
         this.checkLabel(var1, false, "label");
         if (this.labels.get(var1) != null) {
            throw new IllegalArgumentException("Already visited label");
         }
      } catch (IllegalStateException var2) {
         throw b(var2);
      }

      this.labels.put(var1, this.insnCount);
      super.visitLabel(var1);
   }

   public void visitLdcInsn(Object var1) {
      this.checkStartCode();
      this.checkEndCode();
      this.checkLDCConstant(var1);
      super.visitLdcInsn(var1);
      ++this.insnCount;
   }

   public void visitIincInsn(int var1, int var2) {
      this.checkStartCode();
      this.checkEndCode();
      checkUnsignedShort(var1, "Invalid variable index");
      checkSignedShort(var2, "Invalid increment");
      super.visitIincInsn(var1, var2);
      ++this.insnCount;
   }

   public void visitTableSwitchInsn(int param1, int param2, Label param3, Label... param4) {
      // $FF: Couldn't be decompiled
   }

   public void visitLookupSwitchInsn(Label param1, int[] param2, Label[] param3) {
      // $FF: Couldn't be decompiled
   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      try {
         this.checkStartCode();
         this.checkEndCode();
         checkDesc(var1, false);
         if (var1.charAt(0) != '[') {
            throw new IllegalArgumentException("Invalid descriptor (must be an array type descriptor): " + var1);
         }
      } catch (IllegalStateException var5) {
         throw b(var5);
      }

      try {
         if (var2 < 1) {
            throw new IllegalArgumentException("Invalid dimensions (must be greater than 0): " + var2);
         }
      } catch (IllegalStateException var3) {
         throw b(var3);
      }

      try {
         if (var2 > var1.lastIndexOf(91) + 1) {
            throw new IllegalArgumentException("Invalid dimensions (must not be greater than dims(desc)): " + var2);
         }
      } catch (IllegalStateException var4) {
         throw b(var4);
      }

      super.visitMultiANewArrayInsn(var1, var2);
      ++this.insnCount;
   }

   public AnnotationVisitor visitInsnAnnotation(int param1, TypePath param2, String param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   public void visitTryCatchBlock(Label param1, Label param2, Label param3, String param4) {
      // $FF: Couldn't be decompiled
   }

   public AnnotationVisitor visitTryCatchAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      this.checkStartCode();
      this.checkEndCode();
      int var5 = var1 >>> 24;

      try {
         if (var5 != 66) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(var5));
         }
      } catch (IllegalStateException var6) {
         throw b(var6);
      }

      CheckClassAdapter.checkTypeRefAndPath(var1, var2);
      checkDesc(var3, false);
      return new CheckAnnotationAdapter(super.visitTryCatchAnnotation(var1, var2, var3, var4));
   }

   public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
      this.checkStartCode();
      this.checkEndCode();
      checkUnqualifiedName(this.version, var1, "name");
      checkDesc(var2, false);
      this.checkLabel(var4, true, "start label");
      this.checkLabel(var5, true, "end label");
      checkUnsignedShort(var6, "Invalid variable index");
      int var7 = (Integer)this.labels.get(var4);
      int var8 = (Integer)this.labels.get(var5);

      try {
         if (var8 < var7) {
            throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
         }
      } catch (IllegalStateException var9) {
         throw b(var9);
      }

      super.visitLocalVariable(var1, var2, var3, var4, var5, var6);
   }

   public AnnotationVisitor visitLocalVariableAnnotation(int param1, TypePath param2, Label[] param3, Label[] param4, int[] param5, String param6, boolean param7) {
      // $FF: Couldn't be decompiled
   }

   public void visitLineNumber(int var1, Label var2) {
      this.checkStartCode();
      this.checkEndCode();
      checkUnsignedShort(var1, "Invalid line number");
      this.checkLabel(var2, true, "start label");
      super.visitLineNumber(var1, var2);
   }

   public void visitMaxs(int param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public void visitEnd() {
      this.checkEndMethod();
      this.endMethod = true;
      super.visitEnd();
   }

   void checkStartCode() {
      try {
         if (!this.startCode) {
            throw new IllegalStateException("Cannot visit instructions before visitCode has been called.");
         }
      } catch (IllegalStateException var1) {
         throw b(var1);
      }
   }

   void checkEndCode() {
      try {
         if (this.endCode) {
            throw new IllegalStateException("Cannot visit instructions after visitMaxs has been called.");
         }
      } catch (IllegalStateException var1) {
         throw b(var1);
      }
   }

   void checkEndMethod() {
      try {
         if (this.endMethod) {
            throw new IllegalStateException("Cannot visit elements after visitEnd has been called.");
         }
      } catch (IllegalStateException var1) {
         throw b(var1);
      }
   }

   void checkFrameValue(Object param1) {
      // $FF: Couldn't be decompiled
   }

   static void checkOpcode(int param0, int param1) {
      // $FF: Couldn't be decompiled
   }

   static void checkSignedByte(int param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   static void checkSignedShort(int param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   static void checkUnsignedShort(int param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   static void checkConstant(Object param0) {
      // $FF: Couldn't be decompiled
   }

   void checkLDCConstant(Object param1) {
      // $FF: Couldn't be decompiled
   }

   static void checkUnqualifiedName(int param0, String param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   static void checkIdentifier(String var0, String var1) {
      checkIdentifier(var0, 0, -1, var1);
   }

   static void checkIdentifier(String param0, int param1, int param2, String param3) {
      // $FF: Couldn't be decompiled
   }

   static void checkMethodIdentifier(int param0, String param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   static void checkInternalName(String param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   static void checkInternalName(String var0, int var1, int var2, String var3) {
      int var10000;
      label35: {
         try {
            if (var2 == -1) {
               var10000 = var0.length();
               break label35;
            }
         } catch (IllegalArgumentException var8) {
            throw b(var8);
         }

         var10000 = var2;
      }

      int var4 = var10000;

      try {
         int var5 = var1;

         int var6;
         do {
            var6 = var0.indexOf(47, var5 + 1);
            if (var6 == -1 || var6 > var4) {
               var6 = var4;
            }

            checkIdentifier(var0, var5, var6, (String)null);
            var5 = var6 + 1;
         } while(var6 != var4);

      } catch (IllegalArgumentException var7) {
         throw new IllegalArgumentException("Invalid " + var3 + " (must be a fully qualified class name in internal form): " + var0);
      }
   }

   static void checkDesc(String var0, boolean var1) {
      int var2 = checkDesc(var0, 0, var1);

      try {
         if (var2 != var0.length()) {
            throw new IllegalArgumentException("Invalid descriptor: " + var0);
         }
      } catch (IllegalStateException var3) {
         throw b(var3);
      }
   }

   static int checkDesc(String param0, int param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   static void checkMethodDesc(String param0) {
      // $FF: Couldn't be decompiled
   }

   void checkLabel(Label param1, boolean param2, String param3) {
      // $FF: Couldn't be decompiled
   }

   private static void checkNonDebugLabel(Label var0) {
      Field var1 = getLabelStatusField();
      boolean var2 = false;

      int var7;
      try {
         int var10000;
         label28: {
            try {
               if (var1 == null) {
                  var10000 = 0;
                  break label28;
               }
            } catch (IllegalAccessException var5) {
               throw b(var5);
            }

            var10000 = (Integer)var1.get(var0);
         }

         var7 = var10000;
      } catch (IllegalAccessException var6) {
         throw new Error("Internal error");
      }

      try {
         if ((var7 & 1) != 0) {
            throw new IllegalArgumentException("Labels used for debug info cannot be reused for control flow");
         }
      } catch (IllegalAccessException var4) {
         throw b(var4);
      }
   }

   private static Field getLabelStatusField() {
      // $FF: Couldn't be decompiled
   }

   private static Field getLabelField(String var0) {
      try {
         Field var1 = Label.class.getDeclaredField(var0);
         var1.setAccessible(true);
         return var1;
      } catch (NoSuchFieldException var2) {
         return null;
      }
   }

   static {
      String var0 = "BBBBBBBBBBBBBBBBCCIAADDDDDAAAAAAAAAAAAAAAAAAAABBBBBBBBDDDDDAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBJBBBBBBBBBBBBBBBBBBBBHHHHHHHHHHHHHHHHDKLBBBBBBFFFFGGGGAECEBBEEBBAMHHAA";
      TYPE = new int[var0.length()];
      int var1 = 0;

      try {
         while(var1 < TYPE.length) {
            TYPE[var1] = var0.charAt(var1) - 65 - 1;
            ++var1;
         }

      } catch (IllegalStateException var2) {
         throw b(var2);
      }
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

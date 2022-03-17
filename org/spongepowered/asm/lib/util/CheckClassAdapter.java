package org.spongepowered.asm.lib.util;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TryCatchBlockNode;
import org.spongepowered.asm.lib.tree.analysis.Analyzer;
import org.spongepowered.asm.lib.tree.analysis.BasicValue;
import org.spongepowered.asm.lib.tree.analysis.Frame;
import org.spongepowered.asm.lib.tree.analysis.SimpleVerifier;

public class CheckClassAdapter extends ClassVisitor {
   private int version;
   private boolean start;
   private boolean source;
   private boolean outer;
   private boolean end;
   private Map<Label, Integer> labels;
   private boolean checkDataFlow;

   public static void main(String[] var0) throws Exception {
      try {
         if (var0.length != 1) {
            System.err.println("Verifies the given class.");
            System.err.println("Usage: CheckClassAdapter <fully qualified class name or class file name>");
            return;
         }
      } catch (Exception var2) {
         throw b(var2);
      }

      ClassReader var1;
      if (var0[0].endsWith(".class")) {
         var1 = new ClassReader(new FileInputStream(var0[0]));
      } else {
         var1 = new ClassReader(var0[0]);
      }

      verify(var1, false, new PrintWriter(System.err));
   }

   public static void verify(ClassReader var0, ClassLoader var1, boolean var2, PrintWriter var3) {
      ClassNode var4 = new ClassNode();

      Type var10000;
      label73: {
         try {
            var0.accept(new CheckClassAdapter(var4, false), 2);
            if (var4.superName == null) {
               var10000 = null;
               break label73;
            }
         } catch (Exception var17) {
            throw b(var17);
         }

         var10000 = Type.getObjectType(var4.superName);
      }

      Type var5 = var10000;
      List var6 = var4.methods;
      ArrayList var7 = new ArrayList();
      Iterator var8 = var4.interfaces.iterator();

      try {
         while(var8.hasNext()) {
            var7.add(Type.getObjectType((String)var8.next()));
         }
      } catch (Exception var16) {
         throw b(var16);
      }

      for(int var18 = 0; var18 < var6.size(); ++var18) {
         MethodNode var9 = (MethodNode)var6.get(var18);

         SimpleVerifier var10001;
         Type var10002;
         SimpleVerifier var19;
         Type var10003;
         ArrayList var10004;
         boolean var10005;
         label55: {
            try {
               var19 = new SimpleVerifier;
               var10001 = var19;
               var10002 = Type.getObjectType(var4.name);
               var10003 = var5;
               var10004 = var7;
               if ((var4.access & 512) != 0) {
                  var10005 = true;
                  break label55;
               }
            } catch (Exception var15) {
               throw b(var15);
            }

            var10005 = false;
         }

         var10001.<init>(var10002, var10003, var10004, var10005);
         SimpleVerifier var10 = var19;
         Analyzer var11 = new Analyzer(var10);

         try {
            if (var1 != null) {
               var10.setClassLoader(var1);
            }
         } catch (Exception var13) {
            throw b(var13);
         }

         try {
            var11.analyze(var4.name, var9);
            if (!var2) {
               continue;
            }
         } catch (Exception var14) {
            var14.printStackTrace(var3);
         }

         printAnalyzerResult(var9, var11, var3);
      }

      var3.flush();
   }

   public static void verify(ClassReader var0, boolean var1, PrintWriter var2) {
      verify(var0, (ClassLoader)null, var1, var2);
   }

   static void printAnalyzerResult(MethodNode var0, Analyzer<BasicValue> var1, PrintWriter var2) {
      Frame[] var3 = var1.getFrames();
      Textifier var4 = new Textifier();
      TraceMethodVisitor var5 = new TraceMethodVisitor(var4);
      var2.println(var0.name + var0.desc);

      int var6;
      for(var6 = 0; var6 < var0.instructions.size(); ++var6) {
         var0.instructions.get(var6).accept(var5);
         StringBuilder var7 = new StringBuilder();
         Frame var8 = var3[var6];

         label75: {
            try {
               if (var8 == null) {
                  var7.append('?');
                  break label75;
               }
            } catch (IllegalStateException var14) {
               throw b(var14);
            }

            int var9 = 0;

            try {
               while(var9 < var8.getLocals()) {
                  var7.append(getShortName(((BasicValue)var8.getLocal(var9)).toString())).append(' ');
                  ++var9;
               }
            } catch (IllegalStateException var13) {
               throw b(var13);
            }

            var7.append(" : ");
            var9 = 0;

            try {
               while(var9 < var8.getStackSize()) {
                  var7.append(getShortName(((BasicValue)var8.getStack(var9)).toString())).append(' ');
                  ++var9;
               }
            } catch (IllegalStateException var12) {
               throw b(var12);
            }
         }

         try {
            while(var7.length() < var0.maxStack + var0.maxLocals + 1) {
               var7.append(' ');
            }
         } catch (IllegalStateException var11) {
            throw b(var11);
         }

         var2.print(Integer.toString(var6 + 100000).substring(1));
         var2.print(" " + var7 + " : " + var4.text.get(var4.text.size() - 1));
      }

      var6 = 0;

      try {
         while(var6 < var0.tryCatchBlocks.size()) {
            ((TryCatchBlockNode)var0.tryCatchBlocks.get(var6)).accept(var5);
            var2.print(" " + var4.text.get(var4.text.size() - 1));
            ++var6;
         }
      } catch (IllegalStateException var10) {
         throw b(var10);
      }

      var2.println();
   }

   private static String getShortName(String var0) {
      int var1 = var0.lastIndexOf(47);
      int var2 = var0.length();

      try {
         if (var0.charAt(var2 - 1) == ';') {
            --var2;
         }
      } catch (IllegalStateException var3) {
         throw b(var3);
      }

      String var10000;
      try {
         if (var1 == -1) {
            var10000 = var0;
            return var10000;
         }
      } catch (IllegalStateException var4) {
         throw b(var4);
      }

      var10000 = var0.substring(var1 + 1, var2);
      return var10000;
   }

   public CheckClassAdapter(ClassVisitor var1) {
      this(var1, true);
   }

   public CheckClassAdapter(ClassVisitor var1, boolean var2) {
      this(327680, var1, var2);
      if (this.getClass() != CheckClassAdapter.class) {
         throw new IllegalStateException();
      }
   }

   protected CheckClassAdapter(int var1, ClassVisitor var2, boolean var3) {
      super(var1, var2);
      this.labels = new HashMap();
      this.checkDataFlow = var3;
   }

   public void visit(int param1, int param2, String param3, String param4, String param5, String[] param6) {
      // $FF: Couldn't be decompiled
   }

   public void visitSource(String var1, String var2) {
      try {
         this.checkState();
         if (this.source) {
            throw new IllegalStateException("visitSource can be called only once.");
         }
      } catch (IllegalStateException var3) {
         throw b(var3);
      }

      this.source = true;
      super.visitSource(var1, var2);
   }

   public void visitOuterClass(String var1, String var2, String var3) {
      try {
         this.checkState();
         if (this.outer) {
            throw new IllegalStateException("visitOuterClass can be called only once.");
         }
      } catch (IllegalStateException var6) {
         throw b(var6);
      }

      try {
         this.outer = true;
         if (var1 == null) {
            throw new IllegalArgumentException("Illegal outer class owner");
         }
      } catch (IllegalStateException var4) {
         throw b(var4);
      }

      try {
         if (var3 != null) {
            CheckMethodAdapter.checkMethodDesc(var3);
         }
      } catch (IllegalStateException var5) {
         throw b(var5);
      }

      super.visitOuterClass(var1, var2, var3);
   }

   public void visitInnerClass(String param1, String param2, String param3, int param4) {
      // $FF: Couldn't be decompiled
   }

   public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      try {
         this.checkState();
         checkAccess(var1, 413919);
         CheckMethodAdapter.checkUnqualifiedName(this.version, var2, "field name");
         CheckMethodAdapter.checkDesc(var3, false);
         if (var4 != null) {
            checkFieldSignature(var4);
         }
      } catch (IllegalStateException var7) {
         throw b(var7);
      }

      try {
         if (var5 != null) {
            CheckMethodAdapter.checkConstant(var5);
         }
      } catch (IllegalStateException var8) {
         throw b(var8);
      }

      FieldVisitor var6 = super.visitField(var1, var2, var3, var4, var5);
      return new CheckFieldAdapter(var6);
   }

   public MethodVisitor visitMethod(int param1, String param2, String param3, String param4, String[] param5) {
      // $FF: Couldn't be decompiled
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      this.checkState();
      CheckMethodAdapter.checkDesc(var1, false);
      return new CheckAnnotationAdapter(super.visitAnnotation(var1, var2));
   }

   public AnnotationVisitor visitTypeAnnotation(int param1, TypePath param2, String param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   public void visitAttribute(Attribute var1) {
      try {
         this.checkState();
         if (var1 == null) {
            throw new IllegalArgumentException("Invalid attribute (must not be null)");
         }
      } catch (IllegalStateException var2) {
         throw b(var2);
      }

      super.visitAttribute(var1);
   }

   public void visitEnd() {
      this.checkState();
      this.end = true;
      super.visitEnd();
   }

   private void checkState() {
      try {
         if (!this.start) {
            throw new IllegalStateException("Cannot visit member before visit has been called.");
         }
      } catch (IllegalStateException var2) {
         throw b(var2);
      }

      try {
         if (this.end) {
            throw new IllegalStateException("Cannot visit member after visitEnd has been called.");
         }
      } catch (IllegalStateException var1) {
         throw b(var1);
      }
   }

   static void checkAccess(int var0, int var1) {
      try {
         if ((var0 & ~var1) != 0) {
            throw new IllegalArgumentException("Invalid access flags: " + var0);
         }
      } catch (IllegalStateException var9) {
         throw b(var9);
      }

      byte var10000;
      label94: {
         try {
            if ((var0 & 1) == 0) {
               var10000 = 0;
               break label94;
            }
         } catch (IllegalStateException var14) {
            throw b(var14);
         }

         var10000 = 1;
      }

      byte var2 = var10000;

      label86: {
         try {
            if ((var0 & 2) == 0) {
               var10000 = 0;
               break label86;
            }
         } catch (IllegalStateException var13) {
            throw b(var13);
         }

         var10000 = 1;
      }

      byte var3 = var10000;

      label78: {
         try {
            if ((var0 & 4) == 0) {
               var10000 = 0;
               break label78;
            }
         } catch (IllegalStateException var12) {
            throw b(var12);
         }

         var10000 = 1;
      }

      byte var4 = var10000;

      try {
         if (var2 + var3 + var4 > 1) {
            throw new IllegalArgumentException("public private and protected are mutually exclusive: " + var0);
         }
      } catch (IllegalStateException var8) {
         throw b(var8);
      }

      label70: {
         try {
            if ((var0 & 16) == 0) {
               var10000 = 0;
               break label70;
            }
         } catch (IllegalStateException var11) {
            throw b(var11);
         }

         var10000 = 1;
      }

      byte var5 = var10000;

      label62: {
         try {
            if ((var0 & 1024) == 0) {
               var10000 = 0;
               break label62;
            }
         } catch (IllegalStateException var10) {
            throw b(var10);
         }

         var10000 = 1;
      }

      byte var6 = var10000;

      try {
         if (var5 + var6 > 1) {
            throw new IllegalArgumentException("final and abstract are mutually exclusive: " + var0);
         }
      } catch (IllegalStateException var7) {
         throw b(var7);
      }
   }

   public static void checkClassSignature(String var0) {
      int var1 = 0;
      if (getChar(var0, 0) == '<') {
         var1 = checkFormalTypeParameters(var0, var1);
      }

      for(var1 = checkClassTypeSignature(var0, var1); getChar(var0, var1) == 'L'; var1 = checkClassTypeSignature(var0, var1)) {
      }

      try {
         if (var1 != var0.length()) {
            throw new IllegalArgumentException(var0 + ": error at index " + var1);
         }
      } catch (IllegalStateException var2) {
         throw b(var2);
      }
   }

   public static void checkMethodSignature(String var0) {
      int var1 = 0;
      if (getChar(var0, 0) == '<') {
         var1 = checkFormalTypeParameters(var0, var1);
      }

      for(var1 = checkChar('(', var0, var1); "ZCBSIFJDL[T".indexOf(getChar(var0, var1)) != -1; var1 = checkTypeSignature(var0, var1)) {
      }

      var1 = checkChar(')', var0, var1);

      label49: {
         try {
            if (getChar(var0, var1) == 'V') {
               ++var1;
               break label49;
            }
         } catch (IllegalStateException var4) {
            throw b(var4);
         }

         var1 = checkTypeSignature(var0, var1);
      }

      label42:
      while(true) {
         while(true) {
            try {
               if (getChar(var0, var1) != '^') {
                  break label42;
               }

               ++var1;
               if (getChar(var0, var1) != 'L') {
                  break;
               }
            } catch (IllegalStateException var3) {
               throw b(var3);
            }

            var1 = checkClassTypeSignature(var0, var1);
         }

         var1 = checkTypeVariableSignature(var0, var1);
      }

      try {
         if (var1 != var0.length()) {
            throw new IllegalArgumentException(var0 + ": error at index " + var1);
         }
      } catch (IllegalStateException var2) {
         throw b(var2);
      }
   }

   public static void checkFieldSignature(String var0) {
      int var1 = checkFieldTypeSignature(var0, 0);

      try {
         if (var1 != var0.length()) {
            throw new IllegalArgumentException(var0 + ": error at index " + var1);
         }
      } catch (IllegalStateException var2) {
         throw b(var2);
      }
   }

   static void checkTypeRefAndPath(int param0, TypePath param1) {
      // $FF: Couldn't be decompiled
   }

   private static int checkFormalTypeParameters(String var0, int var1) {
      var1 = checkChar('<', var0, var1);

      for(var1 = checkFormalTypeParameter(var0, var1); getChar(var0, var1) != '>'; var1 = checkFormalTypeParameter(var0, var1)) {
      }

      return var1 + 1;
   }

   private static int checkFormalTypeParameter(String var0, int var1) {
      var1 = checkIdentifier(var0, var1);
      var1 = checkChar(':', var0, var1);
      if ("L[T".indexOf(getChar(var0, var1)) != -1) {
         var1 = checkFieldTypeSignature(var0, var1);
      }

      while(getChar(var0, var1) == ':') {
         var1 = checkFieldTypeSignature(var0, var1 + 1);
      }

      return var1;
   }

   private static int checkFieldTypeSignature(String var0, int var1) {
      try {
         switch(getChar(var0, var1)) {
         case 'L':
            return checkClassTypeSignature(var0, var1);
         case '[':
            return checkTypeSignature(var0, var1 + 1);
         }
      } catch (IllegalStateException var2) {
         throw b(var2);
      }
   }

   private static int checkClassTypeSignature(String var0, int var1) {
      var1 = checkChar('L', var0, var1);

      for(var1 = checkIdentifier(var0, var1); getChar(var0, var1) == '/'; var1 = checkIdentifier(var0, var1 + 1)) {
      }

      if (getChar(var0, var1) == '<') {
         var1 = checkTypeArguments(var0, var1);
      }

      while(getChar(var0, var1) == '.') {
         var1 = checkIdentifier(var0, var1 + 1);
         if (getChar(var0, var1) == '<') {
            var1 = checkTypeArguments(var0, var1);
         }
      }

      return checkChar(';', var0, var1);
   }

   private static int checkTypeArguments(String var0, int var1) {
      var1 = checkChar('<', var0, var1);

      for(var1 = checkTypeArgument(var0, var1); getChar(var0, var1) != '>'; var1 = checkTypeArgument(var0, var1)) {
      }

      return var1 + 1;
   }

   private static int checkTypeArgument(String param0, int param1) {
      // $FF: Couldn't be decompiled
   }

   private static int checkTypeVariableSignature(String var0, int var1) {
      var1 = checkChar('T', var0, var1);
      var1 = checkIdentifier(var0, var1);
      return checkChar(';', var0, var1);
   }

   private static int checkTypeSignature(String var0, int var1) {
      try {
         switch(getChar(var0, var1)) {
         case 'B':
         case 'C':
         case 'D':
         case 'F':
         case 'I':
         case 'J':
         case 'S':
         case 'Z':
            return var1 + 1;
         case 'E':
         case 'G':
         case 'H':
         case 'K':
         case 'L':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'T':
         case 'U':
         case 'V':
         case 'W':
         case 'X':
         case 'Y':
         }
      } catch (IllegalStateException var2) {
         throw b(var2);
      }

      return checkFieldTypeSignature(var0, var1);
   }

   private static int checkIdentifier(String var0, int var1) {
      try {
         if (!Character.isJavaIdentifierStart(getChar(var0, var1))) {
            throw new IllegalArgumentException(var0 + ": identifier expected at index " + var1);
         }
      } catch (IllegalStateException var3) {
         throw b(var3);
      }

      ++var1;

      try {
         while(Character.isJavaIdentifierPart(getChar(var0, var1))) {
            ++var1;
         }

         return var1;
      } catch (IllegalStateException var2) {
         throw b(var2);
      }
   }

   private static int checkChar(char var0, String var1, int var2) {
      try {
         if (getChar(var1, var2) == var0) {
            return var2 + 1;
         }
      } catch (IllegalStateException var3) {
         throw b(var3);
      }

      throw new IllegalArgumentException(var1 + ": '" + var0 + "' expected at index " + var2);
   }

   private static char getChar(String var0, int var1) {
      char var10000;
      try {
         if (var1 < var0.length()) {
            var10000 = var0.charAt(var1);
            return var10000;
         }
      } catch (IllegalStateException var2) {
         throw b(var2);
      }

      var10000 = 0;
      return var10000;
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

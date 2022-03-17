package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.TypePath;

public class ClassNode extends ClassVisitor {
   public int version;
   public int access;
   public String name;
   public String signature;
   public String superName;
   public List<String> interfaces;
   public String sourceFile;
   public String sourceDebug;
   public String outerClass;
   public String outerMethod;
   public String outerMethodDesc;
   public List<AnnotationNode> visibleAnnotations;
   public List<AnnotationNode> invisibleAnnotations;
   public List<TypeAnnotationNode> visibleTypeAnnotations;
   public List<TypeAnnotationNode> invisibleTypeAnnotations;
   public List<Attribute> attrs;
   public List<InnerClassNode> innerClasses;
   public List<FieldNode> fields;
   public List<MethodNode> methods;

   public ClassNode() {
      this(327680);
      if (this.getClass() != ClassNode.class) {
         throw new IllegalStateException();
      }
   }

   public ClassNode(int var1) {
      super(var1);
      this.interfaces = new ArrayList();
      this.innerClasses = new ArrayList();
      this.fields = new ArrayList();
      this.methods = new ArrayList();
   }

   public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      try {
         this.version = var1;
         this.access = var2;
         this.name = var3;
         this.signature = var4;
         this.superName = var5;
         if (var6 != null) {
            this.interfaces.addAll(Arrays.asList(var6));
         }

      } catch (IllegalStateException var7) {
         throw b(var7);
      }
   }

   public void visitSource(String var1, String var2) {
      this.sourceFile = var1;
      this.sourceDebug = var2;
   }

   public void visitOuterClass(String var1, String var2, String var3) {
      this.outerClass = var1;
      this.outerMethod = var2;
      this.outerMethodDesc = var3;
   }

   public AnnotationVisitor visitAnnotation(String param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public AnnotationVisitor visitTypeAnnotation(int param1, TypePath param2, String param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   public void visitAttribute(Attribute var1) {
      try {
         if (this.attrs == null) {
            this.attrs = new ArrayList(1);
         }
      } catch (IllegalStateException var2) {
         throw b(var2);
      }

      this.attrs.add(var1);
   }

   public void visitInnerClass(String var1, String var2, String var3, int var4) {
      InnerClassNode var5 = new InnerClassNode(var1, var2, var3, var4);
      this.innerClasses.add(var5);
   }

   public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      FieldNode var6 = new FieldNode(var1, var2, var3, var4, var5);
      this.fields.add(var6);
      return var6;
   }

   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      MethodNode var6 = new MethodNode(var1, var2, var3, var4, var5);
      this.methods.add(var6);
      return var6;
   }

   public void visitEnd() {
   }

   public void check(int param1) {
      // $FF: Couldn't be decompiled
   }

   public void accept(ClassVisitor param1) {
      // $FF: Couldn't be decompiled
   }

   private static IllegalStateException b(IllegalStateException var0) {
      return var0;
   }
}

package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;

public class MethodNode extends MethodVisitor {
   public int access;
   public String name;
   public String desc;
   public String signature;
   public List<String> exceptions;
   public List<ParameterNode> parameters;
   public List<AnnotationNode> visibleAnnotations;
   public List<AnnotationNode> invisibleAnnotations;
   public List<TypeAnnotationNode> visibleTypeAnnotations;
   public List<TypeAnnotationNode> invisibleTypeAnnotations;
   public List<Attribute> attrs;
   public Object annotationDefault;
   public List<AnnotationNode>[] visibleParameterAnnotations;
   public List<AnnotationNode>[] invisibleParameterAnnotations;
   public InsnList instructions;
   public List<TryCatchBlockNode> tryCatchBlocks;
   public int maxStack;
   public int maxLocals;
   public List<LocalVariableNode> localVariables;
   public List<LocalVariableAnnotationNode> visibleLocalVariableAnnotations;
   public List<LocalVariableAnnotationNode> invisibleLocalVariableAnnotations;
   private boolean visited;

   public MethodNode() {
      this(327680);
      if (this.getClass() != MethodNode.class) {
         throw new IllegalStateException();
      }
   }

   public MethodNode(int var1) {
      super(var1);
      this.instructions = new InsnList();
   }

   public MethodNode(int var1, String var2, String var3, String var4, String[] var5) {
      this(327680, var1, var2, var3, var4, var5);
      if (this.getClass() != MethodNode.class) {
         throw new IllegalStateException();
      }
   }

   public MethodNode(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      super(var1);
      this.access = var2;
      this.name = var3;
      this.desc = var4;
      this.signature = var5;
      this.exceptions = new ArrayList(var6 == null ? 0 : var6.length);
      boolean var7 = (var2 & 1024) != 0;

      try {
         if (!var7) {
            this.localVariables = new ArrayList(5);
         }
      } catch (IllegalStateException var8) {
         throw b(var8);
      }

      try {
         this.tryCatchBlocks = new ArrayList();
         if (var6 != null) {
            this.exceptions.addAll(Arrays.asList(var6));
         }
      } catch (IllegalStateException var9) {
         throw b(var9);
      }

      this.instructions = new InsnList();
   }

   public void visitParameter(String var1, int var2) {
      try {
         if (this.parameters == null) {
            this.parameters = new ArrayList(5);
         }
      } catch (IllegalStateException var3) {
         throw b(var3);
      }

      this.parameters.add(new ParameterNode(var1, var2));
   }

   public AnnotationVisitor visitAnnotationDefault() {
      return new AnnotationNode(new MethodNode$1(this, 0));
   }

   public AnnotationVisitor visitAnnotation(String param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public AnnotationVisitor visitTypeAnnotation(int param1, TypePath param2, String param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   public AnnotationVisitor visitParameterAnnotation(int var1, String var2, boolean var3) {
      AnnotationNode var4 = new AnnotationNode(var2);

      int var5;
      label58: {
         label46: {
            try {
               if (!var3) {
                  break label58;
               }

               if (this.visibleParameterAnnotations != null) {
                  break label46;
               }
            } catch (IllegalStateException var8) {
               throw b(var8);
            }

            var5 = Type.getArgumentTypes(this.desc).length;
            this.visibleParameterAnnotations = (List[])(new List[var5]);
         }

         try {
            if (this.visibleParameterAnnotations[var1] == null) {
               this.visibleParameterAnnotations[var1] = new ArrayList(1);
            }
         } catch (IllegalStateException var7) {
            throw b(var7);
         }

         this.visibleParameterAnnotations[var1].add(var4);
         return var4;
      }

      if (this.invisibleParameterAnnotations == null) {
         var5 = Type.getArgumentTypes(this.desc).length;
         this.invisibleParameterAnnotations = (List[])(new List[var5]);
      }

      try {
         if (this.invisibleParameterAnnotations[var1] == null) {
            this.invisibleParameterAnnotations[var1] = new ArrayList(1);
         }
      } catch (IllegalStateException var6) {
         throw b(var6);
      }

      this.invisibleParameterAnnotations[var1].add(var4);
      return var4;
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

   public void visitCode() {
   }

   public void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5) {
      InsnList var10000;
      FrameNode var10001;
      FrameNode var10002;
      int var10003;
      int var10004;
      Object[] var10005;
      label21: {
         try {
            var10000 = this.instructions;
            var10001 = new FrameNode;
            var10002 = var10001;
            var10003 = var1;
            var10004 = var2;
            if (var3 == null) {
               var10005 = null;
               break label21;
            }
         } catch (IllegalStateException var6) {
            throw b(var6);
         }

         var10005 = this.getLabelNodes(var3);
      }

      var10002.<init>(var10003, var10004, var10005, var4, var5 == null ? null : this.getLabelNodes(var5));
      var10000.add((AbstractInsnNode)var10001);
   }

   public void visitInsn(int var1) {
      this.instructions.add((AbstractInsnNode)(new InsnNode(var1)));
   }

   public void visitIntInsn(int var1, int var2) {
      this.instructions.add((AbstractInsnNode)(new IntInsnNode(var1, var2)));
   }

   public void visitVarInsn(int var1, int var2) {
      this.instructions.add((AbstractInsnNode)(new VarInsnNode(var1, var2)));
   }

   public void visitTypeInsn(int var1, String var2) {
      this.instructions.add((AbstractInsnNode)(new TypeInsnNode(var1, var2)));
   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      this.instructions.add((AbstractInsnNode)(new FieldInsnNode(var1, var2, var3, var4)));
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

      this.instructions.add((AbstractInsnNode)(new MethodInsnNode(var1, var2, var3, var4)));
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

      this.instructions.add((AbstractInsnNode)(new MethodInsnNode(var1, var2, var3, var4, var5)));
   }

   public void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4) {
      this.instructions.add((AbstractInsnNode)(new InvokeDynamicInsnNode(var1, var2, var3, var4)));
   }

   public void visitJumpInsn(int var1, Label var2) {
      this.instructions.add((AbstractInsnNode)(new JumpInsnNode(var1, this.getLabelNode(var2))));
   }

   public void visitLabel(Label var1) {
      this.instructions.add((AbstractInsnNode)this.getLabelNode(var1));
   }

   public void visitLdcInsn(Object var1) {
      this.instructions.add((AbstractInsnNode)(new LdcInsnNode(var1)));
   }

   public void visitIincInsn(int var1, int var2) {
      this.instructions.add((AbstractInsnNode)(new IincInsnNode(var1, var2)));
   }

   public void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4) {
      this.instructions.add((AbstractInsnNode)(new TableSwitchInsnNode(var1, var2, this.getLabelNode(var3), this.getLabelNodes(var4))));
   }

   public void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3) {
      this.instructions.add((AbstractInsnNode)(new LookupSwitchInsnNode(this.getLabelNode(var1), var2, this.getLabelNodes(var3))));
   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      this.instructions.add((AbstractInsnNode)(new MultiANewArrayInsnNode(var1, var2)));
   }

   public AnnotationVisitor visitInsnAnnotation(int param1, TypePath param2, String param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   public void visitTryCatchBlock(Label var1, Label var2, Label var3, String var4) {
      this.tryCatchBlocks.add(new TryCatchBlockNode(this.getLabelNode(var1), this.getLabelNode(var2), this.getLabelNode(var3), var4));
   }

   public AnnotationVisitor visitTryCatchAnnotation(int param1, TypePath param2, String param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
      this.localVariables.add(new LocalVariableNode(var1, var2, var3, this.getLabelNode(var4), this.getLabelNode(var5), var6));
   }

   public AnnotationVisitor visitLocalVariableAnnotation(int param1, TypePath param2, Label[] param3, Label[] param4, int[] param5, String param6, boolean param7) {
      // $FF: Couldn't be decompiled
   }

   public void visitLineNumber(int var1, Label var2) {
      this.instructions.add((AbstractInsnNode)(new LineNumberNode(var1, this.getLabelNode(var2))));
   }

   public void visitMaxs(int var1, int var2) {
      this.maxStack = var1;
      this.maxLocals = var2;
   }

   public void visitEnd() {
   }

   protected LabelNode getLabelNode(Label var1) {
      try {
         if (!(var1.info instanceof LabelNode)) {
            var1.info = new LabelNode();
         }
      } catch (IllegalStateException var2) {
         throw b(var2);
      }

      return (LabelNode)var1.info;
   }

   private LabelNode[] getLabelNodes(Label[] var1) {
      LabelNode[] var2 = new LabelNode[var1.length];
      int var3 = 0;

      try {
         while(var3 < var1.length) {
            var2[var3] = this.getLabelNode(var1[var3]);
            ++var3;
         }

         return var2;
      } catch (IllegalStateException var4) {
         throw b(var4);
      }
   }

   private Object[] getLabelNodes(Object[] var1) {
      Object[] var2 = new Object[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         Object var4 = var1[var3];
         if (var4 instanceof Label) {
            var4 = this.getLabelNode((Label)var4);
         }

         var2[var3] = var4;
      }

      return var2;
   }

   public void check(int param1) {
      // $FF: Couldn't be decompiled
   }

   public void accept(ClassVisitor var1) {
      String[] var2 = new String[this.exceptions.size()];
      this.exceptions.toArray(var2);
      MethodVisitor var3 = var1.visitMethod(this.access, this.name, this.desc, this.signature, var2);

      try {
         if (var3 != null) {
            this.accept(var3);
         }

      } catch (IllegalStateException var4) {
         throw b(var4);
      }
   }

   public void accept(MethodVisitor param1) {
      // $FF: Couldn't be decompiled
   }

   private static IllegalStateException b(IllegalStateException var0) {
      return var0;
   }
}

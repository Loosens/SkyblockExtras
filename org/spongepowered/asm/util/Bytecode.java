package org.spongepowered.asm.util;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.FrameNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.IntInsnNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.LineNumberNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.lib.util.CheckClassAdapter;
import org.spongepowered.asm.lib.util.TraceClassVisitor;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.util.throwables.SyntheticBridgeException;

public final class Bytecode {
   public static final int[] CONSTANTS_INT = new int[]{2, 3, 4, 5, 6, 7, 8};
   public static final int[] CONSTANTS_FLOAT = new int[]{11, 12, 13};
   public static final int[] CONSTANTS_DOUBLE = new int[]{14, 15};
   public static final int[] CONSTANTS_LONG = new int[]{9, 10};
   public static final int[] CONSTANTS_ALL = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18};
   private static final Object[] CONSTANTS_VALUES = new Object[]{null, -1, 0, 1, 2, 3, 4, 5, 0L, 1L, 0.0F, 1.0F, 2.0F, 0.0D, 1.0D};
   private static final String[] CONSTANTS_TYPES = new String[]{null, "I", "I", "I", "I", "I", "I", "I", "J", "J", "F", "F", "F", "D", "D", "I", "I"};
   private static final String[] BOXING_TYPES = new String[]{null, "java/lang/Boolean", "java/lang/Character", "java/lang/Byte", "java/lang/Short", "java/lang/Integer", "java/lang/Float", "java/lang/Long", "java/lang/Double", null, null, null};
   private static final String[] UNBOXING_METHODS = new String[]{null, "booleanValue", "charValue", "byteValue", "shortValue", "intValue", "floatValue", "longValue", "doubleValue", null, null, null};
   private static final Class<?>[] MERGEABLE_MIXIN_ANNOTATIONS = new Class[]{Overwrite.class, Intrinsic.class, Final.class, Debug.class};
   private static Pattern mergeableAnnotationPattern = getMergeableAnnotationPattern();
   private static final Logger logger = LogManager.getLogger("mixin");

   private Bytecode() {
   }

   public static MethodNode findMethod(ClassNode param0, String param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public static AbstractInsnNode findInsn(MethodNode var0, int var1) {
      ListIterator var2 = var0.instructions.iterator();

      while(var2.hasNext()) {
         AbstractInsnNode var3 = (AbstractInsnNode)var2.next();

         try {
            if (var3.getOpcode() == var1) {
               return var3;
            }
         } catch (SyntheticBridgeException var4) {
            throw b(var4);
         }
      }

      return null;
   }

   public static MethodInsnNode findSuperInit(MethodNode param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   public static void textify(ClassNode var0, OutputStream var1) {
      var0.accept(new TraceClassVisitor(new PrintWriter(var1)));
   }

   public static void textify(MethodNode var0, OutputStream var1) {
      TraceClassVisitor var2 = new TraceClassVisitor(new PrintWriter(var1));
      MethodVisitor var3 = var2.visitMethod(var0.access, var0.name, var0.desc, var0.signature, (String[])var0.exceptions.toArray(new String[0]));
      var0.accept(var3);
      var2.visitEnd();
   }

   public static void dumpClass(ClassNode var0) {
      ClassWriter var1 = new ClassWriter(3);
      var0.accept(var1);
      dumpClass(var1.toByteArray());
   }

   public static void dumpClass(byte[] var0) {
      ClassReader var1 = new ClassReader(var0);
      CheckClassAdapter.verify(var1, true, new PrintWriter(System.out));
   }

   public static void printMethodWithOpcodeIndices(MethodNode var0) {
      System.err.printf("%s%s\n", var0.name, var0.desc);
      int var1 = 0;
      ListIterator var2 = var0.instructions.iterator();

      try {
         while(var2.hasNext()) {
            System.err.printf("[%4d] %s\n", var1++, describeNode((AbstractInsnNode)var2.next()));
         }

      } catch (SyntheticBridgeException var3) {
         throw b(var3);
      }
   }

   public static void printMethod(MethodNode var0) {
      System.err.printf("%s%s\n", var0.name, var0.desc);
      ListIterator var1 = var0.instructions.iterator();

      try {
         while(var1.hasNext()) {
            System.err.print("  ");
            printNode((AbstractInsnNode)var1.next());
         }

      } catch (SyntheticBridgeException var2) {
         throw b(var2);
      }
   }

   public static void printNode(AbstractInsnNode var0) {
      System.err.printf("%s\n", describeNode(var0));
   }

   public static String describeNode(AbstractInsnNode var0) {
      try {
         if (var0 == null) {
            return String.format("   %-14s ", "null");
         }
      } catch (SyntheticBridgeException var3) {
         throw b(var3);
      }

      try {
         if (var0 instanceof LabelNode) {
            return String.format("[%s]", ((LabelNode)var0).getLabel());
         }
      } catch (SyntheticBridgeException var4) {
         throw b(var4);
      }

      String var1 = String.format("   %-14s ", var0.getClass().getSimpleName().replace("Node", ""));
      if (var0 instanceof JumpInsnNode) {
         var1 = var1 + String.format("[%s] [%s]", getOpcodeName(var0), ((JumpInsnNode)var0).label.getLabel());
      } else if (var0 instanceof VarInsnNode) {
         var1 = var1 + String.format("[%s] %d", getOpcodeName(var0), ((VarInsnNode)var0).var);
      } else if (var0 instanceof MethodInsnNode) {
         MethodInsnNode var2 = (MethodInsnNode)var0;
         var1 = var1 + String.format("[%s] %s %s %s", getOpcodeName(var0), var2.owner, var2.name, var2.desc);
      } else if (var0 instanceof FieldInsnNode) {
         FieldInsnNode var5 = (FieldInsnNode)var0;
         var1 = var1 + String.format("[%s] %s %s %s", getOpcodeName(var0), var5.owner, var5.name, var5.desc);
      } else if (var0 instanceof LineNumberNode) {
         LineNumberNode var6 = (LineNumberNode)var0;
         var1 = var1 + String.format("LINE=[%d] LABEL=[%s]", var6.line, var6.start.getLabel());
      } else if (var0 instanceof LdcInsnNode) {
         var1 = var1 + ((LdcInsnNode)var0).cst;
      } else if (var0 instanceof IntInsnNode) {
         var1 = var1 + ((IntInsnNode)var0).operand;
      } else if (var0 instanceof FrameNode) {
         var1 = var1 + String.format("[%s] ", getOpcodeName(((FrameNode)var0).type, "H_INVOKEINTERFACE", -1));
      } else {
         var1 = var1 + String.format("[%s] ", getOpcodeName(var0));
      }

      return var1;
   }

   public static String getOpcodeName(AbstractInsnNode var0) {
      String var10000;
      try {
         if (var0 != null) {
            var10000 = getOpcodeName(var0.getOpcode());
            return var10000;
         }
      } catch (SyntheticBridgeException var1) {
         throw b(var1);
      }

      var10000 = "";
      return var10000;
   }

   public static String getOpcodeName(int var0) {
      return getOpcodeName(var0, "UNINITIALIZED_THIS", 1);
   }

   private static String getOpcodeName(int param0, String param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public static boolean methodHasLineNumbers(MethodNode var0) {
      ListIterator var1 = var0.instructions.iterator();

      do {
         if (!var1.hasNext()) {
            return false;
         }
      } while(!(var1.next() instanceof LineNumberNode));

      return true;
   }

   public static boolean methodIsStatic(MethodNode var0) {
      boolean var10000;
      try {
         if ((var0.access & 8) == 8) {
            var10000 = true;
            return var10000;
         }
      } catch (SyntheticBridgeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public static boolean fieldIsStatic(FieldNode var0) {
      boolean var10000;
      try {
         if ((var0.access & 8) == 8) {
            var10000 = true;
            return var10000;
         }
      } catch (SyntheticBridgeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public static int getFirstNonArgLocalIndex(MethodNode var0) {
      Type[] var10000;
      boolean var10001;
      try {
         var10000 = Type.getArgumentTypes(var0.desc);
         if ((var0.access & 8) == 0) {
            var10001 = true;
            return getFirstNonArgLocalIndex(var10000, var10001);
         }
      } catch (SyntheticBridgeException var1) {
         throw b(var1);
      }

      var10001 = false;
      return getFirstNonArgLocalIndex(var10000, var10001);
   }

   public static int getFirstNonArgLocalIndex(Type[] var0, boolean var1) {
      int var10000;
      byte var10001;
      try {
         var10000 = getArgsSize(var0);
         if (var1) {
            var10001 = 1;
            return var10000 + var10001;
         }
      } catch (SyntheticBridgeException var2) {
         throw b(var2);
      }

      var10001 = 0;
      return var10000 + var10001;
   }

   public static int getArgsSize(Type[] var0) {
      int var1 = 0;
      Type[] var2 = var0;
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Type var5 = var2[var4];
         var1 += var5.getSize();
      }

      return var1;
   }

   public static void loadArgs(Type[] var0, InsnList var1, int var2) {
      loadArgs(var0, var1, var2, -1);
   }

   public static void loadArgs(Type[] var0, InsnList var1, int var2, int var3) {
      loadArgs(var0, var1, var2, var3, (Type[])null);
   }

   public static void loadArgs(Type[] param0, InsnList param1, int param2, int param3, Type[] param4) {
      // $FF: Couldn't be decompiled
   }

   public static Map<LabelNode, LabelNode> cloneLabels(InsnList var0) {
      HashMap var1 = new HashMap();
      ListIterator var2 = var0.iterator();

      while(var2.hasNext()) {
         AbstractInsnNode var3 = (AbstractInsnNode)var2.next();

         try {
            if (var3 instanceof LabelNode) {
               var1.put((LabelNode)var3, new LabelNode(((LabelNode)var3).getLabel()));
            }
         } catch (SyntheticBridgeException var4) {
            throw b(var4);
         }
      }

      return var1;
   }

   public static String generateDescriptor(Object var0, Object... var1) {
      StringBuilder var2 = (new StringBuilder()).append('(');
      Object[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object var6 = var3[var5];
         var2.append(toDescriptor(var6));
      }

      StringBuilder var10000;
      String var10001;
      try {
         var10000 = var2.append(')');
         if (var0 != null) {
            var10001 = toDescriptor(var0);
            return var10000.append(var10001).toString();
         }
      } catch (SyntheticBridgeException var7) {
         throw b(var7);
      }

      var10001 = "V";
      return var10000.append(var10001).toString();
   }

   private static String toDescriptor(Object var0) {
      try {
         if (var0 instanceof String) {
            return (String)var0;
         }
      } catch (SyntheticBridgeException var2) {
         throw b(var2);
      }

      try {
         if (var0 instanceof Type) {
            return var0.toString();
         }
      } catch (SyntheticBridgeException var4) {
         throw b(var4);
      }

      try {
         if (var0 instanceof Class) {
            return Type.getDescriptor((Class)var0);
         }
      } catch (SyntheticBridgeException var1) {
         throw b(var1);
      }

      String var10000;
      try {
         if (var0 == null) {
            var10000 = "";
            return var10000;
         }
      } catch (SyntheticBridgeException var3) {
         throw b(var3);
      }

      var10000 = var0.toString();
      return var10000;
   }

   public static String getDescriptor(Type[] var0) {
      return "(" + Joiner.on("").join(var0) + ")";
   }

   public static String getDescriptor(Type[] var0, Type var1) {
      return getDescriptor(var0) + var1.toString();
   }

   public static String changeDescriptorReturnType(String var0, String var1) {
      try {
         if (var0 == null) {
            return null;
         }
      } catch (SyntheticBridgeException var2) {
         throw b(var2);
      }

      try {
         if (var1 == null) {
            return var0;
         }
      } catch (SyntheticBridgeException var3) {
         throw b(var3);
      }

      return var0.substring(0, var0.lastIndexOf(41) + 1) + var1;
   }

   public static String getSimpleName(Class<? extends Annotation> var0) {
      return var0.getSimpleName();
   }

   public static String getSimpleName(AnnotationNode var0) {
      return getSimpleName(var0.desc);
   }

   public static String getSimpleName(String var0) {
      int var1 = Math.max(var0.lastIndexOf(47), 0);
      return var0.substring(var1 + 1).replace(";", "");
   }

   public static boolean isConstant(AbstractInsnNode var0) {
      try {
         if (var0 == null) {
            return false;
         }
      } catch (SyntheticBridgeException var1) {
         throw b(var1);
      }

      return Ints.contains(CONSTANTS_ALL, var0.getOpcode());
   }

   public static Object getConstant(AbstractInsnNode param0) {
      // $FF: Couldn't be decompiled
   }

   public static Type getConstantType(AbstractInsnNode var0) {
      try {
         if (var0 == null) {
            return null;
         }
      } catch (SyntheticBridgeException var5) {
         throw b(var5);
      }

      if (var0 instanceof LdcInsnNode) {
         Object var10 = ((LdcInsnNode)var0).cst;

         try {
            if (var10 instanceof Integer) {
               return Type.getType("I");
            }
         } catch (SyntheticBridgeException var4) {
            throw b(var4);
         }

         try {
            if (var10 instanceof Float) {
               return Type.getType("F");
            }
         } catch (SyntheticBridgeException var8) {
            throw b(var8);
         }

         try {
            if (var10 instanceof Long) {
               return Type.getType("J");
            }
         } catch (SyntheticBridgeException var3) {
            throw b(var3);
         }

         try {
            if (var10 instanceof Double) {
               return Type.getType("D");
            }
         } catch (SyntheticBridgeException var7) {
            throw b(var7);
         }

         try {
            if (var10 instanceof String) {
               return Type.getType("Ljava/lang/String;");
            }
         } catch (SyntheticBridgeException var2) {
            throw b(var2);
         }

         try {
            if (var10 instanceof Type) {
               return Type.getType("Ljava/lang/Class;");
            }
         } catch (SyntheticBridgeException var6) {
            throw b(var6);
         }

         throw new IllegalArgumentException("LdcInsnNode with invalid payload type " + var10.getClass() + " in getConstant");
      } else {
         int var1 = Ints.indexOf(CONSTANTS_ALL, var0.getOpcode());

         Type var10000;
         try {
            if (var1 < 0) {
               var10000 = null;
               return var10000;
            }
         } catch (SyntheticBridgeException var9) {
            throw b(var9);
         }

         var10000 = Type.getType(CONSTANTS_TYPES[var1]);
         return var10000;
      }
   }

   public static boolean hasFlag(ClassNode var0, int var1) {
      boolean var10000;
      try {
         if ((var0.access & var1) == var1) {
            var10000 = true;
            return var10000;
         }
      } catch (SyntheticBridgeException var2) {
         throw b(var2);
      }

      var10000 = false;
      return var10000;
   }

   public static boolean hasFlag(MethodNode var0, int var1) {
      boolean var10000;
      try {
         if ((var0.access & var1) == var1) {
            var10000 = true;
            return var10000;
         }
      } catch (SyntheticBridgeException var2) {
         throw b(var2);
      }

      var10000 = false;
      return var10000;
   }

   public static boolean hasFlag(FieldNode var0, int var1) {
      boolean var10000;
      try {
         if ((var0.access & var1) == var1) {
            var10000 = true;
            return var10000;
         }
      } catch (SyntheticBridgeException var2) {
         throw b(var2);
      }

      var10000 = false;
      return var10000;
   }

   public static boolean compareFlags(MethodNode var0, MethodNode var1, int var2) {
      boolean var10000;
      try {
         if (hasFlag(var0, var2) == hasFlag(var1, var2)) {
            var10000 = true;
            return var10000;
         }
      } catch (SyntheticBridgeException var3) {
         throw b(var3);
      }

      var10000 = false;
      return var10000;
   }

   public static boolean compareFlags(FieldNode var0, FieldNode var1, int var2) {
      boolean var10000;
      try {
         if (hasFlag(var0, var2) == hasFlag(var1, var2)) {
            var10000 = true;
            return var10000;
         }
      } catch (SyntheticBridgeException var3) {
         throw b(var3);
      }

      var10000 = false;
      return var10000;
   }

   public static Bytecode$Visibility getVisibility(MethodNode var0) {
      return getVisibility(var0.access & 7);
   }

   public static Bytecode$Visibility getVisibility(FieldNode var0) {
      return getVisibility(var0.access & 7);
   }

   private static Bytecode$Visibility getVisibility(int var0) {
      try {
         if ((var0 & 4) != 0) {
            return Bytecode$Visibility.PROTECTED;
         }
      } catch (SyntheticBridgeException var3) {
         throw b(var3);
      }

      try {
         if ((var0 & 2) != 0) {
            return Bytecode$Visibility.PRIVATE;
         }
      } catch (SyntheticBridgeException var1) {
         throw b(var1);
      }

      try {
         if ((var0 & 1) != 0) {
            return Bytecode$Visibility.PUBLIC;
         }
      } catch (SyntheticBridgeException var2) {
         throw b(var2);
      }

      return Bytecode$Visibility.PACKAGE;
   }

   public static void setVisibility(MethodNode var0, Bytecode$Visibility var1) {
      var0.access = setVisibility(var0.access, var1.access);
   }

   public static void setVisibility(FieldNode var0, Bytecode$Visibility var1) {
      var0.access = setVisibility(var0.access, var1.access);
   }

   public static void setVisibility(MethodNode var0, int var1) {
      var0.access = setVisibility(var0.access, var1);
   }

   public static void setVisibility(FieldNode var0, int var1) {
      var0.access = setVisibility(var0.access, var1);
   }

   private static int setVisibility(int var0, int var1) {
      return var0 & -8 | var1 & 7;
   }

   public static int getMaxLineNumber(ClassNode var0, int var1, int var2) {
      int var3 = 0;
      Iterator var4 = var0.methods.iterator();

      while(var4.hasNext()) {
         MethodNode var5 = (MethodNode)var4.next();
         ListIterator var6 = var5.instructions.iterator();

         while(var6.hasNext()) {
            AbstractInsnNode var7 = (AbstractInsnNode)var6.next();
            if (var7 instanceof LineNumberNode) {
               var3 = Math.max(var3, ((LineNumberNode)var7).line);
            }
         }
      }

      return Math.max(var1, var3 + var2);
   }

   public static String getBoxingType(Type var0) {
      String var10000;
      try {
         if (var0 == null) {
            var10000 = null;
            return var10000;
         }
      } catch (SyntheticBridgeException var1) {
         throw b(var1);
      }

      var10000 = BOXING_TYPES[var0.getSort()];
      return var10000;
   }

   public static String getUnboxingMethod(Type var0) {
      String var10000;
      try {
         if (var0 == null) {
            var10000 = null;
            return var10000;
         }
      } catch (SyntheticBridgeException var1) {
         throw b(var1);
      }

      var10000 = UNBOXING_METHODS[var0.getSort()];
      return var10000;
   }

   public static void mergeAnnotations(ClassNode var0, ClassNode var1) {
      var1.visibleAnnotations = mergeAnnotations(var0.visibleAnnotations, var1.visibleAnnotations, "class", var0.name);
      var1.invisibleAnnotations = mergeAnnotations(var0.invisibleAnnotations, var1.invisibleAnnotations, "class", var0.name);
   }

   public static void mergeAnnotations(MethodNode var0, MethodNode var1) {
      var1.visibleAnnotations = mergeAnnotations(var0.visibleAnnotations, var1.visibleAnnotations, "method", var0.name);
      var1.invisibleAnnotations = mergeAnnotations(var0.invisibleAnnotations, var1.invisibleAnnotations, "method", var0.name);
   }

   public static void mergeAnnotations(FieldNode var0, FieldNode var1) {
      var1.visibleAnnotations = mergeAnnotations(var0.visibleAnnotations, var1.visibleAnnotations, "field", var0.name);
      var1.invisibleAnnotations = mergeAnnotations(var0.invisibleAnnotations, var1.invisibleAnnotations, "field", var0.name);
   }

   private static List<AnnotationNode> mergeAnnotations(List<AnnotationNode> var0, List<AnnotationNode> var1, String var2, String var3) {
      try {
         if (var0 == null) {
            return (List)var1;
         } else {
            if (var1 == null) {
               var1 = new ArrayList();
            }

            Iterator var4 = var0.iterator();

            while(true) {
               AnnotationNode var5;
               while(true) {
                  if (!var4.hasNext()) {
                     return (List)var1;
                  }

                  var5 = (AnnotationNode)var4.next();

                  try {
                     if (!isMergeableAnnotation(var5)) {
                        continue;
                     }
                     break;
                  } catch (Exception var7) {
                     throw b(var7);
                  }
               }

               Iterator var6 = ((List)var1).iterator();

               while(true) {
                  if (var6.hasNext()) {
                     if (!((AnnotationNode)var6.next()).desc.equals(var5.desc)) {
                        continue;
                     }

                     var6.remove();
                  }

                  ((List)var1).add(var5);
                  break;
               }
            }
         }
      } catch (Exception var8) {
         logger.warn("Exception encountered whilst merging annotations for {} {}", new Object[]{var2, var3});
         return (List)var1;
      }
   }

   private static boolean isMergeableAnnotation(AnnotationNode var0) {
      try {
         return var0.desc.startsWith("L" + Constants.MIXIN_PACKAGE_REF) ? mergeableAnnotationPattern.matcher(var0.desc).matches() : true;
      } catch (SyntheticBridgeException var1) {
         throw b(var1);
      }
   }

   private static Pattern getMergeableAnnotationPattern() {
      // $FF: Couldn't be decompiled
   }

   public static void compareBridgeMethods(MethodNode param0, MethodNode param1) {
      // $FF: Couldn't be decompiled
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

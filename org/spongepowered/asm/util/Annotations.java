package org.spongepowered.asm.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;

public final class Annotations {
   private Annotations() {
   }

   public static void setVisible(FieldNode var0, Class<? extends Annotation> var1, Object... var2) {
      AnnotationNode var3 = createNode(Type.getDescriptor(var1), var2);
      var0.visibleAnnotations = add(var0.visibleAnnotations, var3);
   }

   public static void setInvisible(FieldNode var0, Class<? extends Annotation> var1, Object... var2) {
      AnnotationNode var3 = createNode(Type.getDescriptor(var1), var2);
      var0.invisibleAnnotations = add(var0.invisibleAnnotations, var3);
   }

   public static void setVisible(MethodNode var0, Class<? extends Annotation> var1, Object... var2) {
      AnnotationNode var3 = createNode(Type.getDescriptor(var1), var2);
      var0.visibleAnnotations = add(var0.visibleAnnotations, var3);
   }

   public static void setInvisible(MethodNode var0, Class<? extends Annotation> var1, Object... var2) {
      AnnotationNode var3 = createNode(Type.getDescriptor(var1), var2);
      var0.invisibleAnnotations = add(var0.invisibleAnnotations, var3);
   }

   private static AnnotationNode createNode(String param0, Object... param1) {
      // $FF: Couldn't be decompiled
   }

   private static List<AnnotationNode> add(List<AnnotationNode> var0, AnnotationNode var1) {
      if (var0 == null) {
         var0 = new ArrayList(1);
      } else {
         ((List)var0).remove(get((List)var0, var1.desc));
      }

      ((List)var0).add(var1);
      return (List)var0;
   }

   public static AnnotationNode getVisible(FieldNode var0, Class<? extends Annotation> var1) {
      return get(var0.visibleAnnotations, Type.getDescriptor(var1));
   }

   public static AnnotationNode getInvisible(FieldNode var0, Class<? extends Annotation> var1) {
      return get(var0.invisibleAnnotations, Type.getDescriptor(var1));
   }

   public static AnnotationNode getVisible(MethodNode var0, Class<? extends Annotation> var1) {
      return get(var0.visibleAnnotations, Type.getDescriptor(var1));
   }

   public static AnnotationNode getInvisible(MethodNode var0, Class<? extends Annotation> var1) {
      return get(var0.invisibleAnnotations, Type.getDescriptor(var1));
   }

   public static AnnotationNode getSingleVisible(MethodNode var0, Class<? extends Annotation>... var1) {
      return getSingle(var0.visibleAnnotations, var1);
   }

   public static AnnotationNode getSingleInvisible(MethodNode var0, Class<? extends Annotation>... var1) {
      return getSingle(var0.invisibleAnnotations, var1);
   }

   public static AnnotationNode getVisible(ClassNode var0, Class<? extends Annotation> var1) {
      return get(var0.visibleAnnotations, Type.getDescriptor(var1));
   }

   public static AnnotationNode getInvisible(ClassNode var0, Class<? extends Annotation> var1) {
      return get(var0.invisibleAnnotations, Type.getDescriptor(var1));
   }

   public static AnnotationNode getVisibleParameter(MethodNode var0, Class<? extends Annotation> var1, int var2) {
      return getParameter(var0.visibleParameterAnnotations, Type.getDescriptor(var1), var2);
   }

   public static AnnotationNode getInvisibleParameter(MethodNode var0, Class<? extends Annotation> var1, int var2) {
      return getParameter(var0.invisibleParameterAnnotations, Type.getDescriptor(var1), var2);
   }

   public static AnnotationNode getParameter(List<AnnotationNode>[] param0, String param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public static AnnotationNode get(List<AnnotationNode> var0, String var1) {
      try {
         if (var0 == null) {
            return null;
         }
      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }

      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         AnnotationNode var3 = (AnnotationNode)var2.next();

         try {
            if (var1.equals(var3.desc)) {
               return var3;
            }
         } catch (IllegalArgumentException var4) {
            throw b(var4);
         }
      }

      return null;
   }

   private static AnnotationNode getSingle(List<AnnotationNode> var0, Class<? extends Annotation>[] var1) {
      ArrayList var2 = new ArrayList();
      Class[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Class var6 = var3[var5];
         AnnotationNode var7 = get(var0, Type.getDescriptor(var6));

         try {
            if (var7 != null) {
               var2.add(var7);
            }
         } catch (IllegalArgumentException var10) {
            throw b(var10);
         }
      }

      int var11 = var2.size();

      try {
         if (var11 > 1) {
            throw new IllegalArgumentException("Conflicting annotations found: " + Lists.transform(var2, new Annotations$1()));
         }
      } catch (IllegalArgumentException var8) {
         throw b(var8);
      }

      AnnotationNode var10000;
      try {
         if (var11 == 0) {
            var10000 = null;
            return var10000;
         }
      } catch (IllegalArgumentException var9) {
         throw b(var9);
      }

      var10000 = (AnnotationNode)var2.get(0);
      return var10000;
   }

   public static <T> T getValue(AnnotationNode var0) {
      return getValue(var0, "value");
   }

   public static <T> T getValue(AnnotationNode var0, String var1, T var2) {
      Object var3 = getValue(var0, var1);

      Object var10000;
      try {
         if (var3 != null) {
            var10000 = var3;
            return var10000;
         }
      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }

      var10000 = var2;
      return var10000;
   }

   public static <T> T getValue(AnnotationNode var0, String var1, Class<?> var2) {
      Preconditions.checkNotNull(var2, "annotationClass cannot be null");
      Object var3 = getValue(var0, var1);
      if (var3 == null) {
         try {
            var3 = var2.getDeclaredMethod(var1).getDefaultValue();
         } catch (NoSuchMethodException var5) {
         }
      }

      return var3;
   }

   public static <T> T getValue(AnnotationNode param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   public static <T extends Enum<T>> T getValue(AnnotationNode var0, String var1, Class<T> var2, T var3) {
      String[] var4 = (String[])getValue(var0, var1);

      try {
         if (var4 == null) {
            return var3;
         }
      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }

      return toEnumValue(var2, var4);
   }

   public static <T> List<T> getValue(AnnotationNode var0, String var1, boolean var2) {
      Object var3 = getValue(var0, var1);

      try {
         if (var3 instanceof List) {
            return (List)var3;
         }
      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }

      if (var3 != null) {
         ArrayList var4 = new ArrayList();
         var4.add(var3);
         return var4;
      } else {
         return Collections.emptyList();
      }
   }

   public static <T extends Enum<T>> List<T> getValue(AnnotationNode var0, String var1, boolean var2, Class<T> var3) {
      Object var4 = getValue(var0, var1);
      if (!(var4 instanceof List)) {
         if (var4 instanceof String[]) {
            ArrayList var7 = new ArrayList();
            var7.add(toEnumValue(var3, (String[])((String[])var4)));
            return var7;
         } else {
            return Collections.emptyList();
         }
      } else {
         ListIterator var5 = ((List)var4).listIterator();

         try {
            while(var5.hasNext()) {
               var5.set(toEnumValue(var3, (String[])((String[])var5.next())));
            }
         } catch (IllegalArgumentException var6) {
            throw b(var6);
         }

         return (List)var4;
      }
   }

   private static <T extends Enum<T>> T toEnumValue(Class<T> var0, String[] var1) {
      try {
         if (!var0.getName().equals(Type.getType(var1[0]).getClassName())) {
            throw new IllegalArgumentException("The supplied enum class does not match the stored enum value");
         }
      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }

      return Enum.valueOf(var0, var1[1]);
   }

   private static IllegalArgumentException b(IllegalArgumentException var0) {
      return var0;
   }
}

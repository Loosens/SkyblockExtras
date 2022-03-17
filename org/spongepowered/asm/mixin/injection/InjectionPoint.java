package org.spongepowered.asm.mixin.injection;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.modify.AfterStoreLocal;
import org.spongepowered.asm.mixin.injection.modify.BeforeLoadLocal;
import org.spongepowered.asm.mixin.injection.points.AfterInvoke;
import org.spongepowered.asm.mixin.injection.points.BeforeConstant;
import org.spongepowered.asm.mixin.injection.points.BeforeFieldAccess;
import org.spongepowered.asm.mixin.injection.points.BeforeFinalReturn;
import org.spongepowered.asm.mixin.injection.points.BeforeInvoke;
import org.spongepowered.asm.mixin.injection.points.BeforeNew;
import org.spongepowered.asm.mixin.injection.points.BeforeReturn;
import org.spongepowered.asm.mixin.injection.points.BeforeStringInvoke;
import org.spongepowered.asm.mixin.injection.points.JumpInsnPoint;
import org.spongepowered.asm.mixin.injection.points.MethodHead;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.util.Annotations;

public abstract class InjectionPoint {
   public static final int DEFAULT_ALLOWED_SHIFT_BY = 0;
   public static final int MAX_ALLOWED_SHIFT_BY = 5;
   private static Map<String, Class<? extends InjectionPoint>> types = new HashMap();
   private final String slice;
   private final InjectionPoint$Selector selector;
   private final String id;

   protected InjectionPoint() {
      this("", InjectionPoint$Selector.DEFAULT, (String)null);
   }

   protected InjectionPoint(InjectionPointData var1) {
      this(var1.getSlice(), var1.getSelector(), var1.getId());
   }

   public InjectionPoint(String var1, InjectionPoint$Selector var2, String var3) {
      this.slice = var1;
      this.selector = var2;
      this.id = var3;
   }

   public String getSlice() {
      return this.slice;
   }

   public InjectionPoint$Selector getSelector() {
      return this.selector;
   }

   public String getId() {
      return this.id;
   }

   public boolean checkPriority(int var1, int var2) {
      boolean var10000;
      try {
         if (var1 < var2) {
            var10000 = true;
            return var10000;
         }
      } catch (InvalidInjectionException var3) {
         throw b(var3);
      }

      var10000 = false;
      return var10000;
   }

   public abstract boolean find(String var1, InsnList var2, Collection<AbstractInsnNode> var3);

   public String toString() {
      return String.format("@At(\"%s\")", this.getAtCode());
   }

   protected static AbstractInsnNode nextNode(InsnList param0, AbstractInsnNode param1) {
      // $FF: Couldn't be decompiled
   }

   public static InjectionPoint and(InjectionPoint... var0) {
      return new InjectionPoint$Intersection(var0);
   }

   public static InjectionPoint or(InjectionPoint... var0) {
      return new InjectionPoint$Union(var0);
   }

   public static InjectionPoint after(InjectionPoint var0) {
      return new InjectionPoint$Shift(var0, 1);
   }

   public static InjectionPoint before(InjectionPoint var0) {
      return new InjectionPoint$Shift(var0, -1);
   }

   public static InjectionPoint shift(InjectionPoint var0, int var1) {
      return new InjectionPoint$Shift(var0, var1);
   }

   public static List<InjectionPoint> parse(IInjectionPointContext var0, List<AnnotationNode> var1) {
      return parse(var0.getContext(), var0.getMethod(), var0.getAnnotation(), var1);
   }

   public static List<InjectionPoint> parse(IMixinContext var0, MethodNode var1, AnnotationNode var2, List<AnnotationNode> var3) {
      Builder var4 = ImmutableList.builder();
      Iterator var5 = var3.iterator();

      while(var5.hasNext()) {
         AnnotationNode var6 = (AnnotationNode)var5.next();
         InjectionPoint var7 = parse(var0, var1, var2, var6);

         try {
            if (var7 != null) {
               var4.add(var7);
            }
         } catch (InvalidInjectionException var8) {
            throw b(var8);
         }
      }

      return var4.build();
   }

   public static InjectionPoint parse(IInjectionPointContext var0, At var1) {
      return parse(var0.getContext(), var0.getMethod(), var0.getAnnotation(), var1.value(), var1.shift(), var1.by(), Arrays.asList(var1.args()), var1.target(), var1.slice(), var1.ordinal(), var1.opcode(), var1.id());
   }

   public static InjectionPoint parse(IMixinContext var0, MethodNode var1, AnnotationNode var2, At var3) {
      return parse(var0, var1, var2, var3.value(), var3.shift(), var3.by(), Arrays.asList(var3.args()), var3.target(), var3.slice(), var3.ordinal(), var3.opcode(), var3.id());
   }

   public static InjectionPoint parse(IInjectionPointContext var0, AnnotationNode var1) {
      return parse(var0.getContext(), var0.getMethod(), var0.getAnnotation(), var1);
   }

   public static InjectionPoint parse(IMixinContext var0, MethodNode var1, AnnotationNode var2, AnnotationNode var3) {
      String var4 = (String)Annotations.getValue(var3, "value");
      Object var5 = (List)Annotations.getValue(var3, "args");
      String var6 = (String)Annotations.getValue(var3, "target", (Object)"");
      String var7 = (String)Annotations.getValue(var3, "slice", (Object)"");
      At$Shift var8 = (At$Shift)Annotations.getValue(var3, "shift", At$Shift.class, At$Shift.NONE);
      int var9 = (Integer)Annotations.getValue(var3, "by", (int)0);
      int var10 = (Integer)Annotations.getValue(var3, "ordinal", (int)-1);
      int var11 = (Integer)Annotations.getValue(var3, "opcode", (int)0);
      String var12 = (String)Annotations.getValue(var3, "id");
      if (var5 == null) {
         var5 = ImmutableList.of();
      }

      return parse(var0, var1, var2, var4, var8, var9, (List)var5, var6, var7, var10, var11, var12);
   }

   public static InjectionPoint parse(IMixinContext var0, MethodNode var1, AnnotationNode var2, String var3, At$Shift var4, int var5, List<String> var6, String var7, String var8, int var9, int var10, String var11) {
      InjectionPointData var12 = new InjectionPointData(var0, var1, var2, var3, var6, var7, var8, var9, var10, var11);
      Class var13 = findClass(var0, var12);
      InjectionPoint var14 = create(var0, var12, var13);
      return shift(var0, var1, var2, var14, var4, var5);
   }

   private static Class<? extends InjectionPoint> findClass(IMixinContext var0, InjectionPointData var1) {
      String var2 = var1.getType();
      Class var3 = (Class)types.get(var2);

      try {
         if (var3 != null) {
            return var3;
         }

         if (!var2.matches("^([A-Za-z_][A-Za-z0-9_]*\\.)+[A-Za-z_][A-Za-z0-9_]*$")) {
            throw new InvalidInjectionException(var0, var1 + " is not a valid injection point specifier");
         }
      } catch (Exception var6) {
         throw b(var6);
      }

      try {
         var3 = Class.forName(var2);
         types.put(var2, var3);
      } catch (Exception var5) {
         throw new InvalidInjectionException(var0, var1 + " could not be loaded or is not a valid InjectionPoint", var5);
      }

      return var3;
   }

   private static InjectionPoint create(IMixinContext var0, InjectionPointData var1, Class<? extends InjectionPoint> var2) {
      Constructor var3 = null;

      try {
         var3 = var2.getDeclaredConstructor(InjectionPointData.class);
         var3.setAccessible(true);
      } catch (NoSuchMethodException var7) {
         throw new InvalidInjectionException(var0, var2.getName() + " must contain a constructor which accepts an InjectionPointData", var7);
      }

      InjectionPoint var4 = null;

      try {
         var4 = (InjectionPoint)var3.newInstance(var1);
         return var4;
      } catch (Exception var6) {
         throw new InvalidInjectionException(var0, "Error whilst instancing injection point " + var2.getName() + " for " + var1.getAt(), var6);
      }
   }

   private static InjectionPoint shift(IMixinContext param0, MethodNode param1, AnnotationNode param2, InjectionPoint param3, At$Shift param4, int param5) {
      // $FF: Couldn't be decompiled
   }

   private static void validateByValue(IMixinContext param0, MethodNode param1, AnnotationNode param2, InjectionPoint param3, int param4) {
      // $FF: Couldn't be decompiled
   }

   protected String getAtCode() {
      InjectionPoint$AtCode var1 = (InjectionPoint$AtCode)this.getClass().getAnnotation(InjectionPoint$AtCode.class);

      String var10000;
      try {
         if (var1 == null) {
            var10000 = this.getClass().getName();
            return var10000;
         }
      } catch (InvalidInjectionException var2) {
         throw b(var2);
      }

      var10000 = var1.value();
      return var10000;
   }

   public static void register(Class<? extends InjectionPoint> param0) {
      // $FF: Couldn't be decompiled
   }

   static {
      register(BeforeFieldAccess.class);
      register(BeforeInvoke.class);
      register(BeforeNew.class);
      register(BeforeReturn.class);
      register(BeforeStringInvoke.class);
      register(JumpInsnPoint.class);
      register(MethodHead.class);
      register(AfterInvoke.class);
      register(BeforeLoadLocal.class);
      register(AfterStoreLocal.class);
      register(BeforeFinalReturn.class);
      register(BeforeConstant.class);
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

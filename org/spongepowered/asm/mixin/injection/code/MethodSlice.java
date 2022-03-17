package org.spongepowered.asm.mixin.injection.code;

import com.google.common.base.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.throwables.InvalidSliceException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

public final class MethodSlice {
   private static final Logger logger = LogManager.getLogger("mixin");
   private final ISliceContext owner;
   private final String id;
   private final InjectionPoint from;
   private final InjectionPoint to;
   private final String name;

   private MethodSlice(ISliceContext var1, String var2, InjectionPoint var3, InjectionPoint var4) {
      if (var3 == null) {
         try {
            if (var4 == null) {
               throw new InvalidSliceException(var1, String.format("%s is redundant. No 'from' or 'to' value specified", this));
            }
         } catch (InvalidSliceException var5) {
            throw b(var5);
         }
      }

      this.owner = var1;
      this.id = Strings.nullToEmpty(var2);
      this.from = var3;
      this.to = var4;
      this.name = getSliceName(var2);
   }

   public String getId() {
      return this.id;
   }

   public ReadOnlyInsnList getSlice(MethodNode param1) {
      // $FF: Couldn't be decompiled
   }

   private int find(MethodNode param1, InjectionPoint param2, int param3, int param4, String param5) {
      // $FF: Couldn't be decompiled
   }

   public String toString() {
      return this.describe();
   }

   private String describe() {
      return this.describe(this.name);
   }

   private String describe(String var1) {
      return describeSlice(var1, this.owner);
   }

   private static String describeSlice(String var0, ISliceContext var1) {
      String var2 = Bytecode.getSimpleName(var1.getAnnotation());
      MethodNode var3 = var1.getMethod();
      return String.format("%s->%s(%s)::%s%s", var1.getContext(), var2, var0, var3.name, var3.desc);
   }

   private static String getSliceName(String var0) {
      return String.format("@Slice[%s]", Strings.nullToEmpty(var0));
   }

   public static MethodSlice parse(ISliceContext var0, Slice var1) {
      String var2 = var1.id();
      At var3 = var1.from();
      At var4 = var1.to();

      InjectionPoint var10000;
      label31: {
         try {
            if (var3 != null) {
               var10000 = InjectionPoint.parse(var0, (At)var3);
               break label31;
            }
         } catch (InvalidSliceException var8) {
            throw b(var8);
         }

         var10000 = null;
      }

      InjectionPoint var5 = var10000;

      label23: {
         try {
            if (var4 != null) {
               var10000 = InjectionPoint.parse(var0, (At)var4);
               break label23;
            }
         } catch (InvalidSliceException var7) {
            throw b(var7);
         }

         var10000 = null;
      }

      InjectionPoint var6 = var10000;
      return new MethodSlice(var0, var2, var5, var6);
   }

   public static MethodSlice parse(ISliceContext var0, AnnotationNode var1) {
      String var2 = (String)Annotations.getValue(var1, "id");
      AnnotationNode var3 = (AnnotationNode)Annotations.getValue(var1, "from");
      AnnotationNode var4 = (AnnotationNode)Annotations.getValue(var1, "to");

      InjectionPoint var10000;
      label31: {
         try {
            if (var3 != null) {
               var10000 = InjectionPoint.parse(var0, (AnnotationNode)var3);
               break label31;
            }
         } catch (InvalidSliceException var8) {
            throw b(var8);
         }

         var10000 = null;
      }

      InjectionPoint var5 = var10000;

      label23: {
         try {
            if (var4 != null) {
               var10000 = InjectionPoint.parse(var0, (AnnotationNode)var4);
               break label23;
            }
         } catch (InvalidSliceException var7) {
            throw b(var7);
         }

         var10000 = null;
      }

      InjectionPoint var6 = var10000;
      return new MethodSlice(var0, var2, var5, var6);
   }

   private static InvalidSliceException b(InvalidSliceException var0) {
      return var0;
   }
}

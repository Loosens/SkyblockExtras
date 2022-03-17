package org.spongepowered.asm.mixin.injection.points;

import com.google.common.primitives.Ints;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.mixin.injection.Constant$Condition;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.InjectionPoint$AtCode;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

@InjectionPoint$AtCode("CONSTANT")
public class BeforeConstant extends InjectionPoint {
   private static final Logger logger = LogManager.getLogger("mixin");
   private final int ordinal;
   private final boolean nullValue;
   private final Integer intValue;
   private final Float floatValue;
   private final Long longValue;
   private final Double doubleValue;
   private final String stringValue;
   private final Type typeValue;
   private final int[] expandOpcodes;
   private final boolean expand;
   private final String matchByType;
   private final boolean log;

   public BeforeConstant(IMixinContext param1, AnnotationNode param2, String param3) {
      // $FF: Couldn't be decompiled
   }

   public BeforeConstant(InjectionPointData param1) {
      // $FF: Couldn't be decompiled
   }

   private String validateDiscriminator(IMixinContext var1, String var2, Boolean var3, String var4) {
      int var5 = count(var3, this.intValue, this.floatValue, this.longValue, this.doubleValue, this.stringValue, this.typeValue);
      if (var5 == 1) {
         var2 = null;
      } else {
         try {
            if (var5 > 1) {
               throw new InvalidInjectionException(var1, "Conflicting constant discriminators specified " + var4 + " for " + var1);
            }
         } catch (InvalidInjectionException var6) {
            throw b(var6);
         }
      }

      return var2;
   }

   private int[] parseExpandOpcodes(List<Constant$Condition> var1) {
      HashSet var2 = new HashSet();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         Constant$Condition var4 = (Constant$Condition)var3.next();
         Constant$Condition var5 = var4.getEquivalentCondition();
         int[] var6 = var5.getOpcodes();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            int var9 = var6[var8];
            var2.add(var9);
         }
      }

      return Ints.toArray(var2);
   }

   public boolean find(String param1, InsnList param2, Collection<AbstractInsnNode> param3) {
      // $FF: Couldn't be decompiled
   }

   private boolean matchesConditionalInsn(int param1, AbstractInsnNode param2) {
      // $FF: Couldn't be decompiled
   }

   private boolean matchesConstantInsn(AbstractInsnNode param1) {
      // $FF: Couldn't be decompiled
   }

   protected void log(String var1, Object... var2) {
      try {
         if (this.log) {
            logger.info(var1, var2);
         }

      } catch (InvalidInjectionException var3) {
         throw b(var3);
      }
   }

   private static int count(Object... var0) {
      int var1 = 0;
      Object[] var2 = var0;
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object var5 = var2[var4];

         try {
            if (var5 != null) {
               ++var1;
            }
         } catch (InvalidInjectionException var6) {
            throw b(var6);
         }
      }

      return var1;
   }

   private static InvalidInjectionException b(InvalidInjectionException var0) {
      return var0;
   }
}

package org.spongepowered.asm.mixin.injection.invoke;

import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes$InjectionNode;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;

public abstract class InvokeInjector extends Injector {
   protected final String annotationType;

   public InvokeInjector(InjectionInfo var1, String var2) {
      super(var1);
      this.annotationType = var2;
   }

   protected void sanityCheck(Target var1, List<InjectionPoint> var2) {
      super.sanityCheck(var1, var2);
      this.checkTarget(var1);
   }

   protected void checkTarget(Target var1) {
      this.checkTargetModifiers(var1, true);
   }

   protected final void checkTargetModifiers(Target param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   protected void checkTargetForNode(Target param1, InjectionNodes$InjectionNode param2) {
      // $FF: Couldn't be decompiled
   }

   protected void inject(Target var1, InjectionNodes$InjectionNode var2) {
      try {
         if (!(var2.getCurrentTarget() instanceof MethodInsnNode)) {
            throw new InvalidInjectionException(this.info, this.annotationType + " annotation on is targetting a non-method insn in " + var1 + " in " + this);
         }
      } catch (InvalidInjectionException var3) {
         throw c(var3);
      }

      this.injectAtInvoke(var1, var2);
   }

   protected abstract void injectAtInvoke(Target var1, InjectionNodes$InjectionNode var2);

   protected AbstractInsnNode invokeHandlerWithArgs(Type[] var1, InsnList var2, int[] var3) {
      return this.invokeHandlerWithArgs(var1, var2, var3, 0, var1.length);
   }

   protected AbstractInsnNode invokeHandlerWithArgs(Type[] var1, InsnList var2, int[] var3, int var4, int var5) {
      try {
         if (!this.isStatic) {
            var2.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
         }
      } catch (InvalidInjectionException var6) {
         throw c(var6);
      }

      this.pushArgs(var1, var2, var3, var4, var5);
      return this.invokeHandler(var2);
   }

   protected int[] storeArgs(Target var1, Type[] var2, InsnList var3, int var4) {
      int[] var5 = var1.generateArgMap(var2, var4);
      this.storeArgs(var2, var3, var5, var4, var2.length);
      return var5;
   }

   protected void storeArgs(Type[] var1, InsnList var2, int[] var3, int var4, int var5) {
      int var6 = var5 - 1;

      try {
         while(var6 >= var4) {
            var2.add((AbstractInsnNode)(new VarInsnNode(var1[var6].getOpcode(54), var3[var6])));
            --var6;
         }

      } catch (InvalidInjectionException var7) {
         throw c(var7);
      }
   }

   protected void pushArgs(Type[] var1, InsnList var2, int[] var3, int var4, int var5) {
      int var6 = var4;

      try {
         while(var6 < var5) {
            var2.add((AbstractInsnNode)(new VarInsnNode(var1[var6].getOpcode(21), var3[var6])));
            ++var6;
         }

      } catch (InvalidInjectionException var7) {
         throw c(var7);
      }
   }

   private static InvalidInjectionException c(InvalidInjectionException var0) {
      return var0;
   }
}

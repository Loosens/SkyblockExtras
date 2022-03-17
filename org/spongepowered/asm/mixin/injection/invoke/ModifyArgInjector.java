package org.spongepowered.asm.mixin.injection.invoke;

import java.util.Arrays;
import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes$InjectionNode;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Bytecode;

public class ModifyArgInjector extends InvokeInjector {
   private final int index;
   private final boolean singleArgMode;

   public ModifyArgInjector(InjectionInfo var1, int var2) {
      super(var1, "@ModifyArg");
      this.index = var2;
      this.singleArgMode = this.methodArgs.length == 1;
   }

   protected void sanityCheck(Target param1, List<InjectionPoint> param2) {
      // $FF: Couldn't be decompiled
   }

   protected void checkTarget(Target param1) {
      // $FF: Couldn't be decompiled
   }

   protected void inject(Target var1, InjectionNodes$InjectionNode var2) {
      this.checkTargetForNode(var1, var2);
      super.inject(var1, var2);
   }

   protected void injectAtInvoke(Target var1, InjectionNodes$InjectionNode var2) {
      MethodInsnNode var3 = (MethodInsnNode)var2.getCurrentTarget();
      Type[] var4 = Type.getArgumentTypes(var3.desc);
      int var5 = this.findArgIndex(var1, var4);
      InsnList var6 = new InsnList();
      boolean var7 = false;
      int var8;
      if (this.singleArgMode) {
         var8 = this.injectSingleArgHandler(var1, var4, var5, var6);
      } else {
         var8 = this.injectMultiArgHandler(var1, var4, var5, var6);
      }

      var1.insns.insertBefore(var3, (InsnList)var6);
      var1.addToLocals(var8);
      var1.addToStack(2 - (var8 - 1));
   }

   private int injectSingleArgHandler(Target var1, Type[] var2, int var3, InsnList var4) {
      int[] var5 = this.storeArgs(var1, var2, var4, var3);
      this.invokeHandlerWithArgs(var2, var4, var5, var3, var3 + 1);
      this.pushArgs(var2, var4, var5, var3 + 1, var2.length);
      return var5[var5.length - 1] - var1.getMaxLocals() + var2[var2.length - 1].getSize();
   }

   private int injectMultiArgHandler(Target var1, Type[] var2, int var3, InsnList var4) {
      try {
         if (!Arrays.equals(var2, this.methodArgs)) {
            throw new InvalidInjectionException(this.info, "@ModifyArg method " + this + " targets a method with an invalid signature " + Bytecode.getDescriptor(var2) + ", expected " + Bytecode.getDescriptor(this.methodArgs));
         }
      } catch (InvalidInjectionException var6) {
         throw d(var6);
      }

      int[] var5 = this.storeArgs(var1, var2, var4, 0);
      this.pushArgs(var2, var4, var5, 0, var3);
      this.invokeHandlerWithArgs(var2, var4, var5, 0, var2.length);
      this.pushArgs(var2, var4, var5, var3 + 1, var2.length);
      return var5[var5.length - 1] - var1.getMaxLocals() + var2[var2.length - 1].getSize();
   }

   protected int findArgIndex(Target param1, Type[] param2) {
      // $FF: Couldn't be decompiled
   }

   private static InvalidInjectionException d(InvalidInjectionException var0) {
      return var0;
   }
}

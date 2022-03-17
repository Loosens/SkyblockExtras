package org.spongepowered.asm.mixin.injection.invoke;

import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes$InjectionNode;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Bytecode;

public class ModifyArgsInjector extends InvokeInjector {
   private final ArgsClassGenerator argsClassGenerator;

   public ModifyArgsInjector(InjectionInfo var1) {
      super(var1, "@ModifyArgs");
      this.argsClassGenerator = (ArgsClassGenerator)var1.getContext().getExtensions().getGenerator(ArgsClassGenerator.class);
   }

   protected void checkTarget(Target var1) {
      this.checkTargetModifiers(var1, false);
   }

   protected void inject(Target var1, InjectionNodes$InjectionNode var2) {
      this.checkTargetForNode(var1, var2);
      super.inject(var1, var2);
   }

   protected void injectAtInvoke(Target param1, InjectionNodes$InjectionNode param2) {
      // $FF: Couldn't be decompiled
   }

   private boolean verifyTarget(Target var1) {
      String var2 = String.format("(L%s;)V", ArgsClassGenerator.ARGS_REF);
      if (!this.methodNode.desc.equals(var2)) {
         String var3 = Bytecode.changeDescriptorReturnType(var1.method.desc, "V");
         String var4 = String.format("(L%s;%s", ArgsClassGenerator.ARGS_REF, var3.substring(1));

         try {
            if (this.methodNode.desc.equals(var4)) {
               return true;
            }
         } catch (InvalidInjectionException var5) {
            throw d(var5);
         }

         throw new InvalidInjectionException(this.info, "@ModifyArgs injector " + this + " has an invalid signature " + this.methodNode.desc + ", expected " + var2 + " or " + var4);
      } else {
         return false;
      }
   }

   private void packArgs(InsnList var1, String var2, MethodInsnNode var3) {
      String var4 = Bytecode.changeDescriptorReturnType(var3.desc, "L" + var2 + ";");

      try {
         var1.add((AbstractInsnNode)(new MethodInsnNode(184, var2, "of", var4, false)));
         var1.add((AbstractInsnNode)(new InsnNode(89)));
         if (!this.isStatic) {
            var1.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
            var1.add((AbstractInsnNode)(new InsnNode(95)));
         }

      } catch (InvalidInjectionException var5) {
         throw d(var5);
      }
   }

   private void unpackArgs(InsnList param1, String param2, Type[] param3) {
      // $FF: Couldn't be decompiled
   }

   private static InvalidInjectionException d(InvalidInjectionException var0) {
      return var0;
   }
}

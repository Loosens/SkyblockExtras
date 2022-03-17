package org.spongepowered.asm.mixin.injection.invoke;

import org.apache.logging.log4j.Level;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes$InjectionNode;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.SignaturePrinter;

public class ModifyConstantInjector extends RedirectInjector {
   private static final int OPCODE_OFFSET = 6;

   public ModifyConstantInjector(InjectionInfo var1) {
      super(var1, "@ModifyConstant");
   }

   protected void inject(Target var1, InjectionNodes$InjectionNode var2) {
      try {
         if (!this.preInject(var2)) {
            return;
         }
      } catch (UnsupportedOperationException var5) {
         throw b(var5);
      }

      try {
         if (var2.isReplaced()) {
            throw new UnsupportedOperationException("Target failure for " + this.info);
         }
      } catch (UnsupportedOperationException var7) {
         throw b(var7);
      }

      AbstractInsnNode var3 = var2.getCurrentTarget();

      try {
         if (var3 instanceof JumpInsnNode) {
            this.checkTargetModifiers(var1, false);
            this.injectExpandedConstantModifier(var1, (JumpInsnNode)var3);
            return;
         }
      } catch (UnsupportedOperationException var4) {
         throw b(var4);
      }

      try {
         if (Bytecode.isConstant(var3)) {
            this.checkTargetModifiers(var1, false);
            this.injectConstantModifier(var1, var3);
            return;
         }
      } catch (UnsupportedOperationException var6) {
         throw b(var6);
      }

      throw new InvalidInjectionException(this.info, this.annotationType + " annotation is targetting an invalid insn in " + var1 + " in " + this);
   }

   private void injectExpandedConstantModifier(Target param1, JumpInsnNode param2) {
      // $FF: Couldn't be decompiled
   }

   private void injectConstantModifier(Target param1, AbstractInsnNode param2) {
      // $FF: Couldn't be decompiled
   }

   private AbstractInsnNode invokeConstantHandler(Type var1, Target var2, InsnList var3, InsnList var4) {
      String var5 = Bytecode.generateDescriptor(var1, var1);
      boolean var6 = this.checkDescriptor(var5, var2, "getter");

      try {
         if (!this.isStatic) {
            var3.insert((AbstractInsnNode)(new VarInsnNode(25, 0)));
            var2.addToStack(1);
         }
      } catch (UnsupportedOperationException var7) {
         throw b(var7);
      }

      try {
         if (var6) {
            this.pushArgs(var2.arguments, var4, var2.getArgIndices(), 0, var2.arguments.length);
            var2.addToStack(Bytecode.getArgsSize(var2.arguments));
         }
      } catch (UnsupportedOperationException var8) {
         throw b(var8);
      }

      return this.invokeHandler(var4);
   }

   private void checkNarrowing(Target param1, AbstractInsnNode param2, Type param3) {
      // $FF: Couldn't be decompiled
   }

   private void checkNarrowing(Target var1, AbstractInsnNode var2, Type var3, Type var4, int var5, String var6) {
      int var7 = var3.getSort();
      int var8 = var4.getSort();
      if (var8 < var7) {
         String var9 = SignaturePrinter.getTypeName(var3, false);
         String var10 = SignaturePrinter.getTypeName(var4, false);

         String var10000;
         label33: {
            try {
               if (var8 == 1) {
                  var10000 = ". Implicit conversion to <boolean> can cause nondeterministic (JVM-specific) behaviour!";
                  break label33;
               }
            } catch (UnsupportedOperationException var14) {
               throw b(var14);
            }

            var10000 = "";
         }

         String var11 = var10000;

         Level var15;
         label25: {
            try {
               if (var8 == 1) {
                  var15 = Level.ERROR;
                  break label25;
               }
            } catch (UnsupportedOperationException var13) {
               throw b(var13);
            }

            var15 = Level.WARN;
         }

         Level var12 = var15;
         Injector.logger.log(var12, "Narrowing conversion of <{}> to <{}> in {} target {} at opcode {} ({}){}", new Object[]{var9, var10, this.info, var1, var5, var6, var11});
      }

   }

   private static UnsupportedOperationException b(UnsupportedOperationException var0) {
      return var0;
   }
}

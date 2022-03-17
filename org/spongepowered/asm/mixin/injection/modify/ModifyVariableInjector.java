package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collection;
import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes$InjectionNode;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.PrettyPrinter$IPrettyPrintable;
import org.spongepowered.asm.util.SignaturePrinter;

public class ModifyVariableInjector extends Injector {
   private final LocalVariableDiscriminator discriminator;

   public ModifyVariableInjector(InjectionInfo var1, LocalVariableDiscriminator var2) {
      super(var1);
      this.discriminator = var2;
   }

   protected boolean findTargetNodes(MethodNode var1, InjectionPoint var2, InsnList var3, Collection<AbstractInsnNode> var4) {
      if (var2 instanceof ModifyVariableInjector$ContextualInjectionPoint) {
         Target var5 = this.info.getContext().getTargetMethod(var1);
         return ((ModifyVariableInjector$ContextualInjectionPoint)var2).find(var5, var4);
      } else {
         return var2.find(var1.desc, var3, var4);
      }
   }

   protected void sanityCheck(Target param1, List<InjectionPoint> param2) {
      // $FF: Couldn't be decompiled
   }

   protected void inject(Target var1, InjectionNodes$InjectionNode var2) {
      try {
         if (var2.isReplaced()) {
            throw new InvalidInjectionException(this.info, "Variable modifier target for " + this + " was removed by another injector");
         }
      } catch (InvalidImplicitDiscriminatorException var12) {
         throw b(var12);
      }

      ModifyVariableInjector$Context var3 = new ModifyVariableInjector$Context(this.returnType, this.discriminator.isArgsOnly(), var1, var2.getCurrentTarget());

      try {
         if (this.discriminator.printLVT()) {
            this.printLocals(var3);
         }
      } catch (InvalidImplicitDiscriminatorException var11) {
         throw b(var11);
      }

      String var4 = Bytecode.getDescriptor(new Type[]{this.returnType}, this.returnType);

      try {
         if (!var4.equals(this.methodNode.desc)) {
            throw new InvalidInjectionException(this.info, "Variable modifier " + this + " has an invalid signature, expected " + var4 + " but found " + this.methodNode.desc);
         }
      } catch (InvalidImplicitDiscriminatorException var10) {
         throw b(var10);
      }

      try {
         int var5 = this.discriminator.findLocal(var3);

         try {
            if (var5 > -1) {
               this.inject(var3, var5);
            }
         } catch (InvalidImplicitDiscriminatorException var6) {
            throw b(var6);
         }
      } catch (InvalidImplicitDiscriminatorException var8) {
         try {
            if (this.discriminator.printLVT()) {
               this.info.addCallbackInvocation(this.methodNode);
               return;
            }
         } catch (InvalidImplicitDiscriminatorException var7) {
            throw b(var7);
         }

         throw new InvalidInjectionException(this.info, "Implicit variable modifier injection failed in " + this, var8);
      }

      Target var10000;
      byte var10001;
      label56: {
         try {
            var1.insns.insertBefore(var3.node, var3.insns);
            var10000 = var1;
            if (this.isStatic) {
               var10001 = 1;
               break label56;
            }
         } catch (InvalidImplicitDiscriminatorException var9) {
            throw b(var9);
         }

         var10001 = 2;
      }

      var10000.addToStack(var10001);
   }

   private void printLocals(ModifyVariableInjector$Context var1) {
      SignaturePrinter var2 = new SignaturePrinter(this.methodNode.name, this.returnType, this.methodArgs, new String[]{"var"});

      PrettyPrinter var10000;
      String var10001;
      String var10002;
      label53: {
         try {
            var2.setModifiers(this.methodNode);
            var10000 = (new PrettyPrinter()).kvWidth(20).kv("Target Class", this.classNode.name.replace('/', '.')).kv("Target Method", var1.target.method.name).kv("Callback Name", this.methodNode.name).kv("Capture Type", SignaturePrinter.getTypeName(this.returnType, false)).kv("Instruction", "%s %s", var1.node.getClass().getSimpleName(), Bytecode.getOpcodeName(var1.node.getOpcode())).hr();
            var10001 = "Match mode";
            if (this.discriminator.isImplicit(var1)) {
               var10002 = "IMPLICIT (match single)";
               break label53;
            }
         } catch (InvalidInjectionException var6) {
            throw b(var6);
         }

         var10002 = "EXPLICIT (match by criteria)";
      }

      Object var7;
      label46: {
         try {
            var10000 = var10000.kv(var10001, var10002);
            var10001 = "Match ordinal";
            if (this.discriminator.getOrdinal() < 0) {
               var7 = "any";
               break label46;
            }
         } catch (InvalidInjectionException var5) {
            throw b(var5);
         }

         var7 = this.discriminator.getOrdinal();
      }

      label39: {
         try {
            var10000 = var10000.kv(var10001, var7);
            var10001 = "Match index";
            if (this.discriminator.getIndex() < var1.baseArgIndex) {
               var7 = "any";
               break label39;
            }
         } catch (InvalidInjectionException var4) {
            throw b(var4);
         }

         var7 = this.discriminator.getIndex();
      }

      label32: {
         try {
            var10000 = var10000.kv(var10001, var7);
            var10001 = "Match name(s)";
            if (this.discriminator.hasNames()) {
               var7 = this.discriminator.getNames();
               break label32;
            }
         } catch (InvalidInjectionException var3) {
            throw b(var3);
         }

         var7 = "any";
      }

      var10000.kv(var10001, var7).kv("Args only", this.discriminator.isArgsOnly()).hr().add((PrettyPrinter$IPrettyPrintable)var1).print(System.err);
   }

   private void inject(ModifyVariableInjector$Context var1, int var2) {
      try {
         if (!this.isStatic) {
            var1.insns.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
         }
      } catch (InvalidInjectionException var3) {
         throw b(var3);
      }

      var1.insns.add((AbstractInsnNode)(new VarInsnNode(this.returnType.getOpcode(21), var2)));
      this.invokeHandler(var1.insns);
      var1.insns.add((AbstractInsnNode)(new VarInsnNode(this.returnType.getOpcode(54), var2)));
   }

   private static MixinException b(MixinException var0) {
      return var0;
   }
}

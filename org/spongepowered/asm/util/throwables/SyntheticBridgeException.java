package org.spongepowered.asm.util.throwables;

import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.PrettyPrinter;

public class SyntheticBridgeException extends MixinException {
   private static final long serialVersionUID = 1L;
   private final SyntheticBridgeException$Problem problem;
   private final String name;
   private final String desc;
   private final int index;
   private final AbstractInsnNode a;
   private final AbstractInsnNode b;

   public SyntheticBridgeException(SyntheticBridgeException$Problem var1, String var2, String var3, int var4, AbstractInsnNode var5, AbstractInsnNode var6) {
      super(var1.getMessage(var2, var3, var4, var5, var6));
      this.problem = var1;
      this.name = var2;
      this.desc = var3;
      this.index = var4;
      this.a = var5;
      this.b = var6;
   }

   public void printAnalysis(IMixinContext var1, MethodNode var2, MethodNode var3) {
      PrettyPrinter var4 = new PrettyPrinter();
      var4.addWrapped(100, this.getMessage()).hr();
      var4.add().kv("Method", this.name + this.desc).kv("Problem Type", this.problem).add().hr();
      String var5 = (String)Annotations.getValue(Annotations.getVisible(var2, MixinMerged.class), "mixin");

      String var10000;
      label17: {
         try {
            if (var5 != null) {
               var10000 = var5;
               break label17;
            }
         } catch (SyntheticBridgeException var7) {
            throw b(var7);
         }

         var10000 = var1.getTargetClassRef().replace('/', '.');
      }

      String var6 = var10000;
      this.printMethod(var4.add("Existing method").add().kv("Owner", var6).add(), var2).hr();
      this.printMethod(var4.add("Incoming method").add().kv("Owner", var1.getClassRef().replace('/', '.')).add(), var3).hr();
      this.printProblem(var4, var1, var2, var3).print(System.err);
   }

   private PrettyPrinter printMethod(PrettyPrinter param1, MethodNode param2) {
      // $FF: Couldn't be decompiled
   }

   private PrettyPrinter printProblem(PrettyPrinter var1, IMixinContext var2, MethodNode var3, MethodNode var4) {
      // $FF: Couldn't be decompiled
   }

   private PrettyPrinter printTypeComparison(PrettyPrinter var1, String var2, Type var3, Type var4) {
      try {
         var1.kv("Target " + var2, "%s", var3);
         var1.kv("Incoming " + var2, "%s", var4);
         if (var3.equals(var4)) {
            var1.kv("Analysis", "Types match: %s", var3);
            return var1.add();
         }
      } catch (SyntheticBridgeException var7) {
         throw b(var7);
      }

      try {
         if (var3.getSort() != var4.getSort()) {
            var1.kv("Analysis", "Types are incompatible");
            return var1.add();
         }
      } catch (SyntheticBridgeException var6) {
         throw b(var6);
      }

      if (var3.getSort() == 10) {
         ClassInfo var5 = ClassInfo.getCommonSuperClassOrInterface(var3, var4);
         var1.kv("Analysis", "Common supertype: L%s;", var5);
      }

      return var1.add();
   }

   private static SyntheticBridgeException b(SyntheticBridgeException var0) {
      return var0;
   }
}

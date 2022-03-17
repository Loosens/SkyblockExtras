package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.util.Bytecode;

public class AccessorGeneratorMethodProxy extends AccessorGenerator {
   private final MethodNode targetMethod;
   private final Type[] argTypes;
   private final Type returnType;
   private final boolean isInstanceMethod;

   public AccessorGeneratorMethodProxy(AccessorInfo var1) {
      super(var1);
      this.targetMethod = var1.getTargetMethod();
      this.argTypes = var1.getArgTypes();
      this.returnType = var1.getReturnType();
      this.isInstanceMethod = !Bytecode.hasFlag((MethodNode)this.targetMethod, 8);
   }

   public MethodNode generate() {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

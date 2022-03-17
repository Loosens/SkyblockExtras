package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidInterfaceMixinException;
import org.spongepowered.asm.util.Bytecode;

class MixinPreProcessorInterface extends MixinPreProcessorStandard {
   MixinPreProcessorInterface(MixinInfo var1, MixinInfo$MixinClassNode var2) {
      super(var1, var2);
   }

   protected void prepareMethod(MixinInfo$MixinMethodNode param1, ClassInfo$Method param2) {
      // $FF: Couldn't be decompiled
   }

   protected boolean validateField(MixinTargetContext var1, FieldNode var2, AnnotationNode var3) {
      try {
         if (!Bytecode.hasFlag((FieldNode)var2, 8)) {
            throw new InvalidInterfaceMixinException(this.mixin, "Interface mixin contains an instance field! Found " + var2.name + " in " + this.mixin);
         }
      } catch (InvalidInterfaceMixinException var4) {
         throw b(var4);
      }

      return super.validateField(var1, var2, var3);
   }

   private static InvalidInterfaceMixinException b(InvalidInterfaceMixinException var0) {
      return var0;
   }
}

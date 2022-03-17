package org.spongepowered.asm.mixin.transformer;

import java.util.List;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;

class MixinInfo$SubType$Interface extends MixinInfo$SubType {
   MixinInfo$SubType$Interface(MixinInfo var1) {
      super(var1, "@Mixin", true);
   }

   void validate(MixinInfo$State var1, List<ClassInfo> var2) {
      try {
         if (!MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces()) {
            throw new InvalidMixinException(this.mixin, "Interface mixin not supported in current enviromnment");
         }
      } catch (InvalidMixinException var5) {
         throw c(var5);
      }

      MixinInfo$MixinClassNode var3 = var1.getClassNode();

      try {
         if (!"java/lang/Object".equals(var3.superName)) {
            throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + var3.superName.replace('/', '.'));
         }
      } catch (InvalidMixinException var4) {
         throw c(var4);
      }
   }

   MixinPreProcessorStandard createPreProcessor(MixinInfo$MixinClassNode var1) {
      return new MixinPreProcessorInterface(this.mixin, var1);
   }

   private static InvalidMixinException c(InvalidMixinException var0) {
      return var0;
   }
}

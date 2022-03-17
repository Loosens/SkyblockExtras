package org.spongepowered.asm.mixin.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;

class MixinInfo$SubType$Accessor extends MixinInfo$SubType {
   private final Collection<String> interfaces = new ArrayList();

   MixinInfo$SubType$Accessor(MixinInfo var1) {
      super(var1, "@Mixin", false);
      this.interfaces.add(var1.getClassRef());
   }

   boolean isLoadable() {
      return true;
   }

   Collection<String> getInterfaces() {
      return this.interfaces;
   }

   void validateTarget(String param1, ClassInfo param2) {
      // $FF: Couldn't be decompiled
   }

   void validate(MixinInfo$State var1, List<ClassInfo> var2) {
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
      return new MixinPreProcessorAccessor(this.mixin, var1);
   }

   private static InvalidMixinException c(InvalidMixinException var0) {
      return var0;
   }
}

package org.spongepowered.asm.mixin.transformer;

import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;

class MixinInfo$SubType$Standard extends MixinInfo$SubType {
   MixinInfo$SubType$Standard(MixinInfo var1) {
      super(var1, "@Mixin", false);
   }

   void validate(MixinInfo$State var1, List<ClassInfo> var2) {
      MixinInfo$MixinClassNode var3 = var1.getClassNode();
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         ClassInfo var5 = (ClassInfo)var4.next();

         try {
            if (var3.superName.equals(var5.getSuperName())) {
               continue;
            }
         } catch (InvalidMixinException var10) {
            throw c(var10);
         }

         if (!var5.hasSuperClass(var3.superName, ClassInfo$Traversal.SUPER)) {
            ClassInfo var6 = ClassInfo.forName(var3.superName);
            if (var6.isMixin()) {
               Iterator var7 = var6.getTargets().iterator();

               while(var7.hasNext()) {
                  ClassInfo var8 = (ClassInfo)var7.next();

                  try {
                     if (var2.contains(var8)) {
                        throw new InvalidMixinException(this.mixin, "Illegal hierarchy detected. Derived mixin " + this + " targets the same class " + var8.getClassName() + " as its superclass " + var6.getClassName());
                     }
                  } catch (InvalidMixinException var9) {
                     throw c(var9);
                  }
               }
            }

            throw new InvalidMixinException(this.mixin, "Super class '" + var3.superName.replace('/', '.') + "' of " + this.mixin.getName() + " was not found in the hierarchy of target class '" + var5 + "'");
         }

         this.detached = true;
      }

   }

   MixinPreProcessorStandard createPreProcessor(MixinInfo$MixinClassNode var1) {
      return new MixinPreProcessorStandard(this.mixin, var1);
   }

   private static InvalidMixinException c(InvalidMixinException var0) {
      return var0;
   }
}

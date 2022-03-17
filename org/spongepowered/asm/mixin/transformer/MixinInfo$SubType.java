package org.spongepowered.asm.mixin.transformer;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;

abstract class MixinInfo$SubType {
   protected final MixinInfo mixin;
   protected final String annotationType;
   protected final boolean targetMustBeInterface;
   protected boolean detached;

   MixinInfo$SubType(MixinInfo var1, String var2, boolean var3) {
      this.mixin = var1;
      this.annotationType = var2;
      this.targetMustBeInterface = var3;
   }

   Collection<String> getInterfaces() {
      return Collections.emptyList();
   }

   boolean isDetachedSuper() {
      return this.detached;
   }

   boolean isLoadable() {
      return false;
   }

   void validateTarget(String param1, ClassInfo param2) {
      // $FF: Couldn't be decompiled
   }

   abstract void validate(MixinInfo$State var1, List<ClassInfo> var2);

   abstract MixinPreProcessorStandard createPreProcessor(MixinInfo$MixinClassNode var1);

   static MixinInfo$SubType getTypeFor(MixinInfo var0) {
      try {
         if (!var0.getClassInfo().isInterface()) {
            return new MixinInfo$SubType$Standard(var0);
         }
      } catch (InvalidMixinException var6) {
         throw b(var6);
      }

      boolean var1 = false;

      boolean var10000;
      boolean var10001;
      for(Iterator var2 = var0.getClassInfo().getMethods().iterator(); var2.hasNext(); var1 = var10000 | var10001) {
         ClassInfo$Method var3 = (ClassInfo$Method)var2.next();

         try {
            var10000 = var1;
            if (!var3.isAccessor()) {
               var10001 = true;
               continue;
            }
         } catch (InvalidMixinException var5) {
            throw b(var5);
         }

         var10001 = false;
      }

      try {
         if (var1) {
            return new MixinInfo$SubType$Interface(var0);
         }
      } catch (InvalidMixinException var4) {
         throw b(var4);
      }

      return new MixinInfo$SubType$Accessor(var0);
   }

   private static InvalidMixinException b(InvalidMixinException var0) {
      return var0;
   }
}

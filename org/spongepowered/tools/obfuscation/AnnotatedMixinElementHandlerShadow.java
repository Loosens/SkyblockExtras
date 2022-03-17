package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

class AnnotatedMixinElementHandlerShadow extends AnnotatedMixinElementHandler {
   AnnotatedMixinElementHandlerShadow(IMixinAnnotationProcessor var1, AnnotatedMixin var2) {
      super(var1, var2);
   }

   public void registerShadow(AnnotatedMixinElementHandlerShadow$AnnotatedElementShadow<?, ?> var1) {
      try {
         this.validateTarget(var1.getElement(), var1.getAnnotation(), var1.getName(), "@Shadow");
         if (!var1.shouldRemap()) {
            return;
         }
      } catch (Mappings$MappingConflictException var4) {
         throw b(var4);
      }

      Iterator var2 = this.mixin.getTargets().iterator();

      while(var2.hasNext()) {
         TypeHandle var3 = (TypeHandle)var2.next();
         this.registerShadowForTarget(var1, var3);
      }

   }

   private void registerShadowForTarget(AnnotatedMixinElementHandlerShadow$AnnotatedElementShadow<?, ?> param1, TypeHandle param2) {
      // $FF: Couldn't be decompiled
   }

   private static Mappings$MappingConflictException b(Mappings$MappingConflictException var0) {
      return var0;
   }
}

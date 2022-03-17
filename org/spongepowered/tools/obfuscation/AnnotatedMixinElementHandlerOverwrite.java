package org.spongepowered.tools.obfuscation;

import java.lang.reflect.Method;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

class AnnotatedMixinElementHandlerOverwrite extends AnnotatedMixinElementHandler {
   AnnotatedMixinElementHandlerOverwrite(IMixinAnnotationProcessor var1, AnnotatedMixin var2) {
      super(var1, var2);
   }

   public void registerMerge(ExecutableElement var1) {
      this.validateTargetMethod(var1, (AnnotationHandle)null, new AnnotatedMixinElementHandler$AliasedElementName(var1, AnnotationHandle.MISSING), "overwrite", true, true);
   }

   public void registerOverwrite(AnnotatedMixinElementHandlerOverwrite$AnnotatedElementOverwrite param1) {
      // $FF: Couldn't be decompiled
   }

   private boolean registerOverwriteForTarget(AnnotatedMixinElementHandlerOverwrite$AnnotatedElementOverwrite var1, TypeHandle var2) {
      MappingMethod var3 = var2.getMappingMethod(var1.getSimpleName(), var1.getDesc());
      ObfuscationData var4 = this.obf.getDataProvider().getObfMethod(var3);
      if (var4.isEmpty()) {
         Kind var5 = Kind.ERROR;

         try {
            Method var6 = ((ExecutableElement)var1.getElement()).getClass().getMethod("isStatic");
            if ((Boolean)var6.invoke(var1.getElement())) {
               var5 = Kind.WARNING;
            }
         } catch (Exception var7) {
         }

         this.ap.printMessage(var5, "No obfuscation mapping for @Overwrite method", var1.getElement());
         return false;
      } else {
         try {
            this.addMethodMappings(var1.getSimpleName(), var1.getDesc(), var4);
            return true;
         } catch (Mappings$MappingConflictException var8) {
            var1.printMessage(this.ap, Kind.ERROR, "Mapping conflict for @Overwrite method: " + var8.getNew().getSimpleName() + " for target " + var2 + " conflicts with existing mapping " + var8.getOld().getSimpleName());
            return false;
         }
      }
   }

   private static Mappings$MappingConflictException b(Mappings$MappingConflictException var0) {
      return var0;
   }
}

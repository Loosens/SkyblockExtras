package org.spongepowered.tools.obfuscation;

import javax.lang.model.element.ExecutableElement;
import org.spongepowered.asm.mixin.gen.AccessorInfo$AccessorType;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

class AnnotatedMixinElementHandlerAccessor$AnnotatedElementInvoker extends AnnotatedMixinElementHandlerAccessor$AnnotatedElementAccessor {
   public AnnotatedMixinElementHandlerAccessor$AnnotatedElementInvoker(ExecutableElement var1, AnnotationHandle var2, boolean var3) {
      super(var1, var2, var3);
   }

   public String getAccessorDesc() {
      return TypeUtils.getDescriptor((ExecutableElement)this.getElement());
   }

   public AccessorInfo$AccessorType getAccessorType() {
      return AccessorInfo$AccessorType.METHOD_PROXY;
   }

   public String getTargetTypeName() {
      return TypeUtils.getJavaSignature(this.getElement());
   }
}

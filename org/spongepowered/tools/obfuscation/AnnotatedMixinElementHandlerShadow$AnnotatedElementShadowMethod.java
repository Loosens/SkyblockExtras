package org.spongepowered.tools.obfuscation;

import javax.lang.model.element.ExecutableElement;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.IMapping$Type;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

class AnnotatedMixinElementHandlerShadow$AnnotatedElementShadowMethod extends AnnotatedMixinElementHandlerShadow$AnnotatedElementShadow<ExecutableElement, MappingMethod> {
   final AnnotatedMixinElementHandlerShadow this$0;

   public AnnotatedMixinElementHandlerShadow$AnnotatedElementShadowMethod(AnnotatedMixinElementHandlerShadow var1, ExecutableElement var2, AnnotationHandle var3, boolean var4) {
      super(var2, var3, var4, IMapping$Type.METHOD);
      this.this$0 = var1;
   }

   public MappingMethod getMapping(TypeHandle var1, String var2, String var3) {
      return var1.getMappingMethod(var2, var3);
   }

   public void addMapping(ObfuscationType var1, IMapping<?> var2) {
      this.this$0.addMethodMapping(var1, this.setObfuscatedName(var2), this.getDesc(), var2.getDesc());
   }
}

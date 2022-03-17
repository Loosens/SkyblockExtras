package org.spongepowered.tools.obfuscation;

import javax.lang.model.element.VariableElement;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.IMapping$Type;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

class AnnotatedMixinElementHandlerShadow$AnnotatedElementShadowField extends AnnotatedMixinElementHandlerShadow$AnnotatedElementShadow<VariableElement, MappingField> {
   final AnnotatedMixinElementHandlerShadow this$0;

   public AnnotatedMixinElementHandlerShadow$AnnotatedElementShadowField(AnnotatedMixinElementHandlerShadow var1, VariableElement var2, AnnotationHandle var3, boolean var4) {
      super(var2, var3, var4, IMapping$Type.FIELD);
      this.this$0 = var1;
   }

   public MappingField getMapping(TypeHandle var1, String var2, String var3) {
      return new MappingField(var1.getName(), var2, var3);
   }

   public void addMapping(ObfuscationType var1, IMapping<?> var2) {
      this.this$0.addFieldMapping(var1, this.setObfuscatedName(var2), this.getDesc(), var2.getDesc());
   }
}

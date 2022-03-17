package org.spongepowered.tools.obfuscation;

import javax.lang.model.element.Element;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.IMapping$Type;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

abstract class AnnotatedMixinElementHandlerShadow$AnnotatedElementShadow<E extends Element, M extends IMapping<M>> extends AnnotatedMixinElementHandler$AnnotatedElement<E> {
   private final boolean shouldRemap;
   private final AnnotatedMixinElementHandler$ShadowElementName name;
   private final IMapping$Type type;

   protected AnnotatedMixinElementHandlerShadow$AnnotatedElementShadow(E var1, AnnotationHandle var2, boolean var3, IMapping$Type var4) {
      super(var1, var2);
      this.shouldRemap = var3;
      this.name = new AnnotatedMixinElementHandler$ShadowElementName(var1, var2);
      this.type = var4;
   }

   public boolean shouldRemap() {
      return this.shouldRemap;
   }

   public AnnotatedMixinElementHandler$ShadowElementName getName() {
      return this.name;
   }

   public IMapping$Type getElementType() {
      return this.type;
   }

   public String toString() {
      return this.getElementType().name().toLowerCase();
   }

   public AnnotatedMixinElementHandler$ShadowElementName setObfuscatedName(IMapping<?> var1) {
      return this.setObfuscatedName(var1.getSimpleName());
   }

   public AnnotatedMixinElementHandler$ShadowElementName setObfuscatedName(String var1) {
      return this.getName().setObfuscatedName(var1);
   }

   public ObfuscationData<M> getObfuscationData(IObfuscationDataProvider var1, TypeHandle var2) {
      return var1.getObfEntry(this.getMapping(var2, this.getName().toString(), this.getDesc()));
   }

   public abstract M getMapping(TypeHandle var1, String var2, String var3);

   public abstract void addMapping(ObfuscationType var1, IMapping<?> var2);
}

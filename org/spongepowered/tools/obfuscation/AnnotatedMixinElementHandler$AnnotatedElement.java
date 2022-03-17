package org.spongepowered.tools.obfuscation;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

abstract class AnnotatedMixinElementHandler$AnnotatedElement<E extends Element> {
   protected final E element;
   protected final AnnotationHandle annotation;
   private final String desc;

   public AnnotatedMixinElementHandler$AnnotatedElement(E var1, AnnotationHandle var2) {
      this.element = var1;
      this.annotation = var2;
      this.desc = TypeUtils.getDescriptor(var1);
   }

   public E getElement() {
      return this.element;
   }

   public AnnotationHandle getAnnotation() {
      return this.annotation;
   }

   public String getSimpleName() {
      return this.getElement().getSimpleName().toString();
   }

   public String getDesc() {
      return this.desc;
   }

   public final void printMessage(Messager var1, Kind var2, CharSequence var3) {
      var1.printMessage(var2, var3, this.element, this.annotation.asMirror());
   }
}

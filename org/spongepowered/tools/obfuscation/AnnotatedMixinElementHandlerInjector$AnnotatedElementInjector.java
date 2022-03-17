package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.struct.InjectorRemap;

class AnnotatedMixinElementHandlerInjector$AnnotatedElementInjector extends AnnotatedMixinElementHandler$AnnotatedElement<ExecutableElement> {
   private final InjectorRemap state;

   public AnnotatedMixinElementHandlerInjector$AnnotatedElementInjector(ExecutableElement var1, AnnotationHandle var2, InjectorRemap var3) {
      super(var1, var2);
      this.state = var3;
   }

   public boolean shouldRemap() {
      return this.state.shouldRemap();
   }

   public boolean hasCoerceArgument() {
      try {
         if (!this.annotation.toString().equals("@Inject")) {
            return false;
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      Iterator var1 = ((ExecutableElement)this.element).getParameters().iterator();
      if (var1.hasNext()) {
         VariableElement var2 = (VariableElement)var1.next();
         return AnnotationHandle.of(var2, Coerce.class).exists();
      } else {
         return false;
      }
   }

   public void addMessage(Kind var1, CharSequence var2, Element var3, AnnotationHandle var4) {
      this.state.addMessage(var1, var2, var3, var4);
   }

   public String toString() {
      return this.getAnnotation().toString();
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

package org.spongepowered.tools.obfuscation;

import javax.lang.model.element.ExecutableElement;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;

class AnnotatedMixinElementHandlerOverwrite$AnnotatedElementOverwrite extends AnnotatedMixinElementHandler$AnnotatedElement<ExecutableElement> {
   private final boolean shouldRemap;

   public AnnotatedMixinElementHandlerOverwrite$AnnotatedElementOverwrite(ExecutableElement var1, AnnotationHandle var2, boolean var3) {
      super(var1, var2);
      this.shouldRemap = var3;
   }

   public boolean shouldRemap() {
      return this.shouldRemap;
   }
}

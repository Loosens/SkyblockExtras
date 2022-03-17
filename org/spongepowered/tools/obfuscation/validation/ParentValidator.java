package org.spongepowered.tools.obfuscation.validation;

import java.util.Collection;
import javax.lang.model.element.TypeElement;
import org.spongepowered.tools.obfuscation.MixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator$ValidationPass;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public class ParentValidator extends MixinValidator {
   public ParentValidator(IMixinAnnotationProcessor var1) {
      super(var1, IMixinValidator$ValidationPass.EARLY);
   }

   public boolean validate(TypeElement param1, AnnotationHandle param2, Collection<TypeHandle> param3) {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException c(RuntimeException var0) {
      return var0;
   }
}

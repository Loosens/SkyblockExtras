package org.spongepowered.asm.mixin.transformer.ext.extensions;

import org.spongepowered.asm.mixin.throwables.MixinException;

public class ExtensionCheckClass$ValidationFailedException extends MixinException {
   private static final long serialVersionUID = 1L;

   public ExtensionCheckClass$ValidationFailedException(String var1, Throwable var2) {
      super(var1, var2);
   }

   public ExtensionCheckClass$ValidationFailedException(String var1) {
      super(var1);
   }

   public ExtensionCheckClass$ValidationFailedException(Throwable var1) {
      super(var1);
   }
}

package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler;
import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler$ErrorAction;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;

enum MixinTransformer$ErrorPhase$2 {
   IMixinErrorHandler$ErrorAction onError(IMixinErrorHandler var1, String var2, InvalidMixinException var3, IMixinInfo var4, IMixinErrorHandler$ErrorAction var5) {
      try {
         return var1.onApplyError(var2, var3, var4, var5);
      } catch (AbstractMethodError var7) {
         return var5;
      }
   }

   protected String getContext(IMixinInfo var1, String var2) {
      return String.format("%s -> %s", var1, var2);
   }
}

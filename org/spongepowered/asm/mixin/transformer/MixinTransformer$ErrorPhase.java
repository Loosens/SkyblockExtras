package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.MixinEnvironment$Phase;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler;
import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler$ErrorAction;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;

enum MixinTransformer$ErrorPhase {
   PREPARE,
   APPLY;

   private final String text;
   private static final MixinTransformer$ErrorPhase[] $VALUES = new MixinTransformer$ErrorPhase[]{PREPARE, APPLY};

   private MixinTransformer$ErrorPhase() {
      this.text = this.name().toLowerCase();
   }

   abstract IMixinErrorHandler$ErrorAction onError(IMixinErrorHandler var1, String var2, InvalidMixinException var3, IMixinInfo var4, IMixinErrorHandler$ErrorAction var5);

   protected abstract String getContext(IMixinInfo var1, String var2);

   public String getLogMessage(String var1, InvalidMixinException var2, IMixinInfo var3) {
      return String.format("Mixin %s failed %s: %s %s", this.text, this.getContext(var3, var1), var2.getClass().getName(), var2.getMessage());
   }

   public String getErrorMessage(IMixinInfo var1, IMixinConfig var2, MixinEnvironment$Phase var3) {
      return String.format("Mixin [%s] from phase [%s] in config [%s] FAILED during %s", var1, var3, var2, this.name());
   }

   MixinTransformer$ErrorPhase(MixinTransformer$1 var3) {
      this();
   }
}

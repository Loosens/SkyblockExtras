package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.transformer.ext.IHotSwap;

class MixinTransformer$1 implements MixinConfig$IListener {
   final IHotSwap val$hotSwapper;
   final MixinTransformer this$0;

   MixinTransformer$1(MixinTransformer var1, IHotSwap var2) {
      this.this$0 = var1;
      this.val$hotSwapper = var2;
   }

   public void onPrepare(MixinInfo var1) {
      this.val$hotSwapper.registerMixinClass(var1.getClassName());
   }

   public void onInit(MixinInfo var1) {
   }
}

package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Function;

class MixinInfo$2 implements Function<String, String> {
   final MixinInfo this$0;

   MixinInfo$2(MixinInfo var1) {
      this.this$0 = var1;
   }

   public String apply(String var1) {
      return this.this$0.getParent().remapClassName(this.this$0.getClassRef(), var1);
   }
}

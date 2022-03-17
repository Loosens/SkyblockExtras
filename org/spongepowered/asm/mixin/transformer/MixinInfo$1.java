package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Function;
import org.spongepowered.asm.lib.Type;

class MixinInfo$1 implements Function<Type, String> {
   final MixinInfo this$0;

   MixinInfo$1(MixinInfo var1) {
      this.this$0 = var1;
   }

   public String apply(Type var1) {
      return var1.getClassName();
   }
}

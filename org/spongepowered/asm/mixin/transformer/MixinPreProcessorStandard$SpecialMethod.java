package org.spongepowered.asm.mixin.transformer;

import java.lang.annotation.Annotation;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.util.Bytecode;

enum MixinPreProcessorStandard$SpecialMethod {
   MERGE(true),
   OVERWRITE(true, Overwrite.class),
   SHADOW(false, Shadow.class),
   ACCESSOR(false, Accessor.class),
   INVOKER(false, Invoker.class);

   final boolean isOverwrite;
   final Class<? extends Annotation> annotation;
   final String description;
   private static final MixinPreProcessorStandard$SpecialMethod[] $VALUES = new MixinPreProcessorStandard$SpecialMethod[]{MERGE, OVERWRITE, SHADOW, ACCESSOR, INVOKER};

   private MixinPreProcessorStandard$SpecialMethod(boolean var3, Class<? extends Annotation> var4) {
      this.isOverwrite = var3;
      this.annotation = var4;
      this.description = "@" + Bytecode.getSimpleName(var4);
   }

   private MixinPreProcessorStandard$SpecialMethod(boolean var3) {
      this.isOverwrite = var3;
      this.annotation = null;
      this.description = "overwrite";
   }

   public String toString() {
      return this.description;
   }
}

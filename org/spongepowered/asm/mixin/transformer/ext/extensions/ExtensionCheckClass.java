package org.spongepowered.asm.mixin.transformer.ext.extensions;

import org.spongepowered.asm.lib.util.CheckClassAdapter;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.MixinEnvironment$Option;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.transformers.MixinClassWriter;

public class ExtensionCheckClass implements IExtension {
   public boolean checkActive(MixinEnvironment var1) {
      return var1.getOption(MixinEnvironment$Option.DEBUG_VERIFY);
   }

   public void preApply(ITargetClassContext var1) {
   }

   public void postApply(ITargetClassContext var1) {
      try {
         var1.getClassNode().accept(new CheckClassAdapter(new MixinClassWriter(2)));
      } catch (RuntimeException var3) {
         throw new ExtensionCheckClass$ValidationFailedException(var3.getMessage(), var3);
      }
   }

   public void export(MixinEnvironment var1, String var2, boolean var3, byte[] var4) {
   }
}

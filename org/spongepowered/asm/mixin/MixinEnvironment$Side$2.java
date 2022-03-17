package org.spongepowered.asm.mixin;

import org.spongepowered.asm.service.MixinService;

enum MixinEnvironment$Side$2 {
   protected boolean detect() {
      String var1 = MixinService.getService().getSideName();
      return "CLIENT".equals(var1);
   }
}

package org.spongepowered.asm.service.mojang;

import javax.annotation.Resource;
import net.minecraft.launchwrapper.IClassTransformer;
import org.spongepowered.asm.service.ILegacyClassTransformer;

class LegacyTransformerHandle implements ILegacyClassTransformer {
   private final IClassTransformer transformer;

   LegacyTransformerHandle(IClassTransformer var1) {
      this.transformer = var1;
   }

   public String getName() {
      return this.transformer.getClass().getName();
   }

   public boolean isDelegationExcluded() {
      boolean var10000;
      try {
         if (this.transformer.getClass().getAnnotation(Resource.class) != null) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public byte[] transformClassBytes(String var1, String var2, byte[] var3) {
      return this.transformer.transform(var1, var2, var3);
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

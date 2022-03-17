package org.spongepowered.asm.mixin.gen;

import java.util.Set;

enum AccessorInfo$AccessorType$1 {
   AccessorInfo$AccessorType$1(Set var3) {
   }

   AccessorGenerator getGenerator(AccessorInfo var1) {
      return new AccessorGeneratorFieldGetter(var1);
   }
}

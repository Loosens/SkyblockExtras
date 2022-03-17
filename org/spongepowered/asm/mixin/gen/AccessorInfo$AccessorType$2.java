package org.spongepowered.asm.mixin.gen;

import java.util.Set;

enum AccessorInfo$AccessorType$2 {
   AccessorInfo$AccessorType$2(Set var3) {
   }

   AccessorGenerator getGenerator(AccessorInfo var1) {
      return new AccessorGeneratorFieldSetter(var1);
   }
}

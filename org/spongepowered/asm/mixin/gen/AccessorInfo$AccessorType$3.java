package org.spongepowered.asm.mixin.gen;

import java.util.Set;

enum AccessorInfo$AccessorType$3 {
   AccessorInfo$AccessorType$3(Set var3) {
   }

   AccessorGenerator getGenerator(AccessorInfo var1) {
      return new AccessorGeneratorMethodProxy(var1);
   }
}

package org.spongepowered.tools.obfuscation.mirror.mapping;

import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public final class ResolvableMappingMethod extends MappingMethod {
   private final TypeHandle ownerHandle;

   public ResolvableMappingMethod(TypeHandle var1, String var2, String var3) {
      super(var1.getName(), var2, var3);
      this.ownerHandle = var1;
   }

   public MappingMethod getSuper() {
      // $FF: Couldn't be decompiled
   }

   public MappingMethod move(TypeHandle var1) {
      return new ResolvableMappingMethod(var1, this.getSimpleName(), this.getDesc());
   }

   public MappingMethod remap(String var1) {
      return new ResolvableMappingMethod(this.ownerHandle, var1, this.getDesc());
   }

   public MappingMethod transform(String var1) {
      return new ResolvableMappingMethod(this.ownerHandle, this.getSimpleName(), var1);
   }

   public MappingMethod copy() {
      return new ResolvableMappingMethod(this.ownerHandle, this.getSimpleName(), this.getDesc());
   }

   private static RuntimeException c(RuntimeException var0) {
      return var0;
   }
}

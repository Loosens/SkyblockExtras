package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.lib.Type;

public class LocalVariableDiscriminator$Context$Local {
   int ord;
   String name;
   Type type;
   final LocalVariableDiscriminator$Context this$0;

   public LocalVariableDiscriminator$Context$Local(LocalVariableDiscriminator$Context var1, String var2, Type var3) {
      this.this$0 = var1;
      this.ord = 0;
      this.name = var2;
      this.type = var3;
   }

   public String toString() {
      return String.format("Local[ordinal=%d, name=%s, type=%s]", this.ord, this.name, this.type);
   }
}

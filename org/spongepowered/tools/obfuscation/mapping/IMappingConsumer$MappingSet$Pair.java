package org.spongepowered.tools.obfuscation.mapping;

import com.google.common.base.Objects;
import org.spongepowered.asm.obfuscation.mapping.IMapping;

public class IMappingConsumer$MappingSet$Pair<TMapping extends IMapping<TMapping>> {
   public final TMapping from;
   public final TMapping to;

   public IMappingConsumer$MappingSet$Pair(TMapping var1, TMapping var2) {
      this.from = var1;
      this.to = var2;
   }

   public boolean equals(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{this.from, this.to});
   }

   public String toString() {
      return String.format("%s -> %s", this.from, this.to);
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

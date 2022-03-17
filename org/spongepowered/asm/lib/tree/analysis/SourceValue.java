package org.spongepowered.asm.lib.tree.analysis;

import java.util.Set;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;

public class SourceValue implements Value {
   public final int size;
   public final Set<AbstractInsnNode> insns;

   public SourceValue(int var1) {
      this(var1, SmallSet.emptySet());
   }

   public SourceValue(int var1, AbstractInsnNode var2) {
      this.size = var1;
      this.insns = new SmallSet(var2, (Object)null);
   }

   public SourceValue(int var1, Set<AbstractInsnNode> var2) {
      this.size = var1;
      this.insns = var2;
   }

   public int getSize() {
      return this.size;
   }

   public boolean equals(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public int hashCode() {
      return this.insns.hashCode();
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

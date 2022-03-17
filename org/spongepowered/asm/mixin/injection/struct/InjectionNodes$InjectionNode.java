package org.spongepowered.asm.mixin.injection.struct;

import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.util.Bytecode;

public class InjectionNodes$InjectionNode implements Comparable<InjectionNodes$InjectionNode> {
   private static int nextId = 0;
   private final int id;
   private final AbstractInsnNode originalTarget;
   private AbstractInsnNode currentTarget;
   private Map<String, Object> decorations;

   public InjectionNodes$InjectionNode(AbstractInsnNode var1) {
      this.currentTarget = this.originalTarget = var1;
      this.id = nextId++;
   }

   public int getId() {
      return this.id;
   }

   public AbstractInsnNode getOriginalTarget() {
      return this.originalTarget;
   }

   public AbstractInsnNode getCurrentTarget() {
      return this.currentTarget;
   }

   public InjectionNodes$InjectionNode replace(AbstractInsnNode var1) {
      this.currentTarget = var1;
      return this;
   }

   public InjectionNodes$InjectionNode remove() {
      this.currentTarget = null;
      return this;
   }

   public boolean matches(AbstractInsnNode param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean isReplaced() {
      boolean var10000;
      try {
         if (this.originalTarget != this.currentTarget) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public boolean isRemoved() {
      boolean var10000;
      try {
         if (this.currentTarget == null) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public <V> InjectionNodes$InjectionNode decorate(String var1, V var2) {
      try {
         if (this.decorations == null) {
            this.decorations = new HashMap();
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      this.decorations.put(var1, var2);
      return this;
   }

   public boolean hasDecoration(String param1) {
      // $FF: Couldn't be decompiled
   }

   public <V> V getDecoration(String var1) {
      Object var10000;
      try {
         if (this.decorations == null) {
            var10000 = null;
            return var10000;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      var10000 = this.decorations.get(var1);
      return var10000;
   }

   public int compareTo(InjectionNodes$InjectionNode var1) {
      int var10000;
      try {
         if (var1 == null) {
            var10000 = Integer.MAX_VALUE;
            return var10000;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      var10000 = this.hashCode() - var1.hashCode();
      return var10000;
   }

   public String toString() {
      return String.format("InjectionNode[%s]", Bytecode.describeNode(this.currentTarget).replaceAll("\\s+", " "));
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

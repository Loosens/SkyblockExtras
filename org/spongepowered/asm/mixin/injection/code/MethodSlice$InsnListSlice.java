package org.spongepowered.asm.mixin.injection.code;

import java.util.ListIterator;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;

final class MethodSlice$InsnListSlice extends ReadOnlyInsnList {
   private final int start;
   private final int end;

   protected MethodSlice$InsnListSlice(InsnList var1, int var2, int var3) {
      super(var1);
      this.start = var2;
      this.end = var3;
   }

   public ListIterator<AbstractInsnNode> iterator() {
      return this.iterator(0);
   }

   public ListIterator<AbstractInsnNode> iterator(int var1) {
      return new MethodSlice$InsnListSlice$SliceIterator(super.iterator(this.start + var1), this.start, this.end, this.start + var1);
   }

   public AbstractInsnNode[] toArray() {
      AbstractInsnNode[] var1 = super.toArray();
      AbstractInsnNode[] var2 = new AbstractInsnNode[this.size()];
      System.arraycopy(var1, this.start, var2, 0, var2.length);
      return var2;
   }

   public int size() {
      return this.end - this.start + 1;
   }

   public AbstractInsnNode getFirst() {
      return super.get(this.start);
   }

   public AbstractInsnNode getLast() {
      return super.get(this.end);
   }

   public AbstractInsnNode get(int var1) {
      return super.get(this.start + var1);
   }

   public boolean contains(AbstractInsnNode var1) {
      AbstractInsnNode[] var2 = this.toArray();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         AbstractInsnNode var5 = var2[var4];

         try {
            if (var5 == var1) {
               return true;
            }
         } catch (RuntimeException var6) {
            throw b(var6);
         }
      }

      return false;
   }

   public int indexOf(AbstractInsnNode param1) {
      // $FF: Couldn't be decompiled
   }

   public int realIndexOf(AbstractInsnNode var1) {
      return super.indexOf(var1);
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

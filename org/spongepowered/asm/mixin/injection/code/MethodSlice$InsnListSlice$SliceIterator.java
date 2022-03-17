package org.spongepowered.asm.mixin.injection.code;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;

class MethodSlice$InsnListSlice$SliceIterator implements ListIterator<AbstractInsnNode> {
   private final ListIterator<AbstractInsnNode> iter;
   private int start;
   private int end;
   private int index;

   public MethodSlice$InsnListSlice$SliceIterator(ListIterator<AbstractInsnNode> var1, int var2, int var3, int var4) {
      this.iter = var1;
      this.start = var2;
      this.end = var3;
      this.index = var4;
   }

   public boolean hasNext() {
      // $FF: Couldn't be decompiled
   }

   public AbstractInsnNode next() {
      try {
         if (this.index > this.end) {
            throw new NoSuchElementException();
         }
      } catch (NoSuchElementException var1) {
         throw b(var1);
      }

      ++this.index;
      return (AbstractInsnNode)this.iter.next();
   }

   public boolean hasPrevious() {
      boolean var10000;
      try {
         if (this.index > this.start) {
            var10000 = true;
            return var10000;
         }
      } catch (NoSuchElementException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public AbstractInsnNode previous() {
      try {
         if (this.index <= this.start) {
            throw new NoSuchElementException();
         }
      } catch (NoSuchElementException var1) {
         throw b(var1);
      }

      --this.index;
      return (AbstractInsnNode)this.iter.previous();
   }

   public int nextIndex() {
      return this.index - this.start;
   }

   public int previousIndex() {
      return this.index - this.start - 1;
   }

   public void remove() {
      throw new UnsupportedOperationException("Cannot remove insn from slice");
   }

   public void set(AbstractInsnNode var1) {
      throw new UnsupportedOperationException("Cannot set insn using slice");
   }

   public void add(AbstractInsnNode var1) {
      throw new UnsupportedOperationException("Cannot add insn using slice");
   }

   private static NoSuchElementException b(NoSuchElementException var0) {
      return var0;
   }
}

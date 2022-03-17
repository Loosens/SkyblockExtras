package org.spongepowered.asm.lib.tree;

import java.util.ListIterator;
import java.util.NoSuchElementException;

final class InsnList$InsnListIterator implements ListIterator {
   AbstractInsnNode next;
   AbstractInsnNode prev;
   AbstractInsnNode remove;
   final InsnList this$0;

   InsnList$InsnListIterator(InsnList var1, int var2) {
      this.this$0 = var1;
      if (var2 == var1.size()) {
         this.next = null;
         this.prev = var1.getLast();
      } else {
         this.next = var1.get(var2);
         this.prev = this.next.prev;
      }

   }

   public boolean hasNext() {
      boolean var10000;
      try {
         if (this.next != null) {
            var10000 = true;
            return var10000;
         }
      } catch (NoSuchElementException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public Object next() {
      try {
         if (this.next == null) {
            throw new NoSuchElementException();
         }
      } catch (NoSuchElementException var2) {
         throw b(var2);
      }

      AbstractInsnNode var1 = this.next;
      this.prev = var1;
      this.next = var1.next;
      this.remove = var1;
      return var1;
   }

   public void remove() {
      // $FF: Couldn't be decompiled
   }

   public boolean hasPrevious() {
      boolean var10000;
      try {
         if (this.prev != null) {
            var10000 = true;
            return var10000;
         }
      } catch (NoSuchElementException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public Object previous() {
      AbstractInsnNode var1 = this.prev;
      this.next = var1;
      this.prev = var1.prev;
      this.remove = var1;
      return var1;
   }

   public int nextIndex() {
      try {
         if (this.next == null) {
            return this.this$0.size();
         }
      } catch (NoSuchElementException var1) {
         throw b(var1);
      }

      try {
         if (this.this$0.cache == null) {
            this.this$0.cache = this.this$0.toArray();
         }
      } catch (NoSuchElementException var2) {
         throw b(var2);
      }

      return this.next.index;
   }

   public int previousIndex() {
      try {
         if (this.prev == null) {
            return -1;
         }
      } catch (NoSuchElementException var1) {
         throw b(var1);
      }

      try {
         if (this.this$0.cache == null) {
            this.this$0.cache = this.this$0.toArray();
         }
      } catch (NoSuchElementException var2) {
         throw b(var2);
      }

      return this.prev.index;
   }

   public void add(Object var1) {
      label28: {
         try {
            if (this.next != null) {
               this.this$0.insertBefore(this.next, (AbstractInsnNode)var1);
               break label28;
            }
         } catch (NoSuchElementException var3) {
            throw b(var3);
         }

         try {
            if (this.prev != null) {
               this.this$0.insert(this.prev, (AbstractInsnNode)var1);
               break label28;
            }
         } catch (NoSuchElementException var2) {
            throw b(var2);
         }

         this.this$0.add((AbstractInsnNode)var1);
      }

      this.prev = (AbstractInsnNode)var1;
      this.remove = null;
   }

   public void set(Object param1) {
      // $FF: Couldn't be decompiled
   }

   private static NoSuchElementException b(NoSuchElementException var0) {
      return var0;
   }
}

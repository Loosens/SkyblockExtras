package org.spongepowered.asm.lib.tree;

import java.util.ListIterator;
import org.spongepowered.asm.lib.MethodVisitor;

public class InsnList {
   private int size;
   private AbstractInsnNode first;
   private AbstractInsnNode last;
   AbstractInsnNode[] cache;

   public int size() {
      return this.size;
   }

   public AbstractInsnNode getFirst() {
      return this.first;
   }

   public AbstractInsnNode getLast() {
      return this.last;
   }

   public AbstractInsnNode get(int param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean contains(AbstractInsnNode var1) {
      AbstractInsnNode var2 = this.first;

      while(true) {
         try {
            if (var2 == null || var2 == var1) {
               break;
            }
         } catch (IndexOutOfBoundsException var4) {
            throw b(var4);
         }

         var2 = var2.next;
      }

      boolean var10000;
      try {
         if (var2 != null) {
            var10000 = true;
            return var10000;
         }
      } catch (IndexOutOfBoundsException var3) {
         throw b(var3);
      }

      var10000 = false;
      return var10000;
   }

   public int indexOf(AbstractInsnNode var1) {
      try {
         if (this.cache == null) {
            this.cache = this.toArray();
         }
      } catch (IndexOutOfBoundsException var2) {
         throw b(var2);
      }

      return var1.index;
   }

   public void accept(MethodVisitor var1) {
      for(AbstractInsnNode var2 = this.first; var2 != null; var2 = var2.next) {
         var2.accept(var1);
      }

   }

   public ListIterator<AbstractInsnNode> iterator() {
      return this.iterator(0);
   }

   public ListIterator<AbstractInsnNode> iterator(int var1) {
      return new InsnList$InsnListIterator(this, var1);
   }

   public AbstractInsnNode[] toArray() {
      int var1 = 0;
      AbstractInsnNode var2 = this.first;

      AbstractInsnNode[] var3;
      for(var3 = new AbstractInsnNode[this.size]; var2 != null; var2 = var2.next) {
         var3[var1] = var2;
         var2.index = var1++;
      }

      return var3;
   }

   public void set(AbstractInsnNode var1, AbstractInsnNode var2) {
      AbstractInsnNode var3 = var1.next;

      label36: {
         try {
            var2.next = var3;
            if (var3 != null) {
               var3.prev = var2;
               break label36;
            }
         } catch (IndexOutOfBoundsException var7) {
            throw b(var7);
         }

         this.last = var2;
      }

      AbstractInsnNode var4 = var1.prev;

      label28: {
         try {
            var2.prev = var4;
            if (var4 != null) {
               var4.next = var2;
               break label28;
            }
         } catch (IndexOutOfBoundsException var6) {
            throw b(var6);
         }

         this.first = var2;
      }

      if (this.cache != null) {
         int var5 = var1.index;
         this.cache[var5] = var2;
         var2.index = var5;
      } else {
         var2.index = 0;
      }

      var1.index = -1;
      var1.prev = null;
      var1.next = null;
   }

   public void add(AbstractInsnNode var1) {
      label16: {
         try {
            ++this.size;
            if (this.last == null) {
               this.first = var1;
               this.last = var1;
               break label16;
            }
         } catch (IndexOutOfBoundsException var2) {
            throw b(var2);
         }

         this.last.next = var1;
         var1.prev = this.last;
      }

      this.last = var1;
      this.cache = null;
      var1.index = 0;
   }

   public void add(InsnList var1) {
      try {
         if (var1.size == 0) {
            return;
         }
      } catch (IndexOutOfBoundsException var3) {
         throw b(var3);
      }

      label22: {
         try {
            this.size += var1.size;
            if (this.last == null) {
               this.first = var1.first;
               this.last = var1.last;
               break label22;
            }
         } catch (IndexOutOfBoundsException var4) {
            throw b(var4);
         }

         AbstractInsnNode var2 = var1.first;
         this.last.next = var2;
         var2.prev = this.last;
         this.last = var1.last;
      }

      this.cache = null;
      var1.removeAll(false);
   }

   public void insert(AbstractInsnNode var1) {
      label16: {
         try {
            ++this.size;
            if (this.first == null) {
               this.first = var1;
               this.last = var1;
               break label16;
            }
         } catch (IndexOutOfBoundsException var2) {
            throw b(var2);
         }

         this.first.prev = var1;
         var1.next = this.first;
      }

      this.first = var1;
      this.cache = null;
      var1.index = 0;
   }

   public void insert(InsnList var1) {
      try {
         if (var1.size == 0) {
            return;
         }
      } catch (IndexOutOfBoundsException var3) {
         throw b(var3);
      }

      label22: {
         try {
            this.size += var1.size;
            if (this.first == null) {
               this.first = var1.first;
               this.last = var1.last;
               break label22;
            }
         } catch (IndexOutOfBoundsException var4) {
            throw b(var4);
         }

         AbstractInsnNode var2 = var1.last;
         this.first.prev = var2;
         var2.next = this.first;
         this.first = var1.first;
      }

      this.cache = null;
      var1.removeAll(false);
   }

   public void insert(AbstractInsnNode var1, AbstractInsnNode var2) {
      ++this.size;
      AbstractInsnNode var3 = var1.next;

      label17: {
         try {
            if (var3 == null) {
               this.last = var2;
               break label17;
            }
         } catch (IndexOutOfBoundsException var4) {
            throw b(var4);
         }

         var3.prev = var2;
      }

      var1.next = var2;
      var2.next = var3;
      var2.prev = var1;
      this.cache = null;
      var2.index = 0;
   }

   public void insert(AbstractInsnNode var1, InsnList var2) {
      try {
         if (var2.size == 0) {
            return;
         }
      } catch (IndexOutOfBoundsException var7) {
         throw b(var7);
      }

      this.size += var2.size;
      AbstractInsnNode var3 = var2.first;
      AbstractInsnNode var4 = var2.last;
      AbstractInsnNode var5 = var1.next;

      label21: {
         try {
            if (var5 == null) {
               this.last = var4;
               break label21;
            }
         } catch (IndexOutOfBoundsException var6) {
            throw b(var6);
         }

         var5.prev = var4;
      }

      var1.next = var3;
      var4.next = var5;
      var3.prev = var1;
      this.cache = null;
      var2.removeAll(false);
   }

   public void insertBefore(AbstractInsnNode var1, AbstractInsnNode var2) {
      ++this.size;
      AbstractInsnNode var3 = var1.prev;

      label17: {
         try {
            if (var3 == null) {
               this.first = var2;
               break label17;
            }
         } catch (IndexOutOfBoundsException var4) {
            throw b(var4);
         }

         var3.next = var2;
      }

      var1.prev = var2;
      var2.next = var1;
      var2.prev = var3;
      this.cache = null;
      var2.index = 0;
   }

   public void insertBefore(AbstractInsnNode var1, InsnList var2) {
      try {
         if (var2.size == 0) {
            return;
         }
      } catch (IndexOutOfBoundsException var7) {
         throw b(var7);
      }

      this.size += var2.size;
      AbstractInsnNode var3 = var2.first;
      AbstractInsnNode var4 = var2.last;
      AbstractInsnNode var5 = var1.prev;

      label21: {
         try {
            if (var5 == null) {
               this.first = var3;
               break label21;
            }
         } catch (IndexOutOfBoundsException var6) {
            throw b(var6);
         }

         var5.next = var3;
      }

      var1.prev = var4;
      var4.next = var1;
      var3.prev = var5;
      this.cache = null;
      var2.removeAll(false);
   }

   public void remove(AbstractInsnNode param1) {
      // $FF: Couldn't be decompiled
   }

   void removeAll(boolean var1) {
      AbstractInsnNode var3;
      if (var1) {
         for(AbstractInsnNode var2 = this.first; var2 != null; var2 = var3) {
            var3 = var2.next;
            var2.index = -1;
            var2.prev = null;
            var2.next = null;
         }
      }

      this.size = 0;
      this.first = null;
      this.last = null;
      this.cache = null;
   }

   public void clear() {
      this.removeAll(false);
   }

   public void resetLabels() {
      // $FF: Couldn't be decompiled
   }

   private static IndexOutOfBoundsException b(IndexOutOfBoundsException var0) {
      return var0;
   }
}

package org.spongepowered.asm.lib.tree.analysis;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

class SmallSet<E> extends AbstractSet<E> implements Iterator<E> {
   E e1;
   E e2;

   static final <T> Set<T> emptySet() {
      return new SmallSet((Object)null, (Object)null);
   }

   SmallSet(E var1, E var2) {
      this.e1 = var1;
      this.e2 = var2;
   }

   public Iterator<E> iterator() {
      return new SmallSet(this.e1, this.e2);
   }

   public int size() {
      byte var10000;
      try {
         if (this.e1 == null) {
            var10000 = 0;
            return var10000;
         }
      } catch (NoSuchElementException var2) {
         throw b(var2);
      }

      try {
         if (this.e2 == null) {
            var10000 = 1;
            return var10000;
         }
      } catch (NoSuchElementException var1) {
         throw b(var1);
      }

      var10000 = 2;
      return var10000;
   }

   public boolean hasNext() {
      boolean var10000;
      try {
         if (this.e1 != null) {
            var10000 = true;
            return var10000;
         }
      } catch (NoSuchElementException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public E next() {
      try {
         if (this.e1 == null) {
            throw new NoSuchElementException();
         }
      } catch (NoSuchElementException var2) {
         throw b(var2);
      }

      Object var1 = this.e1;
      this.e1 = this.e2;
      this.e2 = null;
      return var1;
   }

   public void remove() {
   }

   Set<E> union(SmallSet<E> param1) {
      // $FF: Couldn't be decompiled
   }

   private static NoSuchElementException b(NoSuchElementException var0) {
      return var0;
   }
}

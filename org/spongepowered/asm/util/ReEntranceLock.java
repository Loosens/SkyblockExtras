package org.spongepowered.asm.util;

public class ReEntranceLock {
   private final int maxDepth;
   private int depth = 0;
   private boolean semaphore = false;

   public ReEntranceLock(int var1) {
      this.maxDepth = var1;
   }

   public int getMaxDepth() {
      return this.maxDepth;
   }

   public int getDepth() {
      return this.depth;
   }

   public ReEntranceLock push() {
      ++this.depth;
      this.checkAndSet();
      return this;
   }

   public ReEntranceLock pop() {
      try {
         if (this.depth == 0) {
            throw new IllegalStateException("ReEntranceLock pop() with zero depth");
         }
      } catch (IllegalStateException var1) {
         throw b(var1);
      }

      --this.depth;
      return this;
   }

   public boolean check() {
      boolean var10000;
      try {
         if (this.depth > this.maxDepth) {
            var10000 = true;
            return var10000;
         }
      } catch (IllegalStateException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public boolean checkAndSet() {
      return this.semaphore |= this.check();
   }

   public ReEntranceLock set() {
      this.semaphore = true;
      return this;
   }

   public boolean isSet() {
      return this.semaphore;
   }

   public ReEntranceLock clear() {
      this.semaphore = false;
      return this;
   }

   private static IllegalStateException b(IllegalStateException var0) {
      return var0;
   }
}

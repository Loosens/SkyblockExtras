package org.spongepowered.asm.util.perf;

import java.util.Arrays;

class Profiler$LiveSection extends Profiler$Section {
   private int cursor;
   private long[] times;
   private long start;
   private long time;
   private long markedTime;
   private int count;
   private int markedCount;
   final Profiler this$0;

   Profiler$LiveSection(Profiler var1, String var2, int var3) {
      super(var1, var2);
      this.this$0 = var1;
      this.cursor = 0;
      this.times = new long[0];
      this.start = 0L;
      this.cursor = var3;
   }

   Profiler$Section start() {
      this.start = System.currentTimeMillis();
      return this;
   }

   protected Profiler$Section stop() {
      try {
         if (this.start > 0L) {
            this.time += System.currentTimeMillis() - this.start;
         }
      } catch (RuntimeException var1) {
         throw c(var1);
      }

      this.start = 0L;
      ++this.count;
      return this;
   }

   public Profiler$Section end() {
      try {
         this.stop();
         if (!this.invalidated) {
            this.this$0.end(this);
         }

         return this;
      } catch (RuntimeException var1) {
         throw c(var1);
      }
   }

   void mark() {
      try {
         if (this.cursor >= this.times.length) {
            this.times = Arrays.copyOf(this.times, this.cursor + 4);
         }
      } catch (RuntimeException var1) {
         throw c(var1);
      }

      this.times[this.cursor] = this.time;
      this.markedTime += this.time;
      this.markedCount += this.count;
      this.time = 0L;
      this.count = 0;
      ++this.cursor;
   }

   public long getTime() {
      return this.time;
   }

   public long getTotalTime() {
      return this.time + this.markedTime;
   }

   public double getSeconds() {
      return (double)this.time * 0.001D;
   }

   public double getTotalSeconds() {
      return (double)(this.time + this.markedTime) * 0.001D;
   }

   public long[] getTimes() {
      long[] var1 = new long[this.cursor + 1];
      System.arraycopy(this.times, 0, var1, 0, Math.min(this.times.length, this.cursor));
      var1[this.cursor] = this.time;
      return var1;
   }

   public int getCount() {
      return this.count;
   }

   public int getTotalCount() {
      return this.count + this.markedCount;
   }

   public double getAverageTime() {
      double var10000;
      try {
         if (this.count > 0) {
            var10000 = (double)this.time / (double)this.count;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw c(var1);
      }

      var10000 = 0.0D;
      return var10000;
   }

   public double getTotalAverageTime() {
      double var10000;
      try {
         if (this.count > 0) {
            var10000 = (double)(this.time + this.markedTime) / (double)(this.count + this.markedCount);
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw c(var1);
      }

      var10000 = 0.0D;
      return var10000;
   }

   private static RuntimeException c(RuntimeException var0) {
      return var0;
   }
}

package org.spongepowered.asm.util.perf;

public class Profiler$Section {
   static final String SEPARATOR_ROOT = " -> ";
   static final String SEPARATOR_CHILD = ".";
   private final String name;
   private boolean root;
   private boolean fine;
   protected boolean invalidated;
   private String info;
   final Profiler this$0;

   Profiler$Section(Profiler var1, String var2) {
      this.this$0 = var1;
      this.name = var2;
      this.info = var2;
   }

   Profiler$Section getDelegate() {
      return this;
   }

   Profiler$Section invalidate() {
      this.invalidated = true;
      return this;
   }

   Profiler$Section setRoot(boolean var1) {
      this.root = var1;
      return this;
   }

   public boolean isRoot() {
      return this.root;
   }

   Profiler$Section setFine(boolean var1) {
      this.fine = var1;
      return this;
   }

   public boolean isFine() {
      return this.fine;
   }

   public String getName() {
      return this.name;
   }

   public String getBaseName() {
      return this.name;
   }

   public void setInfo(String var1) {
      this.info = var1;
   }

   public String getInfo() {
      return this.info;
   }

   Profiler$Section start() {
      return this;
   }

   protected Profiler$Section stop() {
      return this;
   }

   public Profiler$Section end() {
      try {
         if (!this.invalidated) {
            this.this$0.end(this);
         }

         return this;
      } catch (RuntimeException var1) {
         throw b(var1);
      }
   }

   public Profiler$Section next(String var1) {
      this.end();
      return this.this$0.begin(var1);
   }

   void mark() {
   }

   public long getTime() {
      return 0L;
   }

   public long getTotalTime() {
      return 0L;
   }

   public double getSeconds() {
      return 0.0D;
   }

   public double getTotalSeconds() {
      return 0.0D;
   }

   public long[] getTimes() {
      return new long[1];
   }

   public int getCount() {
      return 0;
   }

   public int getTotalCount() {
      return 0;
   }

   public double getAverageTime() {
      return 0.0D;
   }

   public double getTotalAverageTime() {
      return 0.0D;
   }

   public final String toString() {
      return this.name;
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

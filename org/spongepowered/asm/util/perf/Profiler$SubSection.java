package org.spongepowered.asm.util.perf;

class Profiler$SubSection extends Profiler$LiveSection {
   private final String baseName;
   private final Profiler$Section root;
   final Profiler this$0;

   Profiler$SubSection(Profiler var1, String var2, int var3, String var4, Profiler$Section var5) {
      super(var1, var2, var3);
      this.this$0 = var1;
      this.baseName = var4;
      this.root = var5;
   }

   Profiler$Section invalidate() {
      this.root.invalidate();
      return super.invalidate();
   }

   public String getBaseName() {
      return this.baseName;
   }

   public void setInfo(String var1) {
      this.root.setInfo(var1);
      super.setInfo(var1);
   }

   Profiler$Section getDelegate() {
      return this.root;
   }

   Profiler$Section start() {
      this.root.start();
      return super.start();
   }

   public Profiler$Section end() {
      this.root.stop();
      return super.end();
   }

   public Profiler$Section next(String var1) {
      super.stop();
      return this.root.next(var1);
   }
}

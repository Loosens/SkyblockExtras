package org.spongepowered.asm.mixin.transformer;

class MixinApplicatorStandard$Range {
   final int start;
   final int end;
   final int marker;
   final MixinApplicatorStandard this$0;

   MixinApplicatorStandard$Range(MixinApplicatorStandard var1, int var2, int var3, int var4) {
      this.this$0 = var1;
      this.start = var2;
      this.end = var3;
      this.marker = var4;
   }

   boolean isValid() {
      // $FF: Couldn't be decompiled
   }

   boolean contains(int param1) {
      // $FF: Couldn't be decompiled
   }

   boolean excludes(int param1) {
      // $FF: Couldn't be decompiled
   }

   public String toString() {
      return String.format("Range[%d-%d,%d,valid=%s)", this.start, this.end, this.marker, this.isValid());
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

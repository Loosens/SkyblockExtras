package org.spongepowered.asm.mixin;

public enum MixinEnvironment$CompatibilityLevel {
   JAVA_6(6, 50, false),
   JAVA_7(7, 51, false),
   JAVA_8(8, 52, true),
   JAVA_9(9, 53, true);

   private static final int CLASS_V1_9 = 53;
   private final int ver;
   private final int classVersion;
   private final boolean supportsMethodsInInterfaces;
   private MixinEnvironment$CompatibilityLevel maxCompatibleLevel;
   private static final MixinEnvironment$CompatibilityLevel[] $VALUES = new MixinEnvironment$CompatibilityLevel[]{JAVA_6, JAVA_7, JAVA_8, JAVA_9};

   private MixinEnvironment$CompatibilityLevel(int var3, int var4, boolean var5) {
      this.ver = var3;
      this.classVersion = var4;
      this.supportsMethodsInInterfaces = var5;
   }

   private void setMaxCompatibleLevel(MixinEnvironment$CompatibilityLevel var1) {
      this.maxCompatibleLevel = var1;
   }

   boolean isSupported() {
      return true;
   }

   public int classVersion() {
      return this.classVersion;
   }

   public boolean supportsMethodsInInterfaces() {
      return this.supportsMethodsInInterfaces;
   }

   public boolean isAtLeast(MixinEnvironment$CompatibilityLevel param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean canElevateTo(MixinEnvironment$CompatibilityLevel param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean canSupport(MixinEnvironment$CompatibilityLevel var1) {
      try {
         if (var1 == null) {
            return true;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      return var1.canElevateTo(this);
   }

   MixinEnvironment$CompatibilityLevel(int var3, int var4, boolean var5, MixinEnvironment$1 var6) {
      this(var3, var4, var5);
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

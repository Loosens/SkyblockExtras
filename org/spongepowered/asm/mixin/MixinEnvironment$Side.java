package org.spongepowered.asm.mixin;

public enum MixinEnvironment$Side {
   UNKNOWN,
   CLIENT,
   SERVER;

   private static final MixinEnvironment$Side[] $VALUES = new MixinEnvironment$Side[]{UNKNOWN, CLIENT, SERVER};

   private MixinEnvironment$Side() {
   }

   protected abstract boolean detect();

   MixinEnvironment$Side(MixinEnvironment$1 var3) {
      this();
   }
}

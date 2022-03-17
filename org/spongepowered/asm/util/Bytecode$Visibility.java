package org.spongepowered.asm.util;

public enum Bytecode$Visibility {
   PRIVATE(2),
   PROTECTED(4),
   PACKAGE(0),
   PUBLIC(1);

   static final int MASK = 7;
   final int access;
   private static final Bytecode$Visibility[] $VALUES = new Bytecode$Visibility[]{PRIVATE, PROTECTED, PACKAGE, PUBLIC};

   private Bytecode$Visibility(int var3) {
      this.access = var3;
   }
}

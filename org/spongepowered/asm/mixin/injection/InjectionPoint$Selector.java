package org.spongepowered.asm.mixin.injection;

public enum InjectionPoint$Selector {
   FIRST,
   LAST,
   ONE;

   public static final InjectionPoint$Selector DEFAULT = FIRST;
   private static final InjectionPoint$Selector[] $VALUES = new InjectionPoint$Selector[]{FIRST, LAST, ONE};
}

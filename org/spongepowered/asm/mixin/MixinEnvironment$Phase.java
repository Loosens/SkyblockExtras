package org.spongepowered.asm.mixin;

import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import java.util.List;

public final class MixinEnvironment$Phase {
   static final MixinEnvironment$Phase NOT_INITIALISED = new MixinEnvironment$Phase(-1, "NOT_INITIALISED");
   public static final MixinEnvironment$Phase PREINIT = new MixinEnvironment$Phase(0, "PREINIT");
   public static final MixinEnvironment$Phase INIT = new MixinEnvironment$Phase(1, "INIT");
   public static final MixinEnvironment$Phase DEFAULT = new MixinEnvironment$Phase(2, "DEFAULT");
   static final List<MixinEnvironment$Phase> phases;
   final int ordinal;
   final String name;
   private MixinEnvironment environment;

   private MixinEnvironment$Phase(int var1, String var2) {
      this.ordinal = var1;
      this.name = var2;
   }

   public String toString() {
      return this.name;
   }

   public static MixinEnvironment$Phase forName(String var0) {
      Iterator var1 = phases.iterator();

      while(var1.hasNext()) {
         MixinEnvironment$Phase var2 = (MixinEnvironment$Phase)var1.next();

         try {
            if (var2.name.equals(var0)) {
               return var2;
            }
         } catch (IllegalArgumentException var3) {
            throw b(var3);
         }
      }

      return null;
   }

   MixinEnvironment getEnvironment() {
      try {
         if (this.ordinal < 0) {
            throw new IllegalArgumentException("Cannot access the NOT_INITIALISED environment");
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      try {
         if (this.environment == null) {
            this.environment = new MixinEnvironment(this);
         }
      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }

      return this.environment;
   }

   static {
      phases = ImmutableList.of(PREINIT, INIT, DEFAULT);
   }

   private static IllegalArgumentException b(IllegalArgumentException var0) {
      return var0;
   }
}

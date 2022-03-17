package org.spongepowered.asm.mixin.extensibility;

import org.apache.logging.log4j.Level;

public enum IMixinErrorHandler$ErrorAction {
   NONE(Level.INFO),
   WARN(Level.WARN),
   ERROR(Level.FATAL);

   public final Level logLevel;
   private static final IMixinErrorHandler$ErrorAction[] $VALUES = new IMixinErrorHandler$ErrorAction[]{NONE, WARN, ERROR};

   private IMixinErrorHandler$ErrorAction(Level var3) {
      this.logLevel = var3;
   }
}

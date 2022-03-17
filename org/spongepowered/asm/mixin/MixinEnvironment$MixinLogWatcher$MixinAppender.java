package org.spongepowered.asm.mixin;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;

class MixinEnvironment$MixinLogWatcher$MixinAppender extends AbstractAppender {
   MixinEnvironment$MixinLogWatcher$MixinAppender() {
      super("MixinLogWatcherAppender", (Filter)null, (Layout)null);
   }

   public void append(LogEvent param1) {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

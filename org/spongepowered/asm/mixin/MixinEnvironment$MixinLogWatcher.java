package org.spongepowered.asm.mixin;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

class MixinEnvironment$MixinLogWatcher {
   static MixinEnvironment$MixinLogWatcher$MixinAppender appender = new MixinEnvironment$MixinLogWatcher$MixinAppender();
   static Logger log;
   static Level oldLevel = null;

   static void begin() {
      org.apache.logging.log4j.Logger var0 = LogManager.getLogger("FML");

      try {
         if (!(var0 instanceof Logger)) {
            return;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      log = (Logger)var0;
      oldLevel = log.getLevel();
      appender.start();
      log.addAppender(appender);
      log.setLevel(Level.ALL);
   }

   static void end() {
      try {
         if (log != null) {
            log.removeAppender(appender);
         }

      } catch (RuntimeException var0) {
         throw b(var0);
      }
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

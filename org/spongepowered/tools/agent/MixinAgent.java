package org.spongepowered.tools.agent;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.transformer.MixinTransformer;
import org.spongepowered.asm.mixin.transformer.ext.IHotSwap;

public class MixinAgent implements IHotSwap {
   public static final byte[] ERROR_BYTECODE = new byte[]{1};
   static final MixinAgentClassLoader classLoader = new MixinAgentClassLoader();
   static final Logger logger = LogManager.getLogger("mixin.agent");
   static Instrumentation instrumentation = null;
   private static List<MixinAgent> agents = new ArrayList();
   final MixinTransformer classTransformer;

   public MixinAgent(MixinTransformer var1) {
      this.classTransformer = var1;
      agents.add(this);
      if (instrumentation != null) {
         this.initTransformer();
      }

   }

   private void initTransformer() {
      instrumentation.addTransformer(new MixinAgent$Transformer(this), true);
   }

   public void registerMixinClass(String var1) {
      classLoader.addMixinClass(var1);
   }

   public void registerTargetClass(String var1, byte[] var2) {
      classLoader.addTargetClass(var1, var2);
   }

   public static void init(Instrumentation var0) {
      try {
         instrumentation = var0;
         if (!instrumentation.isRedefineClassesSupported()) {
            logger.error("The instrumentation doesn't support re-definition of classes");
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      Iterator var1 = agents.iterator();

      while(var1.hasNext()) {
         MixinAgent var2 = (MixinAgent)var1.next();
         var2.initTransformer();
      }

   }

   public static void premain(String var0, Instrumentation var1) {
      System.setProperty("mixin.hotSwap", "true");
      init(var1);
   }

   public static void agentmain(String var0, Instrumentation var1) {
      init(var1);
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

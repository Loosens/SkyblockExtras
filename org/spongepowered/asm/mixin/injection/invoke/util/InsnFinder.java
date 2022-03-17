package org.spongepowered.asm.mixin.injection.invoke.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.analysis.AnalyzerException;
import org.spongepowered.asm.mixin.injection.struct.Target;

public class InsnFinder {
   private static final Logger logger = LogManager.getLogger("mixin");

   public AbstractInsnNode findPopInsn(Target var1, AbstractInsnNode var2) {
      try {
         (new InsnFinder$PopAnalyzer(var2)).analyze(var1.classNode.name, var1.method);
      } catch (AnalyzerException var5) {
         AnalyzerException var3 = var5;

         try {
            if (var3.getCause() instanceof InsnFinder$AnalysisResultException) {
               return ((InsnFinder$AnalysisResultException)var3.getCause()).getResult();
            }
         } catch (AnalyzerException var4) {
            throw b(var4);
         }

         logger.catching(var5);
      }

      return null;
   }

   private static AnalyzerException b(AnalyzerException var0) {
      return var0;
   }
}

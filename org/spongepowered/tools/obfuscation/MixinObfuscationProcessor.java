package org.spongepowered.tools.obfuscation;

import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;

public abstract class MixinObfuscationProcessor extends AbstractProcessor {
   protected AnnotatedMixins mixins;

   public synchronized void init(ProcessingEnvironment var1) {
      super.init(var1);
      this.mixins = AnnotatedMixins.getMixinsForEnvironment(var1);
   }

   protected void processMixins(RoundEnvironment param1) {
      // $FF: Couldn't be decompiled
   }

   protected void postProcess(RoundEnvironment var1) {
      this.mixins.onPassCompleted(var1);
   }

   public SourceVersion getSupportedSourceVersion() {
      try {
         return SourceVersion.valueOf("RELEASE_8");
      } catch (IllegalArgumentException var2) {
         return super.getSupportedSourceVersion();
      }
   }

   public Set<String> getSupportedOptions() {
      return SupportedOptions.getAllOptions();
   }

   private static IllegalArgumentException b(IllegalArgumentException var0) {
      return var0;
   }
}

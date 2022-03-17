package org.spongepowered.tools.agent;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.mixin.transformer.throwables.MixinReloadException;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.MixinService;

class MixinAgent$Transformer implements ClassFileTransformer {
   final MixinAgent this$0;

   MixinAgent$Transformer(MixinAgent var1) {
      this.this$0 = var1;
   }

   public byte[] transform(ClassLoader param1, String param2, Class<?> param3, ProtectionDomain param4, byte[] param5) throws IllegalClassFormatException {
      // $FF: Couldn't be decompiled
   }

   private List<String> reloadMixin(String var1, byte[] var2) {
      MixinAgent.logger.info("Redefining mixin {}", new Object[]{var1});

      try {
         return this.this$0.classTransformer.reload(var1.replace('/', '.'), var2);
      } catch (MixinReloadException var4) {
         MixinAgent.logger.error("Mixin {} cannot be reloaded, needs a restart to be applied: {} ", new Object[]{var4.getMixinInfo(), var4.getMessage()});
      } catch (Throwable var5) {
         MixinAgent.logger.error("Error while finding targets for mixin " + var1, var5);
      }

      return null;
   }

   private boolean reApplyMixins(List<String> var1) {
      IMixinService var2 = MixinService.getService();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         String var5 = var4.replace('/', '.');
         MixinAgent.logger.debug("Re-transforming target class {}", new Object[]{var4});

         try {
            Class var6 = var2.getClassProvider().findClass(var5);
            byte[] var7 = MixinAgent.classLoader.getOriginalTargetBytecode(var5);
            if (var7 == null) {
               MixinAgent.logger.error("Target class {} bytecode is not registered", new Object[]{var5});
               return false;
            }

            var7 = this.this$0.classTransformer.transformClassBytes((String)null, var5, var7);
            MixinAgent.instrumentation.redefineClasses(new ClassDefinition[]{new ClassDefinition(var6, var7)});
         } catch (Throwable var8) {
            MixinAgent.logger.error("Error while re-transforming target class " + var4, var8);
            return false;
         }
      }

      return true;
   }

   private static Throwable b(Throwable var0) {
      return var0;
   }
}

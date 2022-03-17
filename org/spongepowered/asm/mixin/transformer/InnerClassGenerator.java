package org.spongepowered.asm.mixin.transformer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.transformers.MixinClassWriter;

final class InnerClassGenerator implements IClassGenerator {
   private static final Logger logger = LogManager.getLogger("mixin");
   private final Map<String, String> innerClassNames = new HashMap();
   private final Map<String, InnerClassGenerator$InnerClassInfo> innerClasses = new HashMap();

   public String registerInnerClass(MixinInfo var1, String var2, MixinTargetContext var3) {
      String var4 = String.format("%s%s", var2, var3);
      String var5 = (String)this.innerClassNames.get(var4);
      if (var5 == null) {
         var5 = getUniqueReference(var2, var3);
         this.innerClassNames.put(var4, var5);
         this.innerClasses.put(var5, new InnerClassGenerator$InnerClassInfo(var5, var2, var1, var3));
         logger.debug("Inner class {} in {} on {} gets unique name {}", new Object[]{var2, var1.getClassRef(), var3.getTargetClassRef(), var5});
      }

      return var5;
   }

   public byte[] generate(String var1) {
      String var2 = var1.replace('.', '/');
      InnerClassGenerator$InnerClassInfo var3 = (InnerClassGenerator$InnerClassInfo)this.innerClasses.get(var2);

      try {
         return var3 != null ? this.generate(var3) : null;
      } catch (InvalidMixinException var4) {
         throw b(var4);
      }
   }

   private byte[] generate(InnerClassGenerator$InnerClassInfo var1) {
      try {
         logger.debug("Generating mapped inner class {} (originally {})", new Object[]{var1.getName(), var1.getOriginalName()});
         ClassReader var2 = new ClassReader(var1.getClassBytes());
         MixinClassWriter var3 = new MixinClassWriter(var2, 0);
         var2.accept(new InnerClassGenerator$InnerClassAdapter(var3, var1), 8);
         return var3.toByteArray();
      } catch (InvalidMixinException var4) {
         throw var4;
      } catch (Exception var5) {
         logger.catching(var5);
         return null;
      }
   }

   private static String getUniqueReference(String var0, MixinTargetContext var1) {
      String var2 = var0.substring(var0.lastIndexOf(36) + 1);
      if (var2.matches("^[0-9]+$")) {
         var2 = "Anonymous";
      }

      return String.format("%s$%s$%s", var1.getTargetClassRef(), var2, UUID.randomUUID().toString().replace("-", ""));
   }

   private static InvalidMixinException b(InvalidMixinException var0) {
      return var0;
   }
}

package org.spongepowered.tools.obfuscation;

import javax.lang.model.element.Element;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;

class AnnotatedMixinElementHandler$ShadowElementName extends AnnotatedMixinElementHandler$AliasedElementName {
   private final boolean hasPrefix;
   private final String prefix;
   private final String baseName;
   private String obfuscated;

   AnnotatedMixinElementHandler$ShadowElementName(Element var1, AnnotationHandle var2) {
      super(var1, var2);
      this.prefix = (String)var2.getValue("prefix", "shadow$");
      boolean var3 = false;
      String var4 = this.originalName;
      if (var4.startsWith(this.prefix)) {
         var3 = true;
         var4 = var4.substring(this.prefix.length());
      }

      this.hasPrefix = var3;
      this.obfuscated = this.baseName = var4;
   }

   public String toString() {
      return this.baseName;
   }

   public String baseName() {
      return this.baseName;
   }

   public AnnotatedMixinElementHandler$ShadowElementName setObfuscatedName(IMapping<?> var1) {
      this.obfuscated = var1.getName();
      return this;
   }

   public AnnotatedMixinElementHandler$ShadowElementName setObfuscatedName(String var1) {
      this.obfuscated = var1;
      return this;
   }

   public boolean hasPrefix() {
      return this.hasPrefix;
   }

   public String prefix() {
      String var10000;
      try {
         if (this.hasPrefix) {
            var10000 = this.prefix;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw c(var1);
      }

      var10000 = "";
      return var10000;
   }

   public String name() {
      return this.prefix(this.baseName);
   }

   public String obfuscated() {
      return this.prefix(this.obfuscated);
   }

   public String prefix(String var1) {
      String var10000;
      try {
         if (this.hasPrefix) {
            var10000 = this.prefix + var1;
            return var10000;
         }
      } catch (RuntimeException var2) {
         throw c(var2);
      }

      var10000 = var1;
      return var10000;
   }

   private static RuntimeException c(RuntimeException var0) {
      return var0;
   }
}

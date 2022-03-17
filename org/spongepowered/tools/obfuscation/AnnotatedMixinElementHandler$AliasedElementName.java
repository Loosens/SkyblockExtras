package org.spongepowered.tools.obfuscation;

import java.util.List;
import javax.lang.model.element.Element;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;

class AnnotatedMixinElementHandler$AliasedElementName {
   protected final String originalName;
   private final List<String> aliases;
   private boolean caseSensitive;

   public AnnotatedMixinElementHandler$AliasedElementName(Element var1, AnnotationHandle var2) {
      this.originalName = var1.getSimpleName().toString();
      this.aliases = var2.getList("aliases");
   }

   public AnnotatedMixinElementHandler$AliasedElementName setCaseSensitive(boolean var1) {
      this.caseSensitive = var1;
      return this;
   }

   public boolean isCaseSensitive() {
      return this.caseSensitive;
   }

   public boolean hasAliases() {
      boolean var10000;
      try {
         if (this.aliases.size() > 0) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public List<String> getAliases() {
      return this.aliases;
   }

   public String elementName() {
      return this.originalName;
   }

   public String baseName() {
      return this.originalName;
   }

   public boolean hasPrefix() {
      return false;
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

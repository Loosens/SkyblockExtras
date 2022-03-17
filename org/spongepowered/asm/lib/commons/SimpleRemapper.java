package org.spongepowered.asm.lib.commons;

import java.util.Collections;
import java.util.Map;

public class SimpleRemapper extends Remapper {
   private final Map<String, String> mapping;

   public SimpleRemapper(Map<String, String> var1) {
      this.mapping = var1;
   }

   public SimpleRemapper(String var1, String var2) {
      this.mapping = Collections.singletonMap(var1, var2);
   }

   public String mapMethodName(String var1, String var2, String var3) {
      String var4 = this.map(var1 + '.' + var2 + var3);

      String var10000;
      try {
         if (var4 == null) {
            var10000 = var2;
            return var10000;
         }
      } catch (RuntimeException var5) {
         throw c(var5);
      }

      var10000 = var4;
      return var10000;
   }

   public String mapInvokeDynamicMethodName(String var1, String var2) {
      String var3 = this.map('.' + var1 + var2);

      String var10000;
      try {
         if (var3 == null) {
            var10000 = var1;
            return var10000;
         }
      } catch (RuntimeException var4) {
         throw c(var4);
      }

      var10000 = var3;
      return var10000;
   }

   public String mapFieldName(String var1, String var2, String var3) {
      String var4 = this.map(var1 + '.' + var2);

      String var10000;
      try {
         if (var4 == null) {
            var10000 = var2;
            return var10000;
         }
      } catch (RuntimeException var5) {
         throw c(var5);
      }

      var10000 = var4;
      return var10000;
   }

   public String map(String var1) {
      return (String)this.mapping.get(var1);
   }

   private static RuntimeException c(RuntimeException var0) {
      return var0;
   }
}

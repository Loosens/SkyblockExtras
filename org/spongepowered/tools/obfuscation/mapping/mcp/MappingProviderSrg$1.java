package org.spongepowered.tools.obfuscation.mapping.mcp;

import com.google.common.collect.BiMap;
import com.google.common.io.LineProcessor;
import java.io.File;
import java.io.IOException;

class MappingProviderSrg$1 implements LineProcessor<String> {
   final BiMap val$packageMap;
   final BiMap val$classMap;
   final BiMap val$fieldMap;
   final BiMap val$methodMap;
   final File val$input;
   final MappingProviderSrg this$0;

   MappingProviderSrg$1(MappingProviderSrg var1, BiMap var2, BiMap var3, BiMap var4, BiMap var5, File var6) {
      this.this$0 = var1;
      this.val$packageMap = var2;
      this.val$classMap = var3;
      this.val$fieldMap = var4;
      this.val$methodMap = var5;
      this.val$input = var6;
   }

   public String getResult() {
      return null;
   }

   public boolean processLine(String param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private static IOException b(IOException var0) {
      return var0;
   }
}

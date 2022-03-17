package org.spongepowered.asm.mixin.transformer.debug;

import java.io.File;
import java.io.IOException;
import org.jetbrains.java.decompiler.main.extern.IBytecodeProvider;
import org.jetbrains.java.decompiler.util.InterpreterUtil;

class RuntimeDecompiler$1 implements IBytecodeProvider {
   private byte[] byteCode;
   final RuntimeDecompiler this$0;

   RuntimeDecompiler$1(RuntimeDecompiler var1) {
      this.this$0 = var1;
   }

   public byte[] getBytecode(String var1, String var2) throws IOException {
      try {
         if (this.byteCode == null) {
            this.byteCode = InterpreterUtil.getBytes(new File(var1));
         }
      } catch (IOException var3) {
         throw b(var3);
      }

      return this.byteCode;
   }

   private static IOException b(IOException var0) {
      return var0;
   }
}

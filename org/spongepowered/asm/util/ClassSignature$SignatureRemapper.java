package org.spongepowered.asm.util;

import java.util.HashSet;
import java.util.Set;
import org.spongepowered.asm.lib.signature.SignatureWriter;

class ClassSignature$SignatureRemapper extends SignatureWriter {
   private final Set<String> localTypeVars;
   final ClassSignature this$0;

   ClassSignature$SignatureRemapper(ClassSignature var1) {
      this.this$0 = var1;
      this.localTypeVars = new HashSet();
   }

   public void visitFormalTypeParameter(String var1) {
      this.localTypeVars.add(var1);
      super.visitFormalTypeParameter(var1);
   }

   public void visitTypeVariable(String var1) {
      if (!this.localTypeVars.contains(var1)) {
         ClassSignature$TypeVar var2 = this.this$0.getTypeVar(var1);

         try {
            if (var2 != null) {
               super.visitTypeVariable(var2.toString());
               return;
            }
         } catch (RuntimeException var3) {
            throw c(var3);
         }
      }

      super.visitTypeVariable(var1);
   }

   private static RuntimeException c(RuntimeException var0) {
      return var0;
   }
}

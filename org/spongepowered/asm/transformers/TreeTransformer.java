package org.spongepowered.asm.transformers;

import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.service.ILegacyClassTransformer;

public abstract class TreeTransformer implements ILegacyClassTransformer {
   private ClassReader classReader;
   private ClassNode classNode;

   protected final ClassNode readClass(byte[] var1) {
      return this.readClass(var1, true);
   }

   protected final ClassNode readClass(byte[] var1, boolean var2) {
      ClassReader var3 = new ClassReader(var1);

      try {
         if (var2) {
            this.classReader = var3;
         }
      } catch (RuntimeException var5) {
         throw b(var5);
      }

      ClassNode var4 = new ClassNode();
      var3.accept(var4, 8);
      return var4;
   }

   protected final byte[] writeClass(ClassNode var1) {
      MixinClassWriter var2;
      label18: {
         try {
            if (this.classReader != null && this.classNode == var1) {
               break label18;
            }
         } catch (RuntimeException var3) {
            throw b(var3);
         }

         this.classNode = null;
         var2 = new MixinClassWriter(3);
         var1.accept(var2);
         return var2.toByteArray();
      }

      this.classNode = null;
      var2 = new MixinClassWriter(this.classReader, 3);
      this.classReader = null;
      var1.accept(var2);
      return var2.toByteArray();
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

package org.spongepowered.asm.util.asm;

import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.analysis.SimpleVerifier;

public class MixinVerifier extends SimpleVerifier {
   private Type currentClass;
   private Type currentSuperClass;
   private List<Type> currentClassInterfaces;
   private boolean isInterface;

   public MixinVerifier(Type var1, Type var2, List<Type> var3, boolean var4) {
      super(var1, var2, var3, var4);
      this.currentClass = var1;
      this.currentSuperClass = var2;
      this.currentClassInterfaces = var3;
      this.isInterface = var4;
   }

   protected boolean isInterface(Type param1) {
      // $FF: Couldn't be decompiled
   }

   protected Type getSuperClass(Type param1) {
      // $FF: Couldn't be decompiled
   }

   protected boolean isAssignableFrom(Type param1, Type param2) {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

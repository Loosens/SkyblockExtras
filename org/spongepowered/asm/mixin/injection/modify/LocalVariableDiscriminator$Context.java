package org.spongepowered.asm.mixin.injection.modify;

import java.util.HashMap;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.PrettyPrinter$IPrettyPrintable;

public class LocalVariableDiscriminator$Context implements PrettyPrinter$IPrettyPrintable {
   final Target target;
   final Type returnType;
   final AbstractInsnNode node;
   final int baseArgIndex;
   final LocalVariableDiscriminator$Context$Local[] locals;
   private final boolean isStatic;

   public LocalVariableDiscriminator$Context(Type var1, boolean var2, Target var3, AbstractInsnNode var4) {
      this.isStatic = Bytecode.methodIsStatic(var3.method);
      this.returnType = var1;
      this.target = var3;
      this.node = var4;
      this.baseArgIndex = this.isStatic ? 0 : 1;
      this.locals = this.initLocals(var3, var2, var4);
      this.initOrdinals();
   }

   private LocalVariableDiscriminator$Context$Local[] initLocals(Target param1, boolean param2, AbstractInsnNode param3) {
      // $FF: Couldn't be decompiled
   }

   private void initOrdinals() {
      HashMap var1 = new HashMap();

      for(int var2 = 0; var2 < this.locals.length; ++var2) {
         Integer var3 = 0;
         if (this.locals[var2] != null) {
            var3 = (Integer)var1.get(this.locals[var2].type);

            HashMap var10000;
            Type var10001;
            int var10002;
            label27: {
               try {
                  var10000 = var1;
                  var10001 = this.locals[var2].type;
                  if (var3 == null) {
                     var10002 = 0;
                     break label27;
                  }
               } catch (RuntimeException var4) {
                  throw b(var4);
               }

               var10002 = var3 + 1;
            }

            var10000.put(var10001, var3 = var10002);
            this.locals[var2].ord = var3;
         }
      }

   }

   public void print(PrettyPrinter param1) {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

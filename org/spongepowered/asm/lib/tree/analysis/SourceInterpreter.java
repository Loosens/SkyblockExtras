package org.spongepowered.asm.lib.tree.analysis;

import java.util.List;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;

public class SourceInterpreter extends Interpreter<SourceValue> implements Opcodes {
   public SourceInterpreter() {
      super(327680);
   }

   protected SourceInterpreter(int var1) {
      super(var1);
   }

   public SourceValue newValue(Type var1) {
      try {
         if (var1 == Type.VOID_TYPE) {
            return null;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      SourceValue var10000;
      SourceValue var10001;
      int var10002;
      label22: {
         try {
            var10000 = new SourceValue;
            var10001 = var10000;
            if (var1 == null) {
               var10002 = 1;
               break label22;
            }
         } catch (RuntimeException var3) {
            throw b(var3);
         }

         var10002 = var1.getSize();
      }

      var10001.<init>(var10002);
      return var10000;
   }

   public SourceValue newOperation(AbstractInsnNode param1) {
      // $FF: Couldn't be decompiled
   }

   public SourceValue copyOperation(AbstractInsnNode var1, SourceValue var2) {
      return new SourceValue(var2.getSize(), var1);
   }

   public SourceValue unaryOperation(AbstractInsnNode var1, SourceValue var2) {
      int var3;
      switch(var1.getOpcode()) {
      case 117:
      case 119:
      case 133:
      case 135:
      case 138:
      case 140:
      case 141:
      case 143:
         var3 = 2;
         break;
      case 180:
         var3 = Type.getType(((FieldInsnNode)var1).desc).getSize();
         break;
      default:
         var3 = 1;
      }

      return new SourceValue(var3, var1);
   }

   public SourceValue binaryOperation(AbstractInsnNode var1, SourceValue var2, SourceValue var3) {
      byte var4;
      switch(var1.getOpcode()) {
      case 47:
      case 49:
      case 97:
      case 99:
      case 101:
      case 103:
      case 105:
      case 107:
      case 109:
      case 111:
      case 113:
      case 115:
      case 121:
      case 123:
      case 125:
      case 127:
      case 129:
      case 131:
         var4 = 2;
         break;
      default:
         var4 = 1;
      }

      return new SourceValue(var4, var1);
   }

   public SourceValue ternaryOperation(AbstractInsnNode var1, SourceValue var2, SourceValue var3, SourceValue var4) {
      return new SourceValue(1, var1);
   }

   public SourceValue naryOperation(AbstractInsnNode var1, List<? extends SourceValue> var2) {
      int var3 = var1.getOpcode();
      int var4;
      if (var3 == 197) {
         var4 = 1;
      } else {
         String var10000;
         label19: {
            try {
               if (var3 == 186) {
                  var10000 = ((InvokeDynamicInsnNode)var1).desc;
                  break label19;
               }
            } catch (RuntimeException var6) {
               throw b(var6);
            }

            var10000 = ((MethodInsnNode)var1).desc;
         }

         String var5 = var10000;
         var4 = Type.getReturnType(var5).getSize();
      }

      return new SourceValue(var4, var1);
   }

   public void returnOperation(AbstractInsnNode var1, SourceValue var2, SourceValue var3) {
   }

   public SourceValue merge(SourceValue param1, SourceValue param2) {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

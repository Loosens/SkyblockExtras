package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;

public class AccessorGeneratorFieldSetter extends AccessorGeneratorField {
   public AccessorGeneratorFieldSetter(AccessorInfo var1) {
      super(var1);
   }

   public MethodNode generate() {
      byte var10000;
      label38: {
         try {
            if (this.isInstanceField) {
               var10000 = 1;
               break label38;
            }
         } catch (RuntimeException var8) {
            throw b(var8);
         }

         var10000 = 0;
      }

      byte var1 = var10000;
      int var2 = var1 + this.targetType.getSize();
      int var3 = var1 + this.targetType.getSize();
      MethodNode var4 = this.createMethod(var2, var3);

      try {
         if (this.isInstanceField) {
            var4.instructions.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
         }
      } catch (RuntimeException var6) {
         throw b(var6);
      }

      short var9;
      label30: {
         try {
            var4.instructions.add((AbstractInsnNode)(new VarInsnNode(this.targetType.getOpcode(21), var1)));
            if (this.isInstanceField) {
               var9 = 181;
               break label30;
            }
         } catch (RuntimeException var7) {
            throw b(var7);
         }

         var9 = 179;
      }

      short var5 = var9;
      var4.instructions.add((AbstractInsnNode)(new FieldInsnNode(var5, this.info.getClassNode().name, this.targetField.name, this.targetField.desc)));
      var4.instructions.add((AbstractInsnNode)(new InsnNode(177)));
      return var4;
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

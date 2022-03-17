package org.spongepowered.asm.util.throwables;

import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.util.Bytecode;

public enum SyntheticBridgeException$Problem {
   BAD_INSN("Conflicting opcodes %4$s and %5$s at offset %3$d in synthetic bridge method %1$s%2$s"),
   BAD_LOAD("Conflicting variable access at offset %3$d in synthetic bridge method %1$s%2$s"),
   BAD_CAST("Conflicting type cast at offset %3$d in synthetic bridge method %1$s%2$s"),
   BAD_INVOKE_NAME("Conflicting synthetic bridge target method name in synthetic bridge method %1$s%2$s Existing:%6$s Incoming:%7$s"),
   BAD_INVOKE_DESC("Conflicting synthetic bridge target method descriptor in synthetic bridge method %1$s%2$s Existing:%8$s Incoming:%9$s"),
   BAD_LENGTH("Mismatched bridge method length for synthetic bridge method %1$s%2$s unexpected extra opcode at offset %3$d");

   private final String message;
   private static final SyntheticBridgeException$Problem[] $VALUES = new SyntheticBridgeException$Problem[]{BAD_INSN, BAD_LOAD, BAD_CAST, BAD_INVOKE_NAME, BAD_INVOKE_DESC, BAD_LENGTH};

   private SyntheticBridgeException$Problem(String var3) {
      this.message = var3;
   }

   String getMessage(String var1, String var2, int var3, AbstractInsnNode var4, AbstractInsnNode var5) {
      return String.format(this.message, var1, var2, var3, Bytecode.getOpcodeName(var4), Bytecode.getOpcodeName(var4), getInsnName(var4), getInsnName(var5), getInsnDesc(var4), getInsnDesc(var5));
   }

   private static String getInsnName(AbstractInsnNode var0) {
      String var10000;
      try {
         if (var0 instanceof MethodInsnNode) {
            var10000 = ((MethodInsnNode)var0).name;
            return var10000;
         }
      } catch (SyntheticBridgeException var1) {
         throw b(var1);
      }

      var10000 = "";
      return var10000;
   }

   private static String getInsnDesc(AbstractInsnNode var0) {
      String var10000;
      try {
         if (var0 instanceof MethodInsnNode) {
            var10000 = ((MethodInsnNode)var0).desc;
            return var10000;
         }
      } catch (SyntheticBridgeException var1) {
         throw b(var1);
      }

      var10000 = "";
      return var10000;
   }

   private static SyntheticBridgeException b(SyntheticBridgeException var0) {
      return var0;
   }
}

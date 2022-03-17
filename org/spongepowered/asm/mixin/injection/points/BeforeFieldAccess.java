package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.mixin.injection.InjectionPoint$AtCode;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.util.Bytecode;

@InjectionPoint$AtCode("FIELD")
public class BeforeFieldAccess extends BeforeInvoke {
   private static final String ARRAY_GET = "get";
   private static final String ARRAY_SET = "set";
   private static final String ARRAY_LENGTH = "length";
   public static final int ARRAY_SEARCH_FUZZ_DEFAULT = 8;
   private final int opcode;
   private final int arrOpcode;
   private final int fuzzFactor;

   public BeforeFieldAccess(InjectionPointData var1) {
      super(var1);
      this.opcode = var1.getOpcode(-1, 180, 181, 178, 179, -1);
      String var2 = var1.get("array", "");

      BeforeFieldAccess var10000;
      short var10001;
      label41: {
         try {
            var10000 = this;
            if ("get".equalsIgnoreCase(var2)) {
               var10001 = 46;
               break label41;
            }
         } catch (RuntimeException var5) {
            throw c(var5);
         }

         try {
            if ("set".equalsIgnoreCase(var2)) {
               var10001 = 79;
               break label41;
            }
         } catch (RuntimeException var4) {
            throw c(var4);
         }

         try {
            if ("length".equalsIgnoreCase(var2)) {
               var10001 = 190;
               break label41;
            }
         } catch (RuntimeException var3) {
            throw c(var3);
         }

         var10001 = 0;
      }

      var10000.arrOpcode = var10001;
      this.fuzzFactor = Math.min(Math.max(var1.get("fuzz", 8), 1), 32);
   }

   public int getFuzzFactor() {
      return this.fuzzFactor;
   }

   public int getArrayOpcode() {
      return this.arrOpcode;
   }

   private int getArrayOpcode(String var1) {
      try {
         if (this.arrOpcode != 190) {
            return Type.getType(var1).getElementType().getOpcode(this.arrOpcode);
         }
      } catch (RuntimeException var2) {
         throw c(var2);
      }

      return this.arrOpcode;
   }

   protected boolean matchesInsn(AbstractInsnNode param1) {
      // $FF: Couldn't be decompiled
   }

   protected boolean addInsn(InsnList var1, Collection<AbstractInsnNode> var2, AbstractInsnNode var3) {
      if (this.arrOpcode > 0) {
         FieldInsnNode var4 = (FieldInsnNode)var3;
         int var5 = this.getArrayOpcode(var4.desc);

         try {
            this.log("{} > > > > searching for array access opcode {} fuzz={}", new Object[]{this.className, Bytecode.getOpcodeName(var5), this.fuzzFactor});
            if (findArrayNode(var1, var4, var5, this.fuzzFactor) == null) {
               this.log("{} > > > > > failed to locate matching insn", new Object[]{this.className});
               return false;
            }
         } catch (RuntimeException var6) {
            throw c(var6);
         }
      }

      this.log("{} > > > > > adding matching insn", new Object[]{this.className});
      return super.addInsn(var1, var2, var3);
   }

   public static AbstractInsnNode findArrayNode(InsnList param0, FieldInsnNode param1, int param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException c(RuntimeException var0) {
      return var0;
   }
}

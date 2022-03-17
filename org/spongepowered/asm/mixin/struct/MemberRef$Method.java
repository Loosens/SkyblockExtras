package org.spongepowered.asm.mixin.struct;

import org.spongepowered.asm.lib.tree.MethodInsnNode;

public final class MemberRef$Method extends MemberRef {
   private static final int OPCODES = 191;
   public final MethodInsnNode insn;

   public MemberRef$Method(MethodInsnNode var1) {
      this.insn = var1;
   }

   public boolean isField() {
      return false;
   }

   public int getOpcode() {
      return this.insn.getOpcode();
   }

   public void setOpcode(int var1) {
      try {
         if ((var1 & 191) == 0) {
            throw new IllegalArgumentException("Invalid opcode for method instruction: 0x" + Integer.toHexString(var1));
         }
      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }

      this.insn.setOpcode(var1);
   }

   public String getOwner() {
      return this.insn.owner;
   }

   public void setOwner(String var1) {
      this.insn.owner = var1;
   }

   public String getName() {
      return this.insn.name;
   }

   public void setName(String var1) {
      this.insn.name = var1;
   }

   public String getDesc() {
      return this.insn.desc;
   }

   public void setDesc(String var1) {
      this.insn.desc = var1;
   }

   private static IllegalArgumentException b(IllegalArgumentException var0) {
      return var0;
   }
}

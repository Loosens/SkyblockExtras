package org.spongepowered.asm.mixin.struct;

import org.spongepowered.asm.lib.tree.FieldInsnNode;

public final class MemberRef$Field extends MemberRef {
   private static final int OPCODES = 183;
   public final FieldInsnNode insn;

   public MemberRef$Field(FieldInsnNode var1) {
      this.insn = var1;
   }

   public boolean isField() {
      return true;
   }

   public int getOpcode() {
      return this.insn.getOpcode();
   }

   public void setOpcode(int var1) {
      try {
         if ((var1 & 183) == 0) {
            throw new IllegalArgumentException("Invalid opcode for field instruction: 0x" + Integer.toHexString(var1));
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

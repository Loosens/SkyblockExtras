package org.spongepowered.asm.mixin.struct;

import org.spongepowered.asm.util.Bytecode;

public abstract class MemberRef {
   private static final int[] H_OPCODES = new int[]{0, 180, 178, 181, 179, 182, 184, 183, 183, 185};

   public abstract boolean isField();

   public abstract int getOpcode();

   public abstract void setOpcode(int var1);

   public abstract String getOwner();

   public abstract void setOwner(String var1);

   public abstract String getName();

   public abstract void setName(String var1);

   public abstract String getDesc();

   public abstract void setDesc(String var1);

   public String toString() {
      String var1 = Bytecode.getOpcodeName(this.getOpcode());

      String var10000;
      Object[] var10001;
      Object[] var10002;
      byte var10003;
      String var10004;
      label17: {
         try {
            var10000 = "%s for %s.%s%s%s";
            var10001 = new Object[]{var1, this.getOwner(), this.getName(), null, null};
            var10002 = var10001;
            var10003 = 3;
            if (this.isField()) {
               var10004 = ":";
               break label17;
            }
         } catch (RuntimeException var2) {
            throw b(var2);
         }

         var10004 = "";
      }

      var10002[var10003] = var10004;
      var10001[4] = this.getDesc();
      return String.format(var10000, var10001);
   }

   public boolean equals(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public int hashCode() {
      return this.toString().hashCode();
   }

   static int opcodeFromTag(int param0) {
      // $FF: Couldn't be decompiled
   }

   static int tagFromOpcode(int param0) {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

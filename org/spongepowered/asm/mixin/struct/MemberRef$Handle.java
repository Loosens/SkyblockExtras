package org.spongepowered.asm.mixin.struct;

import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.util.Bytecode;

public final class MemberRef$Handle extends MemberRef {
   private Handle handle;

   public MemberRef$Handle(Handle var1) {
      this.handle = var1;
   }

   public Handle getMethodHandle() {
      return this.handle;
   }

   public boolean isField() {
      try {
         switch(this.handle.getTag()) {
         case 1:
         case 2:
         case 3:
         case 4:
            return true;
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
            return false;
         }
      } catch (RuntimeException var1) {
         throw c(var1);
      }

      throw new MixinTransformerError("Invalid tag " + this.handle.getTag() + " for method handle " + this.handle + ".");
   }

   public int getOpcode() {
      int var1 = MemberRef.opcodeFromTag(this.handle.getTag());

      try {
         if (var1 == 0) {
            throw new MixinTransformerError("Invalid tag " + this.handle.getTag() + " for method handle " + this.handle + ".");
         } else {
            return var1;
         }
      } catch (RuntimeException var2) {
         throw c(var2);
      }
   }

   public void setOpcode(int var1) {
      int var2 = MemberRef.tagFromOpcode(var1);

      try {
         if (var2 == 0) {
            throw new MixinTransformerError("Invalid opcode " + Bytecode.getOpcodeName(var1) + " for method handle " + this.handle + ".");
         }
      } catch (RuntimeException var4) {
         throw c(var4);
      }

      boolean var10000;
      label24: {
         try {
            if (var2 == 9) {
               var10000 = true;
               break label24;
            }
         } catch (RuntimeException var5) {
            throw c(var5);
         }

         var10000 = false;
      }

      boolean var3 = var10000;
      this.handle = new Handle(var2, this.handle.getOwner(), this.handle.getName(), this.handle.getDesc(), var3);
   }

   public String getOwner() {
      return this.handle.getOwner();
   }

   public void setOwner(String var1) {
      boolean var10000;
      label16: {
         try {
            if (this.handle.getTag() == 9) {
               var10000 = true;
               break label16;
            }
         } catch (RuntimeException var3) {
            throw c(var3);
         }

         var10000 = false;
      }

      boolean var2 = var10000;
      this.handle = new Handle(this.handle.getTag(), var1, this.handle.getName(), this.handle.getDesc(), var2);
   }

   public String getName() {
      return this.handle.getName();
   }

   public void setName(String var1) {
      boolean var10000;
      label16: {
         try {
            if (this.handle.getTag() == 9) {
               var10000 = true;
               break label16;
            }
         } catch (RuntimeException var3) {
            throw c(var3);
         }

         var10000 = false;
      }

      boolean var2 = var10000;
      this.handle = new Handle(this.handle.getTag(), this.handle.getOwner(), var1, this.handle.getDesc(), var2);
   }

   public String getDesc() {
      return this.handle.getDesc();
   }

   public void setDesc(String var1) {
      boolean var10000;
      label16: {
         try {
            if (this.handle.getTag() == 9) {
               var10000 = true;
               break label16;
            }
         } catch (RuntimeException var3) {
            throw c(var3);
         }

         var10000 = false;
      }

      boolean var2 = var10000;
      this.handle = new Handle(this.handle.getTag(), this.handle.getOwner(), this.handle.getName(), var1, var2);
   }

   private static RuntimeException c(RuntimeException var0) {
      return var0;
   }
}

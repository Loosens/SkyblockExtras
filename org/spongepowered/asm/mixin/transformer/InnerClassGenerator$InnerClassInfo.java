package org.spongepowered.asm.mixin.transformer;

import java.io.IOException;
import org.spongepowered.asm.lib.commons.Remapper;
import org.spongepowered.asm.service.MixinService;

class InnerClassGenerator$InnerClassInfo extends Remapper {
   private final String name;
   private final String originalName;
   private final MixinInfo owner;
   private final MixinTargetContext target;
   private final String ownerName;
   private final String targetName;

   InnerClassGenerator$InnerClassInfo(String var1, String var2, MixinInfo var3, MixinTargetContext var4) {
      this.name = var1;
      this.originalName = var2;
      this.owner = var3;
      this.ownerName = var3.getClassRef();
      this.target = var4;
      this.targetName = var4.getTargetClassRef();
   }

   String getName() {
      return this.name;
   }

   String getOriginalName() {
      return this.originalName;
   }

   MixinInfo getOwner() {
      return this.owner;
   }

   MixinTargetContext getTarget() {
      return this.target;
   }

   String getOwnerName() {
      return this.ownerName;
   }

   String getTargetName() {
      return this.targetName;
   }

   byte[] getClassBytes() throws ClassNotFoundException, IOException {
      return MixinService.getService().getBytecodeProvider().getClassBytes(this.originalName, true);
   }

   public String mapMethodName(String var1, String var2, String var3) {
      if (this.ownerName.equalsIgnoreCase(var1)) {
         ClassInfo$Method var4 = this.owner.getClassInfo().findMethod(var2, var3, 10);

         try {
            if (var4 != null) {
               return var4.getName();
            }
         } catch (RuntimeException var5) {
            throw c(var5);
         }
      }

      return super.mapMethodName(var1, var2, var3);
   }

   public String map(String var1) {
      try {
         if (this.originalName.equals(var1)) {
            return this.name;
         }
      } catch (RuntimeException var3) {
         throw c(var3);
      }

      try {
         return this.ownerName.equals(var1) ? this.targetName : var1;
      } catch (RuntimeException var2) {
         throw c(var2);
      }
   }

   public String toString() {
      return this.name;
   }

   private static RuntimeException c(RuntimeException var0) {
      return var0;
   }
}

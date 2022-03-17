package org.spongepowered.asm.mixin.transformer;

import java.util.HashSet;
import java.util.List;
import org.spongepowered.asm.mixin.transformer.throwables.MixinReloadException;

class MixinInfo$Reloaded extends MixinInfo$State {
   private final MixinInfo$State previous;
   final MixinInfo this$0;

   MixinInfo$Reloaded(MixinInfo var1, MixinInfo$State var2, byte[] var3) {
      super(var1, var3, var2.getClassInfo());
      this.this$0 = var1;
      this.previous = var2;
   }

   protected void validateChanges(MixinInfo$SubType var1, List<ClassInfo> var2) {
      try {
         if (!this.syntheticInnerClasses.equals(this.previous.syntheticInnerClasses)) {
            throw new MixinReloadException(this.this$0, "Cannot change inner classes");
         }
      } catch (MixinReloadException var9) {
         throw b(var9);
      }

      try {
         if (!this.interfaces.equals(this.previous.interfaces)) {
            throw new MixinReloadException(this.this$0, "Cannot change interfaces");
         }
      } catch (MixinReloadException var6) {
         throw b(var6);
      }

      try {
         if (!(new HashSet(this.softImplements)).equals(new HashSet(this.previous.softImplements))) {
            throw new MixinReloadException(this.this$0, "Cannot change soft interfaces");
         }
      } catch (MixinReloadException var8) {
         throw b(var8);
      }

      List var3 = this.this$0.readTargetClasses(this.classNode, true);

      try {
         if (!(new HashSet(var3)).equals(new HashSet(var2))) {
            throw new MixinReloadException(this.this$0, "Cannot change target classes");
         }
      } catch (MixinReloadException var7) {
         throw b(var7);
      }

      int var4 = this.this$0.readPriority(this.classNode);

      try {
         if (var4 != this.this$0.getPriority()) {
            throw new MixinReloadException(this.this$0, "Cannot change mixin priority");
         }
      } catch (MixinReloadException var5) {
         throw b(var5);
      }
   }

   private static MixinReloadException b(MixinReloadException var0) {
      return var0;
   }
}

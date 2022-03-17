package org.spongepowered.asm.mixin.transformer;

import java.util.List;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.tree.ClassNode;

class MixinInfo$MixinClassNode extends ClassNode {
   public final List<MixinInfo$MixinMethodNode> mixinMethods;
   final MixinInfo this$0;

   public MixinInfo$MixinClassNode(MixinInfo var1, MixinInfo var2) {
      this(var1, 327680);
   }

   public MixinInfo$MixinClassNode(MixinInfo var1, int var2) {
      super(var2);
      this.this$0 = var1;
      this.mixinMethods = (List)this.methods;
   }

   public MixinInfo getMixin() {
      return this.this$0;
   }

   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      MixinInfo$MixinMethodNode var6 = new MixinInfo$MixinMethodNode(this.this$0, var1, var2, var3, var4, var5);
      this.methods.add(var6);
      return var6;
   }
}

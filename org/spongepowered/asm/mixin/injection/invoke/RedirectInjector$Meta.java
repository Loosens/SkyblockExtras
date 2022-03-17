package org.spongepowered.asm.mixin.injection.invoke;

class RedirectInjector$Meta {
   public static final String KEY = "redirector";
   final int priority;
   final boolean isFinal;
   final String name;
   final String desc;
   final RedirectInjector this$0;

   public RedirectInjector$Meta(RedirectInjector var1, int var2, boolean var3, String var4, String var5) {
      this.this$0 = var1;
      this.priority = var2;
      this.isFinal = var3;
      this.name = var4;
      this.desc = var5;
   }

   RedirectInjector getOwner() {
      return this.this$0;
   }
}

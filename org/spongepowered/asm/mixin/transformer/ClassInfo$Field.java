package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.tree.FieldNode;

class ClassInfo$Field extends ClassInfo$Member {
   final ClassInfo this$0;

   public ClassInfo$Field(ClassInfo var1, ClassInfo$Member var2) {
      super(var2);
      this.this$0 = var1;
   }

   public ClassInfo$Field(ClassInfo var1, FieldNode var2) {
      this(var1, var2, false);
   }

   public ClassInfo$Field(ClassInfo param1, FieldNode param2, boolean param3) {
      // $FF: Couldn't be decompiled
   }

   public ClassInfo$Field(ClassInfo var1, String var2, String var3, int var4) {
      super(ClassInfo$Member$Type.FIELD, var2, var3, var4, false);
      this.this$0 = var1;
   }

   public ClassInfo$Field(ClassInfo var1, String var2, String var3, int var4, boolean var5) {
      super(ClassInfo$Member$Type.FIELD, var2, var3, var4, var5);
      this.this$0 = var1;
   }

   public ClassInfo getOwner() {
      return this.this$0;
   }

   public boolean equals(Object var1) {
      try {
         if (!(var1 instanceof ClassInfo$Field)) {
            return false;
         }
      } catch (RuntimeException var2) {
         throw c(var2);
      }

      return super.equals(var1);
   }

   protected String getDisplayFormat() {
      return "%s:%s";
   }

   private static RuntimeException c(RuntimeException var0) {
      return var0;
   }
}

package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;

class MethodNode$1 extends ArrayList<Object> {
   final MethodNode this$0;

   MethodNode$1(MethodNode var1, int var2) {
      super(var2);
      this.this$0 = var1;
   }

   public boolean add(Object var1) {
      this.this$0.annotationDefault = var1;
      return super.add(var1);
   }
}

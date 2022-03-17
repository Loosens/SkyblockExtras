package org.spongepowered.asm.mixin.transformer;

public class ClassInfo$InterfaceMethod extends ClassInfo$Method {
   private final ClassInfo owner;
   final ClassInfo this$0;

   public ClassInfo$InterfaceMethod(ClassInfo var1, ClassInfo$Member var2) {
      super(var1, var2);
      this.this$0 = var1;
      this.owner = var2.getOwner();
   }

   public ClassInfo getOwner() {
      return this.owner;
   }

   public ClassInfo getImplementor() {
      return this.this$0;
   }
}

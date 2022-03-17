package org.spongepowered.asm.mixin.injection.callback;

import org.spongepowered.asm.lib.Type;

public class CallbackInfo implements Cancellable {
   private final String name;
   private final boolean cancellable;
   private boolean cancelled;

   public CallbackInfo(String var1, boolean var2) {
      this.name = var1;
      this.cancellable = var2;
   }

   public String getId() {
      return this.name;
   }

   public String toString() {
      return String.format("CallbackInfo[TYPE=%s,NAME=%s,CANCELLABLE=%s]", this.getClass().getSimpleName(), this.name, this.cancellable);
   }

   public final boolean isCancellable() {
      return this.cancellable;
   }

   public final boolean isCancelled() {
      return this.cancelled;
   }

   public void cancel() throws CancellationException {
      try {
         if (!this.cancellable) {
            throw new CancellationException(String.format("The call %s is not cancellable.", this.name));
         }
      } catch (CancellationException var1) {
         throw b(var1);
      }

      this.cancelled = true;
   }

   static String getCallInfoClassName() {
      return CallbackInfo.class.getName();
   }

   public static String getCallInfoClassName(Type var0) {
      String var10000;
      try {
         if (var0.equals(Type.VOID_TYPE)) {
            var10000 = CallbackInfo.class.getName();
            return var10000.replace('.', '/');
         }
      } catch (CancellationException var1) {
         throw b(var1);
      }

      var10000 = CallbackInfoReturnable.class.getName();
      return var10000.replace('.', '/');
   }

   static String getConstructorDescriptor(Type param0) {
      // $FF: Couldn't be decompiled
   }

   static String getConstructorDescriptor() {
      return String.format("(%sZ)V", "Ljava/lang/String;");
   }

   static String getIsCancelledMethodName() {
      return "isCancelled";
   }

   static String getIsCancelledMethodSig() {
      return "()Z";
   }

   private static CancellationException b(CancellationException var0) {
      return var0;
   }
}

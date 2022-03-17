package org.spongepowered.asm.mixin.injection.callback;

import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes$InjectionNode;
import org.spongepowered.asm.mixin.injection.struct.Target;

class CallbackInjector$Callback extends InsnList {
   private final MethodNode handler;
   private final AbstractInsnNode head;
   final Target target;
   final InjectionNodes$InjectionNode node;
   final LocalVariableNode[] locals;
   final Type[] localTypes;
   final int frameSize;
   final int extraArgs;
   final boolean canCaptureLocals;
   final boolean isAtReturn;
   final String desc;
   final String descl;
   final String[] argNames;
   int ctor;
   int invoke;
   private int marshalVar;
   private boolean captureArgs;
   final CallbackInjector this$0;

   CallbackInjector$Callback(CallbackInjector param1, MethodNode param2, Target param3, InjectionNodes$InjectionNode param4, LocalVariableNode[] param5, boolean param6) {
      // $FF: Couldn't be decompiled
   }

   private boolean isValueReturnOpcode(int param1) {
      // $FF: Couldn't be decompiled
   }

   String getDescriptor() {
      String var10000;
      try {
         if (this.canCaptureLocals) {
            var10000 = this.descl;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = this.desc;
      return var10000;
   }

   String getDescriptorWithAllLocals() {
      return this.target.getCallbackDescriptor(true, this.localTypes, this.target.arguments, this.frameSize, 32767);
   }

   String getCallbackInfoConstructorDescriptor() {
      String var10000;
      try {
         if (this.isAtReturn) {
            var10000 = CallbackInfo.getConstructorDescriptor(this.target.returnType);
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = CallbackInfo.getConstructorDescriptor();
      return var10000;
   }

   void add(AbstractInsnNode var1, boolean var2, boolean var3) {
      this.add(var1, var2, var3, false);
   }

   void add(AbstractInsnNode var1, boolean var2, boolean var3, boolean var4) {
      label40: {
         try {
            if (var4) {
               this.target.insns.insertBefore(this.head, var1);
               break label40;
            }
         } catch (RuntimeException var7) {
            throw b(var7);
         }

         this.add(var1);
      }

      CallbackInjector$Callback var10000;
      int var10001;
      byte var10002;
      label33: {
         try {
            var10000 = this;
            var10001 = this.ctor;
            if (var2) {
               var10002 = 1;
               break label33;
            }
         } catch (RuntimeException var6) {
            throw b(var6);
         }

         var10002 = 0;
      }

      label26: {
         try {
            var10000.ctor = var10001 + var10002;
            var10000 = this;
            var10001 = this.invoke;
            if (var3) {
               var10002 = 1;
               break label26;
            }
         } catch (RuntimeException var5) {
            throw b(var5);
         }

         var10002 = 0;
      }

      var10000.invoke = var10001 + var10002;
   }

   void inject() {
      this.target.insertBefore((InjectionNodes$InjectionNode)this.node, this);
      this.target.addToStack(Math.max(this.invoke, this.ctor));
   }

   boolean checkDescriptor(String param1) {
      // $FF: Couldn't be decompiled
   }

   boolean captureArgs() {
      return this.captureArgs;
   }

   int marshalVar() {
      try {
         if (this.marshalVar < 0) {
            this.marshalVar = this.target.allocateLocal();
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      return this.marshalVar;
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

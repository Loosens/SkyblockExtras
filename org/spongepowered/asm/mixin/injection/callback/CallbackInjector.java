package org.spongepowered.asm.mixin.injection.callback;

import com.google.common.base.Strings;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.points.BeforeReturn;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes$InjectionNode;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Locals;

public class CallbackInjector extends Injector {
   private final boolean cancellable;
   private final LocalCapture localCapture;
   private final String identifier;
   private final Map<Integer, String> ids = new HashMap();
   private int totalInjections = 0;
   private int callbackInfoVar = -1;
   private String lastId;
   private String lastDesc;
   private Target lastTarget;
   private String callbackInfoClass;

   public CallbackInjector(InjectionInfo var1, boolean var2, LocalCapture var3, String var4) {
      super(var1);
      this.cancellable = var2;
      this.localCapture = var3;
      this.identifier = var4;
   }

   protected void sanityCheck(Target var1, List<InjectionPoint> var2) {
      try {
         super.sanityCheck(var1, var2);
         if (var1.isStatic != this.isStatic) {
            throw new InvalidInjectionException(this.info, "'static' modifier of callback method does not match target in " + this);
         }
      } catch (InvalidInjectionException var6) {
         throw c(var6);
      }

      if ("<init>".equals(var1.method.name)) {
         Iterator var3 = var2.iterator();

         while(var3.hasNext()) {
            InjectionPoint var4 = (InjectionPoint)var3.next();

            try {
               if (!var4.getClass().equals(BeforeReturn.class)) {
                  throw new InvalidInjectionException(this.info, "Found injection point type " + var4.getClass().getSimpleName() + " targetting a ctor in " + this + ". Only RETURN allowed for a ctor target");
               }
            } catch (InvalidInjectionException var5) {
               throw c(var5);
            }
         }
      }

   }

   protected void addTargetNode(Target param1, List<InjectionNodes$InjectionNode> param2, AbstractInsnNode param3, Set<InjectionPoint> param4) {
      // $FF: Couldn't be decompiled
   }

   protected void inject(Target var1, InjectionNodes$InjectionNode var2) {
      LocalVariableNode[] var3 = null;

      label19: {
         try {
            if (!this.localCapture.isCaptureLocals() && !this.localCapture.isPrintLocals()) {
               break label19;
            }
         } catch (InvalidInjectionException var4) {
            throw c(var4);
         }

         var3 = Locals.getLocalsAt(this.classNode, var1.method, var2.getCurrentTarget());
      }

      this.inject(new CallbackInjector$Callback(this, this.methodNode, var1, var2, var3, this.localCapture.isCaptureLocals()));
   }

   private void inject(CallbackInjector$Callback param1) {
      // $FF: Couldn't be decompiled
   }

   private String generateBadLVTMessage(CallbackInjector$Callback var1) {
      int var2 = var1.target.indexOf(var1.node);
      List var3 = summariseLocals(this.methodNode.desc, var1.target.arguments.length + 1);
      List var4 = summariseLocals(var1.getDescriptorWithAllLocals(), var1.frameSize);
      return String.format("LVT in %s has incompatible changes at opcode %d in callback %s.\nExpected: %s\n   Found: %s", var1.target, var2, this, var3, var4);
   }

   private MethodNode generateErrorMethod(CallbackInjector$Callback var1, String var2, String var3) {
      MethodNode var4 = this.info.addMethod(this.methodNode.access, this.methodNode.name + "$missing", var1.getDescriptor());

      MethodNode var10000;
      Type[] var10001;
      boolean var10002;
      label17: {
         try {
            var10000 = var4;
            var10001 = Type.getArgumentTypes(var1.getDescriptor());
            if (!this.isStatic) {
               var10002 = true;
               break label17;
            }
         } catch (InvalidInjectionException var6) {
            throw c(var6);
         }

         var10002 = false;
      }

      var10000.maxLocals = Bytecode.getFirstNonArgLocalIndex(var10001, var10002);
      var4.maxStack = 3;
      InsnList var5 = var4.instructions;
      var5.add((AbstractInsnNode)(new TypeInsnNode(187, var2)));
      var5.add((AbstractInsnNode)(new InsnNode(89)));
      var5.add((AbstractInsnNode)(new LdcInsnNode(var3)));
      var5.add((AbstractInsnNode)(new MethodInsnNode(183, var2, "<init>", "(Ljava/lang/String;)V", false)));
      var5.add((AbstractInsnNode)(new InsnNode(191)));
      return var4;
   }

   private void printLocals(CallbackInjector$Callback param1) {
      // $FF: Couldn't be decompiled
   }

   private void createCallbackInfo(CallbackInjector$Callback param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   private void loadOrCreateCallbackInfo(CallbackInjector$Callback param1) {
      // $FF: Couldn't be decompiled
   }

   private void dupReturnValue(CallbackInjector$Callback var1) {
      try {
         if (!var1.isAtReturn) {
            return;
         }
      } catch (InvalidInjectionException var2) {
         throw c(var2);
      }

      var1.add(new InsnNode(89));
      var1.add(new VarInsnNode(var1.target.returnType.getOpcode(54), var1.marshalVar()));
   }

   protected void instanceCallbackInfo(CallbackInjector$Callback param1, String param2, String param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   private void invokeCallback(CallbackInjector$Callback param1, MethodNode param2) {
      // $FF: Couldn't be decompiled
   }

   private String getIdentifier(CallbackInjector$Callback var1) {
      String var10000;
      label30: {
         try {
            if (Strings.isNullOrEmpty(this.identifier)) {
               var10000 = var1.target.method.name;
               break label30;
            }
         } catch (InvalidInjectionException var5) {
            throw c(var5);
         }

         var10000 = this.identifier;
      }

      String var2 = var10000;
      String var3 = (String)this.ids.get(var1.node.getId());

      String var10001;
      StringBuilder var6;
      try {
         var6 = (new StringBuilder()).append(var2);
         if (Strings.isNullOrEmpty(var3)) {
            var10001 = "";
            return var6.append(var10001).toString();
         }
      } catch (InvalidInjectionException var4) {
         throw c(var4);
      }

      var10001 = ":" + var3;
      return var6.append(var10001).toString();
   }

   protected void injectCancellationCode(CallbackInjector$Callback var1) {
      try {
         if (!this.cancellable) {
            return;
         }
      } catch (InvalidInjectionException var3) {
         throw c(var3);
      }

      var1.add(new VarInsnNode(25, this.callbackInfoVar));
      var1.add(new MethodInsnNode(182, this.callbackInfoClass, CallbackInfo.getIsCancelledMethodName(), CallbackInfo.getIsCancelledMethodSig(), false));
      LabelNode var2 = new LabelNode();
      var1.add(new JumpInsnNode(153, var2));
      this.injectReturnCode(var1);
      var1.add(var2);
   }

   protected void injectReturnCode(CallbackInjector$Callback var1) {
      try {
         if (var1.target.returnType.equals(Type.VOID_TYPE)) {
            var1.add(new InsnNode(177));
            return;
         }
      } catch (InvalidInjectionException var5) {
         throw c(var5);
      }

      var1.add(new VarInsnNode(25, var1.marshalVar()));
      String var2 = CallbackInfoReturnable.getReturnAccessor(var1.target.returnType);
      String var3 = CallbackInfoReturnable.getReturnDescriptor(var1.target.returnType);

      try {
         var1.add(new MethodInsnNode(182, this.callbackInfoClass, var2, var3, false));
         if (var1.target.returnType.getSort() == 10) {
            var1.add(new TypeInsnNode(192, var1.target.returnType.getInternalName()));
         }
      } catch (InvalidInjectionException var4) {
         throw c(var4);
      }

      var1.add(new InsnNode(var1.target.returnType.getOpcode(172)));
   }

   protected boolean isStatic() {
      return this.isStatic;
   }

   private static List<String> summariseLocals(String var0, int var1) {
      return summariseLocals(Type.getArgumentTypes(var0), var1);
   }

   private static List<String> summariseLocals(Type[] param0, int param1) {
      // $FF: Couldn't be decompiled
   }

   static String meltSnowman(int param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   private static InvalidInjectionException c(InvalidInjectionException var0) {
      return var0;
   }
}

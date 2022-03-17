package org.spongepowered.asm.mixin.injection.invoke;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.points.BeforeNew;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes$InjectionNode;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

public class RedirectInjector extends InvokeInjector {
   private static final String KEY_NOMINATORS = "nominators";
   private static final String KEY_FUZZ = "fuzz";
   private static final String KEY_OPCODE = "opcode";
   protected RedirectInjector$Meta meta;
   private Map<BeforeNew, RedirectInjector$ConstructorRedirectData> ctorRedirectors;

   public RedirectInjector(InjectionInfo var1) {
      this(var1, "@Redirect");
   }

   protected RedirectInjector(InjectionInfo var1, String var2) {
      super(var1, var2);
      this.ctorRedirectors = new HashMap();
      int var3 = var1.getContext().getPriority();

      boolean var10000;
      label17: {
         try {
            if (Annotations.getVisible(this.methodNode, Final.class) != null) {
               var10000 = true;
               break label17;
            }
         } catch (InvalidInjectionException var5) {
            throw d(var5);
         }

         var10000 = false;
      }

      boolean var4 = var10000;
      this.meta = new RedirectInjector$Meta(this, var3, var4, this.info.toString(), this.methodNode.desc);
   }

   protected void checkTarget(Target var1) {
   }

   protected void addTargetNode(Target param1, List<InjectionNodes$InjectionNode> param2, AbstractInsnNode param3, Set<InjectionPoint> param4) {
      // $FF: Couldn't be decompiled
   }

   private RedirectInjector$ConstructorRedirectData getCtorRedirect(BeforeNew var1) {
      RedirectInjector$ConstructorRedirectData var2 = (RedirectInjector$ConstructorRedirectData)this.ctorRedirectors.get(var1);
      if (var2 == null) {
         var2 = new RedirectInjector$ConstructorRedirectData();
         this.ctorRedirectors.put(var1, var2);
      }

      return var2;
   }

   protected void inject(Target param1, InjectionNodes$InjectionNode param2) {
      // $FF: Couldn't be decompiled
   }

   protected boolean preInject(InjectionNodes$InjectionNode var1) {
      RedirectInjector$Meta var2 = (RedirectInjector$Meta)var1.getDecoration("redirector");

      try {
         if (var2.getOwner() != this) {
            Injector.logger.warn("{} conflict. Skipping {} with priority {}, already redirected by {} with priority {}", new Object[]{this.annotationType, this.info, this.meta.priority, var2.name, var2.priority});
            return false;
         } else {
            return true;
         }
      } catch (InvalidInjectionException var3) {
         throw d(var3);
      }
   }

   protected void postInject(Target param1, InjectionNodes$InjectionNode param2) {
      // $FF: Couldn't be decompiled
   }

   protected void injectAtInvoke(Target var1, InjectionNodes$InjectionNode var2) {
      RedirectInjector$RedirectedInvoke var3 = new RedirectInjector$RedirectedInvoke(var1, (MethodInsnNode)var2.getCurrentTarget());
      this.validateParams(var3);
      InsnList var4 = new InsnList();
      int var5 = Bytecode.getArgsSize(var3.locals) + 1;
      int var6 = 1;
      int[] var7 = this.storeArgs(var1, var3.locals, var4, 0);
      if (var3.captureTargetArgs) {
         int var8 = Bytecode.getArgsSize(var1.arguments);
         var5 += var8;
         var6 += var8;
         var7 = Ints.concat(new int[][]{var7, var1.getArgIndices()});
      }

      AbstractInsnNode var9 = this.invokeHandlerWithArgs(this.methodArgs, var4, var7);
      var1.replaceNode(var3.node, var9, var4);
      var1.addToLocals(var5);
      var1.addToStack(var6);
   }

   protected void validateParams(RedirectInjector$RedirectedInvoke param1) {
      // $FF: Couldn't be decompiled
   }

   private void injectAtFieldAccess(Target var1, InjectionNodes$InjectionNode var2) {
      FieldInsnNode var3 = (FieldInsnNode)var2.getCurrentTarget();
      int var4 = var3.getOpcode();
      Type var5 = Type.getType("L" + var3.owner + ";");
      Type var6 = Type.getType(var3.desc);

      int var10000;
      label57: {
         try {
            if (var6.getSort() == 9) {
               var10000 = var6.getDimensions();
               break label57;
            }
         } catch (InvalidInjectionException var14) {
            throw d(var14);
         }

         var10000 = 0;
      }

      int var7 = var10000;

      label49: {
         try {
            if (this.returnType.getSort() == 9) {
               var10000 = this.returnType.getDimensions();
               break label49;
            }
         } catch (InvalidInjectionException var13) {
            throw d(var13);
         }

         var10000 = 0;
      }

      int var8 = var10000;

      try {
         if (var8 > var7) {
            throw new InvalidInjectionException(this.info, "Dimensionality of handler method is greater than target array on " + this);
         }
      } catch (InvalidInjectionException var11) {
         throw d(var11);
      }

      label65: {
         try {
            if (var8 == 0 && var7 > 0) {
               break label65;
            }
         } catch (InvalidInjectionException var12) {
            throw d(var12);
         }

         this.injectAtScalarField(var1, var3, var4, var5, var6);
         return;
      }

      int var9 = (Integer)var2.getDecoration("fuzz");
      int var10 = (Integer)var2.getDecoration("opcode");
      this.injectAtArrayField(var1, var3, var4, var5, var6, var9, var10);
   }

   private void injectAtArrayField(Target param1, FieldInsnNode param2, int param3, Type param4, Type param5, int param6, int param7) {
      // $FF: Couldn't be decompiled
   }

   private void injectAtGetArray(Target var1, FieldInsnNode var2, AbstractInsnNode var3, Type var4, Type var5) {
      String var6 = getGetArrayHandlerDescriptor(var3, this.returnType, var5);
      boolean var7 = this.checkDescriptor(var6, var1, "array getter");
      this.injectArrayRedirect(var1, var2, var3, var7, "array getter");
   }

   private void injectAtSetArray(Target var1, FieldInsnNode var2, AbstractInsnNode var3, Type var4, Type var5) {
      String var6 = Bytecode.generateDescriptor((Object)null, (Object[])getArrayArgs(var5, 1, var5.getElementType()));
      boolean var7 = this.checkDescriptor(var6, var1, "array setter");
      this.injectArrayRedirect(var1, var2, var3, var7, "array setter");
   }

   public void injectArrayRedirect(Target var1, FieldInsnNode var2, AbstractInsnNode var3, boolean var4, String var5) {
      if (var3 == null) {
         String var9 = "";
         throw new InvalidInjectionException(this.info, String.format("Array element %s on %s could not locate a matching %s instruction in %s. %s", this.annotationType, this, var5, var1, var9));
      } else {
         try {
            if (!this.isStatic) {
               var1.insns.insertBefore(var2, (AbstractInsnNode)(new VarInsnNode(25, 0)));
               var1.addToStack(1);
            }
         } catch (InvalidInjectionException var8) {
            throw d(var8);
         }

         InsnList var6 = new InsnList();

         try {
            if (var4) {
               this.pushArgs(var1.arguments, var6, var1.getArgIndices(), 0, var1.arguments.length);
               var1.addToStack(Bytecode.getArgsSize(var1.arguments));
            }
         } catch (InvalidInjectionException var7) {
            throw d(var7);
         }

         var1.replaceNode(var3, this.invokeHandler(var6), var6);
      }
   }

   public void injectAtScalarField(Target param1, FieldInsnNode param2, int param3, Type param4, Type param5) {
      // $FF: Couldn't be decompiled
   }

   private AbstractInsnNode injectAtGetField(InsnList param1, Target param2, FieldInsnNode param3, boolean param4, Type param5, Type param6) {
      // $FF: Couldn't be decompiled
   }

   private AbstractInsnNode injectAtPutField(InsnList param1, Target param2, FieldInsnNode param3, boolean param4, Type param5, Type param6) {
      // $FF: Couldn't be decompiled
   }

   protected boolean checkDescriptor(String var1, Target var2, String var3) {
      try {
         if (this.methodNode.desc.equals(var1)) {
            return false;
         }
      } catch (InvalidInjectionException var7) {
         throw d(var7);
      }

      int var4 = var1.indexOf(41);
      String var5 = String.format("%s%s%s", var1.substring(0, var4), Joiner.on("").join(var2.arguments), var1.substring(var4));

      try {
         if (this.methodNode.desc.equals(var5)) {
            return true;
         }
      } catch (InvalidInjectionException var6) {
         throw d(var6);
      }

      throw new InvalidInjectionException(this.info, String.format("%s method %s %s has an invalid signature. Expected %s but found %s", this.annotationType, var3, this, var1, this.methodNode.desc));
   }

   protected void injectAtConstructor(Target param1, InjectionNodes$InjectionNode param2) {
      // $FF: Couldn't be decompiled
   }

   private static String getGetArrayHandlerDescriptor(AbstractInsnNode param0, Type param1, Type param2) {
      // $FF: Couldn't be decompiled
   }

   private static Type[] getArrayArgs(Type param0, int param1, Type... param2) {
      // $FF: Couldn't be decompiled
   }

   private static InvalidInjectionException d(InvalidInjectionException var0) {
      return var0;
   }
}

package org.spongepowered.asm.mixin.injection.code;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.mixin.MixinEnvironment$Option;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes$InjectionNode;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.util.Bytecode;

public abstract class Injector {
   protected static final Logger logger = LogManager.getLogger("mixin");
   protected InjectionInfo info;
   protected final ClassNode classNode;
   protected final MethodNode methodNode;
   protected final Type[] methodArgs;
   protected final Type returnType;
   protected final boolean isStatic;

   public Injector(InjectionInfo var1) {
      this(var1.getClassNode(), var1.getMethod());
      this.info = var1;
   }

   private Injector(ClassNode var1, MethodNode var2) {
      this.classNode = var1;
      this.methodNode = var2;
      this.methodArgs = Type.getArgumentTypes(this.methodNode.desc);
      this.returnType = Type.getReturnType(this.methodNode.desc);
      this.isStatic = Bytecode.methodIsStatic(this.methodNode);
   }

   public String toString() {
      return String.format("%s::%s", this.classNode.name, this.methodNode.name);
   }

   public final List<InjectionNodes$InjectionNode> find(InjectorTarget var1, List<InjectionPoint> var2) {
      this.sanityCheck(var1.getTarget(), var2);
      ArrayList var3 = new ArrayList();
      Iterator var4 = this.findTargetNodes(var1, var2).iterator();

      while(var4.hasNext()) {
         Injector$TargetNode var5 = (Injector$TargetNode)var4.next();
         this.addTargetNode(var1.getTarget(), var3, var5.insn, var5.nominators);
      }

      return var3;
   }

   protected void addTargetNode(Target var1, List<InjectionNodes$InjectionNode> var2, AbstractInsnNode var3, Set<InjectionPoint> var4) {
      var2.add(var1.addInjectionNode(var3));
   }

   public final void inject(Target var1, List<InjectionNodes$InjectionNode> var2) {
      Iterator var3 = var2.iterator();

      InjectionNodes$InjectionNode var4;
      while(var3.hasNext()) {
         var4 = (InjectionNodes$InjectionNode)var3.next();

         label35: {
            try {
               if (var4.isRemoved()) {
                  if (!this.info.getContext().getOption(MixinEnvironment$Option.DEBUG_VERBOSE)) {
                     continue;
                  }
                  break label35;
               }
            } catch (InvalidInjectionException var5) {
               throw b(var5);
            }

            this.inject(var1, var4);
            continue;
         }

         logger.warn("Target node for {} was removed by a previous injector in {}", new Object[]{this.info, var1});
      }

      var3 = var2.iterator();

      while(var3.hasNext()) {
         var4 = (InjectionNodes$InjectionNode)var3.next();
         this.postInject(var1, var4);
      }

   }

   private Collection<Injector$TargetNode> findTargetNodes(InjectorTarget param1, List<InjectionPoint> param2) {
      // $FF: Couldn't be decompiled
   }

   protected boolean findTargetNodes(MethodNode var1, InjectionPoint var2, InsnList var3, Collection<AbstractInsnNode> var4) {
      return var2.find(var1.desc, var3, var4);
   }

   protected void sanityCheck(Target var1, List<InjectionPoint> var2) {
      try {
         if (var1.classNode != this.classNode) {
            throw new InvalidInjectionException(this.info, "Target class does not match injector class in " + this);
         }
      } catch (InvalidInjectionException var3) {
         throw b(var3);
      }
   }

   protected abstract void inject(Target var1, InjectionNodes$InjectionNode var2);

   protected void postInject(Target var1, InjectionNodes$InjectionNode var2) {
   }

   protected AbstractInsnNode invokeHandler(InsnList var1) {
      return this.invokeHandler(var1, this.methodNode);
   }

   protected AbstractInsnNode invokeHandler(InsnList var1, MethodNode var2) {
      boolean var10000;
      label40: {
         try {
            if ((var2.access & 2) != 0) {
               var10000 = true;
               break label40;
            }
         } catch (InvalidInjectionException var8) {
            throw b(var8);
         }

         var10000 = false;
      }

      boolean var3 = var10000;

      short var9;
      label43: {
         try {
            if (this.isStatic) {
               var9 = 184;
               break label43;
            }
         } catch (InvalidInjectionException var7) {
            throw b(var7);
         }

         try {
            if (var3) {
               var9 = 183;
               break label43;
            }
         } catch (InvalidInjectionException var6) {
            throw b(var6);
         }

         var9 = 182;
      }

      short var4 = var9;
      MethodInsnNode var5 = new MethodInsnNode(var4, this.classNode.name, var2.name, var2.desc, false);
      var1.add((AbstractInsnNode)var5);
      this.info.addCallbackInvocation(var2);
      return var5;
   }

   protected void throwException(InsnList var1, String var2, String var3) {
      var1.add((AbstractInsnNode)(new TypeInsnNode(187, var2)));
      var1.add((AbstractInsnNode)(new InsnNode(89)));
      var1.add((AbstractInsnNode)(new LdcInsnNode(var3)));
      var1.add((AbstractInsnNode)(new MethodInsnNode(183, var2, "<init>", "(Ljava/lang/String;)V", false)));
      var1.add((AbstractInsnNode)(new InsnNode(191)));
   }

   public static boolean canCoerce(Type param0, Type param1) {
      // $FF: Couldn't be decompiled
   }

   public static boolean canCoerce(String param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   public static boolean canCoerce(char param0, char param1) {
      // $FF: Couldn't be decompiled
   }

   private static boolean canCoerce(ClassInfo param0, ClassInfo param1) {
      // $FF: Couldn't be decompiled
   }

   private static InvalidInjectionException b(InvalidInjectionException var0) {
      return var0;
   }
}

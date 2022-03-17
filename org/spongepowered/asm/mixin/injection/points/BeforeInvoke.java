package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.InjectionPoint$AtCode;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

@InjectionPoint$AtCode("INVOKE")
public class BeforeInvoke extends InjectionPoint {
   protected final MemberInfo target;
   protected final boolean allowPermissive;
   protected final int ordinal;
   protected final String className;
   protected final IMixinContext context;
   protected final Logger logger;
   private boolean log;

   public BeforeInvoke(InjectionPointData param1) {
      // $FF: Couldn't be decompiled
   }

   private String getClassName() {
      InjectionPoint$AtCode var1 = (InjectionPoint$AtCode)this.getClass().getAnnotation(InjectionPoint$AtCode.class);

      String var10000;
      Object[] var10001;
      Object[] var10002;
      byte var10003;
      String var10004;
      label17: {
         try {
            var10000 = "@At(%s)";
            var10001 = new Object[1];
            var10002 = var10001;
            var10003 = 0;
            if (var1 != null) {
               var10004 = var1.value();
               break label17;
            }
         } catch (RuntimeException var2) {
            throw b(var2);
         }

         var10004 = this.getClass().getSimpleName().toUpperCase();
      }

      var10002[var10003] = var10004;
      return String.format(var10000, var10001);
   }

   public BeforeInvoke setLogging(boolean var1) {
      this.log = var1;
      return this;
   }

   public boolean find(String param1, InsnList param2, Collection<AbstractInsnNode> param3) {
      // $FF: Couldn't be decompiled
   }

   protected boolean find(String param1, InsnList param2, Collection<AbstractInsnNode> param3, MemberInfo param4, BeforeInvoke$SearchType param5) {
      // $FF: Couldn't be decompiled
   }

   protected boolean addInsn(InsnList var1, Collection<AbstractInsnNode> var2, AbstractInsnNode var3) {
      var2.add(var3);
      return true;
   }

   protected boolean matchesInsn(AbstractInsnNode var1) {
      return var1 instanceof MethodInsnNode;
   }

   protected void inspectInsn(String var1, InsnList var2, AbstractInsnNode var3) {
   }

   protected boolean matchesInsn(MemberInfo param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   protected void log(String var1, Object... var2) {
      try {
         if (this.log) {
            this.logger.info(var1, var2);
         }

      } catch (RuntimeException var3) {
         throw b(var3);
      }
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

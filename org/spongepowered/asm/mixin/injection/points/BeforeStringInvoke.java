package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.mixin.injection.InjectionPoint$AtCode;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;

@InjectionPoint$AtCode("INVOKE_STRING")
public class BeforeStringInvoke extends BeforeInvoke {
   private static final String STRING_VOID_SIG = "(Ljava/lang/String;)V";
   private final String ldcValue;
   private boolean foundLdc;

   public BeforeStringInvoke(InjectionPointData var1) {
      super(var1);
      this.ldcValue = var1.get("ldc", (String)null);
      if (this.ldcValue == null) {
         throw new IllegalArgumentException(this.getClass().getSimpleName() + " requires named argument \"ldc\" to specify the desired target");
      } else {
         try {
            if (!"(Ljava/lang/String;)V".equals(this.target.desc)) {
               throw new IllegalArgumentException(this.getClass().getSimpleName() + " requires target method with with signature " + "(Ljava/lang/String;)V");
            }
         } catch (IllegalArgumentException var2) {
            throw b(var2);
         }
      }
   }

   public boolean find(String var1, InsnList var2, Collection<AbstractInsnNode> var3) {
      this.foundLdc = false;
      return super.find(var1, var2, var3);
   }

   protected void inspectInsn(String param1, InsnList param2, AbstractInsnNode param3) {
      // $FF: Couldn't be decompiled
   }

   protected boolean matchesInsn(MemberInfo param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   private static IllegalArgumentException b(IllegalArgumentException var0) {
      return var0;
   }
}

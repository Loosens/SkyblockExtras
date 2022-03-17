package org.spongepowered.asm.mixin.injection.points;

import com.google.common.base.Strings;
import java.util.Collection;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.InjectionPoint$AtCode;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;

@InjectionPoint$AtCode("NEW")
public class BeforeNew extends InjectionPoint {
   private final String target;
   private final String desc;
   private final int ordinal;

   public BeforeNew(InjectionPointData var1) {
      super(var1);
      this.ordinal = var1.getOrdinal();
      String var2 = Strings.emptyToNull(var1.get("class", var1.get("target", "")).replace('.', '/'));
      MemberInfo var3 = MemberInfo.parseAndValidate(var2, var1.getContext());
      this.target = var3.toCtorType();
      this.desc = var3.toCtorDesc();
   }

   public boolean hasDescriptor() {
      boolean var10000;
      try {
         if (this.desc != null) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public boolean find(String param1, InsnList param2, Collection<AbstractInsnNode> param3) {
      // $FF: Couldn't be decompiled
   }

   protected boolean findCtor(InsnList param1, TypeInsnNode param2) {
      // $FF: Couldn't be decompiled
   }

   private boolean matchesOwner(TypeInsnNode param1) {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

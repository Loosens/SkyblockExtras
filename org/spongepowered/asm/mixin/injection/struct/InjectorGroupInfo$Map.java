package org.spongepowered.asm.mixin.injection.struct;

import java.util.HashMap;
import java.util.Iterator;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.throwables.InjectionValidationException;
import org.spongepowered.asm.util.Annotations;

public final class InjectorGroupInfo$Map extends HashMap<String, InjectorGroupInfo> {
   private static final long serialVersionUID = 1L;
   private static final InjectorGroupInfo NO_GROUP = new InjectorGroupInfo("NONE", true);

   public InjectorGroupInfo get(Object var1) {
      return this.forName(var1.toString());
   }

   public InjectorGroupInfo forName(String var1) {
      InjectorGroupInfo var2 = (InjectorGroupInfo)super.get(var1);
      if (var2 == null) {
         var2 = new InjectorGroupInfo(var1);
         this.put(var1, var2);
      }

      return var2;
   }

   public InjectorGroupInfo parseGroup(MethodNode var1, String var2) {
      return this.parseGroup(Annotations.getInvisible(var1, Group.class), var2);
   }

   public InjectorGroupInfo parseGroup(AnnotationNode param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public void validateAll() throws InjectionValidationException {
      Iterator var1 = this.values().iterator();

      while(var1.hasNext()) {
         InjectorGroupInfo var2 = (InjectorGroupInfo)var1.next();
         var2.validate();
      }

   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

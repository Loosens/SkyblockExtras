package org.spongepowered.asm.mixin.transformer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.Annotations;

public final class InterfaceInfo {
   private final MixinInfo mixin;
   private final String prefix;
   private final Type iface;
   private final boolean unique;
   private Set<String> methods;

   private InterfaceInfo(MixinInfo param1, String param2, Type param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   private void initMethods() {
      this.methods = new HashSet();
      this.readInterface(this.iface.getInternalName());
   }

   private void readInterface(String var1) {
      ClassInfo var2 = ClassInfo.forName(var1);
      Iterator var3 = var2.getMethods().iterator();

      while(var3.hasNext()) {
         ClassInfo$Method var4 = (ClassInfo$Method)var3.next();
         this.methods.add(var4.toString());
      }

      var3 = var2.getInterfaces().iterator();

      while(var3.hasNext()) {
         String var5 = (String)var3.next();
         this.readInterface(var5);
      }

   }

   public String getPrefix() {
      return this.prefix;
   }

   public Type getIface() {
      return this.iface;
   }

   public String getName() {
      return this.iface.getClassName();
   }

   public String getInternalName() {
      return this.iface.getInternalName();
   }

   public boolean isUnique() {
      return this.unique;
   }

   public boolean renameMethod(MethodNode param1) {
      // $FF: Couldn't be decompiled
   }

   private void decorateUniqueMethod(MethodNode var1) {
      try {
         if (!this.unique) {
            return;
         }
      } catch (InvalidMixinException var3) {
         throw b(var3);
      }

      try {
         if (Annotations.getVisible(var1, Unique.class) == null) {
            Annotations.setVisible(var1, Unique.class);
            this.mixin.getClassInfo().findMethod(var1).setUnique(true);
         }

      } catch (InvalidMixinException var2) {
         throw b(var2);
      }
   }

   static InterfaceInfo fromAnnotation(MixinInfo param0, AnnotationNode param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean equals(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public int hashCode() {
      int var1 = this.mixin.hashCode();
      var1 = 31 * var1 + this.prefix.hashCode();
      var1 = 31 * var1 + this.iface.hashCode();
      return var1;
   }

   private static InvalidMixinException b(InvalidMixinException var0) {
      return var0;
   }
}

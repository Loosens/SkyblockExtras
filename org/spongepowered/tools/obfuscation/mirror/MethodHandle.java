package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.base.Strings;
import javax.lang.model.element.ExecutableElement;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;

public class MethodHandle extends MemberHandle<MappingMethod> {
   private final ExecutableElement element;
   private final TypeHandle ownerHandle;

   public MethodHandle(TypeHandle var1, ExecutableElement var2) {
      this(var1, var2, TypeUtils.getName(var2), TypeUtils.getDescriptor(var2));
   }

   public MethodHandle(TypeHandle var1, String var2, String var3) {
      this(var1, (ExecutableElement)null, var2, var3);
   }

   private MethodHandle(TypeHandle var1, ExecutableElement var2, String var3, String var4) {
      super(var1 != null ? var1.getName() : null, var3, var4);
      this.element = var2;
      this.ownerHandle = var1;
   }

   public boolean isImaginary() {
      boolean var10000;
      try {
         if (this.element == null) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public ExecutableElement getElement() {
      return this.element;
   }

   public Visibility getVisibility() {
      return TypeUtils.getVisibility(this.element);
   }

   public MappingMethod asMapping(boolean param1) {
      // $FF: Couldn't be decompiled
   }

   public String toString() {
      String var10000;
      label16: {
         try {
            if (this.getOwner() != null) {
               var10000 = "L" + this.getOwner() + ";";
               break label16;
            }
         } catch (RuntimeException var4) {
            throw b(var4);
         }

         var10000 = "";
      }

      String var1 = var10000;
      String var2 = Strings.nullToEmpty(this.getName());
      String var3 = Strings.nullToEmpty(this.getDesc());
      return String.format("%s%s%s", var1, var2, var3);
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

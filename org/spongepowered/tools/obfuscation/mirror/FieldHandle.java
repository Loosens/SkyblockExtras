package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.base.Strings;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;

public class FieldHandle extends MemberHandle<MappingField> {
   private final VariableElement element;
   private final boolean rawType;

   public FieldHandle(TypeElement var1, VariableElement var2) {
      this(TypeUtils.getInternalName(var1), var2);
   }

   public FieldHandle(String var1, VariableElement var2) {
      this(var1, var2, false);
   }

   public FieldHandle(TypeElement var1, VariableElement var2, boolean var3) {
      this(TypeUtils.getInternalName(var1), var2, var3);
   }

   public FieldHandle(String var1, VariableElement var2, boolean var3) {
      this(var1, var2, var3, TypeUtils.getName(var2), TypeUtils.getInternalName(var2));
   }

   public FieldHandle(String var1, String var2, String var3) {
      this(var1, (VariableElement)null, false, var2, var3);
   }

   private FieldHandle(String var1, VariableElement var2, boolean var3, String var4, String var5) {
      super(var1, var4, var5);
      this.element = var2;
      this.rawType = var3;
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

   public VariableElement getElement() {
      return this.element;
   }

   public Visibility getVisibility() {
      return TypeUtils.getVisibility(this.element);
   }

   public boolean isRawType() {
      return this.rawType;
   }

   public MappingField asMapping(boolean var1) {
      MappingField var10000;
      MappingField var10001;
      String var10002;
      label16: {
         try {
            var10000 = new MappingField;
            var10001 = var10000;
            if (var1) {
               var10002 = this.getOwner();
               break label16;
            }
         } catch (RuntimeException var2) {
            throw b(var2);
         }

         var10002 = null;
      }

      var10001.<init>(var10002, this.getName(), this.getDesc());
      return var10000;
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
      return String.format("%s%s:%s", var1, var2, var3);
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

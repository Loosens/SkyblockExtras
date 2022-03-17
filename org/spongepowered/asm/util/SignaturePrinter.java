package org.spongepowered.asm.util;

import com.google.common.base.Strings;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;

public class SignaturePrinter {
   private final String name;
   private final Type returnType;
   private final Type[] argTypes;
   private final String[] argNames;
   private String modifiers;
   private boolean fullyQualified;

   public SignaturePrinter(MethodNode var1) {
      this(var1.name, Type.VOID_TYPE, Type.getArgumentTypes(var1.desc));
      this.setModifiers(var1);
   }

   public SignaturePrinter(MethodNode var1, String[] var2) {
      this(var1.name, Type.VOID_TYPE, Type.getArgumentTypes(var1.desc), var2);
      this.setModifiers(var1);
   }

   public SignaturePrinter(MemberInfo var1) {
      this(var1.name, var1.desc);
   }

   public SignaturePrinter(String var1, String var2) {
      this(var1, Type.getReturnType(var2), Type.getArgumentTypes(var2));
   }

   public SignaturePrinter(String param1, Type param2, Type[] param3) {
      // $FF: Couldn't be decompiled
   }

   public SignaturePrinter(String param1, Type param2, LocalVariableNode[] param3) {
      // $FF: Couldn't be decompiled
   }

   public SignaturePrinter(String var1, Type var2, Type[] var3, String[] var4) {
      this.modifiers = "private void";
      this.name = var1;
      this.returnType = var2;
      this.argTypes = var3;
      this.argNames = var4;
      if (this.argTypes.length > this.argNames.length) {
         throw new IllegalArgumentException(String.format("Types array length must not exceed names array length! (names=%d, types=%d)", this.argNames.length, this.argTypes.length));
      }
   }

   public String getFormattedArgs() {
      return this.appendArgs(new StringBuilder(), true, true).toString();
   }

   public String getReturnType() {
      return getTypeName(this.returnType, false, this.fullyQualified);
   }

   public void setModifiers(MethodNode var1) {
      String var2 = getTypeName(Type.getReturnType(var1.desc), false, this.fullyQualified);

      try {
         if ((var1.access & 1) != 0) {
            this.setModifiers("public " + var2);
            return;
         }
      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }

      try {
         if ((var1.access & 4) != 0) {
            this.setModifiers("protected " + var2);
            return;
         }
      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }

      try {
         if ((var1.access & 2) != 0) {
            this.setModifiers("private " + var2);
            return;
         }
      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }

      this.setModifiers(var2);
   }

   public SignaturePrinter setModifiers(String var1) {
      this.modifiers = var1.replace("${returnType}", this.getReturnType());
      return this;
   }

   public SignaturePrinter setFullyQualified(boolean var1) {
      this.fullyQualified = var1;
      return this;
   }

   public boolean isFullyQualified() {
      return this.fullyQualified;
   }

   public String toString() {
      return this.appendArgs((new StringBuilder()).append(this.modifiers).append(" ").append(this.name), false, true).toString();
   }

   public String toDescriptor() {
      StringBuilder var1 = this.appendArgs(new StringBuilder(), true, false);
      return var1.append(getTypeName(this.returnType, false, this.fullyQualified)).toString();
   }

   private StringBuilder appendArgs(StringBuilder param1, boolean param2, boolean param3) {
      // $FF: Couldn't be decompiled
   }

   private StringBuilder appendType(StringBuilder var1, Type var2, String var3) {
      try {
         switch(var2.getSort()) {
         case 9:
            return appendArraySuffix(this.appendType(var1, var2.getElementType(), var3), var2);
         case 10:
            return this.appendType(var1, var2.getClassName(), var3);
         }
      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }

      try {
         var1.append(getTypeName(var2, false, this.fullyQualified));
         if (var3 != null) {
            var1.append(' ').append(var3);
         }

         return var1;
      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }
   }

   private StringBuilder appendType(StringBuilder var1, String var2, String var3) {
      if (!this.fullyQualified) {
         var2 = var2.substring(var2.lastIndexOf(46) + 1);
      }

      try {
         var1.append(var2);
         if (var2.endsWith("CallbackInfoReturnable")) {
            var1.append('<').append(getTypeName(this.returnType, true, this.fullyQualified)).append('>');
         }
      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }

      try {
         if (var3 != null) {
            var1.append(' ').append(var3);
         }

         return var1;
      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }
   }

   public static String getTypeName(Type var0, boolean var1) {
      return getTypeName(var0, var1, false);
   }

   public static String getTypeName(Type param0, boolean param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   private static String arraySuffix(Type var0) {
      return Strings.repeat("[]", var0.getDimensions());
   }

   private static StringBuilder appendArraySuffix(StringBuilder var0, Type var1) {
      int var2 = 0;

      try {
         while(var2 < var1.getDimensions()) {
            var0.append("[]");
            ++var2;
         }

         return var0;
      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

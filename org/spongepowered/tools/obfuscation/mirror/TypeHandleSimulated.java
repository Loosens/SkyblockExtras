package org.spongepowered.tools.obfuscation.mirror;

import java.lang.annotation.Annotation;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.SignaturePrinter;

public class TypeHandleSimulated extends TypeHandle {
   private final TypeElement simulatedType;

   public TypeHandleSimulated(String var1, TypeMirror var2) {
      this(TypeUtils.getPackage(var2), var1, var2);
   }

   public TypeHandleSimulated(PackageElement var1, String var2, TypeMirror var3) {
      super(var1, var2);
      this.simulatedType = (TypeElement)((DeclaredType)var3).asElement();
   }

   protected TypeElement getTargetElement() {
      return this.simulatedType;
   }

   public boolean isPublic() {
      return true;
   }

   public boolean isImaginary() {
      return false;
   }

   public boolean isSimulated() {
      return true;
   }

   public AnnotationHandle getAnnotation(Class<? extends Annotation> var1) {
      return null;
   }

   public TypeHandle getSuperclass() {
      return null;
   }

   public String findDescriptor(MemberInfo var1) {
      String var10000;
      try {
         if (var1 != null) {
            var10000 = var1.desc;
            return var10000;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      var10000 = null;
      return var10000;
   }

   public FieldHandle findField(String var1, String var2, boolean var3) {
      return new FieldHandle((String)null, var1, var2);
   }

   public MethodHandle findMethod(String var1, String var2, boolean var3) {
      return new MethodHandle((TypeHandle)null, var1, var2);
   }

   public MappingMethod getMappingMethod(String var1, String var2) {
      String var3 = (new SignaturePrinter(var1, var2)).setFullyQualified(true).toDescriptor();
      String var4 = TypeUtils.stripGenerics(var3);
      MethodHandle var5 = findMethodRecursive((TypeHandle)this, var1, var3, var4, true);

      MappingMethod var10000;
      try {
         if (var5 != null) {
            var10000 = var5.asMapping(true);
            return var10000;
         }
      } catch (RuntimeException var6) {
         throw b(var6);
      }

      var10000 = super.getMappingMethod(var1, var2);
      return var10000;
   }

   private static MethodHandle findMethodRecursive(TypeHandle param0, String param1, String param2, String param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   private static MethodHandle findMethodRecursive(TypeMirror var0, String var1, String var2, String var3, boolean var4) {
      try {
         if (!(var0 instanceof DeclaredType)) {
            return null;
         }
      } catch (RuntimeException var6) {
         throw b(var6);
      }

      TypeElement var5 = (TypeElement)((DeclaredType)var0).asElement();
      return findMethodRecursive(new TypeHandle(var5), var1, var2, var3, var4);
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mirror.mapping.ResolvableMappingMethod;

public class TypeHandle {
   private final String name;
   private final PackageElement pkg;
   private final TypeElement element;
   private TypeReference reference;

   public TypeHandle(PackageElement var1, String var2) {
      this.name = var2.replace('.', '/');
      this.pkg = var1;
      this.element = null;
   }

   public TypeHandle(TypeElement var1) {
      this.pkg = TypeUtils.getPackage(var1);
      this.name = TypeUtils.getInternalName(var1);
      this.element = var1;
   }

   public TypeHandle(DeclaredType var1) {
      this((TypeElement)var1.asElement());
   }

   public final String toString() {
      return this.name.replace('/', '.');
   }

   public final String getName() {
      return this.name;
   }

   public final PackageElement getPackage() {
      return this.pkg;
   }

   public final TypeElement getElement() {
      return this.element;
   }

   protected TypeElement getTargetElement() {
      return this.element;
   }

   public AnnotationHandle getAnnotation(Class<? extends Annotation> var1) {
      return AnnotationHandle.of(this.getTargetElement(), var1);
   }

   public final List<? extends Element> getEnclosedElements() {
      return getEnclosedElements(this.getTargetElement());
   }

   public <T extends Element> List<T> getEnclosedElements(ElementKind... var1) {
      return getEnclosedElements(this.getTargetElement(), var1);
   }

   public TypeMirror getType() {
      TypeMirror var10000;
      try {
         if (this.getTargetElement() != null) {
            var10000 = this.getTargetElement().asType();
            return var10000;
         }
      } catch (NullPointerException var1) {
         throw b(var1);
      }

      var10000 = null;
      return var10000;
   }

   public TypeHandle getSuperclass() {
      // $FF: Couldn't be decompiled
   }

   public List<TypeHandle> getInterfaces() {
      try {
         if (this.getTargetElement() == null) {
            return Collections.emptyList();
         }
      } catch (NullPointerException var4) {
         throw b(var4);
      }

      Builder var1 = ImmutableList.builder();
      Iterator var2 = this.getTargetElement().getInterfaces().iterator();

      while(var2.hasNext()) {
         TypeMirror var3 = (TypeMirror)var2.next();
         var1.add(new TypeHandle((DeclaredType)var3));
      }

      return var1.build();
   }

   public boolean isPublic() {
      // $FF: Couldn't be decompiled
   }

   public boolean isImaginary() {
      boolean var10000;
      try {
         if (this.getTargetElement() == null) {
            var10000 = true;
            return var10000;
         }
      } catch (NullPointerException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public boolean isSimulated() {
      return false;
   }

   public final TypeReference getReference() {
      try {
         if (this.reference == null) {
            this.reference = new TypeReference(this);
         }
      } catch (NullPointerException var1) {
         throw b(var1);
      }

      return this.reference;
   }

   public MappingMethod getMappingMethod(String var1, String var2) {
      return new ResolvableMappingMethod(this, var1, var2);
   }

   public String findDescriptor(MemberInfo var1) {
      String var2 = var1.desc;
      if (var2 == null) {
         Iterator var3 = this.getEnclosedElements(ElementKind.METHOD).iterator();

         while(var3.hasNext()) {
            ExecutableElement var4 = (ExecutableElement)var3.next();
            if (var4.getSimpleName().toString().equals(var1.name)) {
               var2 = TypeUtils.getDescriptor(var4);
               break;
            }
         }
      }

      return var2;
   }

   public final FieldHandle findField(VariableElement var1) {
      return this.findField(var1, true);
   }

   public final FieldHandle findField(VariableElement var1, boolean var2) {
      return this.findField(var1.getSimpleName().toString(), TypeUtils.getTypeName(var1.asType()), var2);
   }

   public final FieldHandle findField(String var1, String var2) {
      return this.findField(var1, var2, true);
   }

   public FieldHandle findField(String var1, String var2, boolean var3) {
      String var4 = TypeUtils.stripGenerics(var2);
      Iterator var5 = this.getEnclosedElements(ElementKind.FIELD).iterator();

      while(var5.hasNext()) {
         VariableElement var6 = (VariableElement)var5.next();

         try {
            if (compareElement(var6, var1, var2, var3)) {
               return new FieldHandle(this.getTargetElement(), var6);
            }
         } catch (NullPointerException var8) {
            throw b(var8);
         }

         try {
            if (compareElement(var6, var1, var4, var3)) {
               return new FieldHandle(this.getTargetElement(), var6, true);
            }
         } catch (NullPointerException var7) {
            throw b(var7);
         }
      }

      return null;
   }

   public final MethodHandle findMethod(ExecutableElement var1) {
      return this.findMethod(var1, true);
   }

   public final MethodHandle findMethod(ExecutableElement var1, boolean var2) {
      return this.findMethod(var1.getSimpleName().toString(), TypeUtils.getJavaSignature((Element)var1), var2);
   }

   public final MethodHandle findMethod(String var1, String var2) {
      return this.findMethod(var1, var2, true);
   }

   public MethodHandle findMethod(String var1, String var2, boolean var3) {
      String var4 = TypeUtils.stripGenerics(var2);
      return findMethod(this, var1, var2, var4, var3);
   }

   protected static MethodHandle findMethod(TypeHandle param0, String param1, String param2, String param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   protected static boolean compareElement(Element param0, String param1, String param2, boolean param3) {
      // $FF: Couldn't be decompiled
   }

   protected static <T extends Element> List<T> getEnclosedElements(TypeElement param0, ElementKind... param1) {
      // $FF: Couldn't be decompiled
   }

   protected static List<? extends Element> getEnclosedElements(TypeElement var0) {
      List var10000;
      try {
         if (var0 != null) {
            var10000 = var0.getEnclosedElements();
            return var10000;
         }
      } catch (NullPointerException var1) {
         throw b(var1);
      }

      var10000 = Collections.emptyList();
      return var10000;
   }

   private static NullPointerException b(NullPointerException var0) {
      return var0;
   }
}

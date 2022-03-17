package org.spongepowered.tools.obfuscation.validation;

import java.util.Collection;
import java.util.Iterator;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.tools.obfuscation.MixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator$ValidationPass;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

public class TargetValidator extends MixinValidator {
   public TargetValidator(IMixinAnnotationProcessor var1) {
      super(var1, IMixinValidator$ValidationPass.LATE);
   }

   public boolean validate(TypeElement var1, AnnotationHandle var2, Collection<TypeHandle> var3) {
      try {
         if ("true".equalsIgnoreCase(this.options.getOption("disableTargetValidator"))) {
            return true;
         }
      } catch (RuntimeException var4) {
         throw c(var4);
      }

      try {
         if (var1.getKind() == ElementKind.INTERFACE) {
            this.validateInterfaceMixin(var1, var3);
            return true;
         }
      } catch (RuntimeException var5) {
         throw c(var5);
      }

      this.validateClassMixin(var1, var3);
      return true;
   }

   private void validateInterfaceMixin(TypeElement param1, Collection<TypeHandle> param2) {
      // $FF: Couldn't be decompiled
   }

   private void validateClassMixin(TypeElement param1, Collection<TypeHandle> param2) {
      // $FF: Couldn't be decompiled
   }

   private boolean validateSuperClass(TypeMirror var1, TypeMirror var2) {
      try {
         if (TypeUtils.isAssignable(this.processingEnv, var1, var2)) {
            return true;
         }
      } catch (RuntimeException var3) {
         throw c(var3);
      }

      return this.validateSuperClassRecursive(var1, var2);
   }

   private boolean validateSuperClassRecursive(TypeMirror var1, TypeMirror var2) {
      try {
         if (!(var1 instanceof DeclaredType)) {
            return false;
         }
      } catch (RuntimeException var6) {
         throw c(var6);
      }

      try {
         if (TypeUtils.isAssignable(this.processingEnv, var1, var2)) {
            return true;
         }
      } catch (RuntimeException var8) {
         throw c(var8);
      }

      TypeElement var3 = (TypeElement)((DeclaredType)var1).asElement();
      TypeMirror var4 = var3.getSuperclass();

      try {
         if (var4.getKind() == TypeKind.NONE) {
            return false;
         }
      } catch (RuntimeException var5) {
         throw c(var5);
      }

      try {
         if (this.checkMixinsFor(var4, var2)) {
            return true;
         }
      } catch (RuntimeException var7) {
         throw c(var7);
      }

      return this.validateSuperClassRecursive(var4, var2);
   }

   private boolean checkMixinsFor(TypeMirror var1, TypeMirror var2) {
      Iterator var3 = this.getMixinsTargeting(var1).iterator();

      while(var3.hasNext()) {
         TypeMirror var4 = (TypeMirror)var3.next();

         try {
            if (TypeUtils.isAssignable(this.processingEnv, var4, var2)) {
               return true;
            }
         } catch (RuntimeException var5) {
            throw c(var5);
         }
      }

      return false;
   }

   private static RuntimeException c(RuntimeException var0) {
      return var0;
   }
}

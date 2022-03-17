package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.ConstraintParser;
import org.spongepowered.asm.util.ConstraintParser$Constraint;
import org.spongepowered.asm.util.throwables.ConstraintViolationException;
import org.spongepowered.asm.util.throwables.InvalidConstraintException;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.FieldHandle;
import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

abstract class AnnotatedMixinElementHandler {
   protected final AnnotatedMixin mixin;
   protected final String classRef;
   protected final IMixinAnnotationProcessor ap;
   protected final IObfuscationManager obf;
   private IMappingConsumer mappings;

   AnnotatedMixinElementHandler(IMixinAnnotationProcessor var1, AnnotatedMixin var2) {
      this.ap = var1;
      this.mixin = var2;
      this.classRef = var2.getClassRef();
      this.obf = var1.getObfuscationManager();
   }

   private IMappingConsumer getMappings() {
      if (this.mappings == null) {
         IMappingConsumer var1 = this.mixin.getMappings();

         try {
            if (var1 instanceof Mappings) {
               this.mappings = ((Mappings)var1).asUnique();
               return this.mappings;
            }
         } catch (InvalidConstraintException var2) {
            throw b(var2);
         }

         this.mappings = var1;
      }

      return this.mappings;
   }

   protected final void addFieldMappings(String var1, String var2, ObfuscationData<MappingField> var3) {
      Iterator var4 = var3.iterator();

      while(var4.hasNext()) {
         ObfuscationType var5 = (ObfuscationType)var4.next();
         MappingField var6 = (MappingField)var3.get(var5);
         this.addFieldMapping(var5, var1, var6.getSimpleName(), var2, var6.getDesc());
      }

   }

   protected final void addFieldMapping(ObfuscationType var1, AnnotatedMixinElementHandler$ShadowElementName var2, String var3, String var4) {
      this.addFieldMapping(var1, var2.name(), var2.obfuscated(), var3, var4);
   }

   protected final void addFieldMapping(ObfuscationType var1, String var2, String var3, String var4, String var5) {
      MappingField var6 = new MappingField(this.classRef, var2, var4);
      MappingField var7 = new MappingField(this.classRef, var3, var5);
      this.getMappings().addFieldMapping(var1, var6, var7);
   }

   protected final void addMethodMappings(String var1, String var2, ObfuscationData<MappingMethod> var3) {
      Iterator var4 = var3.iterator();

      while(var4.hasNext()) {
         ObfuscationType var5 = (ObfuscationType)var4.next();
         MappingMethod var6 = (MappingMethod)var3.get(var5);
         this.addMethodMapping(var5, var1, var6.getSimpleName(), var2, var6.getDesc());
      }

   }

   protected final void addMethodMapping(ObfuscationType var1, AnnotatedMixinElementHandler$ShadowElementName var2, String var3, String var4) {
      this.addMethodMapping(var1, var2.name(), var2.obfuscated(), var3, var4);
   }

   protected final void addMethodMapping(ObfuscationType var1, String var2, String var3, String var4, String var5) {
      MappingMethod var6 = new MappingMethod(this.classRef, var2, var4);
      MappingMethod var7 = new MappingMethod(this.classRef, var3, var5);
      this.getMappings().addMethodMapping(var1, var6, var7);
   }

   protected final void checkConstraints(ExecutableElement var1, AnnotationHandle var2) {
      try {
         ConstraintParser$Constraint var3 = ConstraintParser.parse((String)var2.getValue("constraints"));

         try {
            var3.check(this.ap.getTokenProvider());
         } catch (ConstraintViolationException var5) {
            this.ap.printMessage(Kind.ERROR, var5.getMessage(), var1, var2.asMirror());
         }
      } catch (InvalidConstraintException var6) {
         this.ap.printMessage(Kind.WARNING, var6.getMessage(), var1, var2.asMirror());
      }

   }

   protected final void validateTarget(Element var1, AnnotationHandle var2, AnnotatedMixinElementHandler$AliasedElementName var3, String var4) {
      try {
         if (var1 instanceof ExecutableElement) {
            this.validateTargetMethod((ExecutableElement)var1, var2, var3, var4, false, false);
            return;
         }
      } catch (InvalidConstraintException var6) {
         throw b(var6);
      }

      try {
         if (var1 instanceof VariableElement) {
            this.validateTargetField((VariableElement)var1, var2, var3, var4);
         }
      } catch (InvalidConstraintException var5) {
         throw b(var5);
      }

   }

   protected final void validateTargetMethod(ExecutableElement param1, AnnotationHandle param2, AnnotatedMixinElementHandler$AliasedElementName param3, String param4, boolean param5, boolean param6) {
      // $FF: Couldn't be decompiled
   }

   private void validateMethodVisibility(ExecutableElement param1, AnnotationHandle param2, String param3, TypeHandle param4, MethodHandle param5) {
      // $FF: Couldn't be decompiled
   }

   protected final void validateTargetField(VariableElement var1, AnnotationHandle var2, AnnotatedMixinElementHandler$AliasedElementName var3, String var4) {
      String var5 = var1.asType().toString();
      Iterator var6 = this.mixin.getTargets().iterator();

      while(var6.hasNext()) {
         TypeHandle var7 = (TypeHandle)var6.next();

         try {
            if (var7.isImaginary()) {
               continue;
            }
         } catch (InvalidConstraintException var14) {
            throw b(var14);
         }

         FieldHandle var8 = var7.findField(var1);

         try {
            if (var8 != null) {
               continue;
            }
         } catch (InvalidConstraintException var13) {
            throw b(var13);
         }

         List var9 = var3.getAliases();
         Iterator var10 = var9.iterator();

         while(var10.hasNext()) {
            String var11 = (String)var10.next();
            if ((var8 = var7.findField(var11, var5)) != null) {
               break;
            }
         }

         try {
            if (var8 == null) {
               this.ap.printMessage(Kind.WARNING, "Cannot find target for " + var4 + " field in " + var7, var1, var2.asMirror());
            }
         } catch (InvalidConstraintException var12) {
            throw b(var12);
         }
      }

   }

   protected final void validateReferencedTarget(ExecutableElement var1, AnnotationHandle var2, MemberInfo var3, String var4) {
      String var5 = var3.toDescriptor();
      Iterator var6 = this.mixin.getTargets().iterator();

      while(var6.hasNext()) {
         TypeHandle var7 = (TypeHandle)var6.next();

         try {
            if (var7.isImaginary()) {
               continue;
            }
         } catch (InvalidConstraintException var10) {
            throw b(var10);
         }

         MethodHandle var8 = var7.findMethod(var3.name, var5);

         try {
            if (var8 == null) {
               this.ap.printMessage(Kind.WARNING, "Cannot find target method for " + var4 + " in " + var7, var1, var2.asMirror());
            }
         } catch (InvalidConstraintException var9) {
            throw b(var9);
         }
      }

   }

   private void printMessage(Kind var1, String var2, Element var3, AnnotationHandle var4) {
      try {
         if (var4 == null) {
            this.ap.printMessage(var1, var2, var3);
            return;
         }
      } catch (InvalidConstraintException var5) {
         throw b(var5);
      }

      this.ap.printMessage(var1, var2, var3, var4.asMirror());
   }

   protected static <T extends IMapping<T>> ObfuscationData<T> stripOwnerData(ObfuscationData<T> var0) {
      ObfuscationData var1 = new ObfuscationData();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         ObfuscationType var3 = (ObfuscationType)var2.next();
         IMapping var4 = (IMapping)var0.get(var3);
         var1.put(var3, var4.move((String)null));
      }

      return var1;
   }

   protected static <T extends IMapping<T>> ObfuscationData<T> stripDescriptors(ObfuscationData<T> var0) {
      ObfuscationData var1 = new ObfuscationData();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         ObfuscationType var3 = (ObfuscationType)var2.next();
         IMapping var4 = (IMapping)var0.get(var3);
         var1.put(var3, var4.transform((String)null));
      }

      return var1;
   }

   private static InvalidConstraintException b(InvalidConstraintException var0) {
      return var0;
   }
}

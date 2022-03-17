package org.spongepowered.tools.obfuscation;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.asm.mixin.gen.AccessorInfo$AccessorType;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

class AnnotatedMixinElementHandlerAccessor$AnnotatedElementAccessor extends AnnotatedMixinElementHandler$AnnotatedElement<ExecutableElement> {
   private final boolean shouldRemap;
   private final TypeMirror returnType;
   private String targetName;

   public AnnotatedMixinElementHandlerAccessor$AnnotatedElementAccessor(ExecutableElement var1, AnnotationHandle var2, boolean var3) {
      super(var1, var2);
      this.shouldRemap = var3;
      this.returnType = ((ExecutableElement)this.getElement()).getReturnType();
   }

   public boolean shouldRemap() {
      return this.shouldRemap;
   }

   public String getAnnotationValue() {
      return (String)this.getAnnotation().getValue();
   }

   public TypeMirror getTargetType() {
      // $FF: Couldn't be decompiled
   }

   public String getTargetTypeName() {
      return TypeUtils.getTypeName(this.getTargetType());
   }

   public String getAccessorDesc() {
      return TypeUtils.getInternalName(this.getTargetType());
   }

   public MemberInfo getContext() {
      return new MemberInfo(this.getTargetName(), (String)null, this.getAccessorDesc());
   }

   public AccessorInfo$AccessorType getAccessorType() {
      AccessorInfo$AccessorType var10000;
      try {
         if (this.returnType.getKind() == TypeKind.VOID) {
            var10000 = AccessorInfo$AccessorType.FIELD_SETTER;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = AccessorInfo$AccessorType.FIELD_GETTER;
      return var10000;
   }

   public void setTargetName(String var1) {
      this.targetName = var1;
   }

   public String getTargetName() {
      return this.targetName;
   }

   public String toString() {
      String var10000;
      try {
         if (this.targetName != null) {
            var10000 = this.targetName;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = "<invalid>";
      return var10000;
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

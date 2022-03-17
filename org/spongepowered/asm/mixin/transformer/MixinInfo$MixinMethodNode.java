package org.spongepowered.asm.mixin.transformer;

import java.lang.annotation.Annotation;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.Surrogate;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

class MixinInfo$MixinMethodNode extends MethodNode {
   private final String originalName;
   final MixinInfo this$0;

   public MixinInfo$MixinMethodNode(MixinInfo var1, int var2, String var3, String var4, String var5, String[] var6) {
      super(327680, var2, var3, var4, var5, var6);
      this.this$0 = var1;
      this.originalName = var3;
   }

   public String toString() {
      return String.format("%s%s", this.originalName, this.desc);
   }

   public String getOriginalName() {
      return this.originalName;
   }

   public boolean isInjector() {
      // $FF: Couldn't be decompiled
   }

   public boolean isSurrogate() {
      boolean var10000;
      try {
         if (this.getVisibleAnnotation(Surrogate.class) != null) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public boolean isSynthetic() {
      return Bytecode.hasFlag((MethodNode)this, 4096);
   }

   public AnnotationNode getVisibleAnnotation(Class<? extends Annotation> var1) {
      return Annotations.getVisible((MethodNode)this, var1);
   }

   public AnnotationNode getInjectorAnnotation() {
      return InjectionInfo.getInjectorAnnotation(this.this$0, this);
   }

   public IMixinInfo getOwner() {
      return this.this$0;
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

package org.spongepowered.asm.mixin.transformer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.MixinEnvironment$CompatibilityLevel;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.Annotations;

class MixinInfo$State {
   private byte[] mixinBytes;
   private final ClassInfo classInfo;
   private boolean detachedSuper;
   private boolean unique;
   protected final Set<String> interfaces;
   protected final List<InterfaceInfo> softImplements;
   protected final Set<String> syntheticInnerClasses;
   protected final Set<String> innerClasses;
   protected MixinInfo$MixinClassNode classNode;
   final MixinInfo this$0;

   MixinInfo$State(MixinInfo var1, byte[] var2) {
      this(var1, var2, (ClassInfo)null);
   }

   MixinInfo$State(MixinInfo var1, byte[] var2, ClassInfo var3) {
      this.this$0 = var1;
      this.interfaces = new HashSet();
      this.softImplements = new ArrayList();
      this.syntheticInnerClasses = new HashSet();
      this.innerClasses = new HashSet();
      this.mixinBytes = var2;
      this.connect();
      this.classInfo = var3 != null ? var3 : ClassInfo.fromClassNode(this.getClassNode());
   }

   private void connect() {
      this.classNode = this.createClassNode(0);
   }

   private void complete() {
      this.classNode = null;
   }

   ClassInfo getClassInfo() {
      return this.classInfo;
   }

   byte[] getClassBytes() {
      return this.mixinBytes;
   }

   MixinInfo$MixinClassNode getClassNode() {
      return this.classNode;
   }

   boolean isDetachedSuper() {
      return this.detachedSuper;
   }

   boolean isUnique() {
      return this.unique;
   }

   List<? extends InterfaceInfo> getSoftImplements() {
      return this.softImplements;
   }

   Set<String> getSyntheticInnerClasses() {
      return this.syntheticInnerClasses;
   }

   Set<String> getInnerClasses() {
      return this.innerClasses;
   }

   Set<String> getInterfaces() {
      return this.interfaces;
   }

   MixinInfo$MixinClassNode createClassNode(int var1) {
      MixinInfo$MixinClassNode var2 = new MixinInfo$MixinClassNode(this.this$0, this.this$0);
      ClassReader var3 = new ClassReader(this.mixinBytes);
      var3.accept(var2, var1);
      return var2;
   }

   void validate(MixinInfo$SubType var1, List<ClassInfo> var2) {
      MixinPreProcessorStandard var3 = var1.createPreProcessor(this.getClassNode()).prepare();
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         ClassInfo var5 = (ClassInfo)var4.next();
         var3.conform(var5);
      }

      MixinInfo$State var10000;
      boolean var10001;
      label19: {
         try {
            var1.validate(this, var2);
            this.detachedSuper = var1.isDetachedSuper();
            var10000 = this;
            if (Annotations.getVisible((ClassNode)this.getClassNode(), Unique.class) != null) {
               var10001 = true;
               break label19;
            }
         } catch (InvalidMixinException var6) {
            throw b(var6);
         }

         var10001 = false;
      }

      var10000.unique = var10001;
      this.validateInner();
      this.validateClassVersion();
      this.validateRemappables(var2);
      this.readImplementations(var1);
      this.readInnerClasses();
      this.validateChanges(var1, var2);
      this.complete();
   }

   private void validateInner() {
      try {
         if (!this.classInfo.isProbablyStatic()) {
            throw new InvalidMixinException(this.this$0, "Inner class mixin must be declared static");
         }
      } catch (InvalidMixinException var1) {
         throw b(var1);
      }
   }

   private void validateClassVersion() {
      if (this.classNode.version > MixinEnvironment.getCompatibilityLevel().classVersion()) {
         String var1 = ".";
         MixinEnvironment$CompatibilityLevel[] var2 = MixinEnvironment$CompatibilityLevel.values();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            MixinEnvironment$CompatibilityLevel var5 = var2[var4];
            if (var5.classVersion() >= this.classNode.version) {
               var1 = String.format(". Mixin requires compatibility level %s or above.", var5.name());
            }
         }

         throw new InvalidMixinException(this.this$0, "Unsupported mixin class version " + this.classNode.version + var1);
      }
   }

   private void validateRemappables(List<ClassInfo> param1) {
      // $FF: Couldn't be decompiled
   }

   private void validateRemappable(Class<Shadow> param1, String param2, AnnotationNode param3) {
      // $FF: Couldn't be decompiled
   }

   void readImplementations(MixinInfo$SubType var1) {
      this.interfaces.addAll(this.classNode.interfaces);
      this.interfaces.addAll(var1.getInterfaces());
      AnnotationNode var2 = Annotations.getInvisible((ClassNode)this.classNode, Implements.class);

      try {
         if (var2 == null) {
            return;
         }
      } catch (InvalidMixinException var9) {
         throw b(var9);
      }

      List var3 = (List)Annotations.getValue(var2);

      try {
         if (var3 == null) {
            return;
         }
      } catch (InvalidMixinException var8) {
         throw b(var8);
      }

      Iterator var4 = var3.iterator();

      while(var4.hasNext()) {
         AnnotationNode var5 = (AnnotationNode)var4.next();
         InterfaceInfo var6 = InterfaceInfo.fromAnnotation(this.this$0, var5);

         try {
            this.softImplements.add(var6);
            this.interfaces.add(var6.getInternalName());
            if (!(this instanceof MixinInfo$Reloaded)) {
               this.classInfo.addInterface(var6.getInternalName());
            }
         } catch (InvalidMixinException var7) {
            throw b(var7);
         }
      }

   }

   void readInnerClasses() {
      // $FF: Couldn't be decompiled
   }

   protected void validateChanges(MixinInfo$SubType var1, List<ClassInfo> var2) {
      var1.createPreProcessor(this.classNode).prepare();
   }

   private static InvalidMixinException b(InvalidMixinException var0) {
      return var0;
   }
}

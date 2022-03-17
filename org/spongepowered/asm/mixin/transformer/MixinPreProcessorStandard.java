package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Strings;
import java.util.Iterator;
import java.util.ListIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.MixinEnvironment$Option;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.transformer.meta.MixinRenamed;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.perf.Profiler;
import org.spongepowered.asm.util.perf.Profiler$Section;

class MixinPreProcessorStandard {
   private static final Logger logger = LogManager.getLogger("mixin");
   protected final MixinInfo mixin;
   protected final MixinInfo$MixinClassNode classNode;
   protected final MixinEnvironment env;
   protected final Profiler profiler = MixinEnvironment.getProfiler();
   private final boolean verboseLogging;
   private final boolean strictUnique;
   private boolean prepared;
   private boolean attached;

   MixinPreProcessorStandard(MixinInfo var1, MixinInfo$MixinClassNode var2) {
      this.mixin = var1;
      this.classNode = var2;
      this.env = var1.getParent().getEnvironment();
      this.verboseLogging = this.env.getOption(MixinEnvironment$Option.DEBUG_VERBOSE);
      this.strictUnique = this.env.getOption(MixinEnvironment$Option.DEBUG_UNIQUE);
   }

   final MixinPreProcessorStandard prepare() {
      try {
         if (this.prepared) {
            return this;
         }
      } catch (IllegalStateException var5) {
         throw b(var5);
      }

      this.prepared = true;
      Profiler$Section var1 = this.profiler.begin("prepare");
      Iterator var2 = this.classNode.mixinMethods.iterator();

      while(var2.hasNext()) {
         MixinInfo$MixinMethodNode var3 = (MixinInfo$MixinMethodNode)var2.next();
         ClassInfo$Method var4 = this.mixin.getClassInfo().findMethod((MethodNode)var3);
         this.prepareMethod(var3, var4);
      }

      var2 = this.classNode.fields.iterator();

      while(var2.hasNext()) {
         FieldNode var6 = (FieldNode)var2.next();
         this.prepareField(var6);
      }

      var1.end();
      return this;
   }

   protected void prepareMethod(MixinInfo$MixinMethodNode var1, ClassInfo$Method var2) {
      this.prepareShadow(var1, var2);
      this.prepareSoftImplements(var1, var2);
   }

   protected void prepareShadow(MixinInfo$MixinMethodNode var1, ClassInfo$Method var2) {
      AnnotationNode var3 = Annotations.getVisible((MethodNode)var1, Shadow.class);

      try {
         if (var3 == null) {
            return;
         }
      } catch (IllegalStateException var6) {
         throw b(var6);
      }

      String var4 = (String)Annotations.getValue(var3, "prefix", Shadow.class);
      if (var1.name.startsWith(var4)) {
         Annotations.setVisible((MethodNode)var1, MixinRenamed.class, "originalName", var1.name);
         String var5 = var1.name.substring(var4.length());
         var1.name = var2.renameTo(var5);
      }

   }

   protected void prepareSoftImplements(MixinInfo$MixinMethodNode var1, ClassInfo$Method var2) {
      Iterator var3 = this.mixin.getSoftImplements().iterator();

      while(var3.hasNext()) {
         InterfaceInfo var4 = (InterfaceInfo)var3.next();

         try {
            if (var4.renameMethod(var1)) {
               var2.renameTo(var1.name);
            }
         } catch (IllegalStateException var5) {
            throw b(var5);
         }
      }

   }

   protected void prepareField(FieldNode var1) {
   }

   final MixinPreProcessorStandard conform(TargetClassContext var1) {
      return this.conform(var1.getClassInfo());
   }

   final MixinPreProcessorStandard conform(ClassInfo var1) {
      Profiler$Section var2 = this.profiler.begin("conform");
      Iterator var3 = this.classNode.mixinMethods.iterator();

      while(var3.hasNext()) {
         MixinInfo$MixinMethodNode var4 = (MixinInfo$MixinMethodNode)var3.next();
         if (var4.isInjector()) {
            ClassInfo$Method var5 = this.mixin.getClassInfo().findMethod((MethodNode)var4, 10);
            this.conformInjector(var1, var4, var5);
         }
      }

      var2.end();
      return this;
   }

   private void conformInjector(ClassInfo var1, MixinInfo$MixinMethodNode var2, ClassInfo$Method var3) {
      MethodMapper var4 = var1.getMethodMapper();
      var4.remapHandlerMethod(this.mixin, var2, var3);
   }

   MixinTargetContext createContextFor(TargetClassContext var1) {
      MixinTargetContext var2 = new MixinTargetContext(this.mixin, this.classNode, var1);
      this.conform(var1);
      this.attach(var2);
      return var2;
   }

   final MixinPreProcessorStandard attach(MixinTargetContext var1) {
      try {
         if (this.attached) {
            throw new IllegalStateException("Preprocessor was already attached");
         }
      } catch (IllegalStateException var4) {
         throw b(var4);
      }

      this.attached = true;
      Profiler$Section var2 = this.profiler.begin("attach");
      Profiler$Section var3 = this.profiler.begin("methods");
      this.attachMethods(var1);
      var3 = var3.next("fields");
      this.attachFields(var1);
      var3 = var3.next("transform");
      this.transform(var1);
      var3.end();
      var2.end();
      return this;
   }

   protected void attachMethods(MixinTargetContext var1) {
      Iterator var2 = this.classNode.mixinMethods.iterator();

      while(var2.hasNext()) {
         MixinInfo$MixinMethodNode var3 = (MixinInfo$MixinMethodNode)var2.next();

         try {
            if (!this.validateMethod(var1, var3)) {
               var2.remove();
               continue;
            }
         } catch (IllegalStateException var6) {
            throw b(var6);
         }

         try {
            if (this.attachInjectorMethod(var1, var3)) {
               var1.addMixinMethod(var3);
               continue;
            }
         } catch (IllegalStateException var9) {
            throw b(var9);
         }

         try {
            if (this.attachAccessorMethod(var1, var3)) {
               var2.remove();
               continue;
            }
         } catch (IllegalStateException var5) {
            throw b(var5);
         }

         try {
            if (this.attachShadowMethod(var1, var3)) {
               var1.addShadowMethod(var3);
               var2.remove();
               continue;
            }
         } catch (IllegalStateException var8) {
            throw b(var8);
         }

         try {
            if (this.attachOverwriteMethod(var1, var3)) {
               var1.addMixinMethod(var3);
               continue;
            }
         } catch (IllegalStateException var4) {
            throw b(var4);
         }

         try {
            if (this.attachUniqueMethod(var1, var3)) {
               var2.remove();
               continue;
            }
         } catch (IllegalStateException var7) {
            throw b(var7);
         }

         this.attachMethod(var1, var3);
         var1.addMixinMethod(var3);
      }

   }

   protected boolean validateMethod(MixinTargetContext var1, MixinInfo$MixinMethodNode var2) {
      return true;
   }

   protected boolean attachInjectorMethod(MixinTargetContext var1, MixinInfo$MixinMethodNode var2) {
      return var2.isInjector();
   }

   protected boolean attachAccessorMethod(MixinTargetContext param1, MixinInfo$MixinMethodNode param2) {
      // $FF: Couldn't be decompiled
   }

   protected boolean attachAccessorMethod(MixinTargetContext param1, MixinInfo$MixinMethodNode param2, MixinPreProcessorStandard$SpecialMethod param3) {
      // $FF: Couldn't be decompiled
   }

   protected boolean attachShadowMethod(MixinTargetContext var1, MixinInfo$MixinMethodNode var2) {
      return this.attachSpecialMethod(var1, var2, MixinPreProcessorStandard$SpecialMethod.SHADOW);
   }

   protected boolean attachOverwriteMethod(MixinTargetContext var1, MixinInfo$MixinMethodNode var2) {
      return this.attachSpecialMethod(var1, var2, MixinPreProcessorStandard$SpecialMethod.OVERWRITE);
   }

   protected boolean attachSpecialMethod(MixinTargetContext param1, MixinInfo$MixinMethodNode param2, MixinPreProcessorStandard$SpecialMethod param3) {
      // $FF: Couldn't be decompiled
   }

   private void conformVisibility(MixinTargetContext param1, MixinInfo$MixinMethodNode param2, MixinPreProcessorStandard$SpecialMethod param3, MethodNode param4) {
      // $FF: Couldn't be decompiled
   }

   protected ClassInfo$Method getSpecialMethod(MixinInfo$MixinMethodNode var1, MixinPreProcessorStandard$SpecialMethod var2) {
      ClassInfo$Method var3 = this.mixin.getClassInfo().findMethod((MethodNode)var1, 10);
      this.checkMethodNotUnique(var3, var2);
      return var3;
   }

   protected void checkMethodNotUnique(ClassInfo$Method var1, MixinPreProcessorStandard$SpecialMethod var2) {
      try {
         if (var1.isUnique()) {
            throw new InvalidMixinException(this.mixin, String.format("%s method %s in %s cannot be @Unique", var2, var1.getName(), this.mixin));
         }
      } catch (IllegalStateException var3) {
         throw b(var3);
      }
   }

   protected void checkMixinNotUnique(MixinInfo$MixinMethodNode var1, MixinPreProcessorStandard$SpecialMethod var2) {
      try {
         if (this.mixin.isUnique()) {
            throw new InvalidMixinException(this.mixin, String.format("%s method %s found in a @Unique mixin %s", var2, var1.name, this.mixin));
         }
      } catch (IllegalStateException var3) {
         throw b(var3);
      }
   }

   protected boolean attachUniqueMethod(MixinTargetContext param1, MixinInfo$MixinMethodNode param2) {
      // $FF: Couldn't be decompiled
   }

   protected void attachMethod(MixinTargetContext param1, MixinInfo$MixinMethodNode param2) {
      // $FF: Couldn't be decompiled
   }

   protected void attachFields(MixinTargetContext param1) {
      // $FF: Couldn't be decompiled
   }

   protected boolean validateField(MixinTargetContext param1, FieldNode param2, AnnotationNode param3) {
      // $FF: Couldn't be decompiled
   }

   protected void transform(MixinTargetContext var1) {
      Iterator var2 = this.classNode.methods.iterator();

      label38:
      while(var2.hasNext()) {
         MethodNode var3 = (MethodNode)var2.next();
         ListIterator var4 = var3.instructions.iterator();

         while(true) {
            AbstractInsnNode var5;
            while(true) {
               if (!var4.hasNext()) {
                  continue label38;
               }

               var5 = (AbstractInsnNode)var4.next();

               try {
                  if (!(var5 instanceof MethodInsnNode)) {
                     break;
                  }

                  this.transformMethod((MethodInsnNode)var5);
               } catch (IllegalStateException var7) {
                  throw b(var7);
               }
            }

            try {
               if (var5 instanceof FieldInsnNode) {
                  this.transformField((FieldInsnNode)var5);
               }
            } catch (IllegalStateException var6) {
               throw b(var6);
            }
         }
      }

   }

   protected void transformMethod(MethodInsnNode param1) {
      // $FF: Couldn't be decompiled
   }

   protected void transformField(FieldInsnNode param1) {
      // $FF: Couldn't be decompiled
   }

   protected static String getDynamicInfo(MethodNode var0) {
      return getDynamicInfo("Method", Annotations.getInvisible(var0, Dynamic.class));
   }

   protected static String getDynamicInfo(FieldNode var0) {
      return getDynamicInfo("Field", Annotations.getInvisible(var0, Dynamic.class));
   }

   private static String getDynamicInfo(String var0, AnnotationNode var1) {
      String var2 = Strings.nullToEmpty((String)Annotations.getValue(var1));
      Type var3 = (Type)Annotations.getValue(var1, "mixin");
      if (var3 != null) {
         var2 = String.format("{%s} %s", var3.getClassName(), var2).trim();
      }

      String var10000;
      try {
         if (var2.length() > 0) {
            var10000 = String.format(" %s is @Dynamic(%s)", var0, var2);
            return var10000;
         }
      } catch (IllegalStateException var4) {
         throw b(var4);
      }

      var10000 = "";
      return var10000;
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

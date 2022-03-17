package org.spongepowered.asm.mixin.transformer;

import com.google.common.collect.ImmutableList;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.SortedSet;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.MixinEnvironment$Option;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.ConstraintParser;
import org.spongepowered.asm.util.ConstraintParser$Constraint;
import org.spongepowered.asm.util.perf.Profiler;
import org.spongepowered.asm.util.perf.Profiler$Section;
import org.spongepowered.asm.util.throwables.ConstraintViolationException;
import org.spongepowered.asm.util.throwables.InvalidConstraintException;

class MixinApplicatorStandard {
   protected static final List<Class<? extends Annotation>> CONSTRAINED_ANNOTATIONS = ImmutableList.of(Overwrite.class, Inject.class, ModifyArg.class, ModifyArgs.class, Redirect.class, ModifyVariable.class, ModifyConstant.class);
   protected static final int[] INITIALISER_OPCODE_BLACKLIST = new int[]{177, 21, 22, 23, 24, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 79, 80, 81, 82, 83, 84, 85, 86};
   protected final Logger logger;
   protected final TargetClassContext context;
   protected final String targetName;
   protected final ClassNode targetClass;
   protected final Profiler profiler;
   protected final boolean mergeSignatures;

   MixinApplicatorStandard(TargetClassContext param1) {
      // $FF: Couldn't be decompiled
   }

   void apply(SortedSet<MixinInfo> var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         MixinInfo var4 = (MixinInfo)var3.next();
         this.logger.log(var4.getLoggingLevel(), "Mixing {} from {} into {}", new Object[]{var4.getName(), var4.getParent(), this.targetName});
         var2.add(var4.createContextFor(this.context));
      }

      var3 = null;

      try {
         Iterator var13 = var2.iterator();

         label46:
         while(true) {
            MixinTargetContext var5;
            if (!var13.hasNext()) {
               MixinApplicatorStandard$ApplicatorPass[] var14 = MixinApplicatorStandard$ApplicatorPass.values();
               int var15 = var14.length;

               for(int var6 = 0; var6 < var15; ++var6) {
                  MixinApplicatorStandard$ApplicatorPass var7 = var14[var6];
                  Profiler$Section var8 = this.profiler.begin("pass", var7.name().toLowerCase());
                  Iterator var9 = var2.iterator();

                  while(var9.hasNext()) {
                     MixinTargetContext var10 = (MixinTargetContext)var9.next();
                     this.applyMixin(var10, var7);
                  }

                  var8.end();
               }

               var13 = var2.iterator();

               while(true) {
                  if (!var13.hasNext()) {
                     break label46;
                  }

                  var5 = (MixinTargetContext)var13.next();
                  var5.postApply(this.targetName, this.targetClass);
               }
            }

            var5 = (MixinTargetContext)var13.next();
            var5.preApply(this.targetName, this.targetClass);
         }
      } catch (InvalidMixinException var11) {
         throw var11;
      } catch (Exception var12) {
         throw new InvalidMixinException(var3, "Unexpecteded " + var12.getClass().getSimpleName() + " whilst applying the mixin class: " + var12.getMessage(), var12);
      }

      this.applySourceMap(this.context);
      this.context.processDebugTasks();
   }

   protected final void applyMixin(MixinTargetContext var1, MixinApplicatorStandard$ApplicatorPass var2) {
      // $FF: Couldn't be decompiled
   }

   protected void applySignature(MixinTargetContext var1) {
      try {
         if (this.mergeSignatures) {
            this.context.mergeSignature(var1.getSignature());
         }

      } catch (InvalidMixinException var2) {
         throw b(var2);
      }
   }

   protected void applyInterfaces(MixinTargetContext var1) {
      Iterator var2 = var1.getInterfaces().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();

         try {
            if (!this.targetClass.interfaces.contains(var3)) {
               this.targetClass.interfaces.add(var3);
               var1.getTargetClassInfo().addInterface(var3);
            }
         } catch (InvalidMixinException var4) {
            throw b(var4);
         }
      }

   }

   protected void applyAttributes(MixinTargetContext var1) {
      try {
         if (var1.shouldSetSourceFile()) {
            this.targetClass.sourceFile = var1.getSourceFile();
         }
      } catch (InvalidMixinException var2) {
         throw b(var2);
      }

      this.targetClass.version = Math.max(this.targetClass.version, var1.getMinRequiredClassVersion());
   }

   protected void applyAnnotations(MixinTargetContext var1) {
      ClassNode var2 = var1.getClassNode();
      Bytecode.mergeAnnotations(var2, this.targetClass);
   }

   protected void applyFields(MixinTargetContext var1) {
      this.mergeShadowFields(var1);
      this.mergeNewFields(var1);
   }

   protected void mergeShadowFields(MixinTargetContext param1) {
      // $FF: Couldn't be decompiled
   }

   protected void mergeNewFields(MixinTargetContext param1) {
      // $FF: Couldn't be decompiled
   }

   protected void applyMethods(MixinTargetContext var1) {
      Iterator var2 = var1.getShadowMethods().iterator();

      MethodNode var3;
      while(var2.hasNext()) {
         var3 = (MethodNode)var2.next();
         this.applyShadowMethod(var1, var3);
      }

      var2 = var1.getMethods().iterator();

      while(var2.hasNext()) {
         var3 = (MethodNode)var2.next();
         this.applyNormalMethod(var1, var3);
      }

   }

   protected void applyShadowMethod(MixinTargetContext var1, MethodNode var2) {
      MethodNode var3 = this.findTargetMethod(var2);

      try {
         if (var3 != null) {
            Bytecode.mergeAnnotations(var2, var3);
         }

      } catch (InvalidMixinException var4) {
         throw b(var4);
      }
   }

   protected void applyNormalMethod(MixinTargetContext var1, MethodNode var2) {
      try {
         var1.transformMethod(var2);
         if (!var2.name.startsWith("<")) {
            this.checkMethodVisibility(var1, var2);
            this.checkMethodConstraints(var1, var2);
            this.mergeMethod(var1, var2);
            return;
         }
      } catch (InvalidMixinException var4) {
         throw b(var4);
      }

      try {
         if ("<clinit>".equals(var2.name)) {
            this.appendInsns(var1, var2);
         }
      } catch (InvalidMixinException var3) {
         throw b(var3);
      }

   }

   protected void mergeMethod(MixinTargetContext param1, MethodNode param2) {
      // $FF: Couldn't be decompiled
   }

   protected boolean isAlreadyMerged(MixinTargetContext param1, MethodNode param2, boolean param3, MethodNode param4) {
      // $FF: Couldn't be decompiled
   }

   protected boolean mergeIntrinsic(MixinTargetContext param1, MethodNode param2, boolean param3, MethodNode param4, AnnotationNode param5) {
      // $FF: Couldn't be decompiled
   }

   protected void displaceIntrinsic(MixinTargetContext param1, MethodNode param2, MethodNode param3) {
      // $FF: Couldn't be decompiled
   }

   protected final void appendInsns(MixinTargetContext param1, MethodNode param2) {
      // $FF: Couldn't be decompiled
   }

   protected void applyInitialisers(MixinTargetContext param1) {
      // $FF: Couldn't be decompiled
   }

   protected MethodNode getConstructor(MixinTargetContext param1) {
      // $FF: Couldn't be decompiled
   }

   private MixinApplicatorStandard$Range getConstructorRange(MethodNode param1) {
      // $FF: Couldn't be decompiled
   }

   protected final Deque<AbstractInsnNode> getInitialiser(MixinTargetContext param1, MethodNode param2) {
      // $FF: Couldn't be decompiled
   }

   protected final void injectInitialiser(MixinTargetContext var1, MethodNode var2, Deque<AbstractInsnNode> var3) {
      Map var4 = Bytecode.cloneLabels(var2.instructions);
      AbstractInsnNode var5 = this.findInitialiserInjectionPoint(var1, var2, var3);

      try {
         if (var5 == null) {
            this.logger.warn("Failed to locate initialiser injection point in <init>{}, initialiser was not mixed in.", new Object[]{var2.desc});
            return;
         }
      } catch (InvalidMixinException var11) {
         throw b(var11);
      }

      Iterator var6 = var3.iterator();

      while(var6.hasNext()) {
         AbstractInsnNode var7 = (AbstractInsnNode)var6.next();

         try {
            if (var7 instanceof LabelNode) {
               continue;
            }
         } catch (InvalidMixinException var9) {
            throw b(var9);
         }

         try {
            if (var7 instanceof JumpInsnNode) {
               throw new InvalidMixinException(var1, "Unsupported JUMP opcode in initialiser in " + var1);
            }
         } catch (InvalidMixinException var10) {
            throw b(var10);
         }

         AbstractInsnNode var8 = var7.clone(var4);
         var2.instructions.insert(var5, var8);
         var5 = var8;
      }

   }

   protected AbstractInsnNode findInitialiserInjectionPoint(MixinTargetContext var1, MethodNode var2, Deque<AbstractInsnNode> var3) {
      HashSet var4 = new HashSet();
      Iterator var5 = var3.iterator();

      while(var5.hasNext()) {
         AbstractInsnNode var6 = (AbstractInsnNode)var5.next();

         try {
            if (var6.getOpcode() == 181) {
               var4.add(fieldKey((FieldInsnNode)var6));
            }
         } catch (InvalidMixinException var12) {
            throw b(var12);
         }
      }

      MixinApplicatorStandard$InitialiserInjectionMode var17 = this.getInitialiserInjectionMode(var1.getEnvironment());
      String var18 = var1.getTargetClassInfo().getName();
      String var7 = var1.getTargetClassInfo().getSuperName();
      AbstractInsnNode var8 = null;
      ListIterator var9 = var2.instructions.iterator();

      while(var9.hasNext()) {
         AbstractInsnNode var10 = (AbstractInsnNode)var9.next();

         String var11;
         label87: {
            try {
               if (var10.getOpcode() == 183 && "<init>".equals(((MethodInsnNode)var10).name)) {
                  break label87;
               }
            } catch (InvalidMixinException var16) {
               throw b(var16);
            }

            try {
               if (var10.getOpcode() != 181 || var17 != MixinApplicatorStandard$InitialiserInjectionMode.DEFAULT) {
                  continue;
               }
            } catch (InvalidMixinException var14) {
               throw b(var14);
            }

            var11 = fieldKey((FieldInsnNode)var10);
            if (var4.contains(var11)) {
               var8 = var10;
            }
            continue;
         }

         var11 = ((MethodInsnNode)var10).owner;

         try {
            if (!var11.equals(var18) && !var11.equals(var7)) {
               continue;
            }
         } catch (InvalidMixinException var15) {
            throw b(var15);
         }

         var8 = var10;

         try {
            if (var17 == MixinApplicatorStandard$InitialiserInjectionMode.SAFE) {
               break;
            }
         } catch (InvalidMixinException var13) {
            throw b(var13);
         }
      }

      return var8;
   }

   private MixinApplicatorStandard$InitialiserInjectionMode getInitialiserInjectionMode(MixinEnvironment var1) {
      String var2 = var1.getOptionValue(MixinEnvironment$Option.INITIALISER_INJECTION_MODE);

      try {
         if (var2 == null) {
            return MixinApplicatorStandard$InitialiserInjectionMode.DEFAULT;
         }
      } catch (Exception var5) {
         throw b(var5);
      }

      try {
         return MixinApplicatorStandard$InitialiserInjectionMode.valueOf(var2.toUpperCase());
      } catch (Exception var4) {
         this.logger.warn("Could not parse unexpected value \"{}\" for mixin.initialiserInjectionMode, reverting to DEFAULT", new Object[]{var2});
         return MixinApplicatorStandard$InitialiserInjectionMode.DEFAULT;
      }
   }

   private static String fieldKey(FieldInsnNode var0) {
      return String.format("%s:%s", var0.desc, var0.name);
   }

   protected void prepareInjections(MixinTargetContext var1) {
      var1.prepareInjections();
   }

   protected void applyInjections(MixinTargetContext var1) {
      var1.applyInjections();
   }

   protected void applyAccessors(MixinTargetContext var1) {
      List var2 = var1.generateAccessors();
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         MethodNode var4 = (MethodNode)var3.next();

         try {
            if (!var4.name.startsWith("<")) {
               this.mergeMethod(var1, var4);
            }
         } catch (InvalidMixinException var5) {
            throw b(var5);
         }
      }

   }

   protected void checkMethodVisibility(MixinTargetContext param1, MethodNode param2) {
      // $FF: Couldn't be decompiled
   }

   protected void applySourceMap(TargetClassContext var1) {
      this.targetClass.sourceDebug = var1.getSourceMap().toString();
   }

   protected void checkMethodConstraints(MixinTargetContext var1, MethodNode var2) {
      Iterator var3 = CONSTRAINED_ANNOTATIONS.iterator();

      while(var3.hasNext()) {
         Class var4 = (Class)var3.next();
         AnnotationNode var5 = Annotations.getVisible(var2, var4);

         try {
            if (var5 != null) {
               this.checkConstraints(var1, var2, var5);
            }
         } catch (InvalidMixinException var6) {
            throw b(var6);
         }
      }

   }

   protected final void checkConstraints(MixinTargetContext var1, MethodNode var2, AnnotationNode var3) {
      try {
         ConstraintParser$Constraint var4 = ConstraintParser.parse(var3);

         try {
            var4.check(var1.getEnvironment());
         } catch (ConstraintViolationException var8) {
            ConstraintViolationException var5 = var8;
            String var6 = String.format("Constraint violation: %s on %s in %s", var8.getMessage(), var2, var1);

            try {
               this.logger.warn(var6);
               if (!var1.getEnvironment().getOption(MixinEnvironment$Option.IGNORE_CONSTRAINTS)) {
                  throw new InvalidMixinException(var1, var6, var5);
               }
            } catch (ConstraintViolationException var7) {
               throw b(var7);
            }
         }

      } catch (InvalidConstraintException var9) {
         throw new InvalidMixinException(var1, var9.getMessage());
      }
   }

   protected final MethodNode findTargetMethod(MethodNode param1) {
      // $FF: Couldn't be decompiled
   }

   protected final FieldNode findTargetField(FieldNode var1) {
      Iterator var2 = this.targetClass.fields.iterator();

      while(var2.hasNext()) {
         FieldNode var3 = (FieldNode)var2.next();

         try {
            if (var3.name.equals(var1.name)) {
               return var3;
            }
         } catch (InvalidMixinException var4) {
            throw b(var4);
         }
      }

      return null;
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

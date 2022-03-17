package org.spongepowered.asm.mixin.transformer;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.MixinEnvironment$CompatibilityLevel;
import org.spongepowered.asm.mixin.MixinEnvironment$Option;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.gen.AccessorInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo$Map;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InjectionValidationException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
import org.spongepowered.asm.mixin.struct.MemberRef;
import org.spongepowered.asm.mixin.struct.MemberRef$Field;
import org.spongepowered.asm.mixin.struct.MemberRef$Handle;
import org.spongepowered.asm.mixin.struct.MemberRef$Method;
import org.spongepowered.asm.mixin.struct.SourceMap$File;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.obfuscation.RemapperChain;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.ClassSignature;

public class MixinTargetContext extends ClassContext implements IMixinContext {
   private static final Logger logger = LogManager.getLogger("mixin");
   private final MixinInfo mixin;
   private final ClassNode classNode;
   private final TargetClassContext targetClass;
   private final String sessionId;
   private final ClassInfo targetClassInfo;
   private final BiMap<String, String> innerClasses;
   private final List<MethodNode> shadowMethods;
   private final Map<FieldNode, ClassInfo$Field> shadowFields;
   private final List<MethodNode> mergedMethods;
   private final InjectorGroupInfo$Map injectorGroups;
   private final List<InjectionInfo> injectors;
   private final List<AccessorInfo> accessors;
   private final boolean inheritsFromMixin;
   private final boolean detachedSuper;
   private final SourceMap$File stratum;
   private int minRequiredClassVersion;

   MixinTargetContext(MixinInfo var1, ClassNode var2, TargetClassContext var3) {
      MixinTargetContext var10000;
      boolean var10001;
      label45: {
         label51: {
            super();
            this.innerClasses = HashBiMap.create();
            this.shadowMethods = new ArrayList();
            this.shadowFields = new LinkedHashMap();
            this.mergedMethods = new ArrayList();
            this.injectorGroups = new InjectorGroupInfo$Map();
            this.injectors = new ArrayList();
            this.accessors = new ArrayList();
            this.minRequiredClassVersion = MixinEnvironment$CompatibilityLevel.JAVA_6.classVersion();
            this.mixin = var1;
            this.classNode = var2;
            this.targetClass = var3;
            this.targetClassInfo = ClassInfo.forName(this.getTarget().getClassRef());
            this.stratum = var3.getSourceMap().addFile(this.classNode);
            var10000 = this;
            InvalidMixinException var10;
            if (!var1.getClassInfo().hasMixinInHierarchy()) {
               try {
                  if (!this.targetClassInfo.hasMixinTargetInHierarchy()) {
                     break label51;
                  }
               } catch (InvalidMixinException var9) {
                  var10 = var9;
                  var10001 = false;
                  throw b(var10);
               }
            }

            try {
               var10001 = true;
               break label45;
            } catch (InvalidMixinException var8) {
               var10 = var8;
               var10001 = false;
               throw b(var10);
            }
         }

         var10001 = false;
      }

      label33: {
         try {
            var10000.inheritsFromMixin = var10001;
            var10000 = this;
            if (!this.classNode.superName.equals(this.getTarget().getClassNode().superName)) {
               var10001 = true;
               break label33;
            }
         } catch (InvalidMixinException var7) {
            throw b(var7);
         }

         var10001 = false;
      }

      var10000.detachedSuper = var10001;
      this.sessionId = var3.getSessionId();
      this.requireVersion(var2.version);
      InnerClassGenerator var4 = (InnerClassGenerator)var3.getExtensions().getGenerator(InnerClassGenerator.class);
      Iterator var5 = this.mixin.getInnerClasses().iterator();

      while(var5.hasNext()) {
         String var6 = (String)var5.next();
         this.innerClasses.put(var6, var4.registerInnerClass(this.mixin, var6, this));
      }

   }

   void addShadowMethod(MethodNode var1) {
      this.shadowMethods.add(var1);
   }

   void addShadowField(FieldNode var1, ClassInfo$Field var2) {
      this.shadowFields.put(var1, var2);
   }

   void addAccessorMethod(MethodNode var1, Class<? extends Annotation> var2) {
      this.accessors.add(AccessorInfo.of(this, var1, var2));
   }

   void addMixinMethod(MethodNode var1) {
      Annotations.setVisible(var1, MixinMerged.class, "mixin", this.getClassName());
      this.getTarget().addMixinMethod(var1);
   }

   void methodMerged(MethodNode var1) {
      this.mergedMethods.add(var1);
      this.targetClassInfo.addMethod(var1);
      this.getTarget().methodMerged(var1);
      Annotations.setVisible(var1, MixinMerged.class, "mixin", this.getClassName(), "priority", this.getPriority(), "sessionId", this.sessionId);
   }

   public String toString() {
      return this.mixin.toString();
   }

   public MixinEnvironment getEnvironment() {
      return this.mixin.getParent().getEnvironment();
   }

   public boolean getOption(MixinEnvironment$Option var1) {
      return this.getEnvironment().getOption(var1);
   }

   public ClassNode getClassNode() {
      return this.classNode;
   }

   public String getClassName() {
      return this.mixin.getClassName();
   }

   public String getClassRef() {
      return this.mixin.getClassRef();
   }

   public TargetClassContext getTarget() {
      return this.targetClass;
   }

   public String getTargetClassRef() {
      return this.getTarget().getClassRef();
   }

   public ClassNode getTargetClassNode() {
      return this.getTarget().getClassNode();
   }

   public ClassInfo getTargetClassInfo() {
      return this.targetClassInfo;
   }

   protected ClassInfo getClassInfo() {
      return this.mixin.getClassInfo();
   }

   public ClassSignature getSignature() {
      return this.getClassInfo().getSignature();
   }

   public SourceMap$File getStratum() {
      return this.stratum;
   }

   public int getMinRequiredClassVersion() {
      return this.minRequiredClassVersion;
   }

   public int getDefaultRequiredInjections() {
      return this.mixin.getParent().getDefaultRequiredInjections();
   }

   public String getDefaultInjectorGroup() {
      return this.mixin.getParent().getDefaultInjectorGroup();
   }

   public int getMaxShiftByValue() {
      return this.mixin.getParent().getMaxShiftByValue();
   }

   public InjectorGroupInfo$Map getInjectorGroups() {
      return this.injectorGroups;
   }

   public boolean requireOverwriteAnnotations() {
      return this.mixin.getParent().requireOverwriteAnnotations();
   }

   public ClassInfo findRealType(ClassInfo var1) {
      try {
         if (var1 == this.getClassInfo()) {
            return this.targetClassInfo;
         }
      } catch (InvalidMixinException var4) {
         throw b(var4);
      }

      ClassInfo var2 = this.targetClassInfo.findCorrespondingType(var1);

      try {
         if (var2 == null) {
            throw new InvalidMixinException(this, "Resolution error: unable to find corresponding type for " + var1 + " in hierarchy of " + this.targetClassInfo);
         } else {
            return var2;
         }
      } catch (InvalidMixinException var3) {
         throw b(var3);
      }
   }

   public void transformMethod(MethodNode var1) {
      this.validateMethod(var1);
      this.transformDescriptor(var1);
      this.transformLVT(var1);
      this.stratum.applyOffset(var1);
      AbstractInsnNode var2 = null;

      AbstractInsnNode var4;
      for(ListIterator var3 = var1.instructions.iterator(); var3.hasNext(); var2 = var4) {
         var4 = (AbstractInsnNode)var3.next();

         try {
            if (var4 instanceof MethodInsnNode) {
               this.transformMethodRef(var1, var3, new MemberRef$Method((MethodInsnNode)var4));
               continue;
            }
         } catch (InvalidMixinException var9) {
            throw b(var9);
         }

         try {
            if (var4 instanceof FieldInsnNode) {
               this.transformFieldRef(var1, var3, new MemberRef$Field((FieldInsnNode)var4));
               this.checkFinal(var1, var3, (FieldInsnNode)var4);
               continue;
            }
         } catch (InvalidMixinException var8) {
            throw b(var8);
         }

         try {
            if (var4 instanceof TypeInsnNode) {
               this.transformTypeNode(var1, var3, (TypeInsnNode)var4, var2);
               continue;
            }
         } catch (InvalidMixinException var7) {
            throw b(var7);
         }

         try {
            if (var4 instanceof LdcInsnNode) {
               this.transformConstantNode(var1, var3, (LdcInsnNode)var4);
               continue;
            }
         } catch (InvalidMixinException var6) {
            throw b(var6);
         }

         try {
            if (var4 instanceof InvokeDynamicInsnNode) {
               this.transformInvokeDynamicNode(var1, var3, (InvokeDynamicInsnNode)var4);
            }
         } catch (InvalidMixinException var5) {
            throw b(var5);
         }
      }

   }

   private void validateMethod(MethodNode param1) {
      // $FF: Couldn't be decompiled
   }

   private void transformLVT(MethodNode var1) {
      try {
         if (var1.localVariables == null) {
            return;
         }
      } catch (InvalidMixinException var5) {
         throw b(var5);
      }

      Iterator var2 = var1.localVariables.iterator();

      while(true) {
         LocalVariableNode var3;
         while(true) {
            do {
               if (!var2.hasNext()) {
                  return;
               }

               var3 = (LocalVariableNode)var2.next();
            } while(var3 == null);

            try {
               if (var3.desc == null) {
                  continue;
               }
               break;
            } catch (InvalidMixinException var4) {
               throw b(var4);
            }
         }

         var3.desc = this.transformSingleDescriptor(Type.getType(var3.desc));
      }
   }

   private void transformMethodRef(MethodNode param1, Iterator<AbstractInsnNode> param2, MemberRef param3) {
      // $FF: Couldn't be decompiled
   }

   private void transformFieldRef(MethodNode param1, Iterator<AbstractInsnNode> param2, MemberRef param3) {
      // $FF: Couldn't be decompiled
   }

   private void checkFinal(MethodNode param1, Iterator<AbstractInsnNode> param2, FieldInsnNode param3) {
      // $FF: Couldn't be decompiled
   }

   private void transformTypeNode(MethodNode param1, Iterator<AbstractInsnNode> param2, TypeInsnNode param3, AbstractInsnNode param4) {
      // $FF: Couldn't be decompiled
   }

   private void transformConstantNode(MethodNode var1, Iterator<AbstractInsnNode> var2, LdcInsnNode var3) {
      var3.cst = this.transformConstant(var1, var2, var3.cst);
   }

   private void transformInvokeDynamicNode(MethodNode var1, Iterator<AbstractInsnNode> var2, InvokeDynamicInsnNode var3) {
      this.requireVersion(51);
      var3.desc = this.transformMethodDescriptor(var3.desc);
      var3.bsm = this.transformHandle(var1, var2, var3.bsm);
      int var4 = 0;

      try {
         while(var4 < var3.bsmArgs.length) {
            var3.bsmArgs[var4] = this.transformConstant(var1, var2, var3.bsmArgs[var4]);
            ++var4;
         }

      } catch (InvalidMixinException var5) {
         throw b(var5);
      }
   }

   private Object transformConstant(MethodNode var1, Iterator<AbstractInsnNode> var2, Object var3) {
      if (var3 instanceof Type) {
         Type var4 = (Type)var3;
         String var5 = this.transformDescriptor(var4);

         try {
            return !var4.toString().equals(var5) ? Type.getType(var5) : var3;
         } catch (InvalidMixinException var6) {
            throw b(var6);
         }
      } else {
         try {
            return var3 instanceof Handle ? this.transformHandle(var1, var2, (Handle)var3) : var3;
         } catch (InvalidMixinException var7) {
            throw b(var7);
         }
      }
   }

   private Handle transformHandle(MethodNode var1, Iterator<AbstractInsnNode> var2, Handle var3) {
      MemberRef$Handle var4 = new MemberRef$Handle(var3);

      try {
         if (var4.isField()) {
            this.transformFieldRef(var1, var2, var4);
            return var4.getMethodHandle();
         }
      } catch (InvalidMixinException var5) {
         throw b(var5);
      }

      this.transformMethodRef(var1, var2, var4);
      return var4.getMethodHandle();
   }

   private void processImaginarySuper(MethodNode param1, FieldInsnNode param2) {
      // $FF: Couldn't be decompiled
   }

   private void updateStaticBinding(MethodNode var1, MemberRef var2) {
      this.updateBinding(var1, var2, ClassInfo$Traversal.SUPER);
   }

   private void updateDynamicBinding(MethodNode var1, MemberRef var2) {
      this.updateBinding(var1, var2, ClassInfo$Traversal.ALL);
   }

   private void updateBinding(MethodNode param1, MemberRef param2, ClassInfo$Traversal param3) {
      // $FF: Couldn't be decompiled
   }

   public void transformDescriptor(FieldNode param1) {
      // $FF: Couldn't be decompiled
   }

   public void transformDescriptor(MethodNode param1) {
      // $FF: Couldn't be decompiled
   }

   public void transformDescriptor(MemberRef param1) {
      // $FF: Couldn't be decompiled
   }

   public void transformDescriptor(TypeInsnNode param1) {
      // $FF: Couldn't be decompiled
   }

   private String transformDescriptor(Type var1) {
      try {
         if (var1.getSort() == 11) {
            return this.transformMethodDescriptor(var1.getDescriptor());
         }
      } catch (InvalidMixinException var2) {
         throw b(var2);
      }

      return this.transformSingleDescriptor(var1);
   }

   private String transformSingleDescriptor(Type var1) {
      try {
         if (var1.getSort() < 9) {
            return var1.toString();
         }
      } catch (InvalidMixinException var2) {
         throw b(var2);
      }

      return this.transformSingleDescriptor(var1.toString(), false);
   }

   private String transformSingleDescriptor(String param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   private String transformMethodDescriptor(String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append('(');
      Type[] var3 = Type.getArgumentTypes(var1);
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Type var6 = var3[var5];
         var2.append(this.transformSingleDescriptor(var6));
      }

      return var2.append(')').append(this.transformSingleDescriptor(Type.getReturnType(var1))).toString();
   }

   public Target getTargetMethod(MethodNode var1) {
      return this.getTarget().getTargetMethod(var1);
   }

   MethodNode findMethod(MethodNode var1, AnnotationNode var2) {
      LinkedList var3 = new LinkedList();
      var3.add(var1.name);
      if (var2 != null) {
         List var4 = (List)Annotations.getValue(var2, "aliases");

         try {
            if (var4 != null) {
               var3.addAll(var4);
            }
         } catch (InvalidMixinException var5) {
            throw b(var5);
         }
      }

      return this.getTarget().findMethod(var3, var1.desc);
   }

   MethodNode findRemappedMethod(MethodNode var1) {
      RemapperChain var2 = this.getEnvironment().getRemappers();
      String var3 = var2.mapMethodName(this.getTarget().getClassRef(), var1.name, var1.desc);

      try {
         if (var3.equals(var1.name)) {
            return null;
         }
      } catch (InvalidMixinException var5) {
         throw b(var5);
      }

      LinkedList var4 = new LinkedList();
      var4.add(var3);
      return this.getTarget().findAliasedMethod(var4, var1.desc);
   }

   FieldNode findField(FieldNode var1, AnnotationNode var2) {
      LinkedList var3 = new LinkedList();
      var3.add(var1.name);
      if (var2 != null) {
         List var4 = (List)Annotations.getValue(var2, "aliases");

         try {
            if (var4 != null) {
               var3.addAll(var4);
            }
         } catch (InvalidMixinException var5) {
            throw b(var5);
         }
      }

      return this.getTarget().findAliasedField(var3, var1.desc);
   }

   FieldNode findRemappedField(FieldNode var1) {
      RemapperChain var2 = this.getEnvironment().getRemappers();
      String var3 = var2.mapFieldName(this.getTarget().getClassRef(), var1.name, var1.desc);

      try {
         if (var3.equals(var1.name)) {
            return null;
         }
      } catch (InvalidMixinException var5) {
         throw b(var5);
      }

      LinkedList var4 = new LinkedList();
      var4.add(var3);
      return this.getTarget().findAliasedField(var4, var1.desc);
   }

   protected void requireVersion(int var1) {
      try {
         this.minRequiredClassVersion = Math.max(this.minRequiredClassVersion, var1);
         if (var1 > MixinEnvironment.getCompatibilityLevel().classVersion()) {
            throw new InvalidMixinException(this, "Unsupported mixin class version " + var1);
         }
      } catch (InvalidMixinException var2) {
         throw b(var2);
      }
   }

   public Extensions getExtensions() {
      return this.targetClass.getExtensions();
   }

   public IMixinInfo getMixin() {
      return this.mixin;
   }

   MixinInfo getInfo() {
      return this.mixin;
   }

   public int getPriority() {
      return this.mixin.getPriority();
   }

   public Set<String> getInterfaces() {
      return this.mixin.getInterfaces();
   }

   public Collection<MethodNode> getShadowMethods() {
      return this.shadowMethods;
   }

   public List<MethodNode> getMethods() {
      return this.classNode.methods;
   }

   public Set<Entry<FieldNode, ClassInfo$Field>> getShadowFields() {
      return this.shadowFields.entrySet();
   }

   public List<FieldNode> getFields() {
      return this.classNode.fields;
   }

   public Level getLoggingLevel() {
      return this.mixin.getLoggingLevel();
   }

   public boolean shouldSetSourceFile() {
      return this.mixin.getParent().shouldSetSourceFile();
   }

   public String getSourceFile() {
      return this.classNode.sourceFile;
   }

   public IReferenceMapper getReferenceMapper() {
      return this.mixin.getParent().getReferenceMapper();
   }

   public void preApply(String var1, ClassNode var2) {
      this.mixin.preApply(var1, var2);
   }

   public void postApply(String var1, ClassNode var2) {
      try {
         this.injectorGroups.validateAll();
      } catch (InjectionValidationException var5) {
         InjectorGroupInfo var4 = var5.getGroup();
         throw new InjectionError(String.format("Critical injection failure: Callback group %s in %s failed injection check: %s", var4, this.mixin, var5.getMessage()));
      }

      this.mixin.postApply(var1, var2);
   }

   public String getUniqueName(MethodNode var1, boolean var2) {
      return this.getTarget().getUniqueName(var1, var2);
   }

   public String getUniqueName(FieldNode var1) {
      return this.getTarget().getUniqueName(var1);
   }

   public void prepareInjections() {
      this.injectors.clear();
      Iterator var1 = this.mergedMethods.iterator();

      while(var1.hasNext()) {
         MethodNode var2 = (MethodNode)var1.next();
         InjectionInfo var3 = InjectionInfo.parse(this, var2);

         try {
            if (var3 == null) {
               continue;
            }
         } catch (InvalidMixinException var4) {
            throw b(var4);
         }

         try {
            if (var3.isValid()) {
               var3.prepare();
               this.injectors.add(var3);
            }
         } catch (InvalidMixinException var5) {
            throw b(var5);
         }

         var2.visibleAnnotations.remove(var3.getAnnotation());
      }

   }

   public void applyInjections() {
      Iterator var1 = this.injectors.iterator();

      InjectionInfo var2;
      while(var1.hasNext()) {
         var2 = (InjectionInfo)var1.next();
         var2.inject();
      }

      var1 = this.injectors.iterator();

      while(var1.hasNext()) {
         var2 = (InjectionInfo)var1.next();
         var2.postInject();
      }

      this.injectors.clear();
   }

   public List<MethodNode> generateAccessors() {
      Iterator var1 = this.accessors.iterator();

      while(var1.hasNext()) {
         AccessorInfo var2 = (AccessorInfo)var1.next();
         var2.locate();
      }

      ArrayList var5 = new ArrayList();
      Iterator var6 = this.accessors.iterator();

      while(var6.hasNext()) {
         AccessorInfo var3 = (AccessorInfo)var6.next();
         MethodNode var4 = var3.generate();
         this.getTarget().addMixinMethod(var4);
         var5.add(var4);
      }

      return var5;
   }

   private static InvalidMixinException b(InvalidMixinException var0) {
      return var0;
   }
}

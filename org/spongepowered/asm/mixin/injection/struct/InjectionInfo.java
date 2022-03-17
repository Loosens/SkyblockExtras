package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Strings;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.code.ISliceContext;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.code.InjectorTarget;
import org.spongepowered.asm.mixin.injection.code.MethodSlice;
import org.spongepowered.asm.mixin.injection.code.MethodSlices;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

public abstract class InjectionInfo extends SpecialMethodInfo implements ISliceContext {
   protected final boolean isStatic;
   protected final Deque<MethodNode> targets;
   protected final MethodSlices slices;
   protected final String atKey;
   protected final List<InjectionPoint> injectionPoints;
   protected final Map<Target, List<InjectionNodes$InjectionNode>> targetNodes;
   protected Injector injector;
   protected InjectorGroupInfo group;
   private final List<MethodNode> injectedMethods;
   private int expectedCallbackCount;
   private int requiredCallbackCount;
   private int maxCallbackCount;
   private int injectedCallbackCount;

   protected InjectionInfo(MixinTargetContext var1, MethodNode var2, AnnotationNode var3) {
      this(var1, var2, var3, "at");
   }

   protected InjectionInfo(MixinTargetContext var1, MethodNode var2, AnnotationNode var3, String var4) {
      super(var1, var2, var3);
      this.targets = new ArrayDeque();
      this.injectionPoints = new ArrayList();
      this.targetNodes = new LinkedHashMap();
      this.injectedMethods = new ArrayList(0);
      this.expectedCallbackCount = 1;
      this.requiredCallbackCount = 0;
      this.maxCallbackCount = Integer.MAX_VALUE;
      this.injectedCallbackCount = 0;
      this.isStatic = Bytecode.methodIsStatic(var2);
      this.slices = MethodSlices.parse(this);
      this.atKey = var4;
      this.readAnnotation();
   }

   protected void readAnnotation() {
      try {
         if (this.annotation == null) {
            return;
         }
      } catch (InvalidMemberDescriptorException var3) {
         throw b(var3);
      }

      String var1 = "@" + Bytecode.getSimpleName(this.annotation);
      List var2 = this.readInjectionPoints(var1);
      this.findMethods(this.parseTargets(var1), var1);
      this.parseInjectionPoints(var2);
      this.parseRequirements();
      this.injector = this.parseInjector(this.annotation);
   }

   protected Set<MemberInfo> parseTargets(String param1) {
      // $FF: Couldn't be decompiled
   }

   protected List<AnnotationNode> readInjectionPoints(String var1) {
      List var2 = Annotations.getValue(this.annotation, this.atKey, false);

      try {
         if (var2 == null) {
            throw new InvalidInjectionException(this, String.format("%s annotation on %s is missing '%s' value(s)", var1, this.method.name, this.atKey));
         } else {
            return var2;
         }
      } catch (InvalidMemberDescriptorException var3) {
         throw b(var3);
      }
   }

   protected void parseInjectionPoints(List<AnnotationNode> var1) {
      this.injectionPoints.addAll(InjectionPoint.parse(this.mixin, this.method, this.annotation, (List)var1));
   }

   protected void parseRequirements() {
      // $FF: Couldn't be decompiled
   }

   protected abstract Injector parseInjector(AnnotationNode var1);

   public boolean isValid() {
      // $FF: Couldn't be decompiled
   }

   public void prepare() {
      this.targetNodes.clear();
      Iterator var1 = this.targets.iterator();

      while(var1.hasNext()) {
         MethodNode var2 = (MethodNode)var1.next();
         Target var3 = this.mixin.getTargetMethod(var2);
         InjectorTarget var4 = new InjectorTarget(this, var3);
         this.targetNodes.put(var3, this.injector.find(var4, this.injectionPoints));
         var4.dispose();
      }

   }

   public void inject() {
      Iterator var1 = this.targetNodes.entrySet().iterator();

      while(var1.hasNext()) {
         Entry var2 = (Entry)var1.next();
         this.injector.inject((Target)var2.getKey(), (List)var2.getValue());
      }

      this.targets.clear();
   }

   public void postInject() {
      // $FF: Couldn't be decompiled
   }

   public void notifyInjected(Target var1) {
   }

   protected String getDescription() {
      return "Callback method";
   }

   public String toString() {
      return describeInjector(this.mixin, this.annotation, this.method);
   }

   public Collection<MethodNode> getTargets() {
      return this.targets;
   }

   public MethodSlice getSlice(String var1) {
      return this.slices.get(this.getSliceId(var1));
   }

   public String getSliceId(String var1) {
      return "";
   }

   public int getInjectedCallbackCount() {
      return this.injectedCallbackCount;
   }

   public MethodNode addMethod(int var1, String var2, String var3) {
      MethodNode var4 = new MethodNode(327680, var1 | 4096, var2, var3, (String)null, (String[])null);
      this.injectedMethods.add(var4);
      return var4;
   }

   public void addCallbackInvocation(MethodNode var1) {
      ++this.injectedCallbackCount;
   }

   private void findMethods(Set<MemberInfo> param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   private void checkTarget(MethodNode var1) {
      AnnotationNode var2 = Annotations.getVisible(var1, MixinMerged.class);

      try {
         if (var2 == null) {
            return;
         }
      } catch (InvalidMemberDescriptorException var4) {
         throw b(var4);
      }

      try {
         if (Annotations.getVisible(var1, Final.class) != null) {
            throw new InvalidInjectionException(this, String.format("%s cannot inject into @Final method %s::%s%s merged by %s", this, this.classNode.name, var1.name, var1.desc, Annotations.getValue(var2, "mixin")));
         }
      } catch (InvalidMemberDescriptorException var3) {
         throw b(var3);
      }
   }

   protected String getDynamicInfo() {
      AnnotationNode var1 = Annotations.getInvisible(this.method, Dynamic.class);
      String var2 = Strings.nullToEmpty((String)Annotations.getValue(var1));
      Type var3 = (Type)Annotations.getValue(var1, "mixin");
      if (var3 != null) {
         var2 = String.format("{%s} %s", var3.getClassName(), var2).trim();
      }

      String var10000;
      try {
         if (var2.length() > 0) {
            var10000 = String.format(" Method is @Dynamic(%s)", var2);
            return var10000;
         }
      } catch (InvalidMemberDescriptorException var4) {
         throw b(var4);
      }

      var10000 = "";
      return var10000;
   }

   public static InjectionInfo parse(MixinTargetContext var0, MethodNode var1) {
      AnnotationNode var2 = getInjectorAnnotation(var0.getMixin(), var1);

      try {
         if (var2 == null) {
            return null;
         }
      } catch (InvalidMemberDescriptorException var6) {
         throw b(var6);
      }

      try {
         if (var2.desc.endsWith(Inject.class.getSimpleName() + ";")) {
            return new CallbackInjectionInfo(var0, var1, var2);
         }
      } catch (InvalidMemberDescriptorException var9) {
         throw b(var9);
      }

      try {
         if (var2.desc.endsWith(ModifyArg.class.getSimpleName() + ";")) {
            return new ModifyArgInjectionInfo(var0, var1, var2);
         }
      } catch (InvalidMemberDescriptorException var5) {
         throw b(var5);
      }

      try {
         if (var2.desc.endsWith(ModifyArgs.class.getSimpleName() + ";")) {
            return new ModifyArgsInjectionInfo(var0, var1, var2);
         }
      } catch (InvalidMemberDescriptorException var8) {
         throw b(var8);
      }

      try {
         if (var2.desc.endsWith(Redirect.class.getSimpleName() + ";")) {
            return new RedirectInjectionInfo(var0, var1, var2);
         }
      } catch (InvalidMemberDescriptorException var4) {
         throw b(var4);
      }

      try {
         if (var2.desc.endsWith(ModifyVariable.class.getSimpleName() + ";")) {
            return new ModifyVariableInjectionInfo(var0, var1, var2);
         }
      } catch (InvalidMemberDescriptorException var7) {
         throw b(var7);
      }

      try {
         return var2.desc.endsWith(ModifyConstant.class.getSimpleName() + ";") ? new ModifyConstantInjectionInfo(var0, var1, var2) : null;
      } catch (InvalidMemberDescriptorException var3) {
         throw b(var3);
      }
   }

   public static AnnotationNode getInjectorAnnotation(IMixinInfo var0, MethodNode var1) {
      AnnotationNode var2 = null;

      try {
         var2 = Annotations.getSingleVisible(var1, Inject.class, ModifyArg.class, ModifyArgs.class, Redirect.class, ModifyVariable.class, ModifyConstant.class);
         return var2;
      } catch (IllegalArgumentException var4) {
         throw new InvalidMixinException(var0, String.format("Error parsing annotations on %s in %s: %s", var1.name, var0.getClassName(), var4.getMessage()));
      }
   }

   public static String getInjectorPrefix(AnnotationNode param0) {
      // $FF: Couldn't be decompiled
   }

   static String describeInjector(IMixinContext var0, AnnotationNode var1, MethodNode var2) {
      return String.format("%s->@%s::%s%s", var0.toString(), Bytecode.getSimpleName(var1), var2.name, var2.desc);
   }

   private static String namesOf(Collection<MemberInfo> param0) {
      // $FF: Couldn't be decompiled
   }

   private static InvalidMemberDescriptorException b(InvalidMemberDescriptorException var0) {
      return var0;
   }
}

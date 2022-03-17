package org.spongepowered.asm.mixin.gen;

import com.google.common.base.Strings;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.regex.Pattern;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment$Option;
import org.spongepowered.asm.mixin.gen.throwables.InvalidAccessorException;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

public class AccessorInfo extends SpecialMethodInfo {
   protected static final Pattern PATTERN_ACCESSOR = Pattern.compile("^(get|set|is|invoke|call)(([A-Z])(.*?))(_\\$md.*)?$");
   protected final Type[] argTypes;
   protected final Type returnType;
   protected final AccessorInfo$AccessorType type;
   private final Type targetFieldType;
   protected final MemberInfo target;
   protected FieldNode targetField;
   protected MethodNode targetMethod;

   public AccessorInfo(MixinTargetContext var1, MethodNode var2) {
      this(var1, var2, Accessor.class);
   }

   protected AccessorInfo(MixinTargetContext var1, MethodNode var2, Class<? extends Annotation> var3) {
      super(var1, var2, Annotations.getVisible(var2, var3));
      this.argTypes = Type.getArgumentTypes(var2.desc);
      this.returnType = Type.getReturnType(var2.desc);
      this.type = this.initType();
      this.targetFieldType = this.initTargetFieldType();
      this.target = this.initTarget();
   }

   protected AccessorInfo$AccessorType initType() {
      try {
         if (this.returnType.equals(Type.VOID_TYPE)) {
            return AccessorInfo$AccessorType.FIELD_SETTER;
         }
      } catch (InvalidAccessorException var1) {
         throw b(var1);
      }

      return AccessorInfo$AccessorType.FIELD_GETTER;
   }

   protected Type initTargetFieldType() {
      // $FF: Couldn't be decompiled
   }

   protected MemberInfo initTarget() {
      MemberInfo var1 = new MemberInfo(this.getTargetName(), (String)null, this.targetFieldType.getDescriptor());
      this.annotation.visit("target", var1.toString());
      return var1;
   }

   protected String getTargetName() {
      String var1 = (String)Annotations.getValue(this.annotation);
      if (Strings.isNullOrEmpty(var1)) {
         String var2 = this.inflectTarget();

         try {
            if (var2 == null) {
               throw new InvalidAccessorException(this.mixin, "Failed to inflect target name for " + this + ", supported prefixes: [get, set, is]");
            } else {
               return var2;
            }
         } catch (InvalidAccessorException var3) {
            throw b(var3);
         }
      } else {
         return MemberInfo.parse(var1, this.mixin).name;
      }
   }

   protected String inflectTarget() {
      return inflectTarget(this.method.name, this.type, this.toString(), this.mixin, this.mixin.getEnvironment().getOption(MixinEnvironment$Option.DEBUG_VERBOSE));
   }

   public static String inflectTarget(String param0, AccessorInfo$AccessorType param1, String param2, IMixinContext param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   public final MemberInfo getTarget() {
      return this.target;
   }

   public final Type getTargetFieldType() {
      return this.targetFieldType;
   }

   public final FieldNode getTargetField() {
      return this.targetField;
   }

   public final MethodNode getTargetMethod() {
      return this.targetMethod;
   }

   public final Type getReturnType() {
      return this.returnType;
   }

   public final Type[] getArgTypes() {
      return this.argTypes;
   }

   public String toString() {
      return String.format("%s->@%s[%s]::%s%s", this.mixin.toString(), Bytecode.getSimpleName(this.annotation), this.type.toString(), this.method.name, this.method.desc);
   }

   public void locate() {
      this.targetField = this.findTargetField();
   }

   public MethodNode generate() {
      MethodNode var1 = this.type.getGenerator(this).generate();
      Bytecode.mergeAnnotations(this.method, var1);
      return var1;
   }

   private FieldNode findTargetField() {
      return (FieldNode)this.findTarget(this.classNode.fields);
   }

   protected <TNode> TNode findTarget(List<TNode> param1) {
      // $FF: Couldn't be decompiled
   }

   private static <TNode> String getNodeDesc(TNode var0) {
      String var10000;
      try {
         if (var0 instanceof MethodNode) {
            var10000 = ((MethodNode)var0).desc;
            return var10000;
         }
      } catch (InvalidAccessorException var2) {
         throw b(var2);
      }

      try {
         if (var0 instanceof FieldNode) {
            var10000 = ((FieldNode)var0).desc;
            return var10000;
         }
      } catch (InvalidAccessorException var1) {
         throw b(var1);
      }

      var10000 = null;
      return var10000;
   }

   private static <TNode> String getNodeName(TNode var0) {
      String var10000;
      try {
         if (var0 instanceof MethodNode) {
            var10000 = ((MethodNode)var0).name;
            return var10000;
         }
      } catch (InvalidAccessorException var2) {
         throw b(var2);
      }

      try {
         if (var0 instanceof FieldNode) {
            var10000 = ((FieldNode)var0).name;
            return var10000;
         }
      } catch (InvalidAccessorException var1) {
         throw b(var1);
      }

      var10000 = null;
      return var10000;
   }

   public static AccessorInfo of(MixinTargetContext var0, MethodNode var1, Class<? extends Annotation> var2) {
      try {
         if (var2 == Accessor.class) {
            return new AccessorInfo(var0, var1);
         }
      } catch (InvalidAccessorException var3) {
         throw b(var3);
      }

      try {
         if (var2 == Invoker.class) {
            return new InvokerInfo(var0, var1);
         }
      } catch (InvalidAccessorException var4) {
         throw b(var4);
      }

      throw new InvalidAccessorException(var0, "Could not parse accessor for unknown type " + var2.getName());
   }

   private static String toLowerCase(String var0, boolean var1) {
      String var10000;
      try {
         if (var1) {
            var10000 = var0.toLowerCase();
            return var10000;
         }
      } catch (InvalidAccessorException var2) {
         throw b(var2);
      }

      var10000 = var0;
      return var10000;
   }

   private static boolean isUpperCase(String var0) {
      return var0.toUpperCase().equals(var0);
   }

   private static InvalidAccessorException b(InvalidAccessorException var0) {
      return var0;
   }
}

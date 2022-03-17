package org.spongepowered.asm.mixin.transformer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.ClassSignature;
import org.spongepowered.asm.util.perf.Profiler;
import org.spongepowered.asm.util.perf.Profiler$Section;

public final class ClassInfo {
   public static final int INCLUDE_PRIVATE = 2;
   public static final int INCLUDE_STATIC = 8;
   public static final int INCLUDE_ALL = 10;
   private static final Logger logger = LogManager.getLogger("mixin");
   private static final Profiler profiler = MixinEnvironment.getProfiler();
   private static final String JAVA_LANG_OBJECT = "java/lang/Object";
   private static final Map<String, ClassInfo> cache = new HashMap();
   private static final ClassInfo OBJECT = new ClassInfo();
   private final String name;
   private final String superName;
   private final String outerName;
   private final boolean isProbablyStatic;
   private final Set<String> interfaces;
   private final Set<ClassInfo$Method> methods;
   private final Set<ClassInfo$Field> fields;
   private final Set<MixinInfo> mixins = new HashSet();
   private final Map<ClassInfo, ClassInfo> correspondingTypes = new HashMap();
   private final MixinInfo mixin;
   private final MethodMapper methodMapper;
   private final boolean isMixin;
   private final boolean isInterface;
   private final int access;
   private ClassInfo superClass;
   private ClassInfo outerClass;
   private ClassSignature signature;

   private ClassInfo() {
      this.name = "java/lang/Object";
      this.superName = null;
      this.outerName = null;
      this.isProbablyStatic = true;
      this.methods = ImmutableSet.of(new ClassInfo$Method(this, "getClass", "()Ljava/lang/Class;"), new ClassInfo$Method(this, "hashCode", "()I"), new ClassInfo$Method(this, "equals", "(Ljava/lang/Object;)Z"), new ClassInfo$Method(this, "clone", "()Ljava/lang/Object;"), new ClassInfo$Method(this, "toString", "()Ljava/lang/String;"), new ClassInfo$Method(this, "notify", "()V"), new ClassInfo$Method[]{new ClassInfo$Method(this, "notifyAll", "()V"), new ClassInfo$Method(this, "wait", "(J)V"), new ClassInfo$Method(this, "wait", "(JI)V"), new ClassInfo$Method(this, "wait", "()V"), new ClassInfo$Method(this, "finalize", "()V")});
      this.fields = Collections.emptySet();
      this.isInterface = false;
      this.interfaces = Collections.emptySet();
      this.access = 1;
      this.isMixin = false;
      this.mixin = null;
      this.methodMapper = null;
   }

   private ClassInfo(ClassNode var1) {
      Profiler$Section var2 = profiler.begin(1, (String)"class.meta");

      try {
         ClassInfo var10000;
         String var10001;
         label145: {
            try {
               this.name = var1.name;
               var10000 = this;
               if (var1.superName != null) {
                  var10001 = var1.superName;
                  break label145;
               }
            } catch (IllegalArgumentException var14) {
               throw b(var14);
            }

            var10001 = "java/lang/Object";
         }

         boolean var22;
         label152: {
            try {
               var10000.superName = var10001;
               this.methods = new HashSet();
               this.fields = new HashSet();
               var10000 = this;
               if ((var1.access & 512) != 0) {
                  var22 = true;
                  break label152;
               }
            } catch (IllegalArgumentException var15) {
               throw b(var15);
            }

            var22 = false;
         }

         MixinInfo var23;
         label160: {
            try {
               var10000.isInterface = var22;
               this.interfaces = new HashSet();
               this.access = var1.access;
               this.isMixin = var1 instanceof MixinInfo$MixinClassNode;
               var10000 = this;
               if (this.isMixin) {
                  var23 = ((MixinInfo$MixinClassNode)var1).getMixin();
                  break label160;
               }
            } catch (IllegalArgumentException var16) {
               throw b(var16);
            }

            var23 = null;
         }

         var10000.mixin = var23;
         this.interfaces.addAll(var1.interfaces);
         Iterator var3 = var1.methods.iterator();

         while(var3.hasNext()) {
            MethodNode var4 = (MethodNode)var3.next();
            this.addMethod(var4, this.isMixin);
         }

         boolean var20 = true;
         String var21 = var1.outerClass;

         FieldNode var6;
         for(Iterator var5 = var1.fields.iterator(); var5.hasNext(); this.fields.add(new ClassInfo$Field(this, var6, this.isMixin))) {
            var6 = (FieldNode)var5.next();

            try {
               if ((var6.access & 4096) == 0 || !var6.name.startsWith("this$")) {
                  continue;
               }
            } catch (IllegalArgumentException var18) {
               throw b(var18);
            }

            var20 = false;
            if (var21 == null) {
               var21 = var6.desc;

               try {
                  if (var21 == null || !var21.startsWith("L")) {
                     continue;
                  }
               } catch (IllegalArgumentException var17) {
                  throw b(var17);
               }

               var21 = var21.substring(1, var21.length() - 1);
            }
         }

         this.isProbablyStatic = var20;
         this.outerName = var21;
         this.methodMapper = new MethodMapper(MixinEnvironment.getCurrentEnvironment(), this);
         this.signature = ClassSignature.ofLazy(var1);
      } finally {
         var2.end();
      }

   }

   void addInterface(String var1) {
      this.interfaces.add(var1);
      this.getSignature().addInterface(var1);
   }

   void addMethod(MethodNode var1) {
      this.addMethod(var1, true);
   }

   private void addMethod(MethodNode var1, boolean var2) {
      try {
         if (!var1.name.startsWith("<")) {
            this.methods.add(new ClassInfo$Method(this, var1, var2));
         }

      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }
   }

   void addMixin(MixinInfo var1) {
      try {
         if (this.isMixin) {
            throw new IllegalArgumentException("Cannot add target " + this.name + " for " + var1.getClassName() + " because the target is a mixin");
         }
      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }

      this.mixins.add(var1);
   }

   public Set<MixinInfo> getMixins() {
      return Collections.unmodifiableSet(this.mixins);
   }

   public boolean isMixin() {
      return this.isMixin;
   }

   public boolean isPublic() {
      boolean var10000;
      try {
         if ((this.access & 1) != 0) {
            var10000 = true;
            return var10000;
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public boolean isAbstract() {
      boolean var10000;
      try {
         if ((this.access & 1024) != 0) {
            var10000 = true;
            return var10000;
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public boolean isSynthetic() {
      boolean var10000;
      try {
         if ((this.access & 4096) != 0) {
            var10000 = true;
            return var10000;
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public boolean isProbablyStatic() {
      return this.isProbablyStatic;
   }

   public boolean isInner() {
      boolean var10000;
      try {
         if (this.outerName != null) {
            var10000 = true;
            return var10000;
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public boolean isInterface() {
      return this.isInterface;
   }

   public Set<String> getInterfaces() {
      return Collections.unmodifiableSet(this.interfaces);
   }

   public String toString() {
      return this.name;
   }

   public MethodMapper getMethodMapper() {
      return this.methodMapper;
   }

   public int getAccess() {
      return this.access;
   }

   public String getName() {
      return this.name;
   }

   public String getClassName() {
      return this.name.replace('/', '.');
   }

   public String getSuperName() {
      return this.superName;
   }

   public ClassInfo getSuperClass() {
      // $FF: Couldn't be decompiled
   }

   public String getOuterName() {
      return this.outerName;
   }

   public ClassInfo getOuterClass() {
      // $FF: Couldn't be decompiled
   }

   public ClassSignature getSignature() {
      return this.signature.wake();
   }

   List<ClassInfo> getTargets() {
      if (this.mixin != null) {
         ArrayList var1 = new ArrayList();
         var1.add(this);
         var1.addAll(this.mixin.getTargets());
         return var1;
      } else {
         return ImmutableList.of(this);
      }
   }

   public Set<ClassInfo$Method> getMethods() {
      return Collections.unmodifiableSet(this.methods);
   }

   public Set<ClassInfo$Method> getInterfaceMethods(boolean param1) {
      // $FF: Couldn't be decompiled
   }

   private ClassInfo addMethodsRecursive(Set<ClassInfo$Method> var1, boolean var2) {
      Iterator var3;
      ClassInfo$Method var4;
      if (this.isInterface) {
         for(var3 = this.methods.iterator(); var3.hasNext(); var1.add(var4)) {
            var4 = (ClassInfo$Method)var3.next();

            try {
               if (!var4.isAbstract()) {
                  var1.remove(var4);
               }
            } catch (IllegalArgumentException var6) {
               throw b(var6);
            }
         }
      } else {
         label60: {
            try {
               if (this.isMixin || !var2) {
                  break label60;
               }
            } catch (IllegalArgumentException var5) {
               throw b(var5);
            }

            var3 = this.mixins.iterator();

            while(var3.hasNext()) {
               MixinInfo var7 = (MixinInfo)var3.next();
               var7.getClassInfo().addMethodsRecursive(var1, var2);
            }
         }
      }

      var3 = this.interfaces.iterator();

      while(var3.hasNext()) {
         String var8 = (String)var3.next();
         forName(var8).addMethodsRecursive(var1, var2);
      }

      return this.getSuperClass();
   }

   public boolean hasSuperClass(String var1) {
      return this.hasSuperClass(var1, ClassInfo$Traversal.NONE);
   }

   public boolean hasSuperClass(String var1, ClassInfo$Traversal var2) {
      try {
         if ("java/lang/Object".equals(var1)) {
            return true;
         }
      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }

      boolean var10000;
      try {
         if (this.findSuperClass(var1, var2) != null) {
            var10000 = true;
            return var10000;
         }
      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }

      var10000 = false;
      return var10000;
   }

   public boolean hasSuperClass(ClassInfo var1) {
      return this.hasSuperClass(var1, ClassInfo$Traversal.NONE, false);
   }

   public boolean hasSuperClass(ClassInfo var1, ClassInfo$Traversal var2) {
      return this.hasSuperClass(var1, var2, false);
   }

   public boolean hasSuperClass(ClassInfo var1, ClassInfo$Traversal var2, boolean var3) {
      try {
         if (OBJECT == var1) {
            return true;
         }
      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }

      boolean var10000;
      try {
         if (this.findSuperClass(var1.name, var2, var3) != null) {
            var10000 = true;
            return var10000;
         }
      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }

      var10000 = false;
      return var10000;
   }

   public ClassInfo findSuperClass(String var1) {
      return this.findSuperClass(var1, ClassInfo$Traversal.NONE);
   }

   public ClassInfo findSuperClass(String var1, ClassInfo$Traversal var2) {
      return this.findSuperClass(var1, var2, false, new HashSet());
   }

   public ClassInfo findSuperClass(String var1, ClassInfo$Traversal var2, boolean var3) {
      try {
         if (OBJECT.name.equals(var1)) {
            return null;
         }
      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }

      return this.findSuperClass(var1, var2, var3, new HashSet());
   }

   private ClassInfo findSuperClass(String var1, ClassInfo$Traversal var2, boolean var3, Set<String> var4) {
      ClassInfo var5 = this.getSuperClass();
      Iterator var6;
      if (var5 != null) {
         var6 = var5.getTargets().iterator();

         while(var6.hasNext()) {
            ClassInfo var7 = (ClassInfo)var6.next();

            try {
               if (var1.equals(var7.getName())) {
                  return var5;
               }
            } catch (IllegalArgumentException var16) {
               throw b(var16);
            }

            ClassInfo var8 = var7.findSuperClass(var1, var2.next(), var3, var4);

            try {
               if (var8 != null) {
                  return var8;
               }
            } catch (IllegalArgumentException var13) {
               throw b(var13);
            }
         }
      }

      if (var3) {
         ClassInfo var17 = this.findInterface(var1);

         try {
            if (var17 != null) {
               return var17;
            }
         } catch (IllegalArgumentException var12) {
            throw b(var12);
         }
      }

      if (var2.canTraverse()) {
         var6 = this.mixins.iterator();

         while(true) {
            MixinInfo var18;
            String var19;
            while(true) {
               if (!var6.hasNext()) {
                  return null;
               }

               var18 = (MixinInfo)var6.next();
               var19 = var18.getClassName();

               try {
                  if (var4.contains(var19)) {
                     continue;
                  }
                  break;
               } catch (IllegalArgumentException var14) {
                  throw b(var14);
               }
            }

            var4.add(var19);
            ClassInfo var9 = var18.getClassInfo();

            try {
               if (var1.equals(var9.getName())) {
                  return var9;
               }
            } catch (IllegalArgumentException var15) {
               throw b(var15);
            }

            ClassInfo var10 = var9.findSuperClass(var1, ClassInfo$Traversal.ALL, var3, var4);

            try {
               if (var10 != null) {
                  return var10;
               }
            } catch (IllegalArgumentException var11) {
               throw b(var11);
            }
         }
      } else {
         return null;
      }
   }

   private ClassInfo findInterface(String var1) {
      Iterator var2 = this.getInterfaces().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         ClassInfo var4 = forName(var3);

         try {
            if (var1.equals(var3)) {
               return var4;
            }
         } catch (IllegalArgumentException var7) {
            throw b(var7);
         }

         ClassInfo var5 = var4.findInterface(var1);

         try {
            if (var5 != null) {
               return var5;
            }
         } catch (IllegalArgumentException var6) {
            throw b(var6);
         }
      }

      return null;
   }

   ClassInfo findCorrespondingType(ClassInfo param1) {
      // $FF: Couldn't be decompiled
   }

   private ClassInfo findSuperTypeForMixin(ClassInfo var1) {
      ClassInfo var2 = this;

      while(true) {
         try {
            if (var2 == null || var2 == OBJECT) {
               return null;
            }
         } catch (IllegalArgumentException var6) {
            throw b(var6);
         }

         Iterator var3 = var2.mixins.iterator();

         while(var3.hasNext()) {
            MixinInfo var4 = (MixinInfo)var3.next();

            try {
               if (var4.getClassInfo().equals(var1)) {
                  return var2;
               }
            } catch (IllegalArgumentException var5) {
               throw b(var5);
            }
         }

         var2 = var2.getSuperClass();
      }
   }

   public boolean hasMixinInHierarchy() {
      // $FF: Couldn't be decompiled
   }

   public boolean hasMixinTargetInHierarchy() {
      // $FF: Couldn't be decompiled
   }

   public ClassInfo$Method findMethodInHierarchy(MethodNode var1, ClassInfo$SearchType var2) {
      return this.findMethodInHierarchy(var1.name, var1.desc, var2, ClassInfo$Traversal.NONE);
   }

   public ClassInfo$Method findMethodInHierarchy(MethodNode var1, ClassInfo$SearchType var2, int var3) {
      return this.findMethodInHierarchy(var1.name, var1.desc, var2, ClassInfo$Traversal.NONE, var3);
   }

   public ClassInfo$Method findMethodInHierarchy(MethodInsnNode var1, ClassInfo$SearchType var2) {
      return this.findMethodInHierarchy(var1.name, var1.desc, var2, ClassInfo$Traversal.NONE);
   }

   public ClassInfo$Method findMethodInHierarchy(MethodInsnNode var1, ClassInfo$SearchType var2, int var3) {
      return this.findMethodInHierarchy(var1.name, var1.desc, var2, ClassInfo$Traversal.NONE, var3);
   }

   public ClassInfo$Method findMethodInHierarchy(String var1, String var2, ClassInfo$SearchType var3) {
      return this.findMethodInHierarchy(var1, var2, var3, ClassInfo$Traversal.NONE);
   }

   public ClassInfo$Method findMethodInHierarchy(String var1, String var2, ClassInfo$SearchType var3, ClassInfo$Traversal var4) {
      return this.findMethodInHierarchy(var1, var2, var3, var4, 0);
   }

   public ClassInfo$Method findMethodInHierarchy(String var1, String var2, ClassInfo$SearchType var3, ClassInfo$Traversal var4, int var5) {
      return (ClassInfo$Method)this.findInHierarchy(var1, var2, var3, var4, var5, ClassInfo$Member$Type.METHOD);
   }

   public ClassInfo$Field findFieldInHierarchy(FieldNode var1, ClassInfo$SearchType var2) {
      return this.findFieldInHierarchy(var1.name, var1.desc, var2, ClassInfo$Traversal.NONE);
   }

   public ClassInfo$Field findFieldInHierarchy(FieldNode var1, ClassInfo$SearchType var2, int var3) {
      return this.findFieldInHierarchy(var1.name, var1.desc, var2, ClassInfo$Traversal.NONE, var3);
   }

   public ClassInfo$Field findFieldInHierarchy(FieldInsnNode var1, ClassInfo$SearchType var2) {
      return this.findFieldInHierarchy(var1.name, var1.desc, var2, ClassInfo$Traversal.NONE);
   }

   public ClassInfo$Field findFieldInHierarchy(FieldInsnNode var1, ClassInfo$SearchType var2, int var3) {
      return this.findFieldInHierarchy(var1.name, var1.desc, var2, ClassInfo$Traversal.NONE, var3);
   }

   public ClassInfo$Field findFieldInHierarchy(String var1, String var2, ClassInfo$SearchType var3) {
      return this.findFieldInHierarchy(var1, var2, var3, ClassInfo$Traversal.NONE);
   }

   public ClassInfo$Field findFieldInHierarchy(String var1, String var2, ClassInfo$SearchType var3, ClassInfo$Traversal var4) {
      return this.findFieldInHierarchy(var1, var2, var3, var4, 0);
   }

   public ClassInfo$Field findFieldInHierarchy(String var1, String var2, ClassInfo$SearchType var3, ClassInfo$Traversal var4, int var5) {
      return (ClassInfo$Field)this.findInHierarchy(var1, var2, var3, var4, var5, ClassInfo$Member$Type.FIELD);
   }

   private <M extends ClassInfo$Member> M findInHierarchy(String param1, String param2, ClassInfo$SearchType param3, ClassInfo$Traversal param4, int param5, ClassInfo$Member$Type param6) {
      // $FF: Couldn't be decompiled
   }

   private <M extends ClassInfo$Member> M cloneMember(M var1) {
      try {
         if (var1 instanceof ClassInfo$Method) {
            return new ClassInfo$Method(this, var1);
         }
      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }

      return new ClassInfo$Field(this, var1);
   }

   public ClassInfo$Method findMethod(MethodNode var1) {
      return this.findMethod(var1.name, var1.desc, var1.access);
   }

   public ClassInfo$Method findMethod(MethodNode var1, int var2) {
      return this.findMethod(var1.name, var1.desc, var2);
   }

   public ClassInfo$Method findMethod(MethodInsnNode var1) {
      return this.findMethod(var1.name, var1.desc, 0);
   }

   public ClassInfo$Method findMethod(MethodInsnNode var1, int var2) {
      return this.findMethod(var1.name, var1.desc, var2);
   }

   public ClassInfo$Method findMethod(String var1, String var2, int var3) {
      return (ClassInfo$Method)this.findMember(var1, var2, var3, ClassInfo$Member$Type.METHOD);
   }

   public ClassInfo$Field findField(FieldNode var1) {
      return this.findField(var1.name, var1.desc, var1.access);
   }

   public ClassInfo$Field findField(FieldInsnNode var1, int var2) {
      return this.findField(var1.name, var1.desc, var2);
   }

   public ClassInfo$Field findField(String var1, String var2, int var3) {
      return (ClassInfo$Field)this.findMember(var1, var2, var3, ClassInfo$Member$Type.FIELD);
   }

   private <M extends ClassInfo$Member> M findMember(String param1, String param2, int param3, ClassInfo$Member$Type param4) {
      // $FF: Couldn't be decompiled
   }

   public boolean equals(Object var1) {
      try {
         if (!(var1 instanceof ClassInfo)) {
            return false;
         }
      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }

      return ((ClassInfo)var1).name.equals(this.name);
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   static ClassInfo fromClassNode(ClassNode var0) {
      ClassInfo var1 = (ClassInfo)cache.get(var0.name);
      if (var1 == null) {
         var1 = new ClassInfo(var0);
         cache.put(var0.name, var1);
      }

      return var1;
   }

   public static ClassInfo forName(String var0) {
      var0 = var0.replace('.', '/');
      ClassInfo var1 = (ClassInfo)cache.get(var0);
      if (var1 == null) {
         try {
            ClassNode var2 = MixinService.getService().getBytecodeProvider().getClassNode(var0);
            var1 = new ClassInfo(var2);
         } catch (Exception var3) {
            logger.catching(Level.TRACE, var3);
            logger.warn("Error loading class: {} ({}: {})", new Object[]{var0, var3.getClass().getName(), var3.getMessage()});
         }

         cache.put(var0, var1);
         logger.trace("Added class metadata for {} to metadata cache", new Object[]{var0});
      }

      return var1;
   }

   public static ClassInfo forType(Type var0) {
      try {
         if (var0.getSort() == 9) {
            return forType(var0.getElementType());
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      try {
         if (var0.getSort() < 9) {
            return null;
         }
      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }

      return forName(var0.getClassName().replace('.', '/'));
   }

   public static ClassInfo getCommonSuperClass(String param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   public static ClassInfo getCommonSuperClass(Type param0, Type param1) {
      // $FF: Couldn't be decompiled
   }

   private static ClassInfo getCommonSuperClass(ClassInfo var0, ClassInfo var1) {
      return getCommonSuperClass(var0, var1, false);
   }

   public static ClassInfo getCommonSuperClassOrInterface(String param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   public static ClassInfo getCommonSuperClassOrInterface(Type param0, Type param1) {
      // $FF: Couldn't be decompiled
   }

   public static ClassInfo getCommonSuperClassOrInterface(ClassInfo var0, ClassInfo var1) {
      return getCommonSuperClass(var0, var1, true);
   }

   private static ClassInfo getCommonSuperClass(ClassInfo param0, ClassInfo param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   static {
      cache.put("java/lang/Object", OBJECT);
   }

   private static IllegalArgumentException b(IllegalArgumentException var0) {
      return var0;
   }
}

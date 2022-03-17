package org.spongepowered.asm.mixin.transformer;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.struct.SourceMap;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.util.ClassSignature;

class TargetClassContext extends ClassContext implements ITargetClassContext {
   private static final Logger logger = LogManager.getLogger("mixin");
   private final MixinEnvironment env;
   private final Extensions extensions;
   private final String sessionId;
   private final String className;
   private final ClassNode classNode;
   private final ClassInfo classInfo;
   private final SourceMap sourceMap;
   private final ClassSignature signature;
   private final SortedSet<MixinInfo> mixins;
   private final Map<String, Target> targetMethods = new HashMap();
   private final Set<MethodNode> mixinMethods = new HashSet();
   private int nextUniqueMethodIndex;
   private int nextUniqueFieldIndex;
   private boolean applied;
   private boolean forceExport;

   TargetClassContext(MixinEnvironment var1, Extensions var2, String var3, String var4, ClassNode var5, SortedSet<MixinInfo> var6) {
      this.env = var1;
      this.extensions = var2;
      this.sessionId = var3;
      this.className = var4;
      this.classNode = var5;
      this.classInfo = ClassInfo.fromClassNode(var5);
      this.signature = this.classInfo.getSignature();
      this.mixins = var6;
      this.sourceMap = new SourceMap(var5.sourceFile);
      this.sourceMap.addFile(this.classNode);
   }

   public String toString() {
      return this.className;
   }

   boolean isApplied() {
      return this.applied;
   }

   boolean isExportForced() {
      return this.forceExport;
   }

   Extensions getExtensions() {
      return this.extensions;
   }

   String getSessionId() {
      return this.sessionId;
   }

   String getClassRef() {
      return this.classNode.name;
   }

   String getClassName() {
      return this.className;
   }

   public ClassNode getClassNode() {
      return this.classNode;
   }

   List<MethodNode> getMethods() {
      return this.classNode.methods;
   }

   List<FieldNode> getFields() {
      return this.classNode.fields;
   }

   public ClassInfo getClassInfo() {
      return this.classInfo;
   }

   SortedSet<MixinInfo> getMixins() {
      return this.mixins;
   }

   SourceMap getSourceMap() {
      return this.sourceMap;
   }

   void mergeSignature(ClassSignature var1) {
      this.signature.merge(var1);
   }

   void addMixinMethod(MethodNode var1) {
      this.mixinMethods.add(var1);
   }

   void methodMerged(MethodNode var1) {
      try {
         if (!this.mixinMethods.remove(var1)) {
            logger.debug("Unexpected: Merged unregistered method {}{} in {}", new Object[]{var1.name, var1.desc, this});
         }

      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }
   }

   MethodNode findMethod(Deque<String> var1, String var2) {
      return this.findAliasedMethod(var1, var2, true);
   }

   MethodNode findAliasedMethod(Deque<String> var1, String var2) {
      return this.findAliasedMethod(var1, var2, false);
   }

   private MethodNode findAliasedMethod(Deque<String> param1, String param2, boolean param3) {
      // $FF: Couldn't be decompiled
   }

   FieldNode findAliasedField(Deque<String> param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   Target getTargetMethod(MethodNode var1) {
      try {
         if (!this.classNode.methods.contains(var1)) {
            throw new IllegalArgumentException("Invalid target method supplied to getTargetMethod()");
         }
      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }

      String var2 = var1.name + var1.desc;
      Target var3 = (Target)this.targetMethods.get(var2);
      if (var3 == null) {
         var3 = new Target(this.classNode, var1);
         this.targetMethods.put(var2, var3);
      }

      return var3;
   }

   String getUniqueName(MethodNode var1, boolean var2) {
      String var3 = Integer.toHexString(this.nextUniqueMethodIndex++);

      String var10000;
      label17: {
         try {
            if (var2) {
               var10000 = "%2$s_$md$%1$s$%3$s";
               break label17;
            }
         } catch (IllegalArgumentException var5) {
            throw b(var5);
         }

         var10000 = "md%s$%s$%s";
      }

      String var4 = var10000;
      return String.format(var4, this.sessionId.substring(30), var1.name, var3);
   }

   String getUniqueName(FieldNode var1) {
      String var2 = Integer.toHexString(this.nextUniqueFieldIndex++);
      return String.format("fd%s$%s$%s", this.sessionId.substring(30), var1.name, var2);
   }

   void applyMixins() {
      try {
         if (this.applied) {
            throw new IllegalStateException("Mixins already applied to target class " + this.className);
         }
      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }

      this.applied = true;
      MixinApplicatorStandard var1 = this.createApplicator();
      var1.apply(this.mixins);
      this.applySignature();
      this.upgradeMethods();
      this.checkMerges();
   }

   private MixinApplicatorStandard createApplicator() {
      try {
         if (this.classInfo.isInterface()) {
            return new MixinApplicatorInterface(this);
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      return new MixinApplicatorStandard(this);
   }

   private void applySignature() {
      this.getClassNode().signature = this.signature.toString();
   }

   private void checkMerges() {
      Iterator var1 = this.mixinMethods.iterator();

      while(var1.hasNext()) {
         MethodNode var2 = (MethodNode)var1.next();

         try {
            if (!var2.name.startsWith("<")) {
               logger.debug("Unexpected: Registered method {}{} in {} was not merged", new Object[]{var2.name, var2.desc, this});
            }
         } catch (IllegalArgumentException var3) {
            throw b(var3);
         }
      }

   }

   void processDebugTasks() {
      // $FF: Couldn't be decompiled
   }

   private static IllegalArgumentException b(IllegalArgumentException var0) {
      return var0;
   }
}

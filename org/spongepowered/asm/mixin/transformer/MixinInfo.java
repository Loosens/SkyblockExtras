package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.MixinEnvironment$Option;
import org.spongepowered.asm.mixin.MixinEnvironment$Phase;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.perf.Profiler;
import org.spongepowered.asm.util.perf.Profiler$Section;

class MixinInfo implements Comparable<MixinInfo>, IMixinInfo {
   private static final IMixinService classLoaderUtil = MixinService.getService();
   static int mixinOrder = 0;
   private final transient Logger logger = LogManager.getLogger("mixin");
   private final transient Profiler profiler = MixinEnvironment.getProfiler();
   private final transient MixinConfig parent;
   private final String name;
   private final String className;
   private final int priority;
   private final boolean virtual;
   private final List<ClassInfo> targetClasses;
   private final List<String> targetClassNames;
   private final transient int order;
   private final transient IMixinService service;
   private final transient IMixinConfigPlugin plugin;
   private final transient MixinEnvironment$Phase phase;
   private final transient ClassInfo info;
   private final transient MixinInfo$SubType type;
   private final transient boolean strict;
   private transient MixinInfo$State pendingState;
   private transient MixinInfo$State state;

   MixinInfo(IMixinService var1, MixinConfig var2, String var3, boolean var4, IMixinConfigPlugin var5, boolean var6) {
      this.order = mixinOrder++;
      this.service = var1;
      this.parent = var2;
      this.name = var3;
      this.className = var2.getMixinPackage() + var3;
      this.plugin = var5;
      this.phase = var2.getEnvironment().getPhase();
      this.strict = var2.getEnvironment().getOption(MixinEnvironment$Option.DEBUG_TARGETS);

      try {
         byte[] var7 = this.loadMixinClass(this.className, var4);
         this.pendingState = new MixinInfo$State(this, var7);
         this.info = this.pendingState.getClassInfo();
         this.type = MixinInfo$SubType.getTypeFor(this);
      } catch (InvalidMixinException var10) {
         throw var10;
      } catch (Exception var11) {
         throw new InvalidMixinException(this, var11);
      }

      try {
         if (!this.type.isLoadable()) {
            classLoaderUtil.registerInvalidClass(this.className);
         }
      } catch (InvalidMixinException var12) {
         throw b(var12);
      }

      try {
         this.priority = this.readPriority(this.pendingState.getClassNode());
         this.virtual = this.readPseudo(this.pendingState.getClassNode());
         this.targetClasses = this.readTargetClasses(this.pendingState.getClassNode(), var6);
         this.targetClassNames = Collections.unmodifiableList(Lists.transform(this.targetClasses, Functions.toStringFunction()));
      } catch (InvalidMixinException var8) {
         throw var8;
      } catch (Exception var9) {
         throw new InvalidMixinException(this, var9);
      }
   }

   void validate() {
      try {
         if (this.pendingState == null) {
            throw new IllegalStateException("No pending validation state for " + this);
         }
      } catch (InvalidMixinException var5) {
         throw b(var5);
      }

      try {
         this.pendingState.validate(this.type, this.targetClasses);
         this.state = this.pendingState;
      } finally {
         this.pendingState = null;
      }

   }

   protected List<ClassInfo> readTargetClasses(MixinInfo$MixinClassNode var1, boolean var2) {
      try {
         if (var1 == null) {
            return Collections.emptyList();
         }
      } catch (InvalidMixinException var10) {
         throw b(var10);
      }

      AnnotationNode var3 = Annotations.getInvisible((ClassNode)var1, Mixin.class);

      try {
         if (var3 == null) {
            throw new InvalidMixinException(this, String.format("The mixin '%s' is missing an @Mixin annotation", this.className));
         }
      } catch (InvalidMixinException var9) {
         throw b(var9);
      }

      ArrayList var4 = new ArrayList();
      List var5 = (List)Annotations.getValue(var3, "value");
      List var6 = (List)Annotations.getValue(var3, "targets");

      try {
         if (var5 != null) {
            this.readTargets(var4, Lists.transform(var5, new MixinInfo$1(this)), var2, false);
         }
      } catch (InvalidMixinException var8) {
         throw b(var8);
      }

      try {
         if (var6 != null) {
            this.readTargets(var4, Lists.transform(var6, new MixinInfo$2(this)), var2, true);
         }

         return var4;
      } catch (InvalidMixinException var7) {
         throw b(var7);
      }
   }

   private void readTargets(Collection<ClassInfo> param1, Collection<String> param2, boolean param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   private boolean shouldApplyMixin(boolean param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   private ClassInfo getTarget(String param1, boolean param2) throws InvalidMixinException {
      // $FF: Couldn't be decompiled
   }

   private void handleTargetError(String var1) {
      try {
         if (this.strict) {
            this.logger.error(var1);
            throw new InvalidMixinException(this, var1);
         }
      } catch (InvalidMixinException var2) {
         throw b(var2);
      }

      this.logger.warn(var1);
   }

   protected int readPriority(ClassNode var1) {
      try {
         if (var1 == null) {
            return this.parent.getDefaultMixinPriority();
         }
      } catch (InvalidMixinException var6) {
         throw b(var6);
      }

      AnnotationNode var2 = Annotations.getInvisible(var1, Mixin.class);

      try {
         if (var2 == null) {
            throw new InvalidMixinException(this, String.format("The mixin '%s' is missing an @Mixin annotation", this.className));
         }
      } catch (InvalidMixinException var5) {
         throw b(var5);
      }

      Integer var3 = (Integer)Annotations.getValue(var2, "priority");

      int var10000;
      try {
         if (var3 == null) {
            var10000 = this.parent.getDefaultMixinPriority();
            return var10000;
         }
      } catch (InvalidMixinException var4) {
         throw b(var4);
      }

      var10000 = var3;
      return var10000;
   }

   protected boolean readPseudo(ClassNode var1) {
      boolean var10000;
      try {
         if (Annotations.getInvisible(var1, Pseudo.class) != null) {
            var10000 = true;
            return var10000;
         }
      } catch (InvalidMixinException var2) {
         throw b(var2);
      }

      var10000 = false;
      return var10000;
   }

   private boolean isReloading() {
      return this.pendingState instanceof MixinInfo$Reloaded;
   }

   private MixinInfo$State getState() {
      MixinInfo$State var10000;
      try {
         if (this.state != null) {
            var10000 = this.state;
            return var10000;
         }
      } catch (InvalidMixinException var1) {
         throw b(var1);
      }

      var10000 = this.pendingState;
      return var10000;
   }

   ClassInfo getClassInfo() {
      return this.info;
   }

   public IMixinConfig getConfig() {
      return this.parent;
   }

   MixinConfig getParent() {
      return this.parent;
   }

   public int getPriority() {
      return this.priority;
   }

   public String getName() {
      return this.name;
   }

   public String getClassName() {
      return this.className;
   }

   public String getClassRef() {
      return this.getClassInfo().getName();
   }

   public byte[] getClassBytes() {
      return this.getState().getClassBytes();
   }

   public boolean isDetachedSuper() {
      return this.getState().isDetachedSuper();
   }

   public boolean isUnique() {
      return this.getState().isUnique();
   }

   public boolean isVirtual() {
      return this.virtual;
   }

   public boolean isAccessor() {
      return this.type instanceof MixinInfo$SubType$Accessor;
   }

   public boolean isLoadable() {
      return this.type.isLoadable();
   }

   public Level getLoggingLevel() {
      return this.parent.getLoggingLevel();
   }

   public MixinEnvironment$Phase getPhase() {
      return this.phase;
   }

   public MixinInfo$MixinClassNode getClassNode(int var1) {
      return this.getState().createClassNode(var1);
   }

   public List<String> getTargetClasses() {
      return this.targetClassNames;
   }

   List<InterfaceInfo> getSoftImplements() {
      return Collections.unmodifiableList(this.getState().getSoftImplements());
   }

   Set<String> getSyntheticInnerClasses() {
      return Collections.unmodifiableSet(this.getState().getSyntheticInnerClasses());
   }

   Set<String> getInnerClasses() {
      return Collections.unmodifiableSet(this.getState().getInnerClasses());
   }

   List<ClassInfo> getTargets() {
      return Collections.unmodifiableList(this.targetClasses);
   }

   Set<String> getInterfaces() {
      return this.getState().getInterfaces();
   }

   MixinTargetContext createContextFor(TargetClassContext var1) {
      MixinInfo$MixinClassNode var2 = this.getClassNode(8);
      Profiler$Section var3 = this.profiler.begin("pre");
      MixinTargetContext var4 = this.type.createPreProcessor(var2).prepare().createContextFor(var1);
      var3.end();
      return var4;
   }

   private byte[] loadMixinClass(String var1, boolean var2) throws ClassNotFoundException {
      Object var3 = null;

      try {
         if (var2) {
            String var4 = this.service.getClassRestrictions(var1);

            try {
               if (var4.length() > 0) {
                  this.logger.error("Classloader restrictions [{}] encountered loading {}, name: {}", new Object[]{var4, this, var1});
               }
            } catch (ClassNotFoundException var5) {
               throw b(var5);
            }
         }

         byte[] var8 = this.service.getBytecodeProvider().getClassBytes(var1, var2);
         return var8;
      } catch (ClassNotFoundException var6) {
         throw new ClassNotFoundException(String.format("The specified mixin '%s' was not found", var1));
      } catch (IOException var7) {
         this.logger.warn("Failed to load mixin {}, the specified mixin will not be applied", new Object[]{var1});
         throw new InvalidMixinException(this, "An error was encountered whilst loading the mixin class", var7);
      }
   }

   void reloadMixin(byte[] var1) {
      try {
         if (this.pendingState != null) {
            throw new IllegalStateException("Cannot reload mixin while it is initialising");
         }
      } catch (InvalidMixinException var2) {
         throw b(var2);
      }

      this.pendingState = new MixinInfo$Reloaded(this, this.state, var1);
      this.validate();
   }

   public int compareTo(MixinInfo var1) {
      try {
         if (var1 == null) {
            return 0;
         }
      } catch (InvalidMixinException var2) {
         throw b(var2);
      }

      try {
         if (var1.priority == this.priority) {
            return this.order - var1.order;
         }
      } catch (InvalidMixinException var3) {
         throw b(var3);
      }

      return this.priority - var1.priority;
   }

   public void preApply(String var1, ClassNode var2) {
      if (this.plugin != null) {
         Profiler$Section var3 = this.profiler.begin("plugin");
         this.plugin.preApply(var1, var2, this.className, this);
         var3.end();
      }

   }

   public void postApply(String var1, ClassNode var2) {
      if (this.plugin != null) {
         Profiler$Section var3 = this.profiler.begin("plugin");
         this.plugin.postApply(var1, var2, this.className, this);
         var3.end();
      }

      this.parent.postApply(var1, var2);
   }

   public String toString() {
      return String.format("%s:%s", this.parent.getName(), this.name);
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

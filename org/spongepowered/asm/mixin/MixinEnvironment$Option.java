package org.spongepowered.asm.mixin;

public enum MixinEnvironment$Option {
   DEBUG_ALL("debug"),
   DEBUG_EXPORT(DEBUG_ALL, "export"),
   DEBUG_EXPORT_FILTER(DEBUG_EXPORT, "filter", false),
   DEBUG_EXPORT_DECOMPILE(DEBUG_EXPORT, MixinEnvironment$Option$Inherit.ALLOW_OVERRIDE, "decompile"),
   DEBUG_EXPORT_DECOMPILE_THREADED(DEBUG_EXPORT_DECOMPILE, MixinEnvironment$Option$Inherit.ALLOW_OVERRIDE, "async"),
   DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES(DEBUG_EXPORT_DECOMPILE, MixinEnvironment$Option$Inherit.ALLOW_OVERRIDE, "mergeGenericSignatures"),
   DEBUG_VERIFY(DEBUG_ALL, "verify"),
   DEBUG_VERBOSE(DEBUG_ALL, "verbose"),
   DEBUG_INJECTORS(DEBUG_ALL, "countInjections"),
   DEBUG_STRICT(DEBUG_ALL, MixinEnvironment$Option$Inherit.INDEPENDENT, "strict"),
   DEBUG_UNIQUE(DEBUG_STRICT, "unique"),
   DEBUG_TARGETS(DEBUG_STRICT, "targets"),
   DEBUG_PROFILER(DEBUG_ALL, MixinEnvironment$Option$Inherit.ALLOW_OVERRIDE, "profiler"),
   DUMP_TARGET_ON_FAILURE("dumpTargetOnFailure"),
   CHECK_ALL("checks"),
   CHECK_IMPLEMENTS(CHECK_ALL, "interfaces"),
   CHECK_IMPLEMENTS_STRICT(CHECK_IMPLEMENTS, MixinEnvironment$Option$Inherit.ALLOW_OVERRIDE, "strict"),
   IGNORE_CONSTRAINTS("ignoreConstraints"),
   HOT_SWAP("hotSwap"),
   ENVIRONMENT(MixinEnvironment$Option$Inherit.ALWAYS_FALSE, "env"),
   OBFUSCATION_TYPE(ENVIRONMENT, MixinEnvironment$Option$Inherit.ALWAYS_FALSE, "obf"),
   DISABLE_REFMAP(ENVIRONMENT, MixinEnvironment$Option$Inherit.INDEPENDENT, "disableRefMap"),
   REFMAP_REMAP(ENVIRONMENT, MixinEnvironment$Option$Inherit.INDEPENDENT, "remapRefMap"),
   REFMAP_REMAP_RESOURCE(ENVIRONMENT, MixinEnvironment$Option$Inherit.INDEPENDENT, "refMapRemappingFile", ""),
   REFMAP_REMAP_SOURCE_ENV(ENVIRONMENT, MixinEnvironment$Option$Inherit.INDEPENDENT, "refMapRemappingEnv", "searge"),
   REFMAP_REMAP_ALLOW_PERMISSIVE(ENVIRONMENT, MixinEnvironment$Option$Inherit.INDEPENDENT, "allowPermissiveMatch", true, "true"),
   IGNORE_REQUIRED(ENVIRONMENT, MixinEnvironment$Option$Inherit.INDEPENDENT, "ignoreRequired"),
   DEFAULT_COMPATIBILITY_LEVEL(ENVIRONMENT, MixinEnvironment$Option$Inherit.INDEPENDENT, "compatLevel"),
   SHIFT_BY_VIOLATION_BEHAVIOUR(ENVIRONMENT, MixinEnvironment$Option$Inherit.INDEPENDENT, "shiftByViolation", "warn"),
   INITIALISER_INJECTION_MODE("initialiserInjectionMode", "default");

   private static final String PREFIX = "mixin";
   final MixinEnvironment$Option parent;
   final MixinEnvironment$Option$Inherit inheritance;
   final String property;
   final String defaultValue;
   final boolean isFlag;
   final int depth;
   private static final MixinEnvironment$Option[] $VALUES = new MixinEnvironment$Option[]{DEBUG_ALL, DEBUG_EXPORT, DEBUG_EXPORT_FILTER, DEBUG_EXPORT_DECOMPILE, DEBUG_EXPORT_DECOMPILE_THREADED, DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES, DEBUG_VERIFY, DEBUG_VERBOSE, DEBUG_INJECTORS, DEBUG_STRICT, DEBUG_UNIQUE, DEBUG_TARGETS, DEBUG_PROFILER, DUMP_TARGET_ON_FAILURE, CHECK_ALL, CHECK_IMPLEMENTS, CHECK_IMPLEMENTS_STRICT, IGNORE_CONSTRAINTS, HOT_SWAP, ENVIRONMENT, OBFUSCATION_TYPE, DISABLE_REFMAP, REFMAP_REMAP, REFMAP_REMAP_RESOURCE, REFMAP_REMAP_SOURCE_ENV, REFMAP_REMAP_ALLOW_PERMISSIVE, IGNORE_REQUIRED, DEFAULT_COMPATIBILITY_LEVEL, SHIFT_BY_VIOLATION_BEHAVIOUR, INITIALISER_INJECTION_MODE};

   private MixinEnvironment$Option(String var3) {
      this((MixinEnvironment$Option)null, var3, true);
   }

   private MixinEnvironment$Option(MixinEnvironment$Option$Inherit var3, String var4) {
      this((MixinEnvironment$Option)null, var3, var4, true);
   }

   private MixinEnvironment$Option(String var3, boolean var4) {
      this((MixinEnvironment$Option)null, var3, var4);
   }

   private MixinEnvironment$Option(String var3, String var4) {
      this((MixinEnvironment$Option)null, MixinEnvironment$Option$Inherit.INDEPENDENT, var3, false, var4);
   }

   private MixinEnvironment$Option(MixinEnvironment$Option var3, String var4) {
      this(var3, MixinEnvironment$Option$Inherit.INHERIT, var4, true);
   }

   private MixinEnvironment$Option(MixinEnvironment$Option var3, MixinEnvironment$Option$Inherit var4, String var5) {
      this(var3, var4, var5, true);
   }

   private MixinEnvironment$Option(MixinEnvironment$Option var3, String var4, boolean var5) {
      this(var3, MixinEnvironment$Option$Inherit.INHERIT, var4, var5, (String)null);
   }

   private MixinEnvironment$Option(MixinEnvironment$Option var3, MixinEnvironment$Option$Inherit var4, String var5, boolean var6) {
      this(var3, var4, var5, var6, (String)null);
   }

   private MixinEnvironment$Option(MixinEnvironment$Option var3, String var4, String var5) {
      this(var3, MixinEnvironment$Option$Inherit.INHERIT, var4, false, var5);
   }

   private MixinEnvironment$Option(MixinEnvironment$Option var3, MixinEnvironment$Option$Inherit var4, String var5, String var6) {
      this(var3, var4, var5, false, var6);
   }

   private MixinEnvironment$Option(MixinEnvironment$Option var3, MixinEnvironment$Option$Inherit var4, String var5, boolean var6, String var7) {
      this.parent = var3;
      this.inheritance = var4;
      this.property = (var3 != null ? var3.property : "mixin") + "." + var5;
      this.defaultValue = var7;
      this.isFlag = var6;

      int var8;
      for(var8 = 0; var3 != null; ++var8) {
         var3 = var3.parent;
      }

      this.depth = var8;
   }

   MixinEnvironment$Option getParent() {
      return this.parent;
   }

   String getProperty() {
      return this.property;
   }

   public String toString() {
      String var10000;
      try {
         if (this.isFlag) {
            var10000 = String.valueOf(this.getBooleanValue());
            return var10000;
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      var10000 = this.getStringValue();
      return var10000;
   }

   private boolean getLocalBooleanValue(boolean var1) {
      return Boolean.parseBoolean(System.getProperty(this.property, Boolean.toString(var1)));
   }

   private boolean getInheritedBooleanValue() {
      // $FF: Couldn't be decompiled
   }

   final boolean getBooleanValue() {
      // $FF: Couldn't be decompiled
   }

   final String getStringValue() {
      // $FF: Couldn't be decompiled
   }

   <E extends Enum<E>> E getEnumValue(E var1) {
      String var2 = System.getProperty(this.property, var1.name());

      try {
         return Enum.valueOf(var1.getClass(), var2.toUpperCase());
      } catch (IllegalArgumentException var4) {
         return var1;
      }
   }

   private static IllegalArgumentException b(IllegalArgumentException var0) {
      return var0;
   }
}

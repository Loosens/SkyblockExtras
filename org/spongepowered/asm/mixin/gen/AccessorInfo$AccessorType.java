package org.spongepowered.asm.mixin.gen;

import com.google.common.collect.ImmutableSet;
import java.util.Set;

public enum AccessorInfo$AccessorType {
   FIELD_GETTER(ImmutableSet.of("get", "is")),
   FIELD_SETTER(ImmutableSet.of("set")),
   METHOD_PROXY(ImmutableSet.of("call", "invoke"));

   private final Set<String> expectedPrefixes;
   private static final AccessorInfo$AccessorType[] $VALUES = new AccessorInfo$AccessorType[]{FIELD_GETTER, FIELD_SETTER, METHOD_PROXY};

   private AccessorInfo$AccessorType(Set<String> var3) {
      this.expectedPrefixes = var3;
   }

   public boolean isExpectedPrefix(String var1) {
      return this.expectedPrefixes.contains(var1);
   }

   public String getExpectedPrefixes() {
      return this.expectedPrefixes.toString();
   }

   abstract AccessorGenerator getGenerator(AccessorInfo var1);

   AccessorInfo$AccessorType(Set var3, AccessorInfo$1 var4) {
      this(var3);
   }
}

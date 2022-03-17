package org.spongepowered.asm.util;

import org.spongepowered.asm.lib.tree.AnnotationNode;

public final class ConstraintParser {
   private ConstraintParser() {
   }

   public static ConstraintParser$Constraint parse(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static ConstraintParser$Constraint parse(AnnotationNode var0) {
      String var1 = (String)Annotations.getValue(var0, "constraints", (Object)"");
      return parse(var1);
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

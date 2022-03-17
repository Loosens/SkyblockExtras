package org.spongepowered.asm.util;

import com.google.common.base.Function;
import org.spongepowered.asm.lib.tree.AnnotationNode;

final class Annotations$1 implements Function<AnnotationNode, String> {
   public String apply(AnnotationNode var1) {
      return var1.desc;
   }
}

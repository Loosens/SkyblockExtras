package org.spongepowered.asm.mixin.extensibility;

import java.util.List;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment$Phase;

public interface IMixinInfo {
   IMixinConfig getConfig();

   String getName();

   String getClassName();

   String getClassRef();

   byte[] getClassBytes();

   boolean isDetachedSuper();

   ClassNode getClassNode(int var1);

   List<String> getTargetClasses();

   int getPriority();

   MixinEnvironment$Phase getPhase();
}

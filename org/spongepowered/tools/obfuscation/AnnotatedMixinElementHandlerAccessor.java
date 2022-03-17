package org.spongepowered.tools.obfuscation;

import com.google.common.base.Strings;
import java.util.Iterator;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment$Option;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.gen.AccessorInfo;
import org.spongepowered.asm.mixin.gen.AccessorInfo$AccessorType;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public class AnnotatedMixinElementHandlerAccessor extends AnnotatedMixinElementHandler implements IMixinContext {
   public AnnotatedMixinElementHandlerAccessor(IMixinAnnotationProcessor var1, AnnotatedMixin var2) {
      super(var1, var2);
   }

   public ReferenceMapper getReferenceMapper() {
      return null;
   }

   public String getClassName() {
      return this.mixin.getClassRef().replace('/', '.');
   }

   public String getClassRef() {
      return this.mixin.getClassRef();
   }

   public String getTargetClassRef() {
      throw new UnsupportedOperationException("Target class not available at compile time");
   }

   public IMixinInfo getMixin() {
      throw new UnsupportedOperationException("MixinInfo not available at compile time");
   }

   public Extensions getExtensions() {
      throw new UnsupportedOperationException("Mixin Extensions not available at compile time");
   }

   public boolean getOption(MixinEnvironment$Option var1) {
      throw new UnsupportedOperationException("Options not available at compile time");
   }

   public int getPriority() {
      throw new UnsupportedOperationException("Priority not available at compile time");
   }

   public Target getTargetMethod(MethodNode var1) {
      throw new UnsupportedOperationException("Target not available at compile time");
   }

   public void registerAccessor(AnnotatedMixinElementHandlerAccessor$AnnotatedElementAccessor var1) {
      try {
         if (var1.getAccessorType() == null) {
            var1.printMessage(this.ap, Kind.WARNING, "Unsupported accessor type");
            return;
         }
      } catch (ReferenceManager$ReferenceConflictException var7) {
         throw b(var7);
      }

      String var2 = this.getAccessorTargetName(var1);

      try {
         if (var2 == null) {
            var1.printMessage(this.ap, Kind.WARNING, "Cannot inflect accessor target name");
            return;
         }
      } catch (ReferenceManager$ReferenceConflictException var6) {
         throw b(var6);
      }

      var1.setTargetName(var2);
      Iterator var3 = this.mixin.getTargets().iterator();

      while(var3.hasNext()) {
         TypeHandle var4 = (TypeHandle)var3.next();

         try {
            if (var1.getAccessorType() == AccessorInfo$AccessorType.METHOD_PROXY) {
               this.registerInvokerForTarget((AnnotatedMixinElementHandlerAccessor$AnnotatedElementInvoker)var1, var4);
               continue;
            }
         } catch (ReferenceManager$ReferenceConflictException var5) {
            throw b(var5);
         }

         this.registerAccessorForTarget(var1, var4);
      }

   }

   private void registerAccessorForTarget(AnnotatedMixinElementHandlerAccessor$AnnotatedElementAccessor param1, TypeHandle param2) {
      // $FF: Couldn't be decompiled
   }

   private void registerInvokerForTarget(AnnotatedMixinElementHandlerAccessor$AnnotatedElementInvoker param1, TypeHandle param2) {
      // $FF: Couldn't be decompiled
   }

   private String getAccessorTargetName(AnnotatedMixinElementHandlerAccessor$AnnotatedElementAccessor var1) {
      String var2 = var1.getAnnotationValue();

      try {
         return Strings.isNullOrEmpty(var2) ? this.inflectAccessorTarget(var1) : var2;
      } catch (ReferenceManager$ReferenceConflictException var3) {
         throw b(var3);
      }
   }

   private String inflectAccessorTarget(AnnotatedMixinElementHandlerAccessor$AnnotatedElementAccessor var1) {
      return AccessorInfo.inflectTarget(var1.getSimpleName(), var1.getAccessorType(), "", this, false);
   }

   private static ReferenceManager$ReferenceConflictException b(ReferenceManager$ReferenceConflictException var0) {
      return var0;
   }
}

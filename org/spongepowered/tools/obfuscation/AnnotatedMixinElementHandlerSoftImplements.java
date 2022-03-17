package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.Interface$Remap;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

public class AnnotatedMixinElementHandlerSoftImplements extends AnnotatedMixinElementHandler {
   AnnotatedMixinElementHandlerSoftImplements(IMixinAnnotationProcessor var1, AnnotatedMixin var2) {
      super(var1, var2);
   }

   public void process(AnnotationHandle var1) {
      try {
         if (!this.mixin.remap()) {
            return;
         }
      } catch (Exception var11) {
         throw b(var11);
      }

      List var2 = var1.getAnnotationList("value");

      try {
         if (var2.size() < 1) {
            this.ap.printMessage(Kind.WARNING, "Empty @Implements annotation", this.mixin.getMixin(), var1.asMirror());
            return;
         }
      } catch (Exception var10) {
         throw b(var10);
      }

      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         AnnotationHandle var4 = (AnnotationHandle)var3.next();
         Interface$Remap var5 = (Interface$Remap)var4.getValue("remap", Interface$Remap.ALL);

         try {
            if (var5 == Interface$Remap.NONE) {
               continue;
            }
         } catch (Exception var9) {
            throw b(var9);
         }

         try {
            TypeHandle var6 = new TypeHandle((DeclaredType)var4.getValue("iface"));
            String var7 = (String)var4.getValue("prefix");
            this.processSoftImplements(var5, var6, var7);
         } catch (Exception var8) {
            this.ap.printMessage(Kind.ERROR, "Unexpected error: " + var8.getClass().getName() + ": " + var8.getMessage(), this.mixin.getMixin(), var4.asMirror());
         }
      }

   }

   private void processSoftImplements(Interface$Remap var1, TypeHandle var2, String var3) {
      Iterator var4 = var2.getEnclosedElements(ElementKind.METHOD).iterator();

      while(var4.hasNext()) {
         ExecutableElement var5 = (ExecutableElement)var4.next();
         this.processMethod(var1, var2, var3, var5);
      }

      var4 = var2.getInterfaces().iterator();

      while(var4.hasNext()) {
         TypeHandle var6 = (TypeHandle)var4.next();
         this.processSoftImplements(var1, var6, var3);
      }

   }

   private void processMethod(Interface$Remap var1, TypeHandle var2, String var3, ExecutableElement var4) {
      String var5 = var4.getSimpleName().toString();
      String var6 = TypeUtils.getJavaSignature((Element)var4);
      String var7 = TypeUtils.getDescriptor(var4);
      MethodHandle var8;
      if (var1 != Interface$Remap.ONLY_PREFIXED) {
         var8 = this.mixin.getHandle().findMethod(var5, var6);

         try {
            if (var8 != null) {
               this.addInterfaceMethodMapping(var1, var2, (String)null, var8, var5, var7);
            }
         } catch (RuntimeException var10) {
            throw b(var10);
         }
      }

      if (var3 != null) {
         var8 = this.mixin.getHandle().findMethod(var3 + var5, var6);

         try {
            if (var8 != null) {
               this.addInterfaceMethodMapping(var1, var2, var3, var8, var5, var7);
            }
         } catch (RuntimeException var9) {
            throw b(var9);
         }
      }

   }

   private void addInterfaceMethodMapping(Interface$Remap param1, TypeHandle param2, String param3, MethodHandle param4, String param5, String param6) {
      // $FF: Couldn't be decompiled
   }

   private ObfuscationData<MappingMethod> applyPrefix(ObfuscationData<MappingMethod> var1, String var2) {
      try {
         if (var2 == null) {
            return var1;
         }
      } catch (RuntimeException var7) {
         throw b(var7);
      }

      ObfuscationData var3 = new ObfuscationData();
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         ObfuscationType var5 = (ObfuscationType)var4.next();
         MappingMethod var6 = (MappingMethod)var1.get(var5);
         var3.put(var5, var6.addPrefix(var2));
      }

      return var3;
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

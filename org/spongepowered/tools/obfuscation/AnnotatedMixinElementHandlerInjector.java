package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.InvalidMemberDescriptorException;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

class AnnotatedMixinElementHandlerInjector extends AnnotatedMixinElementHandler {
   AnnotatedMixinElementHandlerInjector(IMixinAnnotationProcessor var1, AnnotatedMixin var2) {
      super(var1, var2);
   }

   public void registerInjector(AnnotatedMixinElementHandlerInjector$AnnotatedElementInjector var1) {
      try {
         if (this.mixin.isInterface()) {
            this.ap.printMessage(Kind.ERROR, "Injector in interface is unsupported", var1.getElement());
         }
      } catch (InvalidMemberDescriptorException var12) {
         throw b(var12);
      }

      Iterator var2 = var1.getAnnotation().getList("method").iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         MemberInfo var4 = MemberInfo.parse(var3);

         try {
            if (var4.name == null) {
               continue;
            }
         } catch (InvalidMemberDescriptorException var11) {
            throw b(var11);
         }

         try {
            var4.validate();
         } catch (InvalidMemberDescriptorException var8) {
            var1.printMessage(this.ap, Kind.ERROR, var8.getMessage());
         }

         try {
            if (var4.desc != null) {
               this.validateReferencedTarget((ExecutableElement)var1.getElement(), var1.getAnnotation(), var4, var1.toString());
            }
         } catch (InvalidMemberDescriptorException var7) {
            throw b(var7);
         }

         try {
            if (!var1.shouldRemap()) {
               continue;
            }
         } catch (InvalidMemberDescriptorException var10) {
            throw b(var10);
         }

         Iterator var5 = this.mixin.getTargets().iterator();

         while(var5.hasNext()) {
            TypeHandle var6 = (TypeHandle)var5.next();

            try {
               if (!this.registerInjector(var1, var3, var4, var6)) {
                  break;
               }
            } catch (InvalidMemberDescriptorException var9) {
               throw b(var9);
            }
         }
      }

   }

   private boolean registerInjector(AnnotatedMixinElementHandlerInjector$AnnotatedElementInjector param1, String param2, MemberInfo param3, TypeHandle param4) {
      // $FF: Couldn't be decompiled
   }

   public void registerInjectionPoint(AnnotatedMixinElementHandlerInjector$AnnotatedElementInjectionPoint var1, String var2) {
      try {
         if (this.mixin.isInterface()) {
            this.ap.printMessage(Kind.ERROR, "Injector in interface is unsupported", var1.getElement());
         }
      } catch (ReferenceManager$ReferenceConflictException var5) {
         throw b(var5);
      }

      try {
         if (!var1.shouldRemap()) {
            return;
         }
      } catch (ReferenceManager$ReferenceConflictException var7) {
         throw b(var7);
      }

      String var3 = InjectionPointData.parseType((String)var1.getAt().getValue("value"));
      String var4 = (String)var1.getAt().getValue("target");

      try {
         if ("NEW".equals(var3)) {
            this.remapNewTarget(String.format(var2, var3 + ".<target>"), var4, var1);
            this.remapNewTarget(String.format(var2, var3 + ".args[class]"), var1.getAtArg("class"), var1);
            return;
         }
      } catch (ReferenceManager$ReferenceConflictException var6) {
         throw b(var6);
      }

      this.remapReference(String.format(var2, var3 + ".<target>"), var4, var1);
   }

   protected final void remapNewTarget(String var1, String var2, AnnotatedMixinElementHandlerInjector$AnnotatedElementInjectionPoint var3) {
      try {
         if (var2 == null) {
            return;
         }
      } catch (ReferenceManager$ReferenceConflictException var13) {
         throw b(var13);
      }

      MemberInfo var4 = MemberInfo.parse(var2);
      String var5 = var4.toCtorType();
      if (var5 != null) {
         String var6 = var4.toCtorDesc();

         MappingMethod var10000;
         MappingMethod var10001;
         String var10002;
         String var10003;
         String var10004;
         label63: {
            try {
               var10000 = new MappingMethod;
               var10001 = var10000;
               var10002 = var5;
               var10003 = ".";
               if (var6 != null) {
                  var10004 = var6;
                  break label63;
               }
            } catch (ReferenceManager$ReferenceConflictException var16) {
               throw b(var16);
            }

            var10004 = "()V";
         }

         var10001.<init>(var10002, var10003, var10004);
         MappingMethod var7 = var10000;
         ObfuscationData var8 = this.obf.getDataProvider().getRemappedMethod(var7);

         try {
            if (var8.isEmpty()) {
               this.ap.printMessage(Kind.WARNING, "Cannot find class mapping for " + var1 + " '" + var5 + "'", var3.getElement(), var3.getAnnotation().asMirror());
               return;
            }
         } catch (ReferenceManager$ReferenceConflictException var15) {
            throw b(var15);
         }

         ObfuscationData var9 = new ObfuscationData();
         Iterator var10 = var8.iterator();

         label44:
         while(true) {
            ObfuscationType var11;
            MappingMethod var12;
            while(true) {
               if (!var10.hasNext()) {
                  this.obf.getReferenceManager().addClassMapping(this.classRef, var2, var9);
                  break label44;
               }

               var11 = (ObfuscationType)var10.next();
               var12 = (MappingMethod)var8.get(var11);

               try {
                  if (var6 != null) {
                     break;
                  }

                  var9.put(var11, var12.getOwner());
               } catch (ReferenceManager$ReferenceConflictException var14) {
                  throw b(var14);
               }
            }

            var9.put(var11, var12.getDesc().replace(")V", ")L" + var12.getOwner() + ";"));
         }
      }

      var3.notifyRemapped();
   }

   protected final void remapReference(String param1, String param2, AnnotatedMixinElementHandlerInjector$AnnotatedElementInjectionPoint param3) {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

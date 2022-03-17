package org.spongepowered.asm.mixin.transformer;

import java.util.Iterator;
import java.util.Map.Entry;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidInterfaceMixinException;

class MixinApplicatorInterface extends MixinApplicatorStandard {
   MixinApplicatorInterface(TargetClassContext var1) {
      super(var1);
   }

   protected void applyInterfaces(MixinTargetContext param1) {
      // $FF: Couldn't be decompiled
   }

   protected void applyFields(MixinTargetContext var1) {
      Iterator var2 = var1.getShadowFields().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         FieldNode var4 = (FieldNode)var3.getKey();
         this.logger.error("Ignoring redundant @Shadow field {}:{} in {}", new Object[]{var4.name, var4.desc, var1});
      }

      this.mergeNewFields(var1);
   }

   protected void applyInitialisers(MixinTargetContext var1) {
   }

   protected void prepareInjections(MixinTargetContext var1) {
      Iterator var2 = this.targetClass.methods.iterator();

      while(var2.hasNext()) {
         MethodNode var3 = (MethodNode)var2.next();

         try {
            InjectionInfo var9 = InjectionInfo.parse(var1, var3);

            try {
               if (var9 != null) {
                  throw new InvalidInterfaceMixinException(var1, var9 + " is not supported on interface mixin method " + var3.name);
               }
            } catch (InvalidInjectionException var6) {
               throw b(var6);
            }
         } catch (InvalidInjectionException var8) {
            InvalidInjectionException var4 = var8;

            String var10000;
            label32: {
               try {
                  if (var4.getInjectionInfo() != null) {
                     var10000 = var4.getInjectionInfo().toString();
                     break label32;
                  }
               } catch (InvalidInjectionException var7) {
                  throw b(var7);
               }

               var10000 = "Injection";
            }

            String var5 = var10000;
            throw new InvalidInterfaceMixinException(var1, var5 + " is not supported in interface mixin");
         }
      }

   }

   protected void applyInjections(MixinTargetContext var1) {
   }

   private static InvalidInjectionException b(InvalidInjectionException var0) {
      return var0;
   }
}

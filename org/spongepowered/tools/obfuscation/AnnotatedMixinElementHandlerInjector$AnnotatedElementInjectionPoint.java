package org.spongepowered.tools.obfuscation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.lang.model.element.ExecutableElement;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.struct.InjectorRemap;

class AnnotatedMixinElementHandlerInjector$AnnotatedElementInjectionPoint extends AnnotatedMixinElementHandler$AnnotatedElement<ExecutableElement> {
   private final AnnotationHandle at;
   private Map<String, String> args;
   private final InjectorRemap state;

   public AnnotatedMixinElementHandlerInjector$AnnotatedElementInjectionPoint(ExecutableElement var1, AnnotationHandle var2, AnnotationHandle var3, InjectorRemap var4) {
      super(var1, var2);
      this.at = var3;
      this.state = var4;
   }

   public boolean shouldRemap() {
      return this.at.getBoolean("remap", this.state.shouldRemap());
   }

   public AnnotationHandle getAt() {
      return this.at;
   }

   public String getAtArg(String var1) {
      if (this.args == null) {
         this.args = new HashMap();
         Iterator var2 = this.at.getList("args").iterator();

         while(true) {
            String var3;
            while(true) {
               while(true) {
                  if (!var2.hasNext()) {
                     return (String)this.args.get(var1);
                  }

                  var3 = (String)var2.next();

                  try {
                     if (var3 == null) {
                        continue;
                     }
                     break;
                  } catch (RuntimeException var5) {
                     throw b(var5);
                  }
               }

               int var4 = var3.indexOf(61);

               try {
                  if (var4 <= -1) {
                     break;
                  }

                  this.args.put(var3.substring(0, var4), var3.substring(var4 + 1));
               } catch (RuntimeException var6) {
                  throw b(var6);
               }
            }

            this.args.put(var3, "");
         }
      } else {
         return (String)this.args.get(var1);
      }
   }

   public void notifyRemapped() {
      this.state.notifyRemapped();
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

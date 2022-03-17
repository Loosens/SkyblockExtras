package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.util.Counter;

public class MethodMapper {
   private static final Logger logger = LogManager.getLogger("mixin");
   private static final List<String> classes = new ArrayList();
   private static final Map<String, Counter> methods = new HashMap();
   private final ClassInfo info;

   public MethodMapper(MixinEnvironment var1, ClassInfo var2) {
      this.info = var2;
   }

   public ClassInfo getClassInfo() {
      return this.info;
   }

   public void remapHandlerMethod(MixinInfo param1, MethodNode param2, ClassInfo$Method param3) {
      // $FF: Couldn't be decompiled
   }

   public String getHandlerName(MixinInfo$MixinMethodNode var1) {
      String var2 = InjectionInfo.getInjectorPrefix(var1.getInjectorAnnotation());
      String var3 = getClassUID(var1.getOwner().getClassRef());

      String var10000;
      String var10001;
      boolean var10002;
      label17: {
         try {
            var10000 = var1.name;
            var10001 = var1.desc;
            if (!var1.isSurrogate()) {
               var10002 = true;
               break label17;
            }
         } catch (RuntimeException var5) {
            throw b(var5);
         }

         var10002 = false;
      }

      String var4 = getMethodUID(var10000, var10001, var10002);
      return String.format("%s$%s$%s%s", var2, var1.name, var3, var4);
   }

   private static String getClassUID(String var0) {
      int var1 = classes.indexOf(var0);
      if (var1 < 0) {
         var1 = classes.size();
         classes.add(var0);
      }

      return finagle(var1);
   }

   private static String getMethodUID(String var0, String var1, boolean var2) {
      String var3 = String.format("%s%s", var0, var1);
      Counter var4 = (Counter)methods.get(var3);
      if (var4 == null) {
         var4 = new Counter();
         methods.put(var3, var4);
      } else {
         try {
            if (var2) {
               ++var4.value;
            }
         } catch (RuntimeException var5) {
            throw b(var5);
         }
      }

      return String.format("%03x", var4.value);
   }

   private static String finagle(int var0) {
      String var1 = Integer.toHexString(var0);
      StringBuilder var2 = new StringBuilder();

      for(int var3 = 0; var3 < var1.length(); ++var3) {
         char var4 = var1.charAt(var3);

         StringBuilder var10000;
         char var10001;
         byte var10002;
         label23: {
            try {
               var10000 = var2;
               var10001 = var4;
               if (var4 < ':') {
                  var10002 = 49;
                  break label23;
               }
            } catch (RuntimeException var5) {
               throw b(var5);
            }

            var10002 = 10;
         }

         var10000.append((char)(var10001 + var10002));
      }

      return Strings.padStart(var2.toString(), 3, 'z');
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

package org.spongepowered.asm.service.mojang;

import net.minecraft.launchwrapper.Launch;
import org.spongepowered.asm.service.IGlobalPropertyService;

public class Blackboard implements IGlobalPropertyService {
   public final <T> T getProperty(String var1) {
      return Launch.blackboard.get(var1);
   }

   public final void setProperty(String var1, Object var2) {
      Launch.blackboard.put(var1, var2);
   }

   public final <T> T getProperty(String var1, T var2) {
      Object var3 = Launch.blackboard.get(var1);

      Object var10000;
      try {
         if (var3 != null) {
            var10000 = var3;
            return var10000;
         }
      } catch (RuntimeException var4) {
         throw b(var4);
      }

      var10000 = var2;
      return var10000;
   }

   public final String getPropertyString(String var1, String var2) {
      Object var3 = Launch.blackboard.get(var1);

      String var10000;
      try {
         if (var3 != null) {
            var10000 = var3.toString();
            return var10000;
         }
      } catch (RuntimeException var4) {
         throw b(var4);
      }

      var10000 = var2;
      return var10000;
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

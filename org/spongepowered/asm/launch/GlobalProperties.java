package org.spongepowered.asm.launch;

import java.util.ServiceLoader;
import org.spongepowered.asm.service.IGlobalPropertyService;

public final class GlobalProperties {
   private static IGlobalPropertyService service;

   private GlobalProperties() {
   }

   private static IGlobalPropertyService getService() {
      if (service == null) {
         ServiceLoader var0 = ServiceLoader.load(IGlobalPropertyService.class, GlobalProperties.class.getClassLoader());
         service = (IGlobalPropertyService)var0.iterator().next();
      }

      return service;
   }

   public static <T> T get(String var0) {
      return getService().getProperty(var0);
   }

   public static void put(String var0, Object var1) {
      getService().setProperty(var0, var1);
   }

   public static <T> T get(String var0, T var1) {
      return getService().getProperty(var0, var1);
   }

   public static String getString(String var0, String var1) {
      return getService().getPropertyString(var0, var1);
   }
}

package org.spongepowered.asm.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class MixinService {
   private static final Logger logger = LogManager.getLogger("mixin");
   private static MixinService instance;
   private ServiceLoader<IMixinServiceBootstrap> bootstrapServiceLoader;
   private final Set<String> bootedServices = new HashSet();
   private ServiceLoader<IMixinService> serviceLoader;
   private IMixinService service = null;

   private MixinService() {
      this.runBootServices();
   }

   private void runBootServices() {
      this.bootstrapServiceLoader = ServiceLoader.load(IMixinServiceBootstrap.class, this.getClass().getClassLoader());
      Iterator var1 = this.bootstrapServiceLoader.iterator();

      while(var1.hasNext()) {
         IMixinServiceBootstrap var2 = (IMixinServiceBootstrap)var1.next();

         try {
            var2.bootstrap();
            this.bootedServices.add(var2.getServiceClassName());
         } catch (Throwable var4) {
            logger.catching(var4);
         }
      }

   }

   private static MixinService getInstance() {
      try {
         if (instance == null) {
            instance = new MixinService();
         }
      } catch (RuntimeException var0) {
         throw b(var0);
      }

      return instance;
   }

   public static void boot() {
      getInstance();
   }

   public static IMixinService getService() {
      return getInstance().getServiceInstance();
   }

   private synchronized IMixinService getServiceInstance() {
      // $FF: Couldn't be decompiled
   }

   private IMixinService initService() {
      this.serviceLoader = ServiceLoader.load(IMixinService.class, this.getClass().getClassLoader());
      Iterator var1 = this.serviceLoader.iterator();

      while(var1.hasNext()) {
         try {
            IMixinService var2 = (IMixinService)var1.next();

            try {
               if (this.bootedServices.contains(var2.getClass().getName())) {
                  logger.debug("MixinService [{}] was successfully booted in {}", new Object[]{var2.getName(), this.getClass().getClassLoader()});
               }
            } catch (ServiceConfigurationError var3) {
               throw b(var3);
            }

            if (var2.isValid()) {
               return var2;
            }
         } catch (ServiceConfigurationError var4) {
            var4.printStackTrace();
         } catch (Throwable var5) {
            var5.printStackTrace();
         }
      }

      return null;
   }

   private static Throwable b(Throwable var0) {
      return var0;
   }
}

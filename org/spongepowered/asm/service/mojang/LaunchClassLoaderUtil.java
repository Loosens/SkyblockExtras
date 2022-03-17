package org.spongepowered.asm.service.mojang;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import net.minecraft.launchwrapper.LaunchClassLoader;

final class LaunchClassLoaderUtil {
   private static final String CACHED_CLASSES_FIELD = "cachedClasses";
   private static final String INVALID_CLASSES_FIELD = "invalidClasses";
   private static final String CLASS_LOADER_EXCEPTIONS_FIELD = "classLoaderExceptions";
   private static final String TRANSFORMER_EXCEPTIONS_FIELD = "transformerExceptions";
   private final LaunchClassLoader classLoader;
   private final Map<String, Class<?>> cachedClasses;
   private final Set<String> invalidClasses;
   private final Set<String> classLoaderExceptions;
   private final Set<String> transformerExceptions;

   LaunchClassLoaderUtil(LaunchClassLoader var1) {
      this.classLoader = var1;
      this.cachedClasses = (Map)getField(var1, "cachedClasses");
      this.invalidClasses = (Set)getField(var1, "invalidClasses");
      this.classLoaderExceptions = (Set)getField(var1, "classLoaderExceptions");
      this.transformerExceptions = (Set)getField(var1, "transformerExceptions");
   }

   LaunchClassLoader getClassLoader() {
      return this.classLoader;
   }

   boolean isClassLoaded(String var1) {
      return this.cachedClasses.containsKey(var1);
   }

   boolean isClassExcluded(String param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   boolean isClassClassLoaderExcluded(String param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   boolean isClassTransformerExcluded(String param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   void registerInvalidClass(String var1) {
      try {
         if (this.invalidClasses != null) {
            this.invalidClasses.add(var1);
         }

      } catch (RuntimeException var2) {
         throw b(var2);
      }
   }

   Set<String> getClassLoaderExceptions() {
      try {
         if (this.classLoaderExceptions != null) {
            return this.classLoaderExceptions;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      return Collections.emptySet();
   }

   Set<String> getTransformerExceptions() {
      try {
         if (this.transformerExceptions != null) {
            return this.transformerExceptions;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      return Collections.emptySet();
   }

   private static <T> T getField(LaunchClassLoader var0, String var1) {
      try {
         Field var2 = LaunchClassLoader.class.getDeclaredField(var1);
         var2.setAccessible(true);
         return var2.get(var0);
      } catch (Exception var3) {
         var3.printStackTrace();
         return null;
      }
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

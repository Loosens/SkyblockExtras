package org.spongepowered.asm.launch.platform;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

final class MainAttributes {
   private static final Map<URI, MainAttributes> instances = new HashMap();
   protected final Attributes attributes;

   private MainAttributes() {
      this.attributes = new Attributes();
   }

   private MainAttributes(File var1) {
      this.attributes = getAttributes(var1);
   }

   public final String get(String var1) {
      try {
         return this.attributes != null ? this.attributes.getValue(var1) : null;
      } catch (RuntimeException var2) {
         throw b(var2);
      }
   }

   private static Attributes getAttributes(File var0) {
      try {
         if (var0 == null) {
            return null;
         }
      } catch (IOException var22) {
         throw b(var22);
      }

      JarFile var1 = null;

      Attributes var3;
      try {
         var1 = new JarFile(var0);
         Manifest var2 = var1.getManifest();
         if (var2 == null) {
            return new Attributes();
         }

         var3 = var2.getMainAttributes();
      } catch (IOException var20) {
         return new Attributes();
      } finally {
         try {
            try {
               if (var1 != null) {
                  var1.close();
               }
            } catch (RuntimeException var18) {
               throw b(var18);
            }
         } catch (IOException var19) {
         }

      }

      return var3;
   }

   public static MainAttributes of(File var0) {
      return of(var0.toURI());
   }

   public static MainAttributes of(URI var0) {
      MainAttributes var1 = (MainAttributes)instances.get(var0);
      if (var1 == null) {
         var1 = new MainAttributes(new File(var0));
         instances.put(var0, var1);
      }

      return var1;
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

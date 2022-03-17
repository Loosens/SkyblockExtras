package org.spongepowered.asm.util;

public abstract class ObfuscationUtil {
   private ObfuscationUtil() {
   }

   public static String mapDescriptor(String var0, ObfuscationUtil$IClassRemapper var1) {
      return remapDescriptor(var0, var1, false);
   }

   public static String unmapDescriptor(String var0, ObfuscationUtil$IClassRemapper var1) {
      return remapDescriptor(var0, var1, true);
   }

   private static String remapDescriptor(String var0, ObfuscationUtil$IClassRemapper var1, boolean var2) {
      StringBuilder var3 = new StringBuilder();
      StringBuilder var4 = null;

      for(int var5 = 0; var5 < var0.length(); ++var5) {
         char var6 = var0.charAt(var5);

         label42: {
            label41: {
               try {
                  if (var4 == null) {
                     break label41;
                  }

                  if (var6 == ';') {
                     break label42;
                  }
               } catch (IllegalArgumentException var8) {
                  throw b(var8);
               }

               var4.append(var6);
               continue;
            }

            if (var6 == 'L') {
               var4 = new StringBuilder();
            } else {
               var3.append(var6);
            }
            continue;
         }

         var3.append('L').append(remap(var4.toString(), var1, var2)).append(';');
         var4 = null;
      }

      try {
         if (var4 != null) {
            throw new IllegalArgumentException("Invalid descriptor '" + var0 + "', missing ';'");
         }
      } catch (IllegalArgumentException var7) {
         throw b(var7);
      }

      return var3.toString();
   }

   private static Object remap(String var0, ObfuscationUtil$IClassRemapper var1, boolean var2) {
      String var10000;
      label30: {
         try {
            if (var2) {
               var10000 = var1.unmap(var0);
               break label30;
            }
         } catch (IllegalArgumentException var5) {
            throw b(var5);
         }

         var10000 = var1.map(var0);
      }

      String var3 = var10000;

      try {
         if (var3 != null) {
            var10000 = var3;
            return var10000;
         }
      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }

      var10000 = var0;
      return var10000;
   }

   private static IllegalArgumentException b(IllegalArgumentException var0) {
      return var0;
   }
}

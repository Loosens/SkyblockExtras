package org.spongepowered.asm.util;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VersionNumber implements Comparable<VersionNumber>, Serializable {
   private static final long serialVersionUID = 1L;
   public static final VersionNumber NONE = new VersionNumber();
   private static final Pattern PATTERN = Pattern.compile("^(\\d{1,5})(?:\\.(\\d{1,5})(?:\\.(\\d{1,5})(?:\\.(\\d{1,5}))?)?)?(-[a-zA-Z0-9_\\-]+)?$");
   private final long value;
   private final String suffix;

   private VersionNumber() {
      this.value = 0L;
      this.suffix = "";
   }

   private VersionNumber(short[] var1) {
      this(var1, (String)null);
   }

   private VersionNumber(short[] var1, String var2) {
      this.value = pack(var1);
      this.suffix = var2 != null ? var2 : "";
   }

   private VersionNumber(short var1, short var2, short var3, short var4) {
      this(var1, var2, var3, var4, (String)null);
   }

   private VersionNumber(short var1, short var2, short var3, short var4, String var5) {
      this.value = pack(var1, var2, var3, var4);
      this.suffix = var5 != null ? var5 : "";
   }

   public String toString() {
      short[] var1 = unpack(this.value);

      String var10000;
      Object[] var10001;
      Object[] var10002;
      byte var10003;
      String var10004;
      label29: {
         try {
            var10000 = "%d.%d%3$s%4$s%5$s";
            var10001 = new Object[]{var1[0], var1[1], null, null, null};
            var10002 = var10001;
            var10003 = 2;
            if ((this.value & 2147483647L) > 0L) {
               var10004 = String.format(".%d", var1[2]);
               break label29;
            }
         } catch (IllegalArgumentException var3) {
            throw b(var3);
         }

         var10004 = "";
      }

      label22: {
         try {
            var10002[var10003] = var10004;
            var10002 = var10001;
            var10003 = 3;
            if ((this.value & 32767L) > 0L) {
               var10004 = String.format(".%d", var1[3]);
               break label22;
            }
         } catch (IllegalArgumentException var2) {
            throw b(var2);
         }

         var10004 = "";
      }

      var10002[var10003] = var10004;
      var10001[4] = this.suffix;
      return String.format(var10000, var10001);
   }

   public int compareTo(VersionNumber var1) {
      try {
         if (var1 == null) {
            return 1;
         }
      } catch (IllegalArgumentException var6) {
         throw b(var6);
      }

      long var2 = this.value - var1.value;

      byte var10000;
      try {
         if (var2 > 0L) {
            var10000 = 1;
            return var10000;
         }
      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }

      try {
         if (var2 < 0L) {
            var10000 = -1;
            return var10000;
         }
      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }

      var10000 = 0;
      return var10000;
   }

   public boolean equals(Object var1) {
      try {
         if (!(var1 instanceof VersionNumber)) {
            return false;
         }
      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }

      boolean var10000;
      try {
         if (((VersionNumber)var1).value == this.value) {
            var10000 = true;
            return var10000;
         }
      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }

      var10000 = false;
      return var10000;
   }

   public int hashCode() {
      return (int)(this.value >> 32) ^ (int)(this.value & 4294967295L);
   }

   private static long pack(short... var0) {
      return (long)var0[0] << 48 | (long)var0[1] << 32 | (long)(var0[2] << 16) | (long)var0[3];
   }

   private static short[] unpack(long var0) {
      return new short[]{(short)((int)(var0 >> 48)), (short)((int)(var0 >> 32 & 32767L)), (short)((int)(var0 >> 16 & 32767L)), (short)((int)(var0 & 32767L))};
   }

   public static VersionNumber parse(String var0) {
      return parse(var0, NONE);
   }

   public static VersionNumber parse(String var0, String var1) {
      return parse(var0, parse(var1));
   }

   private static VersionNumber parse(String var0, VersionNumber var1) {
      try {
         if (var0 == null) {
            return var1;
         }
      } catch (IllegalArgumentException var9) {
         throw b(var9);
      }

      Matcher var2 = PATTERN.matcher(var0);

      try {
         if (!var2.matches()) {
            return var1;
         }
      } catch (IllegalArgumentException var8) {
         throw b(var8);
      }

      short[] var3 = new short[4];

      for(int var4 = 0; var4 < 4; ++var4) {
         String var5 = var2.group(var4 + 1);
         if (var5 != null) {
            int var6 = Integer.parseInt(var5);

            try {
               if (var6 > 32767) {
                  throw new IllegalArgumentException("Version parts cannot exceed 32767, found " + var6);
               }
            } catch (IllegalArgumentException var7) {
               throw b(var7);
            }

            var3[var4] = (short)var6;
         }
      }

      return new VersionNumber(var3, var2.group(5));
   }

   private static IllegalArgumentException b(IllegalArgumentException var0) {
      return var0;
   }
}

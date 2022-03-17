package org.spongepowered.asm.obfuscation.mapping.common;

import com.google.common.base.Objects;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.IMapping$Type;

public class MappingMethod implements IMapping<MappingMethod> {
   private final String owner;
   private final String name;
   private final String desc;

   public MappingMethod(String var1, String var2) {
      this(getOwnerFromName(var1), getBaseName(var1), var2);
   }

   public MappingMethod(String var1, String var2, String var3) {
      this.owner = var1;
      this.name = var2;
      this.desc = var3;
   }

   public IMapping$Type getType() {
      return IMapping$Type.METHOD;
   }

   public String getName() {
      try {
         if (this.name == null) {
            return null;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      StringBuilder var10000;
      String var10001;
      try {
         var10000 = new StringBuilder();
         if (this.owner != null) {
            var10001 = this.owner + "/";
            return var10000.append(var10001).append(this.name).toString();
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      var10001 = "";
      return var10000.append(var10001).append(this.name).toString();
   }

   public String getSimpleName() {
      return this.name;
   }

   public String getOwner() {
      return this.owner;
   }

   public String getDesc() {
      return this.desc;
   }

   public MappingMethod getSuper() {
      return null;
   }

   public boolean isConstructor() {
      return "<init>".equals(this.name);
   }

   public MappingMethod move(String var1) {
      return new MappingMethod(var1, this.getSimpleName(), this.getDesc());
   }

   public MappingMethod remap(String var1) {
      return new MappingMethod(this.getOwner(), var1, this.getDesc());
   }

   public MappingMethod transform(String var1) {
      return new MappingMethod(this.getOwner(), this.getSimpleName(), var1);
   }

   public MappingMethod copy() {
      return new MappingMethod(this.getOwner(), this.getSimpleName(), this.getDesc());
   }

   public MappingMethod addPrefix(String param1) {
      // $FF: Couldn't be decompiled
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{this.getName(), this.getDesc()});
   }

   public boolean equals(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public String serialise() {
      return this.toString();
   }

   public String toString() {
      String var1 = this.getDesc();

      String var10000;
      Object[] var10001;
      Object[] var10002;
      byte var10003;
      String var10004;
      label29: {
         try {
            var10000 = "%s%s%s";
            var10001 = new Object[]{this.getName(), null, null};
            var10002 = var10001;
            var10003 = 1;
            if (var1 != null) {
               var10004 = " ";
               break label29;
            }
         } catch (RuntimeException var3) {
            throw b(var3);
         }

         var10004 = "";
      }

      label22: {
         try {
            var10002[var10003] = var10004;
            var10002 = var10001;
            var10003 = 2;
            if (var1 != null) {
               var10004 = var1;
               break label22;
            }
         } catch (RuntimeException var2) {
            throw b(var2);
         }

         var10004 = "";
      }

      var10002[var10003] = var10004;
      return String.format(var10000, var10001);
   }

   private static String getBaseName(String var0) {
      try {
         if (var0 == null) {
            return null;
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      int var1 = var0.lastIndexOf(47);

      String var10000;
      try {
         if (var1 > -1) {
            var10000 = var0.substring(var1 + 1);
            return var10000;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      var10000 = var0;
      return var10000;
   }

   private static String getOwnerFromName(String var0) {
      try {
         if (var0 == null) {
            return null;
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      int var1 = var0.lastIndexOf(47);

      String var10000;
      try {
         if (var1 > -1) {
            var10000 = var0.substring(0, var1);
            return var10000;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      var10000 = null;
      return var10000;
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

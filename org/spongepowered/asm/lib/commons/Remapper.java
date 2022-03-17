package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.signature.SignatureReader;
import org.spongepowered.asm.lib.signature.SignatureVisitor;
import org.spongepowered.asm.lib.signature.SignatureWriter;

public abstract class Remapper {
   public String mapDesc(String var1) {
      Type var2 = Type.getType(var1);
      switch(var2.getSort()) {
      case 9:
         String var3 = this.mapDesc(var2.getElementType().getDescriptor());

         for(int var6 = 0; var6 < var2.getDimensions(); ++var6) {
            var3 = '[' + var3;
         }

         return var3;
      case 10:
         String var4 = this.map(var2.getInternalName());

         try {
            if (var4 != null) {
               return 'L' + var4 + ';';
            }
         } catch (RuntimeException var5) {
            throw b(var5);
         }
      default:
         return var1;
      }
   }

   private Type mapType(Type var1) {
      String var2;
      switch(var1.getSort()) {
      case 9:
         var2 = this.mapDesc(var1.getElementType().getDescriptor());

         for(int var3 = 0; var3 < var1.getDimensions(); ++var3) {
            var2 = '[' + var2;
         }

         return Type.getType(var2);
      case 10:
         var2 = this.map(var1.getInternalName());

         Type var10000;
         try {
            if (var2 != null) {
               var10000 = Type.getObjectType(var2);
               return var10000;
            }
         } catch (RuntimeException var4) {
            throw b(var4);
         }

         var10000 = var1;
         return var10000;
      case 11:
         return Type.getMethodType(this.mapMethodDesc(var1.getDescriptor()));
      default:
         return var1;
      }
   }

   public String mapType(String var1) {
      try {
         if (var1 == null) {
            return null;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      return this.mapType(Type.getObjectType(var1)).getInternalName();
   }

   public String[] mapTypes(String[] param1) {
      // $FF: Couldn't be decompiled
   }

   public String mapMethodDesc(String var1) {
      try {
         if ("()V".equals(var1)) {
            return var1;
         }
      } catch (RuntimeException var7) {
         throw b(var7);
      }

      Type[] var2 = Type.getArgumentTypes(var1);
      StringBuilder var3 = new StringBuilder("(");
      int var4 = 0;

      try {
         while(var4 < var2.length) {
            var3.append(this.mapDesc(var2[var4].getDescriptor()));
            ++var4;
         }
      } catch (RuntimeException var6) {
         throw b(var6);
      }

      Type var8 = Type.getReturnType(var1);

      try {
         if (var8 == Type.VOID_TYPE) {
            var3.append(")V");
            return var3.toString();
         }
      } catch (RuntimeException var5) {
         throw b(var5);
      }

      var3.append(')').append(this.mapDesc(var8.getDescriptor()));
      return var3.toString();
   }

   public Object mapValue(Object var1) {
      try {
         if (var1 instanceof Type) {
            return this.mapType((Type)var1);
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      if (var1 instanceof Handle) {
         Handle var2 = (Handle)var1;
         return new Handle(var2.getTag(), this.mapType(var2.getOwner()), this.mapMethodName(var2.getOwner(), var2.getName(), var2.getDesc()), this.mapMethodDesc(var2.getDesc()), var2.isInterface());
      } else {
         return var1;
      }
   }

   public String mapSignature(String var1, boolean var2) {
      try {
         if (var1 == null) {
            return null;
         }
      } catch (RuntimeException var7) {
         throw b(var7);
      }

      SignatureReader var3 = new SignatureReader(var1);
      SignatureWriter var4 = new SignatureWriter();
      SignatureVisitor var5 = this.createSignatureRemapper(var4);

      try {
         if (var2) {
            var3.acceptType(var5);
            return var4.toString();
         }
      } catch (RuntimeException var6) {
         throw b(var6);
      }

      var3.accept(var5);
      return var4.toString();
   }

   /** @deprecated */
   @Deprecated
   protected SignatureVisitor createRemappingSignatureAdapter(SignatureVisitor var1) {
      return new SignatureRemapper(var1, this);
   }

   protected SignatureVisitor createSignatureRemapper(SignatureVisitor var1) {
      return this.createRemappingSignatureAdapter(var1);
   }

   public String mapMethodName(String var1, String var2, String var3) {
      return var2;
   }

   public String mapInvokeDynamicMethodName(String var1, String var2) {
      return var1;
   }

   public String mapFieldName(String var1, String var2, String var3) {
      return var2;
   }

   public String map(String var1) {
      return var1;
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

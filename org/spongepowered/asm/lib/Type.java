package org.spongepowered.asm.lib;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Type {
   public static final int VOID = 0;
   public static final int BOOLEAN = 1;
   public static final int CHAR = 2;
   public static final int BYTE = 3;
   public static final int SHORT = 4;
   public static final int INT = 5;
   public static final int FLOAT = 6;
   public static final int LONG = 7;
   public static final int DOUBLE = 8;
   public static final int ARRAY = 9;
   public static final int OBJECT = 10;
   public static final int METHOD = 11;
   public static final Type VOID_TYPE = new Type(0, (char[])null, 1443168256, 1);
   public static final Type BOOLEAN_TYPE = new Type(1, (char[])null, 1509950721, 1);
   public static final Type CHAR_TYPE = new Type(2, (char[])null, 1124075009, 1);
   public static final Type BYTE_TYPE = new Type(3, (char[])null, 1107297537, 1);
   public static final Type SHORT_TYPE = new Type(4, (char[])null, 1392510721, 1);
   public static final Type INT_TYPE = new Type(5, (char[])null, 1224736769, 1);
   public static final Type FLOAT_TYPE = new Type(6, (char[])null, 1174536705, 1);
   public static final Type LONG_TYPE = new Type(7, (char[])null, 1241579778, 1);
   public static final Type DOUBLE_TYPE = new Type(8, (char[])null, 1141048066, 1);
   private final int sort;
   private final char[] buf;
   private final int off;
   private final int len;

   private Type(int var1, char[] var2, int var3, int var4) {
      this.sort = var1;
      this.buf = var2;
      this.off = var3;
      this.len = var4;
   }

   public static Type getType(String var0) {
      return getType(var0.toCharArray(), 0);
   }

   public static Type getObjectType(String var0) {
      char[] var1 = var0.toCharArray();

      Type var10000;
      Type var10001;
      byte var10002;
      label17: {
         try {
            var10000 = new Type;
            var10001 = var10000;
            if (var1[0] == '[') {
               var10002 = 9;
               break label17;
            }
         } catch (RuntimeException var2) {
            throw b(var2);
         }

         var10002 = 10;
      }

      var10001.<init>(var10002, var1, 0, var1.length);
      return var10000;
   }

   public static Type getMethodType(String var0) {
      return getType(var0.toCharArray(), 0);
   }

   public static Type getMethodType(Type var0, Type... var1) {
      return getType(getMethodDescriptor(var0, var1));
   }

   public static Type getType(Class<?> param0) {
      // $FF: Couldn't be decompiled
   }

   public static Type getType(Constructor<?> var0) {
      return getType(getConstructorDescriptor(var0));
   }

   public static Type getType(Method var0) {
      return getType(getMethodDescriptor(var0));
   }

   public static Type[] getArgumentTypes(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static Type[] getArgumentTypes(Method var0) {
      Class[] var1 = var0.getParameterTypes();
      Type[] var2 = new Type[var1.length];
      int var3 = var1.length - 1;

      try {
         while(var3 >= 0) {
            var2[var3] = getType(var1[var3]);
            --var3;
         }

         return var2;
      } catch (RuntimeException var4) {
         throw b(var4);
      }
   }

   public static Type getReturnType(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static Type getReturnType(Method var0) {
      return getType(var0.getReturnType());
   }

   public static int getArgumentsAndReturnSizes(String param0) {
      // $FF: Couldn't be decompiled
   }

   private static Type getType(char[] var0, int var1) {
      int var2;
      label141: {
         try {
            switch(var0[var1]) {
            case 'B':
               return BYTE_TYPE;
            case 'C':
               return CHAR_TYPE;
            case 'D':
               return DOUBLE_TYPE;
            case 'E':
            case 'G':
            case 'H':
            case 'K':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'T':
            case 'U':
            case 'W':
            case 'X':
            case 'Y':
            default:
               return new Type(11, var0, var1, var0.length - var1);
            case 'F':
               return FLOAT_TYPE;
            case 'I':
               return INT_TYPE;
            case 'J':
               return LONG_TYPE;
            case 'L':
               break;
            case 'S':
               return SHORT_TYPE;
            case 'V':
               return VOID_TYPE;
            case 'Z':
               return BOOLEAN_TYPE;
            case '[':
               break label141;
            }
         } catch (RuntimeException var7) {
            throw b(var7);
         }

         var2 = 1;

         try {
            while(var0[var1 + var2] != ';') {
               ++var2;
            }
         } catch (RuntimeException var6) {
            throw b(var6);
         }

         return new Type(10, var0, var1 + 1, var2 - 1);
      }

      var2 = 1;

      try {
         while(var0[var1 + var2] == '[') {
            ++var2;
         }
      } catch (RuntimeException var5) {
         throw b(var5);
      }

      try {
         if (var0[var1 + var2] != 'L') {
            return new Type(9, var0, var1, var2 + 1);
         }

         ++var2;
      } catch (RuntimeException var4) {
         throw b(var4);
      }

      try {
         while(var0[var1 + var2] != ';') {
            ++var2;
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      return new Type(9, var0, var1, var2 + 1);
   }

   public int getSort() {
      return this.sort;
   }

   public int getDimensions() {
      int var1 = 1;

      try {
         while(this.buf[this.off + var1] == '[') {
            ++var1;
         }

         return var1;
      } catch (RuntimeException var2) {
         throw b(var2);
      }
   }

   public Type getElementType() {
      return getType(this.buf, this.off + this.getDimensions());
   }

   public String getClassName() {
      try {
         switch(this.sort) {
         case 0:
            return "void";
         case 1:
            return "boolean";
         case 2:
            return "char";
         case 3:
            return "byte";
         case 4:
            return "short";
         case 5:
            return "int";
         case 6:
            return "float";
         case 7:
            return "long";
         case 8:
            return "double";
         case 9:
            break;
         case 10:
            return (new String(this.buf, this.off, this.len)).replace('/', '.');
         default:
            return null;
         }
      } catch (RuntimeException var4) {
         throw b(var4);
      }

      StringBuilder var1 = new StringBuilder(this.getElementType().getClassName());
      int var2 = this.getDimensions();

      try {
         while(var2 > 0) {
            var1.append("[]");
            --var2;
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      return var1.toString();
   }

   public String getInternalName() {
      return new String(this.buf, this.off, this.len);
   }

   public Type[] getArgumentTypes() {
      return getArgumentTypes(this.getDescriptor());
   }

   public Type getReturnType() {
      return getReturnType(this.getDescriptor());
   }

   public int getArgumentsAndReturnSizes() {
      return getArgumentsAndReturnSizes(this.getDescriptor());
   }

   public String getDescriptor() {
      StringBuilder var1 = new StringBuilder();
      this.getDescriptor(var1);
      return var1.toString();
   }

   public static String getMethodDescriptor(Type var0, Type... var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append('(');
      int var3 = 0;

      try {
         while(var3 < var1.length) {
            var1[var3].getDescriptor(var2);
            ++var3;
         }
      } catch (RuntimeException var4) {
         throw b(var4);
      }

      var2.append(')');
      var0.getDescriptor(var2);
      return var2.toString();
   }

   private void getDescriptor(StringBuilder var1) {
      try {
         if (this.buf == null) {
            var1.append((char)((this.off & -16777216) >>> 24));
            return;
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      try {
         if (this.sort == 10) {
            var1.append('L');
            var1.append(this.buf, this.off, this.len);
            var1.append(';');
            return;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      var1.append(this.buf, this.off, this.len);
   }

   public static String getInternalName(Class<?> var0) {
      return var0.getName().replace('.', '/');
   }

   public static String getDescriptor(Class<?> var0) {
      StringBuilder var1 = new StringBuilder();
      getDescriptor(var1, var0);
      return var1.toString();
   }

   public static String getConstructorDescriptor(Constructor<?> var0) {
      Class[] var1 = var0.getParameterTypes();
      StringBuilder var2 = new StringBuilder();
      var2.append('(');
      int var3 = 0;

      try {
         while(var3 < var1.length) {
            getDescriptor(var2, var1[var3]);
            ++var3;
         }
      } catch (RuntimeException var4) {
         throw b(var4);
      }

      return var2.append(")V").toString();
   }

   public static String getMethodDescriptor(Method var0) {
      Class[] var1 = var0.getParameterTypes();
      StringBuilder var2 = new StringBuilder();
      var2.append('(');
      int var3 = 0;

      try {
         while(var3 < var1.length) {
            getDescriptor(var2, var1[var3]);
            ++var3;
         }
      } catch (RuntimeException var4) {
         throw b(var4);
      }

      var2.append(')');
      getDescriptor(var2, var0.getReturnType());
      return var2.toString();
   }

   private static void getDescriptor(StringBuilder var0, Class<?> var1) {
      Class var2 = var1;

      char var3;
      label73: {
         while(true) {
            label70: {
               try {
                  if (!var2.isPrimitive()) {
                     break label70;
                  }

                  if (var2 == Integer.TYPE) {
                     break;
                  }
               } catch (RuntimeException var8) {
                  throw b(var8);
               }

               if (var2 == Void.TYPE) {
                  var3 = 'V';
               } else if (var2 == Boolean.TYPE) {
                  var3 = 'Z';
               } else if (var2 == Byte.TYPE) {
                  var3 = 'B';
               } else if (var2 == Character.TYPE) {
                  var3 = 'C';
               } else if (var2 == Short.TYPE) {
                  var3 = 'S';
               } else if (var2 == Double.TYPE) {
                  var3 = 'D';
               } else if (var2 == Float.TYPE) {
                  var3 = 'F';
               } else {
                  var3 = 'J';
               }
               break label73;
            }

            if (!var2.isArray()) {
               var0.append('L');
               String var9 = var2.getName();
               int var4 = var9.length();

               for(int var5 = 0; var5 < var4; ++var5) {
                  char var6 = var9.charAt(var5);

                  StringBuilder var10000;
                  char var10001;
                  label55: {
                     try {
                        var10000 = var0;
                        if (var6 == '.') {
                           var10001 = '/';
                           break label55;
                        }
                     } catch (RuntimeException var7) {
                        throw b(var7);
                     }

                     var10001 = var6;
                  }

                  var10000.append(var10001);
               }

               var0.append(';');
               return;
            }

            var0.append('[');
            var2 = var2.getComponentType();
         }

         var3 = 'I';
      }

      var0.append(var3);
   }

   public int getSize() {
      int var10000;
      try {
         if (this.buf == null) {
            var10000 = this.off & 255;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = 1;
      return var10000;
   }

   public int getOpcode(int param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean equals(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public int hashCode() {
      int var1 = 13 * this.sort;
      if (this.sort >= 9) {
         int var2 = this.off;

         for(int var3 = var2 + this.len; var2 < var3; ++var2) {
            var1 = 17 * (var1 + this.buf[var2]);
         }
      }

      return var1;
   }

   public String toString() {
      return this.getDescriptor();
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

package org.spongepowered.tools.obfuscation.mirror;

import java.util.Iterator;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import org.spongepowered.asm.util.SignaturePrinter;

public abstract class TypeUtils {
   private static final int MAX_GENERIC_RECURSION_DEPTH = 5;
   private static final String OBJECT_SIG = "java.lang.Object";
   private static final String OBJECT_REF = "java/lang/Object";

   private TypeUtils() {
   }

   public static PackageElement getPackage(TypeMirror var0) {
      try {
         if (!(var0 instanceof DeclaredType)) {
            return null;
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      return getPackage((TypeElement)((DeclaredType)var0).asElement());
   }

   public static PackageElement getPackage(TypeElement var0) {
      Element var1 = var0.getEnclosingElement();

      while(true) {
         try {
            if (var1 == null || var1 instanceof PackageElement) {
               return (PackageElement)var1;
            }
         } catch (IllegalArgumentException var2) {
            throw b(var2);
         }

         var1 = var1.getEnclosingElement();
      }
   }

   public static String getElementType(Element var0) {
      try {
         if (var0 instanceof TypeElement) {
            return "TypeElement";
         }
      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }

      try {
         if (var0 instanceof ExecutableElement) {
            return "ExecutableElement";
         }
      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }

      try {
         if (var0 instanceof VariableElement) {
            return "VariableElement";
         }
      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }

      try {
         if (var0 instanceof PackageElement) {
            return "PackageElement";
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      try {
         if (var0 instanceof TypeParameterElement) {
            return "TypeParameterElement";
         }
      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }

      return var0.getClass().getSimpleName();
   }

   public static String stripGenerics(String var0) {
      StringBuilder var1 = new StringBuilder();
      int var2 = 0;

      for(int var3 = 0; var2 < var0.length(); ++var2) {
         char var4 = var0.charAt(var2);

         try {
            if (var4 == '<') {
               ++var3;
            }
         } catch (IllegalArgumentException var6) {
            throw b(var6);
         }

         try {
            if (var3 == 0) {
               var1.append(var4);
               continue;
            }
         } catch (IllegalArgumentException var7) {
            throw b(var7);
         }

         try {
            if (var4 == '>') {
               --var3;
            }
         } catch (IllegalArgumentException var5) {
            throw b(var5);
         }
      }

      return var1.toString();
   }

   public static String getName(VariableElement var0) {
      String var10000;
      try {
         if (var0 != null) {
            var10000 = var0.getSimpleName().toString();
            return var10000;
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      var10000 = null;
      return var10000;
   }

   public static String getName(ExecutableElement var0) {
      String var10000;
      try {
         if (var0 != null) {
            var10000 = var0.getSimpleName().toString();
            return var10000;
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      var10000 = null;
      return var10000;
   }

   public static String getJavaSignature(Element var0) {
      if (!(var0 instanceof ExecutableElement)) {
         return getTypeName(var0.asType());
      } else {
         ExecutableElement var1 = (ExecutableElement)var0;
         StringBuilder var2 = (new StringBuilder()).append("(");
         boolean var3 = false;

         for(Iterator var4 = var1.getParameters().iterator(); var4.hasNext(); var3 = true) {
            VariableElement var5 = (VariableElement)var4.next();

            try {
               if (var3) {
                  var2.append(',');
               }
            } catch (IllegalArgumentException var6) {
               throw b(var6);
            }

            var2.append(getTypeName(var5.asType()));
         }

         var2.append(')').append(getTypeName(var1.getReturnType()));
         return var2.toString();
      }
   }

   public static String getJavaSignature(String var0) {
      return (new SignaturePrinter("", var0)).setFullyQualified(true).toDescriptor();
   }

   public static String getTypeName(TypeMirror var0) {
      // $FF: Couldn't be decompiled
   }

   public static String getTypeName(DeclaredType var0) {
      try {
         if (var0 == null) {
            return "java.lang.Object";
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      return getInternalName((TypeElement)var0.asElement()).replace('/', '.');
   }

   public static String getDescriptor(Element var0) {
      try {
         if (var0 instanceof ExecutableElement) {
            return getDescriptor((ExecutableElement)var0);
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      try {
         if (var0 instanceof VariableElement) {
            return getInternalName((VariableElement)var0);
         }
      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }

      return getInternalName(var0.asType());
   }

   public static String getDescriptor(ExecutableElement var0) {
      try {
         if (var0 == null) {
            return null;
         }
      } catch (IllegalArgumentException var4) {
         throw b(var4);
      }

      StringBuilder var1 = new StringBuilder();
      Iterator var2 = var0.getParameters().iterator();

      while(var2.hasNext()) {
         VariableElement var3 = (VariableElement)var2.next();
         var1.append(getInternalName(var3));
      }

      String var5 = getInternalName(var0.getReturnType());
      return String.format("(%s)%s", var1, var5);
   }

   public static String getInternalName(VariableElement var0) {
      try {
         if (var0 == null) {
            return null;
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      return getInternalName(var0.asType());
   }

   public static String getInternalName(TypeMirror var0) {
      // $FF: Couldn't be decompiled
   }

   public static String getInternalName(DeclaredType var0) {
      try {
         if (var0 == null) {
            return "java/lang/Object";
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      return getInternalName((TypeElement)var0.asElement());
   }

   public static String getInternalName(TypeElement param0) {
      // $FF: Couldn't be decompiled
   }

   private static DeclaredType getUpperBound(TypeMirror var0) {
      try {
         return getUpperBound0(var0, 5);
      } catch (IllegalStateException var2) {
         throw new IllegalArgumentException("Type symbol \"" + var0 + "\" is too complex", var2);
      } catch (IllegalArgumentException var3) {
         throw new IllegalArgumentException("Unable to compute upper bound of type symbol " + var0, var3);
      }
   }

   private static DeclaredType getUpperBound0(TypeMirror var0, int var1) {
      try {
         if (var1 == 0) {
            throw new IllegalStateException("Generic symbol \"" + var0 + "\" is too complex, exceeded " + 5 + " iterations attempting to determine upper bound");
         }
      } catch (IllegalStateException var6) {
         throw b(var6);
      }

      try {
         if (var0 instanceof DeclaredType) {
            return (DeclaredType)var0;
         }
      } catch (IllegalStateException var7) {
         throw b(var7);
      }

      if (var0 instanceof TypeVariable) {
         try {
            TypeMirror var2 = ((TypeVariable)var0).getUpperBound();
            --var1;
            return getUpperBound0(var2, var1);
         } catch (IllegalStateException var3) {
            throw var3;
         } catch (IllegalArgumentException var4) {
            throw var4;
         } catch (Exception var5) {
            throw new IllegalArgumentException("Unable to compute upper bound of type symbol " + var0);
         }
      } else {
         return null;
      }
   }

   public static boolean isAssignable(ProcessingEnvironment param0, TypeMirror param1, TypeMirror param2) {
      // $FF: Couldn't be decompiled
   }

   private static TypeMirror toRawType(ProcessingEnvironment var0, DeclaredType var1) {
      return var0.getElementUtils().getTypeElement(((TypeElement)var1.asElement()).getQualifiedName()).asType();
   }

   public static Visibility getVisibility(Element var0) {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

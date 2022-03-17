package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.collect.ImmutableList;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

public final class AnnotationHandle {
   public static final AnnotationHandle MISSING = new AnnotationHandle((AnnotationMirror)null);
   private final AnnotationMirror annotation;

   private AnnotationHandle(AnnotationMirror var1) {
      this.annotation = var1;
   }

   public AnnotationMirror asMirror() {
      return this.annotation;
   }

   public boolean exists() {
      boolean var10000;
      try {
         if (this.annotation != null) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public String toString() {
      try {
         if (this.annotation == null) {
            return "@{UnknownAnnotation}";
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      return "@" + this.annotation.getAnnotationType().asElement().getSimpleName();
   }

   public <T> T getValue(String var1, T var2) {
      try {
         if (this.annotation == null) {
            return var2;
         }
      } catch (RuntimeException var8) {
         throw b(var8);
      }

      AnnotationValue var3 = this.getAnnotationValue(var1);

      label61: {
         try {
            if (var2 instanceof Enum && var3 != null) {
               break label61;
            }
         } catch (RuntimeException var7) {
            throw b(var7);
         }

         Object var10000;
         try {
            if (var3 != null) {
               var10000 = var3.getValue();
               return var10000;
            }
         } catch (RuntimeException var6) {
            throw b(var6);
         }

         var10000 = var2;
         return var10000;
      }

      VariableElement var4 = (VariableElement)var3.getValue();

      try {
         if (var4 == null) {
            return var2;
         }
      } catch (RuntimeException var5) {
         throw b(var5);
      }

      return Enum.valueOf(var2.getClass(), var4.getSimpleName().toString());
   }

   public <T> T getValue() {
      return this.getValue("value", (Object)null);
   }

   public <T> T getValue(String var1) {
      return this.getValue(var1, (Object)null);
   }

   public boolean getBoolean(String var1, boolean var2) {
      return (Boolean)this.getValue(var1, var2);
   }

   public AnnotationHandle getAnnotation(String var1) {
      Object var2 = this.getValue(var1);

      try {
         if (var2 instanceof AnnotationMirror) {
            return of((AnnotationMirror)var2);
         }
      } catch (RuntimeException var5) {
         throw b(var5);
      }

      if (var2 instanceof AnnotationValue) {
         Object var3 = ((AnnotationValue)var2).getValue();

         try {
            if (var3 instanceof AnnotationMirror) {
               return of((AnnotationMirror)var3);
            }
         } catch (RuntimeException var4) {
            throw b(var4);
         }
      }

      return null;
   }

   public <T> List<T> getList() {
      return this.getList("value");
   }

   public <T> List<T> getList(String var1) {
      List var2 = (List)this.getValue(var1, Collections.emptyList());
      return unwrapAnnotationValueList(var2);
   }

   public List<AnnotationHandle> getAnnotationList(String var1) {
      Object var2 = this.getValue(var1, (Object)null);

      try {
         if (var2 == null) {
            return Collections.emptyList();
         }
      } catch (RuntimeException var7) {
         throw b(var7);
      }

      try {
         if (var2 instanceof AnnotationMirror) {
            return ImmutableList.of(of((AnnotationMirror)var2));
         }
      } catch (RuntimeException var8) {
         throw b(var8);
      }

      List var3 = (List)var2;
      ArrayList var4 = new ArrayList(var3.size());
      Iterator var5 = var3.iterator();

      while(var5.hasNext()) {
         AnnotationValue var6 = (AnnotationValue)var5.next();
         var4.add(new AnnotationHandle((AnnotationMirror)var6.getValue()));
      }

      return Collections.unmodifiableList(var4);
   }

   protected AnnotationValue getAnnotationValue(String var1) {
      Iterator var2 = this.annotation.getElementValues().keySet().iterator();

      while(var2.hasNext()) {
         ExecutableElement var3 = (ExecutableElement)var2.next();

         try {
            if (var3.getSimpleName().contentEquals(var1)) {
               return (AnnotationValue)this.annotation.getElementValues().get(var3);
            }
         } catch (RuntimeException var4) {
            throw b(var4);
         }
      }

      return null;
   }

   protected static <T> List<T> unwrapAnnotationValueList(List<AnnotationValue> var0) {
      try {
         if (var0 == null) {
            return Collections.emptyList();
         }
      } catch (RuntimeException var4) {
         throw b(var4);
      }

      ArrayList var1 = new ArrayList(var0.size());
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         AnnotationValue var3 = (AnnotationValue)var2.next();
         var1.add(var3.getValue());
      }

      return var1;
   }

   protected static AnnotationMirror getAnnotation(Element var0, Class<? extends Annotation> var1) {
      try {
         if (var0 == null) {
            return null;
         }
      } catch (RuntimeException var10) {
         throw b(var10);
      }

      List var2 = var0.getAnnotationMirrors();

      try {
         if (var2 == null) {
            return null;
         }
      } catch (RuntimeException var9) {
         throw b(var9);
      }

      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         AnnotationMirror var4 = (AnnotationMirror)var3.next();
         Element var5 = var4.getAnnotationType().asElement();

         try {
            if (!(var5 instanceof TypeElement)) {
               continue;
            }
         } catch (RuntimeException var8) {
            throw b(var8);
         }

         TypeElement var6 = (TypeElement)var5;

         try {
            if (var6.getQualifiedName().contentEquals(var1.getName())) {
               return var4;
            }
         } catch (RuntimeException var7) {
            throw b(var7);
         }
      }

      return null;
   }

   public static AnnotationHandle of(AnnotationMirror var0) {
      return new AnnotationHandle(var0);
   }

   public static AnnotationHandle of(Element var0, Class<? extends Annotation> var1) {
      return new AnnotationHandle(getAnnotation(var0, var1));
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

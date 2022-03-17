package org.spongepowered.tools.obfuscation.mirror;

import java.io.Serializable;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

public class TypeReference implements Serializable, Comparable<TypeReference> {
   private static final long serialVersionUID = 1L;
   private final String name;
   private transient TypeHandle handle;

   public TypeReference(TypeHandle var1) {
      this.name = var1.getName();
      this.handle = var1;
   }

   public TypeReference(String var1) {
      this.name = var1;
   }

   public String getName() {
      return this.name;
   }

   public String getClassName() {
      return this.name.replace('/', '.');
   }

   public TypeHandle getHandle(ProcessingEnvironment var1) {
      if (this.handle == null) {
         TypeElement var2 = var1.getElementUtils().getTypeElement(this.getClassName());

         try {
            this.handle = new TypeHandle(var2);
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

      return this.handle;
   }

   public String toString() {
      return String.format("TypeReference[%s]", this.name);
   }

   public int compareTo(TypeReference var1) {
      int var10000;
      try {
         if (var1 == null) {
            var10000 = -1;
            return var10000;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      var10000 = this.name.compareTo(var1.name);
      return var10000;
   }

   public boolean equals(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

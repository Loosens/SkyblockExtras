package org.spongepowered.tools.obfuscation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ObfuscationData<T> implements Iterable<ObfuscationType> {
   private final Map<ObfuscationType, T> data;
   private final T defaultValue;

   public ObfuscationData() {
      this((Object)null);
   }

   public ObfuscationData(T var1) {
      this.data = new HashMap();
      this.defaultValue = var1;
   }

   /** @deprecated */
   @Deprecated
   public void add(ObfuscationType var1, T var2) {
      this.put(var1, var2);
   }

   public void put(ObfuscationType var1, T var2) {
      this.data.put(var1, var2);
   }

   public boolean isEmpty() {
      return this.data.isEmpty();
   }

   public T get(ObfuscationType var1) {
      Object var2 = this.data.get(var1);

      Object var10000;
      try {
         if (var2 != null) {
            var10000 = var2;
            return var10000;
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      var10000 = this.defaultValue;
      return var10000;
   }

   public Iterator<ObfuscationType> iterator() {
      return this.data.keySet().iterator();
   }

   public String toString() {
      return String.format("ObfuscationData[%s,DEFAULT=%s]", this.listValues(), this.defaultValue);
   }

   public String values() {
      return "[" + this.listValues() + "]";
   }

   private String listValues() {
      StringBuilder var1 = new StringBuilder();
      boolean var2 = false;

      for(Iterator var3 = this.data.keySet().iterator(); var3.hasNext(); var2 = true) {
         ObfuscationType var4 = (ObfuscationType)var3.next();

         try {
            if (var2) {
               var1.append(',');
            }
         } catch (RuntimeException var5) {
            throw b(var5);
         }

         var1.append(var4.getKey()).append('=').append(this.data.get(var4));
      }

      return var1.toString();
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

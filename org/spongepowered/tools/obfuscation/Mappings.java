package org.spongepowered.tools.obfuscation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer$MappingSet;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer$MappingSet$Pair;

class Mappings implements IMappingConsumer {
   private final Map<ObfuscationType, IMappingConsumer$MappingSet<MappingField>> fieldMappings = new HashMap();
   private final Map<ObfuscationType, IMappingConsumer$MappingSet<MappingMethod>> methodMappings = new HashMap();
   private Mappings$UniqueMappings unique;

   public Mappings() {
      this.init();
   }

   private void init() {
      Iterator var1 = ObfuscationType.types().iterator();

      while(var1.hasNext()) {
         ObfuscationType var2 = (ObfuscationType)var1.next();
         this.fieldMappings.put(var2, new IMappingConsumer$MappingSet());
         this.methodMappings.put(var2, new IMappingConsumer$MappingSet());
      }

   }

   public IMappingConsumer asUnique() {
      try {
         if (this.unique == null) {
            this.unique = new Mappings$UniqueMappings(this);
         }
      } catch (Mappings$MappingConflictException var1) {
         throw b(var1);
      }

      return this.unique;
   }

   public IMappingConsumer$MappingSet<MappingField> getFieldMappings(ObfuscationType var1) {
      IMappingConsumer$MappingSet var2 = (IMappingConsumer$MappingSet)this.fieldMappings.get(var1);

      IMappingConsumer$MappingSet var10000;
      try {
         if (var2 != null) {
            var10000 = var2;
            return var10000;
         }
      } catch (Mappings$MappingConflictException var3) {
         throw b(var3);
      }

      var10000 = new IMappingConsumer$MappingSet();
      return var10000;
   }

   public IMappingConsumer$MappingSet<MappingMethod> getMethodMappings(ObfuscationType var1) {
      IMappingConsumer$MappingSet var2 = (IMappingConsumer$MappingSet)this.methodMappings.get(var1);

      IMappingConsumer$MappingSet var10000;
      try {
         if (var2 != null) {
            var10000 = var2;
            return var10000;
         }
      } catch (Mappings$MappingConflictException var3) {
         throw b(var3);
      }

      var10000 = new IMappingConsumer$MappingSet();
      return var10000;
   }

   public void clear() {
      try {
         this.fieldMappings.clear();
         this.methodMappings.clear();
         if (this.unique != null) {
            this.unique.clearMaps();
         }
      } catch (Mappings$MappingConflictException var1) {
         throw b(var1);
      }

      this.init();
   }

   public void addFieldMapping(ObfuscationType var1, MappingField var2, MappingField var3) {
      IMappingConsumer$MappingSet var4 = (IMappingConsumer$MappingSet)this.fieldMappings.get(var1);
      if (var4 == null) {
         var4 = new IMappingConsumer$MappingSet();
         this.fieldMappings.put(var1, var4);
      }

      var4.add(new IMappingConsumer$MappingSet$Pair(var2, var3));
   }

   public void addMethodMapping(ObfuscationType var1, MappingMethod var2, MappingMethod var3) {
      IMappingConsumer$MappingSet var4 = (IMappingConsumer$MappingSet)this.methodMappings.get(var1);
      if (var4 == null) {
         var4 = new IMappingConsumer$MappingSet();
         this.methodMappings.put(var1, var4);
      }

      var4.add(new IMappingConsumer$MappingSet$Pair(var2, var3));
   }

   private static Mappings$MappingConflictException b(Mappings$MappingConflictException var0) {
      return var0;
   }
}

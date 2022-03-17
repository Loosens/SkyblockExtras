package org.spongepowered.tools.obfuscation;

import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer$MappingSet;

class Mappings$UniqueMappings implements IMappingConsumer {
   private final IMappingConsumer mappings;
   private final Map<ObfuscationType, Map<MappingField, MappingField>> fields = new HashMap();
   private final Map<ObfuscationType, Map<MappingMethod, MappingMethod>> methods = new HashMap();

   public Mappings$UniqueMappings(IMappingConsumer var1) {
      this.mappings = var1;
   }

   public void clear() {
      this.clearMaps();
      this.mappings.clear();
   }

   protected void clearMaps() {
      this.fields.clear();
      this.methods.clear();
   }

   public void addFieldMapping(ObfuscationType var1, MappingField var2, MappingField var3) {
      try {
         if (!this.checkForExistingMapping(var1, var2, var3, this.fields)) {
            this.mappings.addFieldMapping(var1, var2, var3);
         }

      } catch (Mappings$MappingConflictException var4) {
         throw b(var4);
      }
   }

   public void addMethodMapping(ObfuscationType var1, MappingMethod var2, MappingMethod var3) {
      try {
         if (!this.checkForExistingMapping(var1, var2, var3, this.methods)) {
            this.mappings.addMethodMapping(var1, var2, var3);
         }

      } catch (Mappings$MappingConflictException var4) {
         throw b(var4);
      }
   }

   private <TMapping extends IMapping<TMapping>> boolean checkForExistingMapping(ObfuscationType param1, TMapping param2, TMapping param3, Map<ObfuscationType, Map<TMapping, TMapping>> param4) throws Mappings$MappingConflictException {
      // $FF: Couldn't be decompiled
   }

   public IMappingConsumer$MappingSet<MappingField> getFieldMappings(ObfuscationType var1) {
      return this.mappings.getFieldMappings(var1);
   }

   public IMappingConsumer$MappingSet<MappingMethod> getMethodMappings(ObfuscationType var1) {
      return this.mappings.getMethodMappings(var1);
   }

   private static Mappings$MappingConflictException b(Mappings$MappingConflictException var0) {
      return var0;
   }
}

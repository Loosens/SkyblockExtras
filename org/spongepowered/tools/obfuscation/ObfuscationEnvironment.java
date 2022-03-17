package org.spongepowered.tools.obfuscation;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.ObfuscationUtil;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationEnvironment;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer$MappingSet;
import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;
import org.spongepowered.tools.obfuscation.mapping.IMappingWriter;

public abstract class ObfuscationEnvironment implements IObfuscationEnvironment {
   protected final ObfuscationType type;
   protected final IMappingProvider mappingProvider;
   protected final IMappingWriter mappingWriter;
   protected final ObfuscationEnvironment$RemapperProxy remapper = new ObfuscationEnvironment$RemapperProxy(this);
   protected final IMixinAnnotationProcessor ap;
   protected final String outFileName;
   protected final List<String> inFileNames;
   private boolean initDone;

   protected ObfuscationEnvironment(ObfuscationType var1) {
      this.type = var1;
      this.ap = var1.getAnnotationProcessor();
      this.inFileNames = var1.getInputFileNames();
      this.outFileName = var1.getOutputFileName();
      this.mappingProvider = this.getMappingProvider(this.ap, this.ap.getProcessingEnvironment().getFiler());
      this.mappingWriter = this.getMappingWriter(this.ap, this.ap.getProcessingEnvironment().getFiler());
   }

   public String toString() {
      return this.type.toString();
   }

   protected abstract IMappingProvider getMappingProvider(Messager var1, Filer var2);

   protected abstract IMappingWriter getMappingWriter(Messager var1, Filer var2);

   private boolean initMappings() {
      // $FF: Couldn't be decompiled
   }

   public ObfuscationType getType() {
      return this.type;
   }

   public MappingMethod getObfMethod(MemberInfo param1) {
      // $FF: Couldn't be decompiled
   }

   public MappingMethod getObfMethod(MappingMethod var1) {
      return this.getObfMethod(var1, true);
   }

   public MappingMethod getObfMethod(MappingMethod param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public MemberInfo remapDescriptor(MemberInfo var1) {
      boolean var2 = false;
      String var3 = var1.owner;
      String var4;
      if (var3 != null) {
         var4 = this.remapper.map(var3);
         if (var4 != null) {
            var3 = var4;
            var2 = true;
         }
      }

      var4 = var1.desc;
      if (var4 != null) {
         String var5 = ObfuscationUtil.mapDescriptor(var1.desc, this.remapper);
         if (!var5.equals(var1.desc)) {
            var4 = var5;
            var2 = true;
         }
      }

      MemberInfo var10000;
      try {
         if (var2) {
            var10000 = new MemberInfo(var1.name, var3, var4, var1.matchAll);
            return var10000;
         }
      } catch (RuntimeException var6) {
         throw b(var6);
      }

      var10000 = null;
      return var10000;
   }

   public String remapDescriptor(String var1) {
      return ObfuscationUtil.mapDescriptor(var1, this.remapper);
   }

   public MappingField getObfField(MemberInfo var1) {
      return this.getObfField(var1.asFieldMapping(), true);
   }

   public MappingField getObfField(MappingField var1) {
      return this.getObfField(var1, true);
   }

   public MappingField getObfField(MappingField param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public String getObfClass(String var1) {
      try {
         if (!this.initMappings()) {
            return null;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      return this.mappingProvider.getClassMapping(var1);
   }

   public void writeMappings(Collection<IMappingConsumer> var1) {
      IMappingConsumer$MappingSet var2 = new IMappingConsumer$MappingSet();
      IMappingConsumer$MappingSet var3 = new IMappingConsumer$MappingSet();
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         IMappingConsumer var5 = (IMappingConsumer)var4.next();
         var2.addAll(var5.getFieldMappings(this.type));
         var3.addAll(var5.getMethodMappings(this.type));
      }

      this.mappingWriter.write(this.outFileName, this.type, var2, var3);
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

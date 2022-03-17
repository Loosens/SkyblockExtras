package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public class ObfuscationDataProvider implements IObfuscationDataProvider {
   private final IMixinAnnotationProcessor ap;
   private final List<ObfuscationEnvironment> environments;

   public ObfuscationDataProvider(IMixinAnnotationProcessor var1, List<ObfuscationEnvironment> var2) {
      this.ap = var1;
      this.environments = var2;
   }

   public <T> ObfuscationData<T> getObfEntryRecursive(MemberInfo var1) {
      MemberInfo var2 = var1;
      ObfuscationData var3 = this.getObfClass(var1.owner);
      ObfuscationData var4 = this.getObfEntry(var1);

      try {
         TypeHandle var6;
         for(; var4.isEmpty(); var2 = var2.move(var6.getName())) {
            TypeHandle var5 = this.ap.getTypeProvider().getTypeHandle(var2.owner);
            if (var5 == null) {
               return var4;
            }

            var6 = var5.getSuperclass();
            var4 = this.getObfEntryUsing(var2, var6);
            if (!var4.isEmpty()) {
               return applyParents(var3, var4);
            }

            Iterator var7 = var5.getInterfaces().iterator();

            while(var7.hasNext()) {
               TypeHandle var8 = (TypeHandle)var7.next();
               var4 = this.getObfEntryUsing(var2, var8);
               if (!var4.isEmpty()) {
                  return applyParents(var3, var4);
               }
            }

            try {
               if (var6 == null) {
                  break;
               }
            } catch (Exception var9) {
               throw b(var9);
            }
         }

         return var4;
      } catch (Exception var10) {
         var10.printStackTrace();
         return this.getObfEntry(var1);
      }
   }

   private <T> ObfuscationData<T> getObfEntryUsing(MemberInfo var1, TypeHandle var2) {
      ObfuscationData var10000;
      try {
         if (var2 == null) {
            var10000 = new ObfuscationData();
            return var10000;
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      var10000 = this.getObfEntry(var1.move(var2.getName()));
      return var10000;
   }

   public <T> ObfuscationData<T> getObfEntry(MemberInfo var1) {
      try {
         if (var1.isField()) {
            return this.getObfField(var1);
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      return this.getObfMethod(var1.asMethodMapping());
   }

   public <T> ObfuscationData<T> getObfEntry(IMapping<T> param1) {
      // $FF: Couldn't be decompiled
   }

   public ObfuscationData<MappingMethod> getObfMethodRecursive(MemberInfo var1) {
      return this.getObfEntryRecursive(var1);
   }

   public ObfuscationData<MappingMethod> getObfMethod(MemberInfo var1) {
      return this.getRemappedMethod(var1, var1.isConstructor());
   }

   public ObfuscationData<MappingMethod> getRemappedMethod(MemberInfo var1) {
      return this.getRemappedMethod(var1, true);
   }

   private ObfuscationData<MappingMethod> getRemappedMethod(MemberInfo param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public ObfuscationData<MappingMethod> getObfMethod(MappingMethod var1) {
      return this.getRemappedMethod(var1, var1.isConstructor());
   }

   public ObfuscationData<MappingMethod> getRemappedMethod(MappingMethod var1) {
      return this.getRemappedMethod(var1, true);
   }

   private ObfuscationData<MappingMethod> getRemappedMethod(MappingMethod param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public ObfuscationData<MappingMethod> remapDescriptor(ObfuscationData<MappingMethod> var1, MemberInfo var2) {
      Iterator var3 = this.environments.iterator();

      while(var3.hasNext()) {
         ObfuscationEnvironment var4 = (ObfuscationEnvironment)var3.next();
         MemberInfo var5 = var4.remapDescriptor(var2);

         try {
            if (var5 != null) {
               var1.put(var4.getType(), var5.asMethodMapping());
            }
         } catch (RuntimeException var6) {
            throw b(var6);
         }
      }

      return var1;
   }

   public ObfuscationData<MappingField> getObfFieldRecursive(MemberInfo var1) {
      return this.getObfEntryRecursive(var1);
   }

   public ObfuscationData<MappingField> getObfField(MemberInfo var1) {
      return this.getObfField(var1.asFieldMapping());
   }

   public ObfuscationData<MappingField> getObfField(MappingField param1) {
      // $FF: Couldn't be decompiled
   }

   public ObfuscationData<String> getObfClass(TypeHandle var1) {
      return this.getObfClass(var1.getName());
   }

   public ObfuscationData<String> getObfClass(String var1) {
      ObfuscationData var2 = new ObfuscationData(var1);
      Iterator var3 = this.environments.iterator();

      while(var3.hasNext()) {
         ObfuscationEnvironment var4 = (ObfuscationEnvironment)var3.next();
         String var5 = var4.getObfClass(var1);

         try {
            if (var5 != null) {
               var2.put(var4.getType(), var5);
            }
         } catch (RuntimeException var6) {
            throw b(var6);
         }
      }

      return var2;
   }

   private static <T> ObfuscationData<T> applyParents(ObfuscationData<String> var0, ObfuscationData<T> var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         ObfuscationType var3 = (ObfuscationType)var2.next();
         String var4 = (String)var0.get(var3);
         Object var5 = var1.get(var3);
         var1.put(var3, MemberInfo.fromMapping((IMapping)var5).move(var4).asMapping());
      }

      return var1;
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

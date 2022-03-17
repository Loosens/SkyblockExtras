package org.spongepowered.tools.obfuscation;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IReferenceManager;

public class ReferenceManager implements IReferenceManager {
   private final IMixinAnnotationProcessor ap;
   private final String outRefMapFileName;
   private final List<ObfuscationEnvironment> environments;
   private final ReferenceMapper refMapper = new ReferenceMapper();
   private boolean allowConflicts;

   public ReferenceManager(IMixinAnnotationProcessor var1, List<ObfuscationEnvironment> var2) {
      this.ap = var1;
      this.environments = var2;
      this.outRefMapFileName = this.ap.getOption("outRefMapFile");
   }

   public boolean getAllowConflicts() {
      return this.allowConflicts;
   }

   public void setAllowConflicts(boolean var1) {
      this.allowConflicts = var1;
   }

   public void write() {
      try {
         if (this.outRefMapFileName == null) {
            return;
         }
      } catch (Exception var14) {
         throw b(var14);
      }

      PrintWriter var1 = null;

      try {
         var1 = this.newWriter(this.outRefMapFileName, "refmap");
         this.refMapper.write(var1);
      } catch (IOException var12) {
         var12.printStackTrace();
      } finally {
         if (var1 != null) {
            try {
               var1.close();
            } catch (Exception var11) {
            }
         }

      }

   }

   private PrintWriter newWriter(String var1, String var2) throws IOException {
      if (var1.matches("^.*[\\\\/:].*$")) {
         File var4 = new File(var1);
         var4.getParentFile().mkdirs();
         this.ap.printMessage(Kind.NOTE, "Writing " + var2 + " to " + var4.getAbsolutePath());
         return new PrintWriter(var4);
      } else {
         FileObject var3 = this.ap.getProcessingEnvironment().getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", var1);
         this.ap.printMessage(Kind.NOTE, "Writing " + var2 + " to " + (new File(var3.toUri())).getAbsolutePath());
         return new PrintWriter(var3.openWriter());
      }
   }

   public ReferenceMapper getMapper() {
      return this.refMapper;
   }

   public void addMethodMapping(String var1, String var2, ObfuscationData<MappingMethod> var3) {
      Iterator var4 = this.environments.iterator();

      while(var4.hasNext()) {
         ObfuscationEnvironment var5 = (ObfuscationEnvironment)var4.next();
         MappingMethod var6 = (MappingMethod)var3.get(var5.getType());
         if (var6 != null) {
            MemberInfo var7 = new MemberInfo(var6);
            this.addMapping(var5.getType(), var1, var2, var7.toString());
         }
      }

   }

   public void addMethodMapping(String var1, String var2, MemberInfo var3, ObfuscationData<MappingMethod> var4) {
      Iterator var5 = this.environments.iterator();

      while(var5.hasNext()) {
         ObfuscationEnvironment var6 = (ObfuscationEnvironment)var5.next();
         MappingMethod var7 = (MappingMethod)var4.get(var6.getType());
         if (var7 != null) {
            MemberInfo var8 = var3.remapUsing(var7, true);
            this.addMapping(var6.getType(), var1, var2, var8.toString());
         }
      }

   }

   public void addFieldMapping(String var1, String var2, MemberInfo var3, ObfuscationData<MappingField> var4) {
      Iterator var5 = this.environments.iterator();

      while(var5.hasNext()) {
         ObfuscationEnvironment var6 = (ObfuscationEnvironment)var5.next();
         MappingField var7 = (MappingField)var4.get(var6.getType());
         if (var7 != null) {
            MemberInfo var8 = MemberInfo.fromMapping(var7.transform(var6.remapDescriptor(var3.desc)));
            this.addMapping(var6.getType(), var1, var2, var8.toString());
         }
      }

   }

   public void addClassMapping(String var1, String var2, ObfuscationData<String> var3) {
      Iterator var4 = this.environments.iterator();

      while(var4.hasNext()) {
         ObfuscationEnvironment var5 = (ObfuscationEnvironment)var4.next();
         String var6 = (String)var3.get(var5.getType());

         try {
            if (var6 != null) {
               this.addMapping(var5.getType(), var1, var2, var6);
            }
         } catch (ReferenceManager$ReferenceConflictException var7) {
            throw b(var7);
         }
      }

   }

   protected void addMapping(ObfuscationType param1, String param2, String param3, String param4) {
      // $FF: Couldn't be decompiled
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

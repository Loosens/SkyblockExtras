package org.spongepowered.tools.obfuscation.mapping.mcp;

import com.google.common.collect.BiMap;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.mcp.MappingFieldSrg;
import org.spongepowered.tools.obfuscation.mapping.common.MappingProvider;

public class MappingProviderSrg extends MappingProvider {
   public MappingProviderSrg(Messager var1, Filer var2) {
      super(var1, var2);
   }

   public void read(File var1) throws IOException {
      BiMap var2 = this.packageMap;
      BiMap var3 = this.classMap;
      BiMap var4 = this.fieldMap;
      BiMap var5 = this.methodMap;
      Files.readLines(var1, Charset.defaultCharset(), new MappingProviderSrg$1(this, var2, var3, var4, var5, var1));
   }

   public MappingField getFieldMapping(MappingField var1) {
      if (((MappingField)var1).getDesc() != null) {
         var1 = new MappingFieldSrg((MappingField)var1);
      }

      return (MappingField)this.fieldMap.get(var1);
   }
}

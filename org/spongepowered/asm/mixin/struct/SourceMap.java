package org.spongepowered.asm.mixin.struct;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.util.Bytecode;

public class SourceMap {
   private static final String DEFAULT_STRATUM = "Mixin";
   private static final String NEWLINE = "\n";
   private final String sourceFile;
   private final Map<String, SourceMap$Stratum> strata = new LinkedHashMap();
   private int nextLineOffset = 1;
   private String defaultStratum = "Mixin";

   public SourceMap(String var1) {
      this.sourceFile = var1;
   }

   public String getSourceFile() {
      return this.sourceFile;
   }

   public String getPseudoGeneratedSourceFile() {
      return this.sourceFile.replace(".java", "$mixin.java");
   }

   public SourceMap$File addFile(ClassNode var1) {
      return this.addFile(this.defaultStratum, var1);
   }

   public SourceMap$File addFile(String var1, ClassNode var2) {
      return this.addFile(var1, var2.sourceFile, var2.name + ".java", Bytecode.getMaxLineNumber(var2, 500, 50));
   }

   public SourceMap$File addFile(String var1, String var2, int var3) {
      return this.addFile(this.defaultStratum, var1, var2, var3);
   }

   public SourceMap$File addFile(String var1, String var2, String var3, int var4) {
      SourceMap$Stratum var5 = (SourceMap$Stratum)this.strata.get(var1);
      if (var5 == null) {
         var5 = new SourceMap$Stratum(var1);
         this.strata.put(var1, var5);
      }

      SourceMap$File var6 = var5.addFile(this.nextLineOffset, var4, var2, var3);
      this.nextLineOffset += var4;
      return var6;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      this.appendTo(var1);
      return var1.toString();
   }

   private void appendTo(StringBuilder var1) {
      var1.append("SMAP").append("\n");
      var1.append(this.getSourceFile()).append("\n");
      var1.append(this.defaultStratum).append("\n");
      Iterator var2 = this.strata.values().iterator();

      while(var2.hasNext()) {
         SourceMap$Stratum var3 = (SourceMap$Stratum)var2.next();
         var3.appendTo(var1);
      }

      var1.append("*E").append("\n");
   }
}

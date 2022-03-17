package org.spongepowered.asm.mixin.struct;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

class SourceMap$Stratum {
   private static final String STRATUM_MARK = "*S";
   private static final String FILE_MARK = "*F";
   private static final String LINES_MARK = "*L";
   public final String name;
   private final Map<String, SourceMap$File> files = new LinkedHashMap();

   public SourceMap$Stratum(String var1) {
      this.name = var1;
   }

   public SourceMap$File addFile(int var1, int var2, String var3, String var4) {
      SourceMap$File var5 = (SourceMap$File)this.files.get(var4);
      if (var5 == null) {
         var5 = new SourceMap$File(this.files.size() + 1, var1, var2, var3, var4);
         this.files.put(var4, var5);
      }

      return var5;
   }

   void appendTo(StringBuilder var1) {
      var1.append("*S").append(" ").append(this.name).append("\n");
      var1.append("*F").append("\n");
      Iterator var2 = this.files.values().iterator();

      SourceMap$File var3;
      while(var2.hasNext()) {
         var3 = (SourceMap$File)var2.next();
         var3.appendFile(var1);
      }

      var1.append("*L").append("\n");
      var2 = this.files.values().iterator();

      while(var2.hasNext()) {
         var3 = (SourceMap$File)var2.next();
         var3.appendLines(var1);
      }

   }
}

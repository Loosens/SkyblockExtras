package org.spongepowered.asm.mixin.transformer.ext.extensions;

import com.google.common.base.Charsets;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.MixinEnvironment$Option;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.ClassInfo$Method;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.PrettyPrinter;

public class ExtensionCheckInterfaces implements IExtension {
   private static final String AUDIT_DIR = "audit";
   private static final String IMPL_REPORT_FILENAME = "mixin_implementation_report";
   private static final String IMPL_REPORT_CSV_FILENAME = "mixin_implementation_report.csv";
   private static final String IMPL_REPORT_TXT_FILENAME = "mixin_implementation_report.txt";
   private static final Logger logger = LogManager.getLogger("mixin");
   private final File csv;
   private final File report;
   private final Multimap<ClassInfo, ClassInfo$Method> interfaceMethods = HashMultimap.create();
   private boolean strict;

   public ExtensionCheckInterfaces() {
      File var1 = new File(Constants.DEBUG_OUTPUT_DIR, "audit");
      var1.mkdirs();
      this.csv = new File(var1, "mixin_implementation_report.csv");
      this.report = new File(var1, "mixin_implementation_report.txt");

      try {
         Files.write("Class,Method,Signature,Interface\n", this.csv, Charsets.ISO_8859_1);
      } catch (IOException var4) {
      }

      try {
         String var2 = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
         Files.write("Mixin Implementation Report generated on " + var2 + "\n", this.report, Charsets.ISO_8859_1);
      } catch (IOException var3) {
      }

   }

   public boolean checkActive(MixinEnvironment var1) {
      this.strict = var1.getOption(MixinEnvironment$Option.CHECK_IMPLEMENTS_STRICT);
      return var1.getOption(MixinEnvironment$Option.CHECK_IMPLEMENTS);
   }

   public void preApply(ITargetClassContext var1) {
      ClassInfo var2 = var1.getClassInfo();
      Iterator var3 = var2.getInterfaceMethods(false).iterator();

      while(var3.hasNext()) {
         ClassInfo$Method var4 = (ClassInfo$Method)var3.next();
         this.interfaceMethods.put(var2, var4);
      }

   }

   public void postApply(ITargetClassContext param1) {
      // $FF: Couldn't be decompiled
   }

   public void export(MixinEnvironment var1, String var2, boolean var3, byte[] var4) {
   }

   private void appendToCSVReport(String var1, ClassInfo$Method var2, String var3) {
      try {
         Files.append(String.format("%s,%s,%s,%s\n", var1, var2.getName(), var2.getDesc(), var3), this.csv, Charsets.ISO_8859_1);
      } catch (IOException var5) {
      }

   }

   private void appendToTextReport(PrettyPrinter var1) {
      FileOutputStream var2 = null;

      try {
         var2 = new FileOutputStream(this.report, true);
         PrintStream var3 = new PrintStream(var2);
         var3.print("\n");
         var1.print(var3);
      } catch (Exception var7) {
      } finally {
         IOUtils.closeQuietly(var2);
      }

   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

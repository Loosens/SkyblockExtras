package org.spongepowered.asm.mixin.transformer.ext.extensions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.MixinEnvironment$Option;
import org.spongepowered.asm.mixin.transformer.ext.IDecompiler;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.util.Constants;

public class ExtensionClassExporter implements IExtension {
   private static final String DECOMPILER_CLASS = "org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler";
   private static final String EXPORT_CLASS_DIR = "class";
   private static final String EXPORT_JAVA_DIR = "java";
   private static final Logger logger = LogManager.getLogger("mixin");
   private final File classExportDir;
   private final IDecompiler decompiler;

   public ExtensionClassExporter(MixinEnvironment var1) {
      this.classExportDir = new File(Constants.DEBUG_OUTPUT_DIR, "class");
      this.decompiler = this.initDecompiler(var1, new File(Constants.DEBUG_OUTPUT_DIR, "java"));

      try {
         FileUtils.deleteDirectory(this.classExportDir);
      } catch (IOException var3) {
         logger.warn("Error cleaning class output directory: {}", new Object[]{var3.getMessage()});
      }

   }

   public boolean isDecompilerActive() {
      boolean var10000;
      try {
         if (this.decompiler != null) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   private IDecompiler initDecompiler(MixinEnvironment var1, File var2) {
      try {
         if (!var1.getOption(MixinEnvironment$Option.DEBUG_EXPORT_DECOMPILE)) {
            return null;
         }
      } catch (Throwable var12) {
         throw b(var12);
      }

      try {
         boolean var3 = var1.getOption(MixinEnvironment$Option.DEBUG_EXPORT_DECOMPILE_THREADED);

         Logger var10000;
         String var10001;
         Object[] var10002;
         Object[] var10003;
         byte var10004;
         String var10005;
         label50: {
            try {
               var10000 = logger;
               var10001 = "Attempting to load Fernflower decompiler{}";
               var10002 = new Object[1];
               var10003 = var10002;
               var10004 = 0;
               if (var3) {
                  var10005 = " (Threaded mode)";
                  break label50;
               }
            } catch (Throwable var10) {
               throw b(var10);
            }

            var10005 = "";
         }

         StringBuilder var13;
         label43: {
            try {
               var10003[var10004] = var10005;
               var10000.info(var10001, var10002);
               var13 = (new StringBuilder()).append("org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler");
               if (var3) {
                  var10001 = "Async";
                  break label43;
               }
            } catch (Throwable var9) {
               throw b(var9);
            }

            var10001 = "";
         }

         String var4 = var13.append(var10001).toString();
         Class var5 = Class.forName(var4);
         Constructor var6 = var5.getDeclaredConstructor(File.class);
         IDecompiler var7 = (IDecompiler)var6.newInstance(var2);

         label35: {
            try {
               var10000 = logger;
               var10001 = "Fernflower decompiler was successfully initialised, exported classes will be decompiled{}";
               var10002 = new Object[1];
               var10003 = var10002;
               var10004 = 0;
               if (var3) {
                  var10005 = " in a separate thread";
                  break label35;
               }
            } catch (Throwable var8) {
               throw b(var8);
            }

            var10005 = "";
         }

         var10003[var10004] = var10005;
         var10000.info(var10001, var10002);
         return var7;
      } catch (Throwable var11) {
         logger.info("Fernflower could not be loaded, exported classes will not be decompiled. {}: {}", new Object[]{var11.getClass().getSimpleName(), var11.getMessage()});
         return null;
      }
   }

   private String prepareFilter(String var1) {
      var1 = "^\\Q" + var1.replace("**", "\u0081").replace("*", "\u0082").replace("?", "\u0083") + "\\E$";
      return var1.replace("\u0081", "\\E.*\\Q").replace("\u0082", "\\E[^\\.]+\\Q").replace("\u0083", "\\E.\\Q").replace("\\Q\\E", "");
   }

   private boolean applyFilter(String var1, String var2) {
      return Pattern.compile(this.prepareFilter(var1), 2).matcher(var2).matches();
   }

   public boolean checkActive(MixinEnvironment var1) {
      return true;
   }

   public void preApply(ITargetClassContext var1) {
   }

   public void postApply(ITargetClassContext var1) {
   }

   public void export(MixinEnvironment param1, String param2, boolean param3, byte[] param4) {
      // $FF: Couldn't be decompiled
   }

   public File dumpClass(String var1, byte[] var2) {
      File var3 = new File(this.classExportDir, var1 + ".class");

      try {
         FileUtils.writeByteArrayToFile(var3, var2);
      } catch (IOException var5) {
      }

      return var3;
   }

   private static Throwable b(Throwable var0) {
      return var0;
   }
}

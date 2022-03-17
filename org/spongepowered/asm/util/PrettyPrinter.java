package org.spongepowered.asm.util;

import com.google.common.base.Strings;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrettyPrinter {
   private final PrettyPrinter$HorizontalRule horizontalRule;
   private final List<Object> lines;
   private PrettyPrinter$Table table;
   private boolean recalcWidth;
   protected int width;
   protected int wrapWidth;
   protected int kvKeyWidth;
   protected String kvFormat;

   public PrettyPrinter() {
      this(100);
   }

   public PrettyPrinter(int var1) {
      this.horizontalRule = new PrettyPrinter$HorizontalRule(this, new char[]{'*'});
      this.lines = new ArrayList();
      this.recalcWidth = false;
      this.width = 100;
      this.wrapWidth = 80;
      this.kvKeyWidth = 10;
      this.kvFormat = makeKvFormat(this.kvKeyWidth);
      this.width = var1;
   }

   public PrettyPrinter wrapTo(int var1) {
      this.wrapWidth = var1;
      return this;
   }

   public int wrapTo() {
      return this.wrapWidth;
   }

   public PrettyPrinter table() {
      this.table = new PrettyPrinter$Table();
      return this;
   }

   public PrettyPrinter table(String... var1) {
      this.table = new PrettyPrinter$Table();
      String[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String var5 = var2[var4];
         this.table.addColumn(var5);
      }

      return this;
   }

   public PrettyPrinter table(Object... param1) {
      // $FF: Couldn't be decompiled
   }

   public PrettyPrinter spacing(int var1) {
      try {
         if (this.table == null) {
            this.table = new PrettyPrinter$Table();
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      this.table.setColSpacing(var1);
      return this;
   }

   public PrettyPrinter th() {
      return this.th(false);
   }

   private PrettyPrinter th(boolean param1) {
      // $FF: Couldn't be decompiled
   }

   public PrettyPrinter tr(Object... var1) {
      this.th(true);
      this.addLine(this.table.addRow(var1));
      this.recalcWidth = true;
      return this;
   }

   public PrettyPrinter add() {
      this.addLine("");
      return this;
   }

   public PrettyPrinter add(String var1) {
      this.addLine(var1);
      this.width = Math.max(this.width, var1.length());
      return this;
   }

   public PrettyPrinter add(String var1, Object... var2) {
      String var3 = String.format(var1, var2);
      this.addLine(var3);
      this.width = Math.max(this.width, var3.length());
      return this;
   }

   public PrettyPrinter add(Object[] var1) {
      return this.add(var1, "%s");
   }

   public PrettyPrinter add(Object[] var1, String var2) {
      Object[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object var6 = var3[var5];
         this.add(var2, var6);
      }

      return this;
   }

   public PrettyPrinter addIndexed(Object[] var1) {
      int var2 = String.valueOf(var1.length - 1).length();
      String var3 = "[%" + var2 + "d] %s";
      int var4 = 0;

      try {
         while(var4 < var1.length) {
            this.add(var3, var4, var1[var4]);
            ++var4;
         }

         return this;
      } catch (RuntimeException var5) {
         throw b(var5);
      }
   }

   public PrettyPrinter addWithIndices(Collection<?> var1) {
      return this.addIndexed(var1.toArray());
   }

   public PrettyPrinter add(PrettyPrinter$IPrettyPrintable var1) {
      try {
         if (var1 != null) {
            var1.print(this);
         }

         return this;
      } catch (RuntimeException var2) {
         throw b(var2);
      }
   }

   public PrettyPrinter add(Throwable var1) {
      return this.add((Throwable)var1, 4);
   }

   public PrettyPrinter add(Throwable var1, int var2) {
      while(var1 != null) {
         this.add("%s: %s", var1.getClass().getName(), var1.getMessage());
         this.add(var1.getStackTrace(), var2);
         var1 = var1.getCause();
      }

      return this;
   }

   public PrettyPrinter add(StackTraceElement[] var1, int var2) {
      String var3 = Strings.repeat(" ", var2);
      StackTraceElement[] var4 = var1;
      int var5 = var1.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         StackTraceElement var7 = var4[var6];
         this.add("%s%s", var3, var7);
      }

      return this;
   }

   public PrettyPrinter add(Object var1) {
      return this.add((Object)var1, 0);
   }

   public PrettyPrinter add(Object var1, int var2) {
      String var3 = Strings.repeat(" ", var2);
      return this.append(var1, var2, var3);
   }

   private PrettyPrinter append(Object var1, int var2, String var3) {
      try {
         if (var1 instanceof String) {
            return this.add("%s%s", var3, var1);
         }
      } catch (RuntimeException var8) {
         throw b(var8);
      }

      if (var1 instanceof Iterable) {
         Iterator var4 = ((Iterable)var1).iterator();

         while(var4.hasNext()) {
            Object var5 = var4.next();
            this.append(var5, var2, var3);
         }

         return this;
      } else {
         try {
            if (var1 instanceof Map) {
               this.kvWidth(var2);
               return this.add((Map)var1);
            }
         } catch (RuntimeException var7) {
            throw b(var7);
         }

         try {
            if (var1 instanceof PrettyPrinter$IPrettyPrintable) {
               return this.add((PrettyPrinter$IPrettyPrintable)var1);
            }
         } catch (RuntimeException var10) {
            throw b(var10);
         }

         try {
            if (var1 instanceof Throwable) {
               return this.add((Throwable)var1, var2);
            }
         } catch (RuntimeException var6) {
            throw b(var6);
         }

         try {
            if (var1.getClass().isArray()) {
               return this.add((Object[])((Object[])var1), var2 + "%s");
            }
         } catch (RuntimeException var9) {
            throw b(var9);
         }

         return this.add("%s%s", var3, var1);
      }
   }

   public PrettyPrinter addWrapped(String var1, Object... var2) {
      return this.addWrapped(this.wrapWidth, var1, var2);
   }

   public PrettyPrinter addWrapped(int var1, String var2, Object... var3) {
      String var4 = "";
      String var5 = String.format(var2, var3).replace("\t", "    ");
      Matcher var6 = Pattern.compile("^(\\s+)(.*)$").matcher(var5);
      if (var6.matches()) {
         var4 = var6.group(1);
      }

      try {
         Iterator var7 = this.getWrapped(var1, var5, var4).iterator();

         while(var7.hasNext()) {
            String var8 = (String)var7.next();
            this.addLine(var8);
         }
      } catch (Exception var9) {
         this.add(var5);
      }

      return this;
   }

   private List<String> getWrapped(int var1, String var2, String var3) {
      ArrayList var4;
      int var5;
      for(var4 = new ArrayList(); var2.length() > var1; var2 = var3 + var2.substring(var5 + 1)) {
         var5 = var2.lastIndexOf(32, var1);
         if (var5 < 10) {
            var5 = var1;
         }

         String var6 = var2.substring(0, var5);
         var4.add(var6);
      }

      try {
         if (var2.length() > 0) {
            var4.add(var2);
         }

         return var4;
      } catch (RuntimeException var7) {
         throw b(var7);
      }
   }

   public PrettyPrinter kv(String var1, String var2, Object... var3) {
      return this.kv(var1, String.format(var2, var3));
   }

   public PrettyPrinter kv(String var1, Object var2) {
      this.addLine(new PrettyPrinter$KeyValue(this, var1, var2));
      return this.kvWidth(var1.length());
   }

   public PrettyPrinter kvWidth(int var1) {
      try {
         if (var1 > this.kvKeyWidth) {
            this.kvKeyWidth = var1;
            this.kvFormat = makeKvFormat(var1);
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      this.recalcWidth = true;
      return this;
   }

   public PrettyPrinter add(Map<?, ?> var1) {
      Iterator var2 = var1.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();

         String var10000;
         label23: {
            try {
               if (var3.getKey() == null) {
                  var10000 = "null";
                  break label23;
               }
            } catch (RuntimeException var5) {
               throw b(var5);
            }

            var10000 = var3.getKey().toString();
         }

         String var4 = var10000;
         this.kv(var4, var3.getValue());
      }

      return this;
   }

   public PrettyPrinter hr() {
      return this.hr('*');
   }

   public PrettyPrinter hr(char var1) {
      this.addLine(new PrettyPrinter$HorizontalRule(this, new char[]{var1}));
      return this;
   }

   public PrettyPrinter centre() {
      if (!this.lines.isEmpty()) {
         Object var1 = this.lines.get(this.lines.size() - 1);

         try {
            if (var1 instanceof String) {
               this.addLine(new PrettyPrinter$CentredText(this, this.lines.remove(this.lines.size() - 1)));
            }
         } catch (RuntimeException var2) {
            throw b(var2);
         }
      }

      return this;
   }

   private void addLine(Object var1) {
      try {
         if (var1 == null) {
            return;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      this.lines.add(var1);
      this.recalcWidth |= var1 instanceof PrettyPrinter$IVariableWidthEntry;
   }

   public PrettyPrinter trace() {
      return this.trace(getDefaultLoggerName());
   }

   public PrettyPrinter trace(Level var1) {
      return this.trace(getDefaultLoggerName(), var1);
   }

   public PrettyPrinter trace(String var1) {
      return this.trace(System.err, LogManager.getLogger(var1));
   }

   public PrettyPrinter trace(String var1, Level var2) {
      return this.trace(System.err, LogManager.getLogger(var1), var2);
   }

   public PrettyPrinter trace(Logger var1) {
      return this.trace(System.err, var1);
   }

   public PrettyPrinter trace(Logger var1, Level var2) {
      return this.trace(System.err, var1, var2);
   }

   public PrettyPrinter trace(PrintStream var1) {
      return this.trace(var1, getDefaultLoggerName());
   }

   public PrettyPrinter trace(PrintStream var1, Level var2) {
      return this.trace(var1, getDefaultLoggerName(), var2);
   }

   public PrettyPrinter trace(PrintStream var1, String var2) {
      return this.trace(var1, LogManager.getLogger(var2));
   }

   public PrettyPrinter trace(PrintStream var1, String var2, Level var3) {
      return this.trace(var1, LogManager.getLogger(var2), var3);
   }

   public PrettyPrinter trace(PrintStream var1, Logger var2) {
      return this.trace(var1, var2, Level.DEBUG);
   }

   public PrettyPrinter trace(PrintStream var1, Logger var2, Level var3) {
      this.log(var2, var3);
      this.print(var1);
      return this;
   }

   public PrettyPrinter print() {
      return this.print(System.err);
   }

   public PrettyPrinter print(PrintStream var1) {
      this.updateWidth();
      this.printSpecial(var1, this.horizontalRule);
      Iterator var2 = this.lines.iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();

         try {
            if (var3 instanceof PrettyPrinter$ISpecialEntry) {
               this.printSpecial(var1, (PrettyPrinter$ISpecialEntry)var3);
               continue;
            }
         } catch (RuntimeException var4) {
            throw b(var4);
         }

         this.printString(var1, var3.toString());
      }

      this.printSpecial(var1, this.horizontalRule);
      return this;
   }

   private void printSpecial(PrintStream var1, PrettyPrinter$ISpecialEntry var2) {
      var1.printf("/*%s*/\n", var2.toString());
   }

   private void printString(PrintStream var1, String var2) {
      try {
         if (var2 != null) {
            var1.printf("/* %-" + this.width + "s */\n", var2);
         }

      } catch (RuntimeException var3) {
         throw b(var3);
      }
   }

   public PrettyPrinter log(Logger var1) {
      return this.log(var1, Level.INFO);
   }

   public PrettyPrinter log(Logger var1, Level var2) {
      this.updateWidth();
      this.logSpecial(var1, var2, this.horizontalRule);
      Iterator var3 = this.lines.iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();

         try {
            if (var4 instanceof PrettyPrinter$ISpecialEntry) {
               this.logSpecial(var1, var2, (PrettyPrinter$ISpecialEntry)var4);
               continue;
            }
         } catch (RuntimeException var5) {
            throw b(var5);
         }

         this.logString(var1, var2, var4.toString());
      }

      this.logSpecial(var1, var2, this.horizontalRule);
      return this;
   }

   private void logSpecial(Logger var1, Level var2, PrettyPrinter$ISpecialEntry var3) {
      var1.log(var2, "/*{}*/", new Object[]{var3.toString()});
   }

   private void logString(Logger var1, Level var2, String var3) {
      try {
         if (var3 != null) {
            var1.log(var2, String.format("/* %-" + this.width + "s */", var3));
         }

      } catch (RuntimeException var4) {
         throw b(var4);
      }
   }

   private void updateWidth() {
      if (this.recalcWidth) {
         this.recalcWidth = false;
         Iterator var1 = this.lines.iterator();

         while(var1.hasNext()) {
            Object var2 = var1.next();

            try {
               if (var2 instanceof PrettyPrinter$IVariableWidthEntry) {
                  this.width = Math.min(4096, Math.max(this.width, ((PrettyPrinter$IVariableWidthEntry)var2).getWidth()));
               }
            } catch (RuntimeException var3) {
               throw b(var3);
            }
         }
      }

   }

   private static String makeKvFormat(int var0) {
      return String.format("%%%ds : %%s", var0);
   }

   private static String getDefaultLoggerName() {
      String var0 = (new Throwable()).getStackTrace()[2].getClassName();
      int var1 = var0.lastIndexOf(46);

      String var10000;
      try {
         if (var1 == -1) {
            var10000 = var0;
            return var10000;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      var10000 = var0.substring(var1 + 1);
      return var10000;
   }

   public static void dumpStack() {
      (new PrettyPrinter()).add((Throwable)(new Exception("Stack trace"))).print(System.err);
   }

   public static void print(Throwable var0) {
      (new PrettyPrinter()).add(var0).print(System.err);
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

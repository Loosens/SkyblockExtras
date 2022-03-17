package org.spongepowered.asm.util.perf;

import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import org.spongepowered.asm.util.PrettyPrinter;

public final class Profiler {
   public static final int ROOT = 1;
   public static final int FINE = 2;
   private final Map<String, Profiler$Section> sections = new TreeMap();
   private final List<String> phases = new ArrayList();
   private final Deque<Profiler$Section> stack = new LinkedList();
   private boolean active;

   public Profiler() {
      this.phases.add("Initial");
   }

   public void setActive(boolean param1) {
      // $FF: Couldn't be decompiled
   }

   public void reset() {
      Iterator var1 = this.sections.values().iterator();

      while(var1.hasNext()) {
         Profiler$Section var2 = (Profiler$Section)var1.next();
         var2.invalidate();
      }

      this.sections.clear();
      this.phases.clear();
      this.phases.add("Initial");
      this.stack.clear();
   }

   public Profiler$Section get(String param1) {
      // $FF: Couldn't be decompiled
   }

   private Profiler$Section getSubSection(String var1, String var2, Profiler$Section var3) {
      Object var4 = (Profiler$Section)this.sections.get(var1);
      if (var4 == null) {
         var4 = new Profiler$SubSection(this, var1, this.phases.size() - 1, var2, var3);
         this.sections.put(var1, var4);
      }

      return (Profiler$Section)var4;
   }

   boolean isHead(Profiler$Section var1) {
      boolean var10000;
      try {
         if (this.stack.peek() == var1) {
            var10000 = true;
            return var10000;
         }
      } catch (NoSuchElementException var2) {
         throw b(var2);
      }

      var10000 = false;
      return var10000;
   }

   public Profiler$Section begin(String... var1) {
      return this.begin(0, (String[])var1);
   }

   public Profiler$Section begin(int var1, String... var2) {
      return this.begin(var1, Joiner.on('.').join(var2));
   }

   public Profiler$Section begin(String var1) {
      return this.begin(0, (String)var1);
   }

   public Profiler$Section begin(int param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   void end(Profiler$Section param1) {
      // $FF: Couldn't be decompiled
   }

   public void mark(String var1) {
      long var2 = 0L;

      Iterator var4;
      Profiler$Section var5;
      for(var4 = this.sections.values().iterator(); var4.hasNext(); var2 += var5.getTime()) {
         var5 = (Profiler$Section)var4.next();
      }

      if (var2 == 0L) {
         int var6 = this.phases.size();
         this.phases.set(var6 - 1, var1);
      } else {
         this.phases.add(var1);
         var4 = this.sections.values().iterator();

         while(var4.hasNext()) {
            var5 = (Profiler$Section)var4.next();
            var5.mark();
         }

      }
   }

   public Collection<Profiler$Section> getSections() {
      return Collections.unmodifiableCollection(this.sections.values());
   }

   public PrettyPrinter printer(boolean param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   private void printSectionRow(PrettyPrinter param1, int param2, int[] param3, Profiler$Section param4, boolean param5) {
      // $FF: Couldn't be decompiled
   }

   private static NoSuchElementException b(NoSuchElementException var0) {
      return var0;
   }
}

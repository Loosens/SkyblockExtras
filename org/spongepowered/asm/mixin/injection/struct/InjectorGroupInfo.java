package org.spongepowered.asm.mixin.injection.struct;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.mixin.injection.throwables.InjectionValidationException;

public class InjectorGroupInfo {
   private final String name;
   private final List<InjectionInfo> members;
   private final boolean isDefault;
   private int minCallbackCount;
   private int maxCallbackCount;

   public InjectorGroupInfo(String var1) {
      this(var1, false);
   }

   InjectorGroupInfo(String var1, boolean var2) {
      this.members = new ArrayList();
      this.minCallbackCount = -1;
      this.maxCallbackCount = Integer.MAX_VALUE;
      this.name = var1;
      this.isDefault = var2;
   }

   public String toString() {
      return String.format("@Group(name=%s, min=%d, max=%d)", this.getName(), this.getMinRequired(), this.getMaxAllowed());
   }

   public boolean isDefault() {
      return this.isDefault;
   }

   public String getName() {
      return this.name;
   }

   public int getMinRequired() {
      return Math.max(this.minCallbackCount, 1);
   }

   public int getMaxAllowed() {
      return Math.min(this.maxCallbackCount, Integer.MAX_VALUE);
   }

   public Collection<InjectionInfo> getMembers() {
      return Collections.unmodifiableCollection(this.members);
   }

   public void setMinRequired(int param1) {
      // $FF: Couldn't be decompiled
   }

   public void setMaxAllowed(int param1) {
      // $FF: Couldn't be decompiled
   }

   public InjectorGroupInfo add(InjectionInfo var1) {
      this.members.add(var1);
      return this;
   }

   public InjectorGroupInfo validate() throws InjectionValidationException {
      try {
         if (this.members.size() == 0) {
            return this;
         }
      } catch (InjectionValidationException var6) {
         throw b(var6);
      }

      int var1 = 0;

      InjectionInfo var3;
      for(Iterator var2 = this.members.iterator(); var2.hasNext(); var1 += var3.getInjectedCallbackCount()) {
         var3 = (InjectionInfo)var2.next();
      }

      int var7 = this.getMinRequired();
      int var8 = this.getMaxAllowed();

      try {
         if (var1 < var7) {
            throw new InjectionValidationException(this, String.format("expected %d invocation(s) but only %d succeeded", var7, var1));
         }
      } catch (InjectionValidationException var5) {
         throw b(var5);
      }

      try {
         if (var1 > var8) {
            throw new InjectionValidationException(this, String.format("maximum of %d invocation(s) allowed but %d succeeded", var8, var1));
         } else {
            return this;
         }
      } catch (InjectionValidationException var4) {
         throw b(var4);
      }
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

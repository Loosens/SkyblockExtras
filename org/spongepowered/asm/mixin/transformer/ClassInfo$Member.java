package org.spongepowered.asm.mixin.transformer;

abstract class ClassInfo$Member {
   private final ClassInfo$Member$Type type;
   private final String memberName;
   private final String memberDesc;
   private final boolean isInjected;
   private final int modifiers;
   private String currentName;
   private String currentDesc;
   private boolean decoratedFinal;
   private boolean decoratedMutable;
   private boolean unique;

   protected ClassInfo$Member(ClassInfo$Member var1) {
      this(var1.type, var1.memberName, var1.memberDesc, var1.modifiers, var1.isInjected);
      this.currentName = var1.currentName;
      this.currentDesc = var1.currentDesc;
      this.unique = var1.unique;
   }

   protected ClassInfo$Member(ClassInfo$Member$Type var1, String var2, String var3, int var4) {
      this(var1, var2, var3, var4, false);
   }

   protected ClassInfo$Member(ClassInfo$Member$Type var1, String var2, String var3, int var4, boolean var5) {
      this.type = var1;
      this.memberName = var2;
      this.memberDesc = var3;
      this.isInjected = var5;
      this.currentName = var2;
      this.currentDesc = var3;
      this.modifiers = var4;
   }

   public String getOriginalName() {
      return this.memberName;
   }

   public String getName() {
      return this.currentName;
   }

   public String getOriginalDesc() {
      return this.memberDesc;
   }

   public String getDesc() {
      return this.currentDesc;
   }

   public boolean isInjected() {
      return this.isInjected;
   }

   public boolean isRenamed() {
      boolean var10000;
      try {
         if (!this.currentName.equals(this.memberName)) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public boolean isRemapped() {
      boolean var10000;
      try {
         if (!this.currentDesc.equals(this.memberDesc)) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public boolean isPrivate() {
      boolean var10000;
      try {
         if ((this.modifiers & 2) != 0) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public boolean isStatic() {
      boolean var10000;
      try {
         if ((this.modifiers & 8) != 0) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public boolean isAbstract() {
      boolean var10000;
      try {
         if ((this.modifiers & 1024) != 0) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public boolean isFinal() {
      boolean var10000;
      try {
         if ((this.modifiers & 16) != 0) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public boolean isSynthetic() {
      boolean var10000;
      try {
         if ((this.modifiers & 4096) != 0) {
            var10000 = true;
            return var10000;
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public boolean isUnique() {
      return this.unique;
   }

   public void setUnique(boolean var1) {
      this.unique = var1;
   }

   public boolean isDecoratedFinal() {
      return this.decoratedFinal;
   }

   public boolean isDecoratedMutable() {
      return this.decoratedMutable;
   }

   public void setDecoratedFinal(boolean var1, boolean var2) {
      this.decoratedFinal = var1;
      this.decoratedMutable = var2;
   }

   public boolean matchesFlags(int param1) {
      // $FF: Couldn't be decompiled
   }

   public abstract ClassInfo getOwner();

   public ClassInfo getImplementor() {
      return this.getOwner();
   }

   public int getAccess() {
      return this.modifiers;
   }

   public String renameTo(String var1) {
      this.currentName = var1;
      return var1;
   }

   public String remapTo(String var1) {
      this.currentDesc = var1;
      return var1;
   }

   public boolean equals(String param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public boolean equals(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public int hashCode() {
      return this.toString().hashCode();
   }

   public String toString() {
      return String.format(this.getDisplayFormat(), this.memberName, this.memberDesc);
   }

   protected String getDisplayFormat() {
      return "%s%s";
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

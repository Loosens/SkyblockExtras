package org.spongepowered.asm.util;

class ClassSignature$TypeVar implements Comparable<ClassSignature$TypeVar> {
   private final String originalName;
   private String currentName;

   ClassSignature$TypeVar(String var1) {
      this.currentName = this.originalName = var1;
   }

   public int compareTo(ClassSignature$TypeVar var1) {
      return this.currentName.compareTo(var1.currentName);
   }

   public String toString() {
      return this.currentName;
   }

   String getOriginalName() {
      return this.originalName;
   }

   void rename(String var1) {
      this.currentName = var1;
   }

   public boolean matches(String var1) {
      return this.originalName.equals(var1);
   }

   public boolean equals(Object var1) {
      return this.currentName.equals(var1);
   }

   public int hashCode() {
      return this.currentName.hashCode();
   }
}

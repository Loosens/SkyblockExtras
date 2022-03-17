package org.spongepowered.tools.obfuscation;

public class ReferenceManager$ReferenceConflictException extends RuntimeException {
   private static final long serialVersionUID = 1L;
   private final String oldReference;
   private final String newReference;

   public ReferenceManager$ReferenceConflictException(String var1, String var2) {
      this.oldReference = var1;
      this.newReference = var2;
   }

   public String getOld() {
      return this.oldReference;
   }

   public String getNew() {
      return this.newReference;
   }
}

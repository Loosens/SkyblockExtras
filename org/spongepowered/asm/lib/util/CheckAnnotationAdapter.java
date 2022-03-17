package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.AnnotationVisitor;

public class CheckAnnotationAdapter extends AnnotationVisitor {
   private final boolean named;
   private boolean end;

   public CheckAnnotationAdapter(AnnotationVisitor var1) {
      this(var1, true);
   }

   CheckAnnotationAdapter(AnnotationVisitor var1, boolean var2) {
      super(327680, var1);
      this.named = var2;
   }

   public void visit(String param1, Object param2) {
      // $FF: Couldn't be decompiled
   }

   public void visitEnum(String var1, String var2, String var3) {
      try {
         this.checkEnd();
         this.checkName(var1);
         CheckMethodAdapter.checkDesc(var2, false);
         if (var3 == null) {
            throw new IllegalArgumentException("Invalid enum value");
         }
      } catch (IllegalArgumentException var5) {
         throw c(var5);
      }

      try {
         if (this.av != null) {
            this.av.visitEnum(var1, var2, var3);
         }

      } catch (IllegalArgumentException var4) {
         throw c(var4);
      }
   }

   public AnnotationVisitor visitAnnotation(String var1, String var2) {
      CheckAnnotationAdapter var10000;
      CheckAnnotationAdapter var10001;
      AnnotationVisitor var10002;
      label16: {
         try {
            this.checkEnd();
            this.checkName(var1);
            CheckMethodAdapter.checkDesc(var2, false);
            var10000 = new CheckAnnotationAdapter;
            var10001 = var10000;
            if (this.av == null) {
               var10002 = null;
               break label16;
            }
         } catch (IllegalArgumentException var3) {
            throw c(var3);
         }

         var10002 = this.av.visitAnnotation(var1, var2);
      }

      var10001.<init>(var10002);
      return var10000;
   }

   public AnnotationVisitor visitArray(String var1) {
      CheckAnnotationAdapter var10000;
      CheckAnnotationAdapter var10001;
      AnnotationVisitor var10002;
      label16: {
         try {
            this.checkEnd();
            this.checkName(var1);
            var10000 = new CheckAnnotationAdapter;
            var10001 = var10000;
            if (this.av == null) {
               var10002 = null;
               break label16;
            }
         } catch (IllegalArgumentException var2) {
            throw c(var2);
         }

         var10002 = this.av.visitArray(var1);
      }

      var10001.<init>(var10002, false);
      return var10000;
   }

   public void visitEnd() {
      try {
         this.checkEnd();
         this.end = true;
         if (this.av != null) {
            this.av.visitEnd();
         }

      } catch (IllegalArgumentException var1) {
         throw c(var1);
      }
   }

   private void checkEnd() {
      try {
         if (this.end) {
            throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
         }
      } catch (IllegalArgumentException var1) {
         throw c(var1);
      }
   }

   private void checkName(String param1) {
      // $FF: Couldn't be decompiled
   }

   private static IllegalArgumentException c(IllegalArgumentException var0) {
      return var0;
   }
}

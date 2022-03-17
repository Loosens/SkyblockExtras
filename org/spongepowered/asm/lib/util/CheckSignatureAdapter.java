package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.signature.SignatureVisitor;

public class CheckSignatureAdapter extends SignatureVisitor {
   public static final int CLASS_SIGNATURE = 0;
   public static final int METHOD_SIGNATURE = 1;
   public static final int TYPE_SIGNATURE = 2;
   private static final int EMPTY = 1;
   private static final int FORMAL = 2;
   private static final int BOUND = 4;
   private static final int SUPER = 8;
   private static final int PARAM = 16;
   private static final int RETURN = 32;
   private static final int SIMPLE_TYPE = 64;
   private static final int CLASS_TYPE = 128;
   private static final int END = 256;
   private final int type;
   private int state;
   private boolean canBeVoid;
   private final SignatureVisitor sv;

   public CheckSignatureAdapter(int var1, SignatureVisitor var2) {
      this(327680, var1, var2);
   }

   protected CheckSignatureAdapter(int var1, int var2, SignatureVisitor var3) {
      super(var1);
      this.type = var2;
      this.state = 1;
      this.sv = var3;
   }

   public void visitFormalTypeParameter(String param1) {
      // $FF: Couldn't be decompiled
   }

   public SignatureVisitor visitClassBound() {
      try {
         if (this.state != 2) {
            throw new IllegalStateException();
         }
      } catch (IllegalStateException var2) {
         throw b(var2);
      }

      SignatureVisitor var10000;
      label22: {
         try {
            this.state = 4;
            if (this.sv == null) {
               var10000 = null;
               break label22;
            }
         } catch (IllegalStateException var3) {
            throw b(var3);
         }

         var10000 = this.sv.visitClassBound();
      }

      SignatureVisitor var1 = var10000;
      return new CheckSignatureAdapter(2, var1);
   }

   public SignatureVisitor visitInterfaceBound() {
      // $FF: Couldn't be decompiled
   }

   public SignatureVisitor visitSuperclass() {
      // $FF: Couldn't be decompiled
   }

   public SignatureVisitor visitInterface() {
      try {
         if (this.state != 8) {
            throw new IllegalStateException();
         }
      } catch (IllegalStateException var2) {
         throw b(var2);
      }

      SignatureVisitor var10000;
      label22: {
         try {
            if (this.sv == null) {
               var10000 = null;
               break label22;
            }
         } catch (IllegalStateException var3) {
            throw b(var3);
         }

         var10000 = this.sv.visitInterface();
      }

      SignatureVisitor var1 = var10000;
      return new CheckSignatureAdapter(2, var1);
   }

   public SignatureVisitor visitParameterType() {
      // $FF: Couldn't be decompiled
   }

   public SignatureVisitor visitReturnType() {
      // $FF: Couldn't be decompiled
   }

   public SignatureVisitor visitExceptionType() {
      try {
         if (this.state != 32) {
            throw new IllegalStateException();
         }
      } catch (IllegalStateException var2) {
         throw b(var2);
      }

      SignatureVisitor var10000;
      label22: {
         try {
            if (this.sv == null) {
               var10000 = null;
               break label22;
            }
         } catch (IllegalStateException var3) {
            throw b(var3);
         }

         var10000 = this.sv.visitExceptionType();
      }

      SignatureVisitor var1 = var10000;
      return new CheckSignatureAdapter(2, var1);
   }

   public void visitBaseType(char param1) {
      // $FF: Couldn't be decompiled
   }

   public void visitTypeVariable(String param1) {
      // $FF: Couldn't be decompiled
   }

   public SignatureVisitor visitArrayType() {
      // $FF: Couldn't be decompiled
   }

   public void visitClassType(String param1) {
      // $FF: Couldn't be decompiled
   }

   public void visitInnerClassType(String var1) {
      try {
         if (this.state != 128) {
            throw new IllegalStateException();
         }
      } catch (IllegalStateException var3) {
         throw b(var3);
      }

      try {
         CheckMethodAdapter.checkIdentifier(var1, "inner class name");
         if (this.sv != null) {
            this.sv.visitInnerClassType(var1);
         }

      } catch (IllegalStateException var2) {
         throw b(var2);
      }
   }

   public void visitTypeArgument() {
      try {
         if (this.state != 128) {
            throw new IllegalStateException();
         }
      } catch (IllegalStateException var2) {
         throw b(var2);
      }

      try {
         if (this.sv != null) {
            this.sv.visitTypeArgument();
         }

      } catch (IllegalStateException var1) {
         throw b(var1);
      }
   }

   public SignatureVisitor visitTypeArgument(char var1) {
      try {
         if (this.state != 128) {
            throw new IllegalStateException();
         }
      } catch (IllegalStateException var5) {
         throw b(var5);
      }

      try {
         if ("+-=".indexOf(var1) == -1) {
            throw new IllegalArgumentException();
         }
      } catch (IllegalStateException var3) {
         throw b(var3);
      }

      SignatureVisitor var10000;
      label26: {
         try {
            if (this.sv == null) {
               var10000 = null;
               break label26;
            }
         } catch (IllegalStateException var4) {
            throw b(var4);
         }

         var10000 = this.sv.visitTypeArgument(var1);
      }

      SignatureVisitor var2 = var10000;
      return new CheckSignatureAdapter(2, var2);
   }

   public void visitEnd() {
      try {
         if (this.state != 128) {
            throw new IllegalStateException();
         }
      } catch (IllegalStateException var2) {
         throw b(var2);
      }

      try {
         this.state = 256;
         if (this.sv != null) {
            this.sv.visitEnd();
         }

      } catch (IllegalStateException var1) {
         throw b(var1);
      }
   }

   private static IllegalStateException b(IllegalStateException var0) {
      return var0;
   }
}

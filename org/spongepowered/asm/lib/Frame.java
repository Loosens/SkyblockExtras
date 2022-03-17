package org.spongepowered.asm.lib;

class Frame {
   static final int DIM = -268435456;
   static final int ARRAY_OF = 268435456;
   static final int ELEMENT_OF = -268435456;
   static final int KIND = 251658240;
   static final int TOP_IF_LONG_OR_DOUBLE = 8388608;
   static final int VALUE = 8388607;
   static final int BASE_KIND = 267386880;
   static final int BASE_VALUE = 1048575;
   static final int BASE = 16777216;
   static final int OBJECT = 24117248;
   static final int UNINITIALIZED = 25165824;
   private static final int LOCAL = 33554432;
   private static final int STACK = 50331648;
   static final int TOP = 16777216;
   static final int BOOLEAN = 16777225;
   static final int BYTE = 16777226;
   static final int CHAR = 16777227;
   static final int SHORT = 16777228;
   static final int INTEGER = 16777217;
   static final int FLOAT = 16777218;
   static final int DOUBLE = 16777219;
   static final int LONG = 16777220;
   static final int NULL = 16777221;
   static final int UNINITIALIZED_THIS = 16777222;
   static final int[] SIZE;
   Label owner;
   int[] inputLocals;
   int[] inputStack;
   private int[] outputLocals;
   private int[] outputStack;
   int outputStackTop;
   private int initializationCount;
   private int[] initializations;

   final void set(ClassWriter param1, int param2, Object[] param3, int param4, Object[] param5) {
      // $FF: Couldn't be decompiled
   }

   private static int convert(ClassWriter param0, int param1, Object[] param2, int[] param3) {
      // $FF: Couldn't be decompiled
   }

   final void set(Frame var1) {
      this.inputLocals = var1.inputLocals;
      this.inputStack = var1.inputStack;
      this.outputLocals = var1.outputLocals;
      this.outputStack = var1.outputStack;
      this.outputStackTop = var1.outputStackTop;
      this.initializationCount = var1.initializationCount;
      this.initializations = var1.initializations;
   }

   private int get(int param1) {
      // $FF: Couldn't be decompiled
   }

   private void set(int var1, int var2) {
      try {
         if (this.outputLocals == null) {
            this.outputLocals = new int[10];
         }
      } catch (RuntimeException var5) {
         throw b(var5);
      }

      int var3 = this.outputLocals.length;
      if (var1 >= var3) {
         int[] var4 = new int[Math.max(var1 + 1, 2 * var3)];
         System.arraycopy(this.outputLocals, 0, var4, 0, var3);
         this.outputLocals = var4;
      }

      this.outputLocals[var1] = var2;
   }

   private void push(int var1) {
      try {
         if (this.outputStack == null) {
            this.outputStack = new int[10];
         }
      } catch (RuntimeException var5) {
         throw b(var5);
      }

      int var2 = this.outputStack.length;
      if (this.outputStackTop >= var2) {
         int[] var3 = new int[Math.max(this.outputStackTop + 1, 2 * var2)];
         System.arraycopy(this.outputStack, 0, var3, 0, var2);
         this.outputStack = var3;
      }

      this.outputStack[this.outputStackTop++] = var1;
      int var6 = this.owner.inputStackTop + this.outputStackTop;

      try {
         if (var6 > this.owner.outputStackMax) {
            this.owner.outputStackMax = var6;
         }

      } catch (RuntimeException var4) {
         throw b(var4);
      }
   }

   private void push(ClassWriter param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   private static int type(ClassWriter var0, String var1) {
      int var10000;
      label62: {
         try {
            if (var1.charAt(0) == '(') {
               var10000 = var1.indexOf(41) + 1;
               break label62;
            }
         } catch (RuntimeException var8) {
            throw b(var8);
         }

         var10000 = 0;
      }

      int var2 = var10000;

      String var3;
      label54: {
         try {
            switch(var1.charAt(var2)) {
            case 'B':
            case 'C':
            case 'I':
            case 'S':
            case 'Z':
               return 16777217;
            case 'D':
               return 16777219;
            case 'E':
            case 'G':
            case 'H':
            case 'K':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'T':
            case 'U':
            case 'W':
            case 'X':
            case 'Y':
            default:
               break label54;
            case 'F':
               return 16777218;
            case 'J':
               return 16777220;
            case 'L':
               break;
            case 'V':
               return 0;
            }
         } catch (RuntimeException var7) {
            throw b(var7);
         }

         var3 = var1.substring(var2 + 1, var1.length() - 1);
         return 24117248 | var0.addType(var3);
      }

      int var4 = var2 + 1;

      try {
         while(var1.charAt(var4) == '[') {
            ++var4;
         }
      } catch (RuntimeException var6) {
         throw b(var6);
      }

      int var5;
      switch(var1.charAt(var4)) {
      case 'B':
         var5 = 16777226;
         break;
      case 'C':
         var5 = 16777227;
         break;
      case 'D':
         var5 = 16777219;
         break;
      case 'E':
      case 'G':
      case 'H':
      case 'K':
      case 'L':
      case 'M':
      case 'N':
      case 'O':
      case 'P':
      case 'Q':
      case 'R':
      case 'T':
      case 'U':
      case 'V':
      case 'W':
      case 'X':
      case 'Y':
      default:
         var3 = var1.substring(var4 + 1, var1.length() - 1);
         var5 = 24117248 | var0.addType(var3);
         break;
      case 'F':
         var5 = 16777218;
         break;
      case 'I':
         var5 = 16777217;
         break;
      case 'J':
         var5 = 16777220;
         break;
      case 'S':
         var5 = 16777228;
         break;
      case 'Z':
         var5 = 16777225;
      }

      return var4 - var2 << 28 | var5;
   }

   private int pop() {
      try {
         if (this.outputStackTop > 0) {
            return this.outputStack[--this.outputStackTop];
         }
      } catch (RuntimeException var1) {
         throw b(var1);
      }

      return 50331648 | -(--this.owner.inputStackTop);
   }

   private void pop(int var1) {
      try {
         if (this.outputStackTop >= var1) {
            this.outputStackTop -= var1;
            return;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      Label var10000 = this.owner;
      var10000.inputStackTop -= var1 - this.outputStackTop;
      this.outputStackTop = 0;
   }

   private void pop(String param1) {
      // $FF: Couldn't be decompiled
   }

   private void init(int var1) {
      try {
         if (this.initializations == null) {
            this.initializations = new int[2];
         }
      } catch (RuntimeException var4) {
         throw b(var4);
      }

      int var2 = this.initializations.length;
      if (this.initializationCount >= var2) {
         int[] var3 = new int[Math.max(this.initializationCount + 1, 2 * var2)];
         System.arraycopy(this.initializations, 0, var3, 0, var2);
         this.initializations = var3;
      }

      this.initializations[this.initializationCount++] = var1;
   }

   private int init(ClassWriter var1, int var2) {
      int var3;
      if (var2 == 16777222) {
         var3 = 24117248 | var1.addType(var1.thisName);
      } else {
         if ((var2 & -1048576) != 25165824) {
            return var2;
         }

         String var4 = var1.typeTable[var2 & 1048575].strVal1;
         var3 = 24117248 | var1.addType(var4);
      }

      for(int var9 = 0; var9 < this.initializationCount; ++var9) {
         int var5 = this.initializations[var9];
         int var6 = var5 & -268435456;
         int var7 = var5 & 251658240;
         if (var7 == 33554432) {
            var5 = var6 + this.inputLocals[var5 & 8388607];
         } else if (var7 == 50331648) {
            var5 = var6 + this.inputStack[this.inputStack.length - (var5 & 8388607)];
         }

         try {
            if (var2 == var5) {
               return var3;
            }
         } catch (RuntimeException var8) {
            throw b(var8);
         }
      }

      return var2;
   }

   final void initInputFrame(ClassWriter param1, int param2, Type[] param3, int param4) {
      // $FF: Couldn't be decompiled
   }

   void execute(int param1, int param2, ClassWriter param3, Item param4) {
      // $FF: Couldn't be decompiled
   }

   final boolean merge(ClassWriter param1, Frame param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   private static boolean merge(ClassWriter param0, int param1, int[] param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   static {
      int[] var0 = new int[202];
      String var1 = "EFFFFFFFFGGFFFGGFFFEEFGFGFEEEEEEEEEEEEEEEEEEEEDEDEDDDDDCDCDEEEEEEEEEEEEEEEEEEEEBABABBBBDCFFFGGGEDCDCDCDCDCDCDCDCDCDCEEEEDDDDDDDCDCDCEFEFDDEEFFDEDEEEBDDBBDDDDDDCCCCCCCCEFEDDDCDCDEEEEEEEEEEFEEEEEEDDEEDDEE";
      int var2 = 0;

      try {
         while(var2 < var0.length) {
            var0[var2] = var1.charAt(var2) - 69;
            ++var2;
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      SIZE = var0;
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.tree.AbstractInsnNode;

public class Frame<V extends Value> {
   private V returnValue;
   private V[] values;
   private int locals;
   private int top;

   public Frame(int var1, int var2) {
      this.values = (Value[])(new Value[var1 + var2]);
      this.locals = var1;
   }

   public Frame(Frame<? extends V> var1) {
      this(var1.locals, var1.values.length - var1.locals);
      this.init(var1);
   }

   public Frame<V> init(Frame<? extends V> var1) {
      this.returnValue = var1.returnValue;
      System.arraycopy(var1.values, 0, this.values, 0, this.values.length);
      this.top = var1.top;
      return this;
   }

   public void setReturn(V var1) {
      this.returnValue = var1;
   }

   public int getLocals() {
      return this.locals;
   }

   public int getMaxStackSize() {
      return this.values.length - this.locals;
   }

   public V getLocal(int var1) throws IndexOutOfBoundsException {
      try {
         if (var1 >= this.locals) {
            throw new IndexOutOfBoundsException("Trying to access an inexistant local variable");
         }
      } catch (IndexOutOfBoundsException var2) {
         throw b(var2);
      }

      return this.values[var1];
   }

   public void setLocal(int var1, V var2) throws IndexOutOfBoundsException {
      try {
         if (var1 >= this.locals) {
            throw new IndexOutOfBoundsException("Trying to access an inexistant local variable " + var1);
         }
      } catch (IndexOutOfBoundsException var3) {
         throw b(var3);
      }

      this.values[var1] = var2;
   }

   public int getStackSize() {
      return this.top;
   }

   public V getStack(int var1) throws IndexOutOfBoundsException {
      return this.values[var1 + this.locals];
   }

   public void clearStack() {
      this.top = 0;
   }

   public V pop() throws IndexOutOfBoundsException {
      try {
         if (this.top == 0) {
            throw new IndexOutOfBoundsException("Cannot pop operand off an empty stack.");
         }
      } catch (IndexOutOfBoundsException var1) {
         throw b(var1);
      }

      return this.values[--this.top + this.locals];
   }

   public void push(V var1) throws IndexOutOfBoundsException {
      try {
         if (this.top + this.locals >= this.values.length) {
            throw new IndexOutOfBoundsException("Insufficient maximum stack size.");
         }
      } catch (IndexOutOfBoundsException var2) {
         throw b(var2);
      }

      this.values[this.top++ + this.locals] = var1;
   }

   public void execute(AbstractInsnNode param1, Interpreter<V> param2) throws AnalyzerException {
      // $FF: Couldn't be decompiled
   }

   public boolean merge(Frame<? extends V> var1, Interpreter<V> var2) throws AnalyzerException {
      try {
         if (this.top != var1.top) {
            throw new AnalyzerException((AbstractInsnNode)null, "Incompatible stack heights");
         }
      } catch (AnalyzerException var6) {
         throw b(var6);
      }

      boolean var3 = false;

      for(int var4 = 0; var4 < this.locals + this.top; ++var4) {
         Value var5 = var2.merge(this.values[var4], var1.values[var4]);
         if (!var5.equals(this.values[var4])) {
            this.values[var4] = var5;
            var3 = true;
         }
      }

      return var3;
   }

   public boolean merge(Frame<? extends V> param1, boolean[] param2) {
      // $FF: Couldn't be decompiled
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      int var2 = 0;

      try {
         while(var2 < this.getLocals()) {
            var1.append(this.getLocal(var2));
            ++var2;
         }
      } catch (IndexOutOfBoundsException var4) {
         throw b(var4);
      }

      var1.append(' ');
      var2 = 0;

      try {
         while(var2 < this.getStackSize()) {
            var1.append(this.getStack(var2).toString());
            ++var2;
         }
      } catch (IndexOutOfBoundsException var3) {
         throw b(var3);
      }

      return var1.toString();
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

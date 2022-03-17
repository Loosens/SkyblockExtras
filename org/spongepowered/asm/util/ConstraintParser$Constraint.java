package org.spongepowered.asm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.spongepowered.asm.util.throwables.ConstraintViolationException;
import org.spongepowered.asm.util.throwables.InvalidConstraintException;

public class ConstraintParser$Constraint {
   public static final ConstraintParser$Constraint NONE = new ConstraintParser$Constraint();
   private static final Pattern pattern = Pattern.compile("^([A-Z0-9\\-_\\.]+)\\((?:(<|<=|>|>=|=)?([0-9]+)(<|(-)([0-9]+)?|>|(\\+)([0-9]+)?)?)?\\)$");
   private final String expr;
   private String token;
   private String[] constraint;
   private int min = Integer.MIN_VALUE;
   private int max = Integer.MAX_VALUE;
   private ConstraintParser$Constraint next;

   ConstraintParser$Constraint(String var1) {
      this.expr = var1;
      Matcher var2 = pattern.matcher(var1);

      try {
         if (!var2.matches()) {
            throw new InvalidConstraintException("Constraint syntax was invalid parsing: " + this.expr);
         }
      } catch (InvalidConstraintException var3) {
         throw b(var3);
      }

      this.token = var2.group(1);
      this.constraint = new String[]{var2.group(2), var2.group(3), var2.group(4), var2.group(5), var2.group(6), var2.group(7), var2.group(8)};
      this.parse();
   }

   private ConstraintParser$Constraint() {
      this.expr = null;
      this.token = "*";
      this.constraint = new String[0];
   }

   private void parse() {
      // $FF: Couldn't be decompiled
   }

   private boolean has(int var1) {
      boolean var10000;
      try {
         if (this.constraint[var1] != null) {
            var10000 = true;
            return var10000;
         }
      } catch (InvalidConstraintException var2) {
         throw b(var2);
      }

      var10000 = false;
      return var10000;
   }

   private String elem(int var1) {
      return this.constraint[var1];
   }

   private int val(int var1) {
      int var10000;
      try {
         if (this.constraint[var1] != null) {
            var10000 = Integer.parseInt(this.constraint[var1]);
            return var10000;
         }
      } catch (InvalidConstraintException var2) {
         throw b(var2);
      }

      var10000 = 0;
      return var10000;
   }

   void append(ConstraintParser$Constraint var1) {
      try {
         if (this.next != null) {
            this.next.append(var1);
            return;
         }
      } catch (InvalidConstraintException var2) {
         throw b(var2);
      }

      this.next = var1;
   }

   public String getToken() {
      return this.token;
   }

   public int getMin() {
      return this.min;
   }

   public int getMax() {
      return this.max;
   }

   public void check(ITokenProvider var1) throws ConstraintViolationException {
      if (this != NONE) {
         Integer var2 = var1.getToken(this.token);

         try {
            if (var2 == null) {
               throw new ConstraintViolationException("The token '" + this.token + "' could not be resolved in " + var1, this);
            }
         } catch (ConstraintViolationException var5) {
            throw b(var5);
         }

         try {
            if (var2 < this.min) {
               throw new ConstraintViolationException("Token '" + this.token + "' has a value (" + var2 + ") which is less than the minimum value " + this.min + " in " + var1, this, var2);
            }
         } catch (ConstraintViolationException var6) {
            throw b(var6);
         }

         try {
            if (var2 > this.max) {
               throw new ConstraintViolationException("Token '" + this.token + "' has a value (" + var2 + ") which is greater than the maximum value " + this.max + " in " + var1, this, var2);
            }
         } catch (ConstraintViolationException var4) {
            throw b(var4);
         }
      }

      try {
         if (this.next != null) {
            this.next.check(var1);
         }

      } catch (ConstraintViolationException var3) {
         throw b(var3);
      }
   }

   public String getRangeHumanReadable() {
      // $FF: Couldn't be decompiled
   }

   public String toString() {
      return String.format("Constraint(%s [%d-%d])", this.token, this.min, this.max);
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

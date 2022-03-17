package org.spongepowered.asm.util;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.spongepowered.asm.lib.signature.SignatureReader;
import org.spongepowered.asm.lib.signature.SignatureVisitor;
import org.spongepowered.asm.lib.tree.ClassNode;

public class ClassSignature {
   protected static final String OBJECT = "java/lang/Object";
   private final Map<ClassSignature$TypeVar, ClassSignature$TokenHandle> types = new LinkedHashMap();
   private ClassSignature$Token superClass = new ClassSignature$Token("java/lang/Object");
   private final List<ClassSignature$Token> interfaces = new ArrayList();
   private final Deque<String> rawInterfaces = new LinkedList();

   ClassSignature() {
   }

   private ClassSignature read(String var1) {
      if (var1 != null) {
         try {
            (new SignatureReader(var1)).accept(new ClassSignature$SignatureParser(this));
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }

      return this;
   }

   protected ClassSignature$TypeVar getTypeVar(String var1) {
      Iterator var2 = this.types.keySet().iterator();

      while(var2.hasNext()) {
         ClassSignature$TypeVar var3 = (ClassSignature$TypeVar)var2.next();

         try {
            if (var3.matches(var1)) {
               return var3;
            }
         } catch (IllegalArgumentException var4) {
            throw b(var4);
         }
      }

      return null;
   }

   protected ClassSignature$TokenHandle getType(String var1) {
      Iterator var2 = this.types.keySet().iterator();

      while(var2.hasNext()) {
         ClassSignature$TypeVar var3 = (ClassSignature$TypeVar)var2.next();

         try {
            if (var3.matches(var1)) {
               return (ClassSignature$TokenHandle)this.types.get(var3);
            }
         } catch (IllegalArgumentException var4) {
            throw b(var4);
         }
      }

      ClassSignature$TokenHandle var5 = new ClassSignature$TokenHandle(this);
      this.types.put(new ClassSignature$TypeVar(var1), var5);
      return var5;
   }

   protected String getTypeVar(ClassSignature$TokenHandle param1) {
      // $FF: Couldn't be decompiled
   }

   protected void addTypeVar(ClassSignature$TypeVar var1, ClassSignature$TokenHandle var2) throws IllegalArgumentException {
      try {
         if (this.types.containsKey(var1)) {
            throw new IllegalArgumentException("TypeVar " + var1 + " is already present on " + this);
         }
      } catch (IllegalArgumentException var3) {
         throw b(var3);
      }

      this.types.put(var1, var2);
   }

   protected void setSuperClass(ClassSignature$Token var1) {
      this.superClass = var1;
   }

   public String getSuperClass() {
      return this.superClass.asType(true);
   }

   protected void addInterface(ClassSignature$Token param1) {
      // $FF: Couldn't be decompiled
   }

   public void addInterface(String var1) {
      this.rawInterfaces.add(var1);
   }

   protected void addRawInterface(String var1) {
      ClassSignature$Token var2 = new ClassSignature$Token(var1);
      String var3 = var2.asType(true);
      Iterator var4 = this.interfaces.iterator();

      while(var4.hasNext()) {
         ClassSignature$Token var5 = (ClassSignature$Token)var4.next();

         try {
            if (var5.asType(true).equals(var3)) {
               return;
            }
         } catch (IllegalArgumentException var6) {
            throw b(var6);
         }
      }

      this.interfaces.add(var2);
   }

   public void merge(ClassSignature var1) {
      try {
         HashSet var2 = new HashSet();
         Iterator var3 = this.types.keySet().iterator();

         while(true) {
            if (!var3.hasNext()) {
               var1.conform(var2);
               break;
            }

            ClassSignature$TypeVar var4 = (ClassSignature$TypeVar)var3.next();
            var2.add(var4.toString());
         }
      } catch (IllegalStateException var5) {
         var5.printStackTrace();
         return;
      }

      Iterator var6 = var1.types.entrySet().iterator();

      while(var6.hasNext()) {
         Entry var7 = (Entry)var6.next();
         this.addTypeVar((ClassSignature$TypeVar)var7.getKey(), (ClassSignature$TokenHandle)var7.getValue());
      }

      var6 = var1.interfaces.iterator();

      while(var6.hasNext()) {
         ClassSignature$Token var8 = (ClassSignature$Token)var6.next();
         this.addInterface(var8);
      }

   }

   private void conform(Set<String> var1) {
      Iterator var2 = this.types.keySet().iterator();

      while(var2.hasNext()) {
         ClassSignature$TypeVar var3 = (ClassSignature$TypeVar)var2.next();
         String var4 = this.findUniqueName(var3.getOriginalName(), var1);
         var3.rename(var4);
         var1.add(var4);
      }

   }

   private String findUniqueName(String var1, Set<String> var2) {
      try {
         if (!var2.contains(var1)) {
            return var1;
         }
      } catch (IllegalArgumentException var5) {
         throw b(var5);
      }

      String var3;
      if (var1.length() == 1) {
         var3 = this.findOffsetName(var1.charAt(0), var2);

         try {
            if (var3 != null) {
               return var3;
            }
         } catch (IllegalArgumentException var4) {
            throw b(var4);
         }
      }

      var3 = this.findOffsetName('T', var2, "", var1);

      try {
         if (var3 != null) {
            return var3;
         }
      } catch (IllegalArgumentException var9) {
         throw b(var9);
      }

      var3 = this.findOffsetName('T', var2, var1, "");

      try {
         if (var3 != null) {
            return var3;
         }
      } catch (IllegalArgumentException var8) {
         throw b(var8);
      }

      var3 = this.findOffsetName('T', var2, "T", var1);

      try {
         if (var3 != null) {
            return var3;
         }
      } catch (IllegalArgumentException var7) {
         throw b(var7);
      }

      var3 = this.findOffsetName('T', var2, "", var1 + "Type");

      try {
         if (var3 != null) {
            return var3;
         }
      } catch (IllegalArgumentException var6) {
         throw b(var6);
      }

      throw new IllegalStateException("Failed to conform type var: " + var1);
   }

   private String findOffsetName(char var1, Set<String> var2) {
      return this.findOffsetName(var1, var2, "", "");
   }

   private String findOffsetName(char var1, Set<String> var2, String var3, String var4) {
      String var5 = String.format("%s%s%s", var3, var1, var4);

      try {
         if (!var2.contains(var5)) {
            return var5;
         }
      } catch (IllegalArgumentException var7) {
         throw b(var7);
      }

      try {
         if (var1 <= '@' || var1 >= '[') {
            return null;
         }
      } catch (IllegalArgumentException var8) {
         throw b(var8);
      }

      for(int var6 = var1 - 64; var6 + 65 != var1; var6 %= 26) {
         var5 = String.format("%s%s%s", var3, (char)(var6 + 65), var4);

         try {
            if (!var2.contains(var5)) {
               return var5;
            }
         } catch (IllegalArgumentException var9) {
            throw b(var9);
         }

         ++var6;
      }

      return null;
   }

   public SignatureVisitor getRemapper() {
      return new ClassSignature$SignatureRemapper(this);
   }

   public String toString() {
      while(true) {
         try {
            if (this.rawInterfaces.size() > 0) {
               this.addRawInterface((String)this.rawInterfaces.remove());
               continue;
            }
         } catch (IllegalArgumentException var8) {
            throw b(var8);
         }

         StringBuilder var1 = new StringBuilder();
         if (this.types.size() > 0) {
            boolean var2 = false;
            StringBuilder var3 = new StringBuilder();
            Iterator var4 = this.types.entrySet().iterator();

            while(var4.hasNext()) {
               Entry var5 = (Entry)var4.next();
               String var6 = ((ClassSignature$TokenHandle)var5.getValue()).asBound();
               if (!var6.isEmpty()) {
                  var3.append(var5.getKey()).append(':').append(var6);
                  var2 = true;
               }
            }

            try {
               if (var2) {
                  var1.append('<').append(var3).append('>');
               }
            } catch (IllegalArgumentException var7) {
               throw b(var7);
            }
         }

         var1.append(this.superClass.asType());
         Iterator var9 = this.interfaces.iterator();

         while(var9.hasNext()) {
            ClassSignature$Token var10 = (ClassSignature$Token)var9.next();
            var1.append(var10.asType());
         }

         return var1.toString();
      }
   }

   public ClassSignature wake() {
      return this;
   }

   public static ClassSignature of(String var0) {
      return (new ClassSignature()).read(var0);
   }

   public static ClassSignature of(ClassNode var0) {
      try {
         if (var0.signature != null) {
            return of(var0.signature);
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      return generate(var0);
   }

   public static ClassSignature ofLazy(ClassNode var0) {
      try {
         if (var0.signature != null) {
            return new ClassSignature$Lazy(var0.signature);
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      return generate(var0);
   }

   private static ClassSignature generate(ClassNode var0) {
      ClassSignature var1 = new ClassSignature();

      ClassSignature var10000;
      ClassSignature$Token var10001;
      ClassSignature$Token var10002;
      String var10003;
      label26: {
         try {
            var10000 = var1;
            var10001 = new ClassSignature$Token;
            var10002 = var10001;
            if (var0.superName != null) {
               var10003 = var0.superName;
               break label26;
            }
         } catch (IllegalArgumentException var4) {
            throw b(var4);
         }

         var10003 = "java/lang/Object";
      }

      var10002.<init>(var10003);
      var10000.setSuperClass(var10001);
      Iterator var2 = var0.interfaces.iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         var1.addInterface(new ClassSignature$Token(var3));
      }

      return var1;
   }

   private static IllegalArgumentException b(IllegalArgumentException var0) {
      return var0;
   }
}

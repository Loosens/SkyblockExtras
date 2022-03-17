package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.util.Annotations;

public class LocalVariableDiscriminator {
   private final boolean argsOnly;
   private final int ordinal;
   private final int index;
   private final Set<String> names;
   private final boolean print;

   public LocalVariableDiscriminator(boolean var1, int var2, int var3, Set<String> var4, boolean var5) {
      this.argsOnly = var1;
      this.ordinal = var2;
      this.index = var3;
      this.names = Collections.unmodifiableSet(var4);
      this.print = var5;
   }

   public boolean isArgsOnly() {
      return this.argsOnly;
   }

   public int getOrdinal() {
      return this.ordinal;
   }

   public int getIndex() {
      return this.index;
   }

   public Set<String> getNames() {
      return this.names;
   }

   public boolean hasNames() {
      boolean var10000;
      try {
         if (!this.names.isEmpty()) {
            var10000 = true;
            return var10000;
         }
      } catch (InvalidImplicitDiscriminatorException var1) {
         throw b(var1);
      }

      var10000 = false;
      return var10000;
   }

   public boolean printLVT() {
      return this.print;
   }

   protected boolean isImplicit(LocalVariableDiscriminator$Context param1) {
      // $FF: Couldn't be decompiled
   }

   public int findLocal(Type var1, boolean var2, Target var3, AbstractInsnNode var4) {
      try {
         return this.findLocal(new LocalVariableDiscriminator$Context(var1, var2, var3, var4));
      } catch (InvalidImplicitDiscriminatorException var6) {
         return -2;
      }
   }

   public int findLocal(LocalVariableDiscriminator$Context var1) {
      try {
         if (this.isImplicit(var1)) {
            return this.findImplicitLocal(var1);
         }
      } catch (InvalidImplicitDiscriminatorException var2) {
         throw b(var2);
      }

      return this.findExplicitLocal(var1);
   }

   private int findImplicitLocal(LocalVariableDiscriminator$Context param1) {
      // $FF: Couldn't be decompiled
   }

   private int findExplicitLocal(LocalVariableDiscriminator$Context param1) {
      // $FF: Couldn't be decompiled
   }

   public static LocalVariableDiscriminator parse(AnnotationNode var0) {
      boolean var1 = (Boolean)Annotations.getValue(var0, "argsOnly", (Object)Boolean.FALSE);
      int var2 = (Integer)Annotations.getValue(var0, "ordinal", (int)-1);
      int var3 = (Integer)Annotations.getValue(var0, "index", (int)-1);
      boolean var4 = (Boolean)Annotations.getValue(var0, "print", (Object)Boolean.FALSE);
      HashSet var5 = new HashSet();
      List var6 = (List)Annotations.getValue(var0, "name", (Object)((List)null));

      try {
         if (var6 != null) {
            var5.addAll(var6);
         }
      } catch (InvalidImplicitDiscriminatorException var7) {
         throw b(var7);
      }

      return new LocalVariableDiscriminator(var1, var2, var3, var5, var4);
   }

   private static InvalidImplicitDiscriminatorException b(InvalidImplicitDiscriminatorException var0) {
      return var0;
   }
}

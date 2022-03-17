package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Objects;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.IMapping$Type;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.SignaturePrinter;

public final class MemberInfo {
   public final String owner;
   public final String name;
   public final String desc;
   public final boolean matchAll;
   private final boolean forceField;
   private final String unparsed;

   public MemberInfo(String var1, boolean var2) {
      this(var1, (String)null, (String)null, var2);
   }

   public MemberInfo(String var1, String var2, boolean var3) {
      this(var1, var2, (String)null, var3);
   }

   public MemberInfo(String var1, String var2, String var3) {
      this(var1, var2, var3, false);
   }

   public MemberInfo(String var1, String var2, String var3, boolean var4) {
      this(var1, var2, var3, var4, (String)null);
   }

   public MemberInfo(String var1, String var2, String var3, boolean var4, String var5) {
      if (var2 != null) {
         try {
            if (var2.contains(".")) {
               throw new IllegalArgumentException("Attempt to instance a MemberInfo with an invalid owner format");
            }
         } catch (IllegalArgumentException var6) {
            throw b(var6);
         }
      }

      this.owner = var2;
      this.name = var1;
      this.desc = var3;
      this.matchAll = var4;
      this.forceField = false;
      this.unparsed = var5;
   }

   public MemberInfo(AbstractInsnNode var1) {
      this.matchAll = false;
      this.forceField = false;
      this.unparsed = null;
      if (var1 instanceof MethodInsnNode) {
         MethodInsnNode var2 = (MethodInsnNode)var1;
         this.owner = var2.owner;
         this.name = var2.name;
         this.desc = var2.desc;
      } else {
         if (!(var1 instanceof FieldInsnNode)) {
            throw new IllegalArgumentException("insn must be an instance of MethodInsnNode or FieldInsnNode");
         }

         FieldInsnNode var3 = (FieldInsnNode)var1;
         this.owner = var3.owner;
         this.name = var3.name;
         this.desc = var3.desc;
      }

   }

   public MemberInfo(IMapping<?> var1) {
      this.owner = var1.getOwner();
      this.name = var1.getSimpleName();
      this.desc = var1.getDesc();
      this.matchAll = false;
      this.forceField = var1.getType() == IMapping$Type.FIELD;
      this.unparsed = null;
   }

   private MemberInfo(MemberInfo var1, MappingMethod var2, boolean var3) {
      this.owner = var3 ? var2.getOwner() : var1.owner;
      this.name = var2.getSimpleName();
      this.desc = var2.getDesc();
      this.matchAll = var1.matchAll;
      this.forceField = false;
      this.unparsed = null;
   }

   private MemberInfo(MemberInfo var1, String var2) {
      this.owner = var2;
      this.name = var1.name;
      this.desc = var1.desc;
      this.matchAll = var1.matchAll;
      this.forceField = var1.forceField;
      this.unparsed = null;
   }

   public String toString() {
      String var10000;
      label82: {
         try {
            if (this.owner != null) {
               var10000 = "L" + this.owner + ";";
               break label82;
            }
         } catch (IllegalArgumentException var11) {
            throw b(var11);
         }

         var10000 = "";
      }

      String var1 = var10000;

      label74: {
         try {
            if (this.name != null) {
               var10000 = this.name;
               break label74;
            }
         } catch (IllegalArgumentException var10) {
            throw b(var10);
         }

         var10000 = "";
      }

      String var2 = var10000;

      label66: {
         try {
            if (this.matchAll) {
               var10000 = "*";
               break label66;
            }
         } catch (IllegalArgumentException var9) {
            throw b(var9);
         }

         var10000 = "";
      }

      String var3 = var10000;

      label58: {
         try {
            if (this.desc != null) {
               var10000 = this.desc;
               break label58;
            }
         } catch (IllegalArgumentException var8) {
            throw b(var8);
         }

         var10000 = "";
      }

      String var4 = var10000;

      label85: {
         try {
            if (var4.startsWith("(")) {
               var10000 = "";
               break label85;
            }
         } catch (IllegalArgumentException var7) {
            throw b(var7);
         }

         try {
            if (this.desc != null) {
               var10000 = ":";
               break label85;
            }
         } catch (IllegalArgumentException var6) {
            throw b(var6);
         }

         var10000 = "";
      }

      String var5 = var10000;
      return var1 + var2 + var3 + var5 + var4;
   }

   /** @deprecated */
   @Deprecated
   public String toSrg() {
      try {
         if (!this.isFullyQualified()) {
            throw new MixinException("Cannot convert unqualified reference to SRG mapping");
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      try {
         if (this.desc.startsWith("(")) {
            return this.owner + "/" + this.name + " " + this.desc;
         }
      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }

      return this.owner + "/" + this.name;
   }

   public String toDescriptor() {
      try {
         if (this.desc == null) {
            return "";
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      return (new SignaturePrinter(this)).setFullyQualified(true).toDescriptor();
   }

   public String toCtorType() {
      // $FF: Couldn't be decompiled
   }

   public String toCtorDesc() {
      // $FF: Couldn't be decompiled
   }

   public String getReturnType() {
      // $FF: Couldn't be decompiled
   }

   public IMapping<?> asMapping() {
      Object var10000;
      try {
         if (this.isField()) {
            var10000 = this.asFieldMapping();
            return (IMapping)var10000;
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      var10000 = this.asMethodMapping();
      return (IMapping)var10000;
   }

   public MappingMethod asMethodMapping() {
      try {
         if (!this.isFullyQualified()) {
            throw new MixinException("Cannot convert unqualified reference " + this + " to MethodMapping");
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      try {
         if (this.isField()) {
            throw new MixinException("Cannot convert a non-method reference " + this + " to MethodMapping");
         }
      } catch (IllegalArgumentException var2) {
         throw b(var2);
      }

      return new MappingMethod(this.owner, this.name, this.desc);
   }

   public MappingField asFieldMapping() {
      try {
         if (!this.isField()) {
            throw new MixinException("Cannot convert non-field reference " + this + " to FieldMapping");
         }
      } catch (IllegalArgumentException var1) {
         throw b(var1);
      }

      return new MappingField(this.owner, this.name, this.desc);
   }

   public boolean isFullyQualified() {
      // $FF: Couldn't be decompiled
   }

   public boolean isField() {
      // $FF: Couldn't be decompiled
   }

   public boolean isConstructor() {
      return "<init>".equals(this.name);
   }

   public boolean isClassInitialiser() {
      return "<clinit>".equals(this.name);
   }

   public boolean isInitialiser() {
      // $FF: Couldn't be decompiled
   }

   public MemberInfo validate() throws InvalidMemberDescriptorException {
      // $FF: Couldn't be decompiled
   }

   public boolean matches(String var1, String var2, String var3) {
      return this.matches(var1, var2, var3, 0);
   }

   public boolean matches(String param1, String param2, String param3, int param4) {
      // $FF: Couldn't be decompiled
   }

   public boolean matches(String var1, String var2) {
      return this.matches(var1, var2, 0);
   }

   public boolean matches(String param1, String param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   public boolean equals(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{this.matchAll, this.owner, this.name, this.desc});
   }

   public MemberInfo move(String param1) {
      // $FF: Couldn't be decompiled
   }

   public MemberInfo transform(String param1) {
      // $FF: Couldn't be decompiled
   }

   public MemberInfo remapUsing(MappingMethod var1, boolean var2) {
      return new MemberInfo(this, var1, var2);
   }

   public static MemberInfo parseAndValidate(String var0) throws InvalidMemberDescriptorException {
      return parse(var0, (IReferenceMapper)null, (String)null).validate();
   }

   public static MemberInfo parseAndValidate(String var0, IMixinContext var1) throws InvalidMemberDescriptorException {
      return parse(var0, var1.getReferenceMapper(), var1.getClassRef()).validate();
   }

   public static MemberInfo parse(String var0) {
      return parse(var0, (IReferenceMapper)null, (String)null);
   }

   public static MemberInfo parse(String var0, IMixinContext var1) {
      return parse(var0, var1.getReferenceMapper(), var1.getClassRef());
   }

   private static MemberInfo parse(String param0, IReferenceMapper param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public static MemberInfo fromMapping(IMapping<?> var0) {
      return new MemberInfo(var0);
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.TypePath;

public class FieldNode extends FieldVisitor {
   public int access;
   public String name;
   public String desc;
   public String signature;
   public Object value;
   public List<AnnotationNode> visibleAnnotations;
   public List<AnnotationNode> invisibleAnnotations;
   public List<TypeAnnotationNode> visibleTypeAnnotations;
   public List<TypeAnnotationNode> invisibleTypeAnnotations;
   public List<Attribute> attrs;

   public FieldNode(int var1, String var2, String var3, String var4, Object var5) {
      this(327680, var1, var2, var3, var4, var5);
      if (this.getClass() != FieldNode.class) {
         throw new IllegalStateException();
      }
   }

   public FieldNode(int var1, int var2, String var3, String var4, String var5, Object var6) {
      super(var1);
      this.access = var2;
      this.name = var3;
      this.desc = var4;
      this.signature = var5;
      this.value = var6;
   }

   public AnnotationVisitor visitAnnotation(String param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public AnnotationVisitor visitTypeAnnotation(int param1, TypePath param2, String param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   public void visitAttribute(Attribute var1) {
      try {
         if (this.attrs == null) {
            this.attrs = new ArrayList(1);
         }
      } catch (IllegalStateException var2) {
         throw b(var2);
      }

      this.attrs.add(var1);
   }

   public void visitEnd() {
   }

   public void check(int param1) {
      // $FF: Couldn't be decompiled
   }

   public void accept(ClassVisitor var1) {
      FieldVisitor var2 = var1.visitField(this.access, this.name, this.desc, this.signature, this.value);

      try {
         if (var2 == null) {
            return;
         }
      } catch (IllegalStateException var6) {
         throw b(var6);
      }

      int var10000;
      label120: {
         try {
            if (this.visibleAnnotations == null) {
               var10000 = 0;
               break label120;
            }
         } catch (IllegalStateException var12) {
            throw b(var12);
         }

         var10000 = this.visibleAnnotations.size();
      }

      int var3 = var10000;

      int var4;
      AnnotationNode var5;
      for(var4 = 0; var4 < var3; ++var4) {
         var5 = (AnnotationNode)this.visibleAnnotations.get(var4);
         var5.accept(var2.visitAnnotation(var5.desc, true));
      }

      label107: {
         try {
            if (this.invisibleAnnotations == null) {
               var10000 = 0;
               break label107;
            }
         } catch (IllegalStateException var11) {
            throw b(var11);
         }

         var10000 = this.invisibleAnnotations.size();
      }

      var3 = var10000;

      for(var4 = 0; var4 < var3; ++var4) {
         var5 = (AnnotationNode)this.invisibleAnnotations.get(var4);
         var5.accept(var2.visitAnnotation(var5.desc, false));
      }

      label94: {
         try {
            if (this.visibleTypeAnnotations == null) {
               var10000 = 0;
               break label94;
            }
         } catch (IllegalStateException var10) {
            throw b(var10);
         }

         var10000 = this.visibleTypeAnnotations.size();
      }

      var3 = var10000;

      TypeAnnotationNode var13;
      for(var4 = 0; var4 < var3; ++var4) {
         var13 = (TypeAnnotationNode)this.visibleTypeAnnotations.get(var4);
         var13.accept(var2.visitTypeAnnotation(var13.typeRef, var13.typePath, var13.desc, true));
      }

      label81: {
         try {
            if (this.invisibleTypeAnnotations == null) {
               var10000 = 0;
               break label81;
            }
         } catch (IllegalStateException var9) {
            throw b(var9);
         }

         var10000 = this.invisibleTypeAnnotations.size();
      }

      var3 = var10000;

      for(var4 = 0; var4 < var3; ++var4) {
         var13 = (TypeAnnotationNode)this.invisibleTypeAnnotations.get(var4);
         var13.accept(var2.visitTypeAnnotation(var13.typeRef, var13.typePath, var13.desc, false));
      }

      label68: {
         try {
            if (this.attrs == null) {
               var10000 = 0;
               break label68;
            }
         } catch (IllegalStateException var8) {
            throw b(var8);
         }

         var10000 = this.attrs.size();
      }

      var3 = var10000;
      var4 = 0;

      try {
         while(var4 < var3) {
            var2.visitAttribute((Attribute)this.attrs.get(var4));
            ++var4;
         }
      } catch (IllegalStateException var7) {
         throw b(var7);
      }

      var2.visitEnd();
   }

   private static IllegalStateException b(IllegalStateException var0) {
      return var0;
   }
}

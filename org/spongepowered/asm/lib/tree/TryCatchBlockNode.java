package org.spongepowered.asm.lib.tree;

import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;

public class TryCatchBlockNode {
   public LabelNode start;
   public LabelNode end;
   public LabelNode handler;
   public String type;
   public List<TypeAnnotationNode> visibleTypeAnnotations;
   public List<TypeAnnotationNode> invisibleTypeAnnotations;

   public TryCatchBlockNode(LabelNode var1, LabelNode var2, LabelNode var3, String var4) {
      this.start = var1;
      this.end = var2;
      this.handler = var3;
      this.type = var4;
   }

   public void updateIndex(int var1) {
      int var2 = 1107296256 | var1 << 8;
      Iterator var3;
      TypeAnnotationNode var4;
      if (this.visibleTypeAnnotations != null) {
         for(var3 = this.visibleTypeAnnotations.iterator(); var3.hasNext(); var4.typeRef = var2) {
            var4 = (TypeAnnotationNode)var3.next();
         }
      }

      if (this.invisibleTypeAnnotations != null) {
         for(var3 = this.invisibleTypeAnnotations.iterator(); var3.hasNext(); var4.typeRef = var2) {
            var4 = (TypeAnnotationNode)var3.next();
         }
      }

   }

   public void accept(MethodVisitor var1) {
      MethodVisitor var10000;
      Label var10001;
      Label var10002;
      Label var10003;
      label58: {
         try {
            var10000 = var1;
            var10001 = this.start.getLabel();
            var10002 = this.end.getLabel();
            if (this.handler == null) {
               var10003 = null;
               break label58;
            }
         } catch (RuntimeException var7) {
            throw b(var7);
         }

         var10003 = this.handler.getLabel();
      }

      int var8;
      label51: {
         try {
            var10000.visitTryCatchBlock(var10001, var10002, var10003, this.type);
            if (this.visibleTypeAnnotations == null) {
               var8 = 0;
               break label51;
            }
         } catch (RuntimeException var6) {
            throw b(var6);
         }

         var8 = this.visibleTypeAnnotations.size();
      }

      int var2 = var8;

      int var3;
      TypeAnnotationNode var4;
      for(var3 = 0; var3 < var2; ++var3) {
         var4 = (TypeAnnotationNode)this.visibleTypeAnnotations.get(var3);
         var4.accept(var1.visitTryCatchAnnotation(var4.typeRef, var4.typePath, var4.desc, true));
      }

      label38: {
         try {
            if (this.invisibleTypeAnnotations == null) {
               var8 = 0;
               break label38;
            }
         } catch (RuntimeException var5) {
            throw b(var5);
         }

         var8 = this.invisibleTypeAnnotations.size();
      }

      var2 = var8;

      for(var3 = 0; var3 < var2; ++var3) {
         var4 = (TypeAnnotationNode)this.invisibleTypeAnnotations.get(var3);
         var4.accept(var1.visitTryCatchAnnotation(var4.typeRef, var4.typePath, var4.desc, false));
      }

   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

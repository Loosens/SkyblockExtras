package org.spongepowered.asm.lib.tree;

import java.util.List;
import org.spongepowered.asm.lib.AnnotationVisitor;

public class AnnotationNode extends AnnotationVisitor {
   public String desc;
   public List<Object> values;

   public AnnotationNode(String var1) {
      this(327680, var1);
      if (this.getClass() != AnnotationNode.class) {
         throw new IllegalStateException();
      }
   }

   public AnnotationNode(int var1, String var2) {
      super(var1);
      this.desc = var2;
   }

   AnnotationNode(List<Object> var1) {
      super(327680);
      this.values = var1;
   }

   public void visit(String param1, Object param2) {
      // $FF: Couldn't be decompiled
   }

   public void visitEnum(String param1, String param2, String param3) {
      // $FF: Couldn't be decompiled
   }

   public AnnotationVisitor visitAnnotation(String param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public AnnotationVisitor visitArray(String param1) {
      // $FF: Couldn't be decompiled
   }

   public void visitEnd() {
   }

   public void check(int var1) {
   }

   public void accept(AnnotationVisitor var1) {
      label29: {
         try {
            if (var1 == null) {
               return;
            }

            if (this.values == null) {
               break label29;
            }
         } catch (IllegalStateException var5) {
            throw b(var5);
         }

         for(int var2 = 0; var2 < this.values.size(); var2 += 2) {
            String var3 = (String)this.values.get(var2);
            Object var4 = this.values.get(var2 + 1);
            accept(var1, var3, var4);
         }
      }

      var1.visitEnd();
   }

   static void accept(AnnotationVisitor var0, String var1, Object var2) {
      label50: {
         try {
            if (var0 == null) {
               return;
            }

            if (!(var2 instanceof String[])) {
               break label50;
            }
         } catch (IllegalStateException var7) {
            throw b(var7);
         }

         String[] var3 = (String[])((String[])var2);
         var0.visitEnum(var1, var3[0], var3[1]);
         return;
      }

      if (var2 instanceof AnnotationNode) {
         AnnotationNode var8 = (AnnotationNode)var2;
         var8.accept(var0.visitAnnotation(var1, var8.desc));
      } else if (var2 instanceof List) {
         AnnotationVisitor var9 = var0.visitArray(var1);
         if (var9 != null) {
            List var4 = (List)var2;
            int var5 = 0;

            try {
               while(var5 < var4.size()) {
                  accept(var9, (String)null, var4.get(var5));
                  ++var5;
               }
            } catch (IllegalStateException var6) {
               throw b(var6);
            }

            var9.visitEnd();
         }
      } else {
         var0.visit(var1, var2);
      }

   }

   private static IllegalStateException b(IllegalStateException var0) {
      return var0;
   }
}

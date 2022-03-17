package org.spongepowered.asm.mixin.struct;

import java.util.Iterator;
import java.util.ListIterator;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.LineNumberNode;
import org.spongepowered.asm.lib.tree.MethodNode;

public class SourceMap$File {
   public final int id;
   public final int lineOffset;
   public final int size;
   public final String sourceFileName;
   public final String sourceFilePath;

   public SourceMap$File(int var1, int var2, int var3, String var4) {
      this(var1, var2, var3, var4, (String)null);
   }

   public SourceMap$File(int var1, int var2, int var3, String var4, String var5) {
      this.id = var1;
      this.lineOffset = var2;
      this.size = var3;
      this.sourceFileName = var4;
      this.sourceFilePath = var5;
   }

   public void applyOffset(ClassNode var1) {
      Iterator var2 = var1.methods.iterator();

      while(var2.hasNext()) {
         MethodNode var3 = (MethodNode)var2.next();
         this.applyOffset(var3);
      }

   }

   public void applyOffset(MethodNode var1) {
      ListIterator var2 = var1.instructions.iterator();

      while(var2.hasNext()) {
         AbstractInsnNode var3 = (AbstractInsnNode)var2.next();

         try {
            if (var3 instanceof LineNumberNode) {
               ((LineNumberNode)var3).line += this.lineOffset - 1;
            }
         } catch (RuntimeException var4) {
            throw b(var4);
         }
      }

   }

   void appendFile(StringBuilder var1) {
      try {
         if (this.sourceFilePath != null) {
            var1.append("+ ").append(this.id).append(" ").append(this.sourceFileName).append("\n");
            var1.append(this.sourceFilePath).append("\n");
            return;
         }
      } catch (RuntimeException var2) {
         throw b(var2);
      }

      var1.append(this.id).append(" ").append(this.sourceFileName).append("\n");
   }

   public void appendLines(StringBuilder var1) {
      var1.append("1#").append(this.id).append(",").append(this.size).append(":").append(this.lineOffset).append("\n");
   }

   private static RuntimeException b(RuntimeException var0) {
      return var0;
   }
}

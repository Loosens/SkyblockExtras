package org.spongepowered.asm.mixin.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FrameNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.util.Annotations;

public class ClassInfo$Method extends ClassInfo$Member {
   private final List<ClassInfo$FrameData> frames;
   private boolean isAccessor;
   final ClassInfo this$0;

   public ClassInfo$Method(ClassInfo var1, ClassInfo$Member var2) {
      super(var2);
      this.this$0 = var1;
      this.frames = var2 instanceof ClassInfo$Method ? ((ClassInfo$Method)var2).frames : null;
   }

   public ClassInfo$Method(ClassInfo var1, MethodNode var2) {
      this(var1, var2, false);
      ClassInfo$Method var10000 = this;
      boolean var10001 = Annotations.getVisible(var2, Unique.class) != null;

      label20: {
         try {
            var10000.setUnique(var10001);
            var10000 = this;
            if (Annotations.getSingleVisible(var2, Accessor.class, Invoker.class) != null) {
               var10001 = true;
               break label20;
            }
         } catch (RuntimeException var3) {
            throw c(var3);
         }

         var10001 = false;
      }

      var10000.isAccessor = var10001;
   }

   public ClassInfo$Method(ClassInfo var1, MethodNode var2, boolean var3) {
      super(ClassInfo$Member$Type.METHOD, var2.name, var2.desc, var2.access, var3);
      this.this$0 = var1;
      this.frames = this.gatherFrames(var2);
      ClassInfo$Method var10000 = this;
      boolean var10001 = Annotations.getVisible(var2, Unique.class) != null;

      label20: {
         try {
            var10000.setUnique(var10001);
            var10000 = this;
            if (Annotations.getSingleVisible(var2, Accessor.class, Invoker.class) != null) {
               var10001 = true;
               break label20;
            }
         } catch (RuntimeException var4) {
            throw c(var4);
         }

         var10001 = false;
      }

      var10000.isAccessor = var10001;
   }

   public ClassInfo$Method(ClassInfo var1, String var2, String var3) {
      super(ClassInfo$Member$Type.METHOD, var2, var3, 1, false);
      this.this$0 = var1;
      this.frames = null;
   }

   public ClassInfo$Method(ClassInfo var1, String var2, String var3, int var4) {
      super(ClassInfo$Member$Type.METHOD, var2, var3, var4, false);
      this.this$0 = var1;
      this.frames = null;
   }

   public ClassInfo$Method(ClassInfo var1, String var2, String var3, int var4, boolean var5) {
      super(ClassInfo$Member$Type.METHOD, var2, var3, var4, var5);
      this.this$0 = var1;
      this.frames = null;
   }

   private List<ClassInfo$FrameData> gatherFrames(MethodNode var1) {
      ArrayList var2 = new ArrayList();
      ListIterator var3 = var1.instructions.iterator();

      while(var3.hasNext()) {
         AbstractInsnNode var4 = (AbstractInsnNode)var3.next();

         try {
            if (var4 instanceof FrameNode) {
               var2.add(new ClassInfo$FrameData(var1.instructions.indexOf(var4), (FrameNode)var4));
            }
         } catch (RuntimeException var5) {
            throw c(var5);
         }
      }

      return var2;
   }

   public List<ClassInfo$FrameData> getFrames() {
      return this.frames;
   }

   public ClassInfo getOwner() {
      return this.this$0;
   }

   public boolean isAccessor() {
      return this.isAccessor;
   }

   public boolean equals(Object var1) {
      try {
         if (!(var1 instanceof ClassInfo$Method)) {
            return false;
         }
      } catch (RuntimeException var2) {
         throw c(var2);
      }

      return super.equals(var1);
   }

   private static RuntimeException c(RuntimeException var0) {
      return var0;
   }
}

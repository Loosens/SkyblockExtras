package org.spongepowered.asm.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.util.throwables.LVTGeneratorException;

public final class Locals {
   private static final Map<String, List<LocalVariableNode>> calculatedLocalVariables = new HashMap();

   private Locals() {
   }

   public static void loadLocals(Type[] param0, InsnList param1, int param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   public static LocalVariableNode[] getLocalsAt(ClassNode param0, MethodNode param1, AbstractInsnNode param2) {
      // $FF: Couldn't be decompiled
   }

   public static LocalVariableNode getLocalVariableAt(ClassNode var0, MethodNode var1, AbstractInsnNode var2, int var3) {
      return getLocalVariableAt(var0, var1, var1.instructions.indexOf(var2), var3);
   }

   private static LocalVariableNode getLocalVariableAt(ClassNode var0, MethodNode var1, int var2, int var3) {
      LocalVariableNode var4 = null;
      LocalVariableNode var5 = null;
      Iterator var6 = getLocalVariableTable(var0, var1).iterator();

      LocalVariableNode var7;
      while(var6.hasNext()) {
         var7 = (LocalVariableNode)var6.next();

         try {
            if (var7.index != var3) {
               continue;
            }
         } catch (LVTGeneratorException var11) {
            throw b(var11);
         }

         if (isOpcodeInRange(var1.instructions, var7, var2)) {
            var4 = var7;
         } else if (var4 == null) {
            var5 = var7;
         }
      }

      label79: {
         try {
            if (var4 != null || var1.localVariables.isEmpty()) {
               break label79;
            }
         } catch (LVTGeneratorException var10) {
            throw b(var10);
         }

         var6 = getGeneratedLocalVariableTable(var0, var1).iterator();

         label58:
         while(true) {
            while(true) {
               if (!var6.hasNext()) {
                  break label58;
               }

               var7 = (LocalVariableNode)var6.next();

               try {
                  if (var7.index == var3 && isOpcodeInRange(var1.instructions, var7, var2)) {
                     break;
                  }
               } catch (LVTGeneratorException var9) {
                  throw b(var9);
               }
            }

            var4 = var7;
         }
      }

      LocalVariableNode var10000;
      try {
         if (var4 != null) {
            var10000 = var4;
            return var10000;
         }
      } catch (LVTGeneratorException var8) {
         throw b(var8);
      }

      var10000 = var5;
      return var10000;
   }

   private static boolean isOpcodeInRange(InsnList param0, LocalVariableNode param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public static List<LocalVariableNode> getLocalVariableTable(ClassNode var0, MethodNode var1) {
      try {
         if (var1.localVariables.isEmpty()) {
            return getGeneratedLocalVariableTable(var0, var1);
         }
      } catch (LVTGeneratorException var2) {
         throw b(var2);
      }

      return var1.localVariables;
   }

   public static List<LocalVariableNode> getGeneratedLocalVariableTable(ClassNode var0, MethodNode var1) {
      String var2 = String.format("%s.%s%s", var0.name, var1.name, var1.desc);
      List var3 = (List)calculatedLocalVariables.get(var2);

      try {
         if (var3 != null) {
            return var3;
         }
      } catch (LVTGeneratorException var4) {
         throw b(var4);
      }

      var3 = generateLocalVariableTable(var0, var1);
      calculatedLocalVariables.put(var2, var3);
      return var3;
   }

   public static List<LocalVariableNode> generateLocalVariableTable(ClassNode param0, MethodNode param1) {
      // $FF: Couldn't be decompiled
   }

   private static AbstractInsnNode nextNode(InsnList param0, AbstractInsnNode param1) {
      // $FF: Couldn't be decompiled
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

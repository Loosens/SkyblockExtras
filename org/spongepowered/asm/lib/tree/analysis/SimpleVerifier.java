package org.spongepowered.asm.lib.tree.analysis;

import java.util.List;
import org.spongepowered.asm.lib.Type;

public class SimpleVerifier extends BasicVerifier {
   private final Type currentClass;
   private final Type currentSuperClass;
   private final List<Type> currentClassInterfaces;
   private final boolean isInterface;
   private ClassLoader loader;

   public SimpleVerifier() {
      this((Type)null, (Type)null, false);
   }

   public SimpleVerifier(Type var1, Type var2, boolean var3) {
      this(var1, var2, (List)null, var3);
   }

   public SimpleVerifier(Type var1, Type var2, List<Type> var3, boolean var4) {
      this(327680, var1, var2, var3, var4);
   }

   protected SimpleVerifier(int var1, Type var2, Type var3, List<Type> var4, boolean var5) {
      super(var1);
      this.loader = this.getClass().getClassLoader();
      this.currentClass = var2;
      this.currentSuperClass = var3;
      this.currentClassInterfaces = var4;
      this.isInterface = var5;
   }

   public void setClassLoader(ClassLoader var1) {
      this.loader = var1;
   }

   public BasicValue newValue(Type param1) {
      // $FF: Couldn't be decompiled
   }

   protected boolean isArrayValue(BasicValue param1) {
      // $FF: Couldn't be decompiled
   }

   protected BasicValue getElementValue(BasicValue param1) throws AnalyzerException {
      // $FF: Couldn't be decompiled
   }

   protected boolean isSubTypeOf(BasicValue param1, BasicValue param2) {
      // $FF: Couldn't be decompiled
   }

   public BasicValue merge(BasicValue param1, BasicValue param2) {
      // $FF: Couldn't be decompiled
   }

   protected boolean isInterface(Type param1) {
      // $FF: Couldn't be decompiled
   }

   protected Type getSuperClass(Type param1) {
      // $FF: Couldn't be decompiled
   }

   protected boolean isAssignableFrom(Type param1, Type param2) {
      // $FF: Couldn't be decompiled
   }

   protected Class<?> getClass(Type param1) {
      // $FF: Couldn't be decompiled
   }

   private static Exception c(Exception var0) {
      return var0;
   }
}

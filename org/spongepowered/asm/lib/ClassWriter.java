package org.spongepowered.asm.lib;

public class ClassWriter extends ClassVisitor {
   public static final int COMPUTE_MAXS = 1;
   public static final int COMPUTE_FRAMES = 2;
   static final int ACC_SYNTHETIC_ATTRIBUTE = 262144;
   static final int TO_ACC_SYNTHETIC = 64;
   static final int NOARG_INSN = 0;
   static final int SBYTE_INSN = 1;
   static final int SHORT_INSN = 2;
   static final int VAR_INSN = 3;
   static final int IMPLVAR_INSN = 4;
   static final int TYPE_INSN = 5;
   static final int FIELDORMETH_INSN = 6;
   static final int ITFMETH_INSN = 7;
   static final int INDYMETH_INSN = 8;
   static final int LABEL_INSN = 9;
   static final int LABELW_INSN = 10;
   static final int LDC_INSN = 11;
   static final int LDCW_INSN = 12;
   static final int IINC_INSN = 13;
   static final int TABL_INSN = 14;
   static final int LOOK_INSN = 15;
   static final int MANA_INSN = 16;
   static final int WIDE_INSN = 17;
   static final int ASM_LABEL_INSN = 18;
   static final int F_INSERT = 256;
   static final byte[] TYPE;
   static final int CLASS = 7;
   static final int FIELD = 9;
   static final int METH = 10;
   static final int IMETH = 11;
   static final int STR = 8;
   static final int INT = 3;
   static final int FLOAT = 4;
   static final int LONG = 5;
   static final int DOUBLE = 6;
   static final int NAME_TYPE = 12;
   static final int UTF8 = 1;
   static final int MTYPE = 16;
   static final int HANDLE = 15;
   static final int INDY = 18;
   static final int HANDLE_BASE = 20;
   static final int TYPE_NORMAL = 30;
   static final int TYPE_UNINIT = 31;
   static final int TYPE_MERGED = 32;
   static final int BSM = 33;
   ClassReader cr;
   int version;
   int index;
   final ByteVector pool;
   Item[] items;
   int threshold;
   final Item key;
   final Item key2;
   final Item key3;
   final Item key4;
   Item[] typeTable;
   private short typeCount;
   private int access;
   private int name;
   String thisName;
   private int signature;
   private int superName;
   private int interfaceCount;
   private int[] interfaces;
   private int sourceFile;
   private ByteVector sourceDebug;
   private int enclosingMethodOwner;
   private int enclosingMethod;
   private AnnotationWriter anns;
   private AnnotationWriter ianns;
   private AnnotationWriter tanns;
   private AnnotationWriter itanns;
   private Attribute attrs;
   private int innerClassesCount;
   private ByteVector innerClasses;
   int bootstrapMethodsCount;
   ByteVector bootstrapMethods;
   FieldWriter firstField;
   FieldWriter lastField;
   MethodWriter firstMethod;
   MethodWriter lastMethod;
   private int compute;
   boolean hasAsmInsns;

   public ClassWriter(int var1) {
      super(327680);
      this.index = 1;
      this.pool = new ByteVector();
      this.items = new Item[256];
      this.threshold = (int)(0.75D * (double)this.items.length);
      this.key = new Item();
      this.key2 = new Item();
      this.key3 = new Item();
      this.key4 = new Item();
      byte var10001;
      if ((var1 & 2) != 0) {
         var10001 = 0;
      } else {
         label18: {
            try {
               if ((var1 & 1) != 0) {
                  var10001 = 2;
                  break label18;
               }
            } catch (RuntimeException var2) {
               throw b(var2);
            }

            var10001 = 3;
         }
      }

      this.compute = var10001;
   }

   public ClassWriter(ClassReader var1, int var2) {
      this(var2);
      var1.copyPool(this);
      this.cr = var1;
   }

   public final void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      try {
         this.version = var1;
         this.access = var2;
         this.name = this.newClass(var3);
         this.thisName = var3;
         if (var4 != null) {
            this.signature = this.newUTF8(var4);
         }
      } catch (RuntimeException var8) {
         throw b(var8);
      }

      ClassWriter var10000;
      int var10001;
      label46: {
         try {
            var10000 = this;
            if (var5 == null) {
               var10001 = 0;
               break label46;
            }
         } catch (RuntimeException var11) {
            throw b(var11);
         }

         var10001 = this.newClass(var5);
      }

      try {
         var10000.superName = var10001;
         if (var6 == null || var6.length <= 0) {
            return;
         }
      } catch (RuntimeException var10) {
         throw b(var10);
      }

      this.interfaceCount = var6.length;
      this.interfaces = new int[this.interfaceCount];
      int var7 = 0;

      try {
         while(var7 < this.interfaceCount) {
            this.interfaces[var7] = this.newClass(var6[var7]);
            ++var7;
         }
      } catch (RuntimeException var9) {
         throw b(var9);
      }

   }

   public final void visitSource(String var1, String var2) {
      try {
         if (var1 != null) {
            this.sourceFile = this.newUTF8(var1);
         }
      } catch (RuntimeException var4) {
         throw b(var4);
      }

      try {
         if (var2 != null) {
            this.sourceDebug = (new ByteVector()).encodeUTF8(var2, 0, Integer.MAX_VALUE);
         }

      } catch (RuntimeException var3) {
         throw b(var3);
      }
   }

   public final void visitOuterClass(String param1, String param2, String param3) {
      // $FF: Couldn't be decompiled
   }

   public final AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      ByteVector var3 = new ByteVector();
      var3.putShort(this.newUTF8(var1)).putShort(0);
      AnnotationWriter var4 = new AnnotationWriter(this, true, var3, var3, 2);

      try {
         if (var2) {
            var4.next = this.anns;
            this.anns = var4;
            return var4;
         }
      } catch (RuntimeException var5) {
         throw b(var5);
      }

      var4.next = this.ianns;
      this.ianns = var4;
      return var4;
   }

   public final AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      ByteVector var5 = new ByteVector();
      AnnotationWriter.putTarget(var1, var2, var5);
      var5.putShort(this.newUTF8(var3)).putShort(0);
      AnnotationWriter var6 = new AnnotationWriter(this, true, var5, var5, var5.length - 2);

      try {
         if (var4) {
            var6.next = this.tanns;
            this.tanns = var6;
            return var6;
         }
      } catch (RuntimeException var7) {
         throw b(var7);
      }

      var6.next = this.itanns;
      this.itanns = var6;
      return var6;
   }

   public final void visitAttribute(Attribute var1) {
      var1.next = this.attrs;
      this.attrs = var1;
   }

   public final void visitInnerClass(String param1, String param2, String param3, int param4) {
      // $FF: Couldn't be decompiled
   }

   public final FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      return new FieldWriter(this, var1, var2, var3, var4, var5);
   }

   public final MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      return new MethodWriter(this, var1, var2, var3, var4, var5, this.compute);
   }

   public final void visitEnd() {
   }

   public byte[] toByteArray() {
      // $FF: Couldn't be decompiled
   }

   Item newConstItem(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public int newConst(Object var1) {
      return this.newConstItem(var1).index;
   }

   public int newUTF8(String var1) {
      this.key.set(1, var1, (String)null, (String)null);
      Item var2 = this.get(this.key);
      if (var2 == null) {
         this.pool.putByte(1).putUTF8(var1);
         var2 = new Item(this.index++, this.key);
         this.put(var2);
      }

      return var2.index;
   }

   Item newClassItem(String var1) {
      this.key2.set(7, var1, (String)null, (String)null);
      Item var2 = this.get(this.key2);
      if (var2 == null) {
         this.pool.put12(7, this.newUTF8(var1));
         var2 = new Item(this.index++, this.key2);
         this.put(var2);
      }

      return var2;
   }

   public int newClass(String var1) {
      return this.newClassItem(var1).index;
   }

   Item newMethodTypeItem(String var1) {
      this.key2.set(16, var1, (String)null, (String)null);
      Item var2 = this.get(this.key2);
      if (var2 == null) {
         this.pool.put12(16, this.newUTF8(var1));
         var2 = new Item(this.index++, this.key2);
         this.put(var2);
      }

      return var2;
   }

   public int newMethodType(String var1) {
      return this.newMethodTypeItem(var1).index;
   }

   Item newHandleItem(int param1, String param2, String param3, String param4, boolean param5) {
      // $FF: Couldn't be decompiled
   }

   /** @deprecated */
   @Deprecated
   public int newHandle(int var1, String var2, String var3, String var4) {
      ClassWriter var10000;
      int var10001;
      String var10002;
      String var10003;
      String var10004;
      boolean var10005;
      try {
         var10000 = this;
         var10001 = var1;
         var10002 = var2;
         var10003 = var3;
         var10004 = var4;
         if (var1 == 9) {
            var10005 = true;
            return var10000.newHandle(var10001, var10002, var10003, var10004, var10005);
         }
      } catch (RuntimeException var5) {
         throw b(var5);
      }

      var10005 = false;
      return var10000.newHandle(var10001, var10002, var10003, var10004, var10005);
   }

   public int newHandle(int var1, String var2, String var3, String var4, boolean var5) {
      return this.newHandleItem(var1, var2, var3, var4, var5).index;
   }

   Item newInvokeDynamicItem(String param1, String param2, Handle param3, Object... param4) {
      // $FF: Couldn't be decompiled
   }

   public int newInvokeDynamic(String var1, String var2, Handle var3, Object... var4) {
      return this.newInvokeDynamicItem(var1, var2, var3, var4).index;
   }

   Item newFieldItem(String var1, String var2, String var3) {
      this.key3.set(9, var1, var2, var3);
      Item var4 = this.get(this.key3);
      if (var4 == null) {
         this.put122(9, this.newClass(var1), this.newNameType(var2, var3));
         var4 = new Item(this.index++, this.key3);
         this.put(var4);
      }

      return var4;
   }

   public int newField(String var1, String var2, String var3) {
      return this.newFieldItem(var1, var2, var3).index;
   }

   Item newMethodItem(String var1, String var2, String var3, boolean var4) {
      byte var10000;
      label20: {
         try {
            if (var4) {
               var10000 = 11;
               break label20;
            }
         } catch (RuntimeException var7) {
            throw b(var7);
         }

         var10000 = 10;
      }

      byte var5 = var10000;
      this.key3.set(var5, var1, var2, var3);
      Item var6 = this.get(this.key3);
      if (var6 == null) {
         this.put122(var5, this.newClass(var1), this.newNameType(var2, var3));
         var6 = new Item(this.index++, this.key3);
         this.put(var6);
      }

      return var6;
   }

   public int newMethod(String var1, String var2, String var3, boolean var4) {
      return this.newMethodItem(var1, var2, var3, var4).index;
   }

   Item newInteger(int var1) {
      this.key.set(var1);
      Item var2 = this.get(this.key);
      if (var2 == null) {
         this.pool.putByte(3).putInt(var1);
         var2 = new Item(this.index++, this.key);
         this.put(var2);
      }

      return var2;
   }

   Item newFloat(float var1) {
      this.key.set(var1);
      Item var2 = this.get(this.key);
      if (var2 == null) {
         this.pool.putByte(4).putInt(this.key.intVal);
         var2 = new Item(this.index++, this.key);
         this.put(var2);
      }

      return var2;
   }

   Item newLong(long var1) {
      this.key.set(var1);
      Item var3 = this.get(this.key);
      if (var3 == null) {
         this.pool.putByte(5).putLong(var1);
         var3 = new Item(this.index, this.key);
         this.index += 2;
         this.put(var3);
      }

      return var3;
   }

   Item newDouble(double var1) {
      this.key.set(var1);
      Item var3 = this.get(this.key);
      if (var3 == null) {
         this.pool.putByte(6).putLong(this.key.longVal);
         var3 = new Item(this.index, this.key);
         this.index += 2;
         this.put(var3);
      }

      return var3;
   }

   private Item newString(String var1) {
      this.key2.set(8, var1, (String)null, (String)null);
      Item var2 = this.get(this.key2);
      if (var2 == null) {
         this.pool.put12(8, this.newUTF8(var1));
         var2 = new Item(this.index++, this.key2);
         this.put(var2);
      }

      return var2;
   }

   public int newNameType(String var1, String var2) {
      return this.newNameTypeItem(var1, var2).index;
   }

   Item newNameTypeItem(String var1, String var2) {
      this.key2.set(12, var1, var2, (String)null);
      Item var3 = this.get(this.key2);
      if (var3 == null) {
         this.put122(12, this.newUTF8(var1), this.newUTF8(var2));
         var3 = new Item(this.index++, this.key2);
         this.put(var3);
      }

      return var3;
   }

   int addType(String var1) {
      this.key.set(30, var1, (String)null, (String)null);
      Item var2 = this.get(this.key);
      if (var2 == null) {
         var2 = this.addType(this.key);
      }

      return var2.index;
   }

   int addUninitializedType(String var1, int var2) {
      this.key.type = 31;
      this.key.intVal = var2;
      this.key.strVal1 = var1;
      this.key.hashCode = Integer.MAX_VALUE & 31 + var1.hashCode() + var2;
      Item var3 = this.get(this.key);
      if (var3 == null) {
         var3 = this.addType(this.key);
      }

      return var3.index;
   }

   private Item addType(Item var1) {
      ++this.typeCount;
      Item var2 = new Item(this.typeCount, this.key);

      try {
         this.put(var2);
         if (this.typeTable == null) {
            this.typeTable = new Item[16];
         }
      } catch (RuntimeException var4) {
         throw b(var4);
      }

      if (this.typeCount == this.typeTable.length) {
         Item[] var3 = new Item[2 * this.typeTable.length];
         System.arraycopy(this.typeTable, 0, var3, 0, this.typeTable.length);
         this.typeTable = var3;
      }

      this.typeTable[this.typeCount] = var2;
      return var2;
   }

   int getMergedType(int var1, int var2) {
      this.key2.type = 32;
      this.key2.longVal = (long)var1 | (long)var2 << 32;
      this.key2.hashCode = Integer.MAX_VALUE & 32 + var1 + var2;
      Item var3 = this.get(this.key2);
      if (var3 == null) {
         String var4 = this.typeTable[var1].strVal1;
         String var5 = this.typeTable[var2].strVal1;
         this.key2.intVal = this.addType(this.getCommonSuperClass(var4, var5));
         var3 = new Item(0, this.key2);
         this.put(var3);
      }

      return var3.intVal;
   }

   protected String getCommonSuperClass(String param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   private Item get(Item param1) {
      // $FF: Couldn't be decompiled
   }

   private void put(Item var1) {
      int var2;
      if (this.index + this.typeCount > this.threshold) {
         var2 = this.items.length;
         int var3 = var2 * 2 + 1;
         Item[] var4 = new Item[var3];

         Item var8;
         for(int var5 = var2 - 1; var5 >= 0; --var5) {
            for(Item var6 = this.items[var5]; var6 != null; var6 = var8) {
               int var7 = var6.hashCode % var4.length;
               var8 = var6.next;
               var6.next = var4[var7];
               var4[var7] = var6;
            }
         }

         this.items = var4;
         this.threshold = (int)((double)var3 * 0.75D);
      }

      var2 = var1.hashCode % this.items.length;
      var1.next = this.items[var2];
      this.items[var2] = var1;
   }

   private void put122(int var1, int var2, int var3) {
      this.pool.put12(var1, var2).putShort(var3);
   }

   private void put112(int var1, int var2, int var3) {
      this.pool.put11(var1, var2).putShort(var3);
   }

   static {
      byte[] var0 = new byte[220];
      String var1 = "AAAAAAAAAAAAAAAABCLMMDDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAADDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAANAAAAAAAAAAAAAAAAAAAAJJJJJJJJJJJJJJJJDOPAAAAAAGGGGGGGHIFBFAAFFAARQJJKKSSSSSSSSSSSSSSSSSS";
      int var2 = 0;

      try {
         while(var2 < var0.length) {
            var0[var2] = (byte)(var1.charAt(var2) - 65);
            ++var2;
         }
      } catch (RuntimeException var3) {
         throw b(var3);
      }

      TYPE = var0;
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

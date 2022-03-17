package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint$Selector;
import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionPointException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

public class InjectionPointData {
   private static final Pattern AT_PATTERN = createPattern();
   private final Map<String, String> args = new HashMap();
   private final IMixinContext context;
   private final MethodNode method;
   private final AnnotationNode parent;
   private final String at;
   private final String type;
   private final InjectionPoint$Selector selector;
   private final String target;
   private final String slice;
   private final int ordinal;
   private final int opcode;
   private final String id;

   public InjectionPointData(IMixinContext var1, MethodNode var2, AnnotationNode var3, String var4, List<String> var5, String var6, String var7, int var8, int var9, String var10) {
      this.context = var1;
      this.method = var2;
      this.parent = var3;
      this.at = var4;
      this.target = var6;
      this.slice = Strings.nullToEmpty(var7);
      this.ordinal = Math.max(-1, var8);
      this.opcode = var9;
      this.id = var10;
      this.parseArgs(var5);
      this.args.put("target", var6);
      this.args.put("ordinal", String.valueOf(var8));
      this.args.put("opcode", String.valueOf(var9));
      Matcher var11 = AT_PATTERN.matcher(var4);
      this.type = parseType(var11, var4);
      this.selector = parseSelector(var11);
   }

   private void parseArgs(List<String> var1) {
      try {
         if (var1 == null) {
            return;
         }
      } catch (InvalidMemberDescriptorException var6) {
         throw b(var6);
      }

      Iterator var2 = var1.iterator();

      while(true) {
         String var3;
         while(true) {
            do {
               if (!var2.hasNext()) {
                  return;
               }

               var3 = (String)var2.next();
            } while(var3 == null);

            int var4 = var3.indexOf(61);

            try {
               if (var4 <= -1) {
                  break;
               }

               this.args.put(var3.substring(0, var4), var3.substring(var4 + 1));
            } catch (InvalidMemberDescriptorException var5) {
               throw b(var5);
            }
         }

         this.args.put(var3, "");
      }
   }

   public String getAt() {
      return this.at;
   }

   public String getType() {
      return this.type;
   }

   public InjectionPoint$Selector getSelector() {
      return this.selector;
   }

   public IMixinContext getContext() {
      return this.context;
   }

   public MethodNode getMethod() {
      return this.method;
   }

   public Type getMethodReturnType() {
      return Type.getReturnType(this.method.desc);
   }

   public AnnotationNode getParent() {
      return this.parent;
   }

   public String getSlice() {
      return this.slice;
   }

   public LocalVariableDiscriminator getLocalVariableDiscriminator() {
      return LocalVariableDiscriminator.parse(this.parent);
   }

   public String get(String var1, String var2) {
      String var3 = (String)this.args.get(var1);

      String var10000;
      try {
         if (var3 != null) {
            var10000 = var3;
            return var10000;
         }
      } catch (InvalidMemberDescriptorException var4) {
         throw b(var4);
      }

      var10000 = var2;
      return var10000;
   }

   public int get(String var1, int var2) {
      return parseInt(this.get(var1, String.valueOf(var2)), var2);
   }

   public boolean get(String var1, boolean var2) {
      return parseBoolean(this.get(var1, String.valueOf(var2)), var2);
   }

   public MemberInfo get(String var1) {
      try {
         return MemberInfo.parseAndValidate(this.get(var1, ""), this.context);
      } catch (InvalidMemberDescriptorException var3) {
         throw new InvalidInjectionPointException(this.context, "Failed parsing @At(\"%s\").%s descriptor \"%s\" on %s", new Object[]{this.at, var1, this.target, InjectionInfo.describeInjector(this.context, this.parent, this.method)});
      }
   }

   public MemberInfo getTarget() {
      try {
         return MemberInfo.parseAndValidate(this.target, this.context);
      } catch (InvalidMemberDescriptorException var2) {
         throw new InvalidInjectionPointException(this.context, "Failed parsing @At(\"%s\") descriptor \"%s\" on %s", new Object[]{this.at, this.target, InjectionInfo.describeInjector(this.context, this.parent, this.method)});
      }
   }

   public int getOrdinal() {
      return this.ordinal;
   }

   public int getOpcode() {
      return this.opcode;
   }

   public int getOpcode(int var1) {
      int var10000;
      try {
         if (this.opcode > 0) {
            var10000 = this.opcode;
            return var10000;
         }
      } catch (InvalidMemberDescriptorException var2) {
         throw b(var2);
      }

      var10000 = var1;
      return var10000;
   }

   public int getOpcode(int var1, int... var2) {
      int[] var3 = var2;
      int var4 = var2.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         int var6 = var3[var5];

         try {
            if (this.opcode == var6) {
               return this.opcode;
            }
         } catch (InvalidMemberDescriptorException var7) {
            throw b(var7);
         }
      }

      return var1;
   }

   public String getId() {
      return this.id;
   }

   public String toString() {
      return this.type;
   }

   private static Pattern createPattern() {
      return Pattern.compile(String.format("^([^:]+):?(%s)?$", Joiner.on('|').join(InjectionPoint$Selector.values())));
   }

   public static String parseType(String var0) {
      Matcher var1 = AT_PATTERN.matcher(var0);
      return parseType(var1, var0);
   }

   private static String parseType(Matcher var0, String var1) {
      String var10000;
      try {
         if (var0.matches()) {
            var10000 = var0.group(1);
            return var10000;
         }
      } catch (InvalidMemberDescriptorException var2) {
         throw b(var2);
      }

      var10000 = var1;
      return var10000;
   }

   private static InjectionPoint$Selector parseSelector(Matcher param0) {
      // $FF: Couldn't be decompiled
   }

   private static int parseInt(String var0, int var1) {
      try {
         return Integer.parseInt(var0);
      } catch (Exception var3) {
         return var1;
      }
   }

   private static boolean parseBoolean(String var0, boolean var1) {
      try {
         return Boolean.parseBoolean(var0);
      } catch (Exception var3) {
         return var1;
      }
   }

   private static InvalidMemberDescriptorException b(InvalidMemberDescriptorException var0) {
      return var0;
   }
}

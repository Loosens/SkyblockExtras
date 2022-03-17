package org.spongepowered.tools.obfuscation;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.TypeElement;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeReference;

public final class TargetMap extends HashMap<TypeReference, Set<TypeReference>> {
   private static final long serialVersionUID = 1L;
   private final String sessionId;

   private TargetMap() {
      this(String.valueOf(System.currentTimeMillis()));
   }

   private TargetMap(String var1) {
      this.sessionId = var1;
   }

   public String getSessionId() {
      return this.sessionId;
   }

   public void registerTargets(AnnotatedMixin var1) {
      this.registerTargets(var1.getTargets(), var1.getHandle());
   }

   public void registerTargets(List<TypeHandle> var1, TypeHandle var2) {
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         TypeHandle var4 = (TypeHandle)var3.next();
         this.addMixin(var4, var2);
      }

   }

   public void addMixin(TypeHandle var1, TypeHandle var2) {
      this.addMixin(var1.getReference(), var2.getReference());
   }

   public void addMixin(String var1, String var2) {
      this.addMixin(new TypeReference(var1), new TypeReference(var2));
   }

   public void addMixin(TypeReference var1, TypeReference var2) {
      Set var3 = this.getMixinsFor(var1);
      var3.add(var2);
   }

   public Collection<TypeReference> getMixinsTargeting(TypeElement var1) {
      return this.getMixinsTargeting(new TypeHandle(var1));
   }

   public Collection<TypeReference> getMixinsTargeting(TypeHandle var1) {
      return this.getMixinsTargeting(var1.getReference());
   }

   public Collection<TypeReference> getMixinsTargeting(TypeReference var1) {
      return Collections.unmodifiableCollection(this.getMixinsFor(var1));
   }

   private Set<TypeReference> getMixinsFor(TypeReference var1) {
      Object var2 = (Set)this.get(var1);
      if (var2 == null) {
         var2 = new HashSet();
         this.put(var1, var2);
      }

      return (Set)var2;
   }

   public void readImports(File var1) throws IOException {
      try {
         if (!var1.isFile()) {
            return;
         }
      } catch (IOException var6) {
         throw b(var6);
      }

      Iterator var2 = Files.readLines(var1, Charset.defaultCharset()).iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         String[] var4 = var3.split("\t");

         try {
            if (var4.length == 2) {
               this.addMixin(var4[1], var4[0]);
            }
         } catch (IOException var5) {
            throw b(var5);
         }
      }

   }

   public void write(boolean var1) {
      ObjectOutputStream var2 = null;
      FileOutputStream var3 = null;
      boolean var14 = false;

      label127: {
         try {
            var14 = true;
            File var4 = getSessionFile(this.sessionId);

            try {
               if (var1) {
                  var4.deleteOnExit();
               }
            } catch (IOException var19) {
               throw b(var19);
            }

            var3 = new FileOutputStream(var4, true);
            var2 = new ObjectOutputStream(var3);
            var2.writeObject(this);
            var14 = false;
            break label127;
         } catch (Exception var20) {
            var20.printStackTrace();
            var14 = false;
         } finally {
            if (var14) {
               label103: {
                  IOException var6;
                  try {
                     if (var2 == null) {
                        break label103;
                     }

                     try {
                        var2.close();
                        break label103;
                     } catch (IOException var17) {
                        var6 = var17;
                     }
                  } catch (RuntimeException var18) {
                     throw b(var18);
                  }

                  var6.printStackTrace();
               }

            }
         }

         if (var2 != null) {
            try {
               var2.close();
            } catch (IOException var15) {
               var15.printStackTrace();
            }

            return;
         }

         return;
      }

      if (var2 != null) {
         try {
            var2.close();
         } catch (IOException var16) {
            var16.printStackTrace();
         }
      }

   }

   private static TargetMap read(File var0) {
      ObjectInputStream var1 = null;
      FileInputStream var2 = null;
      boolean var14 = false;

      TargetMap var3;
      label117: {
         try {
            var14 = true;
            var2 = new FileInputStream(var0);
            var1 = new ObjectInputStream(var2);
            var3 = (TargetMap)var1.readObject();
            var14 = false;
            break label117;
         } catch (Exception var20) {
            var20.printStackTrace();
            var14 = false;
         } finally {
            if (var14) {
               label98: {
                  IOException var6;
                  try {
                     if (var1 == null) {
                        break label98;
                     }

                     try {
                        var1.close();
                        break label98;
                     } catch (IOException var16) {
                        var6 = var16;
                     }
                  } catch (RuntimeException var17) {
                     throw b(var17);
                  }

                  var6.printStackTrace();
               }

            }
         }

         if (var1 != null) {
            try {
               var1.close();
            } catch (IOException var15) {
               var15.printStackTrace();
            }
         }

         return null;
      }

      IOException var4;
      try {
         if (var1 == null) {
            return var3;
         }

         try {
            var1.close();
            return var3;
         } catch (IOException var18) {
            var4 = var18;
         }
      } catch (RuntimeException var19) {
         throw b(var19);
      }

      var4.printStackTrace();
      return var3;
   }

   public static TargetMap create(String var0) {
      if (var0 != null) {
         File var1 = getSessionFile(var0);
         if (var1.exists()) {
            TargetMap var2 = read(var1);

            try {
               if (var2 != null) {
                  return var2;
               }
            } catch (RuntimeException var3) {
               throw b(var3);
            }
         }
      }

      return new TargetMap();
   }

   private static File getSessionFile(String var0) {
      File var1 = new File(System.getProperty("java.io.tmpdir"));
      return new File(var1, String.format("mixin-targetdb-%s.tmp", var0));
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

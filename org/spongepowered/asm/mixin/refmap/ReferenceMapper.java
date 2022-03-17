package org.spongepowered.asm.mixin.refmap;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.MixinService;

public final class ReferenceMapper implements IReferenceMapper, Serializable {
   private static final long serialVersionUID = 2L;
   public static final String DEFAULT_RESOURCE = "mixin.refmap.json";
   public static final ReferenceMapper DEFAULT_MAPPER = new ReferenceMapper(true, "invalid");
   private final Map<String, Map<String, String>> mappings;
   private final Map<String, Map<String, Map<String, String>>> data;
   private final transient boolean readOnly;
   private transient String context;
   private transient String resource;

   public ReferenceMapper() {
      this(false, "mixin.refmap.json");
   }

   private ReferenceMapper(boolean var1, String var2) {
      this.mappings = Maps.newHashMap();
      this.data = Maps.newHashMap();
      this.context = null;
      this.readOnly = var1;
      this.resource = var2;
   }

   public boolean isDefault() {
      return this.readOnly;
   }

   private void setResourceName(String param1) {
      // $FF: Couldn't be decompiled
   }

   public String getResourceName() {
      return this.resource;
   }

   public String getStatus() {
      String var10000;
      try {
         if (this.isDefault()) {
            var10000 = "No refMap loaded.";
            return var10000;
         }
      } catch (JsonParseException var1) {
         throw b(var1);
      }

      var10000 = "Using refmap " + this.getResourceName();
      return var10000;
   }

   public String getContext() {
      return this.context;
   }

   public void setContext(String var1) {
      this.context = var1;
   }

   public String remap(String var1, String var2) {
      return this.remapWithContext(this.context, var1, var2);
   }

   public String remapWithContext(String var1, String var2, String var3) {
      Map var4 = this.mappings;
      if (var1 != null) {
         var4 = (Map)this.data.get(var1);
         if (var4 == null) {
            var4 = this.mappings;
         }
      }

      return this.remap(var4, var2, var3);
   }

   private String remap(Map<String, Map<String, String>> var1, String var2, String var3) {
      if (var2 == null) {
         Iterator var4 = var1.values().iterator();

         while(var4.hasNext()) {
            Map var5 = (Map)var4.next();

            try {
               if (var5.containsKey(var3)) {
                  return (String)var5.get(var3);
               }
            } catch (JsonParseException var6) {
               throw b(var6);
            }
         }
      }

      Map var9 = (Map)var1.get(var2);

      try {
         if (var9 == null) {
            return var3;
         }
      } catch (JsonParseException var8) {
         throw b(var8);
      }

      String var10 = (String)var9.get(var3);

      String var10000;
      try {
         if (var10 != null) {
            var10000 = var10;
            return var10000;
         }
      } catch (JsonParseException var7) {
         throw b(var7);
      }

      var10000 = var3;
      return var10000;
   }

   public String addMapping(String param1, String param2, String param3, String param4) {
      // $FF: Couldn't be decompiled
   }

   public void write(Appendable var1) {
      (new GsonBuilder()).setPrettyPrinting().create().toJson(this, var1);
   }

   public static ReferenceMapper read(String var0) {
      Logger var1 = LogManager.getLogger("mixin");
      InputStreamReader var2 = null;

      try {
         IMixinService var3 = MixinService.getService();
         InputStream var4 = var3.getResourceAsStream(var0);
         if (var4 != null) {
            var2 = new InputStreamReader(var4);
            ReferenceMapper var5 = readJson(var2);
            var5.setResourceName(var0);
            ReferenceMapper var6 = var5;
            return var6;
         }
      } catch (JsonParseException var11) {
         var1.error("Invalid REFMAP JSON in " + var0 + ": " + var11.getClass().getName() + " " + var11.getMessage());
      } catch (Exception var12) {
         var1.error("Failed reading REFMAP JSON from " + var0 + ": " + var12.getClass().getName() + " " + var12.getMessage());
      } finally {
         IOUtils.closeQuietly(var2);
      }

      return DEFAULT_MAPPER;
   }

   public static ReferenceMapper read(Reader var0, String var1) {
      try {
         ReferenceMapper var2 = readJson(var0);
         var2.setResourceName(var1);
         return var2;
      } catch (Exception var3) {
         return DEFAULT_MAPPER;
      }
   }

   private static ReferenceMapper readJson(Reader var0) {
      return (ReferenceMapper)(new Gson()).fromJson(var0, ReferenceMapper.class);
   }

   private static JsonParseException b(JsonParseException var0) {
      return var0;
   }
}

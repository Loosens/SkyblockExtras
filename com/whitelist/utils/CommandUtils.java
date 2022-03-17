package com.whitelist.utils;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

public class CommandUtils {
   // $FF: synthetic field
   private String username;
   // $FF: synthetic field
   private static final int[] lIlIlI;
   // $FF: synthetic field
   private String content;
   // $FF: synthetic field
   private final String url;
   // $FF: synthetic field
   private List<CommandUtils.EmbedObject> embeds = new ArrayList();
   // $FF: synthetic field
   private static final String[] lIlIIl;
   // $FF: synthetic field
   private boolean tts;
   // $FF: synthetic field
   private String avatarUrl;

   public void setUsername(String llllIllIlIlIIlI) {
      llllIllIlIlIIll.username = llllIllIlIlIIlI;
   }

   public void addEmbed(CommandUtils.EmbedObject llllIllIIlllllI) {
      llllIllIlIIIIIl.embeds.add(llllIllIIlllllI);
      "".length();
   }

   private static boolean llllIl(int var0) {
      return var0 == 0;
   }

   public void setTts(boolean llllIllIlIIIllI) {
      llllIllIlIIIlIl.tts = llllIllIlIIIllI;
   }

   private static boolean lllIll(Object var0) {
      return var0 == null;
   }

   private static boolean llllII(int var0) {
      return var0 != 0;
   }

   private static boolean llllll(int var0, int var1) {
      return var0 < var1;
   }

   private static String llIlll(String llllIlIlllIllIl, String llllIlIllllIIIl) {
      llllIlIlllIllIl = new String(Base64.getDecoder().decode(llllIlIlllIllIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
      String llllIlIlllIlIll = new StringBuilder();
      char[] llllIlIlllIllll = llllIlIllllIIIl.toCharArray();
      long llllIlIlllIlIIl = lIlIlI[0];
      short llllIlIlllIlIII = llllIlIlllIllIl.toCharArray();
      boolean llllIlIlllIIlll = llllIlIlllIlIII.length;
      int llllIlIlllIIllI = lIlIlI[0];

      do {
         if (!llllll(llllIlIlllIIllI, llllIlIlllIIlll)) {
            return String.valueOf(llllIlIlllIlIll);
         }

         char llllIlIllllIIll = llllIlIlllIlIII[llllIlIlllIIllI];
         llllIlIlllIlIll.append((char)(llllIlIllllIIll ^ llllIlIlllIllll[llllIlIlllIlIIl % llllIlIlllIllll.length]));
         "".length();
         ++llllIlIlllIlIIl;
         ++llllIlIlllIIllI;
         "".length();
      } while(((150 + 52 - 126 + 175 ^ 127 + 88 - 116 + 84) & (50 ^ 15 ^ 179 ^ 194 ^ -" ".length())) == 0);

      return null;
   }

   static {
      lllIlI();
      lllIIl();
   }

   private static String llIIII(String llllIllIIIIIIlI, String llllIllIIIIIIIl) {
      try {
         SecretKeySpec llllIllIIIIIlIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llllIllIIIIIIIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
         Cipher llllIllIIIIIlII = Cipher.getInstance("Blowfish");
         llllIllIIIIIlII.init(lIlIlI[2], llllIllIIIIIlIl);
         return new String(llllIllIIIIIlII.doFinal(Base64.getDecoder().decode(llllIllIIIIIIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
      } catch (Exception var4) {
         var4.printStackTrace();
         return null;
      }
   }

   private static boolean lllllI(Object var0) {
      return var0 != null;
   }

   public CommandUtils(String llllIllIlIllllI) {
      llllIllIlIlllll.url = llllIllIlIllllI;
   }

   public void execute() throws IOException {
      if (lllIll(llllIllIIIlllIl.content) && llllII(llllIllIIIlllIl.embeds.isEmpty())) {
         throw new IllegalArgumentException(lIlIIl[lIlIlI[0]]);
      } else {
         CommandUtils.JSONObject llllIllIIIlllII = new CommandUtils.JSONObject();
         llllIllIIIlllII.put(lIlIIl[lIlIlI[1]], llllIllIIIlllIl.content);
         llllIllIIIlllII.put(lIlIIl[lIlIlI[2]], llllIllIIIlllIl.username);
         llllIllIIIlllII.put(lIlIIl[lIlIlI[3]], llllIllIIIlllIl.avatarUrl);
         llllIllIIIlllII.put(lIlIIl[lIlIlI[4]], llllIllIIIlllIl.tts);
         if (llllIl(llllIllIIIlllIl.embeds.isEmpty())) {
            List<CommandUtils.JSONObject> llllIllIIIlIllI = new ArrayList();
            Iterator llllIllIIIlIlIl = llllIllIIIlllIl.embeds.iterator();

            while(llllII(llllIllIIIlIlIl.hasNext())) {
               CommandUtils.EmbedObject llllIllIIIlllll = (CommandUtils.EmbedObject)llllIllIIIlIlIl.next();
               CommandUtils.JSONObject llllIllIIlIIllI = new CommandUtils.JSONObject();
               llllIllIIlIIllI.put(lIlIIl[lIlIlI[5]], llllIllIIIlllll.getTitle());
               llllIllIIlIIllI.put(lIlIIl[lIlIlI[6]], llllIllIIIlllll.getDescription());
               llllIllIIlIIllI.put(lIlIIl[lIlIlI[7]], llllIllIIIlllll.getUrl());
               if (lllllI(llllIllIIIlllll.getColor())) {
                  Color llllIllIIlIlllI = llllIllIIIlllll.getColor();
                  int llllIllIIlIllIl = llllIllIIlIlllI.getRed();
                  llllIllIIlIllIl = (llllIllIIlIllIl << lIlIlI[8]) + llllIllIIlIlllI.getGreen();
                  llllIllIIlIllIl = (llllIllIIlIllIl << lIlIlI[8]) + llllIllIIlIlllI.getBlue();
                  llllIllIIlIIllI.put(lIlIIl[lIlIlI[8]], llllIllIIlIllIl);
               }

               CommandUtils.EmbedObject.Footer llllIllIIlIIlIl = llllIllIIIlllll.getFooter();
               byte llllIllIIIlIIIl = llllIllIIIlllll.getImage();
               CommandUtils.EmbedObject.Thumbnail llllIllIIlIIIll = llllIllIIIlllll.getThumbnail();
               CommandUtils.EmbedObject.Author llllIllIIlIIIlI = llllIllIIIlllll.getAuthor();
               List<CommandUtils.EmbedObject.Field> llllIllIIlIIIIl = llllIllIIIlllll.getFields();
               CommandUtils.JSONObject llllIllIIIIllIl;
               if (lllllI(llllIllIIlIIlIl)) {
                  llllIllIIIIllIl = new CommandUtils.JSONObject();
                  llllIllIIIIllIl.put(lIlIIl[lIlIlI[9]], llllIllIIlIIlIl.getText());
                  llllIllIIIIllIl.put(lIlIIl[lIlIlI[10]], llllIllIIlIIlIl.getIconUrl());
                  llllIllIIlIIllI.put(lIlIIl[lIlIlI[11]], llllIllIIIIllIl);
               }

               if (lllllI(llllIllIIIlIIIl)) {
                  llllIllIIIIllIl = new CommandUtils.JSONObject();
                  llllIllIIIIllIl.put(lIlIIl[lIlIlI[12]], llllIllIIIlIIIl.getUrl());
                  llllIllIIlIIllI.put(lIlIIl[lIlIlI[13]], llllIllIIIIllIl);
               }

               if (lllllI(llllIllIIlIIIll)) {
                  llllIllIIIIllIl = new CommandUtils.JSONObject();
                  llllIllIIIIllIl.put(lIlIIl[lIlIlI[14]], llllIllIIlIIIll.getUrl());
                  llllIllIIlIIllI.put(lIlIIl[lIlIlI[15]], llllIllIIIIllIl);
               }

               if (lllllI(llllIllIIlIIIlI)) {
                  llllIllIIIIllIl = new CommandUtils.JSONObject();
                  llllIllIIIIllIl.put(lIlIIl[lIlIlI[16]], llllIllIIlIIIlI.getName());
                  llllIllIIIIllIl.put(lIlIIl[lIlIlI[17]], llllIllIIlIIIlI.getUrl());
                  llllIllIIIIllIl.put(lIlIIl[lIlIlI[18]], llllIllIIlIIIlI.getIconUrl());
                  llllIllIIlIIllI.put(lIlIIl[lIlIlI[19]], llllIllIIIIllIl);
               }

               List<CommandUtils.JSONObject> llllIllIIIIllIl = new ArrayList();
               Iterator llllIllIIIIllII = llllIllIIlIIIIl.iterator();

               while(llllII(llllIllIIIIllII.hasNext())) {
                  CommandUtils.EmbedObject.Field llllIllIIlIIlll = (CommandUtils.EmbedObject.Field)llllIllIIIIllII.next();
                  CommandUtils.JSONObject llllIllIIlIlIII = new CommandUtils.JSONObject();
                  llllIllIIlIlIII.put(lIlIIl[lIlIlI[20]], llllIllIIlIIlll.getName());
                  llllIllIIlIlIII.put(lIlIIl[lIlIlI[21]], llllIllIIlIIlll.getValue());
                  llllIllIIlIlIII.put(lIlIIl[lIlIlI[22]], llllIllIIlIIlll.isInline());
                  llllIllIIIIllIl.add(llllIllIIlIlIII);
                  "".length();
                  "".length();
                  if (" ".length() <= ((113 ^ 119 ^ 23 ^ 7) & (44 + 99 - 134 + 153 ^ 174 + 54 - 206 + 158 ^ -" ".length()))) {
                     return;
                  }
               }

               llllIllIIlIIllI.put(lIlIIl[lIlIlI[23]], llllIllIIIIllIl.toArray());
               llllIllIIIlIllI.add(llllIllIIlIIllI);
               "".length();
               "".length();
               if (((120 ^ 127) & ~(73 ^ 78)) != 0) {
                  return;
               }
            }

            llllIllIIIlllII.put(lIlIIl[lIlIlI[24]], llllIllIIIlIllI.toArray());
         }

         URL llllIllIIIllIll = new URL(llllIllIIIlllIl.url);
         String llllIllIIIlIlIl = (HttpsURLConnection)llllIllIIIllIll.openConnection();
         llllIllIIIlIlIl.addRequestProperty(lIlIIl[lIlIlI[25]], lIlIIl[lIlIlI[26]]);
         llllIllIIIlIlIl.addRequestProperty(lIlIIl[lIlIlI[27]], lIlIIl[lIlIlI[28]]);
         llllIllIIIlIlIl.setDoOutput((boolean)lIlIlI[1]);
         llllIllIIIlIlIl.setRequestMethod(lIlIIl[lIlIlI[29]]);
         OutputStream llllIllIIIllIIl = llllIllIIIlIlIl.getOutputStream();
         llllIllIIIllIIl.write(llllIllIIIlllII.toString().getBytes(StandardCharsets.UTF_8));
         llllIllIIIllIIl.flush();
         llllIllIIIllIIl.close();
         llllIllIIIlIlIl.getInputStream().close();
         llllIllIIIlIlIl.disconnect();
      }
   }

   public void setAvatarUrl(String llllIllIlIIlIlI) {
      llllIllIlIIllIl.avatarUrl = llllIllIlIIlIlI;
   }

   public void setContent(String llllIllIlIlIllI) {
      llllIllIlIlIlll.content = llllIllIlIlIllI;
   }

   private static void lllIlI() {
      lIlIlI = new int[31];
      lIlIlI[0] = (78 ^ 68) & ~(92 ^ 86);
      lIlIlI[1] = " ".length();
      lIlIlI[2] = "  ".length();
      lIlIlI[3] = "   ".length();
      lIlIlI[4] = 147 ^ 151;
      lIlIlI[5] = 157 ^ 152;
      lIlIlI[6] = 134 ^ 128;
      lIlIlI[7] = 124 ^ 123;
      lIlIlI[8] = 6 ^ 86 ^ 194 ^ 154;
      lIlIlI[9] = 182 ^ 191;
      lIlIlI[10] = 176 ^ 190 ^ 100 ^ 96;
      lIlIlI[11] = "   ".length() ^ 92 ^ 84;
      lIlIlI[12] = 73 ^ 69;
      lIlIlI[13] = 10 ^ 7;
      lIlIlI[14] = 52 ^ 58;
      lIlIlI[15] = 180 ^ 187;
      lIlIlI[16] = 43 ^ 59;
      lIlIlI[17] = 53 ^ 62 ^ 65 ^ 91;
      lIlIlI[18] = 128 ^ 146;
      lIlIlI[19] = 140 + 122 - 119 + 31 ^ 108 + 113 - 95 + 63;
      lIlIlI[20] = 129 ^ 149;
      lIlIlI[21] = 100 ^ 113;
      lIlIlI[22] = 126 ^ 104;
      lIlIlI[23] = 44 + 14 - -5 + 86 ^ 122 + 82 - 78 + 4;
      lIlIlI[24] = 119 ^ 111;
      lIlIlI[25] = 80 ^ 73;
      lIlIlI[26] = 126 ^ 100;
      lIlIlI[27] = 132 ^ 159;
      lIlIlI[28] = 17 ^ 13;
      lIlIlI[29] = 76 ^ 81;
      lIlIlI[30] = 39 ^ 57;
   }

   private static String lllIII(String llllIlIllIllIll, String llllIlIllIllIlI) {
      try {
         Exception llllIlIllIllIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llllIlIllIllIlI.getBytes(StandardCharsets.UTF_8)), lIlIlI[8]), "DES");
         Cipher llllIlIllIlllll = Cipher.getInstance("DES");
         llllIlIllIlllll.init(lIlIlI[2], llllIlIllIllIIl);
         return new String(llllIlIllIlllll.doFinal(Base64.getDecoder().decode(llllIlIllIllIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
      } catch (Exception var4) {
         var4.printStackTrace();
         return null;
      }
   }

   private static void lllIIl() {
      lIlIIl = new String[lIlIlI[30]];
      lIlIIl[lIlIlI[0]] = llIIII("sz9ODyersM1wyupQJMmSotg1ERMbRY15B4+DGIC05/TFz/f4MWYgMvk52eeFIT3b", "PENVi");
      lIlIIl[lIlIlI[1]] = llIIII("VF9t0JxCWow=", "YRpDc");
      lIlIIl[lIlIlI[2]] = llIlll("JAE0AyowHzQ=", "QrQqD");
      lIlIIl[lIlIlI[3]] = lllIII("TE3mkjkOmZACPSUS0S56Hg==", "OMjGU");
      lIlIIl[lIlIlI[4]] = lllIII("4FcTfaK0CCE=", "OcuvY");
      lIlIIl[lIlIlI[5]] = llIIII("+L17UEJ9Iss=", "GECde");
      lIlIIl[lIlIlI[6]] = lllIII("TNs2nIgOPL7ZI31bYYXRFw==", "CUcSx");
      lIlIIl[lIlIlI[7]] = llIlll("Fjg/", "cJSXE");
      lIlIIl[lIlIlI[8]] = llIIII("+4qLzPQIZs4=", "zysIA");
      lIlIIl[lIlIlI[9]] = llIlll("FRcTEw==", "arkgw");
      lIlIIl[lIlIlI[10]] = llIlll("MyQYGh4vNRs=", "ZGwtA");
      lIlIIl[lIlIlI[11]] = llIIII("nJ0VCZXCeis=", "zEPrw");
      lIlIIl[lIlIlI[12]] = llIlll("PwAH", "Jrkdt");
      lIlIIl[lIlIlI[13]] = lllIII("j4XTwTdpFB0=", "sVBgk");
      lIlIIl[lIlIlI[14]] = lllIII("oTXUBTIgo74=", "Huffr");
      lIlIIl[lIlIlI[15]] = llIlll("FxA3DyQNGSsO", "cxBbF");
      lIlIIl[lIlIlI[16]] = llIlll("AwIrMw==", "mcFVl");
      lIlIIl[lIlIlI[17]] = llIlll("NBEO", "AcbqA");
      lIlIIl[lIlIlI[18]] = llIlll("GjI6JhYGIzk=", "sQUHI");
      lIlIIl[lIlIlI[19]] = lllIII("7BrqiqYDv/U=", "mRrDm");
      lIlIIl[lIlIlI[20]] = llIIII("z3jLJhKh5Gc=", "IZYNg");
      lIlIIl[lIlIlI[21]] = llIlll("GjkqDxE=", "lXFzt");
      lIlIIl[lIlIlI[22]] = llIIII("MMS4pyzqc+k=", "Twmnm");
      lIlIIl[lIlIlI[23]] = llIIII("nYe3wArDz9k=", "dQway");
      lIlIIl[lIlIlI[24]] = llIlll("DwEENi8Z", "jlfSK");
      lIlIIl[lIlIlI[25]] = lllIII("VzWo1GRNYdnyHaPpHYnoJw==", "MGJtX");
      lIlIIl[lIlIlI[26]] = lllIII("07OTPpnB5kJ4J6LdQVARlMOQn1kxiRDX", "fenFf");
      lIlIIl[lIlIlI[27]] = llIlll("IQIyIWo1FjI9Mw==", "tqWSG");
      lIlIIl[lIlIlI[28]] = llIlll("AQAjIn0PCCYgPzkFAiYyIw46KH0JOHgENScOLRw=", "KaUCP");
      lIlIIl[lIlIlI[29]] = llIIII("6IHPvpVnFJ4=", "VDuFu");
   }

   public static class EmbedObject {
      // $FF: synthetic field
      private CommandUtils.EmbedObject.Author author;
      // $FF: synthetic field
      private String title;
      // $FF: synthetic field
      private CommandUtils.EmbedObject.Footer footer;
      // $FF: synthetic field
      private CommandUtils.EmbedObject.Thumbnail thumbnail;
      // $FF: synthetic field
      private String url;
      // $FF: synthetic field
      private String description;
      // $FF: synthetic field
      private Color color;
      // $FF: synthetic field
      private List<CommandUtils.EmbedObject.Field> fields = new ArrayList();
      // $FF: synthetic field
      private CommandUtils.EmbedObject.Image image;

      public CommandUtils.EmbedObject setFooter(String lllllIlIIIlIIll, String lllllIlIIIlIIlI) {
         lllllIlIIIlIIIl.footer = new CommandUtils.EmbedObject.Footer(lllllIlIIIlIIll, lllllIlIIIlIIlI);
         return lllllIlIIIlIIIl;
      }

      public CommandUtils.EmbedObject.Image getImage() {
         return lllllIlIIllIlll.image;
      }

      public List<CommandUtils.EmbedObject.Field> getFields() {
         return lllllIlIIllIIII.fields;
      }

      public CommandUtils.EmbedObject setDescription(String lllllIlIIlIIllI) {
         lllllIlIIlIIlll.description = lllllIlIIlIIllI;
         return lllllIlIIlIIlll;
      }

      public Color getColor() {
         return lllllIlIlIIIIII.color;
      }

      public CommandUtils.EmbedObject setImage(String lllllIlIIIIIIll) {
         lllllIlIIIIIllI.image = new CommandUtils.EmbedObject.Image(lllllIlIIIIIIll);
         return lllllIlIIIIIllI;
      }

      public CommandUtils.EmbedObject.Thumbnail getThumbnail() {
         return lllllIlIIlllIIl.thumbnail;
      }

      public CommandUtils.EmbedObject.Author getAuthor() {
         return lllllIlIIllIIll.author;
      }

      public CommandUtils.EmbedObject setColor(Color lllllIlIIIllIII) {
         lllllIlIIIllIIl.color = lllllIlIIIllIII;
         return lllllIlIIIllIIl;
      }

      public String getDescription() {
         return lllllIlIlIIIlIl.description;
      }

      public CommandUtils.EmbedObject setUrl(String lllllIlIIlIIIII) {
         lllllIlIIIlllll.url = lllllIlIIlIIIII;
         return lllllIlIIIlllll;
      }

      public String getTitle() {
         return lllllIlIlIIlIII.title;
      }

      public CommandUtils.EmbedObject.Footer getFooter() {
         return lllllIlIIllllIl.footer;
      }

      public String getUrl() {
         return lllllIlIlIIIIll.url;
      }

      public CommandUtils.EmbedObject setTitle(String lllllIlIIlIllII) {
         lllllIlIIlIllIl.title = lllllIlIIlIllII;
         return lllllIlIIlIllIl;
      }

      public CommandUtils.EmbedObject addField(String lllllIIllllIIIl, String lllllIIllllIIII, boolean lllllIIlllIlIll) {
         lllllIIllllIIlI.fields.add(new CommandUtils.EmbedObject.Field(lllllIIllllIIIl, lllllIIllllIIII, lllllIIlllIlIll));
         "".length();
         return lllllIIllllIIlI;
      }

      public CommandUtils.EmbedObject setAuthor(String lllllIIllllllIl, String lllllIIlllllIII, String lllllIIlllllIll) {
         lllllIIlllllIlI.author = new CommandUtils.EmbedObject.Author(lllllIIllllllIl, lllllIIlllllIII, lllllIIlllllIll);
         return lllllIIlllllIlI;
      }

      public CommandUtils.EmbedObject setThumbnail(String lllllIlIIIIlIIl) {
         lllllIlIIIIlIlI.thumbnail = new CommandUtils.EmbedObject.Thumbnail(lllllIlIIIIlIIl);
         return lllllIlIIIIlIlI;
      }

      private class Field {
         // $FF: synthetic field
         private String name;
         // $FF: synthetic field
         private boolean inline;
         // $FF: synthetic field
         private String value;

         private String getValue() {
            return lllllIIIlIlIIIl.value;
         }

         // $FF: synthetic method
         Field(String lllllIIIIllIlll, String lllllIIIIllllII, boolean lllllIIIIlllIll, Object lllllIIIIlllIlI) {
            this(lllllIIIIllIlll, lllllIIIIllllII, lllllIIIIlllIll);
         }

         private String getName() {
            return lllllIIIlIlIlII.name;
         }

         private boolean isInline() {
            return lllllIIIlIIllll.inline;
         }

         private Field(String lllllIIIlIllllI, String lllllIIIlIlllIl, boolean lllllIIIlIlllII) {
            lllllIIIlIllIll.name = lllllIIIlIllllI;
            lllllIIIlIllIll.value = lllllIIIlIlllIl;
            lllllIIIlIllIll.inline = lllllIIIlIlllII;
         }
      }

      private class Thumbnail {
         // $FF: synthetic field
         private String url;

         private Thumbnail(String lllllIlIllIIIIl) {
            lllllIlIllIIIlI.url = lllllIlIllIIIIl;
         }

         private String getUrl() {
            return lllllIlIlIlllII.url;
         }

         // $FF: synthetic method
         Thumbnail(String lllllIlIlIlIIlI, Object lllllIlIlIlIIIl) {
            this(lllllIlIlIlIIlI);
         }
      }

      private class Image {
         // $FF: synthetic field
         private String url;

         private Image(String lllllIIlIllIllI) {
            lllllIIlIllIlIl.url = lllllIIlIllIllI;
         }

         // $FF: synthetic method
         Image(String lllllIIlIlIIlll, Object lllllIIlIlIIllI) {
            this(lllllIIlIlIIlll);
         }

         private String getUrl() {
            return lllllIIlIllIIII.url;
         }
      }

      private class Footer {
         // $FF: synthetic field
         private String text;
         // $FF: synthetic field
         private String iconUrl;

         private Footer(String lllllIIIIIIIllI, String lllllIIIIIIIlII) {
            lllllIIIIIIlIIl.text = lllllIIIIIIIllI;
            lllllIIIIIIlIIl.iconUrl = lllllIIIIIIIlII;
         }

         private String getIconUrl() {
            return llllIllllllIlII.iconUrl;
         }

         private String getText() {
            return llllIlllllllllI.text;
         }

         // $FF: synthetic method
         Footer(String llllIllllIlllII, String llllIlllllIIIII, Object llllIllllIlllll) {
            this(llllIllllIlllII, llllIlllllIIIII);
         }
      }

      private class Author {
         // $FF: synthetic field
         private String url;
         // $FF: synthetic field
         private String name;
         // $FF: synthetic field
         private String iconUrl;

         // $FF: synthetic method
         Author(String lllllIIlIllllIl, String lllllIIlIllllII, String lllllIIlIlllIll, Object lllllIIllIIIIII) {
            this(lllllIIlIllllIl, lllllIIlIllllII, lllllIIlIlllIll);
         }

         private String getUrl() {
            return lllllIIllIllIII.url;
         }

         private Author(String lllllIIllIlllll, String lllllIIlllIIIll, String lllllIIllIlllIl) {
            lllllIIlllIIlIl.name = lllllIIllIlllll;
            lllllIIlllIIlIl.url = lllllIIlllIIIll;
            lllllIIlllIIlIl.iconUrl = lllllIIllIlllIl;
         }

         private String getName() {
            return lllllIIllIllIlI.name;
         }

         private String getIconUrl() {
            return lllllIIllIlIlII.iconUrl;
         }
      }
   }

   private class JSONObject {
      // $FF: synthetic field
      private static final String[] lIllI;
      // $FF: synthetic field
      private static final int[] lIlIII;
      // $FF: synthetic field
      private final HashMap<String, Object> map;

      private static boolean llIIll(int var0) {
         return var0 != 0;
      }

      private static boolean llIIlI(Object var0) {
         return var0 != null;
      }

      private String quote(String llllIlllIllIIII) {
         return String.valueOf((new StringBuilder()).append(lIllI[lIlIII[8]]).append(llllIlllIllIIII).append(lIllI[lIlIII[9]]));
      }

      // $FF: synthetic method
      JSONObject(Object llllIlllIlIlIlI) {
         this();
      }

      private JSONObject() {
         llllIllllIlIlll.map = new HashMap();
      }

      public String toString() {
         boolean llllIlllIlllIlI = new StringBuilder();
         Set<Entry<String, Object>> llllIlllIlllIIl = llllIlllIllllll.map.entrySet();
         llllIlllIlllIlI.append(lIllI[lIlIII[0]]);
         "".length();
         short llllIlllIlllIII = lIlIII[0];
         Iterator llllIlllIllIlll = llllIlllIlllIIl.iterator();

         do {
            if (!llIIll(llllIlllIllIlll.hasNext())) {
               return String.valueOf(llllIlllIlllIlI);
            }

            Entry<String, Object> llllIllllIIIIII = (Entry)llllIlllIllIlll.next();
            boolean llllIlllIllIlIl = llllIllllIIIIII.getValue();
            llllIlllIlllIlI.append(llllIlllIllllll.quote((String)llllIllllIIIIII.getKey())).append(lIllI[lIlIII[1]]);
            "".length();
            String var10001;
            if (llIIll(llllIlllIllIlIl instanceof String)) {
               llllIlllIlllIlI.append(llllIlllIllllll.quote(String.valueOf(llllIlllIllIlIl)));
               "".length();
               "".length();
               if (((144 ^ 139) & ~(56 ^ 35)) != 0) {
                  return null;
               }
            } else if (llIIll(llllIlllIllIlIl instanceof Integer)) {
               llllIlllIlllIlI.append(Integer.valueOf(String.valueOf(llllIlllIllIlIl)));
               "".length();
               "".length();
               if (((22 ^ 98 ^ 83 ^ 97) & (180 ^ 167 ^ 24 ^ 77 ^ -" ".length())) < 0) {
                  return null;
               }
            } else if (llIIll(llllIlllIllIlIl instanceof Boolean)) {
               llllIlllIlllIlI.append(llllIlllIllIlIl);
               "".length();
               "".length();
               if (null != null) {
                  return null;
               }
            } else if (llIIll(llllIlllIllIlIl instanceof CommandUtils.JSONObject)) {
               llllIlllIlllIlI.append(llllIlllIllIlIl.toString());
               "".length();
               "".length();
               if ((138 ^ 142) <= 0) {
                  return null;
               }
            } else if (llIIll(llllIlllIllIlIl.getClass().isArray())) {
               llllIlllIlllIlI.append(lIllI[lIlIII[2]]);
               "".length();
               boolean llllIlllIllIlII = Array.getLength(llllIlllIllIlIl);
               int llllIllllIIIIll = lIlIII[0];

               while(llIlII(llllIllllIIIIll, llllIlllIllIlII)) {
                  StringBuilder var10000 = llllIlllIlllIlI.append(Array.get(llllIlllIllIlIl, llllIllllIIIIll).toString());
                  if (llIlIl(llllIllllIIIIll, llllIlllIllIlII - lIlIII[1])) {
                     var10001 = lIllI[lIlIII[3]];
                     "".length();
                     if (" ".length() != " ".length()) {
                        return null;
                     }
                  } else {
                     var10001 = lIllI[lIlIII[4]];
                  }

                  var10000.append(var10001);
                  "".length();
                  ++llllIllllIIIIll;
                  "".length();
                  if (null != null) {
                     return null;
                  }
               }

               llllIlllIlllIlI.append(lIllI[lIlIII[5]]);
               "".length();
            }

            ++llllIlllIlllIII;
            if (llIllI(llllIlllIlllIII, llllIlllIlllIIl.size())) {
               var10001 = lIllI[lIlIII[6]];
               "".length();
               if (" ".length() >= "  ".length()) {
                  return null;
               }
            } else {
               var10001 = lIllI[lIlIII[7]];
            }

            llllIlllIlllIlI.append(var10001);
            "".length();
            "".length();
         } while(((22 ^ 92) & ~(136 ^ 194)) == 0);

         return null;
      }

      private static void lIlllI() {
         lIllI = new String[lIlIII[10]];
         lIllI[lIlIII[0]] = lIIIll("8Zu7gy3m6mA=", "SRMgN");
         lIllI[lIlIII[1]] = lIlIIl("ew==", "ASJgG");
         lIllI[lIlIII[2]] = lIlIlI("sy/tze90Py8=", "VAgka");
         lIllI[lIlIII[3]] = lIlIlI("jUZM3RoBYpQ=", "vCYRU");
         lIllI[lIlIII[4]] = lIlIlI("1VvsAiIfEYg=", "XOHND");
         lIllI[lIlIII[5]] = lIlIIl("Pw==", "bsrcr");
         lIllI[lIlIII[6]] = lIlIlI("uF6QGHDqFoM=", "FHDKN");
         lIllI[lIlIII[7]] = lIIIll("mfGuhaH5Wg4=", "IXQxE");
         lIllI[lIlIII[8]] = lIlIlI("N/qyhiU27Zg=", "YLHlL");
         lIllI[lIlIII[9]] = lIIIll("oqGEXsxBXeU=", "OMMrv");
      }

      private static void llIIIl() {
         lIlIII = new int[11];
         lIlIII[0] = "   ".length() & ~"   ".length();
         lIlIII[1] = " ".length();
         lIlIII[2] = "  ".length();
         lIlIII[3] = "   ".length();
         lIlIII[4] = 68 + 109 - 107 + 84 ^ 75 + 3 - -20 + 60;
         lIlIII[5] = 23 + 140 - 127 + 144 ^ 114 + 102 - 118 + 79;
         lIlIII[6] = 56 ^ 62;
         lIlIII[7] = 215 ^ 133 ^ 232 ^ 189;
         lIlIII[8] = 183 ^ 191;
         lIlIII[9] = 18 + 163 - 108 + 129 ^ 24 + 64 - -64 + 43;
         lIlIII[10] = 26 ^ 16;
      }

      private static boolean llIlIl(int var0, int var1) {
         return var0 != var1;
      }

      private static boolean llIlII(int var0, int var1) {
         return var0 < var1;
      }

      private static String lIlIlI(String llllIllIllllIll, String llllIllIllllIII) {
         try {
            SecretKeySpec llllIllIllllllI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llllIllIllllIII.getBytes(StandardCharsets.UTF_8)), lIlIII[8]), "DES");
            byte llllIllIlllIllI = Cipher.getInstance("DES");
            llllIllIlllIllI.init(lIlIII[2], llllIllIllllllI);
            return new String(llllIllIlllIllI.doFinal(Base64.getDecoder().decode(llllIllIllllIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
         } catch (Exception var4) {
            var4.printStackTrace();
            return null;
         }
      }

      private static String lIIIll(String llllIlllIlIIIII, String llllIlllIIlllIl) {
         try {
            SecretKeySpec llllIlllIlIIIll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llllIlllIIlllIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher llllIlllIlIIIlI = Cipher.getInstance("Blowfish");
            llllIlllIlIIIlI.init(lIlIII[2], llllIlllIlIIIll);
            return new String(llllIlllIlIIIlI.doFinal(Base64.getDecoder().decode(llllIlllIlIIIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
         } catch (Exception var4) {
            var4.printStackTrace();
            return null;
         }
      }

      private static String lIlIIl(String llllIlllIIlIIII, String llllIlllIIIllll) {
         llllIlllIIlIIII = new String(Base64.getDecoder().decode(llllIlllIIlIIII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
         short llllIlllIIIlIIl = new StringBuilder();
         char[] llllIlllIIIllIl = llllIlllIIIllll.toCharArray();
         byte llllIlllIIIIlll = lIlIII[0];
         int llllIlllIIIIllI = llllIlllIIlIIII.toCharArray();
         long llllIlllIIIIlIl = llllIlllIIIIllI.length;
         int llllIlllIIIIlII = lIlIII[0];

         do {
            if (!llIlII(llllIlllIIIIlII, llllIlllIIIIlIl)) {
               return String.valueOf(llllIlllIIIlIIl);
            }

            char llllIlllIIlIIIl = llllIlllIIIIllI[llllIlllIIIIlII];
            llllIlllIIIlIIl.append((char)(llllIlllIIlIIIl ^ llllIlllIIIllIl[llllIlllIIIIlll % llllIlllIIIllIl.length]));
            "".length();
            ++llllIlllIIIIlll;
            ++llllIlllIIIIlII;
            "".length();
         } while("   ".length() >= -" ".length());

         return null;
      }

      void put(String llllIllllIIlllI, Object llllIllllIIllIl) {
         if (llIIlI(llllIllllIIllIl)) {
            llllIllllIIllll.map.put(llllIllllIIlllI, llllIllllIIllIl);
            "".length();
         }

      }

      static {
         llIIIl();
         lIlllI();
      }

      private static boolean llIllI(int var0, int var1) {
         return var0 == var1;
      }
   }
}

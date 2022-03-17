package org.spongepowered.asm.service.mojang;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.minecraft.launchwrapper.IClassNameTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.MixinEnvironment$Phase;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.service.IClassBytecodeProvider;
import org.spongepowered.asm.service.IClassProvider;
import org.spongepowered.asm.service.ILegacyClassTransformer;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.ITransformer;
import org.spongepowered.asm.util.ReEntranceLock;
import org.spongepowered.asm.util.perf.Profiler;
import org.spongepowered.asm.util.perf.Profiler$Section;

public class MixinServiceLaunchWrapper implements IMixinService, IClassProvider, IClassBytecodeProvider {
   public static final String BLACKBOARD_KEY_TWEAKCLASSES = "TweakClasses";
   public static final String BLACKBOARD_KEY_TWEAKS = "Tweaks";
   private static final String LAUNCH_PACKAGE = "org.spongepowered.asm.launch.";
   private static final String MIXIN_PACKAGE = "org.spongepowered.asm.mixin.";
   private static final String STATE_TWEAKER = "org.spongepowered.asm.mixin.EnvironmentStateTweaker";
   private static final String TRANSFORMER_PROXY_CLASS = "org.spongepowered.asm.mixin.transformer.Proxy";
   private static final Logger logger = LogManager.getLogger("mixin");
   private final LaunchClassLoaderUtil classLoaderUtil;
   private final ReEntranceLock lock;
   private IClassNameTransformer nameTransformer;

   public MixinServiceLaunchWrapper() {
      this.classLoaderUtil = new LaunchClassLoaderUtil(Launch.classLoader);
      this.lock = new ReEntranceLock(1);
   }

   public String getName() {
      return "LaunchWrapper";
   }

   public boolean isValid() {
      try {
         Launch.classLoader.hashCode();
         return true;
      } catch (Throwable var2) {
         return false;
      }
   }

   public void prepare() {
      Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.launch.");
   }

   public MixinEnvironment$Phase getInitialPhase() {
      try {
         if (findInStackTrace("net.minecraft.launchwrapper.Launch", "launch") > 132) {
            return MixinEnvironment$Phase.DEFAULT;
         }
      } catch (MixinException var1) {
         throw b(var1);
      }

      return MixinEnvironment$Phase.PREINIT;
   }

   public void init() {
      try {
         if (findInStackTrace("net.minecraft.launchwrapper.Launch", "launch") < 4) {
            logger.error("MixinBootstrap.doInit() called during a tweak constructor!");
         }
      } catch (MixinException var3) {
         throw b(var3);
      }

      List var1 = (List)GlobalProperties.get("TweakClasses");

      try {
         if (var1 != null) {
            var1.add("org.spongepowered.asm.mixin.EnvironmentStateTweaker");
         }

      } catch (MixinException var2) {
         throw b(var2);
      }
   }

   public ReEntranceLock getReEntranceLock() {
      return this.lock;
   }

   public Collection<String> getPlatformAgents() {
      return ImmutableList.of("org.spongepowered.asm.launch.platform.MixinPlatformAgentFML");
   }

   public IClassProvider getClassProvider() {
      return this;
   }

   public IClassBytecodeProvider getBytecodeProvider() {
      return this;
   }

   public Class<?> findClass(String var1) throws ClassNotFoundException {
      return Launch.classLoader.findClass(var1);
   }

   public Class<?> findClass(String var1, boolean var2) throws ClassNotFoundException {
      return Class.forName(var1, var2, Launch.classLoader);
   }

   public Class<?> findAgentClass(String var1, boolean var2) throws ClassNotFoundException {
      return Class.forName(var1, var2, Launch.class.getClassLoader());
   }

   public void beginPhase() {
      Launch.classLoader.registerTransformer("org.spongepowered.asm.mixin.transformer.Proxy");
   }

   public void checkEnv(Object var1) {
      try {
         if (var1.getClass().getClassLoader() != Launch.class.getClassLoader()) {
            throw new MixinException("Attempted to init the mixin environment in the wrong classloader");
         }
      } catch (MixinException var2) {
         throw b(var2);
      }
   }

   public InputStream getResourceAsStream(String var1) {
      return Launch.classLoader.getResourceAsStream(var1);
   }

   public void registerInvalidClass(String var1) {
      this.classLoaderUtil.registerInvalidClass(var1);
   }

   public boolean isClassLoaded(String var1) {
      return this.classLoaderUtil.isClassLoaded(var1);
   }

   public String getClassRestrictions(String param1) {
      // $FF: Couldn't be decompiled
   }

   public URL[] getClassPath() {
      return (URL[])Launch.classLoader.getSources().toArray(new URL[0]);
   }

   public Collection<ITransformer> getTransformers() {
      List var1 = Launch.classLoader.getTransformers();
      ArrayList var2 = new ArrayList(var1.size());
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         IClassTransformer var4 = (IClassTransformer)var3.next();

         label30: {
            try {
               if (var4 instanceof ITransformer) {
                  var2.add((ITransformer)var4);
                  break label30;
               }
            } catch (MixinException var6) {
               throw b(var6);
            }

            var2.add(new LegacyTransformerHandle(var4));
         }

         try {
            if (var4 instanceof IClassNameTransformer) {
               logger.debug("Found name transformer: {}", new Object[]{var4.getClass().getName()});
               this.nameTransformer = (IClassNameTransformer)var4;
            }
         } catch (MixinException var5) {
            throw b(var5);
         }
      }

      return var2;
   }

   public byte[] getClassBytes(String var1, String var2) throws IOException {
      byte[] var3 = Launch.classLoader.getClassBytes(var1);

      try {
         if (var3 != null) {
            return var3;
         }
      } catch (Exception var14) {
         throw b(var14);
      }

      URLClassLoader var4 = (URLClassLoader)Launch.class.getClassLoader();
      InputStream var5 = null;

      Object var7;
      try {
         String var6 = var2.replace('.', '/').concat(".class");
         var5 = var4.getResourceAsStream(var6);
         byte[] var15 = IOUtils.toByteArray(var5);
         return var15;
      } catch (Exception var12) {
         var7 = null;
      } finally {
         IOUtils.closeQuietly(var5);
      }

      return (byte[])var7;
   }

   public byte[] getClassBytes(String var1, boolean var2) throws ClassNotFoundException, IOException {
      String var3 = var1.replace('/', '.');
      String var4 = this.unmapClassName(var3);
      Profiler var5 = MixinEnvironment.getProfiler();
      Profiler$Section var6 = var5.begin(1, (String)"class.load");
      byte[] var7 = this.getClassBytes(var4, var3);
      var6.end();
      if (var2) {
         Profiler$Section var8 = var5.begin(1, (String)"class.transform");
         var7 = this.applyTransformers(var4, var3, var7, var5);
         var8.end();
      }

      try {
         if (var7 == null) {
            throw new ClassNotFoundException(String.format("The specified class '%s' was not found", var3));
         } else {
            return var7;
         }
      } catch (ClassNotFoundException var9) {
         throw b(var9);
      }
   }

   private byte[] applyTransformers(String var1, String var2, byte[] var3, Profiler var4) {
      try {
         if (this.classLoaderUtil.isClassExcluded(var1, var2)) {
            return var3;
         }
      } catch (MixinException var12) {
         throw b(var12);
      }

      MixinEnvironment var5 = MixinEnvironment.getCurrentEnvironment();
      Iterator var6 = var5.getTransformers().iterator();

      while(var6.hasNext()) {
         ILegacyClassTransformer var7 = (ILegacyClassTransformer)var6.next();
         this.lock.clear();
         int var8 = var7.getName().lastIndexOf(46);
         String var9 = var7.getName().substring(var8 + 1);
         Profiler$Section var10 = var4.begin(2, (String)var9.toLowerCase());
         var10.setInfo(var7.getName());
         var3 = var7.transformClassBytes(var1, var2, var3);

         try {
            var10.end();
            if (this.lock.isSet()) {
               var5.addTransformerExclusion(var7.getName());
               this.lock.clear();
               logger.info("A re-entrant transformer '{}' was detected and will no longer process meta class data", new Object[]{var7.getName()});
            }
         } catch (MixinException var11) {
            throw b(var11);
         }
      }

      return var3;
   }

   private String unmapClassName(String var1) {
      try {
         if (this.nameTransformer == null) {
            this.findNameTransformer();
         }
      } catch (MixinException var3) {
         throw b(var3);
      }

      try {
         return this.nameTransformer != null ? this.nameTransformer.unmapClassName(var1) : var1;
      } catch (MixinException var2) {
         throw b(var2);
      }
   }

   private void findNameTransformer() {
      List var1 = Launch.classLoader.getTransformers();
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         IClassTransformer var3 = (IClassTransformer)var2.next();

         try {
            if (var3 instanceof IClassNameTransformer) {
               logger.debug("Found name transformer: {}", new Object[]{var3.getClass().getName()});
               this.nameTransformer = (IClassNameTransformer)var3;
            }
         } catch (MixinException var4) {
            throw b(var4);
         }
      }

   }

   public ClassNode getClassNode(String var1) throws ClassNotFoundException, IOException {
      return this.getClassNode(this.getClassBytes(var1, true), 0);
   }

   private ClassNode getClassNode(byte[] var1, int var2) {
      ClassNode var3 = new ClassNode();
      ClassReader var4 = new ClassReader(var1);
      var4.accept(var3, var2);
      return var3;
   }

   public final String getSideName() {
      Iterator var1 = ((List)GlobalProperties.get("Tweaks")).iterator();

      while(var1.hasNext()) {
         ITweaker var2 = (ITweaker)var1.next();

         try {
            if (var2.getClass().getName().endsWith(".common.launcher.FMLServerTweaker")) {
               return "SERVER";
            }
         } catch (MixinException var7) {
            throw b(var7);
         }

         try {
            if (var2.getClass().getName().endsWith(".common.launcher.FMLTweaker")) {
               return "CLIENT";
            }
         } catch (MixinException var4) {
            throw b(var4);
         }
      }

      String var8 = this.getSideName("net.minecraftforge.fml.relauncher.FMLLaunchHandler", "side");

      try {
         if (var8 != null) {
            return var8;
         }
      } catch (MixinException var6) {
         throw b(var6);
      }

      var8 = this.getSideName("cpw.mods.fml.relauncher.FMLLaunchHandler", "side");

      try {
         if (var8 != null) {
            return var8;
         }
      } catch (MixinException var5) {
         throw b(var5);
      }

      var8 = this.getSideName("com.mumfrey.liteloader.launch.LiteLoaderTweaker", "getEnvironmentType");

      try {
         if (var8 != null) {
            return var8;
         } else {
            return "UNKNOWN";
         }
      } catch (MixinException var3) {
         throw b(var3);
      }
   }

   private String getSideName(String var1, String var2) {
      try {
         Class var3 = Class.forName(var1, false, Launch.classLoader);
         Method var4 = var3.getDeclaredMethod(var2);
         return ((Enum)var4.invoke((Object)null)).name();
      } catch (Exception var5) {
         return null;
      }
   }

   private static int findInStackTrace(String param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}

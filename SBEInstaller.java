import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Locale;
import java.util.Objects;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.Highlighter;

public class SBEInstaller extends JFrame implements ActionListener, MouseListener {
   private static final Pattern IN_MODS_SUBFOLDER = Pattern.compile("1\\.8\\.9[/\\\\]?$");
   private JLabel logo = null;
   private JLabel versionInfo = null;
   private JLabel labelFolder = null;
   private JLabel sbeKey = null;
   private JPanel panelCenter = null;
   private JPanel panelBottom = null;
   private JPanel totalContentPane = null;
   private JTextArea descriptionText = null;
   private JTextArea forgeDescriptionText = null;
   private JTextField textFieldFolderLocation = null;
   private JTextField keyField = null;
   private JButton buttonChooseFolder = null;
   private JButton buttonInstall = null;
   private JButton buttonOpenFolder = null;
   private JButton buttonClose = null;
   private static final int TOTAL_HEIGHT = 485;
   private static final int TOTAL_WIDTH = 404;
   private int x = 0;
   private int y = 0;
   private int w = 404;
   private int h;
   private int margin;

   public SBEInstaller() {
      try {
         this.setName("SBEInstaller");
         this.setTitle("Install Skyblock Extras");
         this.setResizable(false);
         this.setSize(404, 485);
         this.setContentPane(this.getPanelContentPane());
         this.getButtonFolder().addActionListener(this);
         this.getButtonInstall().addActionListener(this);
         this.getButtonOpenFolder().addActionListener(this);
         this.getButtonClose().addActionListener(this);
         this.getForgeTextArea().addMouseListener(this);
         this.pack();
         this.setDefaultCloseOperation(3);
         this.getFieldFolder().setText(this.getModsFolder().getPath());
         this.getButtonInstall().setEnabled(true);
         this.getButtonInstall().requestFocus();
      } catch (Exception var2) {
         showErrorPopup(var2);
      }

   }

   public static void main(String[] args) {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
         SBEInstaller frame = new SBEInstaller();
         frame.centerFrame(frame);
         frame.setVisible(true);
      } catch (Exception var2) {
         showErrorPopup(var2);
      }

   }

   private JPanel getPanelContentPane() {
      if (this.totalContentPane == null) {
         try {
            this.totalContentPane = new JPanel();
            this.totalContentPane.setName("PanelContentPane");
            this.totalContentPane.setLayout(new BorderLayout(5, 5));
            this.totalContentPane.setPreferredSize(new Dimension(404, 485));
            this.totalContentPane.add(this.getPanelCenter(), "Center");
            this.totalContentPane.add(this.getPanelBottom(), "South");
         } catch (Throwable var2) {
            showErrorPopup(var2);
         }
      }

      return this.totalContentPane;
   }

   private JPanel getPanelCenter() {
      if (this.panelCenter == null) {
         try {
            (this.panelCenter = new JPanel()).setName("PanelCenter");
            this.panelCenter.setLayout((LayoutManager)null);
            this.panelCenter.add(this.getPictureLabel(), this.getPictureLabel().getName());
            this.panelCenter.add(this.getVersionInfo(), this.getVersionInfo().getName());
            this.panelCenter.add(this.getTextArea(), this.getTextArea().getName());
            this.panelCenter.add(this.getForgeTextArea(), this.getForgeTextArea().getName());
            this.panelCenter.add(this.getSBEKey(), this.getSBEKey().getName());
            this.panelCenter.add(this.getKeyField(), this.getKeyField().getName());
            this.panelCenter.add(this.getLabelFolder(), this.getLabelFolder().getName());
            this.panelCenter.add(this.getFieldFolder(), this.getFieldFolder().getName());
            this.panelCenter.add(this.getButtonFolder(), this.getButtonFolder().getName());
         } catch (Throwable var2) {
            showErrorPopup(var2);
         }
      }

      return this.panelCenter;
   }

   private JLabel getPictureLabel() {
      if (this.logo == null) {
         try {
            this.h = this.w / 2;
            this.margin = 5;
            BufferedImage myPicture = ImageIO.read((InputStream)Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("assets/skyblockextras/banner.png"), "Banner not found."));
            Image scaled = myPicture.getScaledInstance(this.w - this.margin * 2, this.h - this.margin, 4);
            this.logo = new JLabel(new ImageIcon(scaled));
            this.logo.setName("Logo");
            this.logo.setBounds(this.x + this.margin, this.y + this.margin, this.w - this.margin * 2, this.h - this.margin);
            this.logo.setFont(new Font("Dialog", 1, 18));
            this.logo.setHorizontalAlignment(0);
            this.logo.setPreferredSize(new Dimension(this.w, this.h));
            this.y += this.h;
         } catch (Throwable var3) {
            showErrorPopup(var3);
         }
      }

      return this.logo;
   }

   private JLabel getVersionInfo() {
      if (this.versionInfo == null) {
         try {
            this.h = 25;
            this.versionInfo = new JLabel();
            this.versionInfo.setName("LabelMcVersion");
            this.versionInfo.setBounds(this.x, this.y, this.w, this.h);
            this.versionInfo.setFont(new Font("Dialog", 1, 14));
            this.versionInfo.setHorizontalAlignment(0);
            this.versionInfo.setPreferredSize(new Dimension(this.w, this.h));
            this.versionInfo.setText("v" + this.getVersionFromMcmodInfo() + " by AlphaElite - Installer made by Biscuit");
            this.y += this.h;
         } catch (Throwable var2) {
            showErrorPopup(var2);
         }
      }

      return this.versionInfo;
   }

   private JTextArea getTextArea() {
      if (this.descriptionText == null) {
         try {
            this.h = 60;
            this.margin = 10;
            this.descriptionText = new JTextArea();
            this.descriptionText.setName("TextArea");
            this.setTextAreaProperties(this.descriptionText);
            this.descriptionText.setText("This installer will automatically install Skyblock Extras on your computer. You will also need to input a Skyblock Extras Key (obtainable via the SBE Discord server).");
            this.descriptionText.setWrapStyleWord(true);
            this.y += this.h;
         } catch (Throwable var2) {
            showErrorPopup(var2);
         }
      }

      return this.descriptionText;
   }

   private void setTextAreaProperties(JTextArea textArea) {
      textArea.setBounds(this.x + this.margin, this.y + this.margin, this.w - this.margin * 2, this.h - this.margin);
      textArea.setEditable(false);
      textArea.setHighlighter((Highlighter)null);
      textArea.setEnabled(true);
      textArea.setFont(new Font("Dialog", 0, 12));
      textArea.setLineWrap(true);
      textArea.setOpaque(false);
      textArea.setPreferredSize(new Dimension(this.w - this.margin * 2, this.h - this.margin));
   }

   private JTextArea getForgeTextArea() {
      if (this.forgeDescriptionText == null) {
         try {
            this.h = 65;
            this.margin = 10;
            this.forgeDescriptionText = new JTextArea();
            this.forgeDescriptionText.setName("TextAreaForge");
            this.setTextAreaProperties(this.forgeDescriptionText);
            this.forgeDescriptionText.setText("However, you still need to install the newest Forge version in order to be able to run this mod. Click here to visit the download page for Forge 1.8.9!");
            this.forgeDescriptionText.setForeground(Color.BLUE.darker());
            this.forgeDescriptionText.setCursor(Cursor.getPredefinedCursor(12));
            this.forgeDescriptionText.setWrapStyleWord(true);
            this.y += this.h + this.margin;
         } catch (Throwable var2) {
            showErrorPopup(var2);
         }
      }

      return this.forgeDescriptionText;
   }

   private JLabel getSBEKey() {
      if (this.sbeKey == null) {
         this.h = 16;
         this.w = 65;
         this.x += 10;

         try {
            this.sbeKey = new JLabel();
            this.sbeKey.setName("SbeKey");
            this.sbeKey.setBounds(this.x, this.y + 2, this.w, this.h);
            this.sbeKey.setPreferredSize(new Dimension(this.w, this.h));
            this.sbeKey.setText("SBE Key");
         } catch (Throwable var2) {
            showErrorPopup(var2);
         }

         this.x += this.w;
      }

      return this.sbeKey;
   }

   private JTextField getKeyField() {
      if (this.keyField == null) {
         this.h = 20;
         this.w = 200;

         try {
            this.keyField = new JTextField("Enter Key Here (Initial Installation)");
            this.keyField.setName("KeyField");
            this.keyField.setBounds(this.x, this.y, this.w, this.h);
            this.keyField.setEditable(true);
            this.keyField.setPreferredSize(new Dimension(this.w, this.h));
            this.keyField.setFont(new Font(this.keyField.getFont().getName(), 2, this.keyField.getFont().getSize()));
         } catch (Throwable var2) {
            showErrorPopup(var2);
         }

         this.x += this.w;
         this.y += this.h + 10;
      }

      return this.keyField;
   }

   private JLabel getLabelFolder() {
      if (this.labelFolder == null) {
         this.h = 16;
         this.w = 65;
         this.x = 0;
         this.x += 10;

         try {
            this.labelFolder = new JLabel();
            this.labelFolder.setName("LabelFolder");
            this.labelFolder.setBounds(this.x, this.y + 2, this.w, this.h);
            this.labelFolder.setPreferredSize(new Dimension(this.w, this.h));
            this.labelFolder.setText("Mods Folder");
         } catch (Throwable var2) {
            showErrorPopup(var2);
         }

         this.x += this.w;
      }

      return this.labelFolder;
   }

   private JTextField getFieldFolder() {
      if (this.textFieldFolderLocation == null) {
         this.h = 20;
         this.w = 287;

         try {
            this.textFieldFolderLocation = new JTextField();
            this.textFieldFolderLocation.setName("FieldFolder");
            this.textFieldFolderLocation.setBounds(this.x, this.y, this.w, this.h);
            this.textFieldFolderLocation.setEditable(false);
            this.textFieldFolderLocation.setPreferredSize(new Dimension(this.w, this.h));
         } catch (Throwable var2) {
            showErrorPopup(var2);
         }

         this.x += this.w;
      }

      return this.textFieldFolderLocation;
   }

   private JButton getButtonFolder() {
      if (this.buttonChooseFolder == null) {
         this.h = 20;
         this.w = 25;
         this.x += 10;

         try {
            BufferedImage myPicture = ImageIO.read((InputStream)Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("assets/skyblockextras/folder.png"), "Folder icon not found."));
            Image scaled = myPicture.getScaledInstance(this.w - 8, this.h - 6, 4);
            this.buttonChooseFolder = new JButton(new ImageIcon(scaled));
            this.buttonChooseFolder.setName("ButtonFolder");
            this.buttonChooseFolder.setBounds(this.x, this.y, this.w, this.h);
            this.buttonChooseFolder.setPreferredSize(new Dimension(this.w, this.h));
         } catch (Throwable var3) {
            showErrorPopup(var3);
         }
      }

      return this.buttonChooseFolder;
   }

   private JPanel getPanelBottom() {
      if (this.panelBottom == null) {
         try {
            this.panelBottom = new JPanel();
            this.panelBottom.setName("PanelBottom");
            this.panelBottom.setLayout(new FlowLayout(1, 15, 10));
            this.panelBottom.setPreferredSize(new Dimension(390, 55));
            this.panelBottom.add(this.getButtonInstall(), this.getButtonInstall().getName());
            this.panelBottom.add(this.getButtonOpenFolder(), this.getButtonOpenFolder().getName());
            this.panelBottom.add(this.getButtonClose(), this.getButtonClose().getName());
         } catch (Throwable var2) {
            showErrorPopup(var2);
         }
      }

      return this.panelBottom;
   }

   private JButton getButtonInstall() {
      if (this.buttonInstall == null) {
         this.w = 100;
         this.h = 26;

         try {
            this.buttonInstall = new JButton();
            this.buttonInstall.setName("ButtonInstall");
            this.buttonInstall.setPreferredSize(new Dimension(this.w, this.h));
            this.buttonInstall.setText("Install");
         } catch (Throwable var2) {
            showErrorPopup(var2);
         }
      }

      return this.buttonInstall;
   }

   private JButton getButtonOpenFolder() {
      if (this.buttonOpenFolder == null) {
         this.w = 130;
         this.h = 26;

         try {
            this.buttonOpenFolder = new JButton();
            this.buttonOpenFolder.setName("ButtonOpenFolder");
            this.buttonOpenFolder.setPreferredSize(new Dimension(this.w, this.h));
            this.buttonOpenFolder.setText("Open Mods Folder");
         } catch (Throwable var2) {
            showErrorPopup(var2);
         }
      }

      return this.buttonOpenFolder;
   }

   private JButton getButtonClose() {
      if (this.buttonClose == null) {
         this.w = 100;
         this.h = 26;

         try {
            (this.buttonClose = new JButton()).setName("ButtonClose");
            this.buttonClose.setPreferredSize(new Dimension(this.w, this.h));
            this.buttonClose.setText("Cancel");
         } catch (Throwable var2) {
            showErrorPopup(var2);
         }
      }

      return this.buttonClose;
   }

   public void onFolderSelect() {
      File currentDirectory = new File(this.getFieldFolder().getText());
      JFileChooser jFileChooser = new JFileChooser(currentDirectory);
      jFileChooser.setFileSelectionMode(1);
      jFileChooser.setAcceptAllFileFilterUsed(false);
      if (jFileChooser.showOpenDialog(this) == 0) {
         File newDirectory = jFileChooser.getSelectedFile();
         this.getFieldFolder().setText(newDirectory.getPath());
      }

   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == this.getButtonClose()) {
         this.dispose();
         System.exit(0);
      }

      if (e.getSource() == this.getButtonFolder()) {
         this.onFolderSelect();
      }

      if (e.getSource() == this.getButtonInstall()) {
         this.onInstall();
      }

      if (e.getSource() == this.getButtonOpenFolder()) {
         this.onOpenFolder();
      }

   }

   public void mouseClicked(MouseEvent e) {
      if (e.getSource() == this.getForgeTextArea()) {
         try {
            Desktop.getDesktop().browse(new URI("http://files.minecraftforge.net/maven/net/minecraftforge/forge/index_1.8.9.html"));
         } catch (URISyntaxException | IOException var3) {
            showErrorPopup(var3);
         }
      } else if (e.getSource() == this.getKeyField()) {
         this.keyField.requestFocusInWindow();
      }

   }

   public void onInstall() {
      try {
         File modsFolder = new File(this.getFieldFolder().getText());
         if (!modsFolder.exists()) {
            this.showErrorMessage("Folder not found: " + modsFolder.getPath());
            return;
         }

         if (!modsFolder.isDirectory()) {
            this.showErrorMessage("Not a folder: " + modsFolder.getPath());
            return;
         }

         this.tryInstall(modsFolder);
      } catch (Exception var2) {
         showErrorPopup(var2);
      }

   }

   private void tryInstall(File modsFolder) {
      File thisFile = this.getThisFile();
      if (thisFile != null) {
         File newFile = new File(modsFolder, "SkyblockExtras-" + this.getVersionFromMcmodInfo() + ".jar");
         File config = new File(newFile.toString().replaceAll("\\\\mods.*", "\\\\config") + "\\SkyblockExtras.cfg");
         boolean exists;
         if ((exists = !config.exists()) && this.keyField.getText().length() != 30) {
            this.showErrorMessage("Invalid SBE Key!");
            return;
         }

         boolean inSubFolder = IN_MODS_SUBFOLDER.matcher(modsFolder.getPath()).find();
         if (thisFile.equals(newFile)) {
            this.showErrorMessage("You are opening this file from where the file should be installed... there's nothing to be done!");
            return;
         }

         boolean deletingFailure = false;
         boolean failed;
         if (modsFolder.isDirectory()) {
            failed = this.findSkyblockExtrasAndDelete(modsFolder.listFiles());
            if (failed) {
               deletingFailure = true;
            }
         }

         if (inSubFolder) {
            if (modsFolder.getParentFile().isDirectory()) {
               failed = this.findSkyblockExtrasAndDelete(modsFolder.getParentFile().listFiles());
               if (failed) {
                  deletingFailure = true;
               }
            }
         } else {
            File subFolder = new File(modsFolder, "1.8.9");
            if (subFolder.exists() && subFolder.isDirectory()) {
               boolean failed = this.findSkyblockExtrasAndDelete(subFolder.listFiles());
               if (failed) {
                  deletingFailure = true;
               }
            }
         }

         if (deletingFailure) {
            return;
         }

         if (thisFile.isDirectory()) {
            this.showErrorMessage("This file is a directory... Are we in a development environment?");
            return;
         }

         try {
            Files.copy(thisFile.toPath(), newFile.toPath());
            if (exists) {
               PrintWriter writer = new PrintWriter(config);
               writer.println("SBEKey=" + this.keyField.getText());
               writer.close();
            } else {
               String text = this.keyField.getText();
               if (text.length() == 30 && !text.contains(" ")) {
                  BufferedReader reader = new BufferedReader(new FileReader(config));
                  StringBuilder configLines = new StringBuilder();
                  reader.lines().forEach((line) -> {
                     configLines.append(line.trim());
                  });
                  reader.close();
                  JsonObject obj = (new JsonParser()).parse(configLines.toString()).getAsJsonObject();
                  obj.getAsJsonObject("values").get("others").getAsJsonArray().add(new JsonPrimitive(text));
                  String s = (new GsonBuilder()).setPrettyPrinting().create().toJson(obj);
                  PrintWriter writer = new PrintWriter(config);
                  writer.println(s);
                  writer.close();
               }
            }
         } catch (Exception var14) {
            showErrorPopup(var14);
            return;
         }

         this.showMessage("Skyblock Extras has been successfully installed into your mods folder.");
         this.dispose();
         System.exit(0);
      }

   }

   private boolean findSkyblockExtrasAndDelete(File[] files) {
      if (files == null) {
         return false;
      } else {
         File[] var2 = files;
         int var3 = files.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            File file = var2[var4];
            if (!file.isDirectory() && file.getPath().endsWith(".jar")) {
               try {
                  JarFile jarFile = new JarFile(file);
                  ZipEntry mcModInfo = jarFile.getEntry("mcmod.info");
                  if (mcModInfo != null) {
                     InputStream inputStream = jarFile.getInputStream(mcModInfo);
                     String modID = this.getModIDFromInputStream(inputStream);
                     if (modID.equals("SkyblockExtras")) {
                        jarFile.close();

                        try {
                           boolean deleted = file.delete();
                           if (!deleted) {
                              throw new Exception();
                           }
                           continue;
                        } catch (Exception var11) {
                           var11.printStackTrace();
                           this.showErrorMessage("Was not able to delete the other Skyblock Extras files found in your mods folder!" + System.lineSeparator() + "Please make sure that your minecraft is currently closed and try again, or feel" + System.lineSeparator() + "free to open your mods folder and delete those files manually.");
                           return true;
                        }
                     }
                  }

                  jarFile.close();
               } catch (Exception var12) {
               }
            }
         }

         return false;
      }
   }

   public void onOpenFolder() {
      try {
         Desktop.getDesktop().open(this.getModsFolder());
      } catch (Exception var2) {
         showErrorPopup(var2);
      }

   }

   public File getModsFolder() {
      String userHome = System.getProperty("user.home", ".");
      File modsFolder = this.getFile(userHome, "minecraft/mods/1.8.9");
      if (!modsFolder.exists()) {
         modsFolder = this.getFile(userHome, "minecraft/mods");
      }

      if (!modsFolder.exists() && !modsFolder.mkdirs()) {
         throw new RuntimeException("The working directory could not be created: " + modsFolder);
      } else {
         return modsFolder;
      }
   }

   public File getFile(String userHome, String minecraftPath) {
      File workingDirectory;
      switch(this.getOperatingSystem()) {
      case LINUX:
      case SOLARIS:
         workingDirectory = new File(userHome, '.' + minecraftPath + '/');
         break;
      case WINDOWS:
         String applicationData = System.getenv("APPDATA");
         if (applicationData != null) {
            workingDirectory = new File(applicationData, "." + minecraftPath + '/');
         } else {
            workingDirectory = new File(userHome, '.' + minecraftPath + '/');
         }
         break;
      case MACOS:
         workingDirectory = new File(userHome, "Library/Application Support/" + minecraftPath);
         break;
      default:
         workingDirectory = new File(userHome, minecraftPath + '/');
      }

      return workingDirectory;
   }

   public SBEInstaller.OperatingSystem getOperatingSystem() {
      String osName = System.getProperty("os.name").toLowerCase(Locale.US);
      if (osName.contains("win")) {
         return SBEInstaller.OperatingSystem.WINDOWS;
      } else if (osName.contains("mac")) {
         return SBEInstaller.OperatingSystem.MACOS;
      } else if (!osName.contains("solaris") && !osName.contains("sunos")) {
         return !osName.contains("linux") && !osName.contains("unix") ? SBEInstaller.OperatingSystem.UNKNOWN : SBEInstaller.OperatingSystem.LINUX;
      } else {
         return SBEInstaller.OperatingSystem.SOLARIS;
      }
   }

   public void centerFrame(JFrame frame) {
      Rectangle rectangle = frame.getBounds();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Rectangle screenRectangle = new Rectangle(0, 0, screenSize.width, screenSize.height);
      int newX = screenRectangle.x + (screenRectangle.width - rectangle.width) / 2;
      int newY = screenRectangle.y + (screenRectangle.height - rectangle.height) / 2;
      if (newX < 0) {
         newX = 0;
      }

      if (newY < 0) {
         newY = 0;
      }

      frame.setBounds(newX, newY, rectangle.width, rectangle.height);
   }

   public void showMessage(String message) {
      JOptionPane.showMessageDialog((Component)null, message, "Skyblock Extras", 1);
   }

   public void showErrorMessage(String message) {
      JOptionPane.showMessageDialog((Component)null, message, "Skyblock Extras - Error", 0);
   }

   private static String getStacktraceText(Throwable ex) {
      StringWriter stringWriter = new StringWriter();
      ex.printStackTrace(new PrintWriter(stringWriter));
      return stringWriter.toString().replace("\t", "  ");
   }

   private static void showErrorPopup(Throwable ex) {
      ex.printStackTrace();
      JTextArea textArea = new JTextArea("You may need to install the mod manually:\n\n" + getStacktraceText(ex));
      textArea.setEditable(false);
      Font currentFont = textArea.getFont();
      Font newFont = new Font("Monospaced", currentFont.getStyle(), currentFont.getSize());
      textArea.setFont(newFont);
      JScrollPane errorScrollPane = new JScrollPane(textArea);
      errorScrollPane.setPreferredSize(new Dimension(600, 400));
      JOptionPane.showMessageDialog((Component)null, errorScrollPane, "Error", 0);
   }

   private String getVersionFromMcmodInfo() {
      String version = "";

      try {
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((InputStream)Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("mcmod.info"), "mcmod.info not found.")));

         while((version = bufferedReader.readLine()) != null) {
            if (version.contains("\"version\": \"")) {
               version = version.split(Pattern.quote("\"version\": \""))[1];
               version = version.substring(0, version.length() - 2);
               break;
            }
         }

         bufferedReader.close();
      } catch (Exception var3) {
      }

      return version;
   }

   private String getModIDFromInputStream(InputStream inputStream) {
      String version = "";

      try {
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

         while((version = bufferedReader.readLine()) != null) {
            if (version.contains("\"modid\": \"")) {
               version = version.split(Pattern.quote("\"modid\": \""))[1];
               version = version.substring(0, version.length() - 2);
               break;
            }
         }

         bufferedReader.close();
      } catch (Exception var4) {
      }

      return version;
   }

   private File getThisFile() {
      try {
         return new File(SBEInstaller.class.getProtectionDomain().getCodeSource().getLocation().toURI());
      } catch (URISyntaxException var2) {
         showErrorPopup(var2);
         return null;
      }
   }

   public void mousePressed(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public static enum OperatingSystem {
      LINUX,
      SOLARIS,
      WINDOWS,
      MACOS,
      UNKNOWN;
   }
}

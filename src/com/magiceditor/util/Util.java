package com.magiceditor.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import com.magiceditor.file.ExtensionsFilter;
import com.magiceditor.file.MyFileOpened;

import lombok.Getter;

/**
 * 
 * @author Fabricio Braga
 *
 */
public class Util {

	private static Util instance;

	private static ResourceBundle messages;
	private static Locale currentLocale;

	@Getter
	private static List<MyFileOpened> fileOpenList;

	private Util() {
	}

	public synchronized static Util getInstance() {
		if (instance == null) {
			instance = new Util();
			currentLocale = new Locale("en", "US");
			messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
			fileOpenList = new ArrayList<MyFileOpened>();
		}
		return instance;
	}

	public int getScreenWidth() {
		return (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	}

	public int getScreenHeight() {
		return (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	}

	public String getString(String key) {
		return messages.getString(key);
	}

	public static String file2String(String path) {
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			String result = new String(encoded, StandardCharsets.ISO_8859_1);
			
			//avoiding JTextPane problem with empty styles:
			if(result.isEmpty()) {
				result += " ";
			}
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			return "no file loaded: " + e.toString();
		}
	}
	
	public String[] loadFileFilter() {
		return getString("config.files.accepted").split(",");
		
	}
	
	public static void save2File(Path path, String text) {
		try {
			Files.write(path, text.getBytes(StandardCharsets.ISO_8859_1), StandardOpenOption.TRUNCATE_EXISTING);
		}catch(Exception e) {
			handleError(e);
		}
	}
	
	public static ImageIcon createImageIcon(String path, int width, int height) {
        java.net.URL imgURL = Util.class.getResource(path);
        if (imgURL != null) {
        return new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
	
	public static InputStream loadDefaultFont() {
		InputStream is = Util.class.getResourceAsStream("fonts/courbd.ttf");
		return is;
    }
	
	public static File getAppDataFolder() {
		
		try {
			//first we check/create if app folder exists:
			File appFolder = new File(System.getenv("LOCALAPPDATA") + File.separator + EditorConstants.APP_DATA_FOLDER);
			if(!appFolder.exists()) {
				if(appFolder.mkdir()) {
					//System.out.println("creating app data folder:" + appFolder.getAbsolutePath());
					return appFolder;
				}else {
					handleError("We found a problem creating our base folder.");
				}
			}else {
				return appFolder;
			}
		}catch(Exception e) {
			handleError(e);
		}
		return null;
	}
	
	public static boolean isFileOpened(File file) {
		for(MyFileOpened f : getFileOpenList()) {
			if(file.equals(f.getFile())) {
				return true;
			}
		}
		return false;
	}
	
	public static ImageIcon loadTabIcon(String iconName){
		return createImageIcon("icons/" + iconName, 15, 15);
	}
	
	public static ImageIcon loadToolBarIcon(String iconName){
		return createImageIcon("icons/" + iconName, 15, 15);
	}
	
	public static DefaultMutableTreeNode loadSystemNodes() {
		
		File root = new File(File.separator);
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(root.getAbsolutePath());
		
		for(File folder : root.listFiles()) {
			if(folder.isDirectory() && folder.canRead()) {
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(folder.getName());
				top.add(child);
				try {
					if(folder.listFiles().length > 0) {
						child.add(new DefaultMutableTreeNode(folder.listFiles()[0].getName()));
					}
				}catch(NullPointerException e) {
					//do nothing, because nullpointer here could mean a permission denied...
					//removing folder:
					((MutableTreeNode)child.getParent()).remove(child);
				}
			}
		}
		return top;
	}
	
	public static void readOnlySubFolders(DefaultMutableTreeNode root, File folder) {
		try {
			root.removeAllChildren();
			if(folder.isDirectory() && folder.canRead() && folder != null) {
				for(File f : folder.listFiles(new ExtensionsFilter())) {
					//if(f.isDirectory()) {
						DefaultMutableTreeNode child = new DefaultMutableTreeNode(f.getName());
						root.add(child);
						try {
							if(f.listFiles(new ExtensionsFilter()).length > 0) {
								child.add(new DefaultMutableTreeNode(f.listFiles()[0].getName()));
							}
						}catch(NullPointerException e) {
							//do nothing, because nullpointer here could mean a permission denied...
						}
					//}
				}
			}
		}catch(NullPointerException e) {
			//do nothing, because nullpointer here could mean a permission denied...
		}
	}
	
	public static String[] getListOfFilesAccepted() {
		return getInstance().getString("config.files.accepted").split(",");
	}
	
	public static void handleError(String message) {
		System.out.println(message);
	}
	
	public static void handleError(Exception e) {
		e.printStackTrace();
	}

}

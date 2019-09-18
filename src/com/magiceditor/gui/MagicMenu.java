package com.magiceditor.gui;

import java.awt.Font;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.magiceditor.util.Util;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Fabricio Braga
 *
 */
@Getter
@Setter
public class MagicMenu extends JMenuBar{

	// file, #1
	private JMenu menuFile;
	private JMenuItem open;
	private JMenuItem save;
	private JMenuItem saveAll;
	private JMenuItem quit;
	
	// preferences, #2
	private JMenu menuPreferences;
	private JMenuItem language;
	private JMenuItem pt_BR;
	private JMenuItem en_US;
	
	public MagicMenu() {
		initComponents();
	}
	
	private void initComponents() {
		
		menuFile = new JMenu(Util.getInstance().getString("menu.file"));
		open = new JMenuItem(Util.getInstance().getString("menu.open"));
		save = new JMenuItem(Util.getInstance().getString("menu.save"));
		saveAll = new JMenuItem(Util.getInstance().getString("menu.save.all"));
		quit = new JMenuItem(Util.getInstance().getString("menu.quit"));
		
		menuFile.add(open);
		menuFile.add(save);
		menuFile.add(saveAll);
		menuFile.add(quit);
		
		this.add(menuFile);
	}
}

package com.magiceditor.gui;

import javax.swing.JButton;
import javax.swing.JToolBar;

import com.magiceditor.util.EditorConstants;
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
public class AppToolBar extends JToolBar{

	private JButton btnSave;
	private JButton btnOpen;
	private JButton btnClose;
	
	public AppToolBar() {
		initComponents();
	}
	
	private void initComponents() {
	
		btnOpen = new JButton();
		btnOpen.setIcon(Util.loadToolBarIcon(EditorConstants.ICON_OPEN));
		this.add(btnOpen);
		
		btnSave = new JButton();
		btnSave.setIcon(Util.loadToolBarIcon(EditorConstants.ICON_SAVE));
		this.add(btnSave);
		
		btnClose = new JButton();
		btnClose.setIcon(Util.loadToolBarIcon(EditorConstants.ICON_CLOSE));
		this.add(btnClose);
		
		this.setFloatable(false);
	}
}

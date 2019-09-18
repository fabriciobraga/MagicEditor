package com.magiceditor.gui;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.magiceditor.util.Util;

/**
 * 
 * @author Fabricio Braga
 *
 */
public class MagicSplitPanel extends JSplitPane{
	
	private JPanel leftPanel;
	private JPanel rightPanel;

	public MagicSplitPanel() {
		initComponents();
	}
	
	private void initComponents() {
		
		this.setOrientation(HORIZONTAL_SPLIT);
		this.setDividerLocation(Util.getInstance().getScreenWidth()/5);
		
		this.setLeftComponent(leftPanel);
		this.setRightComponent(rightPanel);
	}

	public JPanel getLeftPanel() {
		return leftPanel;
	}

	public void setLeftPanel(JPanel leftPanel) {
		this.leftPanel = leftPanel;
	}

	public JPanel getRightPanel() {
		return rightPanel;
	}

	public void setRightPanel(JPanel rightPanel) {
		this.rightPanel = rightPanel;
	}
}

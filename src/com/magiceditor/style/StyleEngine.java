package com.magiceditor.style;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import com.magiceditor.util.Util;

/**
 * 
 * @author Fabricio Braga
 *
 */
public class StyleEngine {
	
	private static StyleEngine instance;
	
	private StyleEngine() {
		
	}
	
	public static synchronized StyleEngine getInstance() {
		if(instance == null) {
			instance = new StyleEngine();
		}
		return instance;
	}

	public Style buildDefaultStyle(JTextPane textPane) {
		
		Style style = textPane.addStyle("root", null);
		StyleConstants.setForeground(style, new Color(Integer.parseInt(Util.getInstance().getString("style.default.font.rgb"))));
		StyleConstants.setFontSize(style, Integer.parseInt(Util.getInstance().getString("style.default.font.size")));
		
		
		return style;
	}
}

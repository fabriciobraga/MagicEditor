package com.magiceditor.run;

import java.util.Date;

import javax.swing.UIManager;

import com.magiceditor.gui.MainWindow;

/**
 * 
 * @author Fabricio Braga
 *
 */
public class RunApp {

	public static void main(String... args) {
		System.out.println("starting app at: " + new Date());
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new MainWindow();
	}
}

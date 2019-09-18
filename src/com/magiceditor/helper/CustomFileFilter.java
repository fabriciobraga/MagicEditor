package com.magiceditor.helper;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * 
 * @author Fabricio Braga
 *
 */
public class CustomFileFilter extends FileFilter{

	@Override
	public boolean accept(File f) {
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}

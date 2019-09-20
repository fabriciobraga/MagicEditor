package com.magiceditor.file;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import com.magiceditor.util.Util;

/**
 * 
 * @author Fabricio Braga
 *
 */
public class ExtensionsFilter implements FileFilter {

	@Override
	public boolean accept(File file) {
		if(file.exists() && file.canRead()) {
			if(file.isDirectory()) { //allways display folders
				return true;
			}else {
				for(String extension : Util.getListOfFilesAccepted()) {
					if(file.getName().endsWith(extension)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	
}

package com.magiceditor.file;

import java.io.File;

import javax.swing.JTextPane;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Fabricio Braga
 *
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class MyFileOpened {

	private @NonNull File file;
	private @NonNull boolean saved;
	private @NonNull String path;
	private @NonNull String fileName;
	
	private JTextPane textPane;
	
	@Override
	public boolean equals(Object obj) {
		return this.file.equals(((MyFileOpened)obj).getFile());
	}
	
}

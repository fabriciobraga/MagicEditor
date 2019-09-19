package com.magiceditor.gui;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;

public class CreateChildNodes implements Runnable {

	private DefaultMutableTreeNode root;

	private File fileRoot;

	public CreateChildNodes(File fileRoot, DefaultMutableTreeNode root) {
		this.fileRoot = fileRoot;
		this.root = root;
	}

	@Override
	public void run() {
		createChildren(fileRoot, root);
	}

	private void createChildren(File fileRoot, DefaultMutableTreeNode node) {
		File[] files = fileRoot.listFiles();
		if (files == null)
			return;

		// show the file system roots.
        File[] roots = fileRoot.listRoots();
        for (File fileSystemRoot : roots) {
            DefaultMutableTreeNode currentNode = new DefaultMutableTreeNode(fileSystemRoot);
            root.add( node );
            File[] listOfFiles = fileSystemRoot.listFiles();
            for (File file : listOfFiles) {
                if (file.isDirectory()) {
                    node.add(new DefaultMutableTreeNode(file));
                }
            }
            //
        }
	}

}
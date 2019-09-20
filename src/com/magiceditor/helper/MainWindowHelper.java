package com.magiceditor.helper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.magiceditor.file.MyFileOpened;
import com.magiceditor.gui.MagicMenu;
import com.magiceditor.gui.MainWindow;
import com.magiceditor.style.StyleEngine;
import com.magiceditor.util.EditorConstants;
import com.magiceditor.util.Util;

/**
 * 
 * @author Fabricio Braga
 *
 */
public class MainWindowHelper {

	private MainWindow window;
	private MagicMenu menu;
	
	final JFileChooser fc = new JFileChooser(System.getProperty("user.home"));

	public MainWindowHelper(MainWindow window, MagicMenu menu) {

		this.window = window;
		this.menu = menu;

		initCompoments(); // build components and setup the event handlers
		loadLastFiles(); // load files previous opened
		setupPanels(); // setup panels data
	}

	private void initCompoments() {

		// adding events:
		this.menu.getQuit().addActionListener(new WindowCloseHandler());
		this.menu.getOpen().addActionListener(new OpenNewFileHandler());
		this.menu.getSave().addActionListener(new  SaveFileHandler());
		this.menu.getSaveAll().addActionListener(new  SaveAllFilesHandler());
		this.window.getListaArquivos().getSelectionModel().addListSelectionListener(new ListSelectionHandler());
		this.window.getTabbedPane().addChangeListener(new TabbedPaneSelectionHandler());
		this.window.getToolBar().getBtnOpen().addActionListener(new OpenNewFileHandler());
		this.window.getToolBar().getBtnSave().addActionListener(new  SaveFileHandler());
		this.window.getToolBar().getBtnClose().addActionListener(new  CloseFileHandler());
		this.window.getExplorer().addTreeSelectionListener(new TreeSelectionHandler());
		this.window.getExplorer().addTreeWillExpandListener(new TreeExpansionHandler());
		
		//setting the filter to only accept text files:
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", Util.getInstance().loadFileFilter());
		fc.setFileFilter(filter);
	}

	private void loadLastFilesFake() {
		try {
			File file = new File("C:\\dev-sigr\\maven_settings.xml");
			MyFileOpened fileOpened = new MyFileOpened(file, true, file.getAbsolutePath(), file.getName());
			Util.getInstance().getFileOpenList().add(fileOpened);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadLastFiles() {
		try {
			//opening a default empty white file:
			File file = new File(Util.getAppDataFolder().getAbsolutePath() + File.separator + EditorConstants.APP_TEMP_FILE);
			if(!file.exists()) {
				if(file.createNewFile()) {
					System.out.println("file created at: " + file.getAbsolutePath());
				}
			}
			MyFileOpened fileOpened = new MyFileOpened(file, true, file.getAbsolutePath(), file.getName());
			Util.getInstance().getFileOpenList().add(fileOpened);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JScrollPane loadDocument2ScrollPane(MyFileOpened myFile) {

		try {
			JTextPane textPane = new JTextPane();
			StyledDocument doc = textPane.getStyledDocument();
			Style style = StyleEngine.getInstance().buildDefaultStyle(textPane);

			doc.insertString(0, Util.file2String(myFile.getPath()), style);
			doc.addDocumentListener(new DocumentChangeHandler());

			myFile.setTextPane(textPane);
			return new JScrollPane(textPane);

		} catch (Exception e) {
			Util.handleError(e);
		}
		return null;
	}

	private void setupPanels() {

		for (MyFileOpened f : Util.getInstance().getFileOpenList()) {

			try {
				window.getListModel().addElement(f); // adding to left list
				window.getSplitPanel().remove(1); // if we have previous doc

				window.getTabbedPane().addTab(f.getFileName(), Util.loadTabIcon(EditorConstants.ICON_FILE), loadDocument2ScrollPane(f));
				window.getSplitPanel().add(window.getTabbedPane(), 1);
				window.getSplitPanel().setDividerLocation(Util.getInstance().getScreenWidth() / 6);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class DocumentChangeHandler implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent e) {
			changedUpdate(e);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			changedUpdate(e);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			MyFileOpened elementChanged = window.getListModel().getElementAt(window.getTabbedPane().getSelectedIndex());
			
			//we only need to change if there no before change...
			if(elementChanged.isSaved()) {
				elementChanged.setSaved(false);
				elementChanged.setFileName(elementChanged.getFileName() + " *");
				window.getListaArquivos().repaint();
			}
		}
	}

	class WindowCloseHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("closing app...");
			System.exit(0);
		}
	}

	class OpenNewFileHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int returnVal = fc.showOpenDialog(window);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				
				//test if file isn't already opened:
				if(!Util.isFileOpened(file)) {
				
					// crating our new file representation:
					MyFileOpened newFile = new MyFileOpened(file, true, file.getAbsolutePath(), file.getName());
					Util.getFileOpenList().add(newFile);
					window.getListModel().addElement(newFile);
	
					// now we append a new pane to tabbed pane:
					window.getTabbedPane().addTab(newFile.getFileName(), Util.loadTabIcon(EditorConstants.ICON_FILE), loadDocument2ScrollPane(newFile));
					window.getTabbedPane().setSelectedIndex(window.getTabbedPane().getComponentCount() - 1);
				}
			}
		}
	}

	class ListSelectionHandler implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent e) {
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();

			if (!lsm.isSelectionEmpty()) {
				// Find out which indexes are selected.
				int minIndex = lsm.getMinSelectionIndex();
				int maxIndex = lsm.getMaxSelectionIndex();
				for (int i = minIndex; i <= maxIndex; i++) {
					if (lsm.isSelectedIndex(i)) {
						window.getTabbedPane().setSelectedIndex(i);
					}
				}
			}
		}
	}
	
	class TabbedPaneSelectionHandler implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			window.getListaArquivos().setSelectedIndex(window.getTabbedPane().getSelectedIndex());
		}
	}
	
	class SaveFileHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			if(window.getTabbedPane().getTabCount() > 0 && window.getListModel().getSize() > 0) {
				
				//first we need the index:
				int fileSelectedIndex = window.getTabbedPane().getSelectedIndex();
				
				try {
					//now loading doc and saving:
					MyFileOpened fileObject = window.getListModel().get(fileSelectedIndex);
					Util.save2File(fileObject.getFile().toPath(), fileObject.getTextPane().getDocument().getText(0, fileObject.getTextPane().getDocument().getLength()));
					fileObject.setSaved(true);
					fileObject.setFileName(fileObject.getFile().getName());
					window.getListaArquivos().repaint();
					
				}catch(BadLocationException err) {
					Util.handleError(err);
				}
			}
		}
	}
	
	class SaveAllFilesHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			for(MyFileOpened fileObject : Util.getFileOpenList()) {
				
				try {
					//now loading doc and saving:
					Util.save2File(fileObject.getFile().toPath(), fileObject.getTextPane().getDocument().getText(0, fileObject.getTextPane().getDocument().getLength()));
					fileObject.setSaved(true);
					fileObject.setFileName(fileObject.getFile().getName());
					
				}catch(BadLocationException err) {
					Util.handleError(err);
				}
			}
			window.getListaArquivos().repaint();
		}
	}
	
	class CloseFileHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			if(window.getTabbedPane().getTabCount() > 0 && window.getListModel().getSize() > 0) {
				
				// first we need the index:
				int fileSelectedIndex = window.getTabbedPane().getSelectedIndex();
				Util.getFileOpenList().remove(fileSelectedIndex);
				window.getListModel().remove(fileSelectedIndex);
				window.getTabbedPane().remove(fileSelectedIndex);
			}
		}
	}
	
	class TreeSelectionHandler implements TreeSelectionListener{

		@Override
		public void valueChanged(TreeSelectionEvent e) {
			
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    ((JTree)e.getSource()).getLastSelectedPathComponent();
			loadNodesToShow(node);
		}
	}
	
	class TreeExpansionHandler implements TreeWillExpandListener {

		@Override
		public void treeWillExpand(TreeExpansionEvent e) throws ExpandVetoException {
			TreePath path = e.getPath();
	        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
	        loadNodesToShow(node);
			
		}

		@Override
		public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
			// TODO Auto-generated method stub
			
		}
	}
	
	private void loadNodesToShow(DefaultMutableTreeNode node) {
		if(node != null) {
			String folderPath = "";
			for(TreeNode n : node.getPath()) {
				folderPath += n + File.separator;
			}
			System.out.println("selecionou: " + folderPath);
			Util.readOnlySubFolders(node, new File(folderPath));
			window.getExplorer().repaint();
		}	
	}
}

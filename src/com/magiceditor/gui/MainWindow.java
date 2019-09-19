package com.magiceditor.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.io.File;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;

import com.magiceditor.file.MyFileOpened;
import com.magiceditor.helper.MainWindowHelper;
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
public class MainWindow extends JFrame{
	
	private JSplitPane splitPanel;
	private JSplitPane splitPanelExplorer;
	private JScrollPane listScrollPane;
	private JScrollPane fileScrollPane;
	private JTabbedPane tabbedPane;
	private JTree explorer;
	
	JList<MyFileOpened> listaArquivos;
	DefaultListModel<MyFileOpened> listModel;
	
	private MagicMenu menu;
	private AppToolBar toolBar;

	public MainWindow() {
		initComponents();
		
		//adding events and handlers:
		new MainWindowHelper(this, menu);
	}
	
	private void initComponents() {
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setLayout(new BorderLayout());
		this.setTitle("Editor");
		
		//adding splitpane:
		listModel = new DefaultListModel<MyFileOpened>();
		listaArquivos = new JList<MyFileOpened>(listModel);
		listaArquivos.setCellRenderer(new MyCellRenderer());
		listaArquivos.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		listScrollPane = new JScrollPane(listaArquivos);
		fileScrollPane = new JScrollPane(new JTextArea());
		tabbedPane = new JTabbedPane();
		
		//building explorer tree:
		explorer = new JTree(Util.loadSystemNodes());
		explorer.setShowsRootHandles(true);
		JScrollPane treeView = new JScrollPane(explorer);
		
		splitPanelExplorer = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(listaArquivos), treeView);
		splitPanelExplorer.setDividerLocation(Util.getInstance().getScreenHeight()/2);
		splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPanelExplorer, fileScrollPane);
		splitPanel.setDividerLocation(Util.getInstance().getScreenWidth()/5);
		this.add(splitPanel);
		
		
		//adding menu:
		menu = new MagicMenu();
		this.setJMenuBar(menu);
		
		//adding toolbar:
		toolBar = new AppToolBar();
		this.add(toolBar, BorderLayout.NORTH); 
		
		this.setVisible(true);
		getSplitPanel().getComponent(1).requestFocus();
	}
	
	private static class MyCellRenderer extends DefaultListCellRenderer  {

        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof MyFileOpened) {
            	
            	MyFileOpened file = (MyFileOpened) value;
                setText(file.getFileName());
                setIcon(FileSystemView.getFileSystemView().getSystemIcon(file.getFile()));

                if (isSelected) {
                    setBackground(list.getSelectionBackground());
                    setForeground(list.getSelectionForeground());
                } else {
                    setBackground(list.getBackground());
                    setForeground(list.getForeground());
                }
                setEnabled(list.isEnabled());
                setFont(list.getFont());
                setOpaque(true);
                
                if(!file.isSaved()) {
                	setFont(new Font(getFont().getName(), Font.BOLD, getFont().getSize()));
                }
            }
            return this;
        }
    }
	
	
	
}

package com.magiceditor.style;

import java.awt.Color;
import java.awt.Font;

import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

public class MyXMLStyledDocument implements StyledDocument{

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addDocumentListener(DocumentListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeDocumentListener(DocumentListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addUndoableEditListener(UndoableEditListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUndoableEditListener(UndoableEditListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getProperty(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putProperty(Object key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(int offs, int len) throws BadLocationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getText(int offset, int length) throws BadLocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getText(int offset, int length, Segment txt) throws BadLocationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Position getStartPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position getEndPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position createPosition(int offs) throws BadLocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element[] getRootElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element getDefaultRootElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void render(Runnable r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Style addStyle(String nm, Style parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeStyle(String nm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Style getStyle(String nm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCharacterAttributes(int offset, int length, AttributeSet s, boolean replace) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParagraphAttributes(int offset, int length, AttributeSet s, boolean replace) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLogicalStyle(int pos, Style s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Style getLogicalStyle(int p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element getParagraphElement(int pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element getCharacterElement(int pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getForeground(AttributeSet attr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getBackground(AttributeSet attr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Font getFont(AttributeSet attr) {
		// TODO Auto-generated method stub
		return null;
	}

	
}

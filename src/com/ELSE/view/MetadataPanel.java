package com.ELSE.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.time.Year;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ELSE.model.BookMetadata;
import com.ELSE.model.Utils;
import com.ELSE.presenter.Presenter;

public class MetadataPanel {
	static MetadataPanel newInstance(final JPanel parent) {
		return new MetadataPanel(parent);
	}
	
	private BookMetadata book;
	private JLabel bookPreview;
	private final JButton openDefault, openCustom;
	private final JPanel parent;
	private final JTextField titolo, autore, anno, pagine;
	
	private MetadataPanel(final JPanel parent) {
		this.parent = parent;
		titolo = new JTextField(15);
		autore = new JTextField(15);
		anno = new JTextField(15);
		pagine = new JTextField(15);
		openCustom = new JButton("Internal Open");
		openDefault = new JButton("External Open");
	}
	
	void change(final Image image, final BookMetadata book, final boolean editable) {
		// TODO Needs full refactory
		Utils.log(Utils.Debug.DEBUG, "CHANGING TO " + book + " - " + editable);
		final JPanel parentpanel = JInvisiblePanel.newInstance(parent);
		parentpanel.setLayout(new BoxLayout(parentpanel, BoxLayout.X_AXIS));
		this.book = book;
		parent.removeAll();
		bookPreview = new JLabel();
		bookPreview.setIcon(new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(-1, 300, Image.SCALE_DEFAULT)));
		parentpanel.add(Box.createHorizontalGlue());
		parentpanel.add(bookPreview);
		parentpanel.add(Box.createHorizontalGlue());
		final JPanel vbox = JInvisiblePanel.newInstance(parent);
		vbox.setLayout(new BoxLayout(vbox, BoxLayout.Y_AXIS));
		final JPanel panel = JInvisiblePanel.newInstance(vbox);
		panel.setLayout(new GridLayout(0, 2));
		final JLabel ltitolo = new JLabel("Titolo: ");
		final JLabel lautore = new JLabel("Autore: ");
		final JLabel lanno = new JLabel("Anno: ");
		final JLabel lpagine = new JLabel("Pagine: ");
		if (editable) {
			panel.add(ltitolo);
			titolo.setText(book.getTitolo());
			panel.add(titolo);
			panel.add(lautore);
			autore.setText(book.getAutore());
			panel.add(autore);
			panel.add(lanno);
			anno.setText(book.getAnno() != null ? book.getAnno().toString() : "");
			panel.add(anno);
			panel.add(lpagine);
			pagine.setText(Integer.toString(book.getPagine()));
			panel.add(pagine);
		} else {
			panel.add(ltitolo);
			final JLabel titolo = new JLabel(book.getTitolo());
			panel.add(titolo);
			panel.add(lautore);
			final JLabel autore = new JLabel(book.getAutore());
			panel.add(autore);
			panel.add(lanno);
			final JLabel anno = new JLabel(book.getAnno() != null && !book.getAnno().equals(Year.of(0)) ? book.getAnno().toString() : "");
			panel.add(anno);
			panel.add(lpagine);
			final JLabel pagine = new JLabel(book.getPagine() > 0 ? Integer.toString(book.getPagine()) : "");
			panel.add(pagine);
		}
		vbox.add(panel);
		vbox.add(Box.createVerticalGlue());
		final JPanel buttons = JInvisiblePanel.newInstance(vbox);
		buttons.add(openCustom);
		buttons.add(openDefault);
		vbox.add(buttons);
		parentpanel.add(vbox);
		parentpanel.add(Box.createHorizontalGlue());
		parent.add(parentpanel, BorderLayout.NORTH);
		parent.revalidate();
		parent.repaint();
	}
	
	public JTextField getAnno() {
		return anno;
	}
	
	public JTextField getAutore() {
		return autore;
	}
	
	public BookMetadata getBook() {
		return book;
	}
	
	public JButton getOpenCustomButton() {
		return openCustom;
	}
	
	public JButton getOpenDefaultButton() {
		return openDefault;
	}
	
	public JTextField getPagine() {
		return pagine;
	}
	
	public JTextField getTitolo() {
		return titolo;
	}
	
	void setPresenter(final Presenter presenter) {
		titolo.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		autore.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		anno.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		pagine.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		titolo.getDocument().addDocumentListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		autore.getDocument().addDocumentListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		anno.getDocument().addDocumentListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		pagine.getDocument().addDocumentListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		for (final ActionListener al : openCustom.getActionListeners())
			openCustom.removeActionListener(al);
		for (final ActionListener al : openDefault.getActionListeners())
			openDefault.removeActionListener(al);
		openCustom.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		openDefault.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
	}
}

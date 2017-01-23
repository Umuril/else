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
	static MetadataPanel newInstance(JPanel parent) {
		return new MetadataPanel(parent);
	}

	private BookMetadata book;
	private JLabel bookPreview;
	private JButton openDefault, openCustom;
	private JPanel parent;
	private JTextField titolo, autore, anno, pagine;

	private MetadataPanel(JPanel parent) {
		this.parent = parent;
		titolo = new JTextField(15);
		autore = new JTextField(15);
		anno = new JTextField(15);
		pagine = new JTextField(15);
		openCustom = new JButton("Internal Open");
		openDefault = new JButton("External Open");
	}

	void change(Image image, BookMetadata book, boolean editable) {
		// TODO Needs full refactory
		Utils.log(Utils.Debug.DEBUG, "CHANGING TO " + book + " - " + editable);
		JPanel parentpanel = JInvisiblePanel.newInstance(parent);
		parentpanel.setLayout(new BoxLayout(parentpanel, BoxLayout.X_AXIS));
		this.book = book;
		parent.removeAll();
		bookPreview = new JLabel();
		bookPreview.setIcon(new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(-1, 300, Image.SCALE_DEFAULT)));
		parentpanel.add(Box.createHorizontalGlue());
		parentpanel.add(bookPreview);
		parentpanel.add(Box.createHorizontalGlue());
		JPanel things = JInvisiblePanel.newInstance(parent);
		things.setLayout(new BoxLayout(things, BoxLayout.Y_AXIS));
		JPanel panel = JInvisiblePanel.newInstance(things);
		panel.setLayout(new GridLayout(0, 2));
		JLabel ltitolo = new JLabel("Titolo: ");
		JLabel lautore = new JLabel("Autore: ");
		JLabel lanno = new JLabel("Anno: ");
		JLabel lpagine = new JLabel("Pagine: ");
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
			JLabel titolo = new JLabel(book.getTitolo());
			panel.add(titolo);
			panel.add(lautore);
			JLabel autore = new JLabel(book.getAutore());
			panel.add(autore);
			panel.add(lanno);
			JLabel anno = new JLabel(book.getAnno() != null && !book.getAnno().equals(Year.of(0)) ? book.getAnno().toString() : "");
			panel.add(anno);
			panel.add(lpagine);
			JLabel pagine = new JLabel(book.getPagine() > 0 ? Integer.toString(book.getPagine()) : "");
			panel.add(pagine);
		}
		things.add(panel);
		things.add(Box.createVerticalGlue());
		JPanel useless = JInvisiblePanel.newInstance(things);
		useless.add(openCustom);
		useless.add(openDefault);
		things.add(useless);
		parentpanel.add(things);
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

	public JTextField getPagine() {
		return pagine;
	}

	public JTextField getTitolo() {
		return titolo;
	}

	public BookMetadata getBook() {
		return book;
	}

	public JButton getOpenDefaultButton() {
		return openDefault;
	}

	public JButton getOpenCustomButton() {
		return openCustom;
	}

	void setPresenter(Presenter presenter) {
		titolo.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		autore.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		anno.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		pagine.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		titolo.getDocument().addDocumentListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		autore.getDocument().addDocumentListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		anno.getDocument().addDocumentListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		pagine.getDocument().addDocumentListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		for (ActionListener al : openCustom.getActionListeners())
			openCustom.removeActionListener(al);
		for (ActionListener al : openDefault.getActionListeners())
			openDefault.removeActionListener(al);
		openCustom.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		openDefault.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
	}
}

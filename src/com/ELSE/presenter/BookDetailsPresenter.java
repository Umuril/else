package com.ELSE.presenter;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Year;
import java.util.Map.Entry;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.ELSE.model.BookMetadata;
import com.ELSE.model.Model;
import com.ELSE.model.Utils;
import com.ELSE.view.BookDetailsPage;
import com.ELSE.view.MetadataPanel;
import com.ELSE.view.View;

public class BookDetailsPresenter implements ActionListener, DocumentListener {
	private View view;
	private Model model;
	private Presenter presenter;

	public BookDetailsPresenter(View view, Model model, Presenter presenter) {
		this.view = view;
		this.model = model;
		this.presenter = presenter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		BookDetailsPage bookDetailsPage = view.getBookDetailsPage();
		MetadataPanel metadataPanel = view.getMetadataPanel();
		BookMetadata book = view.getMetadataPanel().getBook();
		if (e.getSource() == bookDetailsPage.getBackButton()) {
			presenter.getCenterPresenter().change(null, null);
			view.changeBookPageEditable(Boolean.FALSE);
		} else if (e.getSource() == bookDetailsPage.getEditButton()) {
			view.changeBookPageEditable(null);
			// view.getBookDetailsPage().enableSaveButton(true);
		} else if (e.getSource() == bookDetailsPage.getSaveButton() || e.getSource() == metadataPanel.getTitolo() || e.getSource() == metadataPanel.getAutore() || e.getSource() == metadataPanel.getAnno() || e.getSource() == metadataPanel.getPagine()) {
			for (Entry<String, BookMetadata> entry : model.getLibrary().getDatabase().entrySet()) {
				if (entry.getValue().equals(book)) {
					String s = metadataPanel.getTitolo().getText();
					if (Utils.checkString(s) && s.length() <= 20)
						entry.getValue().setTitolo(s);
					s = metadataPanel.getAutore().getText();
					if (Utils.checkString(s) && s.length() <= 20)
						entry.getValue().setAutore(s);
					s = metadataPanel.getAnno().getText();
					if (Utils.checkString(s) && s.length() <= 4 && s.matches("^\\d+$"))
						entry.getValue().setAnno(String.format("%04d", Integer.parseInt(s)));
					s = metadataPanel.getPagine().getText();
					if (Utils.checkString(s) && s.length() <= 5 && s.matches("^\\d+$"))
						entry.getValue().setPagine(Integer.parseInt(s));
				}
			}
			view.changeBookPageEditable(Boolean.FALSE);
			view.getBookDetailsPage().enableSaveButton(false);
			view.needToSave(true);
		} else if (e.getSource() == metadataPanel.getOpenCustomButton()) {
			for (Entry<String, BookMetadata> entry : model.getLibrary().getDatabase().entrySet()) {
				if (entry.getValue().equals(book)) {
					presenter.getReader(Paths.get(entry.getKey()));
					break;
				}
			}
		} else if (e.getSource() == metadataPanel.getOpenDefaultButton()) {
			for (Entry<String, BookMetadata> entry : model.getLibrary().getDatabase().entrySet()) {
				if (entry.getValue().equals(book)) {
					try {
						Desktop.getDesktop().open(new File(entry.getKey()));
					} catch (RuntimeException | IOException ex) {
						view.setStatusText("Errore nella lettura del libro");
					}
					break;
				}
			}
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		documentListener(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		documentListener(e);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		documentListener(e);
	}

	private void documentListener(DocumentEvent e) {
		view.getBookDetailsPage().enableSaveButton(false);
		BookMetadata book = view.getMetadataPanel().getBook();
		JTextField text;
		text = view.getMetadataPanel().getTitolo();
		if (text.getDocument() == e.getDocument() && text.getText().length() < 20 && !book.getTitolo().equals(text.getText())) {
			Utils.log(Utils.Debug.DEBUG, "DOCUMENT LISTENER ON TITOLO");
			view.getBookDetailsPage().enableSaveButton(true);
		}
		text = view.getMetadataPanel().getAutore();
		if (text.getDocument() == e.getDocument() && text.getText().length() < 20 && !book.getAutore().equals(text.getText())) {
			Utils.log(Utils.Debug.DEBUG, "DOCUMENT LISTENER ON AUTORE");
			view.getBookDetailsPage().enableSaveButton(true);
		}
		text = view.getMetadataPanel().getAnno();
		if (text.getDocument() == e.getDocument() && text.getText().matches("^\\d+$") && text.getText().length() < 5 && !book.getAnno().equals(Year.parse(String.format("%04d", Integer.parseInt(text.getText()))))) {
			Utils.log(Utils.Debug.DEBUG, "DOCUMENT LISTENER ON ANNO");
			view.getBookDetailsPage().enableSaveButton(true);
		}
		text = view.getMetadataPanel().getPagine();
		if (text.getDocument() == e.getDocument() && text.getText().length() < 6 && text.getText().matches("^\\d+$") && book.getPagine() != Integer.parseInt(text.getText())) {
			Utils.log(Utils.Debug.DEBUG, "DOCUMENT LISTENER ON PAGINE");
			view.getBookDetailsPage().enableSaveButton(true);
		}
	}
}

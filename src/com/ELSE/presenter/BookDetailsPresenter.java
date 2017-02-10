package com.ELSE.presenter;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
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

class BookDetailsPresenter implements ActionListener, DocumentListener {
	private final Model model;
	private final Presenter presenter;
	private final View view;
	
	BookDetailsPresenter(final View view, final Model model, final Presenter presenter) {
		this.view = view;
		this.model = model;
		this.presenter = presenter;
	}
	
	@Override
	public void actionPerformed(final ActionEvent action) {
		final BookDetailsPage bookDetailsPage = view.getBookDetailsPage();
		final MetadataPanel metadataPanel = view.getMetadataPanel();
		final BookMetadata book = view.getMetadataPanel().getBook();
		if (action.getSource() == bookDetailsPage.getBackButton()) {
			presenter.getCenterPresenter().change(null, null);
			view.changeBookPageEditable(Boolean.FALSE);
		} else if (action.getSource() == bookDetailsPage.getEditButton())
			view.changeBookPageEditable(null);
		else if (action.getSource() == bookDetailsPage.getSaveButton() || action.getSource() == metadataPanel.getTitolo() || action.getSource() == metadataPanel.getAutore() || action.getSource() == metadataPanel.getAnno() || action.getSource() == metadataPanel.getPagine()) {
			for (final Entry<Path, BookMetadata> entry : model.getLibrary().getDatabase().entrySet())
				if (entry.getValue().equals(book)) {
					String s = metadataPanel.getTitolo().getText();
					if (Utils.validString(s) && s.length() <= 20)
						entry.getValue().setTitolo(s);
					else
						view.setStatusText("Titolo non valido o troppo lungo!");
					s = metadataPanel.getAutore().getText();
					if (Utils.validString(s) && s.length() <= 20)
						entry.getValue().setAutore(s);
					else
						view.setStatusText("Autore non valido o troppo lungo!");
					s = metadataPanel.getAnno().getText();
					if (Utils.validString(s) && s.length() <= 4 && s.matches("^\\d+$"))
						entry.getValue().setAnno(String.format("%04d", Integer.parseInt(s)));
					else
						view.setStatusText("Anno non valido!");
					s = metadataPanel.getPagine().getText();
					if (Utils.validString(s) && s.length() <= 5 && s.matches("^\\d+$"))
						entry.getValue().setPagine(Integer.parseInt(s));
					else
						view.setStatusText("Numero di pagine non valido!");
				}
			view.changeBookPageEditable(Boolean.FALSE);
			view.getBookDetailsPage().enableSaveButton(false);
			view.needToSave(true);
		} else if (action.getSource() == metadataPanel.getOpenCustomButton()) {
			for (final Entry<Path, BookMetadata> entry : model.getLibrary().getDatabase().entrySet())
				if (entry.getValue().equals(book)) {
					presenter.getReader(entry.getKey());
					break;
				}
		} else if (action.getSource() == metadataPanel.getOpenDefaultButton())
			for (final Entry<Path, BookMetadata> entry : model.getLibrary().getDatabase().entrySet())
				if (entry.getValue().equals(book)) {
					try {
						Desktop.getDesktop().open(entry.getKey().toFile());
					} catch (RuntimeException | IOException ex) {
						view.setStatusText("Errore nella lettura del libro");
					}
					break;
				}
	}
	
	@Override
	public void changedUpdate(final DocumentEvent event) {
		documentListener(event);
	}
	
	private void documentListener(final DocumentEvent event) {
		view.getBookDetailsPage().enableSaveButton(false);
		final BookMetadata book = view.getMetadataPanel().getBook();
		JTextField text = view.getMetadataPanel().getTitolo();
		if (text.getDocument() == event.getDocument() && text.getText().length() < 20 && !book.getTitolo().equals(text.getText())) {
			Utils.log(Utils.Debug.DEBUG, "DOCUMENT LISTENER ON TITOLO");
			view.getBookDetailsPage().enableSaveButton(true);
		}
		text = view.getMetadataPanel().getAutore();
		if (text.getDocument() == event.getDocument() && text.getText().length() < 20 && !book.getAutore().equals(text.getText())) {
			Utils.log(Utils.Debug.DEBUG, "DOCUMENT LISTENER ON AUTORE");
			view.getBookDetailsPage().enableSaveButton(true);
		}
		text = view.getMetadataPanel().getAnno();
		if (text.getDocument() == event.getDocument() && text.getText().matches("^\\d+$") && text.getText().length() < 5 && !book.getAnno().equals(Year.parse(String.format("%04d", Integer.parseInt(text.getText()))))) {
			Utils.log(Utils.Debug.DEBUG, "DOCUMENT LISTENER ON ANNO");
			view.getBookDetailsPage().enableSaveButton(true);
		}
		text = view.getMetadataPanel().getPagine();
		if (text.getDocument() == event.getDocument() && text.getText().length() < 6 && text.getText().matches("^\\d+$") && book.getPagine() != Integer.parseInt(text.getText())) {
			Utils.log(Utils.Debug.DEBUG, "DOCUMENT LISTENER ON PAGINE");
			view.getBookDetailsPage().enableSaveButton(true);
		}
	}
	
	@Override
	public void insertUpdate(final DocumentEvent event) {
		documentListener(event);
	}
	
	@Override
	public void removeUpdate(final DocumentEvent event) {
		documentListener(event);
	}
}

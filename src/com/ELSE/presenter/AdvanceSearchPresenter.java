package com.ELSE.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map.Entry;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JTextField;

import com.ELSE.model.BookMetadata;
import com.ELSE.model.Model;
import com.ELSE.model.Utils;
import com.ELSE.view.AdvanceSearch;
import com.ELSE.view.View;

/**
 * Classe che gestisce il presenter del dialog per la ricerca avanzata
 * 
 * @author eddy
 */
class AdvanceSearchPresenter implements ActionListener {
	private final CenterPresenter centerPresenter;
	private final Model model;
	private final View view;
	
	/**
	 * Costruttore
	 * 
	 * @param view
	 *            Vista generale del progetto
	 * @param model
	 *            Modello generale
	 * @param centerPresenter
	 *            Presenter del pannello centrale
	 */
	AdvanceSearchPresenter(final View view, final Model model, final CenterPresenter centerPresenter) {
		this.view = view;
		this.model = model;
		this.centerPresenter = centerPresenter;
	}
	
	@Override
	public void actionPerformed(final ActionEvent action) {
		final AdvanceSearch advanceSearch = view.getAdvanceSearch();
		final JDialog dialog = advanceSearch.getDialog();
		final JTextField titolo = advanceSearch.getTitolo();
		final JTextField autore = advanceSearch.getAutore();
		final JTextField anno = advanceSearch.getAnno();
		final JTextField pagine = advanceSearch.getPagine();
		final JCheckBox epub = advanceSearch.getEpub();
		final JCheckBox html = advanceSearch.getHtml();
		final JCheckBox pdf = advanceSearch.getPdf();
		Utils.log(Utils.Debug.INFO, "Search triggered with title " + titolo.getText() + ".");
		Utils.log(Utils.Debug.DEBUG, "Search triggered with author " + autore.getText() + ".");
		Utils.log(Utils.Debug.DEBUG, model.getLibrary().getDatabase().values());
		if (!Utils.validString(titolo.getText(), autore.getText(), anno.getText(), pagine.getText()))
			return;
		centerPresenter.removeAllBooks();
		for (final Entry<Path, BookMetadata> entry : model.getLibrary().getDatabase().entrySet()) {
			final BookMetadata book = entry.getValue();
			if (epub.isSelected() && entry.getKey().endsWith(".epub") || html.isSelected() && entry.getKey().endsWith(".html") || pdf.isSelected() && entry.getKey().endsWith(".pdf")) {
				boolean found = false;
				if (Utils.validString(book.getTitolo())) {
					if (book.getTitolo().contains(titolo.getText()))
						found = true;
				} else if (Utils.validString(book.getAutore())) {
					if (book.getAutore().contains(autore.getText()))
						found = true;
				} else if (Utils.validYear(book.getAnno())) {
					if (book.getAnno().toString().contains(anno.getText()))
						found = true;
				} else if (book.getPagine() > 0 && Utils.validString(pagine.getText()))
					if (String.valueOf(book.getPagine()).contains(pagine.getText()))
						found = true;
				if (found) {
					Utils.log(Utils.Debug.INFO, "Found: " + book);
					try {
						centerPresenter.addImage(entry.getKey());
					} catch (final IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		dialog.dispose();
	}
}

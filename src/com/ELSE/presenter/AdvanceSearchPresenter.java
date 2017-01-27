package com.ELSE.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map.Entry;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JTextField;

import com.ELSE.model.BookMetadata;
import com.ELSE.model.Model;
import com.ELSE.model.Utils;
import com.ELSE.view.AdvanceSearch;
import com.ELSE.view.View;

public class AdvanceSearchPresenter implements ActionListener {
	private final CenterPresenter centerPresenter;
	private final Model model;
	private final View view;

	public AdvanceSearchPresenter(View view, Model model, CenterPresenter centerPresenter) {
		this.view = view;
		this.model = model;
		this.centerPresenter = centerPresenter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		AdvanceSearch advanceSearch = view.getAdvanceSearch();
		JDialog dialog = advanceSearch.getDialog();
		JTextField titolo = advanceSearch.getTitolo();
		JTextField autore = advanceSearch.getAutore();
		JTextField anno = advanceSearch.getAnno();
		JTextField pagine = advanceSearch.getPagine();
		JCheckBox epub = advanceSearch.getEpub();
		JCheckBox html = advanceSearch.getHtml();
		JCheckBox pdf = advanceSearch.getPdf();
		Utils.log(Utils.Debug.INFO, "Search triggered with title " + titolo.getText() + ".");
		Utils.log(Utils.Debug.DEBUG, "Search triggered with author " + autore.getText() + ".");
		Utils.log(Utils.Debug.DEBUG, model.getLibrary().getDatabase().values());
		if (titolo.getText().isEmpty() && autore.getText().isEmpty() && anno.getText().isEmpty() && pagine.getText().isEmpty())
			return;
		centerPresenter.emptyOfBooks();
		for (Entry<String, BookMetadata> entry : model.getLibrary().getDatabase().entrySet()) {
			BookMetadata book = entry.getValue();
			boolean found = false;
			if (epub.isSelected() && entry.getKey().endsWith(".epub") || html.isSelected() && entry.getKey().endsWith(".html") || pdf.isSelected() && entry.getKey().endsWith(".pdf")) {
				if (book.getAnno() != null && !anno.getText().isEmpty())
					if (book.getAnno().toString().contains(anno.getText()))
						found = true;
				if (book.getAutore() != null && !autore.getText().isEmpty())
					if (book.getAutore().contains(autore.getText()))
						found = true;
				if (book.getPagine() > 0 && !pagine.getText().isEmpty())
					if (String.valueOf(book.getPagine()).contains(pagine.getText()))
						found = true;
				if (book.getTitolo() != null && !titolo.getText().isEmpty())
					if (book.getTitolo().contains(titolo.getText()))
						found = true;
				if (found) {
					Utils.log(Utils.Debug.INFO, "Found: " + book);
					try {
						centerPresenter.addImage(Paths.get(entry.getKey()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		dialog.dispose();
	}
}

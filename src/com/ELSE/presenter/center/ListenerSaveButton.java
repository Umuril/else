package com.ELSE.presenter.center;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Year;
import java.util.Map.Entry;

import com.ELSE.model.BookMetadata;
import com.ELSE.model.Model;
import com.ELSE.view.View;

public class ListenerSaveButton implements ActionListener {

	private View view;
	private Model model;
	private BookMetadata book;

	public ListenerSaveButton(View view, Model model, BookMetadata book) {
		this.view = view;
		this.model = model;
		this.book = book;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (Entry<String, BookMetadata> entry : model.getLibrary()
				.getDatabase().entrySet()) {
			if (entry.getValue().equals(book)) {
				entry.getValue().setTitolo(view.getBookDetailTitolo());
				entry.getValue().setAutore(view.getBookDetailAutore());
				entry.getValue().setAnno(Year.parse(view.getBookDetailAnno()));
				entry.getValue().setNpagine(
						Integer.parseInt(view.getBookDetailPagine()));
			}
		}
		// book.setTitolo(view.getBookDetailTitolo());
		// book.setAutore(view.getBookDetailAutore());
		view.setBookPageEditable(false);
		view.needToSave(true);

	}
}

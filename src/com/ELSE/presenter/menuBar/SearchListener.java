package com.ELSE.presenter.menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JTextField;

import com.ELSE.model.BookMetadata;
import com.ELSE.model.Model;
import com.ELSE.presenter.center.CenterPresenter;

public class SearchListener implements ActionListener {
	private Model model;
	private CenterPresenter centerPresenter;
	private JTextField testo;

	public SearchListener(Model model, CenterPresenter centerPresenter, JTextField testo) {
		this.model = model;
		this.centerPresenter = centerPresenter;
		this.testo = testo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String text = testo.getText();
		if (text.isEmpty()) {
			centerPresenter.aggiorna();
			return;
		}
		System.out.println("Search triggered with " + text + ".");
		System.out.println(model.getLibrary().getDatabase().values());
		centerPresenter.emptyOfBooks();
		for (BookMetadata book : model.getLibrary().getDatabase().values()) {
			boolean found = false;
			if (book.getAnno() != null)
				if (book.getAnno().toString().contains(text))
					found = true;
			if (book.getAutore() != null)
				if (book.getAutore().contains(text))
					found = true;
			if (book.getNpagine() > 0)
				if (String.valueOf(book.getNpagine()).contains(text))
					found = true;
			if (book.getTitolo() != null)
				if (book.getTitolo().contains(text))
					found = true;
			if (found) {
				System.out.println("Found: " + book);
				try {
					centerPresenter.addImage(book);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else
				System.out.println("Book not found: " + book);
		}
	}
}

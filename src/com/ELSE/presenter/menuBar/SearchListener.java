package com.ELSE.presenter.menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import com.ELSE.model.BookMetadata;
import com.ELSE.model.Model;

public class SearchListener implements ActionListener {

	private Model model;
	private JTextField testo;

	public SearchListener(Model model, JTextField testo) {
		this.model = model;
		this.testo = testo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String text = testo.getText();
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
			if (found)
				System.out.println("Found: " + book);
		}
	}

}

package com.ELSE.presenter.center;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ELSE.model.BookMetadata;
import com.ELSE.view.View;

public class ListenerSaveButton implements ActionListener {

	private View view;
	private BookMetadata book;

	public ListenerSaveButton(View view, BookMetadata book) {
		this.view = view;
		this.book = book;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		book.setTitolo(view.getBookDetailTitolo());
		book.setAutore(view.getBookDetailAutore());
		view.setBookPageEditable(false);

	}

}

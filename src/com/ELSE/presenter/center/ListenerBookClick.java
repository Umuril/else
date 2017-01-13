package com.ELSE.presenter.center;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import com.ELSE.model.BookMetadata;
import com.ELSE.view.View;

public class ListenerBookClick implements ActionListener {
	private View view;
	private BufferedImage image;
	private BookMetadata book;

	public ListenerBookClick(View view, BufferedImage image, BookMetadata book) {
		this.view = view;
		this.image = image;
		this.book = book;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		view.change(image, book);
	}
}

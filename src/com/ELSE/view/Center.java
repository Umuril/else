package com.ELSE.view;

import java.awt.Component;
import java.awt.Image;

import javax.swing.JPanel;

import com.ELSE.model.BookMetadata;

class Center {

	private JPanel panel;

	private SliderPage slider;
	private BookDetailsPage bookDetails;

	private Center(Component parent) {
		slider = SliderPage.newInstance();
		bookDetails = BookDetailsPage.newInstance();
		
		panel = JInvisiblePanel.newInstance(parent);
		panel.add(slider.getContainerPanel());
	}

	static Center newInstance(Component parent) {
		return new Center(parent);
	}

	JPanel getPanel() {
		return panel;
	}

	SliderPage getSlider() {
		return slider;
	}

	BookDetailsPage getBookDetails() {
		return bookDetails;
	}

	void change(Image image, BookMetadata book) {

		panel.removeAll();

		if (image == null || book == null) {
			panel.add(slider.getContainerPanel());
		} else {
			bookDetails.updateUpWith(image, book);
			panel.add(bookDetails.getContainerPanel());
		}

		panel.revalidate();
		panel.repaint();
	}

}
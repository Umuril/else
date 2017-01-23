package com.ELSE.view;

import java.awt.Component;
import java.awt.Image;

import javax.swing.JPanel;

import com.ELSE.model.BookMetadata;

class Center {
	static Center newInstance(Component parent) {
		return new Center(parent);
	}

	private BookDetailsPage bookDetails;
	private JPanel panel;
	private SliderPage slider;
	private EmptyPage emptyPage;
	private boolean empty;

	private Center(Component parent) {
		emptyPage = EmptyPage.newInstance();
		slider = SliderPage.newInstance();
		bookDetails = BookDetailsPage.newInstance();
		panel = JInvisiblePanel.newInstance(parent);
		// panel.add(slider.getContainerPanel());
		empty = true;
		// panel.add(emptyPage.getContainerPanel());
	}

	void change(Image image, BookMetadata book) {
		panel.removeAll();
		if (image == null || book == null) {
			if (empty)
				panel.add(emptyPage.getContainerPanel());
			else
				panel.add(slider.getContainerPanel());
		} else {
			bookDetails.updateUpWith(image, book);
			panel.add(bookDetails.getContainerPanel());
		}
		panel.revalidate();
		panel.repaint();
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	BookDetailsPage getBookDetailsPage() {
		return bookDetails;
	}

	JPanel getPanel() {
		return panel;
	}

	SliderPage getSlider() {
		return slider;
	}

	public boolean isEmpty() {
		return empty;
	}
}
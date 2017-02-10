package com.ELSE.view;

import java.awt.Component;
import java.awt.Image;

import javax.swing.JPanel;

import com.ELSE.model.BookMetadata;

class Center {
	static Center newInstance(final Component parent) {
		return new Center(parent);
	}
	
	private final BookDetailsPage bookDetails;
	private boolean empty;
	private final EmptyPage emptyPage;
	private final JPanel panel;
	private final SliderPage slider;
	
	private Center(final Component parent) {
		emptyPage = EmptyPage.newInstance();
		slider = SliderPage.newInstance();
		bookDetails = BookDetailsPage.newInstance();
		panel = JInvisiblePanel.newInstance(parent);
		empty = true;
	}
	
	void change(final Image image, final BookMetadata book) {
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
	
	public void setEmpty(final boolean empty) {
		this.empty = empty;
	}
}
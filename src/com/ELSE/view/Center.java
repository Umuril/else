package com.ELSE.view;

import java.awt.Component;
import java.awt.Image;

import javax.swing.JPanel;

import com.ELSE.model.BookMetadata;

/**
 * Classe che gestisce i pannelli centrali
 * 
 * @author eddy
 */
class Center {
	/**
	 * Metodo statico che restituisce una nuova istanza di Center
	 * 
	 * @param parent
	 *            pannello padre
	 * @return un nuovo oggetto
	 */
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
	
	/**
	 * Metodo che cambia tra i vari pannelli centrali a seconda dei parametri
	 * 
	 * @param image
	 *            immagine del libro selezionato (se presente, null altrimenti)
	 * @param book
	 *            libro selezionato (se presente, null altrimenti)
	 */
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
	
	/**
	 * @return pannello contenente le informazioni di un singolo libro
	 */
	BookDetailsPage getBookDetailsPage() {
		return bookDetails;
	}
	
	/**
	 * @return pannello contenitore centrale
	 */
	JPanel getPanel() {
		return panel;
	}
	
	/**
	 * @return pannello di visualizzazione dei vari libri presenti nella biblioteca
	 */
	SliderPage getSlider() {
		return slider;
	}
	
	/**
	 * @return vero se non ci sono libri nella biblioteca
	 */
	public boolean isEmpty() {
		return empty;
	}
	
	/**
	 * Metodo che indica se non ci sono libri da visualizzare
	 * 
	 * @param empty
	 *            boolean che indica se non ci sono libri
	 */
	public void setEmpty(final boolean empty) {
		this.empty = empty;
	}
}
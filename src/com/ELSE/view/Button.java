package com.ELSE.view;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Classe che implementa un bottone standard con una immagine
 * 
 * @author eddy
 */
class Button {
	/**
	 * Metodo statico che restituisce una nuova istanza di Button
	 * 
	 * @param url
	 *            percorso dell'immagine del bottone
	 * @return un nuovo oggetto
	 */
	static JButton newInstance(final URL url) {
		final JButton button = new JButton(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
		button.setBackground(null);
		button.setBorder(null);
		button.setContentAreaFilled(false);
		return button;
	}
	
	private Button() {
		throw new AssertionError();
	}
}

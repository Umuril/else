package com.ELSE.view;

import java.awt.Component;

import javax.swing.JPanel;

/**
 * Classe che implementa un pannello senza sfondo
 * 
 * @author eddy
 */
class JInvisiblePanel {
	/**
	 * Metodo statico che restituisce una nuova istanza di JInvisiblePanel
	 * 
	 * @param component
	 *            pannello padre
	 * @return un nuovo oggetto
	 */
	static JPanel newInstance(final Component component) {
		final JPanel panel = new JPanel();
		panel.setBackground(component.getBackground());
		return panel;
	}
	
	private JInvisiblePanel() {
		throw new AssertionError();
	}
}
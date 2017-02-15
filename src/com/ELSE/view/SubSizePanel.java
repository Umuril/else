package com.ELSE.view;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Pannello con dimensioni predefinite per il contenuto del pannello centrale
 * 
 * @author eddy
 */
class SubSizePanel {
	/**
	 * Metodo statico che restituisce una nuova istanza di SubSizePanel
	 * 
	 * @param parent
	 *            pannello padre
	 * @return un nuovo oggetto
	 */
	public static JPanel newInstance(final Component parent) {
		final JPanel panel = JInvisiblePanel.newInstance(parent);
		panel.setMaximumSize(new Dimension(2000, 1));
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		return panel;
	}
	
	private SubSizePanel() {
		throw new AssertionError();
	}
}

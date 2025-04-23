package com.ELSE.view;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Classe che implementa un pannello con una barra di ricerca e un bottone
 * 
 * @author eddy
 */
class SearchBar {
	/**
	 * Metodo statico che restituisce una nuova istanza di SearchBar
	 * 
	 * @return un nuovo oggetto
	 */
	static SearchBar newInstance() {
		return new SearchBar();
	}
	
	private final JButton icona;
	private final JPanel panel;
	private final JTextField testo;
	
	private SearchBar() {
		panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder(new CompoundBorder(new LineBorder(Color.black, 1), new EmptyBorder(5, 5, 5, 5)));
		testo = new JTextField(15);
		testo.setBorder(new EmptyBorder(0, 20, 0, 20));
		panel.add(testo);
		icona = Button.newInstance(SearchBar.class.getResource("/search.png"));
		panel.add(icona);
	}
	
	/**
	 * @return bottone del tasto di ricerca
	 */
	JButton getIcona() {
		return icona;
	}
	
	/**
	 * @return pannello contenitore
	 */
	JPanel getPanel() {
		return panel;
	}
	
	/**
	 * @return JtextField relativo alla ricerca
	 */
	JTextField getTesto() {
		return testo;
	}
}
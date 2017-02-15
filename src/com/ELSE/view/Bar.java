package com.ELSE.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ELSE.model.Utils;

/**
 * Classe che implementa la visuale di una barra con bottoni generica. I bottoni sono divisi tra due pannelli, uno sinistro e uno destro
 * 
 * @author eddy
 */
public class Bar {
	/**
	 * Metodo statico che restituisce una nuova istanza di Bar
	 * 
	 * @return un nuovo oggetto
	 */
	static Bar newInstance() {
		return new Bar();
	}
	
	private final JPanel barContainer, left, right;
	
	private Bar() {
		barContainer = new JPanel();
		barContainer.setBackground(new Color(Integer.parseInt(Utils.getPreferences("Color2"))));
		barContainer.setBorder(new EmptyBorder(5, 5, 5, 5));
		barContainer.setLayout(new BorderLayout());
		left = JInvisiblePanel.newInstance(barContainer);
		right = JInvisiblePanel.newInstance(barContainer);
		barContainer.add(left, BorderLayout.WEST);
		barContainer.add(right, BorderLayout.EAST);
	}
	
	/**
	 * Metodo che restituisce il pannello della barra
	 * 
	 * @return jpanel della barra stessa
	 */
	JPanel getBarContainer() {
		return barContainer;
	}
	
	/**
	 * @return pannello sinistro
	 */
	JPanel getLeft() {
		return left;
	}
	
	/**
	 * @return pannello destro
	 */
	JPanel getRight() {
		return right;
	}
	
	/**
	 * Metodo che aggiorna il colore dello sfondo
	 * 
	 * @param color
	 *            nuovo colore dello sfondo
	 */
	public void updateColor(final Color color) {
		barContainer.setBackground(color);
		barContainer.revalidate();
		barContainer.repaint();
	}
}
package com.ELSE.view;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

class SearchBar {
	private JPanel panel;
	private JTextField testo;
	private JButton icona;

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

	static SearchBar newInstance() {
		return new SearchBar();
	}

	JPanel getPanel() {
		return panel;
	}

	JTextField getTesto() {
		return testo;
	}

	JButton getIcona() {
		return icona;
	}
}
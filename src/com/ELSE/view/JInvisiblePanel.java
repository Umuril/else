package com.ELSE.view;

import java.awt.Component;

import javax.swing.JPanel;

class JInvisiblePanel {
	static JPanel newInstance(final Component component) {
		final JPanel panel = new JPanel();
		panel.setBackground(component.getBackground());
		return panel;
	}
	
	private JInvisiblePanel() {
		throw new AssertionError();
	}
}
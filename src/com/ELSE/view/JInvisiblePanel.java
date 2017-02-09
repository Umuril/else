package com.ELSE.view;

import java.awt.Component;

import javax.swing.JPanel;

class JInvisiblePanel {
	static JPanel newInstance(Component component) {
		JPanel panel = new JPanel();
		panel.setBackground(component.getBackground());
		return panel;
	}

	private JInvisiblePanel() {
		throw new AssertionError();
	}
}
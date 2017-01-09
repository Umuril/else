package com.ELSE.view;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

class SubSizePanel {

	private SubSizePanel() {
		throw new AssertionError();
	}

	public static JPanel newInstance(Component container) {
		JPanel panel = JInvisiblePanel.newInstance(container);
		panel.setMaximumSize(new Dimension(2000, 1)); // Will need other numbers
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		return panel;
	}
}

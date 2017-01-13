package com.ELSE.view;

import java.awt.Component;

import javax.swing.JPanel;

class CentralSizePanel {
	private CentralSizePanel() {
		throw new AssertionError();
	}

	public static JPanel newInstance(Component container) {
		JPanel panel = JInvisiblePanel.newInstance(container);
		// panel.setMinimumSize(new Dimension(940, 300));
		// panel.setPreferredSize(new Dimension(940, 400));
		// panel.setMaximumSize(new Dimension(940, 300));
		return panel;
	}
}

package com.ELSE.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

class Bar {
	private JPanel barContainer, left, right;

	private Bar() {
		barContainer = new JPanel();
		barContainer.setBackground(Color.decode("#cbc4a7"));
		barContainer.setBorder(new EmptyBorder(5, 5, 5, 5));
		barContainer.setLayout(new BorderLayout());
		left = JInvisiblePanel.newInstance(barContainer);
		right = JInvisiblePanel.newInstance(barContainer);
		barContainer.add(left, BorderLayout.WEST);
		barContainer.add(right, BorderLayout.EAST);
	}

	static Bar newInstance() {
		return new Bar();
	}

	JPanel getBarContainer() {
		return barContainer;
	}

	JPanel getLeft() {
		return left;
	}

	JPanel getRight() {
		return right;
	}
}
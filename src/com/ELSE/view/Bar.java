package com.ELSE.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ELSE.model.Utils;

public class Bar {
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
	
	JPanel getBarContainer() {
		return barContainer;
	}
	
	JPanel getLeft() {
		return left;
	}
	
	JPanel getRight() {
		return right;
	}
	
	public void updateColor(final Color color) {
		barContainer.setBackground(color);
		barContainer.revalidate();
		barContainer.repaint();
	}
}
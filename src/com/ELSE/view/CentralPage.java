package com.ELSE.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

class CentralPage {
	private CentralPage() {
		throw new AssertionError();
	}

	static JPanel newInstance(CentralProperties components) {
		JPanel container = new JPanel();
		container.setBackground(Color.white);
		container.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));
		container.setMinimumSize(new Dimension(960, 400));
		container.setPreferredSize(new Dimension(960, 400));
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		JPanel up = components.initUp(container);
		container.add(up);
		JPanel down = components.initDown(container);
		container.add(down);
		return container;
	}
}
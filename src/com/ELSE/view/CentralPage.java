package com.ELSE.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.ELSE.model.Utils;

class CentralPage {
	static JPanel newInstance(CentralProperties centralProperties) {
		JPanel container = new JPanel();
		container.setBackground(new Color(Integer.parseInt(Utils.getPreferences("BackColor"))));
		container.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));
		container.setMinimumSize(new Dimension(960, 400));
		container.setPreferredSize(new Dimension(960, 400));
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		JPanel up = centralProperties.initUp(container);
		container.add(up);
		JPanel down = centralProperties.initDown(container);
		container.add(down);
		return container;
	}

	private CentralPage() {
		throw new AssertionError();
	}
}
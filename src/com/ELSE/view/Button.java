package com.ELSE.view;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

class Button {
	static JButton newInstance(URL url) {
		JButton button = new JButton(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
		button.setBackground(null);
		button.setBorder(null);
		button.setContentAreaFilled(false);
		return button;
	}

	private Button() {
		throw new AssertionError();
	}
}

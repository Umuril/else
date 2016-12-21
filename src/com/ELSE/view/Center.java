package com.ELSE.view;

import java.awt.Component;

import javax.swing.JPanel;

class Center {

	private JPanel panel;

	private SliderPage slider;

	private Center(Component parent) {
		slider = SliderPage.newInstance();

		panel = JInvisiblePanel.newInstance(parent);
		panel.add(slider.getContainerPanel());
	}

	static Center newInstance(Component parent) {
		return new Center(parent);
	}

	JPanel getPanel() {
		return panel;
	}

	SliderPage getSlider() {
		return slider;
	}

}
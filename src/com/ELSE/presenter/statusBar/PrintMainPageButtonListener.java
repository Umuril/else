package com.ELSE.presenter.statusBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ELSE.model.Model;

public class PrintMainPageButtonListener implements ActionListener {

	Model model;

	public PrintMainPageButtonListener(Model model) {
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.getLibrary().print();
	}
}

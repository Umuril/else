package com.ELSE.presenter.statusBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ELSE.model.Model;
import com.ELSE.presenter.center.CenterPresenter;

public class LoadMainPageButtonListener implements ActionListener {

	private Model model;
	private CenterPresenter center;

	public LoadMainPageButtonListener(Model model, CenterPresenter center) {
		this.model = model;
		this.center = center;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.loadPathbaseFile();
		center.aggiorna();
	}

}

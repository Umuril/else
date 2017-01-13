package com.ELSE.presenter.statusBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ELSE.presenter.center.CenterPresenter;

public class UpdateMainPageButtonListener implements ActionListener {
	private CenterPresenter centerPresenter;

	public UpdateMainPageButtonListener(CenterPresenter centerPresenter) {
		this.centerPresenter = centerPresenter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		centerPresenter.aggiorna();
	}
}

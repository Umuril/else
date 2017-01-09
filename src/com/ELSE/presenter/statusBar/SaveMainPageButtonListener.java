package com.ELSE.presenter.statusBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveMainPageButtonListener implements ActionListener {

	private StatusBarPresenter statusBarPresenter;

	public SaveMainPageButtonListener(StatusBarPresenter statusBarPresenter) {
		this.statusBarPresenter = statusBarPresenter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		statusBarPresenter.createMetadataFile();
	}

}

package com.ELSE.presenter.statusBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ELSE.view.View;

public class SaveMainPageButtonListener implements ActionListener {
	private View view;
	private StatusBarPresenter statusBarPresenter;

	public SaveMainPageButtonListener(View view, StatusBarPresenter statusBarPresenter) {
		this.view = view;
		this.statusBarPresenter = statusBarPresenter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		statusBarPresenter.createMetadataFile();
		view.needToSave(false);
	}
}

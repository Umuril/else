package com.ELSE.presenter.center;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerPreviousBooks implements ActionListener {
	private CenterPresenter centerPresenter;

	public ListenerPreviousBooks(CenterPresenter centerPresenter) {
		this.centerPresenter = centerPresenter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		centerPresenter.loadPreviousBooks();
	}
}

package com.ELSE.presenter.center;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerForwardBooks implements ActionListener {
	private CenterPresenter centerPresenter;

	public ListenerForwardBooks(CenterPresenter centerPresenter) {
		this.centerPresenter = centerPresenter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Loading next books...");
		centerPresenter.loadNextBooks();
	}
}

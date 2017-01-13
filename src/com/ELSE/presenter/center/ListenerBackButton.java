package com.ELSE.presenter.center;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ELSE.view.View;

public class ListenerBackButton implements ActionListener {
	private CenterPresenter centerPresenter;
	private View view;

	public ListenerBackButton(CenterPresenter centerPresenter, View view) {
		this.centerPresenter = centerPresenter;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		centerPresenter.change(null, null);
		view.setBookPageEditable(false);
	}
}

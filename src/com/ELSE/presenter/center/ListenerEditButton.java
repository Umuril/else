package com.ELSE.presenter.center;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ELSE.view.View;

public class ListenerEditButton implements ActionListener {
	private View view;

	public ListenerEditButton(View view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		view.changeBookPageEditable();
	}
}

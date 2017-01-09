package com.ELSE.presenter.menuBar;

import java.awt.event.ActionListener;

import javax.swing.JTextField;

import com.ELSE.model.Model;
import com.ELSE.view.View;

public class MenuBarPresenter {

	private View view;
	private Model model;

	public MenuBarPresenter(View view, Model model) {
		this.view = view;
		this.model = model;
	}

	public ActionListener settings() {
		return new SettingsListener();
	}

	public ActionListener search(JTextField testo) {
		return new SearchListener(model, testo);
	}

	public ActionListener advSearch() {
		return new AdvanceSearchListener(view);
	}

}

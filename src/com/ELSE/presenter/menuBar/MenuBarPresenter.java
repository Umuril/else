package com.ELSE.presenter.menuBar;

import java.awt.event.ActionListener;

import javax.swing.JTextField;

import com.ELSE.model.Model;
import com.ELSE.presenter.Presenter;
import com.ELSE.view.View;

public class MenuBarPresenter {
	private Model model;
	private Presenter presenter;

	public MenuBarPresenter(View view, Model model, Presenter presenter) {
		this.model = model;
		this.presenter = presenter;
	}

	public ActionListener settings() {
		return new SettingsListener();
	}

	public ActionListener search(JTextField testo) {
		return new SearchListener(model, presenter.getCenterPresenter(), testo);
	}

	public ActionListener advSearch() {
		return new AdvanceSearchListener(presenter.getCenterPresenter(), model);
	}
}

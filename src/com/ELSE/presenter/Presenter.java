package com.ELSE.presenter;

import com.ELSE.model.Model;
import com.ELSE.presenter.center.CenterPresenter;
import com.ELSE.presenter.menuBar.MenuBarPresenter;
import com.ELSE.presenter.statusBar.StatusBarPresenter;
import com.ELSE.view.View;

public class Presenter {

	private MenuBarPresenter menuBarPresenter;
	private CenterPresenter centerPresenter;
	private StatusBarPresenter statusBarPresenter;

	public Presenter(View view, Model model) {
		menuBarPresenter = new MenuBarPresenter(view, model, this);
		centerPresenter = new CenterPresenter(view, model);
		statusBarPresenter = new StatusBarPresenter(view, model, this);
	}

	public MenuBarPresenter getMenuBarPresenter() {
		return menuBarPresenter;
	}

	public CenterPresenter getCenterPresenter() {
		return centerPresenter;
	}

	public StatusBarPresenter getStatusBarPresenter() {
		return statusBarPresenter;
	}
}

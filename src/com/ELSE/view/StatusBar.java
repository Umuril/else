package com.ELSE.view;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.ELSE.presenter.Presenter;

class StatusBar {

	private Bar bar;
	private JLabel statusText;
	private JButton add, update, save, load, print;

	private StatusBar() {
		bar = Bar.newInstance();

		statusText = new JLabel();
		bar.getLeft().add(statusText);

		add = Button.newInstance(StatusBar.class.getResource("/add.png"));

		bar.getRight().add(add);

		update = Button.newInstance(StatusBar.class.getResource("/update.png"));

		bar.getRight().add(update);

		save = Button.newInstance(StatusBar.class.getResource("/save.png"));

		bar.getRight().add(save);

		load = Button.newInstance(StatusBar.class.getResource("/load.png"));
		bar.getRight().add(load);

		print = Button.newInstance(StatusBar.class.getResource("/print.png"));

		bar.getRight().add(print);
	}

	static StatusBar newInstance() {
		return new StatusBar();
	}

	JButton getAddButton() {
		return add;
	}

	JButton getUpdateButton() {
		return update;
	}

	JButton getSaveButton() {
		return save;
	}

	JButton getLoadButton() {
		return load;
	}

	JButton getPrintButton() {
		return print;
	}

	Bar getBar() {
		return bar;
	}

	void setStatusText(String s) {
		statusText.setText(s);
	}

	void setPresenter(Presenter presenter) {
		add.addActionListener(presenter.getStatusBarPresenter()
				.addMainPageButton());
		update.addActionListener(presenter.getStatusBarPresenter()
				.updateMainPageButton());
		save.addActionListener(presenter.getStatusBarPresenter()
				.saveMainPageButton());
		load.addActionListener(presenter.getStatusBarPresenter()
				.loadMainPageButton());
		print.addActionListener(presenter.getStatusBarPresenter()
				.printMainPageButton());
	}
}

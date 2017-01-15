package com.ELSE.view;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.ELSE.presenter.Presenter;

class StatusBar {
	private Bar bar;
	private JLabel statusText;
	private JButton add, remove, update, save, load, print;

	private StatusBar() {
		bar = Bar.newInstance();
		statusText = new JLabel();
		bar.getLeft().add(statusText);
		add = Button.newInstance(StatusBar.class.getResource("/add.png"));
		bar.getRight().add(add);
		remove = Button.newInstance(StatusBar.class.getResource("/remove.png"));
		bar.getRight().add(remove);
		update = Button.newInstance(StatusBar.class.getResource("/update.png"));
		bar.getRight().add(update);
		save = Button.newInstance(StatusBar.class.getResource("/save.png"));
		bar.getRight().add(save);
		load = Button.newInstance(StatusBar.class.getResource("/load.png"));
		bar.getRight().add(load);
		print = Button.newInstance(StatusBar.class.getResource("/print_gray.png"));
		bar.getRight().add(print);
	}

	static StatusBar newInstance() {
		return new StatusBar();
	}

	JButton getAddButton() {
		return add;
	}

	JButton getRemoveButton() {
		return remove;
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

	public void needToSave(boolean need) {
		if (need)
			save.setIcon(new ImageIcon(new ImageIcon(StatusBar.class.getResource("/save_red.png")).getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
		else
			save.setIcon(new ImageIcon(new ImageIcon(StatusBar.class.getResource("/save.png")).getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
	}

	void setPresenter(Presenter presenter) {
		add.addActionListener(presenter.getStatusBarPresenter().addMainPageButton());
		remove.addActionListener(presenter.getStatusBarPresenter().removeMainPageButton());
		update.addActionListener(presenter.getStatusBarPresenter().updateMainPageButton());
		save.addActionListener(presenter.getStatusBarPresenter().saveMainPageButton());
		load.addActionListener(presenter.getStatusBarPresenter().loadMainPageButton());
		print.addActionListener(presenter.getStatusBarPresenter().printMainPageButton());
	}
}

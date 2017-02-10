package com.ELSE.view;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.ELSE.model.Utils;
import com.ELSE.presenter.Presenter;

public class StatusBar {
	static StatusBar newInstance() {
		return new StatusBar();
	}
	
	private final JButton add, remove, update, save, load, print;
	private final Bar bar;
	private final JLabel statusText;
	private Thread thread;
	
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
		print = Button.newInstance(StatusBar.class.getResource("/print.png"));
		bar.getRight().add(print);
	}
	
	public JButton getAddButton() {
		return add;
	}
	
	public Bar getBar() {
		return bar;
	}
	
	public JButton getLoadButton() {
		return load;
	}
	
	public JButton getPrintButton() {
		return print;
	}
	
	public JButton getRemoveButton() {
		return remove;
	}
	
	public JButton getSaveButton() {
		return save;
	}
	
	public JButton getUpdateButton() {
		return update;
	}
	
	void needToSave(final boolean need) {
		if (!Boolean.parseBoolean(Utils.getPreferences("Save")))
			save.setIcon(new ImageIcon(new ImageIcon(StatusBar.class.getResource(need ? "/save_red.png" : "/save.png")).getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
	}
	
	void setPresenter(final Presenter presenter) {
		add.addActionListener(presenter.getStatusBarPresenter());
		remove.addActionListener(presenter.getStatusBarPresenter());
		update.addActionListener(presenter.getStatusBarPresenter());
		save.addActionListener(presenter.getStatusBarPresenter());
		load.addActionListener(presenter.getStatusBarPresenter());
		print.addActionListener(presenter.getStatusBarPresenter());
	}
	
	void setStatusText(final String text) {
		if (thread == null || !thread.isAlive()) {
			thread = new Thread(new Runnable() {
				@Override
				public void run() {
					statusText.setText(text);
					try {
						Thread.sleep(5000);
					} catch (final InterruptedException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
					statusText.setText("");
				}
			});
			thread.start();
		}
	}
}

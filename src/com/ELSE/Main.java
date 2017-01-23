package com.ELSE;

import javax.swing.SwingUtilities;

import com.ELSE.model.Model;
import com.ELSE.presenter.Presenter;
import com.ELSE.view.View;

/**
 * @author Eduard Rubio Cholbi
 */
public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				View view = new View();
				view.setPresenter(new Presenter(view, new Model()));
			}
		});
	}
}

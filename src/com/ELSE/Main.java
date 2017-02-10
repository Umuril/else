package com.ELSE;

import javax.swing.SwingUtilities;

import com.ELSE.model.Model;
import com.ELSE.presenter.Presenter;
import com.ELSE.view.View;

/**
 * @author Eduard Rubio Cholbi
 */
public class Main {
	public static void main(final String[] args) {
		System.out.println(com.sun.javafx.runtime.VersionInfo.getRuntimeVersion());
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final View view = new View();
				view.setPresenter(new Presenter(view, new Model()));
			}
		});
	}
}

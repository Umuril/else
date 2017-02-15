package com.ELSE;

import javax.swing.SwingUtilities;

import com.ELSE.model.Model;
import com.ELSE.presenter.Presenter;
import com.ELSE.view.View;

/**
 * Libreria digitale che permette di leggere e gestire i libri in formato Ebook
 * 
 * @author Eduard Rubio Cholbi
 */
public class Main {
	@SuppressWarnings("javadoc")
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final View view = new View();
				view.setPresenter(new Presenter(view, new Model()));
			}
		});
	}
}

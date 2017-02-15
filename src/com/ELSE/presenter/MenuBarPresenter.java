package com.ELSE.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map.Entry;
import java.util.Objects;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.ELSE.model.BookMetadata;
import com.ELSE.model.Model;
import com.ELSE.model.Utils;
import com.ELSE.view.MenuBar;
import com.ELSE.view.View;

/**
 * Classe che gestisce il presenter dei bottoni nella barra superiore
 * 
 * @author eddy
 */
public class MenuBarPresenter implements ActionListener, DocumentListener {
	private final AdvanceSearchPresenter advanceSearchPresenter;
	private final CenterPresenter centerPresenter;
	private final Model model;
	private final SettingsPresenter settingsPresenter;
	private final View view;
	
	/**
	 * Costruttore
	 * 
	 * @param view
	 *            Vista generica del progetto
	 * @param model
	 *            Modello generico del progetto
	 * @param centerPresenter
	 *            Presenter relativo al pannello centrale
	 */
	MenuBarPresenter(final View view, final Model model, final CenterPresenter centerPresenter) {
		this.model = model;
		this.view = view;
		this.centerPresenter = centerPresenter;
		advanceSearchPresenter = new AdvanceSearchPresenter(view, model, centerPresenter);
		settingsPresenter = new SettingsPresenter(view, model, centerPresenter);
	}
	
	@Override
	public void actionPerformed(final ActionEvent action) {
		final MenuBar menuBar = view.getMenuBar();
		if (action.getSource() == menuBar.getSettingsButton())
			view.setSettingsVisible();
		else if (action.getSource() == menuBar.getSearchButton() || action.getSource() == menuBar.getSearchField())
			cerca();
		else if (action.getSource() == menuBar.getAdvanceSearchButton())
			view.setAdvanceSearchVisible();
	}
	
	private void cerca() {
		if (centerPresenter.isUpdating())
			return;
		Utils.log(Utils.Debug.DEBUG, model.getLibrary().getDatabase().values().size());
		Utils.log(Utils.Debug.DEBUG, model.getLibrary().getDatabase().values());
		centerPresenter.change(null, null);
		final String text = view.getMenuBar().getSearchField().getText();
		if (!Utils.validString(text)) {
			centerPresenter.aggiorna(0);
			return;
		}
		view.enableNextButton(false);
		Utils.log(Utils.Debug.INFO, "Search triggered with " + text + ".");
		Utils.log(Utils.Debug.DEBUG, model.getLibrary().getDatabase().values());
		centerPresenter.removeAllBooks();
		for (final BookMetadata book : model.getLibrary().getDatabase().values()) {
			boolean found = false;
			if (Utils.validString(book.getTitolo())) {
				if (book.getTitolo().contains(text))
					found = true;
			} else if (Utils.validString(book.getAutore())) {
				if (book.getAutore().contains(text))
					found = true;
			} else if (Utils.validYear(book.getAnno())) {
				if (book.getAnno().toString().contains(text))
					found = true;
			} else if (book.getPagine() > 0)
				if (String.valueOf(book.getPagine()).contains(text))
					found = true;
			if (found) {
				Utils.log(Utils.Debug.INFO, "Found: " + book);
				try {
					for (final Entry<Path, BookMetadata> entry : model.getLibrary().getDatabase().entrySet())
						if (Objects.equals(book, entry.getValue())) {
							centerPresenter.addImage(entry.getKey());
							break;
						}
				} catch (final IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void changedUpdate(final DocumentEvent event) {
		cerca();
	}
	
	/**
	 * @return Presenter del bottone di ricerca avanzata
	 */
	public AdvanceSearchPresenter getAdvanceSearchPresenter() {
		return advanceSearchPresenter;
	}
	
	/**
	 * @return Presenter del bottone di impostazioni
	 */
	public SettingsPresenter getSettingsPresenter() {
		return settingsPresenter;
	}
	
	@Override
	public void insertUpdate(final DocumentEvent event) {
		cerca();
	}
	
	@Override
	public void removeUpdate(final DocumentEvent event) {
		cerca();
	}
}

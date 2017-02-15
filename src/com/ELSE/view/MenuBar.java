package com.ELSE.view;

import javax.swing.JButton;
import javax.swing.JTextField;

import com.ELSE.presenter.Presenter;

/**
 * Classe che implementa la visuale della barra superiore del frame principale
 * 
 * @author eddy
 */
public class MenuBar {
	/**
	 * Metodo statico che restituisce una nuova istanza di MenuBar
	 * 
	 * @return un nuovo oggetto
	 */
	static MenuBar newInstance() {
		return new MenuBar();
	}
	
	private final Bar parent;
	private final SearchBar searchBar;
	private final JButton settings, advSearch;
	
	private MenuBar() {
		parent = Bar.newInstance();
		settings = Button.newInstance(MenuBar.class.getResource("/settings.png"));
		parent.getLeft().add(settings);
		searchBar = SearchBar.newInstance();
		parent.getRight().add(searchBar.getPanel());
		advSearch = Button.newInstance(MenuBar.class.getResource("/advsearch.png"));
		parent.getRight().add(advSearch);
	}
	
	/**
	 * @return bottone per la ricerca avanzata
	 */
	public JButton getAdvanceSearchButton() {
		return advSearch;
	}
	
	/**
	 * @return pannello contenente la barra superiore
	 */
	public Bar getParent() {
		return parent;
	}
	
	/**
	 * @return bottone di ricerca
	 */
	public JButton getSearchButton() {
		return searchBar.getIcona();
	}
	
	/**
	 * @return jtextfield della ricerca
	 */
	public JTextField getSearchField() {
		return searchBar.getTesto();
	}
	
	/**
	 * @return bottone per le impostazioni
	 */
	public JButton getSettingsButton() {
		return settings;
	}
	
	/**
	 * Metodo che aggiunge i listener nei bottoni della barra superiore
	 * 
	 * @param presenter
	 *            Presenter generale del progetto
	 */
	void setPresenter(final Presenter presenter) {
		settings.addActionListener(presenter.getMenuBarPresenter());
		searchBar.getTesto().addActionListener(presenter.getMenuBarPresenter());
		searchBar.getTesto().getDocument().addDocumentListener(presenter.getMenuBarPresenter());
		searchBar.getIcona().addActionListener(presenter.getMenuBarPresenter());
		advSearch.addActionListener(presenter.getMenuBarPresenter());
	}
}

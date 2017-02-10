package com.ELSE.view;

import javax.swing.JButton;
import javax.swing.JTextField;

import com.ELSE.presenter.Presenter;

public class MenuBar {
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
	
	public JButton getAdvanceSearchButton() {
		return advSearch;
	}
	
	public Bar getParent() {
		return parent;
	}
	
	public JButton getSearchButton() {
		return searchBar.getIcona();
	}
	
	public JTextField getSearchField() {
		return searchBar.getTesto();
	}
	
	public JButton getSettingsButton() {
		return settings;
	}
	
	void setPresenter(final Presenter presenter) {
		settings.addActionListener(presenter.getMenuBarPresenter());
		searchBar.getTesto().addActionListener(presenter.getMenuBarPresenter());
		searchBar.getTesto().getDocument().addDocumentListener(presenter.getMenuBarPresenter());
		searchBar.getIcona().addActionListener(presenter.getMenuBarPresenter());
		advSearch.addActionListener(presenter.getMenuBarPresenter());
	}
}

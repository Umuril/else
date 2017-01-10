package com.ELSE.view;

import javax.swing.JButton;

import com.ELSE.presenter.Presenter;

class MenuBar {

	private Bar parent;

	private JButton settings;
	private SearchBar searchBar;
	private JButton advSearch;

	private MenuBar() {
		parent = Bar.newInstance();

		settings = Button.newInstance(MenuBar.class
				.getResource("/settings.png"));
		parent.getLeft().add(settings);

		searchBar = SearchBar.newInstance();
		parent.getRight().add(searchBar.getPanel());

		advSearch = Button.newInstance(MenuBar.class
				.getResource("/advsearch.png"));
		parent.getRight().add(advSearch);
	}

	static MenuBar newInstance() {
		return new MenuBar();
	}

	Bar getParent() {
		return parent;
	}

	JButton getSettings() {
		return settings;
	}

	SearchBar getSearchBar() {
		return searchBar;
	}

	JButton getAdvSearch() {
		return advSearch;
	}

	void setPresenter(Presenter presenter) {
		settings.addActionListener(presenter.getMenuBarPresenter().settings());
		searchBar.getTesto().addActionListener(
				presenter.getMenuBarPresenter().search(searchBar.getTesto()));
		searchBar.getIcona().addActionListener(
				presenter.getMenuBarPresenter().search(searchBar.getTesto()));
		advSearch.addActionListener(presenter.getMenuBarPresenter().advSearch());
	}
}

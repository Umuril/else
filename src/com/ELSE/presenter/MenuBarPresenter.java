package com.ELSE.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.ELSE.model.BookMetadata;
import com.ELSE.model.Model;
import com.ELSE.model.Utils;
import com.ELSE.view.MenuBar;
import com.ELSE.view.View;

public class MenuBarPresenter implements ActionListener, DocumentListener {
	private final Model model;
	private final View view;
	private final CenterPresenter centerPresenter;
	private final AdvanceSearchPresenter advanceSearchPresenter;
	private final SettingsPresenter settingsPresenter;

	public MenuBarPresenter(View view, Model model, CenterPresenter centerPresenter) {
		this.model = model;
		this.view = view;
		this.centerPresenter = centerPresenter;
		this.advanceSearchPresenter = new AdvanceSearchPresenter(view, model, centerPresenter);
		this.settingsPresenter = new SettingsPresenter(view, model, centerPresenter);
	}

	public AdvanceSearchPresenter getAdvanceSearchPresenter() {
		return advanceSearchPresenter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MenuBar menuBar = view.getMenuBar();
		if (e.getSource() == menuBar.getSettingsButton()) {
			view.setSettingsVisible();
		} else if (e.getSource() == menuBar.getSearchButton() || e.getSource() == menuBar.getSearchField()) {
			cerca();
		} else if (e.getSource() == menuBar.getAdvanceSearchButton()) {
			view.setAdvanceSearchVisible();
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		cerca();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		cerca();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		cerca();
	}

	private void cerca() {
		if (centerPresenter.isUpdating())
			return;
		System.out.println(model.getLibrary().getDatabase().values().size());
		System.out.println(model.getLibrary().getDatabase().values());
		centerPresenter.change(null, null);
		String text = view.getMenuBar().getSearchField().getText();
		if (text.isEmpty()) {
			centerPresenter.aggiorna(0);
			return;
		}
		view.enableNextButton(false);
		Utils.log(Utils.Debug.INFO, "Search triggered with " + text + ".");
		Utils.log(Utils.Debug.DEBUG, model.getLibrary().getDatabase().values());
		centerPresenter.emptyOfBooks();
		// java.util.ConcurrentModificationException
		// for (BookMetadata book : model.getLibrary().getDatabase().values()) {
		for (Iterator<BookMetadata> iterator = model.getLibrary().getDatabase().values().iterator(); iterator.hasNext();) {
			boolean found = false;
			BookMetadata book = iterator.next();
			if (book.getAnno() != null)
				if (book.getAnno().toString().contains(text))
					found = true;
			if (book.getAutore() != null)
				if (book.getAutore().contains(text))
					found = true;
			if (book.getPagine() > 0)
				if (String.valueOf(book.getPagine()).contains(text))
					found = true;
			if (book.getTitolo() != null)
				if (book.getTitolo().contains(text))
					found = true;
			if (found) {
				Utils.log(Utils.Debug.INFO, "Found: " + book);
				try {
					for (Entry<String, BookMetadata> entry : model.getLibrary().getDatabase().entrySet()) {
						if (Objects.equals(book, entry.getValue())) {
							centerPresenter.addImage(new File(entry.getKey()));
							break;
						}
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	public SettingsPresenter getSettingsPresenter() {
		return settingsPresenter;
	}
}

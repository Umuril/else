package com.ELSE.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.ELSE.model.BookMetadata;
import com.ELSE.model.Model;
import com.ELSE.model.Utils;
import com.ELSE.view.MenuBar;
import com.ELSE.view.View;

public class MenuBarPresenter implements ActionListener, DocumentListener {
	private Model model;
	private View view;
	private CenterPresenter centerPresenter;
	private AdvanceSearchPresenter advanceSearchPresenter;
	private SettingsPresenter settingsPresenter;

	public MenuBarPresenter(View view, Model model, CenterPresenter centerPresenter) {
		this.model = model;
		this.view = view;
		this.centerPresenter = centerPresenter;
		this.advanceSearchPresenter = new AdvanceSearchPresenter(view, model, centerPresenter);
		this.settingsPresenter = new SettingsPresenter(view, model, centerPresenter);
	}
	
	public AdvanceSearchPresenter getAdvanceSearchPresenter(){
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
		if(centerPresenter.isUpdating())
			return;
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
		for (BookMetadata book : model.getLibrary().getDatabase().values()) {
			boolean found = false;
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
					centerPresenter.addImage(book);
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

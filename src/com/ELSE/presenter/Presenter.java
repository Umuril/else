package com.ELSE.presenter;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

import com.ELSE.model.Model;
import com.ELSE.presenter.center.CenterPresenter;
import com.ELSE.presenter.menuBar.MenuBarPresenter;
import com.ELSE.presenter.reader.EPUBReader;
import com.ELSE.presenter.reader.HTMLReader;
import com.ELSE.presenter.reader.PDFReader;
import com.ELSE.presenter.statusBar.StatusBarPresenter;
import com.ELSE.view.View;

public class Presenter {
	private MenuBarPresenter menuBarPresenter;
	private CenterPresenter centerPresenter;
	private StatusBarPresenter statusBarPresenter;

	public Presenter(View view, Model model) {
		menuBarPresenter = new MenuBarPresenter(view, model, this);
		centerPresenter = new CenterPresenter(view, model, this);
		statusBarPresenter = new StatusBarPresenter(view, model, this);
	}

	public MenuBarPresenter getMenuBarPresenter() {
		return menuBarPresenter;
	}

	public CenterPresenter getCenterPresenter() {
		return centerPresenter;
	}

	public StatusBarPresenter getStatusBarPresenter() {
		return statusBarPresenter;
	}

	// This sucks !!!
	public BufferedImage getCover(Path file) {
		if (file.toString().endsWith(".pdf"))
			return new PDFReader(file.toString()).getCover();
		if (file.toString().endsWith(".html"))
			return new HTMLReader(file.toString()).getCover();
		if (file.toString().endsWith(".epub"))
			return new EPUBReader(file.toString()).getCover();
		System.out.println("Formato non supportato. File: " + file.toString());
		return null;
	}

	// This sucks !!!
	public void getReader(Path file) {
		if (file.toString().endsWith(".pdf"))
			new PDFReader(file.toString()).getFrame();
		if (file.toString().endsWith(".html"))
			new HTMLReader(file.toString()).getFrame();
		if (file.toString().endsWith(".epub"))
			new EPUBReader(file.toString()).getFrame();
	}
}

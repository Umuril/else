package com.ELSE.presenter.center;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

import com.ELSE.presenter.Presenter;

public class ListenerCustomBookPreviewClick implements ActionListener {
	private Path path;
	private Presenter presenter;

	public ListenerCustomBookPreviewClick(Path path, Presenter presenter) {
		this.presenter = presenter;
		this.path = path;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		presenter.getReader(path);
	}
}

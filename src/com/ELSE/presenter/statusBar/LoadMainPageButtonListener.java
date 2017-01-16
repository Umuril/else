package com.ELSE.presenter.statusBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import com.ELSE.model.Model;
import com.ELSE.presenter.center.CenterPresenter;
import com.ELSE.view.View;

public class LoadMainPageButtonListener implements ActionListener {

	private View view;
	private Model model;
	private CenterPresenter center;

	public LoadMainPageButtonListener(View view, Model model,
			CenterPresenter center) {
		this.view = view;
		this.model = model;
		this.center = center;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		int result = jfc.showOpenDialog(view.getFrame());
		if (result == JFileChooser.APPROVE_OPTION)
			model.loadPathbaseFile(jfc.getSelectedFile().getAbsolutePath());
		center.aggiorna(-1);
	}
}

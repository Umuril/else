package com.ELSE.presenter.statusBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import com.ELSE.model.Pathbase;
import com.ELSE.view.View;

public class AddMainPageButtonListener implements ActionListener {

	private View view;
	private Pathbase pathbase;

	public AddMainPageButtonListener(View view, Pathbase pathbase) {
		this.view = view;
		this.pathbase = pathbase;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		view.setStatusText("aggiungiFile");
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int result = jfc.showOpenDialog(view.getFrame());
		if (result == JFileChooser.APPROVE_OPTION)
			pathbase.add(jfc.getSelectedFile().getAbsolutePath());
		// add.addActionListener(new AbstractAction() {
		//
		// private static final long serialVersionUID = 1L;
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// //
		// presenter.getStatusBarPresenter().aggiungiFile();
		// }
		//
		// });
	}

}

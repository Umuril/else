package com.ELSE.presenter.statusBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.ELSE.model.Pathbase;
import com.ELSE.presenter.center.CenterPresenter;
import com.ELSE.view.View;

public class RemoveMainPageButtonListener implements ActionListener {
	private View view;
	private Pathbase pathbase;
	private CenterPresenter centerPresenter;

	public RemoveMainPageButtonListener(View view, Pathbase pathbase, CenterPresenter centerPresenter) {
		this.view = view;
		this.pathbase = pathbase;
		this.centerPresenter = centerPresenter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String input = (String) JOptionPane.showInputDialog(null, "Scegli il percorso da cancellare: ", "Rimuovi percorso", JOptionPane.QUESTION_MESSAGE, null, pathbase.getPathsList().toArray(), pathbase.getPathsList().get(0));
		if (input != null) {
			pathbase.remove(input);
			centerPresenter.aggiorna(0);
			view.needToSave(true);
		}
	}
}

package com.ELSE.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.ELSE.model.Model;
import com.ELSE.model.Pathbase;
import com.ELSE.model.Utils;
import com.ELSE.view.StatusBar;
import com.ELSE.view.View;

class StatusBarPresenter implements ActionListener {
	private final View view;
	private final Model model;
	private final CenterPresenter centerPresenter;

	StatusBarPresenter(View view, Model model, CenterPresenter centerPresenter) {
		this.view = view;
		this.model = model;
		this.centerPresenter = centerPresenter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Utils.log(Utils.Debug.DEBUG, "actionPerformed on StatusBarPresenter");
		StatusBar statusBar = view.getStatusBar();
		if (e.getSource() == statusBar.getAddButton()) {
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int result = jfc.showOpenDialog(view.getFrame());
			if (result == JFileChooser.APPROVE_OPTION) {
				model.getPathbase().add(jfc.getSelectedFile().getAbsolutePath());
				model.searchForNewBooks();
				centerPresenter.aggiorna(0);
				view.needToSave(true);
			}
		} else if (e.getSource() == statusBar.getRemoveButton()) {
			Pathbase pathbase = model.getPathbase();
			if (pathbase.size() > 0) {
				String input = (String) JOptionPane.showInputDialog(null, "Scegli il percorso da cancellare: ", "Rimuovi percorso", JOptionPane.QUESTION_MESSAGE, null, pathbase.getPathsList().toArray(), pathbase.getPathsList().get(0));
				if (input != null) {
					Utils.log(Utils.Debug.DEBUG, "BEFORE REMOVE SIZE: " + pathbase.size());
					Utils.log(Utils.Debug.DEBUG, "BEFORE REMOVE: " + pathbase.getPathsList());
					pathbase.remove(input);
					centerPresenter.aggiorna(0);
					view.needToSave(true);
					Utils.log(Utils.Debug.DEBUG, "AFTER REMOVE SIZE: " + pathbase.size());
				}
			}
		} else if (e.getSource() == statusBar.getUpdateButton()) {
			centerPresenter.aggiorna(-1);
		} else if (e.getSource() == statusBar.getSaveButton()) {
			try {
				model.getPathbase().createPathbaseFile(Paths.get(Utils.getPreferences("Pathbase")));
				model.getLibrary().createFile();
				view.needToSave(false);
			} catch (IOException ex) {
				// TODO
				ex.printStackTrace();
			}
		} else if (e.getSource() == statusBar.getLoadButton()) {
			Utils.log(Utils.Debug.DEBUG, "getLoadButton()");
			JFileChooser jfc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
			jfc.setFileFilter(filter);
			jfc.setAcceptAllFileFilterUsed(false);
			if (jfc.showOpenDialog(view.getFrame()) == JFileChooser.APPROVE_OPTION) {
				int size = model.getPathbase().size();
				try {
					model.getPathbase().loadFromFile(Paths.get(jfc.getSelectedFile().getAbsolutePath()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Utils.log(Utils.Debug.DEBUG, "SIZE 1: " + model.getPathbase().size() + " SIZE 2: " + size);
				if (model.getPathbase().size() > size) {
					Utils.log(Utils.Debug.DEBUG, "SIZE INCREASED");
					view.needToSave(true);
					centerPresenter.aggiorna(-1);
				}
			}
		} else if (e.getSource() == statusBar.getPrintButton()) {
			model.getLibrary().print();
		}
	}
}

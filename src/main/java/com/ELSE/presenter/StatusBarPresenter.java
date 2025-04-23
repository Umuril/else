package com.ELSE.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.ELSE.model.Model;
import com.ELSE.model.PathTree;
import com.ELSE.model.Utils;
import com.ELSE.view.StatusBar;
import com.ELSE.view.View;

/**
 * Classe che gestisce gli eventi dei bottoni nella barra inferiore
 * 
 * @author eddy
 */
class StatusBarPresenter implements ActionListener {
	private final CenterPresenter centerPresenter;
	private final Model model;
	private final View view;
	
	/**
	 * Costruttore
	 * 
	 * @param view
	 *            Vista generale del progetto
	 * @param model
	 *            Modello generale del progetto
	 * @param centerPresenter
	 *            Presenter del pannello centrale
	 */
	StatusBarPresenter(final View view, final Model model, final CenterPresenter centerPresenter) {
		this.view = view;
		this.model = model;
		this.centerPresenter = centerPresenter;
	}
	
	@Override
	public void actionPerformed(final ActionEvent action) {
		Utils.log(Utils.Debug.DEBUG, "actionPerformed on StatusBarPresenter");
		final StatusBar statusBar = view.getStatusBar();
		if (action.getSource() == statusBar.getAddButton()) {
			final JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			if (jfc.showOpenDialog(view.getFrame()) == JFileChooser.APPROVE_OPTION) {
				model.getPathTree().add(jfc.getSelectedFile().getAbsolutePath());
				model.searchForNewBooks();
				centerPresenter.aggiorna(0);
				view.needToSave(true);
			}
		} else if (action.getSource() == statusBar.getRemoveButton()) {
			final PathTree pathtree = model.getPathTree();
			if (pathtree.size() > 0) {
				final String input = (String) JOptionPane.showInputDialog(null, "Scegli il percorso da cancellare: ", "Rimuovi percorso", JOptionPane.QUESTION_MESSAGE, null, pathtree.getPathList().toArray(), pathtree.getPathList().get(0));
				if (input != null) {
					Utils.log(Utils.Debug.DEBUG, "BEFORE REMOVE SIZE: " + pathtree.size());
					Utils.log(Utils.Debug.DEBUG, "BEFORE REMOVE: " + pathtree.getPathList());
					pathtree.remove(input);
					centerPresenter.aggiorna(0);
					view.needToSave(true);
					Utils.log(Utils.Debug.DEBUG, "AFTER REMOVE SIZE: " + pathtree.size());
				}
			}
		} else if (action.getSource() == statusBar.getUpdateButton())
			centerPresenter.aggiorna(-1);
		else if (action.getSource() == statusBar.getSaveButton())
			try {
				model.getPathTree().createPathTreeFile(Paths.get(Utils.getPreferences("PathTree")));
				model.getLibrary().createLibraryFile();
				view.needToSave(false);
			} catch (final IOException ex) {
				// TODO
				ex.printStackTrace();
			}
		else if (action.getSource() == statusBar.getLoadButton()) {
			Utils.log(Utils.Debug.DEBUG, "getLoadButton()");
			final JFileChooser jfc = new JFileChooser();
			jfc.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
			jfc.setAcceptAllFileFilterUsed(false);
			if (jfc.showOpenDialog(view.getFrame()) == JFileChooser.APPROVE_OPTION) {
				final int size = model.getPathTree().size();
				try {
					model.getPathTree().loadFromFile(Paths.get(jfc.getSelectedFile().getAbsolutePath()));
				} catch (final IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				Utils.log(Utils.Debug.DEBUG, "SIZE 1: " + model.getPathTree().size() + " SIZE 2: " + size);
				if (model.getPathTree().size() > size) {
					Utils.log(Utils.Debug.DEBUG, "SIZE INCREASED");
					view.needToSave(true);
					centerPresenter.aggiorna(-1);
				}
			}
		} else if (action.getSource() == statusBar.getPrintButton())
			model.getLibrary().print();
	}
}

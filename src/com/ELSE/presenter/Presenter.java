package com.ELSE.presenter;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JOptionPane;

import com.ELSE.model.Model;
import com.ELSE.model.Utils;
import com.ELSE.presenter.reader.EbookReader;
import com.ELSE.view.View;

public class Presenter implements WindowListener {
	private View view;
	private MenuBarPresenter menuBarPresenter;
	private CenterPresenter centerPresenter;
	private StatusBarPresenter statusBarPresenter;
	private Model model;

	public Presenter(View view, Model model) {
		this.view = view;
		this.model = model;
		centerPresenter = new CenterPresenter(view, model, this);
		menuBarPresenter = new MenuBarPresenter(view, model, centerPresenter);
		statusBarPresenter = new StatusBarPresenter(view, model, centerPresenter);
		updateAllColors();
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

	public BufferedImage getCover(Path file) {
		try {
			return EbookReader.newInstance(file).getCover();
		} catch (IOException e) {
			view.setStatusText("Errore nella lettura del libro");
			return null;
		}
	}

	public void getReader(Path file) {
		Utils.log(Utils.Debug.DEBUG, file);
		try {
			EbookReader.newInstance(file).getFrame();
		} catch (IOException e) {
			view.setStatusText("Errore nella lettura del libro");
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (!view.needSave()){
			view.getFrame().dispose();
			System.exit(0);
		}else {
			if (Boolean.parseBoolean(Utils.getPreferences("Save"))) {
				try{
					model.getPathbase().createPathbaseFile(Utils.getPreferences("Pathbase"));
					model.getLibrary().createFile();
					view.getFrame().dispose();
					System.exit(0);
				} catch (IOException e1) {
					// TODO Need checks
					view.setStatusText("Errore salvataggio prima di chiudere");
				}
			} else {
				int confirmed = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler uscire senza salvare?", "Chiudi senza salvare", JOptionPane.YES_NO_OPTION);
				if (confirmed == JOptionPane.YES_OPTION) {
					view.getFrame().dispose();
					System.exit(0);
				}
			}
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	public void updateAllColors() {
		view.updateColor(new Color(Integer.parseInt(Utils.getPreferences("Color1"))));
		Color color2 = new Color(Integer.parseInt(Utils.getPreferences("Color2")));
		view.getAdvanceSearch().updateColor(color2);
		view.getSettings().updateColor(color2);
		view.getMenuBar().getParent().updateColor(color2);
		view.getStatusBar().getBar().updateColor(color2);
		Color backcolor = new Color(Integer.parseInt(Utils.getPreferences("BackColor")));
		view.getBookDetailsPage().updateColor(backcolor);
		view.getSliderPage().updateColor(backcolor);
	}
}

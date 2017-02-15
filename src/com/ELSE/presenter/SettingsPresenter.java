package com.ELSE.presenter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.prefs.BackingStoreException;

import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.ELSE.model.Model;
import com.ELSE.model.Utils;
import com.ELSE.view.Settings;
import com.ELSE.view.View;

/**
 * Classe che gestisce il presenter del pannello impostazioni
 * 
 * @author eddy
 */
class SettingsPresenter implements ActionListener, ItemListener {
	private static void deleteFolderAndItsContent(final Path folder) throws IOException {
		Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
				if (exc != null)
					throw exc;
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}
		});
	}
	
	private final JDialog dialog;
	private final Settings settings;
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
	SettingsPresenter(final View view, final Model model, final CenterPresenter centerPresenter) {
		this.view = view;
		settings = view.getSettings();
		dialog = settings.getDialog();
	}
	
	@Override
	public void actionPerformed(final ActionEvent action) {
		if (action.getSource() == settings.getColor1())
			Utils.setPreferences("Color1", Integer.toString(JColorChooser.showDialog(null, "Choose a color", new Color(Integer.parseInt(Utils.getPreferences("Color1")))).getRGB()));
		else if (action.getSource() == settings.getColor2())
			Utils.setPreferences("Color2", Integer.toString(JColorChooser.showDialog(null, "Choose a color", new Color(Integer.parseInt(Utils.getPreferences("Color2")))).getRGB()));
		else if (action.getSource() == settings.getBackcolor())
			Utils.setPreferences("BackColor", Integer.toString(JColorChooser.showDialog(null, "Choose a color", new Color(Integer.parseInt(Utils.getPreferences("BackColor")))).getRGB()));
		else if (action.getSource() == settings.getPaths()) {
			final JFileChooser fc = new JFileChooser();
			fc.setMultiSelectionEnabled(false);
			if (fc.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION)
				Utils.setPreferences("PathTree", fc.getSelectedFile().toString());
		} else if (action.getSource() == settings.getFolder()) {
			final JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setMultiSelectionEnabled(false);
			if (fc.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION)
				Utils.setPreferences("Folder", fc.getSelectedFile().toString());
		} else if (action.getSource() == settings.getDelete()) {
			if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(dialog, "Sei sicuro di voler cancellare tutto?", "Cancellazione dati", JOptionPane.YES_NO_OPTION)) {
				try {
					SettingsPresenter.deleteFolderAndItsContent(Paths.get(Utils.getPreferences("Folder")));
				} catch (final IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				view.needToSave(false);
			}
		} else if (action.getSource() == settings.getRestore())
			try {
				Utils.resetPreferences();
			} catch (final BackingStoreException ex) {
				JOptionPane.showMessageDialog(settings.getDialog(), "Couldn't reset preferences", "Fatal error", JOptionPane.ERROR_MESSAGE);
			}
		else if (action.getSource() == settings.getConferma())
			dialog.dispose();
	}
	
	@Override
	public void itemStateChanged(final ItemEvent item) {
		if (item.getSource() == settings.getPreview())
			Utils.setPreferences("Preview", Boolean.toString(item.getStateChange() == ItemEvent.SELECTED));
		else if (item.getSource() == settings.getSave())
			Utils.setPreferences("Save", Boolean.toString(item.getStateChange() == ItemEvent.SELECTED));
	}
}

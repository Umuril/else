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

import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.ELSE.model.Model;
import com.ELSE.model.Utils;
import com.ELSE.view.Settings;
import com.ELSE.view.View;

public class SettingsPresenter implements ActionListener, ItemListener {
	private final View view;
	private final Settings settings;
	private final JDialog dialog;

	public SettingsPresenter(View view, Model model, CenterPresenter centerPresenter) {
		this.view = view;
		settings = view.getSettings();
		dialog = settings.getDialog();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == settings.getColor1()) {
			Utils.setPreferences("Color1", Integer.toString(JColorChooser.showDialog(null, "Choose a color", new Color(Integer.parseInt(Utils.getPreferences("Color1")))).getRGB()));
		} else if (e.getSource() == settings.getColor2()) {
			Utils.setPreferences("Color2", Integer.toString(JColorChooser.showDialog(null, "Choose a color", new Color(Integer.parseInt(Utils.getPreferences("Color2")))).getRGB()));
		} else if (e.getSource() == settings.getBackcolor()) {
			Utils.setPreferences("BackColor", Integer.toString(JColorChooser.showDialog(null, "Choose a color", new Color(Integer.parseInt(Utils.getPreferences("BackColor")))).getRGB()));
		} else if (e.getSource() == settings.getPaths()) {
			JFileChooser fc = new JFileChooser();
			fc.setMultiSelectionEnabled(false);
			if (fc.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION)
				Utils.setPreferences("Pathbase", fc.getSelectedFile().toString());
		} else if (e.getSource() == settings.getFolder()) {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setMultiSelectionEnabled(false);
			if (fc.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
				Utils.setPreferences("Folder", fc.getSelectedFile().toString());
				// System.out.println(fc.getSelectedFile().toString() + " <= " + Utils.getPreferences("Folder"));
			}
		} else if (e.getSource() == settings.getDelete()) {
			if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(dialog, "Sei sicuro di voler cancellare tutto?", "Cancellazione dati", JOptionPane.YES_NO_OPTION)) {
				String path = Utils.getPreferences("Folder");
				try {
					deleteFolderAndItsContent(Paths.get(path));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				view.needToSave(false);
			}
		} else if (e.getSource() == settings.getRestore()) {
			Utils.resetPreferences();
		} else if (e.getSource() == settings.getConferma()) {
			dialog.dispose();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == settings.getPreview()) {
			Utils.setPreferences("Preview", Boolean.toString(e.getStateChange() == ItemEvent.SELECTED));
		} else if (e.getSource() == settings.getSave()) {
			Utils.setPreferences("Save", Boolean.toString(e.getStateChange() == ItemEvent.SELECTED));
		}
	}

	/**
	 * Deletes Folder with all of its content
	 * 
	 * @param folder path to folder which should be deleted
	 */
	public static void deleteFolderAndItsContent(final Path folder) throws IOException {
		Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				if (exc != null) {
					throw exc;
				}
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}
}

package com.ELSE.presenter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import com.ELSE.model.Model;
import com.ELSE.model.Utils;
import com.ELSE.view.Settings;
import com.ELSE.view.View;

public class SettingsPresenter implements ActionListener, ItemListener {
	private Settings settings;
	private JDialog dialog;
	
	public SettingsPresenter(View view, Model model, CenterPresenter centerPresenter) {
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
				//System.out.println(fc.getSelectedFile().toString() + " <= " + Utils.getPreferences("Folder"));
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
}

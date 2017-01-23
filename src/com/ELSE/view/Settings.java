package com.ELSE.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import com.ELSE.model.Utils;
import com.ELSE.presenter.Presenter;

public class Settings implements KeyEventDispatcher {
	private JDialog dialog;
	private JButton color1, color2, backcolor, paths, folder;
	private JCheckBox preview, save;
	private JButton conferma;
	private JButton restore;

	private Settings(View view) {
		dialog = new JDialog();
		dialog.setBackground(new Color(Integer.parseInt(Utils.getPreferences("Color1"))));
		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		// dialog.setLocationRelativeTo(null);
		JPanel border = JInvisiblePanel.newInstance(dialog);
		
		border.add(new JLabel("Tutte le impostazioni verranno applicate dopo il primo riavvio"),BorderLayout.NORTH);
		JPanel panel = JInvisiblePanel.newInstance(border);
		// GridLayout layout = new GridLayout(0, 2);
		// layout.setVgap(7);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.anchor = GridBagConstraints.CENTER;
		c.ipady = 10;
		panel.setLayout(layout);
		JLabel lcolor1 = new JLabel("Colore principale: ");
		panel.add(lcolor1,c);
		color1 = new JButton();
		panel.add(color1,c);
		c.gridy = 1;
		JLabel lcolor2 = new JLabel("Colore secondario: ");
		panel.add(lcolor2,c);
		color2 = new JButton();
		panel.add(color2,c);
		c.gridy = 2;
		JLabel lbackcolor = new JLabel("Colore di sfondo: ");
		panel.add(lbackcolor,c);
		backcolor = new JButton();
		panel.add(backcolor,c);
		c.gridy = 3;
		JLabel lpaths = new JLabel("File con i percorsi: ");
		panel.add(lpaths,c);
		paths = new JButton();
		panel.add(paths,c);
		c.gridy = 4;
		JLabel lpreview = new JLabel("Vuoi salvare le anteprime (consigliato): ");
		panel.add(lpreview,c);
		preview = new JCheckBox();
		preview.setBackground(new Color(Integer.parseInt(Utils.getPreferences("Color1"))));
		preview.setSelected(Boolean.parseBoolean(Utils.getPreferences("Preview")));
		panel.add(preview,c);
		c.gridy = 5;
		JLabel lfolder = new JLabel("Quale cartella di lavoro? (obbligatorio): ");
		panel.add(lfolder,c);
		folder = new JButton();
		panel.add(folder,c);
		c.gridy = 6;
		JLabel lsave = new JLabel("Vuoi salvare in automatico prima di uscire?: ");
		panel.add(lsave,c);
		save = new JCheckBox();
		save.setBackground(new Color(Integer.parseInt(Utils.getPreferences("Color1"))));
		save.setSelected(Boolean.parseBoolean(Utils.getPreferences("Save")));
		panel.add(save,c);
		border.add(panel);
		restore = new JButton("Resetta");
		border.add(restore, BorderLayout.SOUTH);
		conferma = new JButton("Conferma");
		border.add(conferma, BorderLayout.SOUTH);
		Border a = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		border.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), a));
		dialog.add(border);
		dialog.setSize(450, 270);
		dialog.setLocationRelativeTo(view.getFrame());
		dialog.setUndecorated(true);
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
	}

	static Settings newInstance(View view) {
		return new Settings(view);
	}

	public void setPresenter(Presenter presenter) {
		color1.addActionListener(presenter.getMenuBarPresenter().getSettingsPresenter());
		color2.addActionListener(presenter.getMenuBarPresenter().getSettingsPresenter());
		backcolor.addActionListener(presenter.getMenuBarPresenter().getSettingsPresenter());
		paths.addActionListener(presenter.getMenuBarPresenter().getSettingsPresenter());
		folder.addActionListener(presenter.getMenuBarPresenter().getSettingsPresenter());
		preview.addItemListener(presenter.getMenuBarPresenter().getSettingsPresenter());
		save.addItemListener(presenter.getMenuBarPresenter().getSettingsPresenter());
		restore.addActionListener(presenter.getMenuBarPresenter().getSettingsPresenter());
		conferma.addActionListener(presenter.getMenuBarPresenter().getSettingsPresenter());
	}

	public void setVisible(boolean visible) {
		dialog.setVisible(visible);
	}

	public JDialog getDialog() {
		return dialog;
	}
	
	public JButton getColor1(){
		return color1;
	}

	public JButton getColor2() {
		return color2;
	}

	public JButton getBackcolor() {
		return backcolor;
	}

	public JButton getPaths() {
		return paths;
	}

	public JButton getFolder() {
		return folder;
	}

	public JCheckBox getPreview() {
		return preview;
	}

	public JCheckBox getSave() {
		return save;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (dialog.isVisible() && e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ESCAPE)
			dialog.dispose();
		return false;
	}

	public JButton getConferma() {
		return conferma;
	}
	
	public JButton getRestore() {
		return restore;
	}
	
	public void updateColor(Color color){
		dialog.setBackground(color);
		dialog.revalidate();
		dialog.repaint();
	}
}

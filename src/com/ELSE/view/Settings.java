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
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import com.ELSE.model.Utils;
import com.ELSE.presenter.Presenter;

/**
 * Classe che implementa la visuale del pannello delle impostazioni
 * 
 * @author eddy
 */
public class Settings implements KeyEventDispatcher {
	/**
	 * Metodo statico che restituisce una nuova istanza di Settings
	 * 
	 * @param view
	 *            Vista generale del progetto
	 * @return un nuovo oggetto
	 */
	static Settings newInstance(final View view) {
		return new Settings(view);
	}
	
	private final JButton color1, color2, backcolor, paths, folder;
	private final JButton delete, restore, conferma;
	private final JDialog dialog;
	private final JCheckBox preview, save;
	
	private Settings(final View view) {
		dialog = new JDialog();
		dialog.setBackground(new Color(Integer.parseInt(Utils.getPreferences("Color1"))));
		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		final JPanel border = JInvisiblePanel.newInstance(dialog);
		border.add(new JLabel("Tutte le impostazioni verranno applicate dopo il primo riavvio"), BorderLayout.NORTH);
		final JPanel panel = JInvisiblePanel.newInstance(border);
		final GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.ipady = 10;
		panel.setLayout(new GridBagLayout());
		final JLabel lcolor1 = new JLabel("Colore principale: ");
		panel.add(lcolor1, c);
		color1 = new JButton();
		panel.add(color1, c);
		c.gridy = 1;
		final JLabel lcolor2 = new JLabel("Colore secondario: ");
		panel.add(lcolor2, c);
		color2 = new JButton();
		panel.add(color2, c);
		c.gridy = 2;
		final JLabel lbackcolor = new JLabel("Colore di sfondo: ");
		panel.add(lbackcolor, c);
		backcolor = new JButton();
		panel.add(backcolor, c);
		c.gridy = 3;
		final JLabel lpaths = new JLabel("File con i percorsi: ");
		panel.add(lpaths, c);
		paths = new JButton();
		panel.add(paths, c);
		c.gridy = 4;
		final JLabel lpreview = new JLabel("Vuoi salvare le anteprime (consigliato): ");
		panel.add(lpreview, c);
		preview = new JCheckBox();
		preview.setBackground(new Color(Integer.parseInt(Utils.getPreferences("Color1"))));
		preview.setSelected(Boolean.parseBoolean(Utils.getPreferences("Preview")));
		panel.add(preview, c);
		c.gridy = 5;
		final JLabel lfolder = new JLabel("Quale cartella di lavoro? (obbligatorio): ");
		panel.add(lfolder, c);
		folder = new JButton();
		panel.add(folder, c);
		c.gridy = 6;
		final JLabel lsave = new JLabel("Vuoi salvare in automatico prima di uscire?: ");
		panel.add(lsave, c);
		save = new JCheckBox();
		save.setBackground(new Color(Integer.parseInt(Utils.getPreferences("Color1"))));
		save.setSelected(Boolean.parseBoolean(Utils.getPreferences("Save")));
		panel.add(save, c);
		border.add(panel);
		delete = new JButton("Cancella tutto");
		border.add(delete, BorderLayout.SOUTH);
		restore = new JButton("Resetta");
		border.add(restore, BorderLayout.SOUTH);
		conferma = new JButton("Conferma");
		border.add(conferma, BorderLayout.SOUTH);
		border.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		dialog.add(border);
		dialog.setSize(450, 270);
		dialog.setLocationRelativeTo(view.getFrame());
		dialog.setUndecorated(true);
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
	}
	
	@Override
	public boolean dispatchKeyEvent(final KeyEvent event) {
		if (dialog.isVisible() && event.getID() == KeyEvent.KEY_RELEASED && event.getKeyCode() == KeyEvent.VK_ESCAPE)
			dialog.dispose();
		return false;
	}
	
	/**
	 * @return bottone per cambiare il colore di sfondo
	 */
	public JButton getBackcolor() {
		return backcolor;
	}
	
	/**
	 * @return bottone per cambiare il colore principale
	 */
	public JButton getColor1() {
		return color1;
	}
	
	/**
	 * @return bottone per cambiare il colore secondario
	 */
	public JButton getColor2() {
		return color2;
	}
	
	/**
	 * @return bottone di conferma delle impostazioni
	 */
	public JButton getConferma() {
		return conferma;
	}
	
	/**
	 * @return bottone che cancella tutti i dati salvati
	 */
	public JButton getDelete() {
		return delete;
	}
	
	/**
	 * @return dialog del pannello delle impostazioni
	 */
	public JDialog getDialog() {
		return dialog;
	}
	
	/**
	 * @return bottone che permettere di scegliere una nuova cartella di lavoro
	 */
	public JButton getFolder() {
		return folder;
	}
	
	/**
	 * @return bottone che permette di cambiare il file con i percorsi
	 */
	public JButton getPaths() {
		return paths;
	}
	
	/**
	 * @return JCheckBox che indica se si voglio salvare le anteprime delle copertine
	 */
	public JCheckBox getPreview() {
		return preview;
	}
	
	/**
	 * @return bottone che ripristina le impostazioni ai valori di default
	 */
	public JButton getRestore() {
		return restore;
	}
	
	/**
	 * @return JCheckBox che indica se si vuole salvare in automatico prima di uscire
	 */
	public JCheckBox getSave() {
		return save;
	}
	
	/**
	 * Metodo che aggiunge i vari action listener a i relativi bottoni
	 * 
	 * @param presenter
	 *            Presenter generale del progetto
	 */
	public void setPresenter(final Presenter presenter) {
		color1.addActionListener(presenter.getMenuBarPresenter().getSettingsPresenter());
		color2.addActionListener(presenter.getMenuBarPresenter().getSettingsPresenter());
		backcolor.addActionListener(presenter.getMenuBarPresenter().getSettingsPresenter());
		paths.addActionListener(presenter.getMenuBarPresenter().getSettingsPresenter());
		folder.addActionListener(presenter.getMenuBarPresenter().getSettingsPresenter());
		preview.addItemListener(presenter.getMenuBarPresenter().getSettingsPresenter());
		save.addItemListener(presenter.getMenuBarPresenter().getSettingsPresenter());
		delete.addActionListener(presenter.getMenuBarPresenter().getSettingsPresenter());
		restore.addActionListener(presenter.getMenuBarPresenter().getSettingsPresenter());
		conferma.addActionListener(presenter.getMenuBarPresenter().getSettingsPresenter());
	}
	
	/**
	 * @param visible
	 *            boolean che indica se si vuole il dialog visibile
	 */
	public void setVisible(final boolean visible) {
		dialog.setVisible(visible);
	}
	
	/**
	 * Metodo che cambia il colore di sfondo del dialog
	 * 
	 * @param color
	 *            nuovo colore di sfondo
	 */
	public void updateColor(final Color color) {
		dialog.setBackground(color);
		dialog.revalidate();
		dialog.repaint();
	}
}

package com.ELSE.view;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.ELSE.model.Utils;
import com.ELSE.presenter.Presenter;

/**
 * Classe che implementa la visuale della barra inferiore del frame principale
 * 
 * @author eddy
 */
public class StatusBar {
	/**
	 * Metodo statico che restituisce una nuova istanza di MenuBar
	 * 
	 * @return un nuovo oggetto
	 */
	static StatusBar newInstance() {
		return new StatusBar();
	}
	
	private final JButton add, remove, update, save, load, print;
	private final Bar bar;
	private final JLabel statusText;
	private Thread thread;
	
	private StatusBar() {
		bar = Bar.newInstance();
		statusText = new JLabel();
		bar.getLeft().add(statusText);
		add = Button.newInstance(StatusBar.class.getResource("/add.png"));
		bar.getRight().add(add);
		remove = Button.newInstance(StatusBar.class.getResource("/remove.png"));
		bar.getRight().add(remove);
		update = Button.newInstance(StatusBar.class.getResource("/update.png"));
		bar.getRight().add(update);
		save = Button.newInstance(StatusBar.class.getResource("/save.png"));
		bar.getRight().add(save);
		load = Button.newInstance(StatusBar.class.getResource("/load.png"));
		bar.getRight().add(load);
		print = Button.newInstance(StatusBar.class.getResource("/print.png"));
		bar.getRight().add(print);
	}
	
	/**
	 * @return bottone che aggiunge un nuovo percorso da seguire
	 */
	public JButton getAddButton() {
		return add;
	}
	
	/**
	 * @return barra inferiore del frame
	 */
	public Bar getBar() {
		return bar;
	}
	
	/**
	 * @return bottone che carica nuovi percorsi da file
	 */
	public JButton getLoadButton() {
		return load;
	}
	
	/**
	 * @return bottone che stampa i libri la lista dei libri presenti
	 */
	public JButton getPrintButton() {
		return print;
	}
	
	/**
	 * @return bottone che cancella un percorso
	 */
	public JButton getRemoveButton() {
		return remove;
	}
	
	/**
	 * @return bottone che salva le modifiche su file
	 */
	public JButton getSaveButton() {
		return save;
	}
	
	/**
	 * @return bottone che aggiorna il frame
	 */
	public JButton getUpdateButton() {
		return update;
	}
	
	/**
	 * Metodo che indica se c'e bisogno di salvare cambiando il colore del tasto salva
	 * 
	 * @param need
	 *            vero se bisogna salvare
	 */
	void needToSave(final boolean need) {
		if (!Boolean.parseBoolean(Utils.getPreferences("Save")))
			save.setIcon(new ImageIcon(new ImageIcon(StatusBar.class.getResource(need ? "/save_red.png" : "/save.png")).getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
	}
	
	/**
	 * Metodo che aggiunge i vari action listener ai relativi bottoni
	 * 
	 * @param presenter
	 *            Presenter generale del progetto
	 */
	void setPresenter(final Presenter presenter) {
		add.addActionListener(presenter.getStatusBarPresenter());
		remove.addActionListener(presenter.getStatusBarPresenter());
		update.addActionListener(presenter.getStatusBarPresenter());
		save.addActionListener(presenter.getStatusBarPresenter());
		load.addActionListener(presenter.getStatusBarPresenter());
		print.addActionListener(presenter.getStatusBarPresenter());
	}
	
	/**
	 * Metodo che imposta il testo da visualizzare in basso per cinque secondi
	 * 
	 * @param text
	 *            testo da visualizzare
	 */
	void setStatusText(final String text) {
		if (thread == null || !thread.isAlive()) {
			thread = new Thread(new Runnable() {
				@Override
				public void run() {
					statusText.setText(text);
					try {
						Thread.sleep(5000);
					} catch (final InterruptedException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
					statusText.setText("");
				}
			});
			thread.start();
		}
	}
}

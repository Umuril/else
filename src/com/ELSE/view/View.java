package com.ELSE.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.ELSE.model.BookMetadata;
import com.ELSE.model.Utils;
import com.ELSE.presenter.Presenter;

/**
 * @author Eduard Rubio Cholbi
 */
public class View {
	private final AdvanceSearch advanceSearch;
	private final Center center;
	private final JFrame framePrincipale;
	private final MenuBar menuBar;
	private boolean needsave;
	private final Settings settings;
	private final StatusBar statusBar;
	
	/**
	 * Costruttore
	 */
	public View() {
		framePrincipale = new JFrame("ELSE");
		framePrincipale.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		framePrincipale.getContentPane().setBackground(new Color(Integer.parseInt(Utils.getPreferences("Color1"))));
		framePrincipale.getContentPane().setLayout(new BorderLayout());
		menuBar = MenuBar.newInstance();
		framePrincipale.getContentPane().add(menuBar.getParent().getBarContainer(), BorderLayout.NORTH);
		center = Center.newInstance(framePrincipale.getContentPane());
		final Box hbox = Box.createHorizontalBox();
		hbox.add(Box.createHorizontalGlue());
		final Box vbox = Box.createVerticalBox();
		vbox.add(Box.createVerticalGlue());
		vbox.add(center.getPanel());
		vbox.add(Box.createVerticalGlue());
		hbox.add(vbox);
		hbox.add(Box.createHorizontalGlue());
		framePrincipale.getContentPane().add(hbox, BorderLayout.CENTER);
		statusBar = StatusBar.newInstance();
		framePrincipale.getContentPane().add(statusBar.getBar().getBarContainer(), BorderLayout.SOUTH);
		framePrincipale.setBounds(100, 20, 1024, 600);
		framePrincipale.setMinimumSize(new Dimension(1024, 600));
		framePrincipale.setVisible(true);
		advanceSearch = AdvanceSearch.newInstance(this);
		settings = Settings.newInstance(this);
	}
	
	/**
	 * Metodo che cambia il pannello centrale
	 * 
	 * @param image
	 *            copertina del libro (se presente)
	 * @param book
	 *            libro da visualizzare (se presente)
	 */
	public void change(final Image image, final BookMetadata book) {
		center.change(image, book);
	}
	
	/**
	 * Metodo che rende il pannello di visualizzazzione di un singolo libro editabile
	 * 
	 * @param editable
	 *            boolean che indica se i campi devono essere editabili
	 */
	public void changeBookPageEditable(final Boolean editable) {
		center.getBookDetailsPage().setEditable(editable == null ? !center.getBookDetailsPage().isEditable() : editable);
		center.getBookDetailsPage().update();
	}
	
	/**
	 * @param enable
	 *            boolean che abilita il bottone indietro nella visualizzazzione dei libri
	 */
	public void enableBackButton(final boolean enable) {
		center.getSlider().enableBackButton(enable);
	}
	
	/**
	 * @param enable
	 *            boolean che abilita il bottone avanti nella visualizzazzione dei libri
	 */
	public void enableNextButton(final boolean enable) {
		center.getSlider().enableNextButton(enable);
	}
	
	/**
	 * @return oggetto che gestice il pannello della ricerca avanzata
	 */
	public AdvanceSearch getAdvanceSearch() {
		return advanceSearch;
	}
	
	/**
	 * @return pannello della visuallizzazione di un certo libro
	 */
	public BookDetailsPage getBookDetailsPage() {
		return center.getBookDetailsPage();
	}
	
	/*************************
	 ********** API **********
	 *************************/
	/**
	 * @return frame principale
	 */
	public JFrame getFrame() {
		return framePrincipale;
	}
	
	/**
	 * @return barra superiore del frame principale
	 */
	public MenuBar getMenuBar() {
		return menuBar;
	}
	
	/**
	 * @return pannello contenenti i dati del libro visualizzato
	 */
	public MetadataPanel getMetadataPanel() {
		return center.getBookDetailsPage().getMetadataPanel();
	}
	
	/**
	 * @return pannello delle impostazioni
	 */
	public Settings getSettings() {
		return settings;
	}
	
	/**
	 * @return pannello centrale contenente la visuallizzazzione dei libri presenti
	 */
	public SliderPage getSliderPage() {
		return center.getSlider();
	}
	
	/**
	 * @return barra inferiore del frame principale
	 */
	public StatusBar getStatusBar() {
		return statusBar;
	}
	
	/**
	 * @return boolean che indica se ci sono libri nella biblioteca
	 */
	public boolean isEmpty() {
		return center.isEmpty();
	}
	
	/**
	 * @return vero se bisogna salvare su file delle modifiche
	 */
	public boolean needSave() {
		return needsave;
	}
	
	/**
	 * @param need
	 *            boolean che indica se c'è bisogno di salvare su file a causa di modifiche
	 */
	public void needToSave(final boolean need) {
		needsave = need;
		statusBar.needToSave(need);
	}
	
	/**
	 * Metodo che rende visibile il pannello per la ricerca avanzata
	 */
	public void setAdvanceSearchVisible() {
		advanceSearch.setVisible(true);
	}
	
	/**
	 * Metodo che indica se bisogna visualizzare libri
	 * 
	 * @param empty
	 *            vero se bisogna visualizzare il pannelo vuoto
	 */
	public void setEmpty(final boolean empty) {
		Utils.log(Utils.Debug.DEBUG, "setEmpty() with " + empty);
		center.setEmpty(empty);
	}
	
	/**
	 * Metodo che setta il presenter alle classe sottostanti
	 * 
	 * @param presenter
	 *            Presenter generale del progetto
	 */
	public void setPresenter(final Presenter presenter) {
		menuBar.setPresenter(presenter);
		center.getBookDetailsPage().setPresenter(presenter);
		center.getSlider().setPresenter(presenter);
		statusBar.setPresenter(presenter);
		advanceSearch.setPresenter(presenter);
		settings.setPresenter(presenter);
		framePrincipale.addWindowListener(presenter);
	}
	
	/**
	 * Metodo che rende visibile il pannello delle impostazioni
	 */
	public void setSettingsVisible() {
		settings.setVisible(true);
	}
	
	/**
	 * Metodo che imposta un nuovo testo nella barra inferiore
	 * 
	 * @param text
	 *            testo da visualizzare
	 */
	public void setStatusText(final String text) {
		statusBar.setStatusText(text);
	}
	
	/**
	 * Metodo che aggiorna il colore di sfondo del frame
	 * 
	 * @param color
	 *            nuovo colore
	 */
	public void updateColor(final Color color) {
		framePrincipale.getContentPane().setBackground(color);
		framePrincipale.getContentPane().revalidate();
		framePrincipale.getContentPane().repaint();
	}
}
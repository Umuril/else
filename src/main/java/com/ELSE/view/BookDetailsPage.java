package com.ELSE.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.ELSE.model.BookMetadata;
import com.ELSE.presenter.Presenter;

/**
 * Classe che implementa la visualizzazione di un libro al interno del pannello centrale
 * 
 * @author eddy
 */
public class BookDetailsPage implements CentralProperties {
	/**
	 * Metodo statico che restituisce una nuova istanza di BookDetailsPage
	 * 
	 * @return un nuovo oggetto
	 */
	static BookDetailsPage newInstance() {
		return new BookDetailsPage();
	}
	
	private JButton back, edit, save;
	private BookMetadata book;
	private JPanel down;
	private boolean editable = false;
	private Image image;
	private MetadataPanel metadataPanel;
	private final JPanel parent;
	private Presenter presenter;
	private JPanel up;
	
	private BookDetailsPage() {
		parent = CentralPage.newInstance(this);
	}
	
	/**
	 * Metodo che abilita/disabilita il tasto di salvataggio e ne cambia il colore(immagine)
	 * 
	 * @param enable
	 *            boolean che indica se deve essere abilitato
	 */
	public void enableSaveButton(final boolean enable) {
		save.setEnabled(enable);
		save.setIcon(new ImageIcon(new ImageIcon(SliderPage.class.getResource(enable ? "/save.png" : "/save_gray.png")).getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
		down.revalidate();
		down.repaint();
	}
	
	/**
	 * @return JButton del tasto indietro
	 */
	public JButton getBackButton() {
		return back;
	}
	
	@Override
	public JPanel getContainerPanel() {
		return parent;
	}
	
	/**
	 * @return JButton del tasto di modifica
	 */
	public JButton getEditButton() {
		return edit;
	}
	
	/**
	 * @return Pannello contente i metadati del libro che si sta visualizzando
	 */
	MetadataPanel getMetadataPanel() {
		return metadataPanel;
	}
	
	/**
	 * @return JButton del tasto salva
	 */
	public JButton getSaveButton() {
		return save;
	}
	
	@Override
	public JPanel initDown(final JPanel parent) {
		down = SubSizePanel.newInstance(parent);
		back = Button.newInstance(SliderPage.class.getResource("/back.png"));
		down.add(back);
		back.setAlignmentX(Component.LEFT_ALIGNMENT);
		final JPanel dcenter = JInvisiblePanel.newInstance(parent);
		edit = Button.newInstance(SliderPage.class.getResource("/edit.png"));
		dcenter.add(edit);
		down.add(dcenter);
		dcenter.setAlignmentX(Component.CENTER_ALIGNMENT);
		save = Button.newInstance(SliderPage.class.getResource("/save_gray.png"));
		save.setEnabled(false);
		down.add(save);
		save.setAlignmentX(Component.RIGHT_ALIGNMENT);
		return down;
	}
	
	@Override
	public JPanel initUp(final JPanel parent) {
		up = JInvisiblePanel.newInstance(parent);
		up.setLayout(new BorderLayout());
		metadataPanel = MetadataPanel.newInstance(up);
		return up;
	}
	
	/**
	 * @return boolean che indica se i dati possono essere editati o meno
	 */
	public boolean isEditable() {
		return editable;
	}
	
	/**
	 * @param editable
	 *            boolean che setta se i dati del pannello devono essere editabili
	 */
	public void setEditable(final boolean editable) {
		this.editable = editable;
	}
	
	/**
	 * Metodo che setta il presenter e aggiunge gli action listener nei vari bottoni
	 * 
	 * @param presenter
	 *            Presenter generale del progetto
	 */
	void setPresenter(final Presenter presenter) {
		this.presenter = presenter;
		back.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		edit.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
	}
	
	/**
	 * Metodo che aggiorna il pannello centrale
	 */
	void update() {
		if (image != null && book != null)
			metadataPanel.change(image, book, editable);
	}
	
	/**
	 * Metodo che aggiorna il colore di sfondo del pannello centrale
	 * 
	 * @param color
	 *            nuovo colore di sfondo
	 */
	public void updateColor(final Color color) {
		parent.setBackground(color);
		parent.revalidate();
		parent.repaint();
		up.setBackground(color);
		up.revalidate();
		up.repaint();
	}
	
	/**
	 * Metodo che aggiorna il pannello centrale con una data immagine e libro
	 * 
	 * @param image
	 *            copertina del libro
	 * @param book
	 *            metadati del libro da visualizzare
	 */
	void updateUpWith(final Image image, final BookMetadata book) {
		this.image = image;
		this.book = book;
		metadataPanel.change(image, book, editable);
		metadataPanel.setPresenter(presenter);
		for (final ActionListener al : save.getActionListeners())
			save.removeActionListener(al);
		save.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
	}
}

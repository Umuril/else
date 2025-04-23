package com.ELSE.view;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import com.ELSE.model.Utils;
import com.ELSE.presenter.Presenter;

/**
 * Classe che gestisce la grafica del pannello di ricerca avanzata
 * 
 * @author eddy
 */
public class AdvanceSearch implements KeyEventDispatcher {
	/**
	 * Metodo statico che restituisce una nuova istanza di AdvanceSearch
	 * 
	 * @param view
	 *            Vista generale del progetto
	 * @return un nuovo oggetto
	 */
	static AdvanceSearch newInstance(final View view) {
		return new AdvanceSearch(view);
	}
	
	private final JButton conferma;
	private final JDialog dialog;
	private final JCheckBox epub, html, pdf;
	private final JTextField titolo, autore, anno, pagine;
	
	private AdvanceSearch(final View view) {
		dialog = new JDialog();
		dialog.setBackground(new Color(Integer.parseInt(Utils.getPreferences("Color1"))));
		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		final JPanel border = JInvisiblePanel.newInstance(dialog);
		final JPanel panel = JInvisiblePanel.newInstance(border);
		panel.setLayout(new GridLayout(0, 2));
		final JLabel ltitolo = new JLabel("Titolo: ");
		panel.add(ltitolo);
		titolo = new JTextField(12);
		panel.add(titolo);
		final JLabel lautore = new JLabel("Autore: ");
		panel.add(lautore);
		autore = new JTextField(12);
		panel.add(autore);
		final JLabel lanno = new JLabel("Anno: ");
		panel.add(lanno);
		anno = new JTextField(12);
		panel.add(anno);
		final JLabel lpagine = new JLabel("Pagine: ");
		panel.add(lpagine);
		pagine = new JTextField(12);
		panel.add(pagine);
		border.add(panel);
		final JPanel panel2 = JInvisiblePanel.newInstance(border);
		panel2.add(new JLabel("EPUB: "));
		epub = new JCheckBox();
		epub.setBackground(panel2.getBackground());
		epub.setSelected(true);
		panel2.add(epub);
		panel2.add(new JLabel("HTML: "));
		html = new JCheckBox();
		html.setBackground(panel2.getBackground());
		html.setSelected(true);
		panel2.add(html);
		panel2.add(new JLabel("PDF: "));
		pdf = new JCheckBox();
		pdf.setBackground(panel2.getBackground());
		pdf.setSelected(true);
		panel2.add(pdf);
		border.add(panel2);
		conferma = new JButton("Conferma");
		border.add(conferma);
		border.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		dialog.add(border);
		dialog.setSize(350, 170);
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
	 * @return la jtextfield relativa al anno
	 */
	public JTextField getAnno() {
		return anno;
	}
	
	/**
	 * @return la jtextfield relativa al autore
	 */
	public JTextField getAutore() {
		return autore;
	}
	
	/**
	 * @return il dialog di tutto il popup
	 */
	public JDialog getDialog() {
		return dialog;
	}
	
	/**
	 * @return checkbox che indica se si stanno cercando degli epub
	 */
	public JCheckBox getEpub() {
		return epub;
	}
	
	/**
	 * @return checkbox che indica se si stanno cercando dei file html
	 */
	public JCheckBox getHtml() {
		return html;
	}
	
	/**
	 * @return la jtextfield relativa alle pagine cercate
	 */
	public JTextField getPagine() {
		return pagine;
	}
	
	/**
	 * @return checkbox che indica se si stanno cercando dei pdf
	 */
	public JCheckBox getPdf() {
		return pdf;
	}
	
	/**
	 * @return la jtextfield relativa al titolo cercato
	 */
	public JTextField getTitolo() {
		return titolo;
	}
	
	/**
	 * Metodo che setta i vari action listener
	 * 
	 * @param presenter
	 *            presenter generico del progetto
	 */
	public void setPresenter(final Presenter presenter) {
		titolo.addActionListener(presenter.getMenuBarPresenter().getAdvanceSearchPresenter());
		autore.addActionListener(presenter.getMenuBarPresenter().getAdvanceSearchPresenter());
		anno.addActionListener(presenter.getMenuBarPresenter().getAdvanceSearchPresenter());
		pagine.addActionListener(presenter.getMenuBarPresenter().getAdvanceSearchPresenter());
		conferma.addActionListener(presenter.getMenuBarPresenter().getAdvanceSearchPresenter());
	}
	
	/**
	 * Metodo che rende visibile/invisibile il dialog
	 * 
	 * @param visible
	 *            vero se il dialog dovrebbe essere visibile
	 */
	public void setVisible(final boolean visible) {
		dialog.setVisible(visible);
	}
	
	/**
	 * Metodo che aggiorna i colori del dialog
	 * 
	 * @param color
	 *            colore di sfondo
	 */
	public void updateColor(final Color color) {
		dialog.setBackground(color);
		dialog.revalidate();
		dialog.repaint();
	}
}

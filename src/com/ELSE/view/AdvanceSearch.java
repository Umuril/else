package com.ELSE.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import com.ELSE.model.Utils;
import com.ELSE.presenter.Presenter;

public class AdvanceSearch implements KeyEventDispatcher {
	private JDialog dialog;
	private JTextField titolo, autore, anno, pagine;
	private JButton conferma;

	private AdvanceSearch(View view) {
		dialog = new JDialog();
		dialog.setBackground(new Color(Integer.parseInt(Utils.getPreferences("Color1"))));
		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		// dialog.setLocationRelativeTo(null);
		JPanel border = JInvisiblePanel.newInstance(dialog);
		JPanel panel = JInvisiblePanel.newInstance(border);
		panel.setLayout(new GridLayout(0, 2));
		JLabel ltitolo = new JLabel("Titolo: ");
		panel.add(ltitolo);
		titolo = new JTextField(15);
		panel.add(titolo);
		JLabel lautore = new JLabel("Autore: ");
		panel.add(lautore);
		autore = new JTextField(15);
		panel.add(autore);
		JLabel lanno = new JLabel("Anno: ");
		panel.add(lanno);
		anno = new JTextField(15);
		panel.add(anno);
		JLabel lpagine = new JLabel("Pagine: ");
		panel.add(lpagine);
		pagine = new JTextField(15);
		panel.add(pagine);
		border.add(panel);
		conferma = new JButton("Conferma");
		border.add(conferma, BorderLayout.SOUTH);
		Border a = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		border.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), a));
		dialog.add(border);
		dialog.setSize(400, 150);
		dialog.setLocationRelativeTo(view.getFrame());
		dialog.setUndecorated(true);
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
	}

	static AdvanceSearch newInstance(View view) {
		return new AdvanceSearch(view);
	}

	public void setPresenter(Presenter presenter) {
		titolo.addActionListener(presenter.getMenuBarPresenter().getAdvanceSearchPresenter());
		autore.addActionListener(presenter.getMenuBarPresenter().getAdvanceSearchPresenter());
		anno.addActionListener(presenter.getMenuBarPresenter().getAdvanceSearchPresenter());
		pagine.addActionListener(presenter.getMenuBarPresenter().getAdvanceSearchPresenter());
		conferma.addActionListener(presenter.getMenuBarPresenter().getAdvanceSearchPresenter());
	}

	public void setVisible(boolean visible) {
		dialog.setVisible(visible);
	}

	public JTextField getTitolo() {
		return titolo;
	}

	public JTextField getAutore() {
		return autore;
	}

	public JTextField getAnno() {
		return anno;
	}

	public JTextField getPagine() {
		return pagine;
	}

	public JDialog getDialog() {
		return dialog;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (dialog.isVisible() && e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ESCAPE)
			dialog.dispose();
		return false;
	}
	
	public void updateColor(Color color){
		dialog.setBackground(color);
		dialog.revalidate();
		dialog.repaint();
	}
}

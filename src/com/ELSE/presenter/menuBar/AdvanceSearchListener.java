package com.ELSE.presenter.menuBar;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ELSE.model.BookMetadata;
import com.ELSE.model.Model;
import com.ELSE.presenter.center.CenterPresenter;
import com.ELSE.view.View;

public class AdvanceSearchListener implements ActionListener {
	private JTextField titolo, autore, anno, pagine;
	private CenterPresenter centerPresenter;
	private Model model;
	private JDialog dialog;
	private View view;

	public AdvanceSearchListener(CenterPresenter centerPresenter, Model model, View view) {
		this.centerPresenter = centerPresenter;
		this.model = model;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dialog = new JDialog();
		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		// dialog.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
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
		JPanel border = new JPanel();
		border.add(panel);
		JButton conferma = new JButton("Conferma");
		conferma.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				System.out.println("Search triggered with title " + titolo.getText() + ".");
				System.out.println("Search triggered with author " + autore.getText() + ".");
				System.out.println(model.getLibrary().getDatabase().values());
				if (titolo.getText().isEmpty() && autore.getText().isEmpty() && anno.getText().isEmpty() && pagine.getText().isEmpty())
					return;
				centerPresenter.emptyOfBooks();
				for (BookMetadata book : model.getLibrary().getDatabase().values()) {
					boolean found = false;
					if (book.getAnno() != null && !anno.getText().isEmpty())
						if (book.getAnno().toString().contains(anno.getText()))
							found = true;
					if (book.getAutore() != null && !autore.getText().isEmpty())
						if (book.getAutore().contains(autore.getText()))
							found = true;
					if (book.getNpagine() > 0 && !pagine.getText().isEmpty())
						if (String.valueOf(book.getNpagine()).contains(pagine.getText()))
							found = true;
					if (book.getTitolo() != null && !titolo.getText().isEmpty())
						if (book.getTitolo().contains(titolo.getText()))
							found = true;
					if (found) {
						System.out.println("Found: " + book);
						try {
							centerPresenter.addImage(book);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else
						System.out.println("Book not found: " + book);
				}
				dialog.dispose();
			}
		});
		border.add(conferma, BorderLayout.SOUTH);
		border.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		dialog.add(border);
		dialog.setSize(400, 150);
		dialog.setLocationRelativeTo(view.getFrame());
		dialog.setUndecorated(true);
		dialog.setVisible(true);
	}
}

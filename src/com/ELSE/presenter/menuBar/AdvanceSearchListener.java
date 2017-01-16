package com.ELSE.presenter.menuBar;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.ELSE.model.BookMetadata;
import com.ELSE.model.Model;
import com.ELSE.presenter.center.CenterPresenter;

public class AdvanceSearchListener implements ActionListener {
	private JTextField titolo;
	private CenterPresenter centerPresenter;
	private Model model;
	private JDialog dialog;

	public AdvanceSearchListener(CenterPresenter centerPresenter, Model model) {
		this.centerPresenter = centerPresenter;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dialog = new JDialog();
		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		JLabel ltitolo = new JLabel("Scegli il titolo:");
		dialog.getContentPane().add(ltitolo, BorderLayout.NORTH);
		titolo = new JTextField(15);
		dialog.getContentPane().add(titolo, BorderLayout.CENTER);
		JButton conferma = new JButton("Conferma");
		conferma.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				String text = titolo.getText();
				if (text.isEmpty()) {
					centerPresenter.aggiorna(0);
					return;
				}
				System.out.println("Search triggered with " + text + ".");
				System.out.println(model.getLibrary().getDatabase().values());
				centerPresenter.emptyOfBooks();
				for (BookMetadata book : model.getLibrary().getDatabase().values()) {
					boolean found = false;
					if (book.getAnno() != null)
						if (book.getAnno().toString().contains(text))
							found = true;
					if (book.getAutore() != null)
						if (book.getAutore().contains(text))
							found = true;
					if (book.getNpagine() > 0)
						if (String.valueOf(book.getNpagine()).contains(text))
							found = true;
					if (book.getTitolo() != null)
						if (book.getTitolo().contains(text))
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
		dialog.getContentPane().add(conferma, BorderLayout.SOUTH);
		dialog.setBounds(100, 100, 100, 100);
		dialog.setUndecorated(true);
		dialog.setVisible(true);
	}
}

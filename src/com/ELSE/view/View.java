package com.ELSE.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.ELSE.model.BookMetadata;
import com.ELSE.presenter.Presenter;

/**
 * @author Eduard Rubio Cholbi
 */
public class View {
	private JFrame framePrincipale;
	private MenuBar menuBar;
	private Center center;
	private StatusBar statusBar;

	public View() {
		framePrincipale = new JFrame("ELSE");
		framePrincipale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		framePrincipale.getContentPane().setBackground(Color.decode("#e2dcc5"));
		framePrincipale.getContentPane().setLayout(new BorderLayout());
		menuBar = MenuBar.newInstance();
		framePrincipale.getContentPane().add(menuBar.getParent().getBarContainer(), BorderLayout.NORTH);
		center = Center.newInstance(framePrincipale.getContentPane());
		Box box = Box.createHorizontalBox();
		box.add(Box.createHorizontalGlue());
		Box bitty = Box.createVerticalBox();
		bitty.add(Box.createVerticalGlue());
		bitty.add(center.getPanel());
		bitty.add(Box.createVerticalGlue());
		box.add(bitty);
		box.add(Box.createHorizontalGlue());
		framePrincipale.getContentPane().add(box, BorderLayout.CENTER);
		statusBar = StatusBar.newInstance();
		framePrincipale.getContentPane().add(statusBar.getBar().getBarContainer(), BorderLayout.SOUTH);
		framePrincipale.setBounds(100, 20, 1024, 600);
		framePrincipale.setMinimumSize(new Dimension(1024, 600)); // 1024 x 768
		// http://www.w3schools.com/browsers/browsers_display.asp
		framePrincipale.setVisible(true);
	}

	public void setPresenter(Presenter presenter) {
		menuBar.setPresenter(presenter);
		center.getBookDetails().setPresenter(presenter);
		statusBar.setPresenter(presenter);
		presenter.getCenterPresenter().loadFromFile("db.txt");
		presenter.getCenterPresenter().aggiorna();
	}

	/*************************************
	 ********** API STARTS HERE **********
	 *************************************/
	public JPanel getUpSlider() {
		return center.getSlider().getUp();
	}

	public JPanel getStatusBar() {
		return statusBar.getBar().getBarContainer();
	}

	public JFrame getFrame() {
		return framePrincipale;
	}

	public void change(Image image, BookMetadata book) {
		center.change(image, book);
	}

	public void setStatusText(String s) {
		statusBar.setStatusText(s);
	}

	public void setBookPageEditable(boolean b) {
		center.getBookDetails().setEditable(b);
		center.getBookDetails().update();
	}

	public void changeBookPageEditable() {
		center.getBookDetails().setEditable(!center.getBookDetails().isEditable());
		center.getBookDetails().update();
	}

	public String getBookDetailTitolo() {
		return center.getBookDetails().getMetadataPanel().getTitolo().getText();
	}

	public String getBookDetailAutore() {
		return center.getBookDetails().getMetadataPanel().getAutore().getText();
	}

	public void needToSave(boolean need) {
		statusBar.needToSave(need);
	}

	public String getBookDetailAnno() {
		return center.getBookDetails().getMetadataPanel().getAnno().getText();
	}

	public String getBookDetailPagine() {
		return center.getBookDetails().getMetadataPanel().getPagine().getText();
	}
}

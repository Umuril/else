package com.ELSE.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import com.ELSE.presenter.Presenter;

public class View {

	private JFrame framePrincipale;

	public View() {

		framePrincipale = new JFrame("ELSE");
		framePrincipale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		framePrincipale.getContentPane().setBackground(Color.decode("#e2dcc5"));
		framePrincipale.getContentPane().setLayout(new BorderLayout());

		framePrincipale.setLocation(100, 20);
		// http://www.w3schools.com/browsers/browsers_display.asp
		framePrincipale.setMinimumSize(new Dimension(1024-100, 768-100));
		framePrincipale.setVisible(true);

	}

	public void setPresenter(Presenter presenter) {
		// TODO Auto-generated method stub

	}

	/******************************************
	 ********** VIEW API STARTS HERE **********
	 ******************************************/

	public JFrame getFrame() {
		return framePrincipale;
	}

}

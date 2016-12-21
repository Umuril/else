package com.ELSE.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JFrame;

import com.ELSE.presenter.Presenter;

/**
 * @author Eduard Rubio Cholbi
 *
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
		framePrincipale.setMinimumSize(new Dimension(1024, 600)); //1024 x 768
		// http://www.w3schools.com/browsers/browsers_display.asp
		framePrincipale.setVisible(true);

	}

	public void setPresenter(Presenter presenter) {
		// TODO Auto-generated method stub
		
	}
}

package com.ELSE.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.JFrame;

import com.ELSE.model.BookMetadata;
import com.ELSE.model.Utils;
import com.ELSE.presenter.Presenter;

/**
 * @author Eduard Rubio Cholbi
 */
public class View {
	private final Center center;
	private final JFrame framePrincipale;
	private final MenuBar menuBar;
	private final StatusBar statusBar;
	private final AdvanceSearch advanceSearch;
	private boolean needsave;
	private final Settings settings;

	public View() {
		framePrincipale = new JFrame("ELSE");
		framePrincipale.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		framePrincipale.getContentPane().setBackground(new Color(Integer.parseInt(Utils.getPreferences("Color1"))));
		framePrincipale.getContentPane().setLayout(new BorderLayout());
		menuBar = MenuBar.newInstance();
		framePrincipale.getContentPane().add(menuBar.getParent().getBarContainer(), BorderLayout.NORTH);
		center = Center.newInstance(framePrincipale.getContentPane());
		Box hbox = Box.createHorizontalBox();
		hbox.add(Box.createHorizontalGlue());
		Box vbox = Box.createVerticalBox();
		vbox.add(Box.createVerticalGlue());
		vbox.add(center.getPanel());
		vbox.add(Box.createVerticalGlue());
		hbox.add(vbox);
		hbox.add(Box.createHorizontalGlue());
		framePrincipale.getContentPane().add(hbox, BorderLayout.CENTER);
		statusBar = StatusBar.newInstance();
		framePrincipale.getContentPane().add(statusBar.getBar().getBarContainer(), BorderLayout.SOUTH);
		framePrincipale.setBounds(100, 20, 1024, 600);
		framePrincipale.setMinimumSize(new Dimension(1024, 600)); // 1024 x 768
		// http://www.w3schools.com/browsers/browsers_display.asp
		framePrincipale.setVisible(true);
		advanceSearch = AdvanceSearch.newInstance(this);
		settings = Settings.newInstance(this);
	}

	public void setPresenter(final Presenter presenter) {
		menuBar.setPresenter(presenter);
		center.getBookDetailsPage().setPresenter(presenter);
		center.getSlider().setPresenter(presenter);
		statusBar.setPresenter(presenter);
		advanceSearch.setPresenter(presenter);
		settings.setPresenter(presenter);
		framePrincipale.addWindowListener(presenter);
		// presenter.getCenterPresenter().loadFromFile("db.txt");
		// Can I delete it? Need more checks
		// presenter.getCenterPresenter().aggiorna();
	}

	/*************************************
	 ********** API STARTS HERE **********
	 *************************************/
	public JFrame getFrame() {
		return framePrincipale;
	}

	public void change(Image image, BookMetadata book) {
		center.change(image, book);
	}

	public void setEmpty(boolean empty) {
		Utils.log(Utils.Debug.DEBUG, "setEmpty() with " + empty);
		center.setEmpty(empty);
	}

	public void changeBookPageEditable(Boolean b) {
		center.getBookDetailsPage().setEditable(b == null ? !center.getBookDetailsPage().isEditable() : b);
		center.getBookDetailsPage().update();
	}

	public MetadataPanel getMetadataPanel() {
		return center.getBookDetailsPage().getMetadataPanel();
	}

	public void enableBackButton(boolean b) {
		center.getSlider().enableBackButton(b);
	}

	public void enableNextButton(boolean b) {
		center.getSlider().enableNextButton(b);
	}

	public SliderPage getSliderPage() {
		return center.getSlider();
		// return center.getSlider().getUp();
	}

	public void setStatusText(String s) {
		statusBar.setStatusText(s);
	}

	public void needToSave(boolean need) {
		needsave = need;
		statusBar.needToSave(need);
	}

	public boolean needSave() {
		return needsave; // TODO Need to change names
	}

	public StatusBar getStatusBar() {
		return statusBar;
	}

	public MenuBar getMenuBar() {
		return menuBar;
	}

	public BookDetailsPage getBookDetailsPage() {
		return center.getBookDetailsPage();
	}

	public void setAdvanceSearchVisible() {
		advanceSearch.setVisible(true);
	}

	public AdvanceSearch getAdvanceSearch() {
		return advanceSearch;
	}

	public boolean isEmpty() {
		return center.isEmpty();
	}

	public void setSettingsVisible() {
		settings.setVisible(true);
	}

	public Settings getSettings() {
		return settings;
	}

	public void updateColor(Color color) {
		framePrincipale.getContentPane().setBackground(color);
		framePrincipale.getContentPane().revalidate();
		framePrincipale.getContentPane().repaint();
	}
}
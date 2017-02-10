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
	
	public void change(final Image image, final BookMetadata book) {
		center.change(image, book);
	}
	
	public void changeBookPageEditable(final Boolean editable) {
		center.getBookDetailsPage().setEditable(editable == null ? !center.getBookDetailsPage().isEditable() : editable);
		center.getBookDetailsPage().update();
	}
	
	public void enableBackButton(final boolean enable) {
		center.getSlider().enableBackButton(enable);
	}
	
	public void enableNextButton(final boolean enable) {
		center.getSlider().enableNextButton(enable);
	}
	
	public AdvanceSearch getAdvanceSearch() {
		return advanceSearch;
	}
	
	public BookDetailsPage getBookDetailsPage() {
		return center.getBookDetailsPage();
	}
	
	/*************************************
	 ********** API STARTS HERE **********
	 *************************************/
	public JFrame getFrame() {
		return framePrincipale;
	}
	
	public MenuBar getMenuBar() {
		return menuBar;
	}
	
	public MetadataPanel getMetadataPanel() {
		return center.getBookDetailsPage().getMetadataPanel();
	}
	
	public Settings getSettings() {
		return settings;
	}
	
	public SliderPage getSliderPage() {
		return center.getSlider();
	}
	
	public StatusBar getStatusBar() {
		return statusBar;
	}
	
	public boolean isEmpty() {
		return center.isEmpty();
	}
	
	public boolean needSave() {
		return needsave;
	}
	
	public void needToSave(final boolean need) {
		needsave = need;
		statusBar.needToSave(need);
	}
	
	public void setAdvanceSearchVisible() {
		advanceSearch.setVisible(true);
	}
	
	public void setEmpty(final boolean empty) {
		Utils.log(Utils.Debug.DEBUG, "setEmpty() with " + empty);
		center.setEmpty(empty);
	}
	
	public void setPresenter(final Presenter presenter) {
		menuBar.setPresenter(presenter);
		center.getBookDetailsPage().setPresenter(presenter);
		center.getSlider().setPresenter(presenter);
		statusBar.setPresenter(presenter);
		advanceSearch.setPresenter(presenter);
		settings.setPresenter(presenter);
		framePrincipale.addWindowListener(presenter);
	}
	
	public void setSettingsVisible() {
		settings.setVisible(true);
	}
	
	public void setStatusText(final String text) {
		statusBar.setStatusText(text);
	}
	
	public void updateColor(final Color color) {
		framePrincipale.getContentPane().setBackground(color);
		framePrincipale.getContentPane().revalidate();
		framePrincipale.getContentPane().repaint();
	}
}
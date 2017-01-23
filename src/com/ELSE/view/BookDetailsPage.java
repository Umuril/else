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

public class BookDetailsPage implements CentralProperties {
	static BookDetailsPage newInstance() {
		return new BookDetailsPage();
	}

	private JButton back, edit, save;
	private BookMetadata book;
	private boolean editable = false;
	private Image image;
	private MetadataPanel metadataPanel;
	private JPanel parent, up, down;
	private Presenter presenter;

	private BookDetailsPage() {
		parent = CentralPage.newInstance(this);
	}

	@Override
	public JPanel getContainerPanel() {
		return parent;
	}

	MetadataPanel getMetadataPanel() {
		return metadataPanel;
	}

	@Override
	public JPanel initDown(JPanel parent) {
		down = SubSizePanel.newInstance(parent);
		back = Button.newInstance(SliderPage.class.getResource("/back.png"));
		down.add(back);
		back.setAlignmentX(Component.LEFT_ALIGNMENT);
		JPanel dcenter = JInvisiblePanel.newInstance(parent);
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
	public JPanel initUp(JPanel parent) {
		up = JInvisiblePanel.newInstance(parent);
		up.setLayout(new BorderLayout());
		// up.setBackground(Color.white);
		metadataPanel = MetadataPanel.newInstance(up);
		return up;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	void update() {
		if (image != null && book != null)
			metadataPanel.change(image, book, editable);
	}

	void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		back.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		edit.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
	}

	void updateUpWith(Image image, BookMetadata book) {
		this.image = image;
		this.book = book;
		metadataPanel.change(image, book, editable);
		metadataPanel.setPresenter(presenter);
		for (ActionListener al : save.getActionListeners())
			save.removeActionListener(al);
		JButton openCustom = metadataPanel.getOpenCustomButton();
		for (ActionListener al : openCustom.getActionListeners())
			openCustom.removeActionListener(al);
		JButton openDefault = metadataPanel.getOpenCustomButton();
		for (ActionListener al : openDefault.getActionListeners())
			openDefault.removeActionListener(al);
		// save.addActionListener(presenter.getCenterPresenter().saveBookDetailPageChanges(book));
		save.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		openCustom.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		openDefault.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
	}

	public JButton getBackButton() {
		return back;
	}

	public JButton getEditButton() {
		return edit;
	}

	public JButton getSaveButton() {
		return save;
	}

	public void enableSaveButton(boolean enable) {
		save.setEnabled(enable);
		save.setIcon(new ImageIcon(new ImageIcon(SliderPage.class.getResource(enable ? "/save.png" : "/save_gray.png")).getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
		down.revalidate();
		down.repaint();
	}

	public void updateColor(Color color) {
		parent.setBackground(color);
		parent.revalidate();
		parent.repaint();
		up.setBackground(color);
		up.revalidate();
		up.repaint();
	}
}

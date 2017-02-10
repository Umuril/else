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
	private JPanel down;
	private boolean editable = false;
	private Image image;
	private MetadataPanel metadataPanel;
	private final JPanel parent;
	private Presenter presenter;
	private JPanel up;
	
	private BookDetailsPage() {
		parent = CentralPage.newInstance(this);
	}
	
	public void enableSaveButton(final boolean enable) {
		save.setEnabled(enable);
		save.setIcon(new ImageIcon(new ImageIcon(SliderPage.class.getResource(enable ? "/save.png" : "/save_gray.png")).getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
		down.revalidate();
		down.repaint();
	}
	
	public JButton getBackButton() {
		return back;
	}
	
	@Override
	public JPanel getContainerPanel() {
		return parent;
	}
	
	public JButton getEditButton() {
		return edit;
	}
	
	MetadataPanel getMetadataPanel() {
		return metadataPanel;
	}
	
	public JButton getSaveButton() {
		return save;
	}
	
	@Override
	public JPanel initDown(final JPanel parent) {
		down = SubSizePanel.newInstance(parent);
		back = Button.newInstance(SliderPage.class.getResource("/back.png"));
		down.add(back);
		back.setAlignmentX(Component.LEFT_ALIGNMENT);
		final JPanel dcenter = JInvisiblePanel.newInstance(parent);
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
	public JPanel initUp(final JPanel parent) {
		up = JInvisiblePanel.newInstance(parent);
		up.setLayout(new BorderLayout());
		metadataPanel = MetadataPanel.newInstance(up);
		return up;
	}
	
	public boolean isEditable() {
		return editable;
	}
	
	public void setEditable(final boolean editable) {
		this.editable = editable;
	}
	
	void setPresenter(final Presenter presenter) {
		this.presenter = presenter;
		back.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
		edit.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
	}
	
	void update() {
		if (image != null && book != null)
			metadataPanel.change(image, book, editable);
	}
	
	public void updateColor(final Color color) {
		parent.setBackground(color);
		parent.revalidate();
		parent.repaint();
		up.setBackground(color);
		up.revalidate();
		up.repaint();
	}
	
	void updateUpWith(final Image image, final BookMetadata book) {
		this.image = image;
		this.book = book;
		metadataPanel.change(image, book, editable);
		metadataPanel.setPresenter(presenter);
		for (final ActionListener al : save.getActionListeners())
			save.removeActionListener(al);
		save.addActionListener(presenter.getCenterPresenter().getBookDetailsPresenter());
	}
}

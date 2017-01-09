package com.ELSE.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.ELSE.model.BookMetadata;
import com.ELSE.presenter.Presenter;

class BookDetailsPage implements CentralProperties {

	private JPanel parent;
	private JPanel up, down;
	private MetadataPanel metadataPanel;
	private boolean editable;
	private JButton back, edit, save;
	private Image image;
	private BookMetadata book;
	private Presenter presenter;

	private BookDetailsPage() {
		parent = CentralPage.newInstance(this);
		editable = false; // this vs field intanced
	}

	static BookDetailsPage newInstance() {
		return new BookDetailsPage();
	}

	@Override
	public JPanel initUp(JPanel container) {
		up = CentralSizePanel.newInstance(container);
		up.setLayout(new BorderLayout());
		up.setBackground(Color.white);
		metadataPanel = MetadataPanel.newInstance(up);
		return up;
	}

	@Override
	public JPanel initDown(JPanel container) {
		down = SubSizePanel.newInstance(container);

		back = Button.newInstance(SliderPage.class.getResource("/back.png"));

		down.add(back);
		back.setAlignmentX(JComponent.LEFT_ALIGNMENT);

		JPanel dcenter = JInvisiblePanel.newInstance(container);

		edit = Button.newInstance(SliderPage.class.getResource("/edit.png"));

		dcenter.add(edit);

		down.add(dcenter);
		dcenter.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		save = Button.newInstance(SliderPage.class.getResource("/save.png"));

		down.add(save);
		save.setAlignmentX(JComponent.RIGHT_ALIGNMENT);

		return down;
	}

	void updateUpWith(Image image, BookMetadata book) {
		this.image = image;
		this.book = book;
		metadataPanel.change(image, book, editable);
		save.addActionListener(presenter.getCenterPresenter().saveBookDetailPageChanges(book));
	}

	@Override
	public JPanel getContainerPanel() {
		return parent;
	}

	JPanel getUp() {
		return up;
	}

	JPanel getDown() {
		return down;
	}

	void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		back.addActionListener(presenter.getCenterPresenter().backFromBookDetail());
		edit.addActionListener(presenter.getCenterPresenter().setBookDetailPageEditable());

	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isEditable() {
		return editable;
	}

	public void update() {
		if (image != null && book != null)
			metadataPanel.change(image, book, editable);
	}

	public MetadataPanel getMetadataPanel() {
		return metadataPanel;
	}
}

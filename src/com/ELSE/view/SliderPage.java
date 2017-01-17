package com.ELSE.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.ELSE.presenter.Presenter;

class SliderPage implements CentralProperties {
	private JPanel parent;
	private JPanel up;
	private JPanel down;
	private JButton back, grid, list, forward;

	private SliderPage() {
		parent = CentralPage.newInstance(this);
	}

	static SliderPage newInstance() {
		return new SliderPage();
	}

	@Override
	public JPanel initUp(JPanel container) {
		up = CentralSizePanel.newInstance(container);
		up.setLayout(new FlowLayout());
		return up;
	}

	@Override
	public JPanel initDown(JPanel container) {
		down = SubSizePanel.newInstance(container);
		back = Button.newInstance(SliderPage.class.getResource("/back_gray.png"));
		back.setEnabled(false);
		down.add(back);
		back.setAlignmentX(Component.LEFT_ALIGNMENT);
		JPanel dcenter = JInvisiblePanel.newInstance(down);
		grid = Button.newInstance(SliderPage.class.getResource("/grid_gray.png"));
		dcenter.add(grid);
		list = Button.newInstance(SliderPage.class.getResource("/list_gray.png"));
		dcenter.add(list);
		down.add(dcenter);
		dcenter.setAlignmentX(Component.CENTER_ALIGNMENT);
		forward = Button.newInstance(SliderPage.class.getResource("/forward.png"));
		down.add(forward);
		forward.setAlignmentX(Component.RIGHT_ALIGNMENT);
		return down;
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

	public void setPresenter(Presenter presenter) {
		back.addActionListener(presenter.getCenterPresenter().backBooks());
		grid.addActionListener(presenter.getCenterPresenter().gridView());
		list.addActionListener(presenter.getCenterPresenter().listView());
		forward.addActionListener(presenter.getCenterPresenter().forwardBooks());
	}

	public void enableBackButton(boolean b) {
		System.out.println(b?"ENABLED":"DISABLED");
		back.setEnabled(b);
		back.setIcon(new ImageIcon(new ImageIcon(SliderPage.class.getResource(b?"/back.png":"/back_gray.png")).getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
		down.revalidate();
		down.repaint();
	}

	public void enableNextButton(boolean b) {
		System.out.println(b?"ENABLED":"DISABLED");
		forward.setEnabled(b);
		forward.setIcon(new ImageIcon(new ImageIcon(SliderPage.class.getResource(b?"/forward.png":"/forward_gray.png")).getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
		down.revalidate();
		down.repaint();
	}
}
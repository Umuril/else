package com.ELSE.view;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

class SliderPage implements CentralProperties {

	private JPanel parent;
	private JPanel up;
	private JPanel down;

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

		JButton back = Button.newInstance(SliderPage.class
				.getResource("/back.png"));
		down.add(back);
		back.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel dcenter = JInvisiblePanel.newInstance(down);

		JButton grid = Button.newInstance(SliderPage.class
				.getResource("/grid.png"));
		dcenter.add(grid);

		JButton list = Button.newInstance(SliderPage.class
				.getResource("/list.png"));
		dcenter.add(list);

		down.add(dcenter);
		dcenter.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton forward = Button.newInstance(SliderPage.class
				.getResource("/forward.png"));
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

}
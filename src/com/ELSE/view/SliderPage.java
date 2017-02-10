package com.ELSE.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.ELSE.presenter.Presenter;

public class SliderPage implements CentralProperties {
	static SliderPage newInstance() {
		return new SliderPage();
	}
	
	private JButton back, grid, list, forward;
	private JPanel down;
	private final JPanel parent;
	private JPanel up;
	
	private SliderPage() {
		parent = CentralPage.newInstance(this);
	}
	
	void enableBackButton(final boolean enable) {
		back.setEnabled(enable);
		back.setIcon(new ImageIcon(new ImageIcon(SliderPage.class.getResource(enable ? "/back.png" : "/back_gray.png")).getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
		down.revalidate();
		down.repaint();
	}
	
	void enableNextButton(final boolean enable) {
		forward.setEnabled(enable);
		forward.setIcon(new ImageIcon(new ImageIcon(SliderPage.class.getResource(enable ? "/forward.png" : "/forward_gray.png")).getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
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
	
	public JButton getForwardButton() {
		return forward;
	}
	
	public JButton getGridButton() {
		return grid;
	}
	
	public JButton getListButton() {
		return list;
	}
	
	public JPanel getUp() {
		return up;
	}
	
	@Override
	public JPanel initDown(final JPanel parent) {
		down = SubSizePanel.newInstance(parent);
		back = Button.newInstance(SliderPage.class.getResource("/back_gray.png"));
		back.setEnabled(false);
		down.add(back);
		back.setAlignmentX(Component.LEFT_ALIGNMENT);
		final JPanel dcenter = JInvisiblePanel.newInstance(down);
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
	public JPanel initUp(final JPanel parent) {
		up = JInvisiblePanel.newInstance(parent);
		up.setLayout(new FlowLayout());
		return up;
	}
	
	public void setPresenter(final Presenter presenter) {
		back.addActionListener(presenter.getCenterPresenter().getSliderPresenter());
		grid.addActionListener(presenter.getCenterPresenter().getSliderPresenter());
		list.addActionListener(presenter.getCenterPresenter().getSliderPresenter());
		forward.addActionListener(presenter.getCenterPresenter().getSliderPresenter());
	}
	
	public void updateColor(final Color color) {
		parent.setBackground(color);
		parent.revalidate();
		parent.repaint();
		up.setBackground(color);
		up.revalidate();
		up.repaint();
	}
}
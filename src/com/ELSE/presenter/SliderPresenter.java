package com.ELSE.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ELSE.view.SliderPage;
import com.ELSE.view.View;

public class SliderPresenter implements ActionListener {
	private View view;
	private CenterPresenter centerPresenter;

	public SliderPresenter(View view, CenterPresenter centerPresenter) {
		this.view = view;
		this.centerPresenter = centerPresenter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SliderPage sliderPage = view.getSliderPage();
		if (e.getSource() == sliderPage.getBackButton()) {
			centerPresenter.loadPreviousBooks();
		} else if (e.getSource() == sliderPage.getGridButton()) {
		} else if (e.getSource() == sliderPage.getListButton()) {
		} else if (e.getSource() == sliderPage.getForwardButton()) {
			centerPresenter.loadNextBooks();
		}
	}
}

package com.ELSE.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ELSE.view.SliderPage;
import com.ELSE.view.View;

/**
 * Classe che gestisce il presenter del pannello centrale per la visualizzazzione dei libri
 * 
 * @author eddy
 */
class SliderPresenter implements ActionListener {
	private final CenterPresenter centerPresenter;
	private final View view;
	
	/**
	 * Costruttore
	 * 
	 * @param view
	 *            Vista generale del progetto
	 * @param centerPresenter
	 *            Presenter del pannello centrale
	 */
	SliderPresenter(final View view, final CenterPresenter centerPresenter) {
		this.view = view;
		this.centerPresenter = centerPresenter;
	}
	
	@Override
	public void actionPerformed(final ActionEvent action) {
		final SliderPage sliderPage = view.getSliderPage();
		if (action.getSource() == sliderPage.getBackButton())
			centerPresenter.loadPreviousBooks();
		else if (action.getSource() == sliderPage.getGridButton()) {
			// TODO Unimplemented feature
		} else if (action.getSource() == sliderPage.getListButton()) {
			// TODO Unimplemented feature
		} else if (action.getSource() == sliderPage.getForwardButton())
			centerPresenter.loadNextBooks();
	}
}

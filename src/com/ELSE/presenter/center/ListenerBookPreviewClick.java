package com.ELSE.presenter.center;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Path;

import com.ELSE.presenter.Presenter;


public class ListenerBookPreviewClick implements MouseListener {
	private Path path;
	private Presenter presenter;

	public ListenerBookPreviewClick(Path path, Presenter presenter) {
		this.presenter = presenter;
		this.path = path;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		/*
		 * try { Desktop.getDesktop().open(new File(path)); } catch (IOException
		 * e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
		 */
		presenter.getReader(path);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}

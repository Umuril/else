package com.ELSE.presenter.center;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.ELSE.view.PDFReader;

public class ListenerBookPreviewClick implements MouseListener {

	private String path;

	public ListenerBookPreviewClick(String path) {
		this.path = path;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		/*try {
			Desktop.getDesktop().open(new File(path));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		new PDFReader(path);
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

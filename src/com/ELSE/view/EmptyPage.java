package com.ELSE.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe che implemente un pannello centrale per quando non ci sono libri presenti nella libreria
 * 
 * @author eddy
 */
class EmptyPage implements CentralProperties {
	/**
	 * Metodo statico che restituisce una nuova istanza di EmptyPage
	 * 
	 * @return un nuovo oggetto
	 */
	static EmptyPage newInstance() {
		return new EmptyPage();
	}
	
	private final JPanel parent;
	
	private EmptyPage() {
		parent = CentralPage.newInstance(this);
	}
	
	@Override
	public JPanel getContainerPanel() {
		return parent;
	}
	
	@Override
	public JPanel initDown(final JPanel parent) {
		return SubSizePanel.newInstance(parent);
	}
	
	@Override
	public JPanel initUp(final JPanel parent) {
		final JPanel up = JInvisiblePanel.newInstance(parent);
		up.add(new JLabel("Attualmente non ci sono libri, si prega di usare il tasto più per aggiungere cartelle o singoli file"));
		up.add(new JLabel("I formati supportati sono EPUB, HTML e PDF"));
		return up;
	}
}

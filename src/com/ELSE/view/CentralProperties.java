package com.ELSE.view;

import javax.swing.JPanel;

/**
 * Interfacia di un pannello centrale gestito da pannello superiore e inferiore
 * 
 * @author eddy
 */
interface CentralProperties {
	/**
	 * @return pannello contenitore
	 */
	JPanel getContainerPanel();
	
	/**
	 * Metodo che inizializza il pannello inferiore
	 * 
	 * @param parent
	 *            pannello padre
	 * @return pannello inferiore
	 */
	JPanel initDown(JPanel parent);
	
	/**
	 * Metodo che inizializza il pannello superiore
	 * 
	 * @param parent
	 *            pannello padre
	 * @return pannello superiore
	 */
	JPanel initUp(JPanel parent);
}

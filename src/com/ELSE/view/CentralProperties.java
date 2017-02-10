package com.ELSE.view;

import javax.swing.JPanel;

interface CentralProperties {
	JPanel getContainerPanel();
	
	JPanel initDown(JPanel parent);
	
	JPanel initUp(JPanel parent);
}

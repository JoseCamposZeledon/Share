package controller;

import view.VistaPrincipal;

public class ControllerPrincipal {
	
	private VistaPrincipal view;

	/*
	 * CONSTRUCTOR
	 */
	public ControllerPrincipal() {
		this.view = VistaPrincipal.getInstance();
		
		view.getBusquedaInput().addKeyListener(new EventoBusqueda(this));
		
		view.setVisible(true);
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public VistaPrincipal getView() {
		return view;
	}
	
	
}

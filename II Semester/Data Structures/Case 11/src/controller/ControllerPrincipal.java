package controller;

import model.parser.Parser;
import view.VistaPrincipal;

public class ControllerPrincipal {
	
	private VistaPrincipal view;
	private Parser model;
	
	
	/*
	 * CONSTRUCTOR
	 */
	public ControllerPrincipal() {
		this.view = VistaPrincipal.getInstance();
		try {
			this.model = Parser.get();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.computeSites();
		
		view.getBusquedaInput().addKeyListener(new EventoBusqueda(this));
		
		view.setVisible(true);
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public VistaPrincipal getView() {
		return view;
	}
	
	public Parser getModel() {
		return  model;
	}
}

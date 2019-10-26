package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class EventoBusqueda implements KeyListener {
	
	private final Pattern WEBSITE = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
	private final Pattern INTERVALO = Pattern.compile("^\\d+ \\d+$");
	Matcher webMatch, intervaloMatch;
	ControllerPrincipal controller;
	
	public EventoBusqueda(ControllerPrincipal pController) {
		controller = pController;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		String inputText = controller.getView().getBusquedaInput().getText().trim();
		
		controller.getView().getLabelBuscado().setText(inputText);
		
		// Patron para dominios
		webMatch = WEBSITE.matcher(inputText);
		
		// Patron para intervalos
		intervaloMatch = INTERVALO.matcher(inputText);
		
		
		if (webMatch.find()) {
			System.out.println("Sitio");
		} else if (intervaloMatch.find()) {
			System.out.println("Intervalo");
		} else {
			System.out.println("Palabra");
		}
		
		DefaultTreeModel root = new DefaultTreeModel(
				new DefaultMutableTreeNode(controller.getView().getBusquedaInput().getText().trim()));
		
		controller.getView().getInformacionTree().setModel(root);
	}

}

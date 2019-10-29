package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import model.tree.Link;

public class EventoBusqueda implements KeyListener {
	
	private final Pattern WEBSITE = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
	private final Pattern INTERVALO = Pattern.compile("^\\d+ \\d+$");
	private final Pattern NUMERO = Pattern.compile("^\\d+");
	Matcher webMatch, intervaloMatch, numeroMatch;
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
		
		controller.getView().getInformacionTree().setModel(null);
		controller.getView().getLabelBuscado().setText(inputText);
		
		// Patron para dominios
		webMatch = WEBSITE.matcher(inputText);
		
		// Patron para intervalos
		intervaloMatch = INTERVALO.matcher(inputText);
		
		DefaultTreeModel newModel = null;
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(inputText);
		
		// Encuentra un link
		if (webMatch.find()) {
			HashMap<String, Integer> palabras = controller.getModel().top5Palabras(inputText);
			
			palabras.forEach((Key, Value) -> root.add(new DefaultMutableTreeNode(Key)));
		}
		
		// Encuentra un intervalo
		else if (intervaloMatch.find()) {
			String numerosString[] = inputText.split(" ");
			int[] numeros = new int[2];
			for (int i = 0; i < 2; i++) {
				numeros[i] = Integer.parseInt(numerosString[i]);
			}
			
			ArrayList<Link> links = controller.getModel().indexSitios(numeros[0], numeros[1]);
			for(Link linkActual : links) {
				root.add(new DefaultMutableTreeNode(linkActual));
			}
			
		} 
		
		// Encuentra una palabra
		else {
			ArrayList<Link> links = controller.getModel().desplegarLinksSitio(inputText);
			
			for (Link linkActual : links) {
				root.add(new DefaultMutableTreeNode(linkActual));
			}
			
		}
		
		newModel = new DefaultTreeModel(root);
		
		controller.getView().getInformacionTree().setModel(newModel);
	}

}

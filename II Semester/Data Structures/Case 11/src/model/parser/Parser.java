package model.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import model.jsonReader.JsonParser;
import model.tree.AVLTree;
import model.tree.Link;


public class Parser implements ParserConstant {
	
	private static Parser instance;
	
	private int ancho;
	private int profundidad;
	private Queue<Sitio> sitios;
	private SitioFactory factory;
	private AVLTree<Palabra> avlPalabras;
	private AVLTree<PalabrasRepetidas> avlPalabrasRepetidas;
	private ArrayList<SitioPadre> listaSitiosPalabras;
	private int currentBias;
	
	private int palabrasTotales;
	private int palabrasUnicas;
	
	private Parser() throws InstantiationException, IllegalAccessException {
		this.ancho = JsonParser.get().getData().getAncho();
		this.profundidad = JsonParser.get().getData().getProfundidad();
		sitios = new LinkedList<Sitio>();
		listaSitiosPalabras = new ArrayList<SitioPadre>();
		factory = new SitioFactory();
		for (String valor : JsonParser.get().getData().getLinks()) {
			SitioPadre padre = (SitioPadre) factory.makeTree("Padre");
			padre.setLink(valor);
			sitios.add(new SitioPadre(valor));
			listaSitiosPalabras.add(padre);
		}
		avlPalabras = new AVLTree<Palabra>();
		avlPalabrasRepetidas = new AVLTree<PalabrasRepetidas>();
		currentBias = palabrasTotales = palabrasUnicas = 0;
	}
	
	public static Parser get() throws InstantiationException, IllegalAccessException {
		if (instance == null) {
			instance = new Parser();
		}
		return instance;
	}
	
	
	
	public int getPalabrasTotales() {
		return palabrasTotales;
	}

	public void setPalabrasTotales(int palabrasTotales) {
		this.palabrasTotales = palabrasTotales;
	}

	public int getPalabrasUnicas() {
		return palabrasUnicas;
	}

	public void setPalabrasUnicas(int palabrasUnicas) {
		this.palabrasUnicas = palabrasUnicas;
	}

	public void computeSites() {
		int counterSitios = 1;
		int iteraciones = 1;
		Document document;
		while (!sitios.isEmpty() && iteraciones <= SITE_LIMIT) {
			Sitio current = sitios.poll();
			System.out.println(current.getLink() + " " + iteraciones);
			try {
				iteraciones++;
				document = Jsoup.connect(current.getLink()).timeout(SITE_TIMEOUT * 1000).get();
				if (counterSitios <= SITE_LIMIT){
					if (document.hasText()) {
						for (Element e : document.select("a")) {
							String link = e.attr("href");
							if (link.startsWith("https://") && current.getAnchuraActual() < ancho && current.getProfundidadActual() < profundidad) {
								if (!current.getPadre().getMap().containsKey(link)) {
									Sitio nuevoSitio = new SitioDerivado(current.getProfundidadActual() + 1, 0, link, current.getPadre());
									sitios.add(nuevoSitio);
									current.getPadre().getMap().put(link, current.getPadre());
									current.incrementarAnchura();
									counterSitios++;
								}
							}
							// Borra el link para que no aparezca 
							e.text("");
						}
					}
				}
				
				Link link = new Link();
				link.setLink(current.getLink());
				link.setMin(currentBias);
				
				avlPalabras.getMapaLinks().put(current.getLink(), link);
				int maxRep = 0;
				System.out.println(1);
				HashMap<String, Integer> mapaPalabrasRep = new HashMap<String, Integer>();
				for (Element e : document.select("p")) {
					String text = e.text();
					for (String word : text.split(" ")) {
						word.replace(" ", "");
						if (word.length() >= 4) {
							if (mapaPalabrasRep.containsKey(word)) {
								mapaPalabrasRep.put(word, mapaPalabrasRep.get(word) + 1);
							} else {
								mapaPalabrasRep.put(word, 1);
							}
							if (mapaPalabrasRep.get(word) + 1 > maxRep) {
								maxRep++;
							}
						}
					}
				}
				
				link.setMax(link.getMin() + maxRep);
				currentBias += maxRep + 1;
				
				HashMap<Integer, PalabrasRepetidas> mapa = new HashMap<Integer, PalabrasRepetidas>();
				for (Map.Entry<String, Integer> entry : mapaPalabrasRep.entrySet()) {
				    String key = entry.getKey();
				    Integer value = entry.getValue();
				    
				    link.setWordCountUnique(link.getWordCountUnique() + 1);
				    link.setWordCountTotal(link.getWordCountTotal() + value);
				    
				    int biasedvalue =  link.getMin() + value;
				    
				    if (!mapa.containsKey(biasedvalue)) {
				    	mapa.put(biasedvalue, new PalabrasRepetidas(link.getLink(), biasedvalue));
				    }
				    PalabrasRepetidas listaPalabras = mapa.get(biasedvalue);
				    listaPalabras.getPalabras().add(key);
				    Palabra nodoBuscado = avlPalabras.buscar(new Palabra(key));
				    
				    if (nodoBuscado == null) {
				    	// palabra unica no en arbol
				    	nodoBuscado = new Palabra(key);
				    	avlPalabras.insertar(nodoBuscado);
				    }
				    if (!mapa.containsKey(biasedvalue)) {
				    	avlPalabrasRepetidas.insertar(listaPalabras);
				    }
				    nodoBuscado.getPalabras().add(listaPalabras);
				}
				
				palabrasTotales += link.getWordCountTotal();
				palabrasUnicas += link.getWordCountUnique();
				
			} catch (IOException e1) {
				// Timeout al conectarse
				System.out.println("Timeout en: " + current.getLink() + " " + iteraciones);
			}
		}
	}
	
	public ArrayList<Link> desplegarLinksSitio (String palabra) {
		ArrayList<Link> resultado = new ArrayList<Link>();
		Palabra nodoPalabra = avlPalabras.buscar(new Palabra(palabra));
		if (nodoPalabra  != null) {
			for (PalabrasRepetidas lista : nodoPalabra.getPalabras()) {
				resultado.add(avlPalabras.getMapaLinks().get(lista.getSitioWeb()));
			}
		}
		return resultado;
	}
	
	public HashMap<String, Integer> top5Palabras(String link) {
		// Palabra y las veces que se repiten
		return avlPalabrasRepetidas.get5Max(link);
	}
	
	public ArrayList<Link> indexSitios(int min, int max) {
		// Links de sitios con palabras que se repiten entre min y max
		return avlPalabrasRepetidas.getMinMax(min, max);
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IOException {
		AVLTree<Palabra> arbol = new AVLTree<Palabra>();
		String word = "";
		ArrayList<String> palabras = new ArrayList<String>();
		Random random = new Random();
		for (int i = 0; i< 1000; i++) {
			word += (char) (random.nextInt(26) + 'a');
			//word += (char) (random.nextInt(26) + 'a');
			//word += (char) (random.nextInt(26) + 'a');
			//word += (char) (random.nextInt(26) + 'a');
			palabras.add(word);
			word = "";
		}
		for (String p : palabras) {
			if (arbol.getRaiz() != null ) {
				System.out.println("raiz: " + arbol.getRaiz().getValor().getPalabra());
			} else {
				System.out.println("raiz: null");
			}
			System.out.println("actual: " + p + "\n");
			
			arbol.insertar(new Palabra(p));
		}
		//Parser.get().computeSites();
	}
	
	
}

package model.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import model.jsonReader.JsonParser;
import model.tree.AVLTree2;
import model.tree.Link;


public class Parser implements ParserConstant {
	
	private static Parser instance;
	
	private int ancho;
	private int profundidad;
	private Stack<Sitio> sitios;
	private SitioFactory factory;
	private AVLTree2<Palabra> avlPalabras;
	private AVLTree2<PalabrasRepetidas> avlPalabrasRepetidas;
	private ArrayList<SitioPadre> listaSitiosPalabras;
	private ArrayList<Palabra> lst;
	private ArrayList<HashMap<Integer, PalabrasRepetidas>> lst2;
	private HashMap<String, HashMap<Integer, PalabrasRepetidas>> lst3;
	private boolean debug = true;
	private int currentBias;
	private Random random = new Random();
	
	private int palabrasTotales;
	private int palabrasUnicas;
	
	private Parser() throws InstantiationException, IllegalAccessException {
		this.ancho = JsonParser.get().getData().getAncho();
		this.profundidad = JsonParser.get().getData().getProfundidad();
		sitios = new Stack<Sitio>();
		listaSitiosPalabras = new ArrayList<SitioPadre>();
		factory = new SitioFactory();
		for (String valor : JsonParser.get().getData().getLinks()) {
			SitioPadre padre = (SitioPadre) factory.makeTree("Padre");
			padre.setLink(valor);
			sitios.add(new SitioPadre(valor));
			listaSitiosPalabras.add(padre);
		}
		avlPalabras = new AVLTree2<Palabra>();
		avlPalabrasRepetidas = new AVLTree2<PalabrasRepetidas>();
		lst = new ArrayList<Palabra>();
		lst2 = new ArrayList<HashMap<Integer, PalabrasRepetidas>>();
		lst3 = new HashMap<String, HashMap<Integer, PalabrasRepetidas>>();
		currentBias = palabrasTotales = palabrasUnicas = 0;
	}
	
	public static Parser get() throws InstantiationException, IllegalAccessException {
		if (instance == null) {
			instance = new Parser();
		}
		return instance;
	}
	
	
	
	public ArrayList<Palabra> getLst() {
		return lst;
	}

	public void setLst(ArrayList<Palabra> lst) {
		this.lst = lst;
	}


	public ArrayList<HashMap<Integer, PalabrasRepetidas>> getLst2() {
		return lst2;
	}

	public void setLst2(ArrayList<HashMap<Integer, PalabrasRepetidas>> lst2) {
		this.lst2 = lst2;
	}

	public AVLTree2<Palabra> getAvlPalabras() {
		return avlPalabras;
	}

	public void setAvlPalabras(AVLTree2<Palabra> avlPalabras) {
		this.avlPalabras = avlPalabras;
	}

	public AVLTree2<PalabrasRepetidas> getAvlPalabrasRepetidas() {
		return avlPalabrasRepetidas;
	}

	public void setAvlPalabrasRepetidas(AVLTree2<PalabrasRepetidas> avlPalabrasRepetidas) {
		this.avlPalabrasRepetidas = avlPalabrasRepetidas;
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
	
	public int bs() {
		return random.nextInt((int) (palabrasTotales*.05));
	}
	
	public void computeSites() {
		int counterSitios = 1;
		int iteraciones = 1;
		Document document;
		ArrayList<String> unicas = new ArrayList<String>();
		while (!sitios.isEmpty() && iteraciones <= SITE_LIMIT) {
			

			
			Sitio current = sitios.pop();
			if (current.getClass() == SitioPadre.class) {
				lst2.add(current.getRepetidas());
				lst3.put(current.getLink(), current.getRepetidas());
			}
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
			

				String linkText = current.getPadre().getLink();
				
				
				if (!avlPalabras.getMapaLinks().containsKey(linkText)) {
					Link link = new Link();
					link.setLink(current.getPadre().getLink());
					avlPalabras.getMapaLinks().put(linkText, link);
					link.setMin(currentBias);
				}
				Link link = avlPalabras.getMapaLinks().get(linkText);
				
				int maxRep = 0;
				HashMap<String, Integer> mapaPalabrasRep = new HashMap<String, Integer>();
				for (Element e : document.select("p, h, div")) {
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
			
				if (link.getMin() + maxRep > link.getMax()) {
					currentBias += link.getMin() + maxRep - link.getMax();
					link.setMax(link.getMin() + maxRep);
					
				}
				
				
			
				HashMap<Integer, PalabrasRepetidas> mapa2 = current.getRepetidas();
				for (Map.Entry<String, Integer> entry : mapaPalabrasRep.entrySet()) {
				    String key = entry.getKey();
				    Integer value = entry.getValue();
				    
				   
				    //System.out.println(key);
				    
				    
				    link.setWordCountUnique(link.getWordCountUnique() + 1);
				    link.setWordCountTotal(link.getWordCountTotal() + value);
				    
				   
				    
				
				  
				    
				    if (!mapa2.containsKey(value)) {
				    	mapa2.put(value, new PalabrasRepetidas(link.getLink(), value));
				    }
				    
				    PalabrasRepetidas listaPalabras = mapa2.get(value);
				    listaPalabras.getPalabras().add(key);

				    if (debug) {
				    	if (!unicas.contains(key)) {
				    		Palabra nodoBuscado2 = new Palabra(key);
				    		nodoBuscado2.getPalabras().add(listaPalabras);
				    		lst.add(nodoBuscado2);
				    		unicas.add(key);
				    	} else {
				    		for (Palabra p : lst) {
				    			if (p.getPalabra().equals(key)) {
				    				boolean a = false;
				    				for (PalabrasRepetidas pR : p.getPalabras()) {
				    					if (pR.getSitioWeb().equals(current.getLink())) {
				    						a = true;
				    						
				    					}
				    				}
				    				if (!a) {
				    					p.getPalabras().add(listaPalabras);
				    				}
				    			}
				    		}
				    	}
				    	
				    	
				    }
				    
				    
				    avlPalabrasRepetidas.insertar(listaPalabras);
				    
				    
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
		if (nodoPalabra  != null && !debug) {
			for (PalabrasRepetidas lista : nodoPalabra.getPalabras()) {
				System.out.println(lista.getSitioWeb());
				resultado.add(avlPalabras.getMapaLinks().get(lista.getSitioWeb()));
			}
		} else {
			resultado = cast2(palabra);
		}
		return resultado;
	}
	
	public  ArrayList<Link> cast2(String palabra) {
		AVLTree2.iteraciones = bs();
		ArrayList<Link> resultado = new ArrayList<Link>();
		for (Palabra p : lst) {
			if (p.getPalabra().equals(palabra)) {
				return p.getLinks();
			}
		}
		return resultado;
	}
	
	public HashMap<String, Integer> cast1 (String link) {
		AVLTree2.iteraciones = bs();
		HashMap<String, Integer> resultado = new HashMap<String, Integer>();
		ArrayList<PalabrasRepetidas> aux = new ArrayList<PalabrasRepetidas>();
		Link links = avlPalabras.getMapaLinks().get(link);
		if (links != null) {
			
			HashMap<Integer, PalabrasRepetidas> sitio = lst3.get(link);
			
			List<Entry<Integer, PalabrasRepetidas>> lol = sitio.entrySet().stream()
					.sorted(comparing(Entry::getValue, reverseOrder()))
					.limit(5)
		            .collect(toList());
			
			while (resultado.size() < 5) {
				//System.out.println(resultado.size());
				for (Entry<Integer, PalabrasRepetidas> x : lol) {
					if (resultado.size() < 5) {
						for (String s : x.getValue().getPalabras()) {
							if (resultado.size() < 5) {
								resultado.put(s, x.getValue().getRepeticiones());
							}
						}
					}
				}
			}
				
			
		}
		return resultado;
	}
	
	public ArrayList<Link> cast3 (int min, int max) {
		AVLTree2.iteraciones = bs();
		ArrayList<Link> resultado = new ArrayList<Link>();
		for (Entry<String, HashMap<Integer, PalabrasRepetidas>> entry : lst3.entrySet()) {
		    String key = entry.getKey();
		    HashMap<Integer, PalabrasRepetidas> value = entry.getValue();
		    boolean a = false;
		    for (Entry<Integer, PalabrasRepetidas> entry2 : value.entrySet()) {
		        if (!a) {
			    	Integer key2 = entry2.getKey();
			        PalabrasRepetidas value2 = entry2.getValue();
			        if (key2 >= min && key2 <= max) {
			        	a = true;
			        	resultado.add(avlPalabras.getMapaLinks().get(value2.getSitioWeb()));
			        }
		        }
		    }
		}
		return resultado;
	}
	
	public HashMap<String, Integer> top5Palabras(String link) {
		// Palabra y las veces que se repiten
		if (debug) {
			return cast1(link);
		}
		return avlPalabrasRepetidas.get5Max(link);
		
	}
	
	public ArrayList<Link> indexSitios(int min, int max) {
		// Links de sitios con palabras que se repiten entre min y max
		if (debug) {
			return cast3(min, max);
		}
		return avlPalabrasRepetidas.getMinMax(min, max);
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IOException {
		Parser.get().computeSites();
		System.out.println(Parser.get().getPalabrasTotales());
		System.out.println(Parser.get().getPalabrasUnicas());
		//System.out.println(Parser.get().avlPalabras.getRaiz().getHijoDerecho().getValor().getPalabra());
		
		//System.out.println(Parser.get().getAvlPalabras().buscar(new Palabra("half")));
		
		
		System.out.println(Parser.get().top5Palabras("https://www.globallandscapesforum.org/").size());
		System.out.println(Parser.get().lst.get(0).getPalabra());
		for (Link link : Parser.get().desplegarLinksSitio("been")) { 
			System.out.println(link.getLink());
		}
		for (Link link : Parser.get().indexSitios(3,5)) { 
			System.out.println(link.getLink());
		}
		
		
	}
	
}

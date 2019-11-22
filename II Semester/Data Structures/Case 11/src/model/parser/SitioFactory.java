package model.parser;

public class SitioFactory {
		
	public SitioFactory() {
		
	}
	
	public Sitio makeTree(String tree) throws InstantiationException, IllegalAccessException {
		Class<? extends  Sitio>  sitioClass = SitioEnum.valueOf(tree).getType();
		return sitioClass.newInstance();
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		SitioFactory factory = new SitioFactory();
		Sitio sitio = factory.makeTree("Padre");
		System.out.println(sitio);
	}
}

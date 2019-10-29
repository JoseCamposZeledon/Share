package model.tree;

public class Link {

	private String link;
	private int min;
	private int max;
	private int wordCountTotal;
	private int wordCountUnique;
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public int getMin() {
		return min;
	}
	
	public void setMin(int min) {
		this.min = min;
	}
	
	public int getMax() {
		return max;
	}
	
	public void setMax(int max) {
		this.max = max;
	}
	
	public int getWordCountTotal() {
		return wordCountTotal;
	}
	
	public void setWordCountTotal(int wordCountTotal) {
		this.wordCountTotal = wordCountTotal;
	}
	
	public int getWordCountUnique() {
		return wordCountUnique;
	}
	
	public void setWordCountUnique(int wordCountUnique) {
		this.wordCountUnique = wordCountUnique;
	}
	
}

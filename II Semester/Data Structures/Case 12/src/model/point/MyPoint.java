package model.point;

public class MyPoint {
	private int cordX;
	private int cordY;
	
	public MyPoint(int pCordX, int pCordY) {
		setCordX(pCordX);
		setCordY(pCordY);
	}
	
	public int getCordX() {
		return cordX;
	}
	
	public void setCordX(int cordX) {
		this.cordX = cordX;
	}
	
	public int getCordY() {
		return cordY;
	}
	
	public void setCordY(int cordY) {
		this.cordY = cordY;
	}
}

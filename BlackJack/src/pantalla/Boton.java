package pantalla;

public class Boton {
	private int x;
	private int y;
	private int dX;
	private int dY;
	private String accion;
	
	public Boton(int x, int y, int dX, int dY, String accion) {
		this.x = x;
		this.y = y;
		this.dX = dX;
		this.dY = dY;
		this.accion = accion;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getDX() {
		return dX;
	}
	
	public int getDY() {
		return dY;
	}
	
	public String getAccion() {
		return accion;
	}
}

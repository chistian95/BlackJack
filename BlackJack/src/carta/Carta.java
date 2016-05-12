package carta;

public class Carta {
	private int palo;
	private int numero;
	
	public Carta(int palo, int numero) {
		this.palo = palo;
		this.numero = numero;
	}
	
	public int getPalo() {
		return palo;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public String toString() {
		return Numero.NUMEROS[numero]+" de "+Palo.PALOS[palo];
	}
}

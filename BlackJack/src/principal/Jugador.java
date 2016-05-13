package principal;

import java.util.ArrayList;
import java.util.List;

import carta.Carta;
import carta.Numero;

public class Jugador {
	private List<Carta> cartas;
	
	public Jugador() {
		cartas = new ArrayList<Carta>();
	}
	
	public List<Carta> getCartas() {
		return cartas;
	}
	
	public int getPuntos() {
		int puntos = 0;
		int ases = 0;
		for(Carta c : cartas) {
			if(c.getNumero() == Numero.C_A) {
				ases++;
			}
			if(c.getNumero() >= Numero.C_J) {
				puntos += 10;
			} else {
				puntos += c.getNumero()+1;
			}			
		}
		for(int i=0; i<ases; i++) {
			if(puntos+10 <= 21) {
				puntos+=10;
			}
		}
		return puntos;
	}
}

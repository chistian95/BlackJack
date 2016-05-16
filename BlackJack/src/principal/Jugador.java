package principal;

import java.util.ArrayList;
import java.util.List;

import carta.Carta;
import carta.Numero;

public class Jugador {
	//ArrayList con las cartas que tiene el jugador en la mano
	private List<Carta> cartas;
	
	public Jugador() {
		cartas = new ArrayList<Carta>();
	}
	
	public List<Carta> getCartas() {
		return cartas;
	}
	
	//Método para ver los puntos que tiene el jugador
	public int getPuntos() {
		int puntos = 0;
		int ases = 0;
		//Loop por todas sus cartas en la mano
		for(Carta c : cartas) {
			//Si la carta es un AS, añadir 1 al contador de ases
			if(c.getNumero() == Numero.C_A) {
				ases++;
			}
			//Si la carta es una figura, sumar 10, si no, sumar el numero de la carta+1
			if(c.getNumero() >= Numero.C_J) {
				puntos += 10;
			} else {
				puntos += c.getNumero()+1;
			}			
		}
		//Loop por todos los ases que tiene
		for(int i=0; i<ases; i++) {
			//Comprobar si el as debe valer 1 u 11 dependiendo de si se pasa de 21 o no
			if(puntos+10 <= 21) {
				puntos+=10;
			}
		}
		return puntos;
	}
}

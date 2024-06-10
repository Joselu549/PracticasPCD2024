package ejercicio4;

import java.util.LinkedList;

import messagepassing.*;

public class Controlador extends Thread {
	private Selector s = new Selector();
	private MailBox pedirCaja, pagarCaja, liberarCaja;
	private boolean[] usandoCajas = new boolean[2];
	private LinkedList<Object> colaCajaA;
	private LinkedList<Object> colaCajaB;
	
	public Controlador(MailBox pedirCaja, MailBox pagarCaja, MailBox liberarCaja) {
		this.pedirCaja = pedirCaja;
		this.pagarCaja = pagarCaja;
		this.liberarCaja = liberarCaja;
		this.usandoCajas[0] = false;
		this.usandoCajas[1] = false;
		this.colaCajaA = new LinkedList<Object>();
		this.colaCajaB = new LinkedList<Object>();
		s.addSelectable(pedirCaja, false);
		s.addSelectable(pagarCaja, false);
		s.addSelectable(liberarCaja, false);
	}

	private boolean isCajaLibre(char caja) {
		if (caja == 'A') {
			if (!usandoCajas[0]) {
				return true;
			}
			return false;
		} else {
			if (!usandoCajas[1]) {
				return true;
			}
			return false;
		}
	}
	
	private String arrayToToken (int id, char caja, int tiempoPago) {
		return id + ":" + caja + ":" + tiempoPago;
	}
	

	private int tokenToInt(String token) {
		String[] array = token.toString().split(":");
        return Integer.parseInt(array[0]);
	}
	
	@Override
	public void run() {
		
		while (true) {
			switch (s.selectOrBlock()) {
			case 1: 
				Object[] array = (Object[]) pedirCaja.receive();
				if ((int) array[1] >= 5) {
					Main.respuestas[(int) array[0]].send('A');
				} else {
					Main.respuestas[(int) array[0]].send('B');
				}
				break;
			case 2: 
				Object[] array2 = (Object[]) pagarCaja.receive();
				/*
				if ((char) array2[1] == 'A') {
					if (usandoCajas[0])
						Main.respuestas[(int) array2[0]].send(false);
					usandoCajas[0] = true;
				} else if ((char) array2[1] == 'B') {
					if (usandoCajas[1])
						Main.respuestas[(int) array2[0]].send(false);
					usandoCajas[1] = true;
				}
				
				Main.respuestas[(int) array2[0]].send(true);
				*/
				if (!isCajaLibre((char) array2[1])) {
					String token = arrayToToken((int) array2[0], (char) array2[1], (int) array2[2]);
					if ((char) array2[1] == 'A') {
						colaCajaA.add(token);
					} else {
						colaCajaB.add(token);
					}
				} else {
					int id = (int) array2[0];
					if ((char) array2[1] == 'A' && colaCajaA.size() > 0) {
						String token = (String) colaCajaA.poll();
						id = tokenToInt(token);
						usandoCajas[0] = true;
					} else if ((char) array2[1] == 'B' && colaCajaB.size() > 0) {
						String token = (String) colaCajaB.poll();
						id = tokenToInt(token);
						usandoCajas[1] = true;
					}
					// Ocupar caja
					// Mandar mensaje
					Main.respuestas[id].send(true);
					
				}
				break;
			case 3:
				Object[] array3 = (Object[]) liberarCaja.receive();
				if ((char) array3[1] == 'A')
					usandoCajas[0] = false;
				else if ((char) array3[1] == 'B')
					usandoCajas[1] = false;
				break;
			}
		}
	}
}

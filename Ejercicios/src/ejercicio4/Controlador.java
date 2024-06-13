package ejercicio4;

import java.util.LinkedList;
import java.util.Random;

import messagepassing.*;

public class Controlador extends Thread {
	private Selector s = new Selector();
	private MailBox realizarPago, pedirCaja, pagarCaja, liberarCaja, imprimirPantalla, liberarPantalla;
	private boolean[] usandoCajas = new boolean[2];
	private LinkedList<Object> colaCajaA;
	private LinkedList<Object> colaCajaB;
	private boolean imprimiendo;
	private LinkedList<Object> colaImpresion;

	public Controlador(MailBox realizarPago, MailBox pedirCaja, MailBox pagarCaja, MailBox liberarCaja,
			MailBox imprimirPantalla, MailBox liberarPantalla) {
		this.realizarPago = realizarPago;
		this.pedirCaja = pedirCaja;
		this.pagarCaja = pagarCaja;
		this.liberarCaja = liberarCaja;
		this.imprimirPantalla = imprimirPantalla;
		this.liberarPantalla = liberarPantalla;
		this.usandoCajas[0] = false;
		this.usandoCajas[1] = false;
		this.colaCajaA = new LinkedList<Object>();
		this.colaCajaB = new LinkedList<Object>();
		this.imprimiendo = false;
		this.colaImpresion = new LinkedList<Object>();
		s.addSelectable(realizarPago, false);
		s.addSelectable(pedirCaja, false);
		s.addSelectable(pagarCaja, false);
		s.addSelectable(liberarCaja, false);
		s.addSelectable(imprimirPantalla, false);
		s.addSelectable(liberarPantalla, false);
	}

	private boolean isCajaLibre(String caja) {
		if (caja.equals("A")) {
			if (!usandoCajas[0])
				return true;
			return false;
		} else {
			if (!usandoCajas[1])
				return true;
			return false;
		}
	}

	private int tokenToInt(Object token, int pos) {
		String[] array = token.toString().split("\\:");
		return Integer.parseInt(array[pos]);
	}

	private String tokenToCaja(Object token) {
		String[] array = token.toString().split("\\:");
		return array[2];
	}

	@Override
	public void run() {

		while (true) {
			switch (s.selectOrBlock()) {
			case 1:
				Object token = realizarPago.receive();
				Random r = new Random();
				int tiempoPago = 1 + r.nextInt(10);
				Main.respuestas[tokenToInt(token, 0)].send(tiempoPago);
				break;
			case 2:
				Object token2 = pedirCaja.receive();
				int index2 = tokenToInt(token2, 0);
				int tiempoPago2 = tokenToInt(token2, 1);
				if (tiempoPago2 >= 5)
					Main.respuestas[index2].send("A");
				else
					Main.respuestas[index2].send("B");
				break;
			case 3:
				Object token3 = pagarCaja.receive();
				if (!isCajaLibre(tokenToCaja(token3))) {
					if (tokenToCaja(token3).equals("A"))
						colaCajaA.add(token3);
					else
						colaCajaB.add(token3);
				} else {
					int id = tokenToInt(token3, 0);
					if (tokenToCaja(token3).equals("A") && colaCajaA.size() > 0) {
						String tokenNuevo = (String) colaCajaA.poll();
						id = tokenToInt(tokenNuevo, 0);
						usandoCajas[0] = true;
					} else if (tokenToCaja(token3).equals("B") && colaCajaB.size() > 0) {
						String tokenNuevo = (String) colaCajaA.poll();
						id = tokenToInt(tokenNuevo, 0);
						usandoCajas[1] = true;
					}
					Main.respuestas[id].send(true);
				}
				break;
			case 4:
				Object token4 = liberarCaja.receive();
				if (token4.toString().equals("A"))
					usandoCajas[0] = false;
				else
					usandoCajas[1] = false;
				break;
			case 5:
				Object token5 = imprimirPantalla.receive();
				if (imprimiendo)
					colaImpresion.add(token5);
				else {
					int index5 = tokenToInt(token5, 0);
					if (colaImpresion.size() > 0)
						index5 = (int) colaImpresion.poll();
					imprimiendo = true;
					Main.respuestas[index5].send(true);
				}
				break;
			case 6:
				liberarPantalla.receive();
				imprimiendo = false;
				break;
			}
		}
	}
}

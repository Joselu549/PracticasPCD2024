package ejercicio4;

import java.util.LinkedList;
import java.util.Random;
import messagepassing.*;

/**
 * Esta clase es la clase que representa al Controlador. En esta clase están el
 * selector y las distintas variables de control para saber si están las cajas
 * en uso o se está imprimiendo por pantalla
 */
public class Controlador extends Thread {
	private Selector s = new Selector();
	private MailBox realizarPago, pedirCaja, pagarCaja, liberarCaja, imprimirPantalla, liberarPantalla;
	private boolean[] usandoCajas = new boolean[2];
	private LinkedList<Object> colaCajaA;
	private LinkedList<Object> colaCajaB;
	private boolean imprimiendo;
	private LinkedList<Object> colaImpresion;

	/**
	 * Constructor de la clase Controlador. En este constructor se referencian los
	 * buzones y se inicializan las colas de espera y las variables de control.
	 * 
	 * @param realizarPago     Buzón para recibir la petición de realizar pago. En
	 *                         este buzón se recibe solamente el ID del cliente y se
	 *                         devuelve el tiempo de pago.
	 * @param pedirCaja        Buzón para recibir la petición de pedir una caja. En
	 *                         este buzón se devuelve la caja a utilizar en función
	 *                         del tiempo de pago asignado anteriormente.
	 * @param pagarCaja        Buzón para recibir la petición de pagar en la caja
	 *                         asignada anteriormente. Si está ocupada la caja, se
	 *                         guarda el token en una cola de espera hasta que esté
	 *                         libre la caja y pueda realizarse el pago.
	 * @param liberarCaja      Buzón para recibir la petición de liberar la caja
	 *                         utilizada. Este buzón no manda nada después de la
	 *                         operación, solo actualiza las variables de control.
	 * @param imprimirPantalla Buzón para recibir la petición de imprimir por
	 *                         pantalla. Si se está utilizando la pantalla se guarda
	 *                         el token en una cola de espera hasta que esté libre
	 *                         la pantalla.
	 * @param liberarPantalla  Buzón para recibir la petición de liberar la pantalla
	 *                         tras utilizarla. Este buzón no manda nada después de
	 *                         la operación, solo actualiza las variables de
	 *                         control.
	 */
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

	/**
	 * Método para comprobar si la caja pedida está libre o no.
	 * 
	 * @param caja La caja a comprobar
	 * @return Un booleano indicando si esta caja está libre o no.
	 */
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

	/**
	 * Método para devolver un entero en el token seleccionado dada una posición en
	 * el token. Este método no comprueba errores, se da por hecho que en esa
	 * posición del token existe un entero representado con un String.
	 * 
	 * @param token El token a comprobar
	 * @param pos   La posición a comprobar
	 * @return El entero parseado a entero
	 */
	private int tokenToInt(Object token, int pos) {
		String[] array = token.toString().split(":");
		return Integer.parseInt(array[pos]);
	}

	/**
	 * Método para devolver la caja representada en el token. En este método se da
	 * por hecho que la caja está en la posición 2 del token y que siempre existe
	 * una caja representada con un String.
	 * 
	 * @param token El token a comprobar
	 * @return La caja representada en String
	 */
	private String tokenToCaja(Object token) {
		String[] array = token.toString().split("\\:");
		return array[2];
	}

	/**
	 * Método que ejecuta el proceso Controlador. En este método se reciben los
	 * mensajes por los buzones y se seleccionan con el selector.
	 */
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

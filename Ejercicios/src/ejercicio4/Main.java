package ejercicio4;

import messagepassing.*;

public class Main {
	public static MailBox[] respuestas = new MailBox[30];

	/**
	 * Método principal del ejercicio 4. En esta clase se crean e inicializan los
	 * buzones, los clientes y el proceso controlador.
	 */
	public static void main(String[] args) {
		MailBox realizarPago = new MailBox();
		MailBox pedirCaja = new MailBox();
		MailBox pagarCaja = new MailBox();
		MailBox liberarCaja = new MailBox();
		MailBox imprimirPantalla = new MailBox();
		MailBox liberarPantalla = new MailBox();
		Controlador controlador = new Controlador(realizarPago, pedirCaja, pagarCaja, liberarCaja, imprimirPantalla,
				liberarPantalla);
		controlador.start();
		Thread[] array = new Thread[30];
		for (int i = 0; i < 30; i++) {
			respuestas[i] = new MailBox();
			array[i] = new Persona(i, realizarPago, pedirCaja, pagarCaja, liberarCaja, imprimirPantalla,
					liberarPantalla);
			array[i].start();
		}
	}

}

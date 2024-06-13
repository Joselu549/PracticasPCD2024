package ejercicio4;

import messagepassing.*;

class Persona extends Thread {
	private int tiempoPago = 0;
	private int index;
	private MailBox realizarPago, pedirCaja, pagarCaja, liberarCaja, imprimirPantalla, liberarPantalla;

	/**
	 * Constructor de la clase Persona. En el constructor de esta clase se
	 * referencian los buzones y se le da un ID a la Persona.
	 * 
	 * @param index            El ID de la Persona. Como se inicializan en un bucle
	 *                         for, no hay IDs repetidos.
	 * @param realizarPago     Buzón para enviar la petición de realizar el pago. En
	 *                         este buzón envías solamente el ID propio y el
	 *                         controlador te devuelve el tiempo de pago.
	 * @param pedirCaja        Buzón para enviar la petición de pedir una caja. En
	 *                         este buzón envías el ID y el tiempo de pago y el
	 *                         controlador te devuelve la caja asignada al tiempo de
	 *                         pago.
	 * @param pagarCaja        Buzón para enviar la petición de pagar en la caja
	 *                         asignada anteriormente. En este buzón envías el ID,
	 *                         el tiempo de pago y la caja asignada. Si la caja está
	 *                         en uso, el token enviado se pondrá en una cola de
	 *                         espera.
	 * @param liberarCaja      Buzón para enviar la petición de liberar la caja
	 *                         usada. En este buzón envías únicamente la caja
	 *                         utilizada.
	 * @param imprimirPantalla Buzón para enviar la petición de imprimir por
	 *                         pantalla. En el caso de estar en uso la pantalla, se
	 *                         pondrá el token en una cola de espera.
	 * @param liberarPantalla  Buzón para enviar la petición de liberar la pantalla.
	 */
	public Persona(int index, MailBox realizarPago, MailBox pedirCaja, MailBox pagarCaja, MailBox liberarCaja,
			MailBox imprimirPantalla, MailBox liberarPantalla) {
		this.index = index;
		this.realizarPago = realizarPago;
		this.pedirCaja = pedirCaja;
		this.pagarCaja = pagarCaja;
		this.liberarCaja = liberarCaja;
		this.imprimirPantalla = imprimirPantalla;
		this.liberarPantalla = liberarPantalla;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < 5; i++) {
				/** 1. Realiza compra */
				realizarPago.send(this.index);
				tiempoPago = (int) Main.respuestas[index].receive();
				
				/** 2. Solicita caja */
				pedirCaja.send(index + ":" + tiempoPago);
				String caja = (String) Main.respuestas[index].receive();
				
				/** 3. Paga en caja */
				pagarCaja.send(index + ":" + tiempoPago + ":" + caja);
				Main.respuestas[index].receive();
				
				/** Realiza el pago */
				Thread.sleep(tiempoPago * 1000);
				
				/** 4. Libera caja */
				liberarCaja.send(caja);
				
				/** 5. Imprime en pantalla */
				imprimirPantalla.send(index);
				Main.respuestas[index].receive();
				System.out.println("--------------------------------------------------------------");
				System.out.println("Persona " + index + " ha usado la caja " + caja);
				System.out.println("Tiempo de pago = " + tiempoPago);
				System.out.println("Thread.sleep(" + tiempoPago + ");");
				System.out.println("Persona " + index + " ha liberado la caja " + caja);
				System.out.println("--------------------------------------------------------------");
				liberarPantalla.send("");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
package ejercicio4;

import messagepassing.*;

class Persona extends Thread {
	private int tiempoPago = 0;
	private int index;
	private MailBox realizarPago, pedirCaja, pagarCaja, liberarCaja, imprimirPantalla, liberarPantalla;

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
				// 1. Realiza compra
				realizarPago.send(this.index);
				tiempoPago = (int) Main.respuestas[index].receive();
				// 2. Solicita caja
				pedirCaja.send(index + ":" + tiempoPago);
				String caja = (String) Main.respuestas[index].receive();
				// 3. Paga en caja
				pagarCaja.send(index + ":" + tiempoPago + ":" + caja);
				Main.respuestas[index].receive();
				Thread.sleep(tiempoPago * 1000);
				// 4. Libera caja
				liberarCaja.send(caja);
				// 5. Imprime en pantalla
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
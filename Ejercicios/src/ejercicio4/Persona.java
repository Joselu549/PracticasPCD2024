package ejercicio4;

import java.util.Random;

import messagepassing.*;

class Persona extends Thread {
	private int tiempoPago = 0;
	private int index;
	private MailBox pedirCaja, pagarCaja, liberarCaja, mutex;

	public Persona(int index, MailBox pedirCaja, MailBox pagarCaja, MailBox liberarCaja, MailBox mutex) {
		this.index = index;
		this.pedirCaja = pedirCaja;
		this.pagarCaja = pagarCaja;
		this.liberarCaja = liberarCaja;
		this.mutex = mutex;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < 5; i++) {
				// 1. Realiza compra
				Random r = new Random();
				tiempoPago = 1 + r.nextInt(10);
				// 2. Solicita caja
				Object[] array = { index, tiempoPago };
				pedirCaja.send(array);
				char caja = (char) Main.respuestas[index].receive();
				// 3. Paga en caja
				Object[] array2 = { index, caja, tiempoPago };
				pagarCaja.send(array2);
				boolean pagado = (boolean) Main.respuestas[index].receive();
				Thread.sleep(tiempoPago * 1000);
				// 4. Libera caja
				if (pagado) {
					Object[] array3 = { index, caja };
					liberarCaja.send(array3);
				}
				// 5. Imprime en pantalla
				Object token = mutex.receive();
				System.out.println("--------------------------------------------------------------");
				System.out.println("Persona " + index + " ha usado la caja " + caja);
				System.out.println("Tiempo de pago = " + tiempoPago);
				System.out.println("Thread.sleep(" + tiempoPago + ");");
				System.out.println("Persona " + index + " ha liberado la caja " + caja);
				System.out.println("--------------------------------------------------------------");
				mutex.send(token);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
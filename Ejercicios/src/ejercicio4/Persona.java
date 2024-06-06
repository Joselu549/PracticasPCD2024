package ejercicio4;

import java.util.Random;

import messagepassing.*;

class Persona extends Thread {
	private int tiempoPago = 0;
	private MailBox cajaA;
	private MailBox cajaB;
	private CommunicationScheme cs;
	
	public Persona(MailBox cajaA, MailBox cajaB, CommunicationScheme cs) {
		this.cajaA = cajaA;
		this.cajaB = cajaB;
		this.cs = cs;
	}
	
	@Override
	public void run() {
		try {
			for (int i = 0; i < 5; i++) {
				// Realiza compra
				Random r = new Random();
				tiempoPago = 1 + r.nextInt(10);
				//Solicita caja
				Object token = cs.receive();
				if (tiempoPago >= 5) {
					// Paga caja A
					cajaA.send("token");
					System.out.println("Persona " + getId() + " ha usado la caja A");
				} else {
					// Paga caja B
					cajaB.send("token");
					System.out.println("Persona " + getId() + " ha usado la caja B");
				}
				System.out.println("Tiempo de pago = " + tiempoPago);
				cs.send(token);
				Thread.sleep(tiempoPago * 1000);
				token = cs.receive();
				System.out.println("Persona " + getId() + " liberando la caja");
				cs.send(token);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
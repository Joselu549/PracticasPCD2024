package ejercicio4;

import java.util.Random;

import messagepassing.*;

public class Cliente implements Runnable {
	private CommunicationScheme[] cajas = new MailBox[2];
	private int id;
	private String caja;
	
	public Cliente(CommunicationScheme[] cajas, int id) {
		this.cajas = cajas;
		this.id = id;
	}

	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			/* 1.- Realiza la compra */
			Random r = new Random();
			try {
				Thread.sleep(500 + r.nextInt(700 - 500));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int tiempopago = 1 + r.nextInt(10 - 1);
			/* 2.- Solicita entrar a la caja que le corresponde */
			if (tiempopago >= 5) {
				/* Seleccionar caja A */
				Object llaveA = cajas[0].receive();
				caja = "A";
				try {
					Thread.sleep(tiempopago * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				/* 4.- Libera caja */
				cajas[0].send(llaveA);
			} else {
				/* Seleccionar caja B */
				Object llaveB = cajas[1].receive();
				caja = "B";
				try {
					Thread.sleep(tiempopago * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				/* 4.- Libera caja */
				cajas[1].send(llaveB);
			}
			/* 5.- Imprime en pantalla operación */
			System.out.println("--------------------------------------------------------------");
			System.out.println("Persona " + id + " ha usado la caja " + caja);
			System.out.println("Tiempo de pago = " + tiempopago + "s");
			System.out.println("Persona " + id + " liberando la caja " + caja);
			System.out.println("--------------------------------------------------------------");
		}
	}
}

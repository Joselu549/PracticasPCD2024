package ejercicio4;

import messagepassing.MailBox;

public class Main {
	public static int MAX_CLIENTES = 30;
	public static void main(String[] args) {
		MailBox[] cajas = new MailBox[2];
		Thread[] clientes = new Thread[MAX_CLIENTES];
		cajas[0] = new MailBox();
		cajas[0].send("llaveA");
		cajas[1] = new MailBox();
		cajas[1].send("llaveB");
		for (int i = 0; i < MAX_CLIENTES; i++) {
			clientes[i] = new Thread(new Cliente(cajas, i));
			clientes[i].start();
		}
		
		try {
			for (int i = 0; i < MAX_CLIENTES; i++) {
				clientes[i].join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Termina el programa");
	}
}

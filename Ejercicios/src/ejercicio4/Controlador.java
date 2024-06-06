package ejercicio4;

import messagepassing.*;

public class Controlador extends Thread {
	private Selector s;
	private MailBox cajaA, cajaB;
	public Controlador(MailBox cajaA, MailBox cajaB) {
		this.cajaA = cajaA;
		this.cajaB = cajaB;
		this.s = new Selector();
		
	}
	
	@Override
	public void run() {
		s.addSelectable(cajaA, false);
		s.addSelectable(cajaB, false);
		while (true) {
			switch (s.selectOrBlock()) {
			case 1: cajaA.receive();
				System.out.println("Paga caja A");
				break;
			case 2: cajaB.receive();
				System.out.println("Paga caja B");
				break;
			}
		}
	}
}

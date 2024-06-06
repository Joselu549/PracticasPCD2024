package ejercicio4;

import messagepassing.*;

public class Main {

	public static void main(String[] args) {
		MailBox cajaA = new MailBox();
		MailBox cajaB = new MailBox();
		MailBox mutex = new MailBox();
		mutex.send("token");
		Controlador controlador = new Controlador(cajaA, cajaB);
		controlador.start();
		Thread[] array = new Thread[30];
		for (int i = 0; i < 30; i++) {
			array[i] = new Persona(cajaA, cajaB, mutex);
			array[i].start();
		}
	}

}

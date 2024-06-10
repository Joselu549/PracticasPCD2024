package ejercicio4;

import messagepassing.*;

public class Main {
	public static MailBox[] respuestas = new MailBox[30];
	public static void main(String[] args) {
		MailBox pedirCaja = new MailBox();
		MailBox pagarCaja = new MailBox();
		MailBox liberarCaja = new MailBox();
		MailBox mutex = new MailBox();
		mutex.send("token");
		Controlador controlador = new Controlador(pedirCaja, pagarCaja, liberarCaja);
		controlador.start();
		Thread[] array = new Thread[30];
		for (int i = 0; i < 30; i++) {
			respuestas[i] = new MailBox();
			array[i] = new Persona(i, pedirCaja, pagarCaja, liberarCaja, mutex);
			array[i].start();
		}
	}

}

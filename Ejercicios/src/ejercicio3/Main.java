package ejercicio3;

import java.util.Random;

public class Main {
	public static void main(String[] args) {
		MonitorBanco monitor = new MonitorBanco();
		Thread[] clientes = new Thread[50];

		for (Thread c : clientes) {
			Random r = new Random();
			c = new Cliente(300 + r.nextInt(501), 1000 + r.nextInt(300), monitor);
			c.start();
		}
	}
}

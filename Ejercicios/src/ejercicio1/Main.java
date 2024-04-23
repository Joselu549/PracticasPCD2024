package ejercicio1;

import java.util.concurrent.locks.ReentrantLock;

public class Main {
	
	public static void main(String[] args) {
		ArrayCompartido arrayCompartido = new ArrayCompartido();
		ReentrantLock pantalla = new ReentrantLock();
		Thread productor = new HiloProductor(arrayCompartido);
		Thread sumador = new HiloSumador(arrayCompartido, pantalla);
		productor.start();
		try {
			productor.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Thread[] arrayConsumidores = new Thread[10];
		for (int i = 0; i < 10; i++) {
			arrayConsumidores[i] = new HiloConsumidor(i, arrayCompartido, pantalla);
			arrayConsumidores[i].start();
		}
		try {
			for (Thread c : arrayConsumidores)
				c.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		sumador.start();
		try {
			sumador.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        System.out.println("\nTerminó el hilo Main\n");
	}
}
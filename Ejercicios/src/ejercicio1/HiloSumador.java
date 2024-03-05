package ejercicio1;

import java.util.concurrent.locks.ReentrantLock;

public class HiloSumador extends Thread {
	private ArrayCompartido arrayCompartido;
	private ReentrantLock pantalla;
	
	public HiloSumador(ArrayCompartido arrayCompartido, ReentrantLock pantalla) {
		this.arrayCompartido = arrayCompartido;
		this.pantalla = pantalla;
	}
	
	@Override
	public void run() {
		int res = arrayCompartido.sumarArrayRes();
		pantalla.lock();
		try {
			System.out.println("\nRESULTADO: " + res);
		} finally {
			pantalla.unlock();
		}
	}
}

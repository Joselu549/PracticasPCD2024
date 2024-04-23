package ejercicio1;

import java.util.concurrent.locks.ReentrantLock;

public class HiloConsumidor extends Thread {
	private int id;
	private ReentrantLock pantalla;
	ArrayCompartido arrayCompartido;

	public HiloConsumidor(int id, ArrayCompartido arrayCompartido, ReentrantLock pantalla) {
		this.arrayCompartido = arrayCompartido;
		this.id = id;
		this.pantalla = pantalla;
	}

	private int evaluarExpresion(int[] a) {
		int res = a[0];
		for (int i = 2; i < 11; i += 2) {
			switch (a[i - 1]) {
			case 1:
				res += a[i];
				break;
			case 2:
				res -= a[i];
				break;
			case 3:
				res *= a[i];
				break;
			case 4:
				res /= a[i];
				break;
			}
		}

		return res;
	}

	@Override
	public void run() {
		int[] aux = arrayCompartido.getArrayID(id);
		int res = evaluarExpresion(aux);
		arrayCompartido.llenarArrayRes(id, res);

		/* IMPRIMIR POR PANTALLA */
		pantalla.lock();
		try {
			System.out.printf("\nHilo " + id + ": [");
			for (int i = 0; i < 11; i++) {
				if ((i % 11) % 2 == 0) {
					System.out.printf(aux[i] + "");
				} else {
					switch (aux[i]) {
					case 1:
						System.out.printf(" + ");
						break;
					case 2:
						System.out.printf(" - ");
						break;
					case 3:
						System.out.printf(" * ");
						break;
					case 4:
						System.out.printf(" / ");
						break;
					}
				}
			}
			System.out.printf("] = " + res + "\n");
		} finally {
			pantalla.unlock();
		}
	}
}
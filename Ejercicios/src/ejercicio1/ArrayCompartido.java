package ejercicio1;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayCompartido {
	private int[] arrayCompartido = new int[110];
	private double[] arrayRes = new double[10];
	private ReentrantLock cerrojoArray = new ReentrantLock();
	private ReentrantLock cerrojoRes = new ReentrantLock();

	public int[] getArrayID(int id) {
		int[] aux = new int[11];
		int idx = id * 11;
		int j = 0;
		for (int i = idx; i < idx + 11; i++) {
			aux[j] = arrayCompartido[i];
			j++;
		}
		return aux;
	}
	
	public void llenarArray() {
		cerrojoArray.lock();
		try {
			for (int i = 0; i < 110; i++) {
				Random r = new Random();
				if ((i % 11) % 2 == 0) {
					arrayCompartido[i] = r.nextInt(100) + 1;
				} else {
					arrayCompartido[i] = r.nextInt(4) + 1;
				}
			}
		} finally {
			cerrojoArray.unlock();
		}
	}
	
	public void llenarArrayRes(int id, int val) {
		cerrojoRes.lock();
		try {
			arrayRes[id] = val;
		} finally {
			cerrojoRes.unlock();
		}
	}

	public int sumarArrayRes() {
		cerrojoRes.lock();
		try {
			int res = 0;
			for (int i = 0; i < 10; i++) {
				res += arrayRes[i];
			}
			return res;
		} finally {
			cerrojoRes.unlock();
		}
	}
}

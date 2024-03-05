package ejercicio1;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayCompartido {
	private int[] arrayCompartido = new int[110];
	private double[] arrayRes = new double[10];
	private ReentrantLock cerrojoArray = new ReentrantLock();
	private ReentrantLock cerrojoRes = new ReentrantLock();
	private Condition condicionArray = cerrojoArray.newCondition();
	private Condition condicionRes = cerrojoRes.newCondition();
	private int estaLlenoRes = 0;
	private boolean estaLleno = false;

	public int[]  getArrayID(int id) {
		int[] aux = new int[11];
		int idx = id * 11;
		int j = 0;
		
		cerrojoArray.lock();
		try {
			
			while (!estaLleno) {
				try {
					condicionArray.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			for (int i = idx; i < idx + 11; i++) {
				aux[j] = arrayCompartido[i];
				j++;
			}
			return aux;
		} finally {
			cerrojoArray.unlock();
		}
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
			estaLleno = true;
			condicionArray.signal();
		} finally {
			cerrojoArray.unlock();
		}
	}
	
	public void llenarArrayRes(int id, int val) {
		cerrojoRes.lock();
		try {
			arrayRes[id] = val;
			estaLlenoRes++;
			if (estaLlenoRes == 10)
				condicionRes.signal();
		} finally {
			cerrojoRes.unlock();
		}
	}

	public int sumarArrayRes() {
		cerrojoRes.lock();
		
		try {
			while (estaLlenoRes != 10) {
				try {
					condicionRes.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
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

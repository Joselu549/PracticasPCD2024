package ejercicio3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorBanco {
	private int maquinasenuso;
	private int mesasenuso;
	private int[] tiempomesas;
	private ReentrantLock l;
	private Condition colamaquinas;
	private Condition colamesas;
	private static int MAX_MAQUINAS = 3;
	private static int MAX_MESAS = 4;

	public MonitorBanco() {
		this.maquinasenuso = 0;
		this.mesasenuso = 0;
		this.tiempomesas = new int[MAX_MESAS];
		this.l = new ReentrantLock();
		this.colamaquinas = l.newCondition();
		this.colamesas = l.newCondition();
	}

	public void cogerMaquina() throws InterruptedException {
		l.lock();
		try {
			while (maquinasenuso >= MAX_MAQUINAS) {
				colamaquinas.await();
			}
			// Coge máquina
			maquinasenuso++;
		} finally {
			l.unlock();
		}
	}

	public void soltarMaquina() throws InterruptedException {
		l.lock();
		try {
			// Suelto máquina
			maquinasenuso--;
			colamaquinas.signal();
		} finally {
			l.unlock();
		}
	}

	public int cogerMesa(int x, int y) throws InterruptedException {
		int mesa;
		l.lock();
		try {
			while (mesasenuso >= MAX_MESAS) {
				colamesas.await();
			}
			// Coge mesa
			mesa = selectMesa(x, y);
			tiempomesas[mesa] += y;
			mesasenuso++;
		} finally {
			l.unlock();
		}
		return mesa;
	}

	public void soltarMesa(int mesa, int y) {
		l.lock();
		try {
			// Suelto mesa
			mesasenuso--;
			tiempomesas[mesa] -= y;
			colamesas.signal();
		} finally {
			l.unlock();
		}
	}

	public int selectMesa(int x, int y) {
		l.lock();
		int mesaselec;
		try {
			mesaselec = 0;
			int tiempomin = tiempomesas[0];
			for (int i = 1; i < tiempomesas.length; i++) {
				if (tiempomesas[i] < tiempomin) {
					tiempomin = tiempomesas[i];
					mesaselec = i;
				}
			}
		} finally {
			l.unlock();
		}
		
		return mesaselec;
	}

	public void print(int mesaselec, int x, int y) {
		System.out.println("--------------------------------------------------------------");
		System.out.println("Tiempo en solicitar el servicio: " + x);
		System.out.println("Será atendido en la mesa: " + mesaselec);
		System.out.println("Tiempo en la mesa: " + y);
		System.out.println("Tiempo de espera en la mesa1 = " + tiempomesas[0] + ", mesa2 = " + tiempomesas[1]
				+ ", mesa3 = " + tiempomesas[2] + ", mesa4 = " + tiempomesas[3]);
		System.out.println("--------------------------------------------------------------");
		for (int i = 0; i < tiempomesas.length; i++) {
			System.out.println("Mesa " + i + ": " + tiempomesas[i]);
		}
	}
}

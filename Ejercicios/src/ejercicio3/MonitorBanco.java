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
		this.tiempomesas = new int[4];
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

	public void cogerMesa(int x, int y) throws InterruptedException {
		l.lock();
		try {
			while (mesasenuso >= MAX_MESAS) {
				colamesas.await();
			}
			// Coge mesa
			int mesa = selectMesa(x, y);
			tiempomesas[mesa] += y;
			mesasenuso++;
		} finally {
			l.unlock();
		}
	}

	public void soltarMesa() {
		l.lock();
		try {
			// Suelto mesa
			mesasenuso--;
			colamesas.signal();
		} finally {
			l.unlock();
		}
	}

	private int selectMesa(int x, int y) {
		int mesaselec = 0;
		int tiempomin = tiempomesas[0];
		if (tiempomesas[1] < tiempomin) {
			mesaselec = 1;
			tiempomin = tiempomesas[1];
		}
		if (tiempomesas[2] < tiempomin) {
			mesaselec = 2;
			tiempomin = tiempomesas[2];
		}
		if (tiempomesas[3] < tiempomin) {
			mesaselec = 3;
			tiempomin = tiempomesas[3];
		}
		System.out.println("--------------------------------------------------------------");
		System.out.println("Tiempo en solicitar el servicio: " + x);
		System.out.println("Será atendido en la mesa: " + mesaselec);
		System.out.println("Tiempo en la mesa: " + y);
		System.out.println("Tiempo de espera en la mesa1 = " + tiempomesas[0] + ", mesa2 = " + tiempomesas[1]
				+ ", mesa3 = " + tiempomesas[2] + ", mesa4 = " + tiempomesas[3]);
		System.out.println("--------------------------------------------------------------");
		return mesaselec;
	}
}

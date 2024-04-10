package ejercicio3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorBanco {
	private static int MAX_MAQUINAS = 3;
	private static int MAX_MESAS = 4;
	private int maquinasenuso;
	private int mesasenuso;
	private int[] tiempomesas;
	private ReentrantLock l;
	private Condition colamaquinas;
	private Condition colamesas;

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

	public int cogerMesa(int mesa, int y) throws InterruptedException {
		l.lock();
		try {
			while(mesasenuso >= MAX_MESAS) {
				colamesas.await();
			}
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
			System.out.println("Ha soltado mesa " + mesa + "?");
		} finally {
			l.unlock();
		}
	}

	public int selectMesa(int y) {
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
			tiempomesas[mesaselec] += y;
		} finally {
			l.unlock();
		}
		
		return mesaselec;
	}
	
	public void print(int mesaselec, int x, int y) {
		l.lock();
		try {
			System.out.println("--------------------------------------------------------------");
			System.out.println("Tiempo en solicitar el servicio: " + x);
			System.out.println("Será atendido en la mesa: " + mesaselec);
			System.out.println("Tiempo en la mesa: " + y);
			System.out.println("Tiempo de espera en la mesa0 = " + tiempomesas[0] + ", mesa1 = " + tiempomesas[1]
					+ ", mesa2 = " + tiempomesas[2] + ", mesa3 = " + tiempomesas[3]);
			System.out.println("--------------------------------------------------------------");
		} finally {
			l.unlock();
		}
	}
}

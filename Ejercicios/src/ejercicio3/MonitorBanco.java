package ejercicio3;

public class MonitorBanco {
	private int maquinasenuso;
	private int mesasenuso;
	private static int MAX_MAQUINAS = 3;
	private static int MAX_MESAS = 4;
	
	public MonitorBanco() {
		this.maquinasenuso = 0;
		this.mesasenuso = 0;
	}
	
	public synchronized void cogerMaquina(String id) throws InterruptedException {
		while (maquinasenuso >= MAX_MAQUINAS) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		maquinasenuso++;
	}

	public synchronized void soltarMaquina(String id) {
		maquinasenuso--;
		notifyAll();
	}
	
	public synchronized void cogerMesa(int x, int y) {
		while (mesasenuso >= MAX_MESAS) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("--------------------------------------------------------------");
		System.out.println("Tiempo en solicitar el servicio: " + x);
		System.out.println("Será atendido en la mesa: ");
		System.out.println("Tiempo en la mesa: " + y);
		System.out.println("Tiempo de espera en la mesa1 = , mesa2 = , mesa3 = , mesa4 = ");
		System.out.println("--------------------------------------------------------------");
		mesasenuso++;
	}
	
	public synchronized void soltarMesa(String id) {
		mesasenuso--;
		notifyAll();
	}
}

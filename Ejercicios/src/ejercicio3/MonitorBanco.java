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
		System.out.println("Coge máquina hilo " + id);
		maquinasenuso++;
	}

	public synchronized void soltarMaquina(String id) {
		System.out.println("Suelta máquina hilo " + id);
		maquinasenuso--;
		notifyAll();
	}
	
	public synchronized void cogerMesa(String id) {
		while (mesasenuso >= MAX_MESAS) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Coge mesa hilo " + id);
		mesasenuso++;
	}
	
	public synchronized void soltarMesa(String id) {
		System.out.println("Suelta mesa hilo " + id);
		mesasenuso--;
		notify();
	}
}

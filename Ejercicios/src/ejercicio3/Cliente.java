package ejercicio3;

public class Cliente extends Thread {
	MonitorBanco monitor;
	private int x;
	private int y;
	public Cliente(int x, int y, MonitorBanco monitor) {
		this.monitor = monitor;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void run() {
		try {
			monitor.cogerMaquina(getName() + "Tiempo máquina: " + x);
			Thread.sleep(x);
			monitor.soltarMaquina(getName());
			monitor.cogerMesa(getName()+ "Tiempo mesa: " + y);
			Thread.sleep(y);
			monitor.soltarMesa(getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

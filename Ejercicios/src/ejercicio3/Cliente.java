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
			monitor.cogerMaquina();
			Thread.sleep(x);
			monitor.soltarMaquina();
			int mesa = monitor.cogerMesa(x, y);
			Thread.sleep(y);
			monitor.soltarMesa(mesa);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

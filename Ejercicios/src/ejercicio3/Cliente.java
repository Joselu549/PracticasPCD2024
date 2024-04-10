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
			int mesa = monitor.selectMesa(y);
			monitor.cogerMaquina();
			Thread.sleep(x);
			monitor.soltarMaquina();
			monitor.print(mesa, x, y);
			monitor.cogerMesa(mesa, y);
			Thread.sleep(y);
			monitor.soltarMesa(mesa, y);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

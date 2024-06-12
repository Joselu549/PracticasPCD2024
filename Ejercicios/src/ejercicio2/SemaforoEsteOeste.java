package ejercicio2;

public class SemaforoEsteOeste extends Thread {

	public void run() {
		while (true) {
			try {
				Main.mutex.acquire();
				if (Main.direccionPasando != 1 || Main.ncochesNS > 0) {
					Main.mutex.release();
					Main.esteOeste.acquire();
				}
				Main.direccionPasando = 1;
				System.out.println("Toca paso de semáforo Este - Oeste");
				if (Main.ncochesEOe > 0)
					Main.cochesEO.release();
				else
					Main.mutex.release();

				Thread.sleep(5000);

				Main.mutex.acquire();
				Main.peatones.release();
			} catch (InterruptedException e) {
				System.err.println("Error en el Semáforo Este - Oeste");
			}
		}
	}
}

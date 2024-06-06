package ejercicio2;

public class SemaforoEsteOeste extends Thread {

	public void run() {
		while (true) {
			try {
				Main.mutex.acquire();
				if (Main.direccionPasando == 0 || Main.direccionPasando == 2) {
					Main.mutex.release();
					Main.esteOeste.acquire();
				}
				System.out.println("Toca paso de semáforo Este - Oeste");
				Main.direccionPasando = 1;
				if (Main.ncochesEOe > 0)
					Main.cochesEO.release();
				Main.mutex.release();

				Thread.sleep(5000);

				Main.mutex.acquire();
				Main.direccionPasando = 2;
				Main.peatones.release();
			} catch (InterruptedException e) {
				System.err.println("Error en el Semáforo Este - Oeste");
				e.printStackTrace();
			}
		}
	}
}

package ejercicio2;

public class SemaforoPeatones extends Thread {

	public void run() {
		while (true) {
			try {
				Main.mutex.acquire();
				if (Main.direccionPasando != 2 || Main.ncochesEO > 0) {
					Main.mutex.release();
					Main.peatones.acquire();
				}
				Main.direccionPasando = 2;
				System.out.println("Toca paso de semáforo Peatones");
				if (Main.numeroPEe > 0)
					Main.peatonesPE.release();
				else
					Main.mutex.release();

				Thread.sleep(5000);

				Main.mutex.acquire();
				Main.norteSur.release();
			} catch (InterruptedException e) {
				System.err.println("Error en el Semáforo Peatones");
			}
		}
	}
}

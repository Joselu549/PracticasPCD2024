package ejercicio2;

public class SemaforoPeatones extends Thread {

	public void run() {
		while (true) {
			try {
				Main.mutex.acquire();
				if (Main.direccionPasando == 0 || Main.direccionPasando == 1) {
					Main.mutex.release();
					Main.peatones.acquire();
				}
				System.out.println("Toca paso de semáforo Peatones");
				Main.direccionPasando = 2;
				if (Main.numeroPEe > 0)
					Main.peatonesPE.release();
				Main.mutex.release();

				Thread.sleep(5000);

				Main.mutex.acquire();
				Main.direccionPasando = 0;
				Main.norteSur.release();
			} catch (InterruptedException e) {
				System.err.println("Error en el Semáforo Peatones");
				e.printStackTrace();
			}
		}
	}
}

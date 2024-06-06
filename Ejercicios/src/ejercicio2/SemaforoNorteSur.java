package ejercicio2;

public class SemaforoNorteSur extends Thread {

	public void run() {
		while (true) {
			try {
				Main.mutex.acquire();
				if (Main.direccionPasando == 1 || Main.direccionPasando == 2) {
					Main.mutex.release();
					Main.norteSur.acquire();
				}
				System.out.println("Toca paso de semáforo Norte - Sur");
				Main.direccionPasando = 0;
				if (Main.ncochesNSe > 0)
					Main.cochesNS.release();
				Main.mutex.release();

				Thread.sleep(5000);

				Main.mutex.acquire();
				Main.direccionPasando = 1;
				Main.esteOeste.release();
			} catch (InterruptedException e) {
				System.err.println("Error en el Semáforo Norte - Sur");
				e.printStackTrace();
			}
		}
	}
}
package ejercicio2;

public class SemaforoNorteSur extends Thread {

	public void run() {
		while (true) {
			try {
				Main.mutex.acquire();
				if (Main.direccionPasando != 0 || Main.numeroPE > 0) {
					Main.mutex.release();
					Main.norteSur.acquire();
				}
				Main.direccionPasando = 0;
				System.out.println("Toca paso de semáforo Norte - Sur");
				if (Main.ncochesNSe > 0)
					Main.cochesNS.release();
				else
					Main.mutex.release();

				Thread.sleep(5000);

				Main.mutex.acquire();
				Main.esteOeste.release();
			} catch (InterruptedException e) {
				System.err.println("Error en el Semáforo Norte - Sur");
			}
		}
	}
}
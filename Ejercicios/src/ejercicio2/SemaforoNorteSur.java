package ejercicio2;

public class SemaforoNorteSur extends Thread {

	public void run() {
		while (true) {
			try {
				Main.mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (Main.ocupado || Main.direccionPasando == 1 || Main.direccionPasando == 2) {
				Main.mutex.release();
				try {
					Main.norteSur.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Main.ocupado = true;
			Main.direccionPasando = 0;
			Main.mutex.release();
			System.out.println("Toca paso de semáforo Norte - Sur");
			if (Main.ncochesNSe > 0)
				Main.cochesNS.release();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				Main.mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Main.ocupado = false;
			Main.direccionPasando = 1;
			Main.esteOeste.release();
		}
	}
}
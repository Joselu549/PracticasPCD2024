package ejercicio2;

public class SemaforoEsteOeste extends Thread {

	public void run() {
		while (true) {
			try {
				Main.mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (Main.ocupado || Main.direccionPasando == 0 || Main.direccionPasando == 2) {
				Main.mutex.release();
				try {
					Main.esteOeste.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Main.ocupado = true;
			Main.direccionPasando = 1;
			Main.mutex.release();
			System.out.println("Toca paso de semáforo Este - Oeste");
			if (Main.ncochesEOe > 0)
				Main.cochesEO.release();
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
			Main.direccionPasando = 2;
			Main.peatones.release();
		}
	}
}

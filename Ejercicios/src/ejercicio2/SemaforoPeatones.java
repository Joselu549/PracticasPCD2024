package ejercicio2;

public class SemaforoPeatones extends Thread {

	public void run() {
		while (true) {
			try {
				Main.mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (Main.ocupado || Main.direccionPasando == 0 || Main.direccionPasando == 1) {
				Main.mutex.release();
				try {
					Main.peatones.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Main.ocupado = true;
			Main.direccionPasando = 2;
			Main.mutex.release();
			System.out.println("Toca paso de semáforo Peatones");
			if (Main.numeroPEe > 0)
				Main.peatonesPE.release();
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
			Main.direccionPasando = 0;
			Main.norteSur.release();
		}
	}
}

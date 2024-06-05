package ejercicio2;

public class Coche extends Thread {

	public void run() {
		while (true) {
			try {
				Main.mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (Main.ncochesNS == 4 || Main.direccionPasando == 1 || Main.direccionPasando == 2) {
				Main.ncochesNSe++;
				Main.mutex.release();
				try {
					Main.cochesNS.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Main.ncochesNSe--;
			}
			Main.ncochesNS++;
			if (Main.ncochesNSe > 0 && Main.ncochesNS < 4)
				Main.cochesNS.release();
			else
				Main.mutex.release();
			System.out.println("Pasando coche NS id: " + getId());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				Main.mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Main.ncochesNS--;
			if (Main.direccionPasando == 0 && Main.ncochesNSe > 0) {
				Main.cochesNS.release();
			} else {
				Main.mutex.release();
			}
			
			try {
				Thread.sleep(7000);
			} catch (InterruptedException e) {	// Espera para cruzar en la otra dirección
				e.printStackTrace();
			}
			
			try {
				Main.mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (Main.ncochesEO == 4 || Main.direccionPasando == 0 || Main.direccionPasando == 2) {
				Main.ncochesEOe++;
				Main.mutex.release();
				try {
					Main.cochesEO.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Main.ncochesEOe--;
			}
			Main.ncochesEO++;
			if (Main.ncochesEOe > 0 && Main.ncochesEO < 4)
				Main.cochesEO.release();
			else
				Main.mutex.release();
			System.out.println("Pasando coche EO id: " + getId());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				Main.mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Main.ncochesEO--;
			if (Main.direccionPasando == 1 && Main.ncochesEOe > 0) {
				Main.cochesEO.release();
			} else {
				Main.mutex.release();
			}
			try {
				Thread.sleep(7000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

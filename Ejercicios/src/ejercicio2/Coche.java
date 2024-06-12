package ejercicio2;

public class Coche extends Thread {

	public void run() {
		while (true) {
			try {
				Main.mutex.acquire();
				if (!(Main.ncochesNS < 4 && Main.direccionPasando == 0)) { // Main.ncochesNS >= 4 || Main.direccionPasando == 1 || Main.direccionPasando == 2
					Main.ncochesNSe++;
					Main.mutex.release();
					Main.cochesNS.acquire();
					Main.ncochesNSe--;
				}
				Main.ncochesNS++;
				System.out.println("Pasando coche NS id: " + getId());
				if (Main.ncochesNSe > 0 && Main.ncochesNS < 4)
					Main.cochesNS.release();	// Despertar encadenado
				else
					Main.mutex.release();

				Thread.sleep(500);	// Pasa por el cruce

				Main.mutex.acquire();
				Main.ncochesNS--;
				if (Main.direccionPasando == 0 && Main.ncochesNSe > 0)
					Main.cochesNS.release();	// Despertar encadenado
				else
					Main.mutex.release();

				Thread.sleep(7000);	// Espera para intentar cruzar

				Main.mutex.acquire();
				if (!(Main.ncochesEO < 4 && Main.direccionPasando == 1)) { // Main.ncochesEO >= 4 || Main.direccionPasando == 0 || Main.direccionPasando == 2
					Main.ncochesEOe++;
					Main.mutex.release();
					Main.cochesEO.acquire();
					Main.ncochesEOe--;
				}
				Main.ncochesEO++;
				System.out.println("Pasando coche EO id: " + getId());
				if (Main.ncochesEOe > 0 && Main.ncochesEO < 4)
					Main.cochesEO.release();	// Despertar encadenado
				else
					Main.mutex.release();

				Thread.sleep(500);

				Main.mutex.acquire();
				Main.ncochesEO--;
				if (Main.direccionPasando == 1 && Main.ncochesEOe > 0)
					Main.cochesEO.release();	// Despertar encadenado
				else
					Main.mutex.release();
				
				Thread.sleep(7000);	// Espera para intentar cruzar
				
			} catch (InterruptedException e) {
				System.err.println("Error en el Coche con id: " + getId());
			}
		}
	}
}

package ejercicio2;

public class Coche extends Thread {

	/**
	 * Método que ejecuta el proceso Coche. Cada coche empieza a conducir en la
	 * dirección Norte - Sur y pasados 7 segundos intenta cruzar en la dirección
	 * Este - Oeste.
	 */
	public void run() {
		while (true) {
			try {
				/** Protocolo de Entrada */
				Main.mutex.acquire();
				if (!(Main.ncochesNS < 4 && Main.direccionPasando == 0)) {
					Main.ncochesNSe++;
					Main.mutex.release();
					Main.cochesNS.acquire();
					Main.ncochesNSe--;
				}
				Main.ncochesNS++;
				System.out.println("Pasando coche NS id: " + getId());
				/** Despertar encadenado para que pasen todos los coches de esta dirección */
				if (Main.ncochesNSe > 0 && Main.ncochesNS < 4)
					Main.cochesNS.release();
				else
					Main.mutex.release();

				/** Sección Crítica */
				Thread.sleep(500); // Pasa por el cruce

				/** Protocolo de Salida */
				Main.mutex.acquire();
				Main.ncochesNS--;
				/** Despertar encadenado para que pasen todos los coches de esta dirección */
				if (Main.direccionPasando == 0 && Main.ncochesNSe > 0)
					Main.cochesNS.release();
				else
					Main.mutex.release();

				/** Espera 7 segundos para intentar cruzar en la otra dirección */
				Thread.sleep(7000);

				/** Protocolo de Entrada */
				Main.mutex.acquire();
				if (!(Main.ncochesEO < 4 && Main.direccionPasando == 1)) {
					Main.ncochesEOe++;
					Main.mutex.release();
					Main.cochesEO.acquire();
					Main.ncochesEOe--;
				}
				Main.ncochesEO++;
				System.out.println("Pasando coche EO id: " + getId());
				/** Despertar encadenado para que pasen todos los coches de esta dirección */
				if (Main.ncochesEOe > 0 && Main.ncochesEO < 4)
					Main.cochesEO.release();
				else
					Main.mutex.release();

				/** Sección Crítica */
				Thread.sleep(500);

				/** Protocolo de Salida */
				Main.mutex.acquire();
				Main.ncochesEO--;
				/** Despertar encadenado para que pasen todos los coches de esta dirección */
				if (Main.direccionPasando == 1 && Main.ncochesEOe > 0)
					Main.cochesEO.release();
				else
					Main.mutex.release();

				/** Espera 7 segundos para intentar cruzar en la otra dirección */
				Thread.sleep(7000); // Espera para intentar cruzar

			} catch (InterruptedException e) {
				System.err.println("Error en el Coche con id: " + getId());
			}
		}
	}
}

package ejercicio2;

public class Peaton extends Thread {

	/**
	 * Método que ejecuta el proceso Peaton. Cada peaton cruza por el paso de
	 * peatones y, tras 8 segundos, vuelve a cruzar.
	 */
	public void run() {
		while (true) {
			try {
				/** Protocolo de Entrada */
				Main.mutex.acquire();
				if (!(Main.numeroPE < 10 && Main.direccionPasando == 2)) {
					Main.numeroPEe++;
					Main.mutex.release();
					Main.peatonesPE.acquire();
					Main.numeroPEe--;
				}
				Main.numeroPE++;
				System.out.println("Pasando peatón id: " + getId());
				/** Despertar encadenado para que pasen todos los coches de esta dirección */
				if (Main.numeroPEe > 0 && Main.numeroPE < 10)
					Main.peatonesPE.release();
				else
					Main.mutex.release();

				/** Sección Crítica */
				Thread.sleep(3000);

				/** Protocolo de Salida */
				Main.mutex.acquire();
				Main.numeroPE--;
				/** Despertar encadenado para que pasen todos los coches de esta dirección */
				if (Main.direccionPasando == 2 && Main.numeroPEe > 0)
					Main.peatonesPE.release();
				else
					Main.mutex.release();

				/** Espera 8 segundos para intentar cruzar otra vez */
				Thread.sleep(8000);

			} catch (InterruptedException e) {
				System.err.println("Error en el Peatón con id: " + getId());
			}
		}
	}
}

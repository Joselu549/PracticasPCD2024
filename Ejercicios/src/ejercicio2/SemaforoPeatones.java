package ejercicio2;

public class SemaforoPeatones extends Thread {

	/**
	 * Método que ejecuta el proceso SemaforoPeatones. Los procesos de los semáforos
	 * se van cambiando la exclusión mutua entre ellos para que coja la exclusión
	 * mutua cada 5 segundos
	 */
	public void run() {
		while (true) {
			try {
				/** Protocolo de Entrada */
				Main.mutex.acquire();
				if (Main.direccionPasando != 2 || Main.ncochesEO > 0) {
					Main.mutex.release();
					Main.peatones.acquire();
				}
				Main.direccionPasando = 2;
				System.out.println("Toca paso de semáforo Peatones");
				/** Despertar encadenado para evisar de que ha cambiado la dirección */
				if (Main.numeroPEe > 0)
					Main.peatonesPE.release();
				else
					Main.mutex.release();

				/** Sección Crítica */
				Thread.sleep(5000);

				/** Protocolo de Salida */
				Main.mutex.acquire();
				Main.norteSur.release();
			} catch (InterruptedException e) {
				System.err.println("Error en el Semáforo Peatones");
			}
		}
	}
}

package ejercicio2;

public class SemaforoEsteOeste extends Thread {

	/**
	 * Método que ejecuta el proceso SemaforoEsteOeste. Los procesos de los
	 * semáforos se van cambiando la exclusión mutua entre ellos para que coja la
	 * exclusión mutua cada 5 segundos
	 */
	public void run() {
		while (true) {
			try {
				/** Protocolo de Entrada */
				Main.mutex.acquire();
				if (Main.direccionPasando != 1 || Main.ncochesNS > 0) {
					Main.mutex.release();
					Main.esteOeste.acquire();
				}
				Main.direccionPasando = 1;
				System.out.println("Toca paso de semáforo Este - Oeste");
				/** Despertar encadenado para evisar de que ha cambiado la dirección */
				if (Main.ncochesEOe > 0)
					Main.cochesEO.release();
				else
					Main.mutex.release();

				/** Sección Crítica */
				Thread.sleep(5000);

				/** Protocolo de Salida */
				Main.mutex.acquire();
				Main.peatones.release();
			} catch (InterruptedException e) {
				System.err.println("Error en el Semáforo Este - Oeste");
			}
		}
	}
}

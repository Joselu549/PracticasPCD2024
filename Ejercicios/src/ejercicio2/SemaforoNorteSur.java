package ejercicio2;

public class SemaforoNorteSur extends Thread {

	/**
	 * Método que ejecuta el proceso SemaforoNorteSur. Los procesos de los semáforos
	 * se van cambiando la exclusión mutua entre ellos para que coja la exclusión
	 * mutua cada 5 segundos
	 */
	public void run() {
		while (true) {
			try {
				/** Protocolo de Entrada */
				Main.mutex.acquire();
				if (Main.direccionPasando != 0 || Main.numeroPE > 0) {
					Main.mutex.release();
					Main.norteSur.acquire();
				}
				Main.direccionPasando = 0;
				System.out.println("Toca paso de semáforo Norte - Sur");
				/** Despertar encadenado para evisar de que ha cambiado la dirección */
				if (Main.ncochesNSe > 0)
					Main.cochesNS.release();
				else
					Main.mutex.release();

				/** Sección Crítica */
				Thread.sleep(5000);

				/** Protocolo de Salida */
				Main.mutex.acquire();
				Main.esteOeste.release();
			} catch (InterruptedException e) {
				System.err.println("Error en el Semáforo Norte - Sur");
			}
		}
	}
}
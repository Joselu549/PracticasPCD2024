package ejercicio2;

public class Peaton extends Thread {

	public void run() {
		while (true) {
			try {
				Main.mutex.acquire();
				if (Main.numeroPE >= 10 || Main.direccionPasando == 0 || Main.direccionPasando == 1) {
					Main.numeroPEe++;
					Main.mutex.release();
					Main.peatonesPE.acquire();
					Main.numeroPEe--;
				}
				Main.numeroPE++;
				System.out.println("Pasando peatón id: " + getId());
				if (Main.numeroPEe > 0 && Main.numeroPE < 10)
					Main.peatonesPE.release();
				else
					Main.mutex.release();

				Thread.sleep(3000);

				Main.mutex.acquire();
				Main.numeroPE--;
				if (Main.direccionPasando == 2 && Main.numeroPEe > 0)
					Main.peatonesPE.release();
				else
					Main.mutex.release();
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				System.err.println("Error en el Peatón con id: " + getId());
				e.printStackTrace();
			}
		}
	}
}

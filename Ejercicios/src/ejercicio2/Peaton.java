package ejercicio2;

public class Peaton extends Thread {
	
	public void run() {
		while (true) {
			try {
				Main.mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (Main.numeroPE == 10 || Main.direccionPasando == 0 || Main.direccionPasando == 1) {
				Main.numeroPEe++;
				Main.mutex.release();
				try {
					Main.peatonesPE.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Main.numeroPEe--;
			}
			Main.numeroPE++;
			if (Main.numeroPEe > 0 && Main.numeroPE < 10)
				Main.peatonesPE.release();
			else
				Main.mutex.release();
			System.out.println("Pasando peatón id: " + getId());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				Main.mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Main.numeroPE--;
			if (Main.direccionPasando == 2 && Main.numeroPEe > 0) {
				Main.peatonesPE.release();
			} else {
				Main.mutex.release();
			}
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

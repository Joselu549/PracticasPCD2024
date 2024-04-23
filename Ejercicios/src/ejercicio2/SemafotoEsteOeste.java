package ejercicio2;

public class SemafotoEsteOeste extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                Main.mutex2.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Main.pantalla.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Toca paso de semáforo Este - Oeste");
            Main.pantalla.release();

            Main.direste.release(4);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Main.direste.acquire(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            Main.diroeste.release(4);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Main.diroeste.acquire(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Main.mutex3.release();
        }
    }
}

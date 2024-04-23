package ejercicio2;

public class SemaforoPeatones extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                Main.mutex3.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Main.pantalla.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Toca paso de semáforo Peatones");
            Main.pantalla.release();

            Main.dirpeatones1.release(10);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Main.dirpeatones1.acquire(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            Main.dirpeatones2.release(10);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Main.dirpeatones2.acquire(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Main.mutex1.release();
        }
    }
}

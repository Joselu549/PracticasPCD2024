package ejercicio2;

public class SemaforoNorteSur extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                Main.mutex1.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Main.pantalla.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Toca paso de semáforo Norte - Sur");
            Main.pantalla.release();
            
            Main.dirnorte.release(4);
           
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Main.dirnorte.acquire(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Main.dirsur.release(4);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Main.dirsur.acquire(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Main.mutex2.release();
        }
    }
}

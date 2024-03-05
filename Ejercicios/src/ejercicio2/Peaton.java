package ejercicio2;

import java.util.Random;

public class Peaton extends Thread {
    //false = direccion1
    //true = direccion2
    private boolean direccion;

    public Peaton() {
        Random r = new Random();
        this.direccion = r.nextBoolean();
    }

    private void imprimirInfo() {
        try {
            Main.pantalla.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!direccion)
            System.out.println(Thread.currentThread().getName() + " Pasando peatón dirección dir1");
        else
            System.out.println(Thread.currentThread().getName() + " Pasando peatón dirección dir2");

        Main.pantalla.release();
    }

    @Override
    public void run() {
        if (!direccion) {
            try {
                Main.dirpeatones1.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            imprimirInfo();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Main.dirpeatones1.release();
            this.direccion = true;
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Main.dirpeatones2.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            imprimirInfo();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Main.dirpeatones2.release();
        } else {
            try {
                Main.dirpeatones2.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            imprimirInfo();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Main.dirpeatones2.release();
            this.direccion = false;
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Main.dirpeatones1.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            imprimirInfo();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Main.dirpeatones1.release();
        }
    }
}

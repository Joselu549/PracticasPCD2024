package ejercicio2;

import java.util.Random;

public class Coche extends Thread {
    
    //false = norte-sur
    //true = este-oeste
    private boolean direccion;

    
    //false = primera dirección
    //true = segunda dirección
    private boolean empiezaen;

    public Coche() {
        Random r = new Random();
        this.direccion = r.nextBoolean();
        this.empiezaen = r.nextBoolean();
    }

    private void imprimirInfo() {
        try {
            Main.pantalla.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf(Thread.currentThread().getName() + " Pasa coche en ");
        if (!direccion) {
            System.out.printf("norte-sur y empiezo en ");
            if (!empiezaen)
                System.out.println("norte");
            else
                System.out.println("sur");
        } else {
            System.out.printf("este-oeste y empiezo en ");
            if (!empiezaen)
                System.out.println("este");
            else
                System.out.println("oeste");
        }
        Main.pantalla.release();
    }

    @Override
    public void run() {
        if (!direccion) {
            if (!empiezaen) {
                try {
                    Main.dirnorte.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                imprimirInfo();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Main.dirnorte.release();
                this.empiezaen = true;
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Main.dirsur.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                imprimirInfo();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Main.dirsur.release();
            } else {
                try {
                    Main.dirsur.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                imprimirInfo();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Main.dirsur.release();
                this.empiezaen = false;
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Main.dirnorte.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                imprimirInfo();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Main.dirnorte.release();
            }
        } else {
            if (!empiezaen) {
                try {
                    Main.direste.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                imprimirInfo();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Main.direste.release();
                this.empiezaen = true;
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Main.diroeste.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                imprimirInfo();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Main.diroeste.release();
            } else {
                try {
                    Main.diroeste.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                imprimirInfo();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Main.diroeste.release();
                this.empiezaen = false;
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Main.direste.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                imprimirInfo();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Main.direste.release();
            }
        }
    }
}

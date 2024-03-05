package ejercicio2;

import java.util.concurrent.Semaphore;

public class Main {
    public static Semaphore dirnorte = new Semaphore(0);
    public static Semaphore dirsur = new Semaphore(0);
    public static Semaphore direste = new Semaphore(0);
    public static Semaphore diroeste = new Semaphore(0);
    public static Semaphore dirpeatones1 = new Semaphore(0);
    public static Semaphore dirpeatones2 = new Semaphore(0);
    public static Semaphore pantalla = new Semaphore(1);
    public static Semaphore mutex1 = new Semaphore(1);
    public static Semaphore mutex2 = new Semaphore(0);
    public static Semaphore mutex3 = new Semaphore(0);
	public static void main(String[] args) {
            
        Thread[] arraycoches = new Thread[50];
        Thread[] arraypeatones = new Thread[100];
        for (Thread c : arraycoches) {
            c = new Coche();
            c.start();
        }
        
        for (Thread p : arraypeatones) {
            p = new Peaton();
            p.start();
        }
        
        SemaforoNorteSur semnortesur = new SemaforoNorteSur();
        SemafotoEsteOeste semesteoeste = new SemafotoEsteOeste();
        SemaforoPeatones sempeatones = new SemaforoPeatones();
        semnortesur.start();
        semesteoeste.start();
        sempeatones.start();
	}
}

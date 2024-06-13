package ejercicio2;

import java.util.concurrent.Semaphore;

public class Main {
	public static Semaphore mutex = new Semaphore(1);
	public static Semaphore norteSur = new Semaphore(0);
	public static Semaphore esteOeste = new Semaphore(0);
	public static Semaphore peatones = new Semaphore(0);
	public static Semaphore cochesNS = new Semaphore(0);
	public static Semaphore cochesEO = new Semaphore(0);
	public static Semaphore peatonesPE = new Semaphore(0);
	public static int direccionPasando = 0; // 0 = NS, 1 = EO, 2 = PE
	public static int ncochesNSe = 0;
	public static int ncochesEOe = 0;
	public static int numeroPEe = 0;
	public static int ncochesNS = 0;
	public static int ncochesEO = 0;
	public static int numeroPE = 0;
	private static int NUM_COCHES = 50;
	private static int NUM_PEATONES = 100;

	/**
	 * Método principal del ejercicio 2. En esta clase se inicializan los semáforos y
	 * los procesos que utilizan las variables compartidas, tanto coches y peatones
	 * como semáforos de paso.
	 */
	public static void main(String[] args) {
		SemaforoNorteSur semNS = new SemaforoNorteSur();
		SemaforoEsteOeste semEO = new SemaforoEsteOeste();
		SemaforoPeatones semPE = new SemaforoPeatones();
		Thread[] arrayCoches = new Thread[NUM_COCHES];
		Thread[] arrayPeatones = new Thread[NUM_PEATONES];
		semNS.start();
		semEO.start();
		semPE.start();
		for (int i = 0; i < NUM_COCHES; i++) {
			arrayCoches[i] = new Coche();
			arrayCoches[i].start();
		}
		for (int i = 0; i < NUM_PEATONES; i++) {
			arrayPeatones[i] = new Peaton();
			arrayPeatones[i].start();
		}

	}
}

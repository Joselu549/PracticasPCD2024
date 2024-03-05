package ejercicio1;

public class HiloProductor extends Thread {
	ArrayCompartido arrayCompartido;
	public HiloProductor(ArrayCompartido arrayCompartido) {
		this.arrayCompartido = arrayCompartido;
	}
	
	@Override
	public void run() {
		arrayCompartido.llenarArray();
	}
}

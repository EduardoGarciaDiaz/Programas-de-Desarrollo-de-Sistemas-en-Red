package Ejemplo2;

public class Programa2 {
    public static void main(String[] args) throws InterruptedException{
        // Creando corredores con ID
        Corredor corredor1 = new Corredor('1');
        Corredor corredor2 = new Corredor('2');
        Corredor corredor3 = new Corredor('3');

        //Se ejecuta cada uno de los corredores en un hilo
        Thread p1 = new Thread(corredor1);
        Thread p2 = new Thread(corredor2);
        Thread p3 = new Thread(corredor3);

        // un método join() para que los otros hilos no puedan iniciar hasta que termine el hilo que se está ejecutando.
        p1.start();
        p1.join();
        p2.start();
        p2.join();
        p3.start();
        p3.join();

    }
}

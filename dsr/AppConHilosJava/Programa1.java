public class Programa1 extends Thread {
    // Implementar métodos de Runnable
    // Se ejecuta al llamar al método start()

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " subproceso corriendo...");
    }

    public static void main(String[] args) {
        //Creando un hilo en Java
        Programa1 hilo = new Programa1();
        hilo.start();
        System.out.println("Este codigo esta fuera del hilo");
    }
    
}
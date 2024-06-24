public class Programa2 implements Runnable {
    private static final int LATENCIA_MILIS = 200;

    @Override
    public void run() {
        while (true) {
            System.out.print(Thread.currentThread().getName() + " ");
            try {
                Thread.sleep(LATENCIA_MILIS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }        
        }
    }

    public static void main(String[] args) {
        Programa2 p1 = new Programa2();
        Programa2 p2 = new Programa2();

        Thread hilo1 = new Thread(p1);
        hilo1.setName("Hola");
        Thread hilo2 = new Thread(p2);
        hilo2.setName("Mundo");
        
        hilo1.start();
        hilo2.start();
    }


}

public class Programa1 implements Runnable {

    private int inicio;
    private int fin;
    private boolean isAscending;

    public Programa1(int inicio, int fin, boolean isAscending) {
        this.inicio = inicio;
        this.fin = fin;
        this.isAscending = isAscending;
    }

    @Override
    public void run() {
        if (isAscending){
            increment();
        } else {
            decrement();
        }

    }

    public void increment() {
        for (int i = inicio ; i < fin ; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void decrement() {
        for (int i = inicio ; i > fin ; i--) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Programa1 p1 = new Programa1(1, 101, true);
        Programa1 p2 = new Programa1(100, 0, false);

        Thread hilo1 = new Thread(p1);
        hilo1.setName("Hilo-1");
        Thread hilo2 = new Thread(p2);
        hilo2.setName("Hilo-2");

        hilo1.start();
        hilo2.start();
    }
}
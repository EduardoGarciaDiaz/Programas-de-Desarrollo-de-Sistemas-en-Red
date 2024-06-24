import java.util.Random;

public class Filosofo implements Runnable {

    private int[] miTenedor = {-1,-1};
    private int numeroFilosofo;
    private static Object[] tenedores = {true, true, true, true, true};
    private final int tiempoMaximoSleepMilis = 5000;

    public Filosofo(int numeroFilosofo) {
        this.numeroFilosofo = numeroFilosofo;
    }

    @Override
    public void run() {
        while (true) {
            Pensar();
            TomarTenedores(numeroFilosofo);
            
            if (miTenedor[0] > -1 && miTenedor[1] > -1){
                Comer();
            }

            SoltarTenedores(numeroFilosofo);
        }
    }

    public void Pensar() {
        
        System.out.println(Thread.currentThread().getName() + " está pensando...");

        Random random = new Random();
        int tiempoPensandoMilis = random.nextInt(tiempoMaximoSleepMilis);

        try {
            Thread.sleep(tiempoPensandoMilis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void TomarTenedores(int numeroFilosofo) {
        System.out.println(Thread.currentThread().getName() + " va a tomar tenedores...");
    
        int indiceTenedorIzquierdo = numeroFilosofo;
        int indiceTenedorDerecho = (numeroFilosofo + 1) % tenedores.length;

        synchronized(tenedores){
            while (!((Boolean)tenedores[indiceTenedorIzquierdo]).booleanValue() || !((Boolean)tenedores[indiceTenedorDerecho]).booleanValue()) {
                try {
                    tenedores.wait(); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            tenedores[indiceTenedorDerecho] = false;

            tenedores[indiceTenedorIzquierdo] = false;
    
            System.out.println(Thread.currentThread().getName() + " Toma el tenedor derecho: " + indiceTenedorDerecho);
            miTenedor[0] = indiceTenedorDerecho;
            System.out.println(Thread.currentThread().getName() + " Toma el tenedor izquierdo: " + indiceTenedorIzquierdo);
            miTenedor[1] = indiceTenedorIzquierdo;
        }
    
        
    }

    public void Comer() {
        if (miTenedor[0] > -1 && miTenedor[1] > -1){
            System.out.println(Thread.currentThread().getName() + " está comiendo...");

            Random random = new Random();
            int tiempoComiendoMilis = random.nextInt(tiempoMaximoSleepMilis);
    
            try {
                Thread.sleep(tiempoComiendoMilis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void SoltarTenedores(int numeroFilosofo){
        System.out.println(Thread.currentThread().getName() + " Dejó de comer.");
        synchronized(tenedores) {
            tenedores[miTenedor[0]] = true;
            tenedores[miTenedor[1]] = true;
    
            miTenedor[0] = -1;
            miTenedor[1] = -1;
            
            tenedores.notify();

        }
    }
}

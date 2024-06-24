public class Main {

    public static void main(String[] args) {
        Thread[] filosofos = new Thread[5];

        for (int i = 0 ; i < filosofos.length ; i++){
            Filosofo filosofo = new Filosofo(i);
            Thread hilo1 = new Thread(filosofo);
            hilo1.setName("Filosofo " + i);

            filosofos[i] = hilo1;
        }

        for (int i = 0 ; i < filosofos.length ; i++){

            filosofos[i].start();
        }
    }
    
}

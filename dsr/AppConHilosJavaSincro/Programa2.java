public class Programa2 {
    private static Object hora = new Object();

    // Clase privada que implementa Runnable
    private static class JuntaThread implements Runnable{
        public JuntaThread(){
            hora = 9;
        }

        @Override
        public void run(){
            realizarJunta();
        }

        public static void realizarJunta(){
            synchronized (hora) {
                System.out.println(Thread.currentThread().getName() + " junta iniciada a las " + hora);
                hora = (int) hora +1;
                System.out.println(Thread.currentThread().getName() + " junta terminada a las " + hora);    
            }
        }
    }

    public static void main(String[] args){
        //Creación de 10 hilos, 1 por junta.
        for (int i = 0 ; i < 10; i++) {
            Thread hilo = new Thread(new JuntaThread(), "Junta-" + (i+1));
            hilo.start();
        }
    }
}
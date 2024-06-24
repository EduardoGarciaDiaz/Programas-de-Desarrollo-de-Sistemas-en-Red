package ServidorMultihilo;

import java.io.IOException;
import java.net.ServerSocket;

public class ServidorMultihilo {
    
    public static void main(String[] args) {
        //Puerto a usar
        final int PUERTO = 9000;

        // Crea un socket de servidor
        try (ServerSocket ss = new ServerSocket(PUERTO)) {
            System.out.println("Servidor escuchando en el puerto: " + PUERTO + " ...");

            int count = 0;
            
            // El servidor va a escuchar conexiones hasta presionar Ctrl+C
            // Cada cliente se manda a un nuevo hilo por lo que siempre
            // estará diespuesto a reicibir nuevos clientes
            while (true) {
                // El servidor envía un archivo grande a cada cliente
                count++;
                HiloHandler cliente = new HiloHandler(ss.accept(), count);
                System.out.println("El servidor tiene " + cliente.count + " Clientes conectados");
                Thread h1 = new Thread(cliente);
                h1.start();
            }

        } catch (IOException ex) {  
            System.out.println(ex);
        }
    }

}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        // Puerto a utilizar
        final int PUERTO = 9000;

        try{

            //Cuando un cliente se conecta se abre el puerto a escuchar conexiones de cliente
            // Si el servidor responde, AQUI se crea el Socket en el SO
            // Socket que se conecta al servidor y crea flujos
            Socket cs = new  Socket("localhost", PUERTO);

            // Establece el stream de salida
            PrintWriter out = new PrintWriter(cs.getOutputStream(), true);
            // Establece el stream de entrada
            BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));

            //Leer mensajes enviados por el servidor
            String lineaRecibida;
            // Iniciamos la comunicación
            // Leemos linea por linea hasta recibir "EOF"
            while (!(lineaRecibida = in.readLine()).equalsIgnoreCase("EOF")) {
                System.out.println("Servidor: " + lineaRecibida);
            }

            // Despedida al servidor
            out.println("Recepcion de datos correcta...");

            //Cierre las conexiones
            out.close();
            in.close();
            cs.close();

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}

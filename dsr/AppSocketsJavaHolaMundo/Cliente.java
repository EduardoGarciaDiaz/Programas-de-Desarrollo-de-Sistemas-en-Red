import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        // Puerto a utilizar
        final int PUERTO = 8080;

        try {
            
            // Cuando un cliente se conecta se abre el puerto a escuchar conexiones de cliente
            // Si el servidor responde, AQUI se crea el SOCKER en el SO
            Socket socket = new Socket("localhost", PUERTO);
            
            // Establece el stream de salida
            PrintWriter outPrintWriter = new PrintWriter(socket.getOutputStream(), true);
            // Establece el stream de entrada
            BufferedReader inBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Variables para leer los datos
            String lineaRecibida;

            // Iniciamos la comunicación
            // Leemos linea por linea hasta recibir "Adios"
            while (!(lineaRecibida = inBufferedReader.readLine()).equalsIgnoreCase("Adios")) {
                System.out.println("Servidor: " + lineaRecibida);
            }

            // Despedida al servidor
            outPrintWriter.println("Recepcion de datos correcta...");

            outPrintWriter.close();
            inBufferedReader.close();
            socket.close();

        } catch (IOException ex) {

        }
    }
}

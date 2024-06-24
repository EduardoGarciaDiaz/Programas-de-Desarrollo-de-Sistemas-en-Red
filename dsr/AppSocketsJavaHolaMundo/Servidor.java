import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    
    public static void main(String args[]) {
        // Puerto a utilizar:
        final int PUERTO = 8080;
        
        try {
            // Objeto socket de servidor
            ServerSocket serverSocket = new ServerSocket(PUERTO);
            System.out.println("Servidor escuchando en el puerto: " + PUERTO + "...");

            // Cuando un cliente se conecta se abre el puerto a escuhar conexiones de cliente
            // AQUI se crea el SOCKET en el SO
            Socket socket = serverSocket.accept();

            // Establece el stream de salida
            PrintWriter outPrintWriter = new PrintWriter(socket.getOutputStream(), true);
            // Establece el stream de entrada
            BufferedReader inBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Mensaje enviado del servidor al cliente
            outPrintWriter.println("Hola");
            outPrintWriter.println("Mundo");
            outPrintWriter.println("Eduardo García Díaz");
            outPrintWriter.println("Desde el servidor!");
            outPrintWriter.println("Adios");

            //Esperar por el cliente
            System.out.println("Cliente: " + inBufferedReader.readLine());

            //Cerrar conexiones
            outPrintWriter.close();
            inBufferedReader.close();
            socket.close();
            serverSocket.close();

        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        }

    }

}
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServidorUDP {
    public static void main(String[] args) throws SocketException, IOException{
        // Puerto establecido con el cliente
        final int puerto = 8080;
        // Retardo en milisegundos
        final int retardo = 4000;

        // Socket UDP
        // Try with finally para cerrar el socket automáticamente
        try (DatagramSocket socketUDP = new DatagramSocket(puerto)) {
            // Buffer que recibe peticiones no mayor a 1024 bytes
            byte[] buffer = new byte[1024];
            System.out.println("Servidor UDP escuchando en el puerto " + puerto + "...");

            // Bucle infinito para escuchar al cliente
            while (true) {
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                //Recibimos la respuesta
                socketUDP.receive(peticion);

                // Mostrar IP y puerto del cliente
                System.out.println("Datagrama recibido del host: " + peticion.getAddress());
                System.out.println("Desde el puerto remoto: " + peticion.getPort());
                System.out.println("Datos recibidos: " + new String(peticion.getData()));

                // Fecha y hora actual
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String strFecha = now.format(formatter);
                System.out.println("La hora del servidor es: " + strFecha );

                try {
                    System.out.println("Simulando retardo en milisegundos: " + retardo);
                    Thread.sleep(retardo);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                // Se crea UDP con mensaje , IP y Puerto
                DatagramPacket respuesta = new DatagramPacket(strFecha.getBytes(), strFecha.getBytes().length,
                peticion.getAddress(), peticion.getPort());

                // Enviar respuesta al cliente
                socketUDP.send(respuesta);
                System.out.println("Datos enviados al cliente.");
            }
        }

    }
    
}
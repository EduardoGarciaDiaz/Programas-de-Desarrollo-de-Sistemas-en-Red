import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClienteUDP {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        // Mensaje a enviar
        String mensaje = new String("Dame la hora local servidor.");
        // Establecemos al servidor
        String servidor = new String("localhost");

        // Puerto acordado con el servidor
        final int puerto = 8080;
        // Espera máxima en milisegundos
        final int espera = 5000;

        // Creamos el socket UDP
        DatagramSocket socketUDP = new DatagramSocket();
        // Obtenga la IP de localhost
        InetAddress hostServidor = InetAddress.getByName(servidor);

        // Se crea la petición UDP con el mensaje, IP y Puerto
        DatagramPacket peticion = new DatagramPacket(mensaje.getBytes(), mensaje.getBytes().length,
        hostServidor, puerto);

        // Esperamos un tiempo maximo
        socketUDP.setSoTimeout(espera);
        System.out.println("Esperamos datos en un maximo dde " + espera + " milisegundos...");
        
        // Se envia la peticion UDP al servidor
        socketUDP.send(peticion);

        try {
            // Esperamos la respuesta no mayor a 1024 bytes
            byte[] buffer = new byte[1024];
            DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length);
            // Recibimos la respuesta
            socketUDP.receive(respuesta);

            // Convertimos  cadena los datos recibidos
            String strText = new String(respuesta.getData(), 0, respuesta.getLength());
            // Convertimos la cadena a tipo Fecha
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime horaServidor = LocalDateTime.parse(strText, formatter);

            //Imprimimos la hora del servidor
            System.out.println("La hora del servidor es: " + horaServidor.format(formatter));

            LocalDateTime horaLocal = MostrarHoraLocal(); 

            Duration duracion = Duration.between(horaServidor, horaLocal);
            System.out.println("La diferencia entre las dos horas es: " + duracion.toMillis() + " milisegundos.");
            
            //Cerrando conexion
            socketUDP.close();
        }catch (SocketTimeoutException e) {
            System.out.println("Tiempo de espera agotado: " + e.getMessage());
        }

    }

    public static LocalDateTime MostrarHoraLocal(){
        // Fecha y hora actual
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String strFecha = now.format(formatter);
        System.out.println("La hora del cliente es: " + strFecha );

        return now;
    }
}
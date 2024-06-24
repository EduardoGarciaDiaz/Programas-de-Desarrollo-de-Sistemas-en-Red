import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Scanner;

public class MulticastUDP {
    @SuppressWarnings("deprecation")
    public static void main(String args[]) {
        try {
            // Puerto acordado para multicast
            final int puerto = 8081;
            // IP para multicast
            InetAddress grupo = InetAddress.getByName("224.0.0.0");
            // Socket muulticast
            MulticastSocket socket = new MulticastSocket(puerto);
            // Se une al grupo
            socket.joinGroup(grupo);

            // A cada nuevo miembro, le permitimos enviar un solo mensaje
            Scanner sc = new Scanner(System.in);
            System.out.println("Envie un mensaje al grupo: ");
            String msj = sc.nextLine();

            // Enviar mensaje por UDP al grupo de todos los clientes conectados
            byte[] m = msj.getBytes();
            DatagramPacket mensajeSalida = new DatagramPacket(m, m.length, grupo, puerto);
            socket.send(mensajeSalida);

            // Buffer para leer datos de otros clientes; No mayor a 1024  bytes
            byte[] buffer = new byte[1024];
            String linea;

            // Leer mensajes de nuevos clientes en el grupo
            // hasta recibir "Adios" por parte de un nuevo miembro
            while (true) {
                // Leemos mensajes UDP que lleguen
                DatagramPacket mensajeEntrada = new DatagramPacket(buffer, buffer.length);
                socket.receive(mensajeEntrada);

                // Convierte el mensaje a cadena
                linea = new String(mensajeEntrada.getData(), 0, mensajeEntrada.getLength());
                System.out.println("Recibido: " + linea);

                // Si el mensaje es "Adios", termina las conexiones
                if (linea.equalsIgnoreCase("Adios")) {
                    socket.leaveGroup(grupo);
                    break;
                }
            }

            // Cerrar conexiones
            sc.close();
            socket.close();

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }
}
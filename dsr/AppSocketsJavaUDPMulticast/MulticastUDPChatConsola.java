import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Scanner;

@SuppressWarnings("deprecation")
public class MulticastUDPChatConsola{

    static boolean terminado = false;
    static String nombre;

    public static void main(String[] args) {
        try {
            final int puerto = 8081;
            // IP para multicast
            InetAddress grupo = InetAddress.getByName("224.0.0.0");
            // Socket muulticast
            MulticastSocket socket = new MulticastSocket(puerto);
            // Se une al grupo
            socket.joinGroup(grupo);

            Scanner sc = new Scanner(System.in);
            System.out.println("Ingrese su nombre: ");
            nombre = sc.nextLine();
            System.out.println("\nPuedes comenzar a chatear: ");

            String linea;
            byte[] buffer = new byte[1024];

            Thread hiloLectura = new Thread(new HiloLectura(socket));
            hiloLectura.start();

            // Se queda a la espera de escritura de este cliente, hasta recibir "Adios"
            while (true) {
                linea = sc.nextLine();

                if (linea.equalsIgnoreCase("Adios")) {
                    terminado = true;

                    // Avisar que nos vamos
                    linea = nombre + ": Ha terminado la conexion";
                    buffer = linea.getBytes();
                    DatagramPacket mensajeSalida = new DatagramPacket(buffer, buffer.length, grupo, puerto);
                    socket.send(mensajeSalida);

                    // Cerrar conexiones
                    socket.leaveGroup(grupo);
                    socket.close();
                    sc.close();
                    break;
                }

                // Enviamos al grupo el mensaje escrito
                linea = nombre + ": " + linea;
                buffer = linea.getBytes();
                DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, grupo, puerto);
                socket.send(datagram);
            }    

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }    

    static class HiloLectura implements Runnable {
        private MulticastSocket socket;

        public HiloLectura(MulticastSocket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            String linea;

            while (!terminado) {
                try {
                    DatagramPacket mensajeEntrada = new DatagramPacket(buffer, buffer.length);
                    socket.receive(mensajeEntrada);
                    linea = new String(buffer, 0, mensajeEntrada.getLength());
                    if (!linea.startsWith(nombre)) {
                        System.out.println(linea);
                    }

                } catch (IOException ex) {
                    System.out.println("Comunicación y socket cerrados.");
                }
            }
        }
    }
}

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("deprecation")
public class MulticastUDPChatGrafico extends JFrame{
    static boolean terminado = false;
    static String nombre;

    private static JLabel lblNombre;
    private static JTextField tfMensaje;
    private static JButton btnEnviar;
    private static JScrollPane spChat;
    private static JTextArea taChat;

    public MulticastUDPChatGrafico() {
        super("Chat Multicast UDP");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        lblNombre = new JLabel("Usuario: " + nombre);
        add(lblNombre, BorderLayout.NORTH);

        taChat = new JTextArea();
        spChat = new JScrollPane(taChat);
        taChat.setEditable(false);
        add(spChat, BorderLayout.CENTER);

        JPanel panelMensajeria = new JPanel(new BorderLayout());

        tfMensaje = new JTextField();
        btnEnviar = new JButton("Enviar");
        panelMensajeria.add(tfMensaje, BorderLayout.CENTER);

        panelMensajeria.add(btnEnviar, BorderLayout.EAST);
        add(panelMensajeria, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {

        new JOptionPane();
        String respuesta = JOptionPane.showInputDialog(null, "Ingrese su nombre: ");
        if (respuesta == null) {
            System.exit(0);
        } else {
            nombre = respuesta;
            new MulticastUDPChatGrafico();
        }

        try {
            final int puerto = 8081;
            // IP para multicast
            InetAddress grupo = InetAddress.getByName("224.0.0.0");
            // Socket muulticast
            MulticastSocket socket = new MulticastSocket(puerto);
            // Se une al grupo
            socket.joinGroup(grupo);

            Thread hiloLectura = new Thread(new HiloLectura(socket));
            hiloLectura.start();

            tfMensaje.addActionListener(e -> {
                btnEnviar.doClick();
            });


            btnEnviar.addActionListener(e -> {
                String linea = tfMensaje.getText();
                taChat.append(linea + "\n");

                try {
                    if (linea.equalsIgnoreCase("Adios")) {
                        terminado = true;
            
                        // Avisar que nos vamos
                        linea = nombre + ": Ha terminado la conexion";
                        byte[] buffer = linea.getBytes();
                        DatagramPacket mensajeSalida = new DatagramPacket(buffer, buffer.length, grupo, puerto);
                        socket.send(mensajeSalida);
                        tfMensaje.setText("");
            
                        // Cerrar conexiones
                        socket.leaveGroup(grupo);
                        socket.close();
                        System.exit(0);
                    } else {
                        linea = nombre + ": " + linea;
                        byte[] buffer = linea.getBytes();
                        DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, grupo, puerto);
                        socket.send(datagram);
                        tfMensaje.setText("");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });   

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
                        taChat.append(linea + "\n");
                    }

                } catch (IOException ex) {
                    System.out.println("Comunicación y socket cerrados.");
                }
            }
        }
    }

}

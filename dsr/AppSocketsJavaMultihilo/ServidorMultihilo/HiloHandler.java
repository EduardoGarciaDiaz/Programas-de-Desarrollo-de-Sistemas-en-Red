package ServidorMultihilo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Paths;

public class HiloHandler implements Runnable{

    // ch de channel
    private final Socket ch;
    PrintWriter out;
    BufferedReader in;
    int count = 0;

    //Creación de flujos
    public HiloHandler(Socket ch, int count) throws IOException {
        this.count = count;
        // Recibe el socket para escuchar conexiones de cliente
        this.ch = ch;
        // Establece el stream de salida
        out = new PrintWriter(ch.getOutputStream(), true);
        // Establece el stream de entrada
        in = new BufferedReader(new InputStreamReader(ch.getInputStream()));
    
        System.out.println("Conexión recibida del cliente: " + count + " " + ch.getInetAddress().getHostAddress());
        count++;
    }

    @Override
    public void run() {
        try {
            // Abrir un flujo a un archivo grande para que tarde enviandolo
            String pathArchivo = Paths.get("ServidorMultihilo\\archivote.csv").toAbsolutePath().toString();
            File file_in = new File(pathArchivo);

            // Abrir archivo y pasar linea por linea del archivo al cliente
            FileReader fr;
            fr = new FileReader(file_in);
            BufferedReader br = new BufferedReader(fr);

            // agregue la lectura de cada una de las líneas 
            //y envíela al flujo de datos de salida del cliente
            String lineaLeida;
            while ((lineaLeida = br.readLine()) != null) {
                out.println(lineaLeida);
            }

            // Enviamos al cliente que el archivo ha sido transmitido
            out.println("EOF");
            br.close();
            fr.close();

            // Leemos la despedida del cliente
            System.out.println("Cliente " + this.count + " :" + in.readLine());

            //Cerramos las conexiones
            in.close();
            out.close();
            ch.close();

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}

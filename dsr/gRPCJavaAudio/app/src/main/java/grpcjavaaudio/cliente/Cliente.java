package grpcjavaaudio.cliente;

import java.util.Scanner;

// Soporte enviar stream gRPC
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

// Soporte para archivos
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

// Player WAV
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;

// Soporte archivo proto
import com.proto.audio.AudioServiceGrpc;
import com.proto.audio.Audio.DownloadFileRequest;

// Player MP3
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Cliente {
    
    public static void main(String[] args) {
       // Establece el servidor gRPC
       String host = "localhost";
       // Puerto del servidor gRPC
       int puerto = 8080;
       // Guarda el nombre del archivo a solicitar, de Resourses
       String nombreArchivo;

       // Crear lectura de consola
       Scanner sc = new Scanner(System.in);

       // Crear canal de comunicacion
       ManagedChannel ch = ManagedChannelBuilder
                .forAddress(host, puerto)
                .usePlaintext()
                .build();

        while (true){
            System.out.println("Escoge la opcion deseada:");
            System.out.println("1. Descargar archivo y reproducir al mismo tiempo WAV");
            System.out.println("2. Descargar archivo y luego reproducir WAV");
            System.out.println("3. Descargar archivo y luego reproducir MP3");
            System.out.println("4. Salir");

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion){
                case 1:
                    //Recibe el archivo WAV y lo reproduce mientras llega
                    nombreArchivo = "anyma.wav";
                    streamWav(ch, nombreArchivo, 44100F);
                    break;
                case 2:
                    //Recibe el archivo WAV y lo reproduce después de recibirlo
                    nombreArchivo = "anyma.wav";
                    ByteArrayInputStream streamWav = downloadFile(ch, nombreArchivo);
                    //Reproduce WAV
                    playWav(streamWav, nombreArchivo);
                    try {
                        streamWav.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    //Recibe el archivo MP3 y lo reproduce después de recibirlo
                    nombreArchivo = "Avicii - The Nights.mp3";
                    ByteArrayInputStream streamMP3 = downloadFile(ch, nombreArchivo);
                    //Reproduce MP3
                    playMp3(streamMP3, nombreArchivo);
                    try {
                        streamMP3.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    
                    // Terminamos la comunicación
                    System.out.println("Apagando...");
                    ch.shutdown();
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    // Descarga y reproduce al mismo tiempo
    public static void streamWav(ManagedChannel ch, String nombre, float sampleRate){
        try {
            // AudioFormat(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian)
            AudioFormat newFormat = new AudioFormat(sampleRate, 16, 2, true, false);
            SourceDataLine sourceDataLine = AudioSystem.getSourceDataLine(newFormat);
            sourceDataLine.open(newFormat);
            sourceDataLine.start();

            // Obtenemos la referencia al stub
            AudioServiceGrpc.AudioServiceBlockingStub stub = AudioServiceGrpc.newBlockingStub(ch);
            // Construimos la peticion enviando un parametro
            DownloadFileRequest peticion = DownloadFileRequest.newBuilder().setNombreArchivo(nombre).build();

            // Establecemos una longitud de chunk
            int bufferSize = 1024;
            System.out.println("Reproduciendo el archivo: " + nombre);

            // Usando el stub, realizamos la llamada streaming RPC
            stub.downloadAudio(peticion).forEachRemaining(respuesta -> {
                try {
                    sourceDataLine.write(respuesta.getData().toByteArray(), 0, bufferSize);
                }catch (Exception ex){
                    System.out.println("Error al reproducir el archivo: " + nombre);
                }
            });

            System.out.println("\nRecepcion de datos correcta.");
            System.out.println("Reproduccion terminada.\n\n");

            // Cerramos la linea
            sourceDataLine.drain();
            sourceDataLine.close();

        } catch (LineUnavailableException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Descarga el archivo (stream)
    public static ByteArrayInputStream downloadFile(ManagedChannel ch, String nombre){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        // Obtenemos la referencia al stub
        AudioServiceGrpc.AudioServiceBlockingStub stub = AudioServiceGrpc.newBlockingStub(ch);
        // Construimos la peticion enviando un parametro
        DownloadFileRequest peticion = DownloadFileRequest.newBuilder().setNombreArchivo(nombre).build();

        System.out.println("Recibiendo el archivo: " + nombre);
        // Usando el stub, realizamos la llamada streaming RPC
        stub.downloadAudio(peticion).forEachRemaining(respuesta -> {
            // Imprimimos la respuesta de RPC
            try {
                stream.write(respuesta.getData().toByteArray());
                System.out.print(".");
            }catch (Exception ex){
                System.out.println("Error al recibir el archivo: " + nombre);
            }
        });

        System.out.println("\nRecepcion de datos correcta.\n\n");

        // Convierte el output stream a un input stream para reproducirlo
        return new ByteArrayInputStream(stream.toByteArray());
    }

    // Reproduce un archivo WAV descargado
    public static void playWav(ByteArrayInputStream inStream, String nombre){
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(inStream);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            System.out.println("Reproduciendo el archivo: " + nombre + "...\n\n");
            clip.start();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                System.out.println("Error al reproducir el archivo: " + nombre);
            }
            clip.stop();
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Reproduce un archivo MP3 descargado
    public static void playMp3(ByteArrayInputStream inStream, String nombre){
        try {
            System.out.println("Reproduciendo el archivo: " + nombre + "...\n\n");
            Player player = new Player(inStream);	
            player.play();
        }catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
}

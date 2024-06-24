package grpcholamundostream.servidor;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class Servidor {
    public static void main(String[] args) throws IOException, InterruptedException {
        int puerto = 8080;

        // Creando el servidor usando la implementacion del proto
        Server servidor = ServerBuilder
                .forPort(puerto)
                .addService(new ServidorImpl())
                .build();

        // Se inicia el servidor
        servidor.start();

        System.out.println("Servidor iniciado...");
        System.out.println("Escuchando en el puerto: " + puerto);

        // Al recibir la solicitud de apagado Ctrl + C
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Recibiendo solicitud de apagado...");
                servidor.shutdown();
                System.out.println("Servidor detenido.");
            }
        });

        // Esperar a que se cierren las conexiones
        servidor.awaitTermination();   
    }
}

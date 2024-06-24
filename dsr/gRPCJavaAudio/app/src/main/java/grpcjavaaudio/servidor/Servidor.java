package grpcjavaaudio.servidor;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class Servidor {

    public static void main(String[] args) throws InterruptedException, IOException {
        int puerto = 8080;

        // Creando servidor gRPC usando la implementacion del proto
        Server servidor = ServerBuilder.forPort(puerto)
                .addService(new ServidorImpl())
                .build();
 
        // Iniciar servidor
        servidor.start();

        System.out.println("Servidor iniciado...");
        System.out.println("Escuchando en el puerto: " + puerto);

        // Al recibir la solicitud de apagado
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Recibida solicitud de apagado...");
                servidor.shutdown();
                System.out.println("Servidor apagado...");
            }
        });

        // Espera a que se cierren las conexiones
        servidor.awaitTermination();
    }
}

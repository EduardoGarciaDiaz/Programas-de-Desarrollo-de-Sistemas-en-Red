package grpcholamundo.servidor;

import java.io.IOException;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class Servidor {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Puerto
        int puerto = 8080;

        // Se crea el servidor gRPC usando la implementación del proto
        Server servidor = ServerBuilder
                .forPort(puerto)
                .addService(new ServidorImpl())
                .addService(new CalculadoraImpl())
                .build();
        
        // Se inicia el servidor
        servidor.start();

        System.out.println("Servidor iniciado...");
        System.out.println("Escuchando en el puerto: " + puerto);

        // Al recibir la solicitud de apagado Ctrl+C
        System.out.println("\nEscribe Ctrl+C para apagar el servidor.");
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("Recibiendo solicitud de apagado...");
                servidor.shutdown();
                System.out.println("Servidor apagado.");
            }
        });

        // Espera a que se cierren las conexiones
        servidor.awaitTermination();
    }
    
}
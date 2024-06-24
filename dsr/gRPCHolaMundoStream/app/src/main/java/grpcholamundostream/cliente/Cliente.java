package grpcholamundostream.cliente;

import com.proto.saludo.SaludoServiceGrpc;
import com.proto.saludo.Holamundo.SaludoRequest;
import com.proto.saludo.Holamundo.SaludoResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


public class Cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 8080;

        ManagedChannel ch = ManagedChannelBuilder
                .forAddress(host, puerto)
                .usePlaintext()
                .build();
        
        // Saludo una vez
        saludarUnario(ch);

        // Saludo muchas veces (Stream)
        saludarStream(ch);

        System.out.println("---------\nArchivo\n---------");

        // Obtener archivo (Stream)
        solicitarArchivo(ch, "archivote.csv");

        // Terminar comunicación
        System.out.println("Apagando...");
        ch.shutdown();       
    }

    //Saludo una vez. (Unario)
    public static void saludarUnario(ManagedChannel ch) {
        // Obtener referencia al stub
        SaludoServiceGrpc.SaludoServiceBlockingStub stub = SaludoServiceGrpc.newBlockingStub(ch);
        // Construir peticion enviando un parametro
        SaludoRequest peticion = SaludoRequest.newBuilder().setNombre("Eduardo").build();
        // Llamada RPC usando el Stub
        SaludoResponse respuesta = stub.saludo(peticion);

        //imprimimos la respuesta RPC
        System.out.println("Respuesta RPC: " + respuesta.getResultado());
    }

    // Saludi Muchas veces. (Stream)
    public static void saludarStream(ManagedChannel ch) {
        // Obtener referencia al stub
        SaludoServiceGrpc.SaludoServiceBlockingStub stub = SaludoServiceGrpc.newBlockingStub(ch);
        // Construimos la peticion enviando un parametro
        SaludoRequest peticion = SaludoRequest.newBuilder().setNombre("Eduardo").build();

        // Llamada RPC usando el Stub
        // al ser un Stream, Iteramos hasta que no haya más datos
        stub.saludoStream(peticion).forEachRemaining(respuesta -> {
            // Imprimimos la respuesta de RPC
            System.out.println("Respuesta RPC: " + respuesta.getResultado());
        });        
    }

    public static void solicitarArchivo(ManagedChannel ch, String nombreArchivo) {
        SaludoServiceGrpc.SaludoServiceBlockingStub stub = SaludoServiceGrpc.newBlockingStub(ch);
        SaludoRequest peticion = SaludoRequest.newBuilder().setNombre(nombreArchivo).build();

        stub.obtenerArchivoStream(peticion).forEachRemaining(respuesta -> {
            System.out.println(respuesta.getResultado());
        });
    }
}

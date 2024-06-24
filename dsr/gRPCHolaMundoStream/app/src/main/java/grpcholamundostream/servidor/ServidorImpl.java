package grpcholamundostream.servidor;

import java.util.Scanner;

import com.proto.saludo.Holamundo.SaludoRequest;
import com.proto.saludo.Holamundo.SaludoResponse;
import com.proto.saludo.SaludoServiceGrpc.SaludoServiceImplBase;

import io.grpc.stub.StreamObserver;

public class ServidorImpl extends SaludoServiceImplBase {

    @Override
    public void saludo(SaludoRequest request, StreamObserver<SaludoResponse> responseObserver) {
        // Construyendo respuesta a enviarle al cliente
        SaludoResponse respuesta = SaludoResponse.newBuilder()
                .setResultado("Hola " + request.getNombre())
                .build();

        // Enviar la respuesta al cliente con onNext
        responseObserver.onNext(respuesta);
        // Avisa que se ha terminado
        responseObserver.onCompleted();
    }

    @Override
    public void saludoStream(SaludoRequest request, StreamObserver<SaludoResponse> responseObserver) {
        // Ciclo for para simular el envio de multiples chunks de datos
        for (int i = 0 ; i <= 10 ; i++) {
            // Construyendo respuesta para enviarle al cliente
            SaludoResponse respuesta = SaludoResponse.newBuilder()
                    .setResultado("Hola " + request.getNombre() + " por " + i + "a vez.")
                    .build();

            // Enviar la respuesta al cliente con onNext
            responseObserver.onNext(respuesta);
        }
        // Avisa que se ha terminado
        responseObserver.onCompleted();
    }

    @Override
    public void obtenerArchivoStream(SaludoRequest request, StreamObserver<SaludoResponse> responseObserver) {
        String nombreArchivo = "/" + request.getNombre();

        try (Scanner scanner = new Scanner(ServidorImpl.class.getResourceAsStream(nombreArchivo))){
            while (scanner.hasNextLine()) {
                SaludoResponse respuesta = SaludoResponse.newBuilder()
                        .setResultado(scanner.nextLine())
                        .build();
                
                responseObserver.onNext(respuesta);
            }

            responseObserver.onCompleted();
        }
        
    }
    
}

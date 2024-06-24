package grpcjavaaudio.servidor;

import java.io.IOException;
import java.io.InputStream;

import com.proto.audio.Audio.DataChunkResponse;
import com.proto.audio.Audio.DownloadFileRequest;
import com.google.protobuf.ByteString;
import com.proto.audio.AudioServiceGrpc;

import io.grpc.stub.StreamObserver;

public class ServidorImpl extends AudioServiceGrpc.AudioServiceImplBase {

    @Override
    public void downloadAudio(DownloadFileRequest request, StreamObserver<DataChunkResponse> responseObserver) {
        // Obtenemos el nombre del archivo solicitado
        String archivoNombre = "/" + request.getNombreArchivo();
        System.out.println("\n\nEnviando el archivo: " + request.getNombreArchivo());

        // Abrimos el archivo
        InputStream fileStream = ServidorImpl.class.getResourceAsStream(archivoNombre);

        // Establecemos una longitud de chunk
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        // Cuenta los bytes leidos para enviar al cliente
        int length;
        try {
            while ((length = fileStream.read(buffer, 0, bufferSize)) != -1){
                // Se construye la respuesta a enviarle al cliente
                DataChunkResponse respuesta = DataChunkResponse.newBuilder()
                        .setData(ByteString.copyFrom(buffer, 0, length))
                        .build();

                        System.out.print(".");
                
                // En RPC se utiliza onNext para enviar la respuesta
                responseObserver.onNext(respuesta);
            }
            fileStream.close();
        } catch (IOException ex){
            System.out.println("No se pudo obtener el archivo: " + archivoNombre);
        }
        responseObserver.onCompleted();
    }
}

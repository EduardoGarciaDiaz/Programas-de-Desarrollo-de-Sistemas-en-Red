package grpcholamundo.servidor;

import com.proto.calculadora.Calculadora.OperacionRequest;
import com.proto.calculadora.Calculadora.OperacionResponse;
import com.proto.calculadora.CalculadoraServiceGrpc.CalculadoraServiceImplBase;

import io.grpc.stub.StreamObserver;

public class CalculadoraImpl extends CalculadoraServiceImplBase{
    @Override
    public void sumar(OperacionRequest request, StreamObserver<OperacionResponse> responseObserver) {

        float resultado = request.getPrimerNumero() + request.getSegundoNumero();

        OperacionResponse respuesta = OperacionResponse.newBuilder()
                .setResultado(resultado)
                .build();

        // onNext: Enviar la respuesta
        responseObserver.onNext(respuesta);
        responseObserver.onCompleted();
    }

    @Override
    public void restar(OperacionRequest request, StreamObserver<OperacionResponse> responseObserver) {
        float resultado = request.getPrimerNumero() - request.getSegundoNumero();

        OperacionResponse respuesta = OperacionResponse.newBuilder()
                .setResultado(resultado)
                .build();
        
        responseObserver.onNext(respuesta);
        responseObserver.onCompleted();
    }

    
    @Override
    public void multiplicar(OperacionRequest request, StreamObserver<OperacionResponse> responseObserver) {
        float resultado = request.getPrimerNumero() * request.getSegundoNumero();

        OperacionResponse respuesta = OperacionResponse.newBuilder()
                .setResultado(resultado)
                .build();
        
        responseObserver.onNext(respuesta);
        responseObserver.onCompleted();
    }

    @Override
    public void dividir(OperacionRequest request, StreamObserver<OperacionResponse> responseObserver) {
        float resultado = request.getPrimerNumero() / request.getSegundoNumero();

        OperacionResponse respuesta = OperacionResponse.newBuilder()
                .setResultado(resultado)
                .build();
        
        responseObserver.onNext(respuesta);
        responseObserver.onCompleted();        
    }
}
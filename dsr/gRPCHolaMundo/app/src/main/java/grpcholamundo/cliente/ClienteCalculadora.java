package grpcholamundo.cliente;

import javax.swing.JOptionPane;

import com.proto.calculadora.CalculadoraServiceGrpc;
import com.proto.calculadora.CalculadoraServiceGrpc.CalculadoraServiceBlockingStub;
import com.proto.calculadora.Calculadora.OperacionRequest;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ClienteCalculadora {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int puerto = 8080;

        // Canal de comunicación
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, puerto)
                .usePlaintext()
                .build();
        
        // Referencia al stub
        CalculadoraServiceGrpc.CalculadoraServiceBlockingStub stub = CalculadoraServiceGrpc.newBlockingStub(channel);

        MostrarCalculadora(stub);

        channel.shutdown();
    }
    
    private static void MostrarCalculadora(CalculadoraServiceBlockingStub stub){
        while(true){
            String opt = JOptionPane.showInputDialog(
                "Calcular\n" +
                "Suma.............(1)\n" +
                "Resta............(2)\n" +
                "Multiplicacion...(3)\n" +
                "Division.........(4)\n\n" +
                "Enter para salir."
            );

            if (opt == null || opt.equals("")) {
                break;
            }

            int primerNumero = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el primer numero"));
            int segundoNumero = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el segundo numero"));

            // Petición.
            OperacionRequest peticion = OperacionRequest.newBuilder()
            .setPrimerNumero(primerNumero)
            .setSegundoNumero(segundoNumero)
            .build();
        
            switch (opt){
                case "1":
                    // Llamada a RPC con el stub
                    JOptionPane.showMessageDialog(null,
                            primerNumero + " + " + segundoNumero + " = " 
                            + stub.sumar(peticion).getResultado());
                    break;
                case "2":
                    JOptionPane.showMessageDialog(null,
                            primerNumero + " - " + segundoNumero + " = " 
                            + stub.restar(peticion).getResultado());
                    break;
                case "3":
                    JOptionPane.showMessageDialog(null,
                            primerNumero + " x " + segundoNumero + " = " 
                            + stub.multiplicar(peticion).getResultado());
                    break;
                case "4":
                    JOptionPane.showMessageDialog(null,
                            primerNumero + " / " + segundoNumero + " = " 
                            + stub.dividir(peticion).getResultado());
                    break;
            }
        
        }
    }
}
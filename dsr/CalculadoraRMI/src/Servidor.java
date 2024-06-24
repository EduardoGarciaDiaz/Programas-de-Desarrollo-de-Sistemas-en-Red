import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Servidor {
    public static void main(String[] args) {
        try {
            
            // Puerto del servidor
            int puerto = 8080;
            // Direccion del servidor
            //String servidor = "localhost";
            String servidor = "192.168.56.105";

            System.out.println("Iniciando servidor en: ");
            System.out.println("Hostname: " + servidor);
            System.out.println("Puerto: " + puerto);

            // Crea el registro de este objeto remoto
            Registry registro = LocateRegistry.createRegistry(puerto);
            // Establece IP del servidor
            System.setProperty("java.rmi.server.hostname", servidor);
            // Le coloca un nombre al objeto remoto y establece el stub
            registro.rebind("Calculadora", new CalcRMI());

            System.out.println("Servidor en ejecución en espera de clientes...");
            
        } catch (RemoteException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }
}
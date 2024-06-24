import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculadora extends Remote {

    public float sumar(int a, int b) throws RemoteException;

    public float restar(int a, int b) throws RemoteException;

    public float multiplicar(int a, int b) throws RemoteException;
    
    public float dividir(int a, int b) throws RemoteException;
    
}
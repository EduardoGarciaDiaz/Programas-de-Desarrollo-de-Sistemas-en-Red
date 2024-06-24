import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalcRMI extends UnicastRemoteObject implements Calculadora{
    
    protected CalcRMI() throws RemoteException {
        super();
    }

    @Override
    public float sumar(int a, int b) throws RemoteException {
        return a + b;
    }

    @Override
    public float restar(int a, int b) throws RemoteException {
        return a - b;
    }

    @Override
    public float multiplicar(int a, int b) throws RemoteException {
        return a * b;
    }

    @Override
    public float dividir(int a, int b) throws RemoteException {
        return a / b;
    }
}
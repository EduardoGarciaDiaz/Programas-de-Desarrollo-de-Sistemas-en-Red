import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

public class Cliente {
    
    public static void main(String[] args) {
        // Puerto del servidor
        int puerto = 8080;	
        // Direccion del servidor
        //String servidor = "localhost";
        String servidor = "192.168.56.105";

        try{

            Calculadora calc = (Calculadora) Naming.lookup("rmi://" + servidor + ":" + puerto + "/Calculadora");
            
            while(true){
                String opt = JOptionPane.showInputDialog(
                    "Calcular\n" +
                    "Suma.............(1)\n" +
                    "Resta............(2)\n" +
                    "Multiplicación...(3)\n" +
                    "División.........(4)\n\n" +
                    "Cancelar para salir."
                );

                if (opt == null) {
                    break;
                }

                int num1 = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el primer número"));
                int num2 = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el segundo número"));
            
                switch (opt){
                    case "1":
                        JOptionPane.showMessageDialog(null, num1 + " + " + num2 + " = " + calc.sumar(num1, num2));
                        break;
                    case "2":
                        JOptionPane.showMessageDialog(null, num1 + " - " + num2 + " = " + calc.restar(num1, num2));
                        break;
                    case "3":
                        JOptionPane.showMessageDialog(null, num1 + " x " + num2 + " = " + calc.multiplicar(num1, num2));
                        break;
                    case "4":
                        JOptionPane.showMessageDialog(null, num1 + " / " + num2 + " = " + calc.dividir(num1, num2));
                        break;
                }
            
            }

        } catch (RemoteException | NotBoundException ex){
            JOptionPane.showMessageDialog(null, "No de pudo conectar al servidor:\n" + ex);
        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "La URL esta en forma incorrecto:\n" + ex);
        }
    }
}
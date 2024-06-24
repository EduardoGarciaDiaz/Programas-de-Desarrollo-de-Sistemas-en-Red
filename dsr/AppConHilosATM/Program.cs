namespace AppConHilosATM;

class Program
{
    static int accountBalance = 1000; // Saldo inicial de la cuenta
    static Random random = new Random();

    static void PerformTransaction(object? threadId)
    {
        for (int i = 0 ; i < 5 ; i++) 
        {
            //Cantidad a retirar aletaoria:
            int amountToWithdraw = random.Next(10, 101);

            //Simular tiempo de procesamiento
            Thread.Sleep(500);

            //Candado para garantizar que solo un hilo acceda al saldo a la vez
            lock (typeof(Program))
            {
                if (accountBalance >= amountToWithdraw)    
                {
                    accountBalance -= amountToWithdraw;
                    Console.WriteLine($"Thread {threadId}: Se retiraron ${amountToWithdraw} pesos. Quedan ${accountBalance} pesos");
                }
                else 
                {
                    System.Console.WriteLine($"Thread {threadId}: Fondos insuficientes. Se requieren ${amountToWithdraw} pesos" );
                }
            }
        }
    }

    static void Main(string[] args)
    {
        System.Console.WriteLine("¡Bienvenido al cajeo automático!");
        System.Console.WriteLine($"Cuentas con ${accountBalance} pesos");
        System.Console.WriteLine("Preione Enter para iniciar transacciones...");
        Console.ReadLine();

        //Crea varios hilos para simular transacciones en cajeros automáticos
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.Length; i++)
        {
            threads[i] = new Thread(PerformTransaction);
            threads[i].Start(i+1);
        }

        //Espera a que se completen todos los hilos
        foreach (Thread thread in threads)
        {
            thread.Join();
        }

        System.Console.WriteLine("Todas las transacciones completadas.");
        System.Console.WriteLine($"Saldo final de la cuenta: ${accountBalance} pesos");
    }
}

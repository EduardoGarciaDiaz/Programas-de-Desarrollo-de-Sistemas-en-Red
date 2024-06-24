namespace AppConHilos;

class Program
{
    public static void DoTrabajoPesado()
    {
        Console.WriteLine("DoTrabajoPesado: Estoy levantando un camión!!");
        Thread.Sleep(1000);
        Console.WriteLine("DoTrabajoPesado: Cansado! Necesito una siesta de 3 seg.");
        Thread.Sleep(1000);
        Console.WriteLine("DoTrabajoPesado: 1...");
        Thread.Sleep(1000);
        Console.WriteLine("DoTrabajoPesado: 2...");
        Thread.Sleep(1000);
        Console.WriteLine("DoTrabajoPesado: 3...");
        Console.WriteLine("DoTrabajoPesado: Ya desperté");

    }

    public static void DoAlgo()
    {
        Console.WriteLine("DoAlgo: Oye! Estoy haciendo algo aquí!");
        for (int i = 0; i < 20; i++)
            Console. Write($"{i} ");
        Console.WriteLine();
        Console.WriteLine("DoAlgo: Ya terminé.");
        
    }

    // Punto de entrada de un programa en C#
    static void Main(string[] args)
    {
        // Aquí Inicia la ejecución del programa
        Console.WriteLine("El Main thread comienza aqui.");

        ///Sin hilos:
        // Este método tarda 4 segundos en terminar.
        //Program.DoTrabajoPesado();

        ///Con hilos:
        //Crea un hilo y llama a un método en segundo plano
        Thread backgroundthread = new Thread(new ThreadStart(Program.DoTrabajoPesado));
        //inicia el hilo
        backgroundthread.Start();

        //Este método no se tarda nada
        Program.DoAlgo();

        // Aquí Termina la ejecución
        Console.WriteLine("El Main thread termina aquí");

    }

}

#include <stdio.h> 
#include <unistd.h> // for exit
#include <stdlib.h> // for fork(), and sleep()


int main ()
{
    // Creando el proceso hijo
    int pid = fork();
    printf("id del proceso: %d\n", pid);

    if (pid > 0) // True para el proceso padre
    {
        sleep(20);
    }
    else if (pid==0) // True para el proceso hijo
    {
        printf("\nProceso zombie creado con éxito!");
        printf("\nEstará activo durante 20 segundos\n");
        exit(0);

    }
    else{ // True cuando el procso Hijo no se pudo crear
        printf("\nLo sentimos! El proceso Hijo no pudo ser creado...");
    }

    return 0;
}
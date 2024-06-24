import threading
import os

# Funcion para el hilo 1
def task1():
    print("Tarea 1 asignada al thread: {}".format(threading.current_thread().name))
    print("ID del proceso corriendo la Tarea 1: {}".format(os.getpid()))

# Función para el hilo 
def task2():
    print("Tarea  asignada al thread: {}".format(threading.current_thread().name))
    print("ID del proceso corriendo la tarea 2: {}".format(os.getpid()))

#Uso de la variable __name__
if __name__ == "__main__":
    print("ID del proceso corriendo el programa main: {}".format(os.getpid()))
    print("Nombre del Main thread: {}".format(threading.current_thread().name))

# Creación de hilos
t1 = threading.Thread(target = task1, name = "Hilo 1")
t2 = threading.Thread(target = task2, name = "Hilo 2")

# Iniciación de hilos
t1.start()
t2.start()

print("Programa terminado")

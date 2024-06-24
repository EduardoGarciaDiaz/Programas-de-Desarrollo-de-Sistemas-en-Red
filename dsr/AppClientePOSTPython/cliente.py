import http.client

# Definir los datos de inicio de sesión
username = "eduardo"
password = "123456"

# Definir la dirección IP y el puerto del socket
ip = "192.168.56.105"
puerto = "8080"

# Definir los encabezados que se enviarán
headers = {"Content-type": "application/x-www-form-urlencoded"}

# Definir los datos que se enviarán al servidor
data = "username=" + username + "&password" + password

#Crea una conexión con el sockect
connection = http.client.HTTPConnection(ip, puerto)

#Enviar los datos como si fuera un inicio de sesión
connection.request("POST", "/login", body=data, headers=headers)

# Obtener la respuesta del servidor
response = connection.getresponse()

# Verifica si la colicitud se completó correctamente
if response.status == 200:
    print("Inicio de sesión exitosamente!")
else:
    print("Error en el inicio de sesión", response.reason)

# Cierra la conexión
    connection.close()

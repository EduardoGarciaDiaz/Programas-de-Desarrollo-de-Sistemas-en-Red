const express = require('express')
const dotenv = require('dotenv')
const app = express()

// Carga la configuración del archivo .env
dotenv.config();
// Establece el motor de vistas a ejs
app.set('view engine', 'ejs');

// Rutas
const homeRouter = require('./routes/home')
// Dos endpoints: / y /home
app.use("/", homeRouter)
app.use('/home', homeRouter)

// Configuracion de servicios
// Middleware para el manejo de errores (Debe ser el último middleware a utilizar)
const errorhandler = require('./middlewares/errorhandler')
app.use(errorhandler)

// Inicia el servidor web en el puerto SERVER_PORT definido en el archivo .env
app.listen(process.env.SERVER_PORT, () => {
    console.log(`Aplicación de ejemplo escuchando en el puerto:${process.env.SERVER_PORT}`)
})


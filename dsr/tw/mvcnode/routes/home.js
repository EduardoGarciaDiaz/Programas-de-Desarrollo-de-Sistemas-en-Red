// Se crea un enrutador en Express
//Establecemos que la ruta /home será manejada por el contrlador /controllers/home

const express = require('express')
const router = express.Router()
const { index } = require('../controllers/home')    //Importa el controlador de home

// Establece las rutas donde se encuentran los controladores
router.use("/", index)      // Cuando las solicitudes coincidan con la ruta "/" seran manejadas por index (controller)

module.exports = router     //Exporta el enrutador para ser utilizado en otros archivos


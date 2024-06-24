const swaggerAutogen = require('swagger-autogen')();

const doc = {
    info: {
        // nombre de la api
        title: 'Backend Node.js API peliculas',
        description: 'API en Node.js',
    },
    host: 'localhost:3000'
}

// Generar nuevo archivo con documentación
const outputFile = './swagger-output.json';
const routes = ['./index.js'];

// General documentación
swaggerAutogen(outputFile, routes, doc);
const grpc = require("@grpc/grpc-js");
const protoLoader = require("@grpc/proto-loader");
const dotenv = require('dotenv');
const fs = require('fs');
const PROTO_PATH = "./proto/audio.proto";

// Carga la ocnfiguración del archivo .env
dotenv.config();

// Carga la implementación del archivo proto para JS
const packageDefinition = protoLoader.loadSync(PROTO_PATH);
const audioProto = grpc.loadPackageDefinition(packageDefinition);

// Crea el servidor gRPC
const server = new grpc.Server();
server.addService(audioProto.AudioService.service, {downloadAudio: downloadAudioImpl});
// Inicia el servidor en el puerto SERVER_PORT definido en el archivo .env
server.bindAsync(`localhost:${process.env.SERVER_PORT}`, grpc.ServerCredentials.createInsecure(), () => {
    console.log(`Servidor gRPC en ejecución en el puerto ${process.env.SERVER_PORT}`);
});

// Implementación de proto
function downloadAudioImpl(call) {
    // Se crea un stream en chunk de 1024
    const stream = fs.createReadStream(`./recursos/${call.request.nombreArchivo}`, { highWaterMark: 1024 });

    console.log(`\n\nEnviando el archivo: ${call.request.nombreArchivo}`);
    stream.on('data', function (chunk) {
        call.write( { data: chunk } );
        process.stdout.write('.');
    }).on('end', function () {
        call.end();
        stream.close();
        console.log('\nEnvío de datos terminado.');
    });
}
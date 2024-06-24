const errorLogger = (err, req, res, next) => {
    //Aqui se puede enviar el error a un archivo de texto
    console.error(err.message);
    next(err);
}

module.exports = errorLogger;
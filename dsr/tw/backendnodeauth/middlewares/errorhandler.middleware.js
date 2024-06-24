const { stack } = require("sequelize/lib/utils");

const errorHandler = (err, req, res, next) => {
    let mensaje = 'No se ha podido procesar la petición. Inténtelo nuevamente más tarde.'

    if (process.env.NODE_ENV === 'development') {
        const statucCode = err.statusCode || 400;
        mensaje = err.message || mensaje;
        return res.status(statusCode).json({
            success: false,
            status: err.statucCode,
            mensaje: mensaje,
            stack: err.stack
        })
    }

    return res.status(400).send({ mensaje: mensaje });
}

module.exports = errorHandler;
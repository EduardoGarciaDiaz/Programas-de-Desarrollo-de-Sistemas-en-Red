const router = require('express').Router();
const auth = require('../controllers/auth.controller');

//POST: api/auth
router.post('/', auth.login);

//GET: api/auth/tiempo
router.get('/tiempo', auth.tiempo);

module.exports = router;
const router = require('express').Router();
const roles = require('../controllers/roles.controller');

// GET: api/roles
router.get('/', roles.getAll);

module.exports = router;
var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

/* Login Route */
router.post('/login', function(req, res, next) {
  /* take user post attributes */
  // e.g. username and password

  // go to database to verify username and password

  // Generator JWT with a uid (username) claim
  // send back JWT to user
});

module.exports = router;

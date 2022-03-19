var express = require('express');
var router = express.Router();
const PosExpModel = require('../models/posexp');
const UsersModel = require('../models/usersModel');
const NegExpModel = require('../models/negexp');

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

router.get('/posExp', function(req,res){
  // get data from mongodb and pass it to the view
  PosExpModel.find({}, function (err,data) {
    if (err) throw err;
    res.json(data);
  });
});


router.get('/getPositiveExperienceData/:posIdGraph', function(req,res){
  // get data from mongodb and pass it to the view
  UsersModel.find({_id: req.params.posIdGraph}, function(err, data){ 
    if (err) throw err;
    res.json(data);
  }); 
});


router.post('/newPosExp', function(req,res){
  // get data from the view and add it to mongodb
  var newPosExp = PosExpModel(req.body).save(function(err,data){
    if (err) throw err;
    res.json(data);
  });
});

router.get('/negExp', function(req,res){
  // get data from mongodb and pass it to the view
  NegExpModel.find({}, function(err, data){
    if (err) throw err;
    res.json(data);
  });
});

router.post('/newNegExp', function(req,res){
  // get data from the view and add it to mongodb
  var newNegExp = NegExpModel(req.body).save(function(err,data){
    if (err) throw err;
    res.json(data);
  });
});

router.get('/users', function(req,res){
  // get data from mongodb and pass it to the view
  let users = new UsersModel();
  users.find({}, function(err, data){
    if (err) throw err;
    res.render('users', {experiences: data});
  });
});

router.post('/newUser', function(req,res){
  // get data from the view and add it to mongodb
  var newUser = UsersModel(req.body).save(function(err,data){
    if (err) throw err;
    res.json(data);
  });
});


//GET user's set of positive ids
// 1. Get with USER id set of positive ids
// 2. for all the pos exp in the set get the pos objects with the positive ids
router.get('/getUsersData/:userIdFire', function(req,res){
  // get data from mongodb and pass it to the view
  UsersModel.find({userId: req.params.userIdFire}, function(err, data){ 
    if (err) throw err;
    res.json(data);
  }); 
});



module.exports = router;

var express = require('express');
var router = express.Router();
const PosExpModel = require('../models/posexp');
const UsersModel = require('../models/usersModel');
const NegExpModel = require('../models/negexp');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
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

router.get('/getAllUserPosExp/:userFireId', function(req, res){
  PosExpModel.find({userId: req.params.userFireId}, function(err, data){
    if (err) throw err;

      var idArray = []
      data.forEach(element => {
        var id = (element._id).toString()
        idArray.push(id)
      })
       res.json(idArray);        
  })
});

router.put('/updateUserPosExp/:userFireId/:posSetIds', function(req,res){
  UsersModel.updateOne({userId: req.params.userFireId},{$set:{positiveExpSet:req.params.posSetIds}}, function(err, dataUpdate){
    if (err) throw err;
    console.log(dataUpdate)
    res.json(dataUpdate);
  
});
})
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

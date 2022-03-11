var bodyParser = require('body-parser');
var mongoose = require('mongoose');

//mongoose.set('useNewUrlParser', true);
//mongoose.set('useUnifiedTopology', true);

mongoose.connect('mongodb+srv://oloughlinaoife1:gardenPicnic123!@finalyear.aeozg.mongodb.net/gratitudeApp?retryWrites=true&w=majority');

  var urlencodedParser = bodyParser.urlencoded({extended: false});
  module.exports = function(app){  
    
}
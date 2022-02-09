var bodyParser = require('body-parser');
var mongoose = require('mongoose');

//mongoose.set('useNewUrlParser', true);
//mongoose.set('useUnifiedTopology', true);

mongoose.connect('mongodb+srv://oloughlinaoife1:gardenPicnic123!@finalyear.aeozg.mongodb.net/gratitudeApp?retryWrites=true&w=majority');




  var urlencodedParser = bodyParser.urlencoded({extended: false});
  module.exports = function(app){

    
  
  /*  app.delete('/pos/:item', function(req,res){
      // delete the requested item form mongodb
      // When an item comes through as a URL it has hyphens between the words not spaces
      // So we have to replace the hyphens with spaces to match up properly
      Todo.find({item: req.params.item.replace(/\-/g, " ")}).deleteOne(function(err,data){
        if (err) throw err;
        res.json(data);
      });*/
  
      /* If not using db remove data from local data array of todo objects
        data = data.filter(function(todo){
        return todo.item.replace(/ /g, '-') !== req.params.item;
      });   */
  
    
}
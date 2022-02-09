var express = require('express');
//var controller = require('./controllers/database.js');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var bodyParser = require('body-parser');
var mongoose = require('mongoose');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');

//mongoose.set('useNewUrlParser', true);
//mongoose.set('useUnifiedTopology', true);

var app = express();

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/users', usersRouter);

module.exports = app;

const mongoURL = 'mongodb+srv://oloughlinaoife1:gardenPicnic123!@finalyear.aeozg.mongodb.net/gratitudeApp?retryWrites=true&w=majority';
mongoose.connect(mongoURL, {useNewUrlParser: true, useUnifiedTopology: true});

mongoose.connection.on('error', (err) => {
  console.log('Mongoose Connection Error!', err);
});

// static files
//app.use(express.static('./public'));

//fire controllers
//controller(app);

// listen to port
app.listen(3001);
console.log('Listening on port 3001');

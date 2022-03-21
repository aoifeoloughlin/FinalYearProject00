const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const usersSchema = new Schema({
    userId:{
        type: String
    },
    userName: {
        type: String
    },
    positiveExpSet:{
        type: Array
    },
    negativeExpSet:{
        type: Array
    },
    ratioScore:{
        type: String
    }
});

module.exports = mongoose.model('UsersModel', usersSchema);
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
        type: Array,
        default: [0]
    },
    negativeExpSet:{
        type: Array,
        default: [0]
    },
    ratioScore:{
        type: String,
        default: 4
    }
});

module.exports = mongoose.model('UsersModel', usersSchema);
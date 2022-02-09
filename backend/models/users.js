const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const usersSchema = new Schema({
    userName: {
        type: String
    },
    password: {
        type: String
    },
    dateOfBirth: {
        type: Date
    }
});

module.exports = mongoose.model('Users', usersSchema);
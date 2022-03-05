const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const negexpSchema = new Schema({
    negExperience: {
        type: String
    },
    datePosted: {
        type: Date
    },
    weight:{
        type: Number
    }
});

module.exports = mongoose.model('NegExp', negexpSchema);
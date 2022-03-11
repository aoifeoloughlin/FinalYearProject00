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
    }, 
    userId:{
        type: String
    }
});

module.exports = mongoose.model('NegExp', negexpSchema);
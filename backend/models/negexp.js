const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const negexpSchema = new Schema({
    negExperience: {
        type: String
    },
    datePosted: {
        type: Date
    }
});

module.exports = mongoose.model('NegExp', negexpSchema);
const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const posexpSchema = new Schema({
    posExperience: {
        type: String
    },
    datePosted: {
        type: Date
    }
});

module.exports = mongoose.model('PosExp', posexpSchema);
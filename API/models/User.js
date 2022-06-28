const mongoose = require('mongoose')
const validator = require('validator')
const bcrypt = require('bcryptjs')
const jwt = require('jsonwebtoken')

const userSchema = mongoose.Schema({
    firstName:{
        type: String,
        required: true,
        trim: true
    },
    lastName:{
        type: String,
        required: true,
        trim: true
    },
    email:{
        type: String,
        required: true,
        unique: true,
        lowercase: true,
        validate: value => {
            if(!validator.isEmail(value)) {
                throw new Error({error: 'Invalid Email Address.'})
            }
        }
    },
    password:{
        type: String,
        required: true,
        minLength: 4,
        trim: true
    },
    tokens: [{
        token: {
            type: String,
            required: true
        }
    }]
})

userSchema.pre('save', async function (next){
    // Hashing the password
    const user = this
    if(user.isModified('password')){
        user.password = await bcrypt.hash(user.password, 8)
    }
})

userSchema.static.findByCredentials = async(email, password) => {
    // Mongoose call
    const user = await user.findOne({email})
    if(!user){
        throw new Error({error: "Invalid login credentials."})
    }
    const isPasswordMatch = await bcrypt.compare(password, user.password)
    if(!isPasswordMatch){
        throw new Error({error: "Invalid login credentials."})
    }
    return user
}

const User = mongoose.model('User', userSchema)
module.exports = User
const express = require('express')
const userRouter = require('./routers/user')
const geoRouter = require('./routers/geocode')
const port = process.env.PORT
require('./db/db')

const app = express()

app.use(express.urlencoded({extended: true}))
app.use(express.json())
app.use(userRouter)
app.use(geoRouter)

app.listen(port, () => {
    console.log(`Server running on port ${port}`)
})
import express, { Express, Request, Response } from "express";
import { geoRouter } from "./routers/geocode";
import config from "./config"

const app : Express = express();
const port = config.PORT;

app.use(express.urlencoded({extended: true}))
app.use(express.json())
app.use(geoRouter)

app.listen(port, () => {
    console.log(`Server running at https://localhost:${port}`)
})
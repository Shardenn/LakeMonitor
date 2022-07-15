import {Client} from "@googlemaps/google-maps-services-js";

import config from "../config"

import * as express from "express";
export const geoRouter = express.Router();
const client = new Client({})

geoRouter.get('/geo', async(req, res) => {
    client.reverseGeocode({
        params:{
            key: config.GOOGLE_MAPS_API_KEY,
            latlng: {lat: 60.684112, lng: 24.216948}
        }
    }
        ).then((geoRes) => {
        res.send(geoRes.data);
    })
    .catch((error) => {
        console.log("Error catching a geocoding promise");
        res.send(error);
    })
})
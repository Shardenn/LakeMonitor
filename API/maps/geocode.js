const express = require('express')
const router = express.Router()

const args = {
    params: {
        key: 'AIzaSyAnlQuwWSISKuKURMwSkFCse70bF_k2dRY',
        latlng: "60.684112, 24.216948"
    }
}

const { Client }  = require('@googlemaps/google-maps-services-js')

router.get('/geo', async(req, res) => {
    res.status(200).send("hi")
    try {
        const client = new Client({})

        client.geocode(args).then((r) => {
            console.log(r.data.results[0].plus_code.compound_code)
        })
        .catch((e) => {
            console.log(e.response.data.error_message)
        })
        
        res.status(201).send({r})
    } catch (error) {
        res.status(400).send({error})
    }
})
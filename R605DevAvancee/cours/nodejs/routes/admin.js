const express = require("express");
const router = express.Router();
const path = require("path");
const rootApps = require("../outils/path");
const navCtrl = require("../controllers/navController");

router.get('/ajout',(req,res,next) => {
    navCtrl.getAjoutProduits(req,res,next);
})

router.post('/ajout',(req,res,next) => {
    navCtrl.postAjoutProduit(req,res,next);
})

module.exports = router;
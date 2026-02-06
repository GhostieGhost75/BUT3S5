const express = require("express");
const router = express.Router();
const path = require("path");
const rootApps = require("../outils/path");
const navCtrl = require("../controllers/navController");

const {produits} = require("./admin");

router.get('/',(req,res,next) => {
    navCtrl.getBoutique(req,res,next);
})

module.exports = router;
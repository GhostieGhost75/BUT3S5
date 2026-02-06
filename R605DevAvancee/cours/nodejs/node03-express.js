const express = require("express");
const port = 3000;
const app = express();
const bodyParser = require("body-parser");

let html="<!DOCTYPE html><html><head><title>page ajout</title></head>";
html+="<body><h1>Page d'ajout</h1>";
html+="<form action='produit' method='POST'>";
html+="<label for='produit'>Produit</label>";
html+="<input type='text' name='produit' placeholder='Produit'/>";
html+="<button type='submit' id='produit' value='produit'>Produit</button>";
html+="</form>";
html+="</body></html>";

app.listen(port, () => {
    console.log("Server Express est à l'écoute sur  port : " + port);
})

app.use(bodyParser.urlencoded({extended: false}));

app.get('/ajout',(req,res,next) => {
    console.log('middleware ajout', req.method);
    res.send(html);
})

app.post('/produit',(req,res,next) => {
    console.log('middleware produit', req.method);
    console.log(req.body);
    res.redirect('/ajout');
})

app.get('/',(req,res,next) => {
    console.log('middleware racine', req.method);
    res.send("<h1>Ma page web</h1>");
})
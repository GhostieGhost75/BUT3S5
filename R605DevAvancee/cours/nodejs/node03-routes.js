const express = require("express");
const port = 3000;
const app = express();
const bodyParser = require("body-parser");
const path = require("path");
const session = require("express-session");

const error404 = require('./controllers/error404');
const clientRoutes = require('./routes/client');
const adminRoutes = require('./routes/admin')

app.listen(port, () => {
    console.log("Server Express est à l'écoute sur le port : " + port);
})

app.set("view engine", "ejs");
app.set("views", "views");

app.use(session({secret: 'secret',resave: false, saveUninitialized: false}));
app.use(bodyParser.urlencoded({extended: false}));
app.use(express.static(path.join(__dirname,'public')));

app.use('/admin',adminRoutes);
app.use(clientRoutes);

app.use(error404.getError404);
const mod01= require("./module/module01.js");
const colors = require("colors");
const addition = require("./module/module02.js");
const multiplication = require("./module/module02");

console.log("afficher les nombres de 1 à 10");
for (var i =1;i<=10;i++){
    console.log(i)
}

console.log(colors.green("bonjour tout le monde"));
console.log(addition.addition(2,5))
console.log(multiplication.multiplication(3,5))

const fs = require("fs");
let data1 = fs.readFileSync("data/fichier.txt", "utf8");
console.log(data1);

let data2 = fs.readFileSync("data/fichier.txt");
console.log(data2);

fs.readFile("data/fichier.txt", "utf8",function(err,data3){
    console.log(colors.green(data3));
});

fs.readFile("fichier2.txt", "utf8",function(err,data4){
    console.log(colors.blue(data4));
});


console.log("la suite du programme")

fs.appendFile("data/fichier.txt","\n re bonjour les enfants",function(err) {
    fs.readFile("data/fichier.txt","utf8",function(err,data5){
        console.log(colors.red(data5));
    });
});
const http = require('http');
const port = 3000;
const fs = require("fs");

const server = http.createServer();

server.on("request", (request, response) => {
    const method = request.method;
    const url = request.url;
    if (url ==="/"){
        response.setHeader("Content-Type", "text/html");
        response.setHeader("Charset", "utf-8");
        var html="";
        html+="<html lang='fr'><head><title>titre</title><meta charset='UTF-8'></head></html>";
        html+="<body><h1>Formulaire</h1></body>";
        html+="<form action='/message' method='post'>";
        html+="<label for='message'>Message</label>";
        html+="<input type='text' name='message' method='post'>";
        html+="<button type='submit'>Valider</button></form>";
        html+="</body></html>";
        return response.end(html)
    }
    if (url==='/message' && request.method==='POST'){
        const dataMessage=[];
        let message;
        request.on('data', (chunk) => {
            console.log(chunk);
            dataMessage.push(chunk);
        })
        request.on('end', () => {
            const messageParsed= Buffer.concat(dataMessage).toString()
            console.log(messageParsed);
            message = messageParsed.split("=")[1];
            console.log(message);
            fs.writeFileSync("message.txt",message);
        })
        console.log(dataMessage);
        response.statusCode = 302;
        response.setHeader('Location', '/');
        return response.end();
    }
    response.setHeader("Content-Type", "text/html");
    response.setHeader("Charset", "utf-8");
    var html="";
    html+="<html lang='fr'><head><title>titre</title><meta charset='UTF-8'></head></html>";
    html+="<body><h1>Bonjour tout le monde</h1></body>";
    html+="</body></html>";
    response.end(html)

})

server.listen(port);
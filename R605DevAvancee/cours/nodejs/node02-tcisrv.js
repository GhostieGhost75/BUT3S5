const net = require('net');
const server = net.createServer((socket)=>{
    console.log("un client est connecté");
    var timer=setInterval(()=>{
        socket.write((new Date()).toString());
    },2000);

    socket.on("error",()=>{
        console.log("error");
        clearInterval(timer);
    });

    server.on("listening",()=>{
        console.log("server started");
    });
});

server.listen(3000);
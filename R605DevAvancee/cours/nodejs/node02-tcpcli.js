const net = require('net');
const clientTCP = net.connect(3000);
clientTCP.setEncoding("utf8")
clientTCP.on("data",function(chunk){
    console.log(chunk);
});
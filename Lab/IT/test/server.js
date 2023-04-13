const cluster = require('cluster');
const http = require('http').createServer(function(req, res) {
    console.log(process.pid);

    res.writeHead(200, {'Content-Type': 'text/html'});
    res.end('Hello World!');
});

if (cluster.isMaster) {
    for (let i = 0; i < 4; i++) {
        cluster.fork();
    }

}else{
    console.log(process.pid);

    http.listen(3000, "0.0.0.0", 1)

}


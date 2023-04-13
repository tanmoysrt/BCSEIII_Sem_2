const cluster = require('cluster');


if (cluster.isMaster) {
    for (let i = 0; i < 4; i++) {
        cluster.fork();
    }
}else{
    const http = require('http').createServer(function(req, res) {
        res.writeHead(200, {'Content-Type': 'text/html'});
        res.end('Hello World!');
    });
    http.listen(3000)

}


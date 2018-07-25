const http = require('http');
const express = require('express');
const morgan = require('morgan');
const webServerConfig = require('../config/web-server.js');
// line that requires ../config/web-server.js here
const database = require('./database.js');

let httpServer;

function initialize() {
    return new Promise((resolve, reject) => {
        const app = express();
        httpServer = http.createServer(app);
        // Combines logging info from request and response
        app.use(morgan('combined'));

        // *** app.get call below this line ***
/*        app.get('/', (req, res) => {
            res.end('Hello World! Eres el exito!!');
        });*/
        // *** line that adds morgan to app here ***

        app.get('/', async (req, res) => {
            const result = await database.simpleExecute('select user, systimestamp from dual');
            const user = result.rows[0].USER;
            const date = result.rows[0].SYSTIMESTAMP;

            res.end(`DB user: ${user}\nDate: ${date}`);
        });

        httpServer.listen(webServerConfig.port, err => {
            if (err) {
                reject(err);
                return;
            }

            console.log(`Web server listening on localhost:${webServerConfig.port}`);

            resolve();
        });
    });
}

module.exports.initialize = initialize;


//Close Web Server

function close() {
    return new Promise((resolve, reject) => {
        httpServer.close((err) => {
            if (err) {
                reject(err);
                return;
            }

            resolve();
        });
    });
}

module.exports.close = close;
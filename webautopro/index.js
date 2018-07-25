const webServer = require('./services/web-server.js');

async function startup() {
    console.log('Starting application');

    try {
        console.log('Initializing web server module');

        await webServer.initialize();
    } catch (err) {
        console.error(err);

        process.exit(1);
    }
}

startup();

/*
* Recordar instalar lo siguiente:
*       npm  init   - y
*       npm  install  express   - s
*       node   .   //<--- con esto ejecutamos
* */
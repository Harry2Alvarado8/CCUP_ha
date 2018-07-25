const webServer = require('./services/web-server.js');
//  *** line that requires services/web-server.js is here ***
const database = require('./services/database.js');

// *** line that requires services/web-server.js is here ***
const dbConfig = require('./config/database.js');
const defaultThreadPoolSize = 4;

// Increase thread pool size by poolMax
process.env.UV_THREADPOOL_SIZE = dbConfig.hrPool.poolMax + defaultThreadPoolSize;

async function startup() {
    try {
        console.log('Initializing database module');

        await database.initialize();
    } catch (err) {
        console.error(err);

        process.exit(1); // Non-zero failure code
    }

    // *** existing try block in startup here ***
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
*       [npm  init   - y]
*       [npm  install  express   - s]
*       [node   .]   //<--- con esto ejecutamos
*
*       Al utilizar el modulo de morgan se debe
*       de instalar las dependencias.
*       [npm install morgan -s]
*
*       Se instala la dependencia de oracle
*       [npm install oracledb -s]
*
* */

// *** previous code above this line ***

async function shutdown(e) {
    // *** existing try-catch block in shutdown here ***

    try {
        console.log('Closing database module');

        await database.close();
    } catch (err) {
        console.log('Encountered error', e);

        err = err || e;
    }

    let err = e;

    console.log('Shutting down');

    try {
        console.log('Closing web server module');

        await webServer.close();
    } catch (e) {
        console.log('Encountered error', e);

        err = err || e;
    }

    console.log('Exiting process');

    if (err) {
        process.exit(1); // Non-zero failure code
    } else {
        process.exit(0);
    }
}

process.on('SIGTERM', () => {
    console.log('Received SIGTERM');

    shutdown();
});

process.on('SIGINT', () => {
    console.log('Received SIGINT');

    shutdown();
});

process.on('uncaughtException', err => {
    console.log('Uncaught exception');
    console.error(err);

    shutdown(err);
});
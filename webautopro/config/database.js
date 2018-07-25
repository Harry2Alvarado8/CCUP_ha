module.exports = {
    hrPool: {
        /*user: process.env.HR_USER || T66_AUTOPRO,
        password: process.env.HR_PASSWORD || 123456,
        connectString: process.env.HR_CONNECTIONSTRING || '192.168.0.12/orcl',*/
        user: 'T66_AUTOPRO',
        password:  '123456',
        connectString: '192.168.0.12/orcl',
        poolMin: 10,
        poolMax: 10,
        poolIncrement: 0
    }
};
module.exports = {
    devServer: {
        open: false,
        host: '0.0.0.0',
        port: 8080,
        https: false,
        disableHostCheck: true,
        proxy: {
            '/api': {
                target: 'http://localhost:9003/',
                ws: true,
                changOrigin: true,
                pathRewrite: {
                    '^/api': ''
                }
            }
        }
    }
}

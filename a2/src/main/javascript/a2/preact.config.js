module.exports = function(config) {
    config.devServer.proxy = [
        {
            path: '/api/**',
            target: 'http://localhost:8080',
            changeOrigin: true,
            changeHost: true,
        }
    ];
};
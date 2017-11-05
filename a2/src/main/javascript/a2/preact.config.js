module.exports = function(config, env) {
    if(!env.production) {
        config.devServer.proxy = [
            {
                path: '/api/**',
                target: 'http://localhost:8080',
                changeOrigin: true,
                changeHost: true,
            }
        ];
    }
};
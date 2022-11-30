const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,

  devServer: {
    proxy: {
      "/api": {
        target: "http://192.168.0.36:8089",
        changeOrigin: true,
      },
    },
  },
});
